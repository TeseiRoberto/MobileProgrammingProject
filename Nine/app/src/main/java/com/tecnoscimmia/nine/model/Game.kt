package com.tecnoscimmia.nine.model

import android.content.Context
import com.tecnoscimmia.nine.MainActivity
import com.tecnoscimmia.nine.R

/*
 * This file defines all the classes used to model the game and it's properties
 */


// This class holds the current game settings, is responsible for loading and saving game settings in the preferences file
class GameSettings private constructor(val activity: MainActivity)
{
	// Current values of the settings
	private var theme: 					String = ""
	private var keyboardLayout: 		String = ""
	private var showTutorial: 			Boolean = false

	// Getter methods
	fun getTheme() : 					String { return theme }
	fun getKeyboardLayout() : 			String { return keyboardLayout }
	fun needToShowTutorial():			Boolean { return showTutorial }

	// TODO: Maybe I need to move this somewhere else...
	fun getAvailableGameModes() : 		Array<String>	{ return availableGameModes!! }


	// Changes the theme and save the new one in the preferences file
	fun setTheme(newTheme: String)
	{
		theme = newTheme
		val res = activity.resources
		val preferencesFile = activity.getPreferences(Context.MODE_PRIVATE)

		preferencesFile.edit().putString(res.getString(R.string.settings_theme_key), newTheme).apply()
	}


	// Changes the keyboard layout and save the new one in the preferences file
	fun setKeyboardLayout(newKeyboardLayout: String)
	{
		keyboardLayout = newKeyboardLayout
		val res = activity.resources
		val preferencesFile = activity.getPreferences(Context.MODE_PRIVATE)

		preferencesFile.edit().putString(res.getString(R.string.settings_keyboard_layout_key), newKeyboardLayout).apply()
	}


	// Loads values for the settings from the preferences file
	private fun loadFromPreferencesFile()
	{
		val res = activity.resources
		val preferencesFile = activity.getPreferences(Context.MODE_PRIVATE)

		// Check if settings values are present in the preferences file
		if(preferencesFile.contains(res.getString(R.string.settings_theme_key)))
		{
			// If they are present then we load them
			theme 			= preferencesFile.getString(res.getString(R.string.settings_theme_key), LIGHT_THEME) as String
			keyboardLayout 	= preferencesFile.getString(res.getString(R.string.settings_keyboard_layout_key), KBD_LAYOUT_3X3) as String
			showTutorial 	= preferencesFile.getBoolean(res.getString(R.string.settings_show_tutorial_key), false)

		} else {
			// Otherwise we set default values and we save those in the preferences file
			theme 			= LIGHT_THEME
			keyboardLayout 	= KBD_LAYOUT_3X3
			showTutorial 	= true

			val edit = preferencesFile.edit()
			edit.putString(res.getString(R.string.settings_theme_key), theme)
			edit.putString(res.getString(R.string.settings_keyboard_layout_key), keyboardLayout)

			// Here we save false because we will display the tutorial "right now" (during this session of the application) and the next time it will not be needed
			edit.putBoolean(res.getString(R.string.settings_show_tutorial_key), false)
			edit.apply()
		}
	}

	companion object
	{
		private var instance: 	GameSettings? = null				// The singleton instance

		// Values for the theme setting (internal representation)
		val LIGHT_THEME: 		String = "LIGHT_THEME"
		val DARK_THEME: 		String = "DARK_THEME"

		// Values for the keyboard layout setting (internal representation)
		val KBD_LAYOUT_3X3:		String = "3X3"
		val KBD_LAYOUT_IN_LINE: String = "IN_LINE"

		// TODO: Maybe I need to move this somewhere else...
		private var availableGameModes: 		Array<String>? = null


		fun getInstance(activity: MainActivity) : GameSettings
		{
			if(instance != null)
				return instance!!

			instance = GameSettings(activity)

			// Load the available game modes from the resource file TODO: Maybe I need to move this somewhere else...
			availableGameModes = activity.resources.getStringArray(R.array.settings_game_mode_values)

			// Load user preferences from the preferences file
			instance!!.loadFromPreferencesFile()

			return instance!!
		}
	}
}


