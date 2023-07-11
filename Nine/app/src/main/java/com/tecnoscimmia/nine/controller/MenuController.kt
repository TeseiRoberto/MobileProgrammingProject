package com.tecnoscimmia.nine.controller

import android.content.Context
import androidx.navigation.NavHostController
import com.tecnoscimmia.nine.MainActivity
import com.tecnoscimmia.nine.R
import com.tecnoscimmia.nine.model.GameSettings
import com.tecnoscimmia.nine.view.NineScreen

/*
* This file defines the controller for the menu screen
*/

class MenuController(val navigationCntrl: NavHostController, val settings: GameSettings, val appCntxt: Context)
{
	// Load the available game modes from the resource file
	val availableGameModes = listOf(
		appCntxt.resources.getString(R.string.settings_game_mode_training),
		appCntxt.resources.getString(R.string.settings_game_mode_challenge)
		)


	// This function converts the internal representation of the game mode value to the UI one
	fun getGameMode() : String
	{
		return when(settings.getGameMode())
		{
			GameSettings.GameModeSetting.TRAINING_GAME_MODE -> appCntxt.getString(R.string.settings_game_mode_challenge)
			GameSettings.GameModeSetting.CHALLENGE_GAME_MODE -> appCntxt.getString(R.string.settings_game_mode_challenge)
		}
	}


	// Changes the value of the game mode setting
	fun setGameMode(newGameMode: String)
	{
		val newGameMode = when (newGameMode)					// Convert given string to the internal representation that game settings class uses
		{
			appCntxt.getString(R.string.settings_game_mode_challenge) -> GameSettings.GameModeSetting.CHALLENGE_GAME_MODE
			appCntxt.getString(R.string.settings_game_mode_challenge) -> GameSettings.GameModeSetting.CHALLENGE_GAME_MODE
			else -> return
		}

		settings.setGameMode(newGameMode)
	}


	// Navigate to the settings screen
	fun switchToSettingsScreen()
	{
		navigationCntrl.navigate(route = NineScreen.Settings.name)		// Switch to settings screen
	}


	// Navigate to the scoreboard screen
	fun switchToScoreboardScreen()
	{
		navigationCntrl.navigate(route = NineScreen.Scoreboard.name)	// Switch to scoreboard screen
	}


	// Navigate to the game screen, no need to pass the game mode because that is stored in the game settings class
	fun startNewGame(i: Int)
	{
		navigationCntrl.navigate(route = NineScreen.Game.name)			// Switch to game screen
	}
}
