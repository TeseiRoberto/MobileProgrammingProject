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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.tecnoscimmia.nine.R
import com.tecnoscimmia.nine.ui.theme.NineButtonStyle
import com.tecnoscimmia.nine.view.widgets.GameControlPanel
import com.tecnoscimmia.nine.view.widgets.GameInfoPanel
import com.tecnoscimmia.nine.view.widgets.GameModeSelector
import com.tecnoscimmia.nine.view.widgets.GameStarter
import com.tecnoscimmia.nine.view.widgets.GoBackButton
import com.tecnoscimmia.nine.view.widgets.InputRow
import com.tecnoscimmia.nine.view.widgets.MenuPanelLandscape
import com.tecnoscimmia.nine.view.widgets.MenuPanelPortrait
import com.tecnoscimmia.nine.view.widgets.PauseMenu
import com.tecnoscimmia.nine.view.widgets.Scoreboard
import com.tecnoscimmia.nine.view.widgets.ScreenTitle
import com.tecnoscimmia.nine.view.widgets.SettingRow
import com.tecnoscimmia.nine.viewModel.GameViewModel
import com.tecnoscimmia.nine.viewModel.MenuViewModel
import com.tecnoscimmia.nine.viewModel.ScoreboardViewModel
import com.tecnoscimmia.nine.viewModel.SettingsViewModel


/*
 * This file contains the definitions of all the screens that the application is composed of
 */


// Enumeration of all the screens that makes the application
enum class NineScreen { MainMenu, Scoreboard, Settings, Game, Tutorial }


@Composable
fun MenuScreen(navigationCntrl : NavHostController, menuVM: MenuViewModel, isLandscape: Boolean)
{
	Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween)
	{
		if(isLandscape)
		{
			MenuPanelLandscape(onClickSettings = { navigationCntrl.navigate(route = NineScreen.Settings.name) },
								onClickScoreboard = { navigationCntrl.navigate(route = NineScreen.Scoreboard.name) })

			GameStarter(250.dp, 180.dp, onSwipe = { navigationCntrl.navigate(route = NineScreen.Game.name) }, swipeThreshold = 300f, maxIconScale = 4f)
		} else {

			MenuPanelPortrait(onClickSettings = { navigationCntrl.navigate(route = NineScreen.Settings.name) },
				onClickScoreboard = { navigationCntrl.navigate(route = NineScreen.Scoreboard.name) })

			GameStarter(230.dp, 300.dp, onSwipe = { navigationCntrl.navigate(route = NineScreen.Game.name) }, swipeThreshold = 300f, maxIconScale = 4f)
		}

		GameModeSelector(menuVM.getGameMode(), onLeftArrowClick = menuVM::setPrevGameMode, onRightArrowClick = menuVM::setNextGameMode)
	}
}


@Composable
fun ScoreboardScreen(navigationCntrl: NavHostController, scoreboardVM: ScoreboardViewModel, isLandscape: Boolean)
{
	val scoreboardData = scoreboardVM.getScoreboardData().observeAsState(initial = listOf())

	Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween)
	{
		ScreenTitle(title = stringResource(id = R.string.scoreboard_screen_title))

		if(isLandscape)
			Scoreboard(data = scoreboardData.value, widthOccupation = 0.8f, heightOccupation = 0.6f)
		else
			Scoreboard(data = scoreboardData.value, widthOccupation = 1f, heightOccupation = 0.9f)

		GoBackButton(navigationCntrl)
	}
}


@Composable
fun SettingsScreen(navigationCntrl: NavHostController, settingsVM: SettingsViewModel, isLandscape: Boolean)
{
	// If device is in landscape mode then settings are positioned into 2 adjacent columns, otherwise
	// they are all in the same column. We define the settings UI here as 2 lambdas to avoid code repetition.
	val settingSet1 = @Composable {
		SettingRow(settingName = stringResource(id = R.string.settings_theme),				// Setting to change theme
			availableValues = settingsVM.getAvailableThemes(),
			currValue = settingsVM.getTheme(),
			onSettingChange = settingsVM::setTheme
		)

		SettingRow(settingName = stringResource(id = R.string.settings_symbols_set),		// Setting to change the symbols set
			availableValues = settingsVM.getAvailableSymbolsSets(),
			currValue = settingsVM.getSymbolsSet(),
			onSettingChange = settingsVM::setSymbolsSet
		)
	}

	val settingSet2 = @Composable {
		SettingRow(settingName = stringResource(id = R.string.settings_keyboard_layout),	// Setting to change keyboard layout
			availableValues = settingsVM.getAvailableKeyboardLayouts(),
			currValue = settingsVM.getKeyboardLayout(),
			onSettingChange = settingsVM::setKeyboardLayout
		)

		SettingRow(settingName = stringResource(id = R.string.settings_debug_mode),	// Setting to change the debug mode
			availableValues = settingsVM.getAvailableDebugModes(),
			currValue = settingsVM.getDebugMode(),
			onSettingChange = settingsVM::setDebugMode
		)
	}

	Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween)
	{
		ScreenTitle(title = stringResource(id = R.string.settings_screen_title))

		if(isLandscape)										// If device is in landscape mode then put settingsSets in 2 adjacent columns
		{
			Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically)
			{
				Column(modifier = Modifier.fillMaxWidth().weight(0.5f), horizontalAlignment = Alignment.CenterHorizontally,
					verticalArrangement = Arrangement.SpaceBetween, content = { settingSet1() } )

				Column(modifier = Modifier.fillMaxWidth().weight(0.5f), horizontalAlignment = Alignment.CenterHorizontally,
					verticalArrangement = Arrangement.SpaceBetween, content = { settingSet2() } )
			}
		} else {											// Otherwise put one on top of the other
			settingSet1()
			settingSet2()
		}

		Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
			horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically)
		{
			// Apply changes button
			Button(modifier = Modifier.weight(0.5f).padding(12.dp), enabled = settingsVM.hasSomethingChanged(),
				shape = RoundedCornerShape(NineButtonStyle.cornerRadius),
				onClick = settingsVM::saveChangesToSettings,
				content = { Text(text = stringResource(R.string.settings_message_apply_changes)) }
			)

			// Start tutorial button
			Button(modifier = Modifier.weight(0.5f).padding(12.dp), shape = RoundedCornerShape(NineButtonStyle.cornerRadius),
				onClick = { /* TODO: Uncomment when tutorial screen is implemented!!! navigationCntrl.navigate(route = NineScreen.Tutorial.name)*/ },
				content = { Text(text = stringResource(R.string.settings_message_start_tutorial)) }
			)
		}

		// Go back button
		GoBackButton(navigationCntrl)
	}
}


@Composable
fun GameScreen(navigationCntrl: NavHostController, gameVM: GameViewModel, isLandscape: Boolean)
{
	Column(modifier = Modifier.fillMaxSize().zIndex(1f), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween)
	{
		GameInfoPanel(isLandscape = isLandscape, time = gameVM.getTime(), attempts = gameVM.getAttemptsNum())
		InputRow(userInput = gameVM.getUserInput(), currIndex = gameVM.getSelectedIndex(), differencesStr = gameVM.getDifferenceString())

		// If the app is running in debug mode then we can show the secret key
		if(gameVM.isDebugModeActive())
			Text(text = "secret key is: ${gameVM.getSecretKey()}")

		GameControlPanel(isLandscape = isLandscape, gameVM = gameVM)
	}

	// If the match is paused then we draw the pause menu on top of the game screen
	if(gameVM.isMatchPaused())
	{
		PauseMenu(navigationCntrl = navigationCntrl, gameVM = gameVM)
	}

}


