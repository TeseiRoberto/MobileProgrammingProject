package com.tecnoscimmia.nine.controller

import android.content.res.Resources
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
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
	private var currMatch : MutableState<Match>					// Data about the current match that is being played by the user
	private var selectedIndex = mutableStateOf(0)			// Index in which we will place the next symbol inserted by the user


	init {
		currMatch = mutableStateOf(
			Match(symbolsSet = Symbol.generateSymbolsSubset(appResources = appResources, symbolsSetType = settings.getSymbolsSet()) )
		)

		startNewMatch()
	}


	// Getter methods
	fun getKeyboardLayout() : 	GameSettings.KeyboardLayoutSetting 	{ return settings.getKeyboardLayout()}
	fun getSymbolsSet() : 		Array<Symbol>						{ return currMatch.value.getSymbolsSet() }
	fun getSelectedIndex() : 	Int									{ return selectedIndex.value}
	fun getUserInput() : 		Array<Symbol>						{ return currMatch.value.getUserKey() }


	// Moves forward the selectedIndex (if possible)
	fun selectNextSymbol()
	{
		if(selectedIndex.value + 1 < currMatch.value.getUserKey().size)
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
		currMatch.value.insertSymbol(symbol = symbol, index = selectedIndex.value)
		//selectNextSymbol()
	}


	// Resets currMatch data to create a new match
	fun startNewMatch()
	{
		// Create a new match
		currMatch.value = Match(symbolsSet = Symbol.generateSymbolsSubset(appResources = appResources, symbolsSetType = settings.getSymbolsSet()))
		currMatch.value.generateSecretKey()
		selectedIndex.value = 0
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