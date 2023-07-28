package com.tecnoscimmia.nine.model

import com.tecnoscimmia.nine.utils.Timer

/*
 * This file defines the MatchClass, this class contains all the data needed for a match
 */


class Match(private val symbolsSet: Array<Symbol>)
{
	private var secretKey: 		Array<Symbol>				// Permutation of symbols generated randomly by the game
	private var userKey: 		Array<Symbol>				// Permutation inserted by the user
	private var differences:	String = ""					// This string indicates the distances between the elements of secretKey and userKey
	private var isPaused:		Boolean = false

	private var attemptsNum:	UInt = 0u					// Total Number of attempts


	init {
		secretKey = Array(size = 9, init = { Symbol(value = "") } )	// Fill secret key array with default values
		userKey = Array(size = 9, init = { Symbol(value = "") } )	// Fill user key array with default values
	}


	// Returns the symbols set used for this match
	fun getSymbolsSet() : 	Array<Symbol>	{ return symbolsSet }
	fun getAttempts() : 	UInt			{ return attemptsNum }
	fun getUserKey() : 		Array<Symbol>	{ return userKey }


	// Inserts a new symbols in user key at the given index
	fun insertSymbol(symbol: String, index: Int)
	{
		if(index < userKey.size)
			userKey[index].value = symbol
	}


	// Generates a new secret key using the symbols in the symbols set
	fun generateSecretKey()
	{}


	// Calculates the difference string comparing the content of userKey with the secret key
	fun evaluate(userKey: String)
	{}

}