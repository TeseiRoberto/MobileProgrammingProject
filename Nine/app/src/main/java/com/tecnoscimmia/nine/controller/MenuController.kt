package com.tecnoscimmia.nine.controller

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavHostController
import com.tecnoscimmia.nine.MainActivity
import com.tecnoscimmia.nine.model.GameSettings
import com.tecnoscimmia.nine.view.NineScreen

/*
* This file defines the controller for the menu screen
*/

class MenuController(val navigationCntrl: NavHostController, val settings: GameSettings)
{
	private var gameModeIndex = 0

	fun getSelectedGameMode() : String
	{
		return settings.gameMode.value
	}

	// Changes the selected GameMode to the next one
	fun setGameModeToNext()
	{
		if(gameModeIndex < (settings.getAvailableGameModes().size - 1))
		{
			gameModeIndex++
			settings.gameMode.value = settings.getAvailableGameModes()[gameModeIndex]
		}
	}

	// Changes the selectedGameMode to the next one
	fun setGameModeToPrev()
	{
		if(gameModeIndex > 0)
		{
			gameModeIndex--
			settings.gameMode.value = settings.getAvailableGameModes()[gameModeIndex]
		}
	}

	fun switchToSettingsScreen()
	{
		navigationCntrl.navigate(route = NineScreen.Settings.name)		// Switch to settings screen
	}

	fun switchToScoreboardScreen()
	{
		navigationCntrl.navigate(route = NineScreen.Scoreboard.name)	// Switch to scoreboard screen
	}

	fun startNewGame()
	{
		//navigationCntrl.navigate(route = NineScreen.Game.name)			// Switch to game screen
	}
}