package com.tecnoscimmia.nine.model

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import com.tecnoscimmia.nine.MainActivity
import com.tecnoscimmia.nine.R

/*
 * This file defines all the classes used to model the game and it's properties
 */


// This class holds the current game settings and defines all the available values for each game setting.
// The values that a game setting can assume are represented as strings and all the available values are
// defined in an array in the strings.xml file.
class GameSettings private constructor()
{
	// Current settings values
	var language					= mutableStateOf("")
	var theme						= mutableStateOf("")
	var keyboardLayout				= mutableStateOf("")
	var gameMode 					= mutableStateOf("")


	// Returns an array of strings that specify all the languages supported by the application
	fun getAvailableLanguages() : Array <String>
	{
		return availableLanguages!!								// This array is initialized in getInstance so it should be safe to return it
	}


	// Returns an array of strings that specify all the themes supported by the application
	fun getAvailableThemes() : Array <String>
	{
		return availableThemes!!								// This array is initialized in getInstance so it should be safe to return it
	}


	// Returns an array of strings that specify all the keyboard layouts supported by the application
	fun getAvailableKeyboardLayouts() : Array <String>
	{
		return availableKeyboardLayouts!!						// This array is initialized in getInstance so it should be safe to return it
	}


	// Returns an array of strings that specify all the game modes supported by the application
	fun getAvailableGameModes() : Array <String>
	{
		return availableGameModes!!								// This array is initialized in getInstance so it should be safe to return it
	}

	companion object
	{
		fun getInstance(cntxt: MainActivity) : GameSettings
		{
			if(instance != null)								// If instance is already initialized just return it
				return instance!!

			instance = GameSettings()							// Otherwise, create the instance

			// Load all available values for each setting
			availableLanguages 			= cntxt.resources.getStringArray(R.array.setting_language_values)
			availableThemes 			= cntxt.resources.getStringArray(R.array.setting_theme_values)
			availableKeyboardLayouts 	= cntxt.resources.getStringArray(R.array.setting_keyboard_layout_values)
			availableGameModes 			= cntxt.resources.getStringArray(R.array.setting_game_mode_values)

			// Load settings value from preferences file
			loadFromFile(cntxt)

			instance!!.gameMode.value = availableGameModes!![0]	// Set starting game mode
			return instance!!
		}


		// Loads game settings from the default preferences file of the application, this function MUST
		// be called after all the available settings arrays have been initialized because if settings values are
		// not present in the preferences file then the available values are used to set default values for settings;
		private fun loadFromFile(cntxt: MainActivity)
		{
			if(instance == null)								// This function must be called only when the instance has already been initialized
				return

			val preferences = cntxt.getPreferences(Context.MODE_PRIVATE)

			// Check if settings are saved in the preferences file, if they are then we load them
			if(preferences.contains(cntxt.getString(R.string.setting_language)) == true)
			{
				instance!!.language.value 		= preferences.getString(cntxt.getString(R.string.setting_language), "")!!
				instance!!.theme.value 			= preferences.getString(cntxt.getString(R.string.setting_theme), "")!!
				instance!!.keyboardLayout.value = preferences.getString(cntxt.getString(R.string.setting_keyboard_layout), "")!!

			} else {	// Otherwise we need to store default values in the file and set current values to default too
				instance!!.language.value 		= availableLanguages!![0]
				instance!!.theme.value 			= availableThemes!![0]
				instance!!.keyboardLayout.value = availableKeyboardLayouts!![0]

				preferences.edit().putString(cntxt.getString(R.string.setting_language), instance!!.language.value).apply()
				preferences.edit().putString(cntxt.getString(R.string.setting_theme), instance!!.theme.value).apply()
				preferences.edit().putString(cntxt.getString(R.string.setting_keyboard_layout), instance!!.keyboardLayout.value).apply()
			}
		}


		private var instance: GameSettings? 	= null

		// Arrays of available values for each setting
		private var availableLanguages: 		Array<String>? = null
		private var availableThemes:			Array<String>? = null
		private var availableKeyboardLayouts:	Array<String>? = null
		private var availableGameModes:			Array<String>? = null
	}

}


// TODO: Assign correct data types to this class
// This class holds data about a match played in the past, is used by scoreboard controller and scoreboard screen
data class MatchResult(val time: String, val date: String, val gameMode: String)