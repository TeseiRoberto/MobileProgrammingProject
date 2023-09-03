package com.tecnoscimmia.nine.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.tecnoscimmia.nine.MainActivity
import com.tecnoscimmia.nine.model.GameSettings
import com.tecnoscimmia.nine.model.MatchResultDb
import com.tecnoscimmia.nine.model.MatchResultRepository
import com.tecnoscimmia.nine.model.SettingsRepository

/*
* This file defines the view-model for the settings screen
*/


class SettingsViewModel(private val settings: GameSettings, private val settingsRepo: SettingsRepository,
				private val resultRepo: MatchResultRepository) : ViewModel()
{
	private var currTheme 				= mutableStateOf(settings.getTheme())
	private var currKeyboardLayout		= mutableStateOf(settings.getKeyboardLayout())
	private var currSymbolsSet			= mutableStateOf(settings.getSymbolsSet())
	private var currDebugMode			= mutableStateOf(settings.getDebugMode())
	private var hasSomethingChanged 	= mutableStateOf(false)


	// Getter methods (needed because the view should not access the repository directly)
	fun getAvailableThemes() : 			List<String> { return settingsRepo.getAvailableThemes() }
	fun getAvailableKeyboardLayouts() : List<String> { return settingsRepo.getAvailableKeyboardLayouts() }
	fun getAvailableSymbolsSets() : 	List<String> { return settingsRepo.getAvailableSymbolsSets() }
	fun getAvailableDebugModes() : 		List<String> { return settingsRepo.getAvailableDebugModes() }

	fun hasSomethingChanged() : 		Boolean { return hasSomethingChanged.value }


	// Converts the internal representation of the theme value to the UI one
	fun getTheme() : String
	{
		return try { settingsRepo.themeInternalToUI(currTheme.value) } catch(e: Exception) { "" }
	}


	// Converts the internal representation of the keyboard layout value to the UI one
	fun getKeyboardLayout() : String
	{
		return try { settingsRepo.keyboardLayoutInternalToUI(currKeyboardLayout.value) } catch(e: Exception) { "" }
	}


	// Converts the internal representation of the symbol set value to the UI one
	fun getSymbolsSet() : String
	{
		return try { settingsRepo.symbolsSetInternalToUI(currSymbolsSet.value) } catch(e: Exception) { "" }
	}


	// Converts the internal representation of the debug mode value to the UI one
	fun getDebugMode() : String
	{
		return try { settingsRepo.debugModeInternalToUI(currDebugMode.value) } catch(e: Exception) { "" }
	}


	// Converts the UI representation of the theme to the internal one and updates the current selected theme
	fun setTheme(newTheme: String)
	{
		val newInternalTheme = try { settingsRepo.themeUItoInternal(newTheme) } catch(e: Exception) { currTheme.value }

		currTheme.value = newInternalTheme
		hasSomethingChanged.value = true
	}


	// Converts the UI representation of the keyboard layout to the internal one and updates the current selected layout
	fun setKeyboardLayout(newLayout: String)
	{
		val newInternalLayout =	try { settingsRepo.keyboardLayoutUItoInternal(newLayout) } catch(e: Exception) { currKeyboardLayout.value }

		currKeyboardLayout.value = newInternalLayout
		hasSomethingChanged.value = true
	}


	// Converts the UI representation of the symbols set to the internal one and updates the current value
	fun setSymbolsSet(newSymbolsSet: String)
	{
		val newInternalSymbolsSet = try { settingsRepo.symbolsSetUItoInternal(newSymbolsSet) } catch(e: Exception) { currSymbolsSet.value }

		currSymbolsSet.value = newInternalSymbolsSet
		hasSomethingChanged.value = true
	}


	// Converts the UI representation of the debug mode value to the internal one and updates the current value
	fun setDebugMode(newDebugMode: String)
	{
		val newInternalDebugMode = try { settingsRepo.debugModeUItoInternal(newDebugMode) } catch(e: Exception) { currDebugMode.value }

		currDebugMode.value = newInternalDebugMode
		hasSomethingChanged.value = true
	}


	// Applies changes to the game settings class
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

		if(currDebugMode.value != settings.getDebugMode())
			settings.setDebugMode(currDebugMode.value)

		hasSomethingChanged.value = false
	}


	fun clearScoreboardData()
	{
		resultRepo.deleteAllResults()
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

				val settingsRepo = SettingsRepository.getInstance(application.applicationContext)

				return SettingsViewModel(settings = GameSettings.getInstance(application.applicationContext), settingsRepo = settingsRepo,
					resultRepo = MatchResultRepository(dao = MatchResultDb.getInstance(application.applicationContext).matchResultDao(), settingsRepo = settingsRepo)
				) as T
			}
		}
	}
}