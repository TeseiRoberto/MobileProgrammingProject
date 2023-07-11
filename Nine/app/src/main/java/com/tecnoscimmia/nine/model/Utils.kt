package com.tecnoscimmia.nine.model

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


// This class implements a really simple timer used to keep track of elapsed time,
class Timer
{
	@get:Synchronized @set:Synchronized
	private var isRunning: 	Boolean	= false

	@get:Synchronized @set:Synchronized
	private var elapsedSec: Long 	= 0L						// Number of seconds elapsed since the timer was started


	// Launches a coroutine that sleeps for a second and after the sleep increments the timer
	fun start()
	{
		if(isRunning)											// If the timer is already running then return
			return

		isRunning = true
		CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO)
		{
			while(isRunning)
			{
				delay(1000)
				elapsedSec++
			}
		}
	}


	// Stops the timer
	fun pause()
	{
		if(isRunning)											// If the timer is running we stop it
			isRunning = false
	}


	// Restarts the timer from where it left
	fun unPause()
	{
		start()
	}


	// Stops the timer and reset it
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