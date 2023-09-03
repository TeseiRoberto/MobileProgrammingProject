package com.tecnoscimmia.nine.model

import android.content.res.Resources
import com.tecnoscimmia.nine.R

/*
 * This file defines the SymbolSet and Symbol classes.
 *
 * The symbol class holds a string that represent a symbol that can be used in the app.
 *
 * The SymbolSet class is simply an array of symbols, the size of the
 * array is DIGITS_NUM - 1 (the DIGITS_NUM constant is defined in the GameSettings class); as the
 * application name says the default size is 9 but it is possible to change it.
 *
 * SymbolsSet contains the symbols that are used to generate the secret key of a match,
 * the user can actually insert and are displayed in the Keyboard widget in the game screen.
 *
 */


data class Symbol(val value: String)

class SymbolSet private constructor(val data: Array<Symbol>)
{

	companion object
	{
		// Returns an instance of the SymbolSet class that contains all the symbols of the symbol set type specified
		fun loadSymbolsSet(appResources: Resources, symbolsSetType: GameSettings.SymbolsSetSetting) : SymbolSet
		{
			val result = mutableListOf<Symbol>()

			val symbolsStr = when(symbolsSetType)			// Load the symbols string from the resource file according to the given symbol set type
			{
				GameSettings.SymbolsSetSetting.NUMBERS_SYMBOLS_SET -> appResources.getString(R.string.numbers_symbols)
				GameSettings.SymbolsSetSetting.LETTERS_SYMBOLS_SET -> appResources.getString(R.string.letters_symbols)
				GameSettings.SymbolsSetSetting.EMOTICONS_SYMBOLS_SET -> appResources.getString(R.string.emoticons_symbols)
				GameSettings.SymbolsSetSetting.PUNCTUATION_SYMBOLS_SET -> appResources.getString(R.string.punctuation_symbols)
				GameSettings.SymbolsSetSetting.MIXED_SYMBOL_SET ->
						appResources.getString(R.string.numbers_symbols) + ',' + appResources.getString(R.string.letters_symbols) + ',' +
							appResources.getString(R.string.emoticons_symbols) + ',' + appResources.getString(R.string.punctuation_symbols)
			}

			val symbols = symbolsStr.split(",")

			for(el in symbols)
			{
				if(el.length > 1)							// Strings with a length greater than 1 must be decoded
				{
					val emojiCode = Integer.decode(el)
					result.add(Symbol(value = String(Character.toChars(emojiCode))))
				} else {
					result.add(Symbol(value = el))
				}
			}

			return SymbolSet(data = result.toTypedArray())
		}


		// Returns an instance of the SymbolSet class that contains only a subset of the symbols available in the given symbols set type
		fun generateSymbolsSubset(appResources: Resources, symbolsSetType: GameSettings.SymbolsSetSetting) : SymbolSet
		{
			val result = mutableListOf<Symbol>()
			val completeSet = loadSymbolsSet(appResources, symbolsSetType)

			// If current symbols set has less elements than what we need then we return it directly (we cannot choose a subset of elements)
			if(completeSet.data.size <= GameSettings.MAX_DIGITS_NUM)
				return completeSet

			for(i in 0 until GameSettings.MAX_DIGITS_NUM)				// Otherwise we need to choose a certain number of symbols from the complete set randomly
			{
				var j = (completeSet.data.indices).random()				// Get a random index
				while(completeSet.data[j] in result)						// Loop until we find an element that we have not already choose
				{
					if(j == completeSet.data.size - 1)
						j = 0
					else
						j++
				}

				result.add(completeSet.data[j])
			}

			return SymbolSet(data = result.toTypedArray())
		}

	}

}