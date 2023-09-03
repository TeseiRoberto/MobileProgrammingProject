package com.tecnoscimmia.nine.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


// This class implements a really simple chronometer used to keep track of elapsed time during the game
class Chronometer(@set:Synchronized private var onUpdateCallback: (() -> Unit)? = null, private val updateRateMilliSec: Long)
{
	@get:Synchronized @set:Synchronized
	private var isRunning: 	Boolean	= false

	@get:Synchronized @set:Synchronized
	private var elapsedMilliSec: Long 	= 0L						// Number of milli seconds elapsed since the chronometer was started


	// Method to set a callback that will be invoked after each second when the chronometer gets updated
	fun setOnTickCallback(onUpdate: () -> Unit)
	{
		onUpdateCallback = onUpdate
	}


	// Launches a coroutine that sleeps for a certain amount of milli seconds and after the sleep
	// increments the timer, startDelayMilliSec specifies how much time to wait before actually starting the chronometer
	fun start(startDelayMilliSec: Long = 0L)
	{
		if(isRunning)											// If the chronometer is already running then return
			return

		isRunning = true
		CoroutineScope(Dispatchers.IO).launch()
		{
			if(startDelayMilliSec != 0L)
				delay(startDelayMilliSec)

			while(isRunning)
			{
				delay(updateRateMilliSec)
				elapsedMilliSec += updateRateMilliSec

				if(onUpdateCallback != null)
					onUpdateCallback!!()
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

		elapsedMilliSec = 0L
	}


	// Returns the elapsed time as a string
	fun getElapsedTimeAsString() : String
	{
		var totalTime = elapsedMilliSec / 1000				// Get elapsed time in seconds

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
