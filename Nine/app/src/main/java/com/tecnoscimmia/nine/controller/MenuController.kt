package com.tecnoscimmia.nine.controller

import androidx.navigation.NavHostController
import com.tecnoscimmia.nine.MainActivity
import com.tecnoscimmia.nine.model.GameSettings
import com.tecnoscimmia.nine.view.NineScreen

/*
* This file defines the controller for the menu screen
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
		/* TODO: Need to check that current game mode is actually available and need to pass that to game controller some way
		var gameMode = ""
		if(i in 0 until settings.getAvailableGameModes().size)
			gameMode = settings.getAvailableGameModes()[i]
		else
			return*/

		navigationCntrl.navigate(route = NineScreen.Game.name)			// Switch to game screen
	}
}
