package com.tecnoscimmia.nine.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


// A row that gives 2 buttons to change the game mode and shows the game mode currently selected
@Composable
fun GameModeSelector(modeLabel : String)
{
	Row(modifier = Modifier.fillMaxWidth(1f), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically)
	{
		ButtonWithIcon(onClick = {/*TODO*/}, iconId = NineIcons.leftArrow)
		Text(text = modeLabel)
		ButtonWithIcon(onClick = {/*TODO*/}, iconId = NineIcons.rightArrow)
	}
}