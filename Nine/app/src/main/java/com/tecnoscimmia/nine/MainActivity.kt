package com.tecnoscimmia.nine

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tecnoscimmia.nine.controller.GameController
import com.tecnoscimmia.nine.controller.MainController
import com.tecnoscimmia.nine.controller.ScoreboardController
import com.tecnoscimmia.nine.controller.SettingsController
import com.tecnoscimmia.nine.ui.theme.NineTheme
import com.tecnoscimmia.nine.view.GameScreen
import com.tecnoscimmia.nine.view.MenuScreen
import com.tecnoscimmia.nine.view.NineScreen
import com.tecnoscimmia.nine.view.ScoreboardScreen
import com.tecnoscimmia.nine.view.SettingsScreen


@Preview
@Composable
fun Test() // TODO: Remove this preview function
{
	val mainCntrl = MainController(rememberNavController())

	NineTheme {
		val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
		mainCntrl.navigationCntrl = rememberNavController()

		// Set navigation host so that we can navigate between the different screens of the app
		NavHost(navController = mainCntrl.navigationCntrl, startDestination = NineScreen.MainMenu.name)
		{
			composable(NineScreen.MainMenu.name)
			{
				MenuScreen(cntrl = mainCntrl, isLandscape = isLandscape)
			}

			composable(NineScreen.Scoreboard.name)
			{
				val scoreboardCntrl = ScoreboardController(mainCntrl.navigationCntrl)
				ScoreboardScreen(cntrl = scoreboardCntrl, isLandscape = isLandscape)
			}

			composable(NineScreen.Settings.name)
			{
				val settingsCntrl = SettingsController(mainCntrl.navigationCntrl)
				SettingsScreen(cntrl = settingsCntrl, isLandscape = isLandscape)
			}

			composable(NineScreen.Game.name)
			{
				val gameCntrl = GameController(mainCntrl.navigationCntrl)
				GameScreen(cntrl = gameCntrl, isLandscape = isLandscape)
			}
		}

		Surface(modifier = Modifier.fillMaxSize(), content = { MenuScreen(cntrl = mainCntrl, isLandscape = isLandscape) } )
	}
}

/*
 * WHAT THE FUCK IT'S HAPPENING IN THIS CODE???
 *
 * Briefly...
 *
 * The application is composed of 4 states: menu, settings, scoreboard and game.
 * Each state has a composable function called *Screen (it represents the view) and a controller
 * that implements the logic for that particular state and controls the model classes.
 *
 * All this mess to implement the MVC architecture pattern.  :_D
 *
 */


class MainActivity : ComponentActivity()
{

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)

		setContent {
			val mainCntrl = MainController(rememberNavController())		// Instantiate main controller

			NineTheme {

				val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

				// Set navigation host so that we can navigate between the different screens of the app
				NavHost(navController = mainCntrl.navigationCntrl, startDestination = NineScreen.MainMenu.name)
				{
					composable(NineScreen.MainMenu.name)
					{
						MenuScreen(cntrl = mainCntrl, isLandscape = isLandscape)
					}

					composable(NineScreen.Scoreboard.name)
					{
						val scoreboardCntrl = ScoreboardController(mainCntrl.navigationCntrl)
						ScoreboardScreen(cntrl = scoreboardCntrl, isLandscape = isLandscape)
					}

					composable(NineScreen.Settings.name)
					{
						val settingsCntrl = SettingsController(mainCntrl.navigationCntrl)
						SettingsScreen(cntrl = settingsCntrl, isLandscape = isLandscape)
					}

					composable(NineScreen.Game.name)
					{
						val gameCntrl = GameController(mainCntrl.navigationCntrl)
						GameScreen(cntrl = gameCntrl, isLandscape = isLandscape)
					}
				}
			}
		}
	}

}
