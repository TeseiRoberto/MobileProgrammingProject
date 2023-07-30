package com.tecnoscimmia.nine.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList

/*
 * This file defines the MatchClass, this class contains all the data needed for a match
 */


class Match(private val symbolsSet: Array<Symbol>)
{
	private var secretKey: 			Array<Symbol>							// Permutation of symbols generated randomly by the game
	private var userKey 			= mutableStateListOf<Symbol>()			// Permutation inserted by the user
	private var differences 		= mutableStateOf("")				// This string indicates the distances between the elements of secretKey and userKey
	private var isPaused			= mutableStateOf(false)

	private var attemptsNum			= mutableStateOf(0u)				// Total Number of attempts


	init {
		secretKey = Array(size = 9, init = { Symbol(value = "") } )			// Fill secret key array with default values

		for(i in 0 until GameSettings.DIGITS_NUM)							// Fill user key array with default values
			userKey.add(Symbol(value = ""))
	}


	// Returns the symbols set used for this match
	fun getSymbolsSet() : 	Array<Symbol>				{ return symbolsSet }
	fun getAttempts() : 	UInt						{ return attemptsNum.value }
	fun getUserKey() : 		SnapshotStateList<Symbol>	{ return userKey }


	// Inserts a new symbols in user key at the given index
	fun insertSymbol(symbol: String, index: Int)
	{
		if(index < userKey.size)
			userKey[index] = Symbol(value = symbol)
	}


	// Generates a new secret key using the symbols in the symbols set
	fun generateSecretKey()
	{
		// TODO: Add implementation
	}


	// Calculates the difference string comparing the content of userKey with the secret key
	fun evaluate()
	{
		attemptsNum.value++
		// TODO: Add implementation
	}

}