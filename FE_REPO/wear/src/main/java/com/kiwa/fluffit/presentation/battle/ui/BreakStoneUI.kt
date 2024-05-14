package com.kiwa.fluffit.presentation.battle.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.ImageDecoderDecoder
import com.kiwa.fluffit.R
import com.kiwa.fluffit.presentation.util.formatTime
import kotlinx.coroutines.delay

@Composable
internal fun BreakStoneGameUI() {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            add(ImageDecoderDecoder.Factory())
        }.build()

    val descriptionVisibility = remember { mutableStateOf(false) }
    val descriptionCountDown = remember { mutableIntStateOf(3) }

    val time = 60 * 1000L
    val timer = remember { mutableStateOf(time.formatTime()) }
    val startTime = remember { mutableLongStateOf(0L) }
    val isTimerRunning = remember { mutableStateOf(false) }

    val score = remember { mutableIntStateOf(0) }

    val onCrush = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(isTimerRunning.value) {
        if (isTimerRunning.value && startTime.longValue == 0L) {
            startTime.longValue = System.currentTimeMillis()
            while (isTimerRunning.value) {
                val currentTime = System.currentTimeMillis()
                val elapsedTime = currentTime - startTime.longValue
                val newTimer = (time - elapsedTime).formatTime()
                timer.value = newTimer
                delay(10)
                if (elapsedTime >= time) {
                    timer.value = 0L.formatTime()
                    isTimerRunning.value = false
                }
            }
        }
    }

    LaunchedEffect(onCrush.value) {
        if (onCrush.value) {
            delay(10)
            onCrush.value = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = timer.value,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 8.dp),
            style = MaterialTheme.typography.display2
        )


        Box(modifier = Modifier
            .size(100.dp)
            .align(Alignment.Center)
            .clickable {
                if (isTimerRunning.value) {
                    onCrush.value = !onCrush.value
                    score.intValue++
                }
            }
        ) {
            if (!onCrush.value) {
                Image(
                    painter = rememberAsyncImagePainter(
                        imageLoader = imageLoader,
                        model = R.drawable.stone,
                    ),
                    contentDescription = null,
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.stone_hurt),
                    contentDescription = null
                )
            }
        }

        Text(
            text = "${score.intValue}",
            style = MaterialTheme.typography.display2,
            modifier = Modifier.align(Alignment.BottomCenter)
        )


        if (startTime.longValue == 0L) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(Alignment.Center)
            )
            {
                Text(
                    text = "60초동안 최대한 많이 돌을 깨보세요!",
                    style = MaterialTheme.typography.display3
                )

                Text(
                    text = "${descriptionCountDown.intValue}",
                    style = MaterialTheme.typography.title1.merge(
                        colorResource(id = R.color.watchBlue)
                    )
                )
            }

            LaunchedEffect(Unit) {
                while (descriptionCountDown.intValue > 0) {
                    delay(1000)
                    descriptionCountDown.intValue -= 1

                    if (descriptionCountDown.intValue < 1) {
                        descriptionVisibility.value = false
                        isTimerRunning.value = true
                    }
                }
            }
        }
    }
}
