package com.example.androidpracticumcustomview.ui.theme.compose

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay


/*
Задание:
Реализуйте необходимые компоненты;
Создайте проверку что дочерних элементов не более 2-х;
Предусмотрите обработку ошибок рендера дочерних элементов.
Задание по желанию:
Предусмотрите параметризацию длительности анимации.
 */
@Composable
fun CustomContainerCompose(
    firstChild: @Composable (() -> Unit)?,
    secondChild: @Composable (() -> Unit)?
) {

    val childrenCount = listOfNotNull(firstChild, secondChild).size
    require(childrenCount <= 2) {
        "CustomContainerCompose поддерживает не более двух дочерних элементов"
    }
    var firstVisible by remember { mutableStateOf(false) }
    var secondVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (firstChild != null) {
            firstVisible = true
        }

        if (secondChild != null) {
            delay(3000L)
            secondVisible = true
        }
    }

    // Основной контейнер
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        if (firstChild != null) {
            AnimatedSlideInFromCenter(
                isVisible = firstVisible,
                direction = SlideDirection.Up,
                modifier = Modifier.align(Alignment.TopCenter)
            ) {
                firstChild()
            }
        }

        if (secondChild != null) {
            AnimatedSlideInFromCenter(
                isVisible = secondVisible,
                direction = SlideDirection.Down,
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                secondChild()
            }
        }
    }
}

@Composable
fun AnimatedSlideInFromCenter(
    isVisible: Boolean,
    direction: SlideDirection,
    modifier: Modifier = Modifier,
    durationMillis: Int = 5000,
    content: @Composable () -> Unit
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val density = LocalDensity.current

    val centerOffsetY = with(density) { (screenHeight / 2).toPx() }

    val targetOffsetY = when (direction) {
        SlideDirection.Up -> centerOffsetY
        SlideDirection.Down -> -centerOffsetY
    }

    val offsetY by animateFloatAsState(
        targetValue = if (isVisible) 0f else targetOffsetY,
        animationSpec = tween(durationMillis),
        label = "OffsetY"
    )

    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(durationMillis),
        label = "Alpha"
    )

    Box(
        modifier = modifier
            .graphicsLayer {
                translationY = offsetY
                this.alpha = alpha
            }
    ) {
        content()
    }
}


