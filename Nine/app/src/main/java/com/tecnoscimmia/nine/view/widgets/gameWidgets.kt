
package com.tecnoscimmia.nine.view.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tecnoscimmia.nine.model.GameSettings
import com.tecnoscimmia.nine.model.Symbol
import com.tecnoscimmia.nine.ui.theme.NineButtonStyle
import com.tecnoscimmia.nine.ui.theme.NineIconStyle
import com.tecnoscimmia.nine.ui.theme.NineTextStyle
import com.tecnoscimmia.nine.viewModel.GameViewModel

/*
 * This file contains definitions of all the widgets used in the game screen
 */


// A button that contains a symbol, this is used to implement the Keyboard and the SymbolRow widgets
@Composable
fun SymbolButton(width: Dp, height: Dp, symbol: String, backgroundColor: Color = Color.White, borderColor: Color = Color.Black,
				 onClick: (String) -> Unit, isSelected: Boolean = false)
{
	val modifier = Modifier.size(width = width, height = height)
		.background(color = backgroundColor)
		.border(
			border = BorderStroke(width = if (isSelected) 3.dp else 1.dp, color = borderColor),
			shape = RoundedCornerShape(
				NineButtonStyle.cornerRadius
			)
		)

	Button(modifier = modifier, onClick = { onClick(symbol) }, shape = RoundedCornerShape(NineButtonStyle.cornerRadius))
	{
		Text(text = symbol, textAlign = TextAlign.Center, fontWeight = NineTextStyle.subTitle.fontWeight,
			fontSize = NineTextStyle.title.fontSize, fontFamily = NineTextStyle.subTitle.fontFamily)
	}
}


// A collection of SymbolButtons placed accordingly to the given layout and the isLandscape value, when one of the buttons is
// clicked the onBtnClick callback is called and the symbol associated to the clicked button is passed as parameter
@Composable
fun Keyboard(modifier: Modifier, isLandscape: Boolean, symbolSet: Array<Symbol>, keyboardLayout: GameSettings.KeyboardLayoutSetting,
			 buttonsPadding: Dp, onBtnClick: (String) -> Unit)
{
	// Choose the number of buttons to place on each line of the keyboard according to the given layout
	val numOfCols = when(keyboardLayout)
	{
		GameSettings.KeyboardLayoutSetting.TWO_LINES_KBD_LAYOUT -> 5

		// The 3x3 layout can be used only in portrait mode, if we are in landscape we fallback to TWO_LINES layout
		GameSettings.KeyboardLayoutSetting.THREE_BY_THREE_KBD_LAYOUT -> if(!isLandscape) 3 else 5
	}

	Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center)
	{
		var i = 0
		while(i < symbolSet.size)
		{
			Row(modifier = Modifier.fillMaxWidth().padding(vertical = buttonsPadding), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.Top)
			{
				var j = 0
				while(j < numOfCols && i < symbolSet.size)
				{
					SymbolButton(width = 64.dp, height = 64.dp, symbol = symbolSet[i].value, onClick = onBtnClick)
					i++
					j++
				}
			}
		}
	}
}


// A panel that shows the timer and the number of attempts
@Composable
fun GameInfoPanel(isLandscape: Boolean, time: String, attempts: UInt)
{
	Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically)
	{
		Card(modifier = Modifier.fillMaxWidth().padding(horizontal = if(isLandscape) 128.dp else 32.dp),
			shape = RoundedCornerShape(bottomStart = NineButtonStyle.cornerRadius, bottomEnd = NineButtonStyle.cornerRadius))
		{
			Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically)
			{
				Icon(painter = painterResource(NineIconStyle.hourglass), contentDescription = null,
					modifier = Modifier.size(width = NineIconStyle.veryShortWidth, height = NineIconStyle.veryShortHeight))

				Text(text = time, fontFamily = NineTextStyle.subTitle.fontFamily, fontSize = NineTextStyle.subTitle.fontSize,
					fontWeight = NineTextStyle.subTitle.fontWeight)

				Text(text = "Attempts: $attempts", fontFamily = NineTextStyle.subTitle.fontFamily,
					fontSize = NineTextStyle.subTitle.fontSize, fontWeight = NineTextStyle.subTitle.fontWeight)
			}
		}
	}
}


// This widget shows the symbols currently inserted by the user and below each symbol the distance between
// the position in which the symbol is inserted and the position in the secret key generated by the game
// (The distance is passed as a string and should contain a number of elements equal to the userInput list)
@Composable
fun InputRow(userInput: SnapshotStateList<Symbol>, currIndex: Int, differencesStr: String)
{
	Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 36.dp, vertical = 4.dp))
	{
		Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically)
		{
			for(i in userInput.indices)							// For each symbol in the user input
			{
				Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center)
				{
					var text = userInput[i].value

					if(i < differencesStr.length)				// If a difference value is present for the current symbol
						text += "\n${differencesStr[i]}"		// We write the value under the symbol

					val element = @Composable {
						Text(text = text, textAlign = TextAlign.Center,
							fontWeight = NineTextStyle.subTitle.fontWeight,
							fontSize = if(i == currIndex) NineTextStyle.title.fontSize else NineTextStyle.subTitle.fontSize,
							fontFamily = NineTextStyle.subTitle.fontFamily)
					}

					// If the current element is the one selected then we draw a box around it
					if(i == currIndex)
						Box(modifier = Modifier.size(64.dp).border(BorderStroke(width = 3.dp, color = Color.Black)),
							contentAlignment = Alignment.Center, content = { element() })
					else
						element()
				}
			}
		}
	}
}



// A panel that contains the keyboard widget and some buttons that enables the user to control the game state
@Composable
fun GameControlPanel(isLandscape: Boolean, gameVM: GameViewModel)
{
	if(isLandscape)
	{
		Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.Bottom)
		{
			Column(modifier = Modifier.width(width = NineButtonStyle.normalWidth),
					horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Bottom)
			{
				// Pause button
				ButtonWithIcon(btnShape = RoundedCornerShape(topEnd = NineButtonStyle.cornerRadius),
					iconId = NineIconStyle.pause, onClick = { /*TODO*/} )

				// Select left symbol button
				ButtonWithIcon(iconId = NineIconStyle.leftArrow, onClick = gameVM::selectPrevSymbol)
			}

			Keyboard(modifier = Modifier.fillMaxWidth().weight(0.5f), isLandscape = isLandscape, symbolSet = gameVM.getSymbolsSet(),
				keyboardLayout = gameVM.getKeyboardLayout(), buttonsPadding = 6.dp, onBtnClick = { symbol -> gameVM.insertSymbol(symbol = symbol) })

			Column(modifier = Modifier.width(width = NineButtonStyle.normalWidth),
				horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Bottom)
			{
				// Evaluate button
				ButtonWithIcon(btnShape = RoundedCornerShape(topStart = NineButtonStyle.cornerRadius),
					iconId = NineIconStyle.done, onClick = gameVM::evaluate)

				// Select right symbol button
				ButtonWithIcon(iconId = NineIconStyle.rightArrow, onClick = gameVM::selectNextSymbol )
			}
		}
	} else {
		Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween)
		{
			Keyboard(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp), isLandscape = isLandscape, symbolSet = gameVM.getSymbolsSet(),
				keyboardLayout = gameVM.getKeyboardLayout(), buttonsPadding = 6.dp, onBtnClick = { symbol -> gameVM.insertSymbol(symbol = symbol) })

			Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically)
			{
				// Select left symbol button
				ButtonWithIcon(btnShape = RoundedCornerShape(topStart = NineButtonStyle.cornerRadius),
					iconId = NineIconStyle.leftArrow, onClick = gameVM::selectPrevSymbol )

				// Pause button
				ButtonWithIcon(iconId = NineIconStyle.pause, onClick = { /*TODO*/} )

				// Evaluate button
				ButtonWithIcon(iconId = NineIconStyle.done, onClick = gameVM::evaluate)

				// Select right symbol button
				ButtonWithIcon(btnShape = RoundedCornerShape(topEnd = NineButtonStyle.cornerRadius),
					iconId = NineIconStyle.rightArrow, onClick = gameVM::selectNextSymbol )
			}
		}
	}
}

