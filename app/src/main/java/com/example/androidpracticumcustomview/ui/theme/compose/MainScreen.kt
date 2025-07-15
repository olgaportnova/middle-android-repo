package com.example.androidpracticumcustomview.ui.theme.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidpracticumcustomview.R

/*
Задание:
Реализуйте необходимые компоненты.
*/
@Composable
fun MainScreen(closeActivity: () -> Unit) {

    val firstText = stringResource(id = R.string.first_view)
    val secondText = stringResource(id = R.string.second_view)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable { closeActivity.invoke() },
            contentAlignment = Alignment.Center
        ) {
            CustomContainerCompose(
                firstChild = {
                    Text(
                        text = firstText,
                        fontSize = 22.sp,
                        color = Color.White,
                        modifier = Modifier
                            .background(Color(0xFFEF6C00), shape = RoundedCornerShape(16.dp))
                            .padding(20.dp)
                    )
                },
                secondChild = {
                    Text(
                        text = secondText,
                        fontSize = 22.sp,
                        color = Color.White,
                        modifier = Modifier
                            .background(Color(0xFF00ACC1), shape = RoundedCornerShape(16.dp))
                            .padding(20.dp)
                    )
                }
            )
        }
    }

