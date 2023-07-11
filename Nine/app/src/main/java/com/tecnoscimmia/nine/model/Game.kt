package com.tecnoscimmia.nine.model

import android.content.Context
import com.tecnoscimmia.nine.MainActivity
import com.tecnoscimmia.nine.R

/*
 * This file defines all the classes used to model the game and it's properties
 */


// This class holds the current game settings, is responsible for loading and saving game settings in the preferences file.
//(note that not all the game settings needs to be saved on file, the gameMode for example is not saved)
class GameSettings private constructor(val activity: MainActivity)
{
	// Current values of the settings
	private var theme: 					ThemeSetting = ThemeSetting.LIGHT_THEME
	private var keyboardLayout: 		KeyboardLayoutSetting = KeyboardLayoutSetting.TWO_LINES_KBD_LAYOUT
	private var gameMode:				GameModeSetting = GameModeSetting.TRAINING
	private var showTutorial: 			Boolean = false


	// Those enums defines all the available values for each game setting
	enum class ThemeSetting 			{ LIGHT_THEME, DARK_THEME }
	enum class KeyboardLayoutSetting 	{ THREE_BY_THREE_KBD_LAYOUT, TWO_LINES_KBD_LAYOUT }
	enum class GameModeSetting			{ TUTORIAL, TRAINING, CHALLENGE }


	// Getter methods
	fun getTheme() : 					ThemeSetting 			{ return theme }
	fun getKeyboardLayout() : 			KeyboardLayoutSetting 	{ return keyboardLayout }
	fun needToShowTutorial():			Boolean 				{ return showTutorial }

	// TODO: Maybe I need to move this somewhere else...
	fun getAvailableGameModes() : 		Array<String>			{ return availableGameModes!! }


	// Changes the theme and save the new one in the preferences file
	fun setTheme(newTheme: ThemeSetting)
	{
		theme = newTheme
		val res = activity.resources
		val preferencesFile = activity.getPreferences(Context.MODE_PRIVATE)

		preferencesFile.edit().putString(res.getString(R.string.settings_theme_key), newTheme.name).apply()
	}


	// Changes the keyboard layout and save the new one in the preferences file
	fun setKeyboardLayout(newKeyboardLayout: KeyboardLayoutSetting)
	{
		keyboardLayout = newKeyboardLayout
		val res = activity.resources
		val preferencesFile = activity.getPreferences(Context.MODE_PRIVATE)

		preferencesFile.edit().putString(res.getString(R.string.settings_keyboard_layout_key), newKeyboardLayout.name).apply()
	}


	// Loads values for the settings from the preferences file
	private fun loadFromPreferencesFile()
	{
		val res = activity.resources
		val preferencesFile = activity.getPreferences(Context.MODE_PRIVATE)

		// Check if settings values are present in the preferences file
		if(preferencesFile.contains(res.getString(R.string.settings_theme_key)))
		{
			// If they are present then we load them (we load the string from file and convert it to the correct enum type)
			theme = GameSettings.ThemeSetting.valueOf(
				preferencesFile.getString(res.getString(R.string.settings_theme_key), GameSettings.ThemeSetting.LIGHT_THEME.name) as String)

			keyboardLayout = GameSettings.KeyboardLayoutSetting.valueOf(
				preferencesFile.getString(res.getString(R.string.settings_keyboard_layout_key), GameSettings.KeyboardLayoutSetting.TWO_LINES_KBD_LAYOUT.name) as String)

			showTutorial 	= preferencesFile.getBoolean(res.getString(R.string.settings_show_tutorial_key), false)

		} else {
			// Otherwise we set default values and we save those in the preferences file
			theme 			= GameSettings.ThemeSetting.LIGHT_THEME
			keyboardLayout 	= GameSettings.KeyboardLayoutSetting.TWO_LINES_KBD_LAYOUT
			showTutorial 	= true

			val edit = preferencesFile.edit()
			edit.putString(res.getString(R.string.settings_theme_key), theme.name)
			edit.putString(res.getString(R.string.settings_keyboard_layout_key), keyboardLayout.name)

			// Here we save false because we will display the tutorial "right now" (during this session of the application) and the next time it will not be needed
			edit.putBoolean(res.getString(R.string.settings_show_tutorial_key), false)
			edit.apply()
		}
	}

	companion object
	{
		private var instance: 	GameSettings? = null				// The singleton instance

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


