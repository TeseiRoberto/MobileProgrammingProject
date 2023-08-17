package com.tecnoscimmia.nine.model

import android.content.Context
import android.content.SharedPreferences
import com.tecnoscimmia.nine.R

/*
 * This file defines the GameSettings class. This class holds the current game settings and is responsible for loading and
 * saving game settings value in the shared preferences file. (Note that not all the game settings needs to be saved on file,
 * for example the gameMode is not saved, neither is the showTutorial)
 */


class GameSettings private constructor(private val preferencesFile: SharedPreferences)
{
	// Those enums defines all the available values for each game setting
	enum class ThemeSetting 			{ LIGHT_THEME, DARK_THEME }
	enum class KeyboardLayoutSetting 	{ THREE_BY_THREE_KBD_LAYOUT, TWO_LINES_KBD_LAYOUT }
	enum class GameModeSetting			{ TRAINING_GAME_MODE, CHALLENGE_GAME_MODE }								// Don't change the names of those enums, they are used to store data in the DB in this way we are not bound to the representation of a specific locale
	enum class SymbolsSetSetting		{ NUMBERS_SYMBOLS_SET, LETTERS_SYMBOLS_SET, EMOTICONS_SYMBOLS_SET }


	// Current values of the settings
	private var theme: 					ThemeSetting = ThemeSetting.LIGHT_THEME									// The current theme of the app
	private var keyboardLayout: 		KeyboardLayoutSetting = KeyboardLayoutSetting.TWO_LINES_KBD_LAYOUT		// Layout used for the custom keyboard in the game screen
	private var gameMode:				GameModeSetting = GameModeSetting.TRAINING_GAME_MODE					// Mode in which the game will be played
	private var symbolsSet:				SymbolsSetSetting = SymbolsSetSetting.NUMBERS_SYMBOLS_SET				// Set of symbols used in the game (a symbols set is just a string that contains comma separated strings)
	private var showTutorial: 			Boolean = false															// Indicates if tutorial will be shown on app start up
	private var debugMode:				Boolean = false															// Indicates if the application is running in debug mode

	
	// Getter methods
	fun getTheme() : 					ThemeSetting 			{ return theme }
	fun getKeyboardLayout() : 			KeyboardLayoutSetting 	{ return keyboardLayout }
	fun getGameMode():					GameModeSetting			{ return gameMode }
	fun getSymbolsSet():				SymbolsSetSetting		{ return symbolsSet }
	fun needToShowTutorial():			Boolean 				{ return showTutorial }
	fun isDebugModeActive():			Boolean					{ return debugMode }


	// Changes the theme and save the new one in the preferences file
	fun setTheme(newTheme: ThemeSetting)
	{
		theme = newTheme
		preferencesFile.edit().putString(themePreferencesKey, newTheme.name).apply()
	}


	// Changes the keyboard layout and save the new one in the preferences file
	fun setKeyboardLayout(newKeyboardLayout: KeyboardLayoutSetting)
	{
		keyboardLayout = newKeyboardLayout
		preferencesFile.edit().putString(keyboardLayoutPreferencesKey, newKeyboardLayout.name).apply()
	}


	// Set the game mode type
	fun setGameMode(newGameMode: GameModeSetting)
	{
		gameMode = newGameMode
	}



	// Set which set of symbols will be used in the game and save it in the preferences file
	fun setSymbolsSet(newSymbolsSet: SymbolsSetSetting)
	{
		symbolsSet = newSymbolsSet
		preferencesFile.edit().putString(symbolsSetPreferencesKey, newSymbolsSet.name).apply()
	}


	// Changes the debug mode setting and save the new one in the preferences file
	fun setDebugMode(newMode: Boolean)
	{
		debugMode = newMode
		preferencesFile.edit().putBoolean(debugModePreferencesKey, newMode).apply()
	}



	// Loads values for the settings from the preferences file
	private fun loadFromFile()
	{
		// Check if settings values are present in the preferences file
		if(preferencesFile.contains(themePreferencesKey))
		{
			// If they are present then we load them (we load the string from file and convert it to the correct enum type)
			theme = ThemeSetting.valueOf( preferencesFile.getString(themePreferencesKey, ThemeSetting.LIGHT_THEME.name) as String )

			keyboardLayout = KeyboardLayoutSetting.valueOf(
				preferencesFile.getString(keyboardLayoutPreferencesKey, KeyboardLayoutSetting.TWO_LINES_KBD_LAYOUT.name) as String )

			symbolsSet = SymbolsSetSetting.valueOf(
				preferencesFile.getString(symbolsSetPreferencesKey, SymbolsSetSetting.NUMBERS_SYMBOLS_SET.name) as String )

			debugMode = preferencesFile.getBoolean(debugModePreferencesKey, false)


		} else {
			// Otherwise we set default values and we save those in the preferences file
			theme 			= ThemeSetting.LIGHT_THEME
			keyboardLayout 	= KeyboardLayoutSetting.TWO_LINES_KBD_LAYOUT
			symbolsSet		= SymbolsSetSetting.NUMBERS_SYMBOLS_SET
			debugMode		= false

			// If settings were not saved then this may be the first time the app is started, so we need to show the tutorial
			showTutorial 	= true

			val edit = preferencesFile.edit()
			edit.putString(themePreferencesKey, theme.name)
			edit.putString(keyboardLayoutPreferencesKey, keyboardLayout.name)
			edit.putString(symbolsSetPreferencesKey, symbolsSet.name)
			edit.putBoolean(debugModePreferencesKey, debugMode)
			edit.apply()
		}
	}

	companion object
	{
		private var instance: 	GameSettings? = null				// The singleton instance

		const val MAX_DIGITS_NUM = 9								// Number of digits used to create the secret key

		// Keys used to save data in the preferences file (those are loaded when get instance is called for the first time)
		private var themePreferencesKey 			= ""
		private var keyboardLayoutPreferencesKey	= ""
		private var symbolsSetPreferencesKey		= ""
		private var debugModePreferencesKey			= ""


		fun getInstance(appCntxt: Context) : GameSettings
		{
			if(instance != null)
				return instance!!

			// Load the preferences file
			val preferencesFile = appCntxt.getSharedPreferences(appCntxt.getString(R.string.preferences_file_name), Context.MODE_PRIVATE)

			// Load keys used in the preferences file to store settings values
			themePreferencesKey 			= appCntxt.resources.getString(R.string.settings_theme_key)
			keyboardLayoutPreferencesKey 	= appCntxt.resources.getString(R.string.settings_keyboard_layout_key)
			symbolsSetPreferencesKey		= appCntxt.resources.getString(R.string.settings_symbols_set_key)
			debugModePreferencesKey 		= appCntxt.getString(R.string.settings_debug_mode_key)

			instance = GameSettings(preferencesFile)				// Instantiate singleton
			instance!!.loadFromFile()								// Load settings from preferences file

			return instance!!
		}
	}
}


