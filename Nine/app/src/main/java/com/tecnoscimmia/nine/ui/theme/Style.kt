package com.tecnoscimmia.nine.ui.theme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecnoscimmia.nine.R

/*
 * This file contains the definitions for all the classes used to define the style of the application and all it's widgets,
 * for example it define a class for the style of the icons, one for the style of buttons, etc...
 */


class NineTextStyle
{
	companion object
	{
		val title 		= TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Default)
		val subTitle 	= TextStyle(fontSize = 22.sp, fontWeight = FontWeight.SemiBold, fontFamily = FontFamily.Default)
		val simple 		= TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal, fontFamily = FontFamily.Default)
	}
}


class NineButtonStyle
{
	companion object
	{
		val longWidth		= 96.dp
		val longHeight		= 96.dp
		val normalWidth		= 74.dp
		val normalHeight 	= 74.dp
		val borderWidth 	= 1.dp

		val keyboardBtnWidth = 64.dp
		val keyboardBtnHeight = 64.dp

		val cornerRadius	= 12.dp

		val defaultModifier = Modifier.size(width = normalWidth, height = normalHeight)
	}
}


class NineIconStyle
{
	companion object
	{
		val appIcon				= R.drawable.app_icon
		val leftArrow 			= R.drawable.left_arrow
		val rightArrow			= R.drawable.right_arrow
		val leftArrowRound		= R.drawable.left_arrow_rounded
		val rightArrowRound		= R.drawable.right_arrow_rounded
		val downArrowRound		= R.drawable.down_arrow_rounded
		val settings			= R.drawable.settings
		val done				= R.drawable.done
		val hourglass			= R.drawable.hourglass
		val pause				= R.drawable.pause
		val trophy				= R.drawable.trophy

		val longWidth			= 96.dp
		val longHeight			= 96.dp
		val normalWidth 		= 64.dp
		val normalHeight 		= 64.dp
		val shortWidth			= 48.dp
		val shortHeight			= 48.dp
		val veryShortWidth		= 38.dp
		val veryShortHeight		= 38.dp

		val defaultModifier = Modifier.fillMaxSize()
	}
}


class NinePaddingStyle
{
	companion object
	{
		var largeHorPadding 		= 128.dp
		var largeVertPadding		= 128.dp
		var mediumHorPadding 		= 64.dp
		var mediumVertPadding 		= 64.dp
		var normalHorPadding		= 32.dp
		var normalVertPadding		= 32.dp

		val extraSmallPadding		= 8.dp


		// This function calculates some of the padding values according to the screen dimensions given
		fun calculatePaddingValues(screenWidth: Dp, screenHeight: Dp)
		{
			largeHorPadding = screenWidth / 4
			largeVertPadding = screenHeight / 4
			mediumHorPadding = screenWidth / 6
			mediumVertPadding = screenHeight / 6
		}

	}

}
