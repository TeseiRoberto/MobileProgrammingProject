package com.tecnoscimmia.nine.controller

import android.content.Context
import androidx.navigation.NavHostController
import com.tecnoscimmia.nine.MainActivity
import com.tecnoscimmia.nine.R
import com.tecnoscimmia.nine.model.GameSettings

/*
* This file defines the controller for the settings screen
*/

class SettingsController(val navigationCntrl: NavHostController, val settings: GameSettings, val appCntxt: Context)
{
	// List of all the available themes
	val availableThemes = listOf(
		appCntxt.resources.getString(R.string.settings_theme_light),
		appCntxt.resources.getString(R.string.settings_theme_dark)
	)

	// List of all the available keyboard layouts
	val availableKeyboardLayouts = listOf(
		appCntxt.resources.getString(R.string.settings_keyboard_layout_two_lines),
		appCntxt.resources.getString(R.string.settings_keyboard_layout_3x3)
	)

	val availableSymbolSet = listOf(
		appCntxt.getString(R.string.settings_symbols_set_numbers),
		appCntxt.getString(R.string.settings_symbols_set_letters),
		appCntxt.getString(R.string.settings_symbols_set_emoticons)
		)


	// This function converts the internal representation of the keyboard layout value to the UI one
	fun getTheme() : String
	{
		return when(settings.getTheme())
		{
			GameSettings.ThemeSetting.LIGHT_THEME -> appCntxt.resources.getString(R.string.settings_theme_light)
			GameSettings.ThemeSetting.DARK_THEME -> appCntxt.resources.getString(R.string.settings_theme_dark)
			else -> ""
		}
	}


	// This function converts the internal representation of the keyboard layout value to the UI one
	fun getKeyboardLayout() : String
	{
		return when(settings.getKeyboardLayout())
		{
			GameSettings.KeyboardLayoutSetting.TWO_LINES_KBD_LAYOUT -> appCntxt.resources.getString(R.string.settings_keyboard_layout_two_lines)
			GameSettings.KeyboardLayoutSetting.THREE_BY_THREE_KBD_LAYOUT -> appCntxt.resources.getString(R.string.settings_keyboard_layout_3x3)
			else -> ""
		}
	}


	// This function converts the internal representation of the symbol set value to the UI one
	fun getSymbolsSet() : String
	{
		return when(settings.getSymbolsSet())
		{
			GameSettings.SymbolsSetSetting.NUMBERS_SYMBOLS_SET -> appCntxt.resources.getString(R.string.settings_symbols_set_numbers)
			GameSettings.SymbolsSetSetting.LETTERS_SYMBOLS_SET -> appCntxt.resources.getString(R.string.settings_symbols_set_letters)
			GameSettings.SymbolsSetSetting.EMOTICONS_SYMBOLS_SET -> appCntxt.resources.getString(R.string.settings_symbols_set_emoticons)
			else -> ""
		}
	}


	// Changes the value of the theme setting and sets the new theme for the application
	private fun setTheme(newTheme: String)
	{
		val newValue = when(newTheme)							// Convert given string to the internal representation that game settings class uses
		{
			appCntxt.getString(R.string.settings_theme_dark) -> GameSettings.ThemeSetting.DARK_THEME
			appCntxt.getString(R.string.settings_theme_light) -> GameSettings.ThemeSetting.LIGHT_THEME
			else -> return
		}

		settings.setTheme(newValue)								// Update setting value

		/*TODO: Add implementation (need to actually change the app theme)*/
	}


	// Changes the value of the keyboard layout setting
	private fun setKeyboardLayout(newLayout: String)
	{
		val newValue = when(newLayout)							// Convert given string to the internal representation that game settings class uses
		{
			appCntxt.getString(R.string.settings_keyboard_layout_two_lines) -> GameSettings.KeyboardLayoutSetting.TWO_LINES_KBD_LAYOUT
			appCntxt.getString(R.string.settings_keyboard_layout_3x3) -> GameSettings.KeyboardLayoutSetting.THREE_BY_THREE_KBD_LAYOUT
			else -> return
		}

		settings.setKeyboardLayout(newValue)					// Update setting value
	}


	// Changes the value of the symbols set setting
	private fun setSymbolsSet(newSymbolsSet: String)
	{
		val newValue = when(newSymbolsSet)						// Convert given string to the internal representation that game settings class uses
		{
			appCntxt.getString(R.string.settings_symbols_set_numbers) -> GameSettings.SymbolsSetSetting.NUMBERS_SYMBOLS_SET
			appCntxt.getString(R.string.settings_symbols_set_letters) -> GameSettings.SymbolsSetSetting.LETTERS_SYMBOLS_SET
			appCntxt.getString(R.string.settings_symbols_set_emoticons) -> GameSettings.SymbolsSetSetting.EMOTICONS_SYMBOLS_SET
			else -> return
		}

		settings.setSymbolsSet(newValue)						// Update setting value
	}


	// Updates the game settings with the given values
	fun applyChangesToSettings(newTheme: String, newKeyboardLayout: String, newSymbolSet: String)
	{
		if(newTheme != getTheme() && newTheme in availableThemes)
			setTheme(newTheme)

		if(newKeyboardLayout != getKeyboardLayout() && newKeyboardLayout in availableKeyboardLayouts)
			setKeyboardLayout(newKeyboardLayout)

		if(newSymbolSet != getSymbolsSet() && newSymbolSet in availableSymbolSet)
			setSymbolsSet(newSymbolSet)
	}


	fun startTutorial()
	{
		// TODO: Start the tutorial (Uncomment when the tutorial screen is implemented)
		//navigationCntrl.navigate(route = NineScreen.Game.name)			// Switch to tutorial screen
	}
}