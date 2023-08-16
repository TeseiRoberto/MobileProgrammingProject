package com.tecnoscimmia.nine.viewModel

import android.content.res.Resources
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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
	private var differenceStr 		= mutableStateOf("")		// This string indicates the distances between the elements of secretKey and userKey
	private var isMatchPaused		= mutableStateOf(false)
	private var isMatchOver			= mutableStateOf(false)

	init {
		currMatch.generateSecretKey()
	}

	// Getter methods
	fun getKeyboardLayout() : 	GameSettings.KeyboardLayoutSetting 	{ return settings.getKeyboardLayout()}
	fun getSymbolsSet() : 		Array<Symbol>						{ return currMatch.getSymbolsSet() }
	fun getSelectedIndex() : 	Int									{ return selectedIndex.value}
	fun getUserInput() : 		SnapshotStateList<Symbol>			{ return currMatch.getUserKey() }

	fun getDifferenceString() : String								{ return differenceStr.value }
	fun getTime() : 			String								{ return "XX:XX" /* TODO: Add implementation*/ }
	fun getAttemptsNum() : 		UInt								{ return currMatch.getAttempts() }
	fun isMatchPaused() : 		Boolean								{ return isMatchPaused.value }
	fun isMatchOver() : 		Boolean								{ return isMatchOver.value }
	fun isDebugModeActive() : 	Boolean								{ return settings.isDebugModeActive() }
	fun getSecretKey() : 		String 								{ return currMatch.getSecretKeyAsString() }


	// Moves forward the selectedIndex (if possible)
	fun selectNextSymbol()
	{
		val userKeySize = currMatch.getUserKey().size

		// If we are in a free spot or we are at the last possible symbol
		if(selectedIndex.value + 1 > userKeySize || selectedIndex.value + 1 == GameSettings.MAX_DIGITS_NUM)
			selectedIndex.value = 0						// Then we wrap around
		else
			selectedIndex.value++						// Otherwise we can move to the next symbol
	}


	// Moves backwards the selectedIndex (if possible)
	fun selectPrevSymbol()
	{
		if(selectedIndex.value > 0)						// If we are somewhere in the middle of the user input
		{
			selectedIndex.value--						// Then we can go back to the previous symbol
		} else {
			val userKeySize = currMatch.getUserKey().size

			if(userKeySize != GameSettings.MAX_DIGITS_NUM)	// Otherwise if there is still space to add symbols
				selectedIndex.value = userKeySize			// We move to the first free space
			else
				selectedIndex.value = userKeySize - 1		// If there's no space left then we move to the last symbol
		}
	}


	// Inserts the given symbols in the user key and increments the selected index (if possible)
	fun insertSymbol(symbol: String)
	{
		currMatch.insertSymbol(symbol = symbol, index = selectedIndex.value)

		if(selectedIndex.value + 1 < GameSettings.MAX_DIGITS_NUM)
			selectedIndex.value++
	}


	// Stops the current match
	fun pauseMatch()
	{
		if(isMatchPaused.value == false)
			isMatchPaused.value = true
		// TODO: pause the timer when we will have one :_D
	}


	// Restarts the current match from where it was left
	fun resumeMatch()
	{
		if(isMatchPaused.value)
			isMatchPaused.value = false
		// TODO: unpause the timer when we will have one :_D
	}


	fun evaluate()
	{
		val userKey = currMatch.getUserKey()

		// Check if there are empty symbols in the user input, if there is at least one then we cannot evaluate
		// the user input so we set the selected index to that element because the user must insert something in it
		for(i in userKey.indices)
		{
			if(userKey[i].isEmpty())
			{
				selectedIndex.value = i
				return
			}
		}

		differenceStr.value = currMatch.evaluate()			// Check differences between user key and secret key

		if(differenceStr.value == "000000000")				// If the user key and the secret key are the same
		{
			when(settings.getGameMode())					// We do something according to the current game mode
			{
				GameSettings.GameModeSetting.CHALLENGE_GAME_MODE -> isMatchOver.value = true
				GameSettings.GameModeSetting.TRAINING_GAME_MODE -> startNewMatch()
			}
		}

	}


	// Creates a new match and resets the properties of the view model that are related to the old match
	fun startNewMatch()
	{		currMatch.generateSecretKey()

		currMatch = Match(symbolsSet = Symbol.generateSymbolsSubset(appResources = appResources, symbolsSetType = settings.getSymbolsSet()))
		currMatch.generateSecretKey()
		selectedIndex.value = 0
		isMatchPaused.value = false
		isMatchOver.value = false
	}


	// GameViewModel factory
	companion object
	{
		val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory
		{
			@Suppress("UNCHECKED_CAST")
			override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T
			{
				val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])						// Get the application object form extras

				val gameSettings 	= GameSettings.getInstance(application.applicationContext)											// Retrieve game settings instance
				val resultRepo 		= MatchResultRepository(MatchResultDb.getInstance(application.applicationContext).matchResultDao())	// Instantiate a match result repository

				return GameViewModel(appResources = application.resources, settings = gameSettings, resultRepo = resultRepo) as T
			}
		}
	}
}

