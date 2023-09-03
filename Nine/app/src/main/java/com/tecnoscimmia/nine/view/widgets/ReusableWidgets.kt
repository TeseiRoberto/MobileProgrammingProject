package com.tecnoscimmia.nine.view.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.tecnoscimmia.nine.R
import com.tecnoscimmia.nine.ui.theme.NineButtonStyle
import com.tecnoscimmia.nine.ui.theme.NineIconStyle
import com.tecnoscimmia.nine.ui.theme.NinePaddingStyle
import com.tecnoscimmia.nine.ui.theme.NineTextStyle
import com.tecnoscimmia.nine.view.NineScreen

/*
* This file contains the definitions of widgets that don't belong to a particular view and
* some basic widgets that are used to build more complex one
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
fun ButtonWithIcon(btnModifier: Modifier = NineButtonStyle.defaultModifier,
				   enabled: Boolean = true, onClick: () -> Unit, btnShape : Shape = RectangleShape,
				   btnShowBackground: Boolean = true, btnBackgroundColor: Color = MaterialTheme.colorScheme.background,
				   btnShowBorder: Boolean = true, btnBorderColor: Color = MaterialTheme.colorScheme.outline,
				   iconModifier: Modifier = NineIconStyle.defaultModifier, iconId: Int, iconColor: Color = MaterialTheme.colorScheme.onBackground)
{
	Button(modifier = btnModifier, enabled = enabled, onClick = onClick, shape = btnShape,
		colors = ButtonDefaults.buttonColors(containerColor = if(btnShowBackground) btnBackgroundColor else btnBackgroundColor.copy(alpha = 0f)),
		border = BorderStroke(width = NineButtonStyle.borderWidth, color = if(btnShowBorder) btnBorderColor else btnBorderColor.copy(alpha = 0f))
	)
	{
		Icon(modifier = iconModifier, painter = painterResource(id = iconId), contentDescription = null, tint = iconColor)
	}
}


// A simple button positioned in the bottom left with an arrow icon on top, it's used to go back to the previous screen.
@Composable
fun GoBackButton(navigationCntrl: NavHostController)
{
	// Lambda called when the back button is clicked, had to add explicit return type because popBackStack returns a Boolean and
	// being the only instruction it's result would be returned by the lambda
	val onClickBackBtn = {
		navigationCntrl.popBackStack(route = NineScreen.MainMenu.name, inclusive = false)
		Unit
	}

	Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.Bottom)
	{
		ButtonWithIcon(onClick = onClickBackBtn, iconId = NineIconStyle.leftArrowRound, btnShape = RoundedCornerShape(topEnd = NineButtonStyle.cornerRadius))
	}
}


// This composable creates a transparent box that will be displayed on top of everything that has a lower Z index.
// Using this we can draw other composable elements on top of the already existing ones
@Composable
fun OverlayContent(zIndex: Float, contentAlignment: Alignment = Alignment.Center, content: @Composable () -> Unit)
{
	Box(modifier = Modifier
		.absoluteOffset(x = 0.dp, 0.dp)
		.fillMaxSize()
		.zIndex(zIndex = zIndex),
		contentAlignment = contentAlignment
		)
	{
		content()
	}
}


// Default style for a card
@Composable
fun NineCard(isLandscape: Boolean, content: @Composable ColumnScope.() -> Unit)
{
	Card(modifier = Modifier.fillMaxWidth()
		.padding(horizontal = if(isLandscape) NinePaddingStyle.largeHorPadding else NinePaddingStyle.mediumHorPadding)
		.border(color = MaterialTheme.colorScheme.outline, width = 2.dp, shape = RoundedCornerShape(NineButtonStyle.cornerRadius)),
		content = content
	)

}


// A form that with 2 buttons that asks for a confirm from the user
@Composable
fun ConfirmationForm(isLandscape: Boolean, text: String, onConfirm: () -> Unit, onDismiss: () -> Unit)
{
	OverlayContent(2f)
	{
		NineCard(isLandscape = isLandscape)
		{
			Column(modifier = Modifier.fillMaxWidth().padding(NinePaddingStyle.extraSmallPadding)
				, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly)
			{
				Text(text = text, modifier = Modifier.padding(NinePaddingStyle.extraSmallPadding),
					textAlign = TextAlign.Center, fontWeight = NineTextStyle.simple.fontWeight,
					fontSize = NineTextStyle.simple.fontSize, fontFamily = NineTextStyle.simple.fontFamily)

				Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically)
				{
					// Confirm button
					Button(onClick = onConfirm, shape = RoundedCornerShape(NineButtonStyle.cornerRadius))
					{
						Text(text = stringResource(R.string.settings_confirm), textAlign = TextAlign.Center, fontWeight = NineTextStyle.subTitle.fontWeight,
							fontSize = NineTextStyle.subTitle.fontSize, fontFamily = NineTextStyle.subTitle.fontFamily)
					}

					// Dismiss button
					Button(onClick = onDismiss, shape = RoundedCornerShape(NineButtonStyle.cornerRadius))
					{
						Text(text = stringResource(R.string.settings_dismiss), textAlign = TextAlign.Center, fontWeight = NineTextStyle.subTitle.fontWeight,
							fontSize = NineTextStyle.subTitle.fontSize, fontFamily = NineTextStyle.subTitle.fontFamily)
					}
				}
			}
		}
	}
}

