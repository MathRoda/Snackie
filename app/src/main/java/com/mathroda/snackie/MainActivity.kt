package com.mathroda.snackie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mathroda.snackie.ui.theme.SnackieTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SnackieTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val success = rememberSnackieState()
                    val error = rememberSnackieState()
                    val custom = rememberSnackieState()

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(onClick = { success.addMessage("This is a Success Message!") }) {
                            Text(text = "Success")
                        }

                        Button(onClick = { error.addMessage("This is a Error Message!") }) {
                            Text(text = "Error")
                        }

                        Button(onClick = { custom.addMessage("This is a Custom Message!") }) {
                            Text(text = "Custom")
                        }
                    }

                    SnackieSuccess(state = success)

                    SnackieError(state = error)

                    SnackieCustom(state = custom, position = SnackiePosition.Float)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SnackieTheme {
        Greeting("Android")
    }
}