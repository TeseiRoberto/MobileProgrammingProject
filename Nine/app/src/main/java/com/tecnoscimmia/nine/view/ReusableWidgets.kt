package com.tecnoscimmia.nine.view

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.tecnoscimmia.nine.R
import com.tecnoscimmia.nine.ui.theme.NineButtonStyle
import com.tecnoscimmia.nine.ui.theme.NineIconStyle
import com.tecnoscimmia.nine.ui.theme.NineTextStyle

/*
 * This file contains the definitions for all the widgets that can be used in both portrait and landscape mode
 * and some basic widgets that are used to create more complex one
 */


// A simple text used to display the title of a screen, this ensures that all screens will use the same font style
@Composable
fun ScreenTitle(title: String)
{
	Text(text = title, fontSize = NineTextStyle.title.fontSize,
		fontWeight = NineTextStyle.title.fontWeight, fontFamily = NineTextStyle.title.fontFamily)
}


// A simple button with an icon on top
@Composable
fun ButtonWithIcon(btnModifier: Modifier = NineButtonStyle.defaultModifier, onClick: () -> Unit, btnShape : Shape = RectangleShape,
				   btnShowBackground : Boolean = true, btnBackgroundColor: Color = Color.White,
				   btnShowBorder: Boolean = true, btnBorderColor: Color = Color.Black,
				   iconModifier: Modifier = NineIconStyle.defaultModifier, iconId: Int, iconColor: Color = Color.Black)
{
	Button(modifier = btnModifier, onClick = onClick, shape = btnShape,
		colors = ButtonDefaults.buttonColors(containerColor = if(btnShowBackground) btnBackgroundColor else btnBackgroundColor.copy(alpha = 0f)),
		border = BorderStroke(width = NineButtonStyle.borderWidth, color = if(btnShowBorder) btnBorderColor else btnBorderColor.copy(alpha = 0f))
	)
	{
		Icon(modifier = iconModifier, painter = painterResource(id = iconId), contentDescription = null, tint = iconColor)
	}
}


// A simple button with an arrow icon positioned at the bottom left, it's used to go back to the previous screen, of the onClick
// callback is given then it's executed after the screen has changed
@Composable
fun GoBackButton(navigationCntrl: NavHostController, onClick: ( () -> Unit)? = null)
{
	// Lambda called when the back button is clicked, had to add explicit return type because popBackStack returns a Boolean and
	// being the only instruction it's result would be returned by the lambda
	val onClickBackBtn = {
		navigationCntrl.popBackStack(route = NineScreen.MainMenu.name, inclusive = false)

		if(onClick != null)
			onClick()

		Unit
	}

	Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.Bottom)
	{
		ButtonWithIcon(onClick = onClickBackBtn, iconId = NineIconStyle.leftArrow_round, btnShape = RoundedCornerShape(topEnd = NineButtonStyle.cornerRadius))
	}
}


// A row with 2 buttons used to change the game mode and some text that displays the game mode currently selected
@Composable
fun GameModeSelector(availableModes: List<String>, currMode: Int = 0)
{
	val currGameMode = remember { mutableStateOf(currMode) }

	Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically)
	{
		// Left arrow
		ButtonWithIcon(onClick = { /*TODO*/ },
			btnModifier = Modifier.size(width = NineButtonStyle.longWidth, height = NineButtonStyle.longHeight),
			iconId = NineIconStyle.leftArrow_round, btnShowBorder = false, btnShowBackground = false
		)

		// Current game mode selected
		Text(text = if(currMode in availableModes.indices) availableModes[currGameMode.value] else "",
			fontWeight = NineTextStyle.subTitle.fontWeight,
			fontSize = NineTextStyle.subTitle.fontSize
		)

		// Right arrow
		ButtonWithIcon(onClick = { /*TODO*/ },
			btnModifier = Modifier.size(width = NineButtonStyle.longWidth, height = NineButtonStyle.longHeight),
			iconId = NineIconStyle.rightArrow_round, btnShowBorder = false, btnShowBackground = false)
	}
}


// A box containing an icon that is listening for swipe events, when a swipe (towards the top of the device) happens
// the icon gets scaled (on the y axis) and if the swipe is grater than the swipeThreshold then the given onSwipe callback is called
@Composable
fun GameStarter(width: Float, height: Float, onSwipe: () -> Unit, swipeThreshold: Float, maxIconScale: Float)
{
	val c = LocalContext.current // TODO: To be removed, only for debug!!!

	val currScale = remember { mutableStateOf(1f) }				// Value used to scale on the Y axis the image
	val currSwipeAmount = remember { mutableStateOf(0f) }			// Value changed when the user swipes in the box

	val modifier = Modifier
		.size(width = width.dp, height = height.dp)
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
					{
						//onSwipe()										// Then call the callback function
						Toast
							.makeText(c, "currSwipeAmount >= maxSwipeAmount", Toast.LENGTH_SHORT)
							.show()
					}

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

		Text(text = stringResource(R.string.to_play_message), fontSize = NineTextStyle.subTitle.fontSize,
			fontWeight = NineTextStyle.subTitle.fontWeight, fontFamily = NineTextStyle.subTitle.fontFamily)
	}
}

/* // TODO: Adjust the alignment of elements on different rows
// A row that contains data about a game played, it's used in the scoreboard screen
@Composable
fun ScoreboardEntry(rank: Int, time: String, date: String, gameMode: String)
{
	Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically)
	{
		// If rank is in top 3 then draw the trophy icon
		if(rank in 1..3)
		{
			Icon(painterResource(id = NineIconStyle.trophy), contentDescription = null,
				modifier = Modifier.size(width = NineIconStyle.shortWidth, height = NineIconStyle.shortHeight),
				tint = when(rank)
				{
					1 -> NineColors.gold
					2 -> NineColors.silver
					3 -> NineColors.bronze
					else -> Color.Black
				}
			)
		} else {	// Otherwise just the text
			Text(text = rank.toString(), fontWeight = NineTextStyle.simple.fontWeight,
				fontFamily = NineTextStyle.simple.fontFamily, fontSize = NineTextStyle.simple.fontSize)
		}

		// Time
		Text(text = time, fontWeight = NineTextStyle.simple.fontWeight,
			fontFamily = NineTextStyle.simple.fontFamily, fontSize = NineTextStyle.simple.fontSize)

		// Date
		Text(text = date, fontWeight = NineTextStyle.simple.fontWeight,
			fontFamily = NineTextStyle.simple.fontFamily, fontSize = NineTextStyle.simple.fontSize)

		// Game mode
		Text(text = gameMode, fontWeight = NineTextStyle.simple.fontWeight,
			fontFamily = NineTextStyle.simple.fontFamily, fontSize = NineTextStyle.simple.fontSize)
	}
}*/