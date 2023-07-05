package com.tecnoscimmia.nine.controller

import androidx.navigation.NavHostController
import com.tecnoscimmia.nine.model.MatchResult

/*
 * This file defines the controller for the scoreboard screen
 */

class ScoreboardController(val navigationCntrl: NavHostController)
{
	fun loadScoreboardData() : List<MatchResult>
	{
		// TODO: Now we simply return some hard coded data but we actually need to retrieve this data from a database
		val testData = listOf(
			MatchResult(time = "21:00", date = "18/08/2015", gameMode = "Free"),
			MatchResult(time = "1:50", date = "12/04/2023", gameMode = "Free"),
			MatchResult(time = "2:00", date = "31/01/2044", gameMode = "Challenge"),
			MatchResult(time = "1:00", date = "1/11/202020", gameMode = "Free"),
			MatchResult(time = "08:59:12", date = "7/33/1245", gameMode = "Free"),
			MatchResult(time = "08:59:12", date = "7/33/1245", gameMode = "Free"),
			MatchResult(time = "08:59:12", date = "7/33/1245", gameMode = "Challenge"),
			MatchResult(time = "18:01", date = "7/33/1245", gameMode = "Free"),
			MatchResult(time = "08:59:12", date = "7/33/1245", gameMode = "Free"),
			MatchResult(time = "08:59:12", date = "7/33/1245", gameMode = "Challenge"),
			MatchResult(time = "08:59:12", date = "7/33/1245", gameMode = "Free"),
			MatchResult(time = "08:59:12", date = "7/33/1245", gameMode = "Free"),
			MatchResult(time = "08:59:12", date = "7/33/1245", gameMode = "Free"),
			MatchResult(time = "08:59:12", date = "7/33/1245", gameMode = "Challenge"),
			MatchResult(time = "08:59:12", date = "7/33/1245", gameMode = "Free"),
			MatchResult(time = "08:59:12", date = "7/33/1245", gameMode = "Free"),
			MatchResult(time = "08:59:12", date = "7/33/1245", gameMode = "Challenge"),
		)

		return testData
	}
}