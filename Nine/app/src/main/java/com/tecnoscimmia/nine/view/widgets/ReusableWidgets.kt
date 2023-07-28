package com.tecnoscimmia.nine.view.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.tecnoscimmia.nine.ui.theme.NineButtonStyle
import com.tecnoscimmia.nine.ui.theme.NineIconStyle
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
