package com.tecnoscimmia.nine.model

import android.content.res.Resources
import com.tecnoscimmia.nine.R

/*
 * This file defines the Symbol class, instances of this class are used to create what we call
 * symbols set. A symbols set is is just an array of instances of Symbol class, the size of the
 * array is DIGITS_NUM - 1, (the DIGITS_NUM constant is defined in the GameSettings class); as the
 * application name says the default size is 9 but is possible to change it.
 *
 * SymbolsSet defines the symbols that: are used to generate the secret key of a match,
 * the user can actually insert, are displayed in the Keyboard widget in the game screen.
 *
 */


class Symbol(val value: String = "")
{

	fun isEmpty() : Boolean
	{
		return value == ""
	}


	companion object
	{
		// Returns an array that contains all the symbols of the given symbols set
		fun loadSymbolsSet(appResources: Resources, symbolsSetType: GameSettings.SymbolsSetSetting) : Array<Symbol>
		{
			val result = mutableListOf<Symbol>()
			var usingEmoticons = false

			// Load the symbols string from the resource file
			val symbolsStr = when(symbolsSetType)
			{
				GameSettings.SymbolsSetSetting.NUMBERS_SYMBOLS_SET -> appResources.getString(R.string.numbers_symbols)
				GameSettings.SymbolsSetSetting.LETTERS_SYMBOLS_SET -> appResources.getString(R.string.letters_symbols)
				GameSettings.SymbolsSetSetting.EMOTICONS_SYMBOLS_SET -> {
					usingEmoticons = true
					appResources.getString(R.string.emoticons_symbols)
				}
			}

			// Now we need to parse the symbols string
			var currStr = ""												// Temporary buffer for the current symbol
			for(i in 0 .. symbolsStr.length)
			{
				if(i == symbolsStr.length || symbolsStr[i] == ',')	// If we reached the end of the string or the current char is a comma
				{
					if(usingEmoticons)								// If we are using emoticons as symbols set then we need to parse the hex code of the emoji
					{
						val emojiCode = Integer.decode(currStr)
						result.add(Symbol(value = String(Character.toChars(emojiCode))))
					} else {
						result.add(Symbol(value = currStr))			// Otherwise we just add the symbol to the result list
					}
					currStr = ""
				} else {
					currStr += symbolsStr[i]
				}
			}

			return result.toTypedArray()
		}


		// Generate an array of symbols classes, the elements of the array are chosen randomly from the complete symbols set
		fun generateSymbolsSubset(appResources: Resources, symbolsSetType: GameSettings.SymbolsSetSetting) : Array<Symbol>
		{
			val result = mutableListOf<Symbol>()
			val completeSet = loadSymbolsSet(appResources, symbolsSetType)

			// If current symbols set has less elements than what we need then we return it directly (we cannot choose a subset of elements)
			if(completeSet.size <= GameSettings.MAX_DIGITS_NUM)
				return completeSet

			// Otherwise we need to choose a certain number of symbols from the complete set randomly
			for(i in 0 until GameSettings.MAX_DIGITS_NUM)
			{
				var j = (completeSet.indices).random()					// Get a random index
				while(completeSet[j] in result)							// Loop until we find an element that we have not already choose
				{
					if(j == completeSet.size - 1)
						j = 0
					else
						j++
				}

				result.add(completeSet[j])
			}

			return result.toTypedArray()
		}

	}

}