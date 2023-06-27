package com.tecnoscimmia.nine.controller

import androidx.navigation.NavHostController
import com.tecnoscimmia.nine.view.NineScreen

/*
 * This file defines the MainController class, this class defines the app behaviour in the menu screens.
 */

class MainController(navCntrl: NavHostController)
{
	var navigationCntrl = navCntrl


	fun SwitchToSettingsScreen()
	{
		navigationCntrl.navigate(route = NineScreen.Settings.name)		// Switch to settings screen
	}


	fun SwitchToScoreboardScreen()
	{
		navigationCntrl.navigate(route = NineScreen.Scoreboard.name)	// Switch to scoreboard screen
	}


	fun startGame()
	{
		navigationCntrl.navigate(route = NineScreen.Game.name)			// Switch to game screen
	}

}