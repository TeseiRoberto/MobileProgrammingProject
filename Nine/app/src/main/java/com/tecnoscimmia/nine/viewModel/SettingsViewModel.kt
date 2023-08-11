package com.tecnoscimmia.nine.viewModel

import android.content.res.Resources
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.tecnoscimmia.nine.MainActivity
import com.tecnoscimmia.nine.R
import com.tecnoscimmia.nine.model.GameSettings
import com.tecnoscimmia.nine.model.SettingsRepository

/*
* This file defines the view-model for the settings screen
*/


class SettingsViewModel(private val appResources: Resources, private val settings: GameSettings, private val settingsRepo: SettingsRepository) : ViewModel()
{
	private var currTheme 			= mutableStateOf(settings.getTheme())
	private var currKeyboardLayout	= mutableStateOf(settings.getKeyboardLayout())
	private var currSymbolsSet		= mutableStateOf(settings.getSymbolsSet())
	private var currDebugMode		= mutableStateOf(settings.isDebugModeActive())
	private var hasSomethingChanged = mutableStateOf(false)


	// Getter methods (needed because the view should not access the repository directly)
	fun getAvailableThemes() : 			List<String> { return settingsRepo.getAvailableThemes() }
	fun getAvailableKeyboardLayouts() : List<String> { return settingsRepo.getAvailableKeyboardLayouts() }
	fun getAvailableSymbolsSets() : 	List<String> { return settingsRepo.getAvailableSymbolsSets() }
	fun getAvailableDebugModes() : 		List<String> { return settingsRepo.getAvailableDebugModes() }

	fun hasSomethingChanged() : 		Boolean { return hasSomethingChanged.value }


	// Converts the internal representation of the theme value to the UI one
	fun getTheme() : String
	{
		return when(currTheme.value)
		{
			GameSettings.ThemeSetting.LIGHT_THEME -> appResources.getString(R.string.settings_theme_light)
			GameSettings.ThemeSetting.DARK_THEME ->  appResources.getString(R.string.settings_theme_dark)
		}
	}


	// Converts the internal representation of the keyboard layout value to the UI one
	fun getKeyboardLayout() : String
	{
		return when(currKeyboardLayout.value)
		{
			GameSettings.KeyboardLayoutSetting.TWO_LINES_KBD_LAYOUT -> appResources.getString(R.string.settings_keyboard_layout_two_lines)
			GameSettings.KeyboardLayoutSetting.THREE_BY_THREE_KBD_LAYOUT -> appResources.getString(R.string.settings_keyboard_layout_3x3)
		}
	}


	// Converts the internal representation of the symbol set value to the UI one
	fun getSymbolsSet() : String
	{
		return when(currSymbolsSet.value)
		{
			GameSettings.SymbolsSetSetting.NUMBERS_SYMBOLS_SET -> appResources.getString(R.string.settings_symbols_set_numbers)
			GameSettings.SymbolsSetSetting.LETTERS_SYMBOLS_SET -> appResources.getString(R.string.settings_symbols_set_letters)
			GameSettings.SymbolsSetSetting.EMOTICONS_SYMBOLS_SET -> appResources.getString(R.string.settings_symbols_set_emoticons)
		}
	}


	// Converts the internal representation of the debug mode value to the UI one
	fun getDebugMode() : String
	{
		return when(currDebugMode.value)
		{
			false -> appResources.getString(R.string.settings_debug_mode_inactive)
			true -> appResources.getString(R.string.settings_debug_mode_active)
		}
	}


	// Converts the UI representation of the theme to the internal one and updates the current selected theme
	fun setTheme(newTheme: String)
	{
		val newInternalTheme = when(newTheme)
		{
			appResources.getString(R.string.settings_theme_light) -> GameSettings.ThemeSetting.LIGHT_THEME
			appResources.getString(R.string.settings_theme_dark) -> GameSettings.ThemeSetting.DARK_THEME
			else -> return
		}

		currTheme.value = newInternalTheme
		hasSomethingChanged.value = true
	}


	// Converts the UI representation of the keyboard layout to the internal one and updates the current selected layout
	fun setKeyboardLayout(newLayout: String)
	{
		val newInternalLayout = when(newLayout)
		{
			appResources.getString(R.string.settings_keyboard_layout_3x3) -> GameSettings.KeyboardLayoutSetting.THREE_BY_THREE_KBD_LAYOUT
			appResources.getString(R.string.settings_keyboard_layout_two_lines) -> GameSettings.KeyboardLayoutSetting.TWO_LINES_KBD_LAYOUT
			else -> return
		}

		currKeyboardLayout.value = newInternalLayout
		hasSomethingChanged.value = true
	}


	// Converts the UI representation of the symbols set to the internal one and updates the game settings class with it
	fun setSymbolsSet(newSymbolsSet: String)
	{
		val newInternalSymbolsSet = when(newSymbolsSet)
		{
			appResources.getString(R.string.settings_symbols_set_numbers) -> GameSettings.SymbolsSetSetting.NUMBERS_SYMBOLS_SET
			appResources.getString(R.string.settings_symbols_set_letters) -> GameSettings.SymbolsSetSetting.LETTERS_SYMBOLS_SET
			appResources.getString(R.string.settings_symbols_set_emoticons) -> GameSettings.SymbolsSetSetting.EMOTICONS_SYMBOLS_SET
			else -> return
		}

		currSymbolsSet.value = newInternalSymbolsSet
		hasSomethingChanged.value = true
	}


	// Converts the UI representation of the debug mode value to the internal one
	fun setDebugMode(newDebugMode: String)
	{
		val newInternalDebugMode = when(newDebugMode)
		{
			appResources.getString(R.string.settings_debug_mode_inactive) -> false
			appResources.getString(R.string.settings_debug_mode_active) -> true
			else -> return
		}

		currDebugMode.value = newInternalDebugMode
		hasSomethingChanged.value = true
	}


	// Apply changes to the game settings class
	fun saveChangesToSettings()
	{
		if(currTheme.value != settings.getTheme())
		{
			settings.setTheme(newTheme = currTheme.value)
			MainActivity.appTheme.value = currTheme.value			// Change the theme of the application
		}

		if(currKeyboardLayout.value != settings.getKeyboardLayout())
			settings.setKeyboardLayout(newKeyboardLayout = currKeyboardLayout.value)

		if(currSymbolsSet.value != settings.getSymbolsSet())
			settings.setSymbolsSet(newSymbolsSet = currSymbolsSet.value)

		if(currDebugMode.value != settings.isDebugModeActive())
			settings.setDebugMode(currDebugMode.value)

		hasSomethingChanged.value = false
	}


	// SettingsViewModel factory
	companion object
	{
		val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory
		{
			@Suppress("UNCHECKED_CAST")
			override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T
			{
				val application = checkNotNull(extras[APPLICATION_KEY])			// Get the application object form extras
				val savedStateHandle = extras.createSavedStateHandle()			// Create a SavedStateHandle for this ViewModel from extras

				return SettingsViewModel(appResources = application.resources,
					settings = GameSettings.getInstance(application.applicationContext),
					settingsRepo = SettingsRepository.getInstance(application.applicationContext)) as T
			}
		}
	}
}