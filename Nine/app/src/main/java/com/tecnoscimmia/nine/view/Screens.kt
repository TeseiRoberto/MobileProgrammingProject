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
import com.tecnoscimmia.nine.model.MatchResult

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
	// TODO: Some test data that needs to be removed after tests!
	val testData = listOf(
		MatchResult(time = "21:00", date = "18/08/2015", gameMode = "Free"),
		MatchResult(time = "1:50", date = "12/04/2023", gameMode = "Free"),
		MatchResult(time = "2:00", date = "31/01/2044", gameMode = "Challenge"),
		MatchResult(time = "1:00", date = "1/11/202020", gameMode = "Free"),
		MatchResult(time = "08:59:12", date = "7/33/1245", gameMode = "Free"),
		MatchResult(time = "08:59:12", date = "7/33/1245", gameMode = "Free"),
		MatchResult(time = "08:59:12", date = "7/33/1245", gameMode = "Challenge"),
		MatchResult(time = "18:01", date = "7/33/1245", gameMode = "Free"),
		MatchResult(time = "08:59:12", date = "7/33/1245", gameMode = "Free"),
		MatchResult(time = "08:59:12", date = "7/33/1245", gameMode = "Challenge"),
		MatchResult(time = "08:59:12", date = "7/33/1245", gameMode = "Free"),
		MatchResult(time = "08:59:12", date = "7/33/1245", gameMode = "Free"),
		MatchResult(time = "08:59:12", date = "7/33/1245", gameMode = "Free"),
		MatchResult(time = "08:59:12", date = "7/33/1245", gameMode = "Challenge"),
		MatchResult(time = "08:59:12", date = "7/33/1245", gameMode = "Free"),
		MatchResult(time = "08:59:12", date = "7/33/1245", gameMode = "Free"),
		MatchResult(time = "08:59:12", date = "7/33/1245", gameMode = "Challenge"),
		)

	Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween)
	{
		ScreenTitle(title = stringResource(id = R.string.scoreboard_screen_title))

		if(isLandscape)
			Scoreboard(data = testData, widthOccupation = 0.8f, heightOccupation = 0.6f)
		else
			Scoreboard(data = testData, widthOccupation = 1f, heightOccupation = 0.9f)

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

		if(isLandscape)
			// TODO
			else
				// TODO

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