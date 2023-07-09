package com.tecnoscimmia.nine.controller

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.tecnoscimmia.nine.model.MatchResult
import com.tecnoscimmia.nine.model.MatchResultDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

/*
 * This file defines the controller for the scoreboard screen
 */

class ScoreboardController(val navigationCntrl: NavHostController, val db: MatchResultDb)
{


	// Inserts the result of a match played in the database, the insertion is made using a coroutine
	fun addMatchResult(timestamp: Date, matchDuration: String, gameMode: String)
	{
		CoroutineScope(Dispatchers.IO).launch()
		{
			val dateFormat = SimpleDateFormat("yyyy/mm/dd")
			val date = dateFormat.format(timestamp)

			db.matchResultDao().insertResult(MatchResult(date = date, time = matchDuration, gameMode = gameMode))
		}
	}


	// Clears the content of the matchResult table
	fun clearResults()
	{
		CoroutineScope(Dispatchers.IO).launch()
		{
			db.matchResultDao().clearAllResults()
		}
	}


	// Loads a list of match played in the past from the database, the operation is executed in a coroutine
	// and when it's completed the onCompletion callback is called
	@OptIn(ExperimentalCoroutinesApi::class)
	fun loadScoreboardData() : List<MatchResult>
	{
		// TODO: Now we simply return some hard coded data but we actually need to retrieve this data from a database
		val testData = listOf(
			MatchResult(id = 1, time = "21:00", date = "18/08/2015", gameMode = "Free"),
			MatchResult(id = 2, time = "1:50", date = "12/04/2023", gameMode = "Free"),
			MatchResult(id = 3, time = "2:00", date = "31/01/2044", gameMode = "Challenge"),
			MatchResult(id = 4, time = "1:00", date = "1/11/202020", gameMode = "Free"),
			MatchResult(id = 5, time = "08:59:12", date = "7/33/1245", gameMode = "Free"),
			MatchResult(id = 6, time = "08:59:12", date = "7/33/1245", gameMode = "Free"),
			MatchResult(id = 7, time = "08:59:12", date = "7/33/1245", gameMode = "Challenge"),
			MatchResult(id = 8, time = "18:01", date = "7/33/1245", gameMode = "Free"),
			MatchResult(id = 9, time = "08:59:12", date = "7/33/1245", gameMode = "Free"),
			MatchResult(id = 10, time = "08:59:12", date = "7/33/1245", gameMode = "Challenge"),
			MatchResult(id = 11, time = "08:59:12", date = "7/33/1245", gameMode = "Free"),
			MatchResult(id = 12, time = "08:59:12", date = "7/33/1245", gameMode = "Free"),
			MatchResult(id = 13, time = "08:59:12", date = "7/33/1245", gameMode = "Free"),
			MatchResult(id = 14, time = "08:59:12", date = "7/33/1245", gameMode = "Challenge"),
			MatchResult(id = 15, time = "08:59:12", date = "7/33/1245", gameMode = "Free"),
			MatchResult(id = 16, time = "08:59:12", date = "7/33/1245", gameMode = "Free"),
			MatchResult(id = 17, time = "08:59:12", date = "7/33/1245", gameMode = "Challenge"),
		)
		return testData

/*
	// TODO: I need to make this shit work!
		if(scoreboardData != null)								// If data is already loaded
			return

		val result = CoroutineScope(Dispatchers.IO).async()		// Start the coroutine to load the match results
		{
			db.matchResultDao().getResults()
		}

		result.invokeOnCompletion {								// Set callback function that will be invoked when data is ready
			if(it == null)										// If there was no error
				scoreboardData = result.getCompleted()			// We can finally retrieve the data
			else    											// Otherwise if there was some error then we set data to an empty list
				scoreboardData = listOf<MatchResult>()

			onCompletion()
		}*/
	}
}


