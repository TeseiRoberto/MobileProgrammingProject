package com.tecnoscimmia.nine.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tecnoscimmia.nine.R
import com.tecnoscimmia.nine.controller.GameController
import com.tecnoscimmia.nine.controller.MenuController
import com.tecnoscimmia.nine.controller.ScoreboardController
import com.tecnoscimmia.nine.controller.SettingsController
import com.tecnoscimmia.nine.ui.theme.NineButtonStyle
import java.util.Calendar

/*
 * This file contains the definitions of all the screens that the application is composed of
 */


// Enumeration of all the screens that makes the application
enum class NineScreen { MainMenu, Scoreboard, Settings, Game, Tutorial }

@Composable
fun MenuScreen(cntrl: MenuController, isLandscape: Boolean)
{
	val selectedGameMode = rememberSaveable { mutableStateOf(0) }

	Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween)
	{
		if(isLandscape)
		{
			MenuPanelLandscape(onClickSettings = cntrl::switchToSettingsScreen, onClickScoreboard = cntrl::switchToScoreboardScreen)
			GameStarter(250.dp, 200.dp, onSwipe = { cntrl.startNewGame(selectedGameMode.value) }, swipeThreshold = 300f, maxIconScale = 4f)
		} else {
			MenuPanelPortrait(onClickSettings = cntrl::switchToSettingsScreen, onClickScoreboard = cntrl::switchToScoreboardScreen)
			GameStarter(230.dp, 300.dp, onSwipe = { cntrl.startNewGame(selectedGameMode.value) }, swipeThreshold = 300f, maxIconScale = 4f)
		}

		GameModeSelector(currGameMode = cntrl.settings.getAvailableGameModes()[selectedGameMode.value],
			onLeftArrowClick = {
				if(selectedGameMode.value > 0)
					selectedGameMode.value--
			},
			onRightArrowClick = {
				if(selectedGameMode.value < (cntrl.settings.getAvailableGameModes().size - 1))
					selectedGameMode.value++
			}
		)
	}
}


@Composable
fun ScoreboardScreen(cntrl: ScoreboardController, isLandscape: Boolean)
{
	val scoreboardData = cntrl.getScoreboardData().observeAsState().value

	Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween)
	{
		ScreenTitle(title = stringResource(id = R.string.scoreboard_screen_title))

		if(isLandscape)
			Scoreboard(data = scoreboardData, widthOccupation = 0.8f, heightOccupation = 0.6f)
		else
			Scoreboard(data = scoreboardData, widthOccupation = 1f, heightOccupation = 0.9f)

		GoBackButton(cntrl.navigationCntrl)
	}
}


@Composable
fun SettingsScreen(cntrl: SettingsController, isLandscape: Boolean)
{
	val currTheme 			= rememberSaveable { mutableStateOf(cntrl.getTheme()) }
	val currKeyboardLayout 	= rememberSaveable { mutableStateOf(cntrl.getKeyboardLayout()) }

	// When a setting is changed this becomes true and enables the apply changes button
	val hasSomethingChanged = rememberSaveable { mutableStateOf(false) }

	// If device is in landscape mode then settings are positioned into 2 adjacent columns, otherwise
	// they are all in the same column. We define the settings UI here as 2 lambdas to avoid code repetition.
	val settingSet1 = @Composable {
		SettingRow(settingName = stringResource(id = R.string.settings_theme),				// Setting to change theme
			availableValues = cntrl.availableThemes,
			currValue = currTheme.value,
			onSettingChange = { newTheme ->
				currTheme.value = newTheme
				hasSomethingChanged.value = true
			}
		)
	}

	val settingSet2 = @Composable {
		SettingRow(settingName = stringResource(id = R.string.settings_keyboard_layout),	// Setting to change keyboard layout
			availableValues = cntrl.availableKeyboardLayouts,
			currValue = currKeyboardLayout.value,
			onSettingChange = { newLayout ->
				currKeyboardLayout.value = newLayout
				hasSomethingChanged.value = true
			}
		)
	}

	Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween)
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

		Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
			horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically)
		{
			// Apply changes button
			Button(modifier = Modifier.weight(0.5f).padding(12.dp), enabled = hasSomethingChanged.value,
				shape = RoundedCornerShape(NineButtonStyle.cornerRadius),
				onClick = {
					hasSomethingChanged.value = false
					cntrl.applyChangesToSettings(newTheme = currTheme.value, newKeyboardLayout = currKeyboardLayout.value)
				},
				content = { Text(text = stringResource(R.string.settings_message_apply_changes)) }
			)

			// Start tutorial button
			Button(modifier = Modifier.weight(0.5f).padding(12.dp), shape = RoundedCornerShape(NineButtonStyle.cornerRadius),
				onClick = { cntrl.startTutorial() },
				content = { Text(text = stringResource(R.string.settings_message_start_tutorial)) }
			)
		}

		// Go back button
		GoBackButton(cntrl.navigationCntrl)
	}
}


@Composable
fun GameScreen(cntrl: GameController, isLandscape: Boolean)
{
	val symbolList = listOf('1', '2', '3', '4', '5', '6', '7', '8', '9')

	Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween)
	{
		Keyboard(symbolSet = symbolList)

		GoBackButton(cntrl.navCntrl) // TODO: Remove this button
	}
}

