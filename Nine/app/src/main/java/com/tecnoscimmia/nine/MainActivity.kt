package com.tecnoscimmia.nine

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tecnoscimmia.nine.controller.GameViewModel
import com.tecnoscimmia.nine.controller.MenuViewModel
import com.tecnoscimmia.nine.controller.ScoreboardViewModel
import com.tecnoscimmia.nine.controller.SettingsViewModel
import com.tecnoscimmia.nine.ui.theme.NineTheme
import com.tecnoscimmia.nine.view.GameScreen
import com.tecnoscimmia.nine.view.MenuScreen
import com.tecnoscimmia.nine.view.NineScreen
import com.tecnoscimmia.nine.view.ScoreboardScreen
import com.tecnoscimmia.nine.view.SettingsScreen


/*
 * WHAT THE FUCK IT'S HAPPENING IN THIS CODE???
 *
 * Briefly...
 *
 * The application is composed of 4 states: menu, settings, scoreboard and game.
 * Each state has a composable function called *Screen (it represents the view) and a view-model
 * class that implements the logic for that particular state and holds and controls the model classes.
 *
 * All this mess to implement the MVVM architecture pattern.  :_D
 *
 */

class MainActivity : ComponentActivity()
{

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)

		setContent {
			val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

			// Instantiate navigation controller
			val navigationCntrl = rememberNavController()

			NineTheme()
			{
				// Set navigation host for the navigation controller so that we can navigate between
				// the different screens of the app (we are actually building the navigation graph)
				NavHost(navController = navigationCntrl, startDestination = NineScreen.MainMenu.name)
				{
					composable(NineScreen.MainMenu.name)
					{
						val menuViewModel: MenuViewModel by viewModels { MenuViewModel.Factory }
						MenuScreen(navigationCntrl = navigationCntrl, menuVM = menuViewModel, isLandscape = isLandscape)
					}

					composable(NineScreen.Scoreboard.name)
					{
						val scoreboardVM: ScoreboardViewModel by viewModels() { ScoreboardViewModel.Factory }
						ScoreboardScreen(navigationCntrl = navigationCntrl, scoreboardVM = scoreboardVM, isLandscape = isLandscape)
					}

					composable(NineScreen.Settings.name)
					{
						val settingsVM: SettingsViewModel by viewModels() { SettingsViewModel.Factory }
						SettingsScreen(navigationCntrl = navigationCntrl, settingsVM = settingsVM, isLandscape = isLandscape)
					}

					composable(NineScreen.Game.name)
					{
						val gameVM: GameViewModel by viewModels() { GameViewModel.Factory }
						GameScreen(navigationCntrl = navigationCntrl, gameVM = gameVM, isLandscape = isLandscape)
					}
				}
			}
		}
	}

}