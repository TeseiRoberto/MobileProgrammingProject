package com.tecnoscimmia.nine.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

/*
 * This file contains definitions for all the colors used in the application and all it's widgets
 */

class NineColors
{
	companion object
	{
		val Purple80 		= Color(0xFFD0BCFF)	// TODO: Remove this default color
		val PurpleGrey80 	= Color(0xFFCCC2DC)	// TODO: Remove this default color
		val Pink80 			= Color(0xFFEFB8C8)	// TODO: Remove this default color

		val Purple40 		= Color(0xFF6650a4) 	// TODO: Remove this default color
		val PurpleGrey40 	= Color(0xFF625b71)	// TODO: Remove this default color
		val Pink40 			= Color(0xFF7D5260)	// TODO: Remove this default color

		val gold			= Color(0xFFEDDF41)
		val silver			= Color(0xFFBFBFBF)
		val bronze 			= Color(0xFFD16D33)

		val settingsGrey 	= Color(0xFF808080)

		val lightTheme = lightColorScheme(primary = Purple40, secondary = PurpleGrey40, tertiary = Pink40)
		val darkTheme = darkColorScheme(primary = Purple80, secondary = PurpleGrey80, tertiary = Pink80)
	}
}