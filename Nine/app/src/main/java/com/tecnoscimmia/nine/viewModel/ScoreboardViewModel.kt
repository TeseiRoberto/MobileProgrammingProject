package com.tecnoscimmia.nine.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.tecnoscimmia.nine.model.MatchResult
import com.tecnoscimmia.nine.model.MatchResultDb
import com.tecnoscimmia.nine.model.MatchResultRepository
import com.tecnoscimmia.nine.model.SettingsRepository


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


	// ScoreboardViewModel factory
	companion object
	{
		val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory
		{
			@Suppress("UNCHECKED_CAST")
			override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T
			{
				val application = checkNotNull(extras[APPLICATION_KEY])			// Get the application object form extras

				return ScoreboardViewModel(resultRepo = MatchResultRepository(
					dao = MatchResultDb.getInstance(application.applicationContext).matchResultDao(),
					settingsRepo = SettingsRepository.getInstance(application.applicationContext))
				) as T
			}
		}
	}

}

