package com.tecnoscimmia.nine.view

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import com.tecnoscimmia.nine.R
import com.tecnoscimmia.nine.controller.MenuController
import com.tecnoscimmia.nine.controller.ScoreboardController
import com.tecnoscimmia.nine.controller.SettingsController
import com.tecnoscimmia.nine.model.GameSettings

/*
 * This file contains the definitions of all the screens that the application is composed of
 */


// Enumeration of all the screens that makes the application
enum class NineScreen { MainMenu, Scoreboard, Settings, Game }

@Composable
fun MenuScreen(cntrl: MenuController, isLandscape: Boolean)
{
	Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween)
	{
		if(isLandscape)
		{
			MenuPanelLandscape(onClickSettings = cntrl::switchToSettingsScreen, onClickScoreboard = cntrl::switchToScoreboardScreen)
			GameStarter(250f, 200f, onSwipe = cntrl::startNewGame, swipeThreshold = 300f, maxIconScale = 4f)
		} else {
			MenuPanelPortrait(onClickSettings = cntrl::switchToSettingsScreen, onClickScoreboard = cntrl::switchToScoreboardScreen)
			GameStarter(230f, 300f, onSwipe = cntrl::startNewGame, swipeThreshold = 300f, maxIconScale = 4f)
		}


		GameModeSelector(cntrl = cntrl)
	}
}


@Composable
fun ScoreboardScreen(cntrl: ScoreboardController, isLandscape: Boolean)
{
	Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween)
	{
		ScreenTitle(title = stringResource(id = R.string.scoreboard_screen_title))

		if(isLandscape)
			Scoreboard(data = cntrl.loadScoreboardData(), widthOccupation = 0.8f, heightOccupation = 0.6f)
		else
			Scoreboard(data = cntrl.loadScoreboardData(), widthOccupation = 1f, heightOccupation = 0.9f)

		GoBackButton(cntrl.navigationCntrl)
	}
}


@Composable
fun SettingsScreen(cntrl: SettingsController, isLandscape: Boolean)
{
	/* If device is in landscape mode then settings are positioned into 2 adjacent columns, otherwise
	they are all in the same column. We define the settings here as 2 lambdas to avoid code repetition. */
	val settingSet1 = @Composable {
		SettingRow(settingName = stringResource(id = R.string.setting_language),
			availableValues = cntrl.settings.getAvailableLanguages(),
			currentValue = cntrl.settings.language.value,
			onSettingChange = cntrl::setLanguage
		)

		SettingRow(settingName = stringResource(id = R.string.setting_theme),
			availableValues = cntrl.settings.getAvailableThemes(),
			currentValue = cntrl.settings.theme.value,
			onSettingChange = cntrl::setTheme
		)
	}

	val settingSet2 = @Composable {
		SettingRow(settingName = stringResource(id = R.string.setting_keyboard_layout),
			availableValues = cntrl.settings.getAvailableKeyboardLayouts(),
			currentValue = cntrl.settings.keyboardLayout.value,
			onSettingChange = cntrl::setKeyboardLayout
		)
	}

	Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween)
	{
		ScreenTitle(title = stringResource(id = R.string.settings_screen_title))

		if(isLandscape)									// If landscape mode then put settingsSets in 2 adjacent columns
		{
			Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically)
			{
				Column(modifier = Modifier.fillMaxWidth().weight(0.5f), horizontalAlignment = Alignment.CenterHorizontally,
					verticalArrangement = Arrangement.SpaceBetween, content = { settingSet1() } )

				Column(modifier = Modifier.fillMaxWidth().weight(0.5f), horizontalAlignment = Alignment.CenterHorizontally,
					verticalArrangement = Arrangement.SpaceBetween, content = { settingSet2() } )
			}
		} else {										// Otherwise put one on top of the other
			settingSet1()
			settingSet2()
		}

		GoBackButton(cntrl.navigationCntrl)
	}
}

