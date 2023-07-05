package com.tecnoscimmia.nine.controller

import androidx.navigation.NavHostController
import com.tecnoscimmia.nine.R
import com.tecnoscimmia.nine.model.GameSettings

/*
* This file defines the controller for the settings screen
*/

class SettingsController(val navigationCntrl: NavHostController, val settings: GameSettings)
{

	fun setLanguage(newLanguage: String)
	{}

	fun setTheme(newTheme: String)
	{}

	fun setKeyboardLayout(newLayout: String)
	{}
}