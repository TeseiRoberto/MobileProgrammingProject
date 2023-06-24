package com.tecnoscimmia.nine

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tecnoscimmia.nine.ui.theme.NineTheme
import com.tecnoscimmia.nine.view.GameScreen
import com.tecnoscimmia.nine.view.MenuScreen
import com.tecnoscimmia.nine.view.NineScreen
import com.tecnoscimmia.nine.view.OptionsScreen
import com.tecnoscimmia.nine.view.ScoreboardScreen


@Preview
@Composable
fun Test()
{
	NineTheme {
		Surface(modifier = Modifier.background(Color.White).fillMaxSize(), content = { MenuScreen(false) } )
		//Surface(modifier = Modifier.background(Color.White).fillMaxSize(), content = { ScoreboardScreen(false) } )
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
				val navController = rememberNavController()

				// Set navigation host so that we can navigate between the different screens of the app
				NavHost(navController = navController, startDestination = NineScreen.MainMenu.name)
				{
					composable(NineScreen.MainMenu.name)	{ MenuScreen(isLandscape = isLandscape)}
					composable(NineScreen.Scoreboard.name) 	{ ScoreboardScreen(isLandscape = isLandscape) }
					composable(NineScreen.Options.name)		{ OptionsScreen(isLandscape = isLandscape) }
					composable(NineScreen.Game.name)		{ GameScreen(isLandscape = isLandscape) }
				}
			}
		}
	}

}
