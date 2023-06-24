package com.tecnoscimmia.nine.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.tecnoscimmia.nine.R
import com.tecnoscimmia.nine.ui.theme.NineButtonStyle
import com.tecnoscimmia.nine.ui.theme.NineIconStyle

/*
 * This file contains the definitions of all the screens that the application is composed of
 */

// Enumeration of all the screens that makes the application
enum class NineScreen { MainMenu, Scoreboard, Options, Game }


@Composable
fun MenuScreen(isLandscape: Boolean)
{
	Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween)
	{
		if(isLandscape)
		{
			MenuPanelLandscape()
			GameStarter(250f, 200f, onSwipe = { /*TODO*/ }, swipeThreshold = 300f, maxIconScale = 4f)
		} else {
			MenuPanelPortrait()
			GameStarter(230f, 300f, onSwipe = { /*TODO*/ }, swipeThreshold = 300f, maxIconScale = 4f)
		}

		GameModeSelector(availableModes = listOf("free", "challenge"), currMode = 0)
	}
}


@Composable
fun ScoreboardScreen(isLandscape: Boolean)
{
	Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween)
	{
		ScreenTitle(title = stringResource(id = R.string.scoreboard_screen_title))

		/* TODO: Add lazy list that shows the results of all games played
		val testRank = listOf(1, 2, 3, 4, 5, 6)
		val testTime = listOf("01:00", "1:00", "1:19", "2:45", "99:73", "1234:00")
		val testDate = listOf("02/01/1999", "26/08/1913", "12/04/2034", "01/08/1945", "99/73/44444", "1234/00/0900")
		val gameModeTest = listOf("free", "free", "challenge", "free", "lalalalalalal", "free")

		for(i in 0..5)
			ScoreboardEntry(rank = testRank[i], time = testTime[i], date = testDate[i], gameMode = gameModeTest[i])*/

		// Back button
		Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.Bottom)
		{
			ButtonWithIcon(onClick = { /*TODO*/ }, iconId = NineIconStyle.leftArrow_round,
							btnShape = RoundedCornerShape(topEnd = NineButtonStyle.cornerRadius)
			)
		}
	}
}


@Composable
fun OptionsScreen(isLandscape: Boolean)
{
	Column()
	{
		ScreenTitle(title = stringResource(id = R.string.option_screen_title))
		// TODO: Add implementation...
	}
}


@Composable
fun GameScreen(isLandscape: Boolean)
{
	Column()
	{
		ScreenTitle(title = "Game screen!!!")
		// TODO: Add implementation...
	}
}