package com.tecnoscimmia.nine.model

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase



/*
 * This file defines all the classes that makes the database in which we store results about
 * matches played in the past
 */

// This class holds data (that are in the database) about a match played in the past, those data will be used in the scoreboard screen
@Entity
data class MatchResult(@PrimaryKey(autoGenerate = true) var id: Int = 0, val time: String, val date: String, val gameMode: String)


// This interface is implemented by the room library and will enable us to perform
// insertion, removal and query of data stored in the db
@Dao
interface MatchResultDao
{
	@Insert
	suspend fun insertResult(result: MatchResult)				// Inserts a new match result in the db

	@Query("SELECT * FROM MatchResult")							// Returns a list containing all the match results stored in the db
	suspend fun getAllResults() : List<MatchResult>

	@Query("DELETE FROM MatchResult")							// Removes all the match results currently stored in the db
	suspend fun clearAllResults()
}


// This class defines the actual database that stores data about matches played in the past
@Database(entities = [MatchResult::class], version = 1, exportSchema = false)
abstract class MatchResultDb : RoomDatabase()
{
	abstract fun matchResultDao(): MatchResultDao

	companion object
	{
		private var instance: MatchResultDb? = null				// Singleton instance

		fun getInstance(cntxt: Context) : MatchResultDb
		{
			if(instance == null)
			{
				instance = Room.databaseBuilder(context = cntxt, klass = MatchResultDb::class.java, name = "matchResults.db").build()
			}

			return instance!!
		}
	}
}

