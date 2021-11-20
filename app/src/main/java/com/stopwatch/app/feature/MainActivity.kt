package com.stopwatch.app.feature

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.stopwatch.app.R
import com.stopwatch.app.common.navigation.NavigationDirections
import com.stopwatch.app.feature.login.LoginViewModel
import com.stopwatch.app.feature.timer.TimerViewModel
import com.stopwatch.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Math.cos
import java.lang.Math.sin
import kotlin.time.ExperimentalTime

@ExperimentalTime
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val navController = rememberNavController()
                Surface(color = MaterialTheme.colors.background) {
                    NavigationComponent(navController = navController)
                }
            }
        }
    }

    @Composable
    fun NavigationComponent(navController: NavHostController) {
        NavHost(
            navController = navController,
            startDestination = NavigationDirections.login.destination
        ) {
            composable(NavigationDirections.login.destination) {
                LoginNavigation(navController = navController)
            }
            composable(NavigationDirections.timer.destination) {
                TimerScreen()
            }
        }
    }

    @Composable
    fun LoginNavigation(navController: NavController, viewModel: LoginViewModel = hiltViewModel()) {
        when {
            viewModel.isLoggedIn() -> {
                navController.navigate(NavigationDirections.timer.destination) {
                    launchSingleTop = true
                }
            }
            else -> {
                val loginSuccess = viewModel.loginState
                if (loginSuccess) {
                    navController.navigate(NavigationDirections.timer.destination) {
                        launchSingleTop = true
                    }
                } else {
                    LoginScreen(viewModel = viewModel)
                }
            }
        }
    }

    @Composable
    fun LoginScreen(viewModel: LoginViewModel) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            var username by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }

            OutlinedTextField(
                isError = !viewModel.loginState,
                modifier = Modifier.fillMaxWidth(),
                value = username,
                onValueChange = { username = it },
                label = { Text(stringResource(R.string.username)) }
            )

            OutlinedTextField(
                isError = !viewModel.loginState,
                modifier = Modifier.fillMaxWidth(),
                value = password,
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = { password = it },
                label = { Text(stringResource(R.string.password)) }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("btnLogin"),
                onClick = {
                    viewModel.login(username, password)
                },
                enabled = username.isNotEmpty() && password.isNotEmpty()) {
                Text(
                    text = stringResource(R.string.login)
                )
            }
        }
    }

    @Composable
    fun TimerScreen(viewModel: TimerViewModel = hiltViewModel()) {
        val timerState = viewModel.timerState
        if (viewModel.shouldResumeTimerOnStart()) {
            viewModel.startTimer()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Stopwatch(viewModel)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                fontSize = 48.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                text = timerState.value.toString()
            )

            Spacer(modifier = Modifier.height(16.dp))

            val buttonColor = animateColorAsState(
                if (timerState.isRunning) Color.Red else MaterialTheme.colors.primary
            )
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = buttonColor.value),
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    when {
                        timerState.isRunning -> {
                            viewModel.stopTimer()
                        }
                        else -> {
                            viewModel.startTimer()
                        }
                    }
                }) {
                Text(
                    text = when {
                        timerState.isRunning -> {
                            stringResource(R.string.stop)
                        }
                        else -> {
                            stringResource(R.string.start)
                        }
                    }
                )
            }
        }
    }

    @Composable
    fun StopwatchTop() {
        Box(modifier = Modifier.wrapContentSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(modifier = Modifier.wrapContentSize()) {
                    Canvas(modifier = Modifier.size(70.dp, 70.dp)) {
                        drawArc(
                            color = Color.Black,
                            startAngle = 110f,
                            sweepAngle = 330f,
                            useCenter = false,
                            style = Stroke(16.dp.value, cap = StrokeCap.Round)
                        )
                    }
                    Canvas(modifier = Modifier
                        .size(35.dp, 20.dp)
                        .align(Alignment.BottomCenter)) {
                        drawRect(
                            color = Color.Gray
                        )
                        drawRect(
                            color = Color.Black,
                            style = Stroke(6f)
                        )
                    }
                }
                Canvas(modifier = Modifier.size(25.dp, 30.dp)) {
                    drawRect(
                        color = Color.Black
                    )
                }
            }
        }
    }

    @Composable
    fun StopwatchCircle() {
        Canvas(modifier = Modifier.size(220.dp)) {
            drawCircle(
                color = Color.Black,
                style = Stroke(24f)
            )
            drawCircle(
                color = Color.Gray
            )
        }
    }

    @Composable
    fun StopwatchButton(isRunning: Boolean) {
        Column(Modifier.wrapContentSize()) {
            Canvas(modifier = Modifier.wrapContentSize()) {
                val size = if(isRunning) 0f else 30f
                val degree = -Math.PI / 2 + (55 * Math.PI / 30)
                val x = center.x + cos(degree) * 345.dp.value
                val y = center.x + sin(degree) * 345.dp.value
                rotate(55f, pivot = Offset(x.toFloat(), y.toFloat())) {
                    drawRect(
                        color = Color.Gray,
                        size = Size(size, 40f),
                        topLeft = Offset(x.toFloat(), y.toFloat())
                    )
                }
            }
            Canvas(modifier = Modifier.wrapContentSize()) {
                val radiusMultiplier = if(isRunning) 335.dp.value else 360.dp.value
                val degree = -Math.PI / 2 + (55 * Math.PI / 30)
                val x = center.x + cos(degree) * radiusMultiplier
                val y = center.x + sin(degree) * radiusMultiplier
                rotate(55f, pivot = Offset(x.toFloat(), y.toFloat())) {
                    drawRect(
                        color = Color.Black,
                        size = Size(20f, 50f),
                        topLeft = Offset(x.toFloat() + 3, y.toFloat() - 3)
                    )
                }
            }
        }
    }

    @Composable
    fun StopwatchMarks() {
        val primaryColor = MaterialTheme.colors.primary
        Canvas(modifier = Modifier.wrapContentSize()) {
            val oneMinuteRadian = Math.PI / 30
            0.rangeTo(59).forEach { minute ->
                val isHour = minute % 5 == 0
                val degree = -Math.PI / 2 + (minute * oneMinuteRadian)
                val x = center.x + cos(degree) * 260.dp.value
                val y = center.x + sin(degree) * 260.dp.value

                val radius: Float = if (isHour) {
                    12f
                } else {
                    6f
                }
                val color = if (isHour) {
                    primaryColor
                } else {
                    Color.White
                }
                drawCircle(
                    color = color,
                    center = Offset(x.toFloat(), y.toFloat()),
                    radius = radius
                )
            }
        }
    }

    @Composable
    fun StopwatchHand(second: Long) {
        Canvas(modifier = Modifier.wrapContentSize()) {
            val degree = -Math.PI / 2 + (second * Math.PI / 30)
            val x = center.x + cos(degree) * 280.dp.value
            val y = center.x + sin(degree) * 280.dp.value
            drawLine(
                color = Color.Black,
                start = center,
                strokeWidth = 3f,
                end = Offset(x.toFloat(), y.toFloat())
            )
            drawCircle(
                color = Color.Black,
                center = center,
                radius = 6f
            )
        }
    }

    @Composable
    fun StopwatchTime(time: String) {
        Text(text = time, Modifier.padding(top = 56.dp), fontFamily = FontFamily.SansSerif, color = Color.White)
    }

    @Composable
    fun Stopwatch(viewModel: TimerViewModel) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            StopwatchTop()
            Box(contentAlignment = Alignment.Center) {
                StopwatchCircle()
                StopwatchMarks()
                StopwatchButton(viewModel.timerState.isRunning)
                StopwatchTime(viewModel.formattedElapsedTime(viewModel.timerState.value))
                StopwatchHand(viewModel.timerState.value)
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        AppTheme {
            StopwatchTop()
        }
    }
}

