package com.tecnoscimmia.nine.controller

import androidx.navigation.NavHostController
import com.tecnoscimmia.nine.MainActivity
import com.tecnoscimmia.nine.R
import com.tecnoscimmia.nine.model.GameSettings

/*
* This file defines the controller for the settings screen
*/

class SettingsController(val navigationCntrl: NavHostController, val settings: GameSettings, val activity: MainActivity)
{
	// List of all the available themes
	val availableThemes = listOf(
		activity.resources.getString(R.string.settings_theme_light),
		activity.resources.getString(R.string.settings_theme_dark)
	)

	// List of all the available keyboard layouts
	val availableKeyboardLayouts = listOf(
		activity.resources.getString(R.string.settings_keyboard_layout_3x3),
		activity.resources.getString(R.string.settings_keyboard_layout_in_line)
	)

	// This function converts the internal representation of the keyboard layout value to the UI one
	fun getTheme() : String
	{
		return when(settings.getTheme())
		{
			GameSettings.LIGHT_THEME -> activity.resources.getString(R.string.settings_theme_light)
			GameSettings.DARK_THEME -> activity.resources.getString(R.string.settings_theme_dark)
			else -> ""
		}
	}


	// This function converts the internal representation of the keyboard layout value to the UI one
	fun getKeyboardLayout() : String
	{
		return when(settings.getKeyboardLayout())
		{
			GameSettings.KBD_LAYOUT_3X3 -> activity.resources.getString(R.string.settings_keyboard_layout_3x3)
			GameSettings.KBD_LAYOUT_IN_LINE -> activity.resources.getString(R.string.settings_keyboard_layout_in_line)
			else -> ""
		}
	}


	// Changes the value of the theme setting and sets the new theme for the application
	private fun setTheme(newTheme: String)
	{
		val newValue = when(newTheme)							// Convert given string to the internal representation that game settings class uses
		{
			activity.getString(R.string.settings_theme_dark) -> GameSettings.DARK_THEME
			else -> GameSettings.LIGHT_THEME
		}

		settings.setTheme(newValue)								// Update setting value

		/*TODO: Add implementation (need to actually change the app theme)*/
	}


	// Changes the value of the keyboard layout setting
	private fun setKeyboardLayout(newLayout: String)
	{
		val newValue = when(newLayout)							// Convert given string to the internal representation that game settings class uses
		{
			activity.getString(R.string.settings_keyboard_layout_3x3) -> GameSettings.KBD_LAYOUT_3X3
			else -> GameSettings.KBD_LAYOUT_IN_LINE
		}

		settings.setKeyboardLayout(newValue)					// Update setting value
	}


	// Updates the game settings with the given values
	fun applyChangesToSettings(newTheme: String, newKeyboardLayout: String)
	{
		if(newTheme != getTheme() && newTheme in availableThemes)
			setTheme(newTheme)

		if(newKeyboardLayout != getKeyboardLayout() && newKeyboardLayout in availableKeyboardLayouts)
			setKeyboardLayout(newKeyboardLayout)
	}


	fun startTutorial()
	{
		// TODO: Start the tutorial (Uncomment when the tutorial screen is implemented)
		//navigationCntrl.navigate(route = NineScreen.Game.name)			// Switch to tutorial screen
	}
}