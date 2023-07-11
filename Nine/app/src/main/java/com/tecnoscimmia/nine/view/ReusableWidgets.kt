package com.tecnoscimmia.nine.view

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.tecnoscimmia.nine.R
import com.tecnoscimmia.nine.model.GameSettings
import com.tecnoscimmia.nine.ui.theme.NineButtonStyle
import com.tecnoscimmia.nine.ui.theme.NineColors
import com.tecnoscimmia.nine.ui.theme.NineIconStyle
import com.tecnoscimmia.nine.ui.theme.NineTextStyle
import com.tecnoscimmia.nine.model.MatchResult

/*
 * This file contains the definitions for all the widgets that can be used in both portrait and landscape mode
 * and some basic widgets that are used to create more complex one
 */


// A simple text used to display the title of a screen, this ensures that all screens will use the same font style
@Composable
fun ScreenTitle(title: String, verticalPadding: Dp = 12.dp)
{
	Text(modifier = Modifier.padding(vertical = verticalPadding), text = title, fontSize = NineTextStyle.title.fontSize,
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


// A simple button positioned in the bottom left with an arrow icon on top, it's used to go back to the previous screen.
// If the onClick callback is given then it's executed after the screen has changed
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
		ButtonWithIcon(onClick = onClickBackBtn, iconId = NineIconStyle.leftArrowRound, btnShape = RoundedCornerShape(topEnd = NineButtonStyle.cornerRadius))
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
	val c = LocalContext.current // TODO: To be removed, only for debug!!!

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
					{
						onSwipe()                                        // Then call the callback function
						Toast.makeText(c, "currSwipeAmount >= maxSwipeAmount", Toast.LENGTH_SHORT).show()
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

		Text(text = stringResource(R.string.menu_message_to_play), fontSize = NineTextStyle.subTitle.fontSize,
			fontWeight = NineTextStyle.subTitle.fontWeight, fontFamily = NineTextStyle.subTitle.fontFamily)
	}
}


// A lazy column used to display data about matches played in the past, it's used in the scoreboard screen,
// note that this function does not sort the list in any way!
@Composable
fun Scoreboard(data: List<MatchResult>?, widthOccupation: Float, heightOccupation: Float)
{
	Column(modifier = Modifier.fillMaxWidth(widthOccupation).fillMaxHeight(heightOccupation),
		horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top)
	{
		Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
			horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically)
		{
			// Rank column header
			Text(text = stringResource(R.string.scoreboard_rank_column_header), modifier = Modifier.weight(0.5f),
				fontWeight = NineTextStyle.subTitle.fontWeight, fontFamily = NineTextStyle.subTitle.fontFamily,
				fontSize = NineTextStyle.subTitle.fontSize, textAlign = TextAlign.Center)

			// Time column header
			Text(text = stringResource(R.string.scoreboard_time_column_header), modifier = Modifier.weight(0.5f),
				fontWeight = NineTextStyle.subTitle.fontWeight, fontFamily = NineTextStyle.subTitle.fontFamily,
				fontSize = NineTextStyle.subTitle.fontSize, textAlign = TextAlign.Center)

			// Date column header
			Text(text = stringResource(R.string.scoreboard_date_column_header), modifier = Modifier.weight(0.5f),
				fontWeight = NineTextStyle.subTitle.fontWeight, fontFamily = NineTextStyle.subTitle.fontFamily,
				fontSize = NineTextStyle.subTitle.fontSize, textAlign = TextAlign.Center)

			// Game mode column header
			Text(text = stringResource(R.string.scoreboard_game_mode_column_header), modifier = Modifier.weight(0.5f),
				fontWeight = NineTextStyle.subTitle.fontWeight, fontFamily = NineTextStyle.subTitle.fontFamily,
				fontSize = NineTextStyle.subTitle.fontSize, textAlign = TextAlign.Center)
		}

		if(data.isNullOrEmpty())
		{
			Text(text = stringResource(id = R.string.scoreboard_message_is_empty), modifier = Modifier.padding(vertical = 48.dp),
				fontWeight = NineTextStyle.subTitle.fontWeight, fontSize = NineTextStyle.subTitle.fontSize, fontFamily = NineTextStyle.subTitle.fontFamily)
		} else {
			LazyColumn(modifier = Modifier.fillMaxWidth())
			{
				itemsIndexed(data)
				{ index, match ->
					ScoreboardEntry(rank = index + 1, time = match.time, date = match.date, gameMode = match.gameMode)
				}
			}	
		}
	}
}


// A row that contains data about a match played in the past, it's used in the scoreboard widget
@Composable
fun ScoreboardEntry(rank: Int, time: String, date: String, gameMode: String)
{
	Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 6.dp))
	{
		Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp, vertical = 8.dp),
			horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically)
		{
			if(rank in 1..3)								// If rank is in top 3 then draw the trophy icon
			{
				Icon(painterResource(id = NineIconStyle.trophy), contentDescription = null,
					modifier = Modifier
						.size(width = NineIconStyle.shortWidth, height = NineIconStyle.shortHeight)
						.weight(0.5f),
					tint = when(rank)
					{
						1 -> NineColors.gold
						2 -> NineColors.silver
						3 -> NineColors.bronze
						else -> Color.Black
					}
				)
			} else {										// Otherwise just the text
				Text(text = rank.toString(), modifier = Modifier.weight(0.5f),
					fontWeight = NineTextStyle.subTitle.fontWeight, fontFamily = NineTextStyle.simple.fontFamily,
					fontSize = NineTextStyle.simple.fontSize, textAlign = TextAlign.Center)
			}

			// Duration
			Text(text = time, modifier = Modifier.weight(0.5f), fontWeight = NineTextStyle.simple.fontWeight,
				fontFamily = NineTextStyle.simple.fontFamily, fontSize = NineTextStyle.simple.fontSize, textAlign = TextAlign.Center)

			// Date
			Text(text = date, modifier = Modifier.weight(0.7f), fontWeight = NineTextStyle.simple.fontWeight,
				fontFamily = NineTextStyle.simple.fontFamily, fontSize = NineTextStyle.simple.fontSize, textAlign = TextAlign.Center)

			// Game mode
			Text(text = gameMode, modifier = Modifier.weight(0.5f), fontWeight = NineTextStyle.simple.fontWeight,
				fontFamily = NineTextStyle.simple.fontFamily, fontSize = NineTextStyle.simple.fontSize, textAlign = TextAlign.Center)
		}
	}
}


// A row that contains the name of a setting, the current value and a button that enables a drop down menu, this menu contains
// all the available values for the setting. When the user select an entry from the drop down menu then onSettingChange is
// called and the value of the entry is given as parameter
@Composable
fun SettingRow(settingName: String, availableValues: List<String>, currValue: String, onSettingChange: (newValue: String) -> Unit)
{
	val isMenuExpanded = remember { mutableStateOf(false) }

	Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 8.dp),
		horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically)
	{
		// Name of the setting
		Text(text = settingName, modifier = Modifier.weight(0.5f), textAlign = TextAlign.Center,
			fontWeight = NineTextStyle.subTitle.fontWeight, fontSize = NineTextStyle.subTitle.fontSize,
			fontFamily = NineTextStyle.subTitle.fontFamily)

		// Button that enables the drop down menu so the user can choose another value for the setting
		Button(modifier = Modifier.weight(0.5f), onClick = { isMenuExpanded.value = true },
				shape = RoundedCornerShape(NineButtonStyle.cornerRadius))
		{
			Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically)
			{
				// Display the current value assigned to the setting
				Text(text = currValue, textAlign = TextAlign.Center, fontWeight = NineTextStyle.subTitle.fontWeight,
					fontSize = NineTextStyle.subTitle.fontSize, fontFamily = NineTextStyle.subTitle.fontFamily)

				// This icon signals to user that clicking on this button will enable a drop down menu
				Icon(painter = painterResource(NineIconStyle.downArrowRound), contentDescription = null)

			}

			// The drop down menu that shows all the available values for this setting
			DropdownMenu(expanded = isMenuExpanded.value, onDismissRequest = { isMenuExpanded.value = false} )
			{
				availableValues.forEach { value ->			// For each available value create an item in the drop down menu
					DropdownMenuItem(text = { Text(text = value) },
						onClick = {
							isMenuExpanded.value = false	// Close the drop down menu
							onSettingChange(value)			// Call the given callback with the selected setting
						}
					)
				}
			}

		}
	}
}


// A button that contains a symbol, this is used to implement the Keyboard and the SymbolRow widgets
@Composable
fun SymbolButton(width: Dp, height: Dp, symbol: Char, backgroundColor: Color = Color.White, borderColor: Color = Color.Black,
				onClick: (Char) -> Unit, isSelected: Boolean = false)
{
	val modifier = Modifier
		.size(width = width, height = height)
		.background(color = backgroundColor)
		.border(border = BorderStroke(width = if(isSelected) 3.dp else 1.dp, color = borderColor), shape = RoundedCornerShape(NineButtonStyle.cornerRadius))

	Button(modifier = modifier, onClick = { onClick(symbol) }, shape = RoundedCornerShape(NineButtonStyle.cornerRadius))
	{
		Text(text = symbol.toString(), fontWeight = NineTextStyle.subTitle.fontWeight,
				fontSize = NineTextStyle.subTitle.fontSize, fontFamily = NineTextStyle.subTitle.fontFamily)
	}
}


@Composable
fun Keyboard(symbolSet: List<Char>, keyboardLayout: GameSettings.KeyboardLayoutSetting)
{
	// Build the keyboard according to the layout
	/*when(keyboardLayout)
	{
		GameSettings.KeyboardLayoutSetting.TWO_LINES_KBD_LAYOUT ->
		{
			Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly)
			{
				Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically)
				{

				}

				Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically)
				{

				}
			}
		}

		GameSettings.KeyboardLayoutSetting.THREE_BY_THREE_KBD_LAYOUT ->
		{

		}
	}*/

	// This is the in-line layout
	Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically)
	{
		symbolSet.forEach() { symbol ->
			SymbolButton(width = 64.dp, height = 64.dp, symbol = symbol, onClick = {} )
		}
	}
}