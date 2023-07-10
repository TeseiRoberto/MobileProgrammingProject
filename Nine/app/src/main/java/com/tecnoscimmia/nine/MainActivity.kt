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
import com.tecnoscimmia.nine.controller.MenuController
import com.tecnoscimmia.nine.controller.ScoreboardController
import com.tecnoscimmia.nine.controller.SettingsController
import com.tecnoscimmia.nine.model.GameSettings
import com.tecnoscimmia.nine.model.MatchResultDb
import com.tecnoscimmia.nine.model.MatchResultRepository
import com.tecnoscimmia.nine.ui.theme.NineTheme
import com.tecnoscimmia.nine.view.MenuScreen
import com.tecnoscimmia.nine.view.NineScreen
import com.tecnoscimmia.nine.view.ScoreboardScreen
import com.tecnoscimmia.nine.view.SettingsScreen


@Preview
@Composable
fun Test() // TODO: Remove this preview function
{
	val activity = MainActivity()
	val gameSettings = GameSettings.getInstance(activity = activity)
	val menuCntrl = MenuController(navigationCntrl = rememberNavController(), settings = gameSettings)

	NineTheme()
	{
		//Surface(modifier = Modifier.fillMaxSize(), content = { MenuScreen(cntrl = menuCntrl, isLandscape = false) } )
		//Surface(modifier = Modifier.fillMaxSize(), content = { SettingsScreen(cntrl = menuCntrl, isLandscape = false) } )
		//Surface(modifier = Modifier.fillMaxSize(), content = { SettingsScreen(cntrl = menuCntrl, isLandscape = false) } )

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

// Stack overflow using flow https://stackoverflow.com/questions/73892063/how-to-show-our-room-database-content-in-a-lazycolumn-in-jetpack-compose
// Tutorial on coroutine result https://www.youtube.com/watch?v=pqQkl9BInAw&t=128s

class MainActivity : ComponentActivity()
{

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)

		// TODO: This looks really awful, needs to be fixed
		val activity = this

		// Load game settings from preferences file
		val gameSettings = GameSettings.getInstance(activity = this)

		// Initialize the database
		val db = MatchResultDb.getInstance(cntxt = applicationContext)

		setContent {
			val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

			// Instantiate the menu controller
			val menuCntrl = MenuController(navigationCntrl = rememberNavController(), settings = gameSettings)

			NineTheme()
			{
				// Set navigation host for the navigation controller so that we can navigate between
				// the different screens of the app (we are actually building the navigation graph)
				NavHost(navController = menuCntrl.navigationCntrl,
					startDestination = /*if(gameSettings.needToShowTutorial() == true) NineScreen.Tutorial.name else*/ NineScreen.MainMenu.name)
				{
					composable(NineScreen.MainMenu.name)
					{
						MenuScreen(cntrl = menuCntrl, isLandscape = isLandscape)
					}

					composable(NineScreen.Scoreboard.name)
					{
						val scoreboardCntrl = ScoreboardController(menuCntrl.navigationCntrl, resultRepo = MatchResultRepository(db.matchResultDao()))
						ScoreboardScreen(cntrl = scoreboardCntrl, isLandscape = isLandscape)
					}

					composable(NineScreen.Settings.name)
					{
						val settingsCntrl = SettingsController(menuCntrl.navigationCntrl, settings = menuCntrl.settings, activity = activity)
						SettingsScreen(cntrl = settingsCntrl, isLandscape = isLandscape)
					}

					/*composable(NineScreen.Game.name)
					{
						val gameCntrl = GameController(mainCntrl.navigationCntrl)
						GameScreen(cntrl = gameCntrl, isLandscape = isLandscape)
					}*/

					/*composable(NineScreen.Tutorial.name)
					{
						val gameCntrl = GameController(mainCntrl.navigationCntrl)
						TutorialScreen(cntrl = gameCntrl, isLandscape = isLandscape)
					}*/
				}
			}
		}
	}

}