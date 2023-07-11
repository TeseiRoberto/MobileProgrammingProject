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
		activity.resources.getString(R.string.settings_keyboard_layout_two_lines),
		activity.resources.getString(R.string.settings_keyboard_layout_3x3)
	)


	// This function converts the internal representation of the keyboard layout value to the UI one
	fun getTheme() : String
	{
		return when(settings.getTheme())
		{
			GameSettings.ThemeSetting.LIGHT_THEME -> activity.resources.getString(R.string.settings_theme_light)
			GameSettings.ThemeSetting.DARK_THEME -> activity.resources.getString(R.string.settings_theme_dark)
			else -> ""
		}
	}


	// This function converts the internal representation of the keyboard layout value to the UI one
	fun getKeyboardLayout() : String
	{
		return when(settings.getKeyboardLayout())
		{
			GameSettings.KeyboardLayoutSetting.TWO_LINES_KBD_LAYOUT -> activity.resources.getString(R.string.settings_keyboard_layout_two_lines)
			GameSettings.KeyboardLayoutSetting.THREE_BY_THREE_KBD_LAYOUT -> activity.resources.getString(R.string.settings_keyboard_layout_3x3)
			else -> ""
		}
	}


	// Changes the value of the theme setting and sets the new theme for the application
	private fun setTheme(newTheme: String)
	{
		val newValue = when(newTheme)							// Convert given string to the internal representation that game settings class uses
		{
			activity.getString(R.string.settings_theme_dark) -> GameSettings.ThemeSetting.DARK_THEME
			else -> GameSettings.ThemeSetting.LIGHT_THEME
		}

		settings.setTheme(newValue)								// Update setting value

		/*TODO: Add implementation (need to actually change the app theme)*/
	}


	// Changes the value of the keyboard layout setting
	private fun setKeyboardLayout(newLayout: String)
	{
		val newValue = when(newLayout)							// Convert given string to the internal representation that game settings class uses
		{
			activity.getString(R.string.settings_keyboard_layout_3x3) -> GameSettings.KeyboardLayoutSetting.THREE_BY_THREE_KBD_LAYOUT
			else -> GameSettings.KeyboardLayoutSetting.TWO_LINES_KBD_LAYOUT
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