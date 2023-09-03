package com.tecnoscimmia.nine.model

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlin.math.absoluteValue

/*
 * This file defines the MatchClass, this class contains all the data needed for a match
 */


class Match(private val symbolsSet: SymbolSet)
{
	private var secretKey			: Array<Symbol>						// Permutation of symbols generated randomly by the game
	private val userKey 			= mutableStateListOf<Symbol>()		// Permutation inserted by the user
	private val attemptsNum			= mutableStateOf(0u)			// Total Number of attempts
	private val isMatchOver			= mutableStateOf(false)		// Signals when the secret key and the user key contains the same symbols in the same order


	init {
		// Fill secret key array with default values
		secretKey = Array(size = GameSettings.MAX_DIGITS_NUM, init = { Symbol(value = "") } )
	}

	// Getter methods
	fun getSymbolsSet() : 	SymbolSet					{ return symbolsSet }			// Returns the symbols set used for this match
	fun getAttempts() : 	UInt						{ return attemptsNum.value }
	fun getUserKey() : 		SnapshotStateList<Symbol>	{ return userKey }
	fun isMatchOver() : 	Boolean						{ return isMatchOver.value }


	// This method returns the secret key as a string
	fun getSecretKeyAsString() : String
	{
		var secretKeyStr = ""
		for(symbol in secretKey)
			secretKeyStr += symbol.value

		return secretKeyStr
	}


	// Inserts a new symbols in user key at the given index
	fun insertSymbol(symbol: Symbol, index: Int)
	{
		// Check that given index is not out of bounds (we limit the dimension of user key between the range [0, MAX_DIGITS_NUM) )
		if(index < 0 || index >= GameSettings.MAX_DIGITS_NUM)
			return

		var swapIndex = -1
		for(i in userKey.indices)							// Check if the given symbol has already been inserted
		{
			if(userKey[i].value == symbol.value)			// If it has then we need to swap it from the old position to the new one
			{
				swapIndex = i
				break
			}
		}

		if(index >= userKey.size)							// If we are inserting a new element
		{
			if(swapIndex != -1)
			{
				userKey[swapIndex] = Symbol(value = "")
				userKey.add(symbol)
			} else {
				userKey.add(symbol)
			}

		} else {											// If we are inserting into an existing element
			if(swapIndex != -1)
			{
				userKey[swapIndex] = Symbol(value = userKey[index].value)
				userKey[index] = symbol
			} else {
				userKey[index] = symbol
			}
		}
	}


	// Generates a new secret key using the symbols in the symbols set
	fun generateSecretKey()
	{
		for(i in secretKey.indices)									// For every element in secret key
		{
			val j = symbolsSet.data.indices.random()				// Choose a random index
			var currSymbol = symbolsSet.data[j]						// Get the symbol at the chosen index

			if(currSymbol in secretKey)								// If the chosen symbol is already in the secret key then we need to chose another one
			{
				for(k in 1 until symbolsSet.data.size)			// What we do is simply move forward and backwards from the chosen index and the first
				{													// symbol that we find that has not already been chosen gets picked
					if(j + k < symbolsSet.data.size && symbolsSet.data[j + k] !in secretKey)
					{
						currSymbol = symbolsSet.data[j + k]
						break
					}

					if(j - k >= 0 && symbolsSet.data[j - k] !in secretKey)
					{
						currSymbol = symbolsSet.data[j - k]
						break
					}
				}
			}

			secretKey[i] = currSymbol
		}

	}


	// Calculates the differences string comparing the content of userKey and secret key and then returns it
	fun evaluate() : String
	{
		// If the length of the user key is not equal to the length of the secret key then we cannot compare them
		if(userKey.size != secretKey.size)
			return ""

		attemptsNum.value++
		var differencesStr = ""
		var correctlyGuessedNum = 0							// We keep track of the number of symbols guessed correctly to determine if the match is over or not
		val keyHalfLen = GameSettings.MAX_DIGITS_NUM / 2

		// We need to calculate the minimum distance ,in terms of indices, between the symbols in the user key and the symbols in the secret key
		for(i in userKey.indices)
		{
			var symbolsDistance = 0

			for(j in secretKey.indices)
			{
				if(userKey[i].value == secretKey[j].value)
				{
					symbolsDistance = (i - j).absoluteValue

					if(symbolsDistance == 0)
						correctlyGuessedNum++

					break
				}
			}

			// Add char to differences string to indicate the distance between symbols
			differencesStr += if(symbolsDistance <= keyHalfLen)
				symbolsDistance.toString()
			else
				(GameSettings.MAX_DIGITS_NUM - symbolsDistance).toString()

		}

		if(correctlyGuessedNum == GameSettings.MAX_DIGITS_NUM)
			isMatchOver.value = true

		return differencesStr
	}

}
