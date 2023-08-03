package com.tecnoscimmia.nine.viewModel

import android.content.res.Resources
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.tecnoscimmia.nine.model.GameSettings
import com.tecnoscimmia.nine.model.Match
import com.tecnoscimmia.nine.model.MatchResultDb
import com.tecnoscimmia.nine.model.MatchResultRepository
import com.tecnoscimmia.nine.model.Symbol

/*
 * This file defines the view-model for the game screen
 */


class GameViewModel(private val appResources: Resources, private val settings: GameSettings,
						private val resultRepo: MatchResultRepository) : ViewModel()
{
	// Data about the current match that is being played by the user
	private var currMatch 			= Match(symbolsSet = Symbol.generateSymbolsSubset(appResources = appResources, symbolsSetType = settings.getSymbolsSet()))

	private var selectedIndex 		= mutableStateOf(0)		// Index in which we will place the next symbol inserted by the user
	private var insertedSymbolsNum 	= 0								// Keeps track of how many symbols has been inserted by the user (to avoid a premature evaluation of the user key)
	private var differenceStr 		= mutableStateOf("")		// This string indicates the distances between the elements of secretKey and userKey


	init {
		currMatch.generateSecretKey()
	}

	fun getSecretKey() : String // TODO: Remove this function is just to test the evaluate method
	{
		var secretKey = ""
		for(symbol in currMatch.getSecretKey())
			secretKey += symbol.value

		return secretKey
	}


	// Getter methods
	fun getKeyboardLayout() : 	GameSettings.KeyboardLayoutSetting 	{ return settings.getKeyboardLayout()}
	fun getSymbolsSet() : 		Array<Symbol>						{ return currMatch.getSymbolsSet() }
	fun getSelectedIndex() : 	Int									{ return selectedIndex.value}
	fun getUserInput() : 		SnapshotStateList<Symbol>			{ return currMatch.getUserKey() }
	fun getDifferenceString() : String								{ return differenceStr.value }
	fun getTime() : 			String								{ return "XX:XX" /* TODO: Add implementation*/ }
	fun getAttemptsNum() : 		UInt								{ return currMatch.getAttempts() }
	fun isDebugModeActive() : 	Boolean								{ return settings.isDebugModeActive() }

	// Moves forward the selectedIndex (if possible)
	fun selectNextSymbol()
	{
		val userKey = currMatch.getUserKey()

		// We can select the next symbol only if we are not going out of array bounds and the current symbol is not empty
		if(selectedIndex.value + 1 < userKey.size && userKey[selectedIndex.value].value.isNotEmpty())
			selectedIndex.value++
	}


	// Moves backwards the selectedIndex (if possible)
	fun selectPrevSymbol()
	{
		if(selectedIndex.value > 0)
			selectedIndex.value--
	}


	// Inserts the given symbols in the user key and increments the selected index (if possible)
	fun insertSymbol(symbol: String)
	{
		if(insertedSymbolsNum < GameSettings.MAX_DIGITS_NUM && selectedIndex.value == insertedSymbolsNum)
			insertedSymbolsNum++

		currMatch.insertSymbol(symbol = symbol, index = selectedIndex.value)
		selectNextSymbol()
	}


	fun evaluate()
	{
		if(insertedSymbolsNum >= GameSettings.MAX_DIGITS_NUM)
			differenceStr.value = currMatch.evaluate()
	}


	// Creates a new match and resets the properties of the view model that are related to the old match
	fun startNewMatch()
	{
		currMatch = Match(symbolsSet = Symbol.generateSymbolsSubset(appResources = appResources, symbolsSetType = settings.getSymbolsSet()))
		currMatch.generateSecretKey()
		selectedIndex.value = 0
		insertedSymbolsNum = 0
	}


	// GameViewModel factory
	companion object
	{
		val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory
		{
			@Suppress("UNCHECKED_CAST")
			override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T
			{
				val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])			// Get the application object form extras
				val savedStateHandle = extras.createSavedStateHandle()			// Create a SavedStateHandle for this ViewModel from extras

				return GameViewModel(appResources = application.resources,
					settings = GameSettings.getInstance(application.applicationContext),
					resultRepo = MatchResultRepository(MatchResultDb.getInstance(application.applicationContext).matchResultDao()) ) as T
			}
		}
	}
}