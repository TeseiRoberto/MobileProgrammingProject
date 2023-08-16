package com.tecnoscimmia.nine

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tecnoscimmia.nine.model.GameSettings
import com.tecnoscimmia.nine.ui.theme.NineTheme
import com.tecnoscimmia.nine.view.GameScreen
import com.tecnoscimmia.nine.view.MenuScreen
import com.tecnoscimmia.nine.view.NineScreen
import com.tecnoscimmia.nine.view.ScoreboardScreen
import com.tecnoscimmia.nine.view.SettingsScreen
import com.tecnoscimmia.nine.viewModel.GameViewModel
import com.tecnoscimmia.nine.viewModel.MenuViewModel
import com.tecnoscimmia.nine.viewModel.ScoreboardViewModel
import com.tecnoscimmia.nine.viewModel.SettingsViewModel


/*
 * What is happening with this code?
 * Briefly...
 *
 * The application is composed of 4 states: menu, settings, scoreboard and game.
 * Each state has a composable function called *Screen (it represents the view) and a view-model
 * class that implements the logic for that particular state and holds and controls the model classes.
 *
 */

class MainActivity : ComponentActivity()
{
	companion object
	{
		var appTheme = mutableStateOf(GameSettings.ThemeSetting.LIGHT_THEME)
	}


	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)

		setContent {

			appTheme.value = GameSettings.getInstance(appCntxt = applicationContext).getTheme()
			val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

			NineTheme(appTheme = appTheme.value)
			{
				val navigationCntrl = rememberNavController()		// Instantiate the navigation controller

				Surface(modifier = Modifier.fillMaxSize())
				{
					// Set navigation host for the navigation controller so that we can navigate between
					// the different screens of the app (we are actually building the navigation graph)
					NavHost(navController = navigationCntrl, startDestination = NineScreen.MainMenu.name)
					{
						composable(NineScreen.MainMenu.name)
						{
							val menuViewModel: MenuViewModel = viewModel(factory = MenuViewModel.Factory)
							MenuScreen(navigationCntrl = navigationCntrl, menuVM = menuViewModel, isLandscape = isLandscape)
						}

						composable(NineScreen.Scoreboard.name)
						{
							val scoreboardVM: ScoreboardViewModel = viewModel(factory = ScoreboardViewModel.Factory)
							ScoreboardScreen(navigationCntrl = navigationCntrl, scoreboardVM = scoreboardVM, isLandscape = isLandscape)
						}

						composable(NineScreen.Settings.name)
						{
							val settingsVM: SettingsViewModel = viewModel(factory = SettingsViewModel.Factory)
							SettingsScreen(navigationCntrl = navigationCntrl, settingsVM = settingsVM, isLandscape = isLandscape)
						}

						composable(NineScreen.Game.name)
						{
							val gameVM: GameViewModel = viewModel(factory = GameViewModel.Factory)
							GameScreen(navigationCntrl = navigationCntrl, gameVM = gameVM, isLandscape = isLandscape)
						}
					}
				}
			}
		}
	}

}