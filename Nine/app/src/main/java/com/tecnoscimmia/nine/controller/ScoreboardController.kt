package com.tecnoscimmia.nine.controller

import androidx.lifecycle.LiveData
import androidx.navigation.NavHostController
import com.tecnoscimmia.nine.model.MatchResult
import com.tecnoscimmia.nine.model.MatchResultRepository
import java.util.Date

/*
 * This file defines the controller for the scoreboard screen
 */


class ScoreboardController(val navigationCntrl: NavHostController, val resultRepo: MatchResultRepository)
{
	// Returns all the data contained in the MatchResult table of the db
	fun getScoreboardData() : LiveData<List<MatchResult>>
	{
		return resultRepo.getAllResults()
	}


	// Stores data about a match played in the db
	fun addMatchResult(timestamp: Date, matchDuration: String, gameMode: String)
	{
		resultRepo.addMatchResult(timestamp = timestamp, matchDuration = matchDuration, gameMode = gameMode)
	}


	// Deletes data stored in the db about all matches played
	fun deleteScoreboardData()
	{
		resultRepo.deleteAllResults()
	}

}


