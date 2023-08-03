package com.tecnoscimmia.nine.model

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlin.math.absoluteValue

/*
 * This file defines the MatchClass, this class contains all the data needed for a match
 */


class Match(private val symbolsSet: Array<Symbol>)
{
	private var secretKey: 			Array<Symbol>							// Permutation of symbols generated randomly by the game
	private var userKey 			= mutableStateListOf<Symbol>()			// Permutation inserted by the user
	private var isPaused			= mutableStateOf(false)
	private var attemptsNum			= mutableStateOf(0u)				// Total Number of attempts


	init {
		secretKey = Array(size = 9, init = { Symbol(value = "") } )			// Fill secret key array with default values

		for(i in 0 until GameSettings.MAX_DIGITS_NUM)					// Fill user key array with default values
			userKey.add(Symbol(value = ""))
	}


	// Returns the symbols set used for this match
	fun getSymbolsSet() : 	Array<Symbol>				{ return symbolsSet }
	fun getAttempts() : 	UInt						{ return attemptsNum.value }
	fun getUserKey() : 		SnapshotStateList<Symbol>	{ return userKey }

	fun getSecretKey() : Array<Symbol> { return secretKey } // TODO: Remove this function is just to test the evaluate method

	// Inserts a new symbols in user key at the given index
	fun insertSymbol(symbol: String, index: Int)
	{
		if(index < userKey.size)
			userKey[index] = Symbol(value = symbol)
	}


	// Generates a new secret key using the symbols in the symbols set
	fun generateSecretKey()
	{
		// TODO: This code for now simply sets the secret key using the same order of elements of the given symbol set, but we need to
		// TODO: actually generate the secret key randomly!
		for(i in secretKey.indices)
			secretKey[i].value = symbolsSet[i].value
	}


	// Calculates the differences string comparing the content of userKey with the secret key and then returns it
	fun evaluate() : String
	{
		attemptsNum.value++
		var differencesStr = ""

		// We need to calculate the distance ,in terms of indices, between the symbols in the user key and the symbols in the secret key
		for(i in userKey.indices)
		{
			var symbolsDistance = 0

			for(j in secretKey.indices)
			{
				if(userKey[i].value == secretKey[j].value)
				{
					symbolsDistance = (i - j).absoluteValue
					break
				}
			}

			// Add char to differences string to indicate the distance between symbols
			differencesStr += if(symbolsDistance == 0)
				"0"
			else if(symbolsDistance < GameSettings.MAX_DIGITS_NUM / 2)
				symbolsDistance.toString()
			else
				(GameSettings.MAX_DIGITS_NUM - symbolsDistance).toString()

		}

		return differencesStr
	}

}