package com.tecnoscimmia.nine.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.tecnoscimmia.nine.model.GameSettings
import com.tecnoscimmia.nine.model.SettingsRepository

/*
* This file defines the view-model for the menu screen
*/


class MenuViewModel(private val settings: GameSettings, private val settingsRepo: SettingsRepository) : ViewModel()
{
	private var gameModeIndex = mutableStateOf(0)                    // Current game mode selected in the game mode selector


	// Returns the name of the game mode currently selected
	fun getGameMode(): String
	{
		return settingsRepo.getAvailableGameModes()[gameModeIndex.value]
	}


	// Converts the UI representation of the game mode to the internal one and set's the new game mode in game settings class
	fun setGameMode(newMode: String)
	{
		val newGameMode = try { settingsRepo.gameModeUItoInternal(newMode) } catch(e: Exception) { settings.getGameMode() }
		settings.setGameMode(newGameMode)
	}


	// Decrements the gameModeIndex and changes the selected game mode (if possible)
	fun setPrevGameMode()
	{
		if (gameModeIndex.value > 0)
		{
			gameModeIndex.value--
			setGameMode(settingsRepo.getAvailableGameModes()[gameModeIndex.value])
		}
	}


	// Increments the gameModeIndex and changes the selected game mode (if possible)
	fun setNextGameMode()
	{
		if (gameModeIndex.value < (settingsRepo.getAvailableGameModes().size - 1))
		{
			gameModeIndex.value++
			setGameMode(settingsRepo.getAvailableGameModes()[gameModeIndex.value])
		}
	}


	// MenuViewModel factory
	companion object
	{
		val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory
		{
			@Suppress("UNCHECKED_CAST")
			override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T
			{
				val application = checkNotNull(extras[APPLICATION_KEY])			// Get the application object form extras

				return MenuViewModel(settings = GameSettings.getInstance(application.applicationContext),
					settingsRepo = SettingsRepository.getInstance(application.applicationContext)) as T
			}
		}
	}
}
