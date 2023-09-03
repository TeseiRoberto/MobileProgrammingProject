package com.tecnoscimmia.nine.model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


/*
 * This file defines all the classes that makes the database in which we store results about
 * matches played in the past
 */

// This class holds data (that are in the database) about a match played in the past, those data
// will be used in the scoreboard screen (room uses this class to define the MatchResult table)
@Entity
data class MatchResult(@PrimaryKey(autoGenerate = true) var id: Int = 0, var duration: String, var date: String, var gameMode: String)


// This interface is implemented by the room library and will enable us to perform insertion, removal and query of data stored in the db
@Dao
interface MatchResultDao
{
	@Insert
	suspend fun insertResult(result: MatchResult)				// Inserts a new match result in the db

	@Query("SELECT * FROM MatchResult ORDER BY duration")		// Returns a list containing all the match results stored in the db ordered by duration
	suspend fun getAllResults() : List<MatchResult>

	@Query("DELETE FROM MatchResult")							// Removes all the match results currently stored in the db
	suspend fun clearAllResults()
}


// This class defines the actual database that stores data about matches played in the past
@Database(entities = [MatchResult::class], version = 1)
abstract class MatchResultDb : RoomDatabase()
{
	abstract fun matchResultDao(): MatchResultDao

	companion object
	{
		private var instance: MatchResultDb? = null				// Singleton instance

		fun getInstance(cntxt: Context) : MatchResultDb
		{
			synchronized(this)
			{
				if(instance == null)
				{
					instance = Room.databaseBuilder(context = cntxt, klass = MatchResultDb::class.java, name = "matchResults.db").build()
				}
			}

			return instance!!
		}
	}
}


// Note: this class needs access to the settings repo so that it can convert internal representation of values to UI one
class MatchResultRepository(private val dao: MatchResultDao, private val settingsRepo: SettingsRepository)
{
	private val matchResultsData = MutableLiveData<List<MatchResult>>()


	// Inserts a new record in the MatchResult table of the db
	fun addMatchResult(matchDuration: String, matchGameMode: String)
	{
		CoroutineScope(Dispatchers.IO).launch()
		{
			val today = LocalDate.now()
			dao.insertResult(MatchResult(duration = matchDuration, date = today.toString(), gameMode = matchGameMode))
		}
	}


	fun getAllResults() : LiveData<List<MatchResult>>
	{
		CoroutineScope(Dispatchers.IO).launch()
		{
			val dbData = dao.getAllResults()

			for(el in dbData)
			{
				el.gameMode = settingsRepo.gameModeInternalToUI(GameSettings.GameModeSetting.valueOf(el.gameMode))

				// Convert date to user locale
				val date = LocalDate.parse(el.date)
				el.date = date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)).toString()
			}

			matchResultsData.postValue(dbData)
		}

		return matchResultsData
	}


	// Deletes all the elements in the MatchResult table
	fun deleteAllResults()
	{
		CoroutineScope(Dispatchers.IO).launch()
		{
			dao.clearAllResults()
		}
	}
}


