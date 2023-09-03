package com.tecnoscimmia.nine.view.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tecnoscimmia.nine.ui.theme.NineButtonStyle
import com.tecnoscimmia.nine.ui.theme.NineIconStyle
import com.tecnoscimmia.nine.ui.theme.NineTextStyle

/*
 * This file contains definitions for all the widgets used in the settings screen
*/


// A row that contains the name of a setting, the current value and a button that enables a drop down menu, this menu contains
// all the available values for the setting. When the user select an entry from the drop down menu then onSettingChange is
// called and the value of the entry is given as parameter
@Composable
fun SettingRow(settingName: String, availableValues: List<String>, currValue: String, onSettingChange: (newValue: String) -> Unit)
{
	val isMenuExpanded = remember { mutableStateOf(false) }

	Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically)
	{
		// Name of the setting
		Text(modifier = Modifier.padding(start = 14.dp).weight(0.5f),
			text = settingName, textAlign = TextAlign.Center,
			fontWeight = NineTextStyle.subTitle.fontWeight, fontSize = NineTextStyle.subTitle.fontSize,
			fontFamily = NineTextStyle.subTitle.fontFamily)

		// Button that enables the drop down menu so the user can choose another value for the setting
		Button(modifier = Modifier.padding(end = 14.dp).weight(0.5f),
			onClick = { isMenuExpanded.value = true },
			shape = RoundedCornerShape(NineButtonStyle.cornerRadius)
		)
		{
			Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically)
			{
				// Display the current value assigned to the setting
				Text(modifier = Modifier.weight(0.8f),
					text = currValue, textAlign = TextAlign.Center, fontWeight = NineTextStyle.subTitle.fontWeight,
					fontSize = NineTextStyle.subTitle.fontSize, fontFamily = NineTextStyle.subTitle.fontFamily)

				// This icon signals to user that clicking on this button will enable a drop down menu
				Icon(modifier = Modifier.weight(0.2f),
					painter = painterResource(NineIconStyle.downArrowRound), contentDescription = null)

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
}