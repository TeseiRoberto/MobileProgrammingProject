package com.tecnoscimmia.nine.view.widgets

/*
 * This file contains the definitions of all the widgets that are used in the scoreboard screen
 */

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tecnoscimmia.nine.R
import com.tecnoscimmia.nine.model.MatchResult
import com.tecnoscimmia.nine.ui.theme.NineColors
import com.tecnoscimmia.nine.ui.theme.NineIconStyle
import com.tecnoscimmia.nine.ui.theme.NineTextStyle


// A lazy column used to display data about matches played in the past, it's used in the scoreboard screen,
// note that this function does not sort the list in any way!
@Composable
fun Scoreboard(data: List<MatchResult>, widthOccupation: Float, heightOccupation: Float)
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

		if(data.isEmpty())
		{
			Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically)
			{
				Text(text = stringResource(id = R.string.scoreboard_message_is_empty), modifier = Modifier.padding(vertical = 48.dp),
					textAlign = TextAlign.Center, fontWeight = NineTextStyle.subTitle.fontWeight,
					fontSize = NineTextStyle.subTitle.fontSize, fontFamily = NineTextStyle.subTitle.fontFamily)
			}
		} else {
			LazyColumn(modifier = Modifier.fillMaxWidth())
			{
				itemsIndexed(data)
				{ index, match ->
					ScoreboardEntry(rank = index + 1, time = match.duration, date = match.date, gameMode = match.gameMode)
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
				Icon(
					painterResource(id = NineIconStyle.trophy), contentDescription = null,
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
