package com.tecnoscimmia.nine.controller

import androidx.navigation.NavHostController
import com.tecnoscimmia.nine.model.GameSettings
import com.tecnoscimmia.nine.view.NineScreen

/*
* This file defines the controller and the view model class for the menu screen
*/

class MenuController(val navigationCntrl: NavHostController, val settings: GameSettings)
{

	fun switchToSettingsScreen()
	{
		navigationCntrl.navigate(route = NineScreen.Settings.name)		// Switch to settings screen
	}

	fun switchToScoreboardScreen()
	{
		navigationCntrl.navigate(route = NineScreen.Scoreboard.name)	// Switch to scoreboard screen
	}

	fun startNewGame(i: Int)
	{
		var gameMode = ""
		if(i in 0 until settings.getAvailableGameModes().size)
			gameMode = settings.getAvailableGameModes()[i]
		else
			return

		// TODO: Start the game (Uncomment when the game screen is implemented)
		//navigationCntrl.navigate(route = NineScreen.Game.name)			// Switch to game screen
	}
}

/*
// This class keeps track of data in the menu screen that needs to survive to configuration changes
class MenuViewModel(var gameModeIndex: Int, var selectedGameMode: MutableState<String>) : ViewModel()
{

	companion object
	{
		// This is the factory that creates instances of this view model class
		val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
			@Suppress("UNCHECKED_CAST")
			override fun <T: ViewModel> create(modelClass: Class<T>, extras: CreationExtras) : T
			{
				// Get the Application object from extras
				val application = checkNotNull(extras[APPLICATION_KEY])

				// Create a SavedStateHandle for this ViewModel from extras
				val savedStateHandle = extras.createSavedStateHandle()

				// The first time that the view model gets created we need to set a current game mode,
				// this default game mode is loaded directly from the resource file
				val index = 0
				val defaultGameMode = application.resources.getStringArray(R.array.setting_game_mode_values)[index]

				return MenuViewModel(gameModeIndex = index, selectedGameMode = mutableStateOf(defaultGameMode)) as T
			}
		}
	}
}*/