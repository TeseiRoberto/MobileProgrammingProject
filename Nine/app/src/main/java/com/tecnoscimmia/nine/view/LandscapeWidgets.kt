package com.tecnoscimmia.nine.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tecnoscimmia.nine.R
import com.tecnoscimmia.nine.ui.theme.NineButtonStyle
import com.tecnoscimmia.nine.ui.theme.NineColors
import com.tecnoscimmia.nine.ui.theme.NineIconStyle

/*
 * This file defines all the widgets specifically made for the landscape mode
 */

// This widget contains the settings and scoreboard buttons and the application title
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