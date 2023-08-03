package com.tecnoscimmia.nine.view.widgets

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.tecnoscimmia.nine.R
import com.tecnoscimmia.nine.ui.theme.NineButtonStyle
import com.tecnoscimmia.nine.ui.theme.NineColors
import com.tecnoscimmia.nine.ui.theme.NineIconStyle
import com.tecnoscimmia.nine.ui.theme.NineTextStyle

/*
* This file contains the definitions of all the widgets that are used in the menu screen
*/


// This widget contains the settings and scoreboard buttons and the application title, is used when device is in portrait mode
@Composable
fun MenuPanelPortrait(onClickSettings: () -> Unit, onClickScoreboard: () -> Unit)
{

	Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween)
	{
		Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.Top)
		{
			// Settings button
			ButtonWithIcon(onClick = onClickSettings, btnShape = RoundedCornerShape(bottomStart = NineButtonStyle.cornerRadius),
				btnModifier = Modifier.size(width = NineButtonStyle.longWidth, height = NineButtonStyle.normalHeight),
				iconId = NineIconStyle.settings, iconColor = NineColors.settingsGrey)

			// Scoreboard button
			ButtonWithIcon(onClick = onClickScoreboard, btnShape = RoundedCornerShape(bottomEnd = NineButtonStyle.cornerRadius),
				btnModifier = Modifier.size(width = NineButtonStyle.longWidth, height = NineButtonStyle.normalHeight),
				iconId = NineIconStyle.trophy, iconColor = NineColors.gold
			)
		}

		Spacer(modifier = Modifier.fillMaxWidth().height(32.dp))

		Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically)
		{
			// Game icon
			Icon(modifier = Modifier.size(width = NineIconStyle.shortWidth, height = NineIconStyle.shortHeight),
				painter = painterResource(NineIconStyle.appIcon), contentDescription = null)

			Spacer(modifier = Modifier.width(width = 12.dp))

			// Game title
			ScreenTitle(title = stringResource(R.string.app_name))
		}
	}
}


// This widget contains the settings and scoreboard buttons and the application title, is used when device is in landscape mode
@Composable
fun MenuPanelLandscape(onClickSettings: () -> Unit, onClickScoreboard: () -> Unit)
{
	Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically)
	{
		// Settings button
		ButtonWithIcon(onClick = onClickSettings, btnShape = RoundedCornerShape(bottomEnd = NineButtonStyle.cornerRadius),
			btnModifier = Modifier.size(width = NineButtonStyle.longWidth, height = NineButtonStyle.normalHeight),
			iconId = NineIconStyle.settings, iconColor = NineColors.settingsGrey)

		Row(horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically)
		{
			// Game icon
			Icon(modifier = Modifier.size(width = NineIconStyle.shortWidth, height = NineIconStyle.shortHeight),
				painter = painterResource(NineIconStyle.appIcon), contentDescription = null)

			Spacer(modifier = Modifier.width(width = 12.dp))

			// Game title
			ScreenTitle(title = stringResource(id = R.string.app_name))
		}

		// Scoreboard button
		ButtonWithIcon(onClick = onClickScoreboard, btnShape = RoundedCornerShape(bottomStart = NineButtonStyle.cornerRadius),
			btnModifier = Modifier.size(width = NineButtonStyle.longWidth, height = NineButtonStyle.normalHeight),
			iconId = NineIconStyle.trophy, iconColor = NineColors.gold
		)
	}
}


// A row with 2 buttons used to change the game mode and some text that displays the game mode currently selected
@Composable
fun GameModeSelector(currGameMode: String, onLeftArrowClick: () -> Unit, onRightArrowClick: () -> Unit)
{
	Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically)
	{
		// Left arrow
		ButtonWithIcon(onClick = onLeftArrowClick,
			btnModifier = Modifier.size(width = NineButtonStyle.longWidth, height = NineButtonStyle.longHeight),
			iconId = NineIconStyle.leftArrowRound, btnShowBorder = false, btnShowBackground = false
		)

		// Current game mode selected
		Text(text = currGameMode, fontWeight = NineTextStyle.subTitle.fontWeight, fontSize = NineTextStyle.subTitle.fontSize)

		// Right arrow
		ButtonWithIcon(onClick = onRightArrowClick, btnShowBorder = false, btnShowBackground = false,
			btnModifier = Modifier.size(width = NineButtonStyle.longWidth, height = NineButtonStyle.longHeight),
			iconId = NineIconStyle.rightArrowRound)
	}
}


// A box containing an icon that is listening for swipe events, when a swipe (towards the top of the device) happens
// the icon gets scaled (on the y axis) and if the swipe is grater than the swipeThreshold then the given onSwipe callback is called
@Composable
fun GameStarter(width: Dp, height: Dp, onSwipe: () -> Unit, swipeThreshold: Float, maxIconScale: Float)
{
	val currScale = remember { mutableStateOf(1f) }				// Value used to scale on the Y axis the image
	val currSwipeAmount = remember { mutableStateOf(0f) }			// Value changed when the user swipes in the box

	val modifier = Modifier
		.size(width = width, height = height)
		.background(Color.Red) // TODO: To be removed, only for debug!!!
		.pointerInput(Unit)
		{
			detectDragGestures(
				onDrag = { change, dragAmount ->                        // Executed when the drag happens
					change.consume()

					if (dragAmount.y < 0)                                // Update the current swipe amount only if the swipe motion is from bottom to top
					{
						currSwipeAmount.value += dragAmount.y

						if (currScale.value < maxIconScale)
							currScale.value += 0.1f
					}
				},

				onDragEnd = {                                            // Executed when the drag is over
					if (currSwipeAmount.value < swipeThreshold)          // If the swipe made by the user is "long enough"
						onSwipe()                                        // Then call the callback function

					currScale.value = 1f                                // Reset all values
					currSwipeAmount.value = 0f
				}
			)
		}

	Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center)
	{
		Box(modifier = modifier, contentAlignment = Alignment.Center)
		{
			Icon(modifier = Modifier.scale(scaleX = 1f, scaleY = currScale.value)
				.size(width = NineIconStyle.longWidth, height = NineIconStyle.normalHeight),
				painter = painterResource(NineIconStyle.hourglass), contentDescription = null)
		}

		Text(text = stringResource(R.string.menu_message_to_play), fontSize = NineTextStyle.subTitle.fontSize,
			fontWeight = NineTextStyle.subTitle.fontWeight, fontFamily = NineTextStyle.subTitle.fontFamily)
	}
}
