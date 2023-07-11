package com.tecnoscimmia.nine.model

import android.content.Context
import com.tecnoscimmia.nine.MainActivity
import com.tecnoscimmia.nine.R

/*
 * This file defines all the classes used to model the game and it's properties
 */


// This class holds the current game settings, is responsible for loading and saving game settings in the preferences file.
//(Note that not all the game settings needs to be saved on file, for example the gameMode is not saved and neither is the showTutorial)
class GameSettings private constructor(val activity: MainActivity)
{
	// Current values of the settings
	private var theme: 					ThemeSetting = ThemeSetting.LIGHT_THEME									// The current theme of the app
	private var keyboardLayout: 		KeyboardLayoutSetting = KeyboardLayoutSetting.TWO_LINES_KBD_LAYOUT		// Layout used for the custom keyboard in the game screen
	private var gameMode:				GameModeSetting = GameModeSetting.TRAINING_GAME_MODE					// Mode in which the game will be played
	private var symbolSet:				SymbolsSetSetting = SymbolsSetSetting.NUMBERS_SYMBOLS_SET				// Set of symbols used in the game
	private var showTutorial: 			Boolean = false															// Indicates if tutorial will be shown on app start up


	// Those enums defines all the available values for each game setting
	enum class ThemeSetting 			{ LIGHT_THEME, DARK_THEME }
	enum class KeyboardLayoutSetting 	{ THREE_BY_THREE_KBD_LAYOUT, TWO_LINES_KBD_LAYOUT }
	enum class GameModeSetting			{ TRAINING_GAME_MODE, CHALLENGE_GAME_MODE }
	enum class SymbolsSetSetting		{ NUMBERS_SYMBOLS_SET, LETTERS_SYMBOLS_SET, EMOTICONS_SYMBOLS_SET }


	// Getter methods
	fun getTheme() : 					ThemeSetting 			{ return theme }
	fun getKeyboardLayout() : 			KeyboardLayoutSetting 	{ return keyboardLayout }
	fun getGameMode():					GameModeSetting			{ return gameMode }
	fun getSymbolsSet():				SymbolsSetSetting		{ return symbolSet }
	fun needToShowTutorial():			Boolean 				{ return showTutorial }


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


	// Set the game mode type
	fun setGameMode(newGameMode: GameModeSetting)
	{
		gameMode = newGameMode
	}


	// Set which set of symbols will be used in the game and save it to the preferences file
	fun setSymbolsSet(newSymbolSet: SymbolsSetSetting)
	{
		symbolSet = newSymbolSet
		val res = activity.resources
		val preferencesFile = activity.getPreferences(Context.MODE_PRIVATE)

		preferencesFile.edit().putString(res.getString(R.string.settings_symbols_set_key), newSymbolSet.name).apply()
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
			theme = ThemeSetting.valueOf(
				preferencesFile.getString(res.getString(R.string.settings_theme_key), ThemeSetting.LIGHT_THEME.name) as String )

			keyboardLayout = KeyboardLayoutSetting.valueOf(
				preferencesFile.getString(res.getString(R.string.settings_keyboard_layout_key), KeyboardLayoutSetting.TWO_LINES_KBD_LAYOUT.name) as String )

			symbolSet = SymbolsSetSetting.valueOf(
				preferencesFile.getString(res.getString(R.string.settings_symbols_set_key), SymbolsSetSetting.NUMBERS_SYMBOLS_SET.name) as String )

		} else {
			// Otherwise we set default values and we save those in the preferences file
			theme 			= ThemeSetting.LIGHT_THEME
			keyboardLayout 	= KeyboardLayoutSetting.TWO_LINES_KBD_LAYOUT
			symbolSet		= SymbolsSetSetting.NUMBERS_SYMBOLS_SET

			// If settings were not saved then this may be the first time the app is started, so we need to show the tutorial
			showTutorial 	= true

			val edit = preferencesFile.edit()
			edit.putString(res.getString(R.string.settings_theme_key), theme.name)
			edit.putString(res.getString(R.string.settings_keyboard_layout_key), keyboardLayout.name)
			edit.putString(res.getString(R.string.settings_symbols_set_key), symbolSet.name)
			edit.apply()
		}
	}

	companion object
	{
		private var instance: 	GameSettings? = null				// The singleton instance

		fun getInstance(activity: MainActivity) : GameSettings
		{
			if(instance != null)
				return instance!!

			instance = GameSettings(activity)

			// Load user preferences from the preferences file
			instance!!.loadFromPreferencesFile()

			return instance!!
		}
	}
}


