package com.tecnoscimmia.nine.ui

import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

/*
 * This file contains the definitions of some simple composable functions that realize reusable widgets that are then used to create more complex one
 */

// A simple button with an icon on top and some text under it
@Composable
fun ButtonWithIcon(modifier: Modifier = Modifier, iconId: Int, text: String? = null, showBackground: Boolean = true, onClick: () -> Unit)
{
	Button(onClick = onClick, modifier = modifier)
	{
		Icon(painter = painterResource(id = iconId), contentDescription = null)

		if(text != null)									// If some text is given
			Text(text = text)								// Then draw the text
	}
}