package com.tecnoscimmia.nine.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


// This class implements a really simple chronometer used to keep track of elapsed time during the game
class Chronometer
{
	@get:Synchronized @set:Synchronized
	private var isRunning: 	Boolean	= false

	@get:Synchronized @set:Synchronized
	private var elapsedSec: Long 	= 0L						// Number of seconds elapsed since the chronometer was started

	@set:Synchronized
	private var onTickCallback: (() -> Unit)? = null			// Callback invoked when the chronometer updates


	// Method to set a callback that will be invoked after each second when the chronometer gets updated
	fun setOnTickCallback(onTick: () -> Unit)
	{
		onTickCallback = onTick
	}


	// Launches a coroutine that sleeps for a second and after the sleep increments the timer
	fun start()
	{
		if(isRunning)											// If the chronometer is already running then return
			return

		isRunning = true
		CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO)
		{
			while(isRunning)
			{
				delay(1000)
				elapsedSec++

				if(onTickCallback != null)
					onTickCallback!!()
			}
		}
	}


	// Stops the chronometer
	fun pause()
	{
		if(isRunning)											// If the chronometer is running we stop it
			isRunning = false
	}


	// Restarts the chronometer from where it left
	fun unpause()
	{
		start()
	}


	// Stops the chronometer and reset it
	fun reset()
	{
		if(isRunning)
			isRunning = false

		elapsedSec = 0L
	}


	// Returns the elapsed time as a string
	fun getElapsedTimeAsString() : String
	{
		var totalTime = elapsedSec

		val hours = (totalTime / 3600).toInt()
		totalTime -= hours * 3600

		val minutes = (totalTime / 60).toInt()
		totalTime -= minutes * 60

		return if(hours != 0)
			String.format("%02d:%02d:%02d", hours, minutes, totalTime)
		else
			String.format("%02d:%02d", minutes, totalTime)
	}
}
