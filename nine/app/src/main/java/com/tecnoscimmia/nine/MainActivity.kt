package com.tecnoscimmia.nine

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun TestFunc()
{
	Row(modifier = Modifier.fillMaxWidth(1f), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.Top)
	{
		Text(text = "Hello world!")
	}
}


class MainActivity : ComponentActivity()
{

	override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?)
	{
		super.onCreate(savedInstanceState, persistentState)

		setContent {
			TestFunc()
		}
	}
}