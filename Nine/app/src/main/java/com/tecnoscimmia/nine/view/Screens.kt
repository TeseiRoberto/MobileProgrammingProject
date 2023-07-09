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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tecnoscimmia.nine.R
import com.tecnoscimmia.nine.controller.MenuController
import com.tecnoscimmia.nine.controller.ScoreboardController
import com.tecnoscimmia.nine.controller.SettingsController
import com.tecnoscimmia.nine.ui.theme.NineButtonStyle

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
			GameStarter(250f, 200f, onSwipe = { cntrl.startNewGame(selectedGameMode.value) }, swipeThreshold = 300f, maxIconScale = 4f)
		} else {
			MenuPanelPortrait(onClickSettings = cntrl::switchToSettingsScreen, onClickScoreboard = cntrl::switchToScoreboardScreen)
			GameStarter(230f, 300f, onSwipe = { cntrl.startNewGame(selectedGameMode.value) }, swipeThreshold = 300f, maxIconScale = 4f)
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

/* TODO: I need to make this work, currently the database is capable of storing data but we need a way to retrieve those data and display it...
@Composable
fun ScoreboardScreen(cntrl: ScoreboardController, isLandscape: Boolean)
{
	var isDataLoaded = remember { mutableStateOf(false) }		// This will become true when the scoreboard data will be ready to be displayed

	Log.v("MAGIC_LOGGER", "Recomposing Scoreboard screen\nisDataLoaded = ${isDataLoaded.value}, scoreboardData = ${cntrl.scoreboardData}")

	// Call the loadScoreboardData, this method is a coroutine that loads data from the db and executes the onCompletion callback
	// when data are finally available
	cntrl.loadScoreboardData(onCompletion = { isDataLoaded.value = true } )

	Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween)
	{
		ScreenTitle(title = stringResource(id = R.string.scoreboard_screen_title))

		if(isDataLoaded.value == false)											// If the scoreboard data is not loaded yet
		{
			LoadingIcon()														// We display the loading icon
		} else if(isDataLoaded.value == true && cntrl.scoreboardData != null)	// Otherwise we display the scoreboard with the loaded data (if the data is available)
		{
			if(isLandscape)
				Scoreboard(data = cntrl.scoreboardData!!, widthOccupation = 0.8f, heightOccupation = 0.6f)
			else
				Scoreboard(data = cntrl.scoreboardData!!, widthOccupation = 1f, heightOccupation = 0.9f)
		}
	}
}*/


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

/*
@Composable
fun GameScreen(isLandscape: Boolean)
{
	Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween)
	{
		// TODO: Add implementation
	}
}*/

