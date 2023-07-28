package com.tecnoscimmia.nine.model

import android.content.Context
import com.tecnoscimmia.nine.R

/*
 * This file defines the SettingRepository class, this class is a singleton and is responsible of
 * loading all the available themes, keyboard layouts, game modes from the strings.xml file
*/

class SettingsRepository private constructor()
{
	companion object
	{
		private var instance : SettingsRepository? = null

		private var availableThemes: 			List<String>? = null 	// List of all the available themes for the app
		private var availableKeyboardLayouts: 	List<String>? = null	// List of all the available keyboard layouts for the game keyboard
		private var availableSymbolsSets: 		List<String>? = null	// List of all the available symbols sets
		private var availableGameModes: 		List<String>? = null	// List of all the game modes in which the game can be played in


		fun getInstance(appCntxt: Context) : SettingsRepository
		{
			if(instance != null)
				return instance!!

			instance = SettingsRepository()

			// Load available themes from strings.xml
			availableThemes = listOf(
				appCntxt.getString(R.string.settings_theme_light),
				appCntxt.resources.getString(R.string.settings_theme_dark)
			)

			// Load available keyboard layouts from strings.xml
			availableKeyboardLayouts = listOf(
				appCntxt.resources.getString(R.string.settings_keyboard_layout_two_lines),
				appCntxt.resources.getString(R.string.settings_keyboard_layout_3x3)
			)

			// Load available symbols sets from strings.xml
			availableSymbolsSets = listOf(
				appCntxt.getString(R.string.settings_symbols_set_numbers),
				appCntxt.getString(R.string.settings_symbols_set_letters),
				appCntxt.getString(R.string.settings_symbols_set_emoticons)
			)

			// Load available game modes from strings.xml
			availableGameModes = listOf(
				appCntxt.resources.getString(R.string.settings_game_mode_training),
				appCntxt.resources.getString(R.string.settings_game_mode_challenge)
			)

			return instance!!
		}
	}


	// Getter methods
	fun getAvailableThemes() 			: List<String> 		{ return availableThemes!! }
	fun getAvailableKeyboardLayouts() 	: List<String> 		{ return availableKeyboardLayouts!! }
	fun getAvailableSymbolsSets() 		: List<String> 		{ return availableSymbolsSets!! }
	fun getAvailableGameModes() 		: List<String> 		{ return availableGameModes!! }
}