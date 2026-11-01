package com.example.nookink

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.nookink.ui.theme.AppBibliotecaTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppBibliotecaTheme {
                AppPantallas()
            }
        }
    }

    @Preview
    @Composable
    private fun MainActivityPreview() {
        AppBibliotecaTheme {
            AppPantallas()
        }
    }

}