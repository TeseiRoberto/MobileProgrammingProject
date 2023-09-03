
package com.tecnoscimmia.nine.view.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.tecnoscimmia.nine.R
import com.tecnoscimmia.nine.model.GameSettings
import com.tecnoscimmia.nine.model.Symbol
import com.tecnoscimmia.nine.model.SymbolSet
import com.tecnoscimmia.nine.ui.theme.NineButtonStyle
import com.tecnoscimmia.nine.ui.theme.NineIconStyle
import com.tecnoscimmia.nine.ui.theme.NinePaddingStyle
import com.tecnoscimmia.nine.ui.theme.NineTextStyle
import com.tecnoscimmia.nine.view.NineScreen
import com.tecnoscimmia.nine.viewModel.GameViewModel

/*
 * This file contains definitions of all the widgets used in the game screen
 */


// A button that contains a symbol, this is used to implement the Keyboard widget
@Composable
fun SymbolButton(enabled: Boolean, width: Dp, height: Dp, symbol: Symbol, onClick: (Symbol) -> Unit)
{
	Button(modifier = Modifier.size(width = width, height = height),
		enabled = enabled, onClick = { onClick(symbol) }, shape = RoundedCornerShape(NineButtonStyle.cornerRadius))
	{
		Text(text = symbol.value, textAlign = TextAlign.Center, fontWeight = NineTextStyle.subTitle.fontWeight,
			fontSize = NineTextStyle.title.fontSize, fontFamily = NineTextStyle.subTitle.fontFamily)
	}
}


// A collection of SymbolButtons placed accordingly to the given layout, when one of the buttons is
// clicked the onBtnClick callback is called and the symbol associated to the clicked button is passed as parameter
@Composable
fun Keyboard(modifier: Modifier, enabled: Boolean, symbolSet: SymbolSet, keyboardLayout: GameSettings.KeyboardLayoutSetting,
			 buttonsPadding: Dp, onBtnClick: (Symbol) -> Unit)
{
	// Choose the number of buttons to place on each line of the keyboard according to the given layout
	val numOfCols = when(keyboardLayout)
	{
		GameSettings.KeyboardLayoutSetting.TWO_LINES_KBD_LAYOUT -> 5
		GameSettings.KeyboardLayoutSetting.THREE_BY_THREE_KBD_LAYOUT -> 3
	}

	Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center)
	{
		var i = 0
		while(i < symbolSet.data.size)
		{
			Row(modifier = Modifier.fillMaxWidth().padding(vertical = buttonsPadding), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.Top)
			{
				var j = 0
				while(j < numOfCols && i < symbolSet.data.size)
				{
					SymbolButton(enabled = enabled, width = NineButtonStyle.keyboardBtnWidth, height = NineButtonStyle.keyboardBtnHeight, symbol = symbolSet.data[i], onClick = onBtnClick)
					i++
					j++
				}
			}
		}
	}
}


// A panel that shows info about the game
@Composable
fun GameInfoPanel(isLandscape: Boolean, gameVM: GameViewModel)
{
	Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically)
	{
		Card(modifier = Modifier.fillMaxWidth().padding(horizontal = if(isLandscape) NinePaddingStyle.largeHorPadding else NinePaddingStyle.normalHorPadding),
			shape = RoundedCornerShape(bottomStart = NineButtonStyle.cornerRadius, bottomEnd = NineButtonStyle.cornerRadius))
		{
			Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
				horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically)
			{
					// Hourglass icon
					Icon(painter = painterResource(NineIconStyle.hourglass), contentDescription = null,
						modifier = Modifier.size(width = NineIconStyle.veryShortWidth, height = NineIconStyle.veryShortHeight))

					// Elapsed time
					Text(text = gameVM.getTime(), fontFamily = NineTextStyle.subTitle.fontFamily,
						fontSize = NineTextStyle.subTitle.fontSize, fontWeight = NineTextStyle.subTitle.fontWeight)

					// Number of attempts
					Text(text = stringResource(R.string.game_screen_attempts) + gameVM.getAttemptsNum(),
						fontFamily = NineTextStyle.subTitle.fontFamily, fontSize = NineTextStyle.subTitle.fontSize,
						fontWeight = NineTextStyle.subTitle.fontWeight)
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
			for(i in 0 until GameSettings.MAX_DIGITS_NUM)	// For each symbol in the user input
			{
				Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center)
				{
					var text = ""

					if(i < userInput.size)
						text = userInput[i].value

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
						Box(modifier = Modifier.size(64.dp).border(BorderStroke(width = 3.dp, color = MaterialTheme.colorScheme.outline)),
							contentAlignment = Alignment.Center, content = { element() })
					else
						element()
				}
			}
		}
	}
}



// A panel that contains the keyboard widget and some buttons that enables the user to interact with the game
@Composable
fun GameControlPanel(isLandscape: Boolean, gameVM: GameViewModel)
{
	// If the current match is paused or is over then the keyboard and the control buttons must be disabled
	val areBtnsEnabled = !(gameVM.isMatchPaused() || gameVM.isMatchOver())

	if(isLandscape)
	{
		Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.Bottom)
		{
			Column(modifier = Modifier.width(width = NineButtonStyle.normalWidth),
					horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Bottom)
			{
				// Pause button
				ButtonWithIcon(enabled = areBtnsEnabled, btnShape = RoundedCornerShape(topEnd = NineButtonStyle.cornerRadius),
					iconId = NineIconStyle.pause, onClick = gameVM::pauseMatch )

				// Select left symbol button
				ButtonWithIcon(enabled = areBtnsEnabled, iconId = NineIconStyle.leftArrow, onClick = gameVM::selectPrevSymbol)
			}

			Keyboard(modifier = Modifier.fillMaxWidth().weight(0.5f),
				enabled = areBtnsEnabled, symbolSet = gameVM.getSymbolsSet(),
				keyboardLayout = if(isLandscape) GameSettings.KeyboardLayoutSetting.TWO_LINES_KBD_LAYOUT else gameVM.getKeyboardLayout(),
				buttonsPadding = 6.dp, onBtnClick = { symbol -> gameVM.insertSymbol(symbol = symbol) })

			Column(modifier = Modifier.width(width = NineButtonStyle.normalWidth),
				horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Bottom)
			{
				// Evaluate button
				ButtonWithIcon(enabled = areBtnsEnabled, btnShape = RoundedCornerShape(topStart = NineButtonStyle.cornerRadius),
					iconId = NineIconStyle.done, onClick = gameVM::evaluate)

				// Select right symbol button
				ButtonWithIcon(enabled = areBtnsEnabled, iconId = NineIconStyle.rightArrow, onClick = gameVM::selectNextSymbol )
			}
		}
	} else {
		Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween)
		{
			Keyboard(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
				enabled = areBtnsEnabled, symbolSet = gameVM.getSymbolsSet(),
				keyboardLayout = if(isLandscape) GameSettings.KeyboardLayoutSetting.TWO_LINES_KBD_LAYOUT else gameVM.getKeyboardLayout(),
				buttonsPadding = 6.dp, onBtnClick = { symbol -> gameVM.insertSymbol(symbol = symbol) })

			Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically)
			{
				// Select left symbol button
				ButtonWithIcon(enabled = areBtnsEnabled, btnShape = RoundedCornerShape(topStart = NineButtonStyle.cornerRadius),
					iconId = NineIconStyle.leftArrow, onClick = gameVM::selectPrevSymbol )

				// Pause button
				ButtonWithIcon(enabled = areBtnsEnabled, iconId = NineIconStyle.pause, onClick = gameVM::pauseMatch )

				// Evaluate button
				ButtonWithIcon(enabled = areBtnsEnabled, iconId = NineIconStyle.done, onClick = gameVM::evaluate)

				// Select right symbol button
				ButtonWithIcon(enabled = areBtnsEnabled, btnShape = RoundedCornerShape(topEnd = NineButtonStyle.cornerRadius),
					iconId = NineIconStyle.rightArrow, onClick = gameVM::selectNextSymbol )
			}
		}
	}
}


// A menu with some buttons that enables the user to leave the current match or resume it
// Note that this composable makes use of the zIndex modifier attribute so that is drawn on top of the game screen
@Composable
fun PauseMenu(isLandscape: Boolean, navigationCntrl: NavHostController, gameVM: GameViewModel)
{
	OverlayContent(zIndex = 2f)
	{
		NineCard(isLandscape = isLandscape)
		{
			Column(modifier = Modifier.fillMaxWidth().padding(NinePaddingStyle.extraSmallPadding)
				, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly)
			{
				ScreenTitle(title = stringResource(R.string.pause_menu_title))

				// Resume button
				Button(onClick = gameVM::resumeMatch,
					shape = RoundedCornerShape(NineButtonStyle.cornerRadius),
					modifier = Modifier.padding(vertical = NinePaddingStyle.extraSmallPadding))
				{
					Text(text = stringResource(R.string.pause_menu_resume), textAlign = TextAlign.Center, fontWeight = NineTextStyle.subTitle.fontWeight,
						fontSize = NineTextStyle.subTitle.fontSize, fontFamily = NineTextStyle.subTitle.fontFamily)
				}

				// Quit button
				Button(onClick = { navigationCntrl.popBackStack(route = NineScreen.MainMenu.name, inclusive = false) },
					shape = RoundedCornerShape(NineButtonStyle.cornerRadius),
					modifier = Modifier.padding(vertical = NinePaddingStyle.extraSmallPadding))
				{
					Text(text = stringResource(R.string.pause_menu_quit), textAlign = TextAlign.Center, fontWeight = NineTextStyle.subTitle.fontWeight,
						fontSize = NineTextStyle.subTitle.fontSize, fontFamily = NineTextStyle.subTitle.fontFamily)
				}
			}
		}
	}
}


// A menu that is shown when the user guesses the secret key, it enables the user to go back to the main menu or start a new match
// Note that this composable makes use of the OnTopSurface composable so that is drawn on top of the game screen
@Composable
fun WinPanel(isLandscape: Boolean, navigationCntrl: NavHostController, gameVM: GameViewModel)
{
	OverlayContent(zIndex = 2f)
	{
		NineCard(isLandscape = isLandscape)
		{
			Column(modifier = Modifier.fillMaxWidth().padding(NinePaddingStyle.extraSmallPadding)
				, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly)
			{
				ScreenTitle(title = stringResource(R.string.game_screen_win_message))

				// Play again button
				Button(onClick = gameVM::startNewMatch,
					shape = RoundedCornerShape(NineButtonStyle.cornerRadius),
					modifier = Modifier.padding(vertical = NinePaddingStyle.extraSmallPadding))
				{
					Text(text = stringResource(R.string.game_screen_play_again), textAlign = TextAlign.Center, fontWeight = NineTextStyle.subTitle.fontWeight,
						fontSize = NineTextStyle.subTitle.fontSize, fontFamily = NineTextStyle.subTitle.fontFamily)
				}

				// Quit button
				Button(onClick = { navigationCntrl.popBackStack(route = NineScreen.MainMenu.name, inclusive = false) },
					shape = RoundedCornerShape(NineButtonStyle.cornerRadius),
					modifier = Modifier.padding(vertical = NinePaddingStyle.extraSmallPadding))
				{
					Text(text = stringResource(R.string.game_screen_back_to_menu), textAlign = TextAlign.Center, fontWeight = NineTextStyle.subTitle.fontWeight,
						fontSize = NineTextStyle.subTitle.fontSize, fontFamily = NineTextStyle.subTitle.fontFamily)
				}
			}

		}
	}
}
