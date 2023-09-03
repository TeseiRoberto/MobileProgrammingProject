package com.tecnoscimmia.nine.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.tecnoscimmia.nine.model.GameSettings


@Composable
fun NineTheme(appTheme: GameSettings.ThemeSetting, darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit)
{
	val colorScheme = when(appTheme)
	{
		GameSettings.ThemeSetting.DARK_THEME -> NineColors.darkTheme
		else -> NineColors.lightTheme
	}

	val view = LocalView.current
	if (!view.isInEditMode) {
		SideEffect {
			val window = (view.context as Activity).window
			window.statusBarColor = colorScheme.primary.toArgb()
			WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
		}
	}

	// Calculate the padding values that will be used in the application accordingly to the screen size
	NinePaddingStyle.calculatePaddingValues(
		screenWidth = LocalConfiguration.current.screenWidthDp.dp,
		screenHeight = LocalConfiguration.current.screenWidthDp.dp)

	MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}