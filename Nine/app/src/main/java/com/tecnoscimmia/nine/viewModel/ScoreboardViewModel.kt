package com.tecnoscimmia.nine.controller

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.tecnoscimmia.nine.model.MatchResult
import com.tecnoscimmia.nine.model.MatchResultDb
import com.tecnoscimmia.nine.model.MatchResultRepository
import java.util.Date


/*
 * This file defines the view-model for the scoreboard screen
 */

class ScoreboardViewModel(private val resultRepo: MatchResultRepository) : ViewModel()
{
	// Returns all the data contained in the MatchResult table of the db
	fun getScoreboardData() : LiveData<List<MatchResult>>
	{
		return resultRepo.getAllResults()
	}

	/* TODO: Actually we don't needed those here
	fun addMatchResult(timestamp: Date, matchDuration: String, gameMode: String)
	{
		resultRepo.addMatchResult(timestamp = timestamp, matchDuration = matchDuration, gameMode = gameMode)
	}


	// Deletes data stored in the db about all matches played
	fun deleteScoreboardData()
	{
		resultRepo.deleteAllResults()
	}*/


	// ScoreboardViewModel factory
	companion object
	{
		val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory
		{
			@Suppress("UNCHECKED_CAST")
			override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T
			{
				val application = checkNotNull(extras[APPLICATION_KEY])			// Get the application object form extras
				val savedStateHandle = extras.createSavedStateHandle()			// Create a SavedStateHandle for this ViewModel from extras

				return ScoreboardViewModel(resultRepo = MatchResultRepository(
					dao = MatchResultDb.getInstance(application.applicationContext).matchResultDao()) ) as T
			}
		}
	}

}

