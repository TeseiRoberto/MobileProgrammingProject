package com.tecnoscimmia.nine

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tecnoscimmia.nine.ui.theme.NineTheme
import com.tecnoscimmia.nine.view.GameScreen
import com.tecnoscimmia.nine.view.MenuScreen
import com.tecnoscimmia.nine.view.NineScreen
import com.tecnoscimmia.nine.view.ScoreboardScreen
import com.tecnoscimmia.nine.view.SettingsScreen


@Preview
@Composable
fun Test()
{
	NineTheme {
		val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
		val navigationCntrl = rememberNavController()

		// Set navigation host so that we can navigate between the different screens of the app
		NavHost(navController = navigationCntrl, startDestination = NineScreen.MainMenu.name)
		{
			composable(NineScreen.MainMenu.name)	{ MenuScreen(isLandscape = isLandscape, navigationCntrl = navigationCntrl) }
			composable(NineScreen.Scoreboard.name) 	{ ScoreboardScreen(isLandscape = isLandscape, navigationCntrl = navigationCntrl) }
			composable(NineScreen.Settings.name)	{ SettingsScreen(isLandscape = isLandscape, navigationCntrl = navigationCntrl) }
			composable(NineScreen.Game.name)		{ GameScreen(isLandscape = isLandscape, navigationCntrl = navigationCntrl) }
		}

		navigationCntrl.navigate(route = NineScreen.MainMenu.name)
	}
}


class MainActivity : ComponentActivity()
{

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)

		setContent {
			NineTheme {

				val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
				val navigationCntrl = rememberNavController()

				// Set navigation host so that we can navigate between the different screens of the app
				NavHost(navController = navigationCntrl, startDestination = NineScreen.MainMenu.name)
				{
					composable(NineScreen.MainMenu.name)	{ MenuScreen(isLandscape = isLandscape, navigationCntrl = navigationCntrl) }
					composable(NineScreen.Scoreboard.name) 	{ ScoreboardScreen(isLandscape = isLandscape, navigationCntrl = navigationCntrl) }
					composable(NineScreen.Settings.name)	{ SettingsScreen(isLandscape = isLandscape, navigationCntrl = navigationCntrl) }
					composable(NineScreen.Game.name)		{ GameScreen(isLandscape = isLandscape, navigationCntrl = navigationCntrl) }
				}
			}
		}
	}

}
