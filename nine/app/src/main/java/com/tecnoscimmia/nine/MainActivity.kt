package com.tecnoscimmia.nine

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tecnoscimmia.nine.ui.MainScreen

@Preview
@Composable
fun TestFunc()
{
	MainScreen()
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