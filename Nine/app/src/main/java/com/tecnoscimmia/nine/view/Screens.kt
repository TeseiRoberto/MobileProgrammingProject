package com.tecnoscimmia.nine.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.tecnoscimmia.nine.R
import com.tecnoscimmia.nine.controller.GameController
import com.tecnoscimmia.nine.controller.MainController
import com.tecnoscimmia.nine.controller.ScoreboardController
import com.tecnoscimmia.nine.controller.SettingsController

/*
 * This file contains the definitions of all the screens that the application is composed of
 */

// Enumeration of all the screens that makes the application
enum class NineScreen { MainMenu, Scoreboard, Settings, Game }


@Composable
fun MenuScreen(cntrl: MainController, isLandscape: Boolean)
{
	Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween)
	{
		if(isLandscape)
		{
			MenuPanelLandscape(onClickSettings = cntrl::SwitchToSettingsScreen, onClickScoreboard = cntrl::SwitchToScoreboardScreen)
			GameStarter(250f, 200f, onSwipe = cntrl::startGame, swipeThreshold = 300f, maxIconScale = 4f)
		} else {
			MenuPanelPortrait(onClickSettings = cntrl::SwitchToSettingsScreen, onClickScoreboard = cntrl::SwitchToScoreboardScreen)
			GameStarter(230f, 300f, onSwipe = cntrl::startGame, swipeThreshold = 300f, maxIconScale = 4f)
		}

		GameModeSelector(availableModes = listOf("free", "challenge"), currMode = 0)
	}
}


@Composable
fun ScoreboardScreen(cntrl: ScoreboardController, isLandscape: Boolean)
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

		GoBackButton(cntrl.navigationCntrl)
	}
}


@Composable
fun SettingsScreen(cntrl: SettingsController, isLandscape: Boolean)
{
	Column()
	{
		ScreenTitle(title = stringResource(id = R.string.settings_screen_title))
		// TODO: Add implementation...

		GoBackButton(cntrl.navigationCntrl)
	}
}


@Composable
fun GameScreen(cntrl: GameController, isLandscape: Boolean)
{
	Column()
	{
		ScreenTitle(title = "Game screen!!!")
		// TODO: Add implementation...
	}
}