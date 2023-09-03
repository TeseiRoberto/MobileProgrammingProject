package com.tecnoscimmia.nine.model

import android.content.Context
import com.tecnoscimmia.nine.R

/*
 * This file defines the SettingRepository class, this class is a singleton and is responsible of
 * loading all the available themes, keyboard layouts, game modes from the strings.xml file,
 * furthermore is responsible of the conversion of the values from internal to UI representation
 * and viceversa
*/

class SettingsRepository private constructor()
{
	// Getter methods
	fun getAvailableThemes() 			: List<String> 		{ return availableThemes!!.keys.toList() }
	fun getAvailableKeyboardLayouts() 	: List<String> 		{ return availableKeyboardLayouts!!.keys.toList() }
	fun getAvailableSymbolsSets() 		: List<String> 		{ return availableSymbolsSets!!.keys.toList() }
	fun getAvailableGameModes() 		: List<String> 		{ return availableGameModes!!.keys.toList() }
	fun getAvailableDebugModes()		: List<String>		{ return availableDebugModes!!.keys.toList() }


	// Converts the given theme value from the internal representation to the UI one
	fun themeInternalToUI(internalRep: GameSettings.ThemeSetting) : String
	{
		val result = availableThemes!!.entries.find { it.value == internalRep }
			?: throw Exception("The given internal representation of Theme value has no matching UI representation!")

		return result.key
	}


	// Converts the given theme value from the UI representation to the internal one
	fun themeUItoInternal(uiRep: String) : GameSettings.ThemeSetting
	{
		val result = availableThemes!![uiRep]
			?: throw Exception("The given UI representation of Theme value has no matching internal representation!")

		return result
	}


	// Converts the given keyboardLayout value from the internal representation to the UI one
	fun keyboardLayoutInternalToUI(internalRep: GameSettings.KeyboardLayoutSetting) : String
	{
		val result = availableKeyboardLayouts!!.entries.find { it.value == internalRep }
			?: throw Exception("The given internal representation of KeyboardLayout value has no matching UI representation!")

		return result.key
	}


	// Converts the given keyboardLayout value from the UI representation to the internal one
	fun keyboardLayoutUItoInternal(uiRep: String) : GameSettings.KeyboardLayoutSetting
	{
		val result = availableKeyboardLayouts!![uiRep]
			?: throw Exception("The given UI representation of keyboard layout value has no matching internal representation!")

		return result
	}


	// Converts the given game mode value from the internal representation to the UI one
	fun gameModeInternalToUI(internalRep: GameSettings.GameModeSetting) : String
	{
		val result = availableGameModes!!.entries.find { it.value == internalRep }
			?: throw Exception("The given internal representation of GameMode value has no matching UI representation!")

		return result.key
	}


	// Converts the given game mode value from the UI representation to the internal one
	fun gameModeUItoInternal(uiRep: String) : GameSettings.GameModeSetting
	{
		val result = availableGameModes!![uiRep]
			?: throw Exception("The given UI representation of GameMode value has no matching internal representation!")

		return result
	}


	// Converts the given symbols set value from the internal representation to the UI one
	fun symbolsSetInternalToUI(internalRep: GameSettings.SymbolsSetSetting) : String
	{
		val result = availableSymbolsSets!!.entries.find { it.value == internalRep }
			?: throw Exception("The given internal representation of SymbolsSet value has no matching UI representation!")

		return result.key
	}


	// Converts the given symbols set value from the UI representation to the internal one
	fun symbolsSetUItoInternal(uiRep: String) : GameSettings.SymbolsSetSetting
	{
		val result = availableSymbolsSets!![uiRep]
			?: throw Exception("The given UI representation of SymbolsSet value has no matching internal representation!")

		return result
	}


	// Converts the given debug mode value from the internal representation to the UI one
	fun debugModeInternalToUI(internalRep: GameSettings.DebugModeSetting) : String
	{
		val result = availableDebugModes!!.entries.find { it.value == internalRep }
			?: throw Exception("The given internal representation of DebugMode value has no matching UI representation!")

		return result.key
	}


	// Converts the given debug mode value from the UI representation to the internal one
	fun debugModeUItoInternal(uiRep: String) : GameSettings.DebugModeSetting
	{
		val result = availableDebugModes!![uiRep]
			?: throw Exception("The given UI representation of DebugMode value has no matching internal representation!")

		return result
	}


	companion object
	{
		private var instance : SettingsRepository? = null

		// The following maps contains the UI representation of the available value for a setting as keys and the internal representation of that
		// value as values, in this way UI and internal representation are completely separated
		private var availableThemes: 			Map<String, GameSettings.ThemeSetting>? 		 = null 	// Contains all the available themes for the app
		private var availableKeyboardLayouts: 	Map<String, GameSettings.KeyboardLayoutSetting>? = null		// Contains all the available keyboard layouts for the game keyboard
		private var availableSymbolsSets: 		Map<String, GameSettings.SymbolsSetSetting>? 	 = null		// Contains all the available symbols sets
		private var availableGameModes: 		Map<String, GameSettings.GameModeSetting>? 		 = null		// Contains all the game modes in which the game can be played in
		private var availableDebugModes:		Map<String, GameSettings.DebugModeSetting>? 	 = null		// Contains all the possible values for the debug mode setting


		fun getInstance(appCntxt: Context) : SettingsRepository
		{
			if(instance != null)
				return instance!!

			instance = SettingsRepository()

			// Load available themes from strings.xml and map them to the internal representation
			availableThemes = mapOf(
				appCntxt.getString(R.string.settings_theme_light) to GameSettings.ThemeSetting.LIGHT_THEME,
				appCntxt.resources.getString(R.string.settings_theme_dark) to GameSettings.ThemeSetting.DARK_THEME
			)

			// Load available keyboard layouts from strings.xml and map them to the internal representation
			availableKeyboardLayouts = mapOf(
				appCntxt.resources.getString(R.string.settings_keyboard_layout_two_lines) to GameSettings.KeyboardLayoutSetting.TWO_LINES_KBD_LAYOUT,
				appCntxt.resources.getString(R.string.settings_keyboard_layout_3x3) to GameSettings.KeyboardLayoutSetting.THREE_BY_THREE_KBD_LAYOUT
			)

			// Load available symbols sets from strings.xml and map them to the internal representation
			availableSymbolsSets = mapOf(
				appCntxt.getString(R.string.settings_symbols_set_numbers) to GameSettings.SymbolsSetSetting.NUMBERS_SYMBOLS_SET,
				appCntxt.getString(R.string.settings_symbols_set_letters) to GameSettings.SymbolsSetSetting.LETTERS_SYMBOLS_SET,
				appCntxt.getString(R.string.settings_symbols_set_emoticons) to GameSettings.SymbolsSetSetting.EMOTICONS_SYMBOLS_SET,
				appCntxt.getString(R.string.settings_symbols_set_punctuation) to GameSettings.SymbolsSetSetting.PUNCTUATION_SYMBOLS_SET,
				appCntxt.getString(R.string.settings_symbols_set_mixed) to GameSettings.SymbolsSetSetting.MIXED_SYMBOL_SET
			)

			// Load available game modes from strings.xml and map them to the internal representation
			availableGameModes = mapOf(
				appCntxt.resources.getString(R.string.settings_game_mode_training) to GameSettings.GameModeSetting.TRAINING_GAME_MODE,
				appCntxt.resources.getString(R.string.settings_game_mode_challenge) to GameSettings.GameModeSetting.CHALLENGE_GAME_MODE
			)

			// Load available values for the debug mode and map them to the internal representation
			availableDebugModes = mapOf(
				appCntxt.getString(R.string.settings_debug_mode_active) to GameSettings.DebugModeSetting.DEBUG_MODE,
				appCntxt.getString(R.string.settings_debug_mode_inactive) to GameSettings.DebugModeSetting.NO_DEBUG_MODE,
			)

			return instance!!
		}
	}
}
