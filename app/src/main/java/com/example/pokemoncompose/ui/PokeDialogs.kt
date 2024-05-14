package com.example.pokemoncompose.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ccink.resources.*

@Preview
@Composable
fun PreviewErrorDialog() {
    ErrorDialog(onDismissRequest = {})
}

@Composable
fun ProgressBar() {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.progress_bar_three)
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            isPlaying = true,
            modifier = Modifier.size(FiftySixDp)
        )
    }
}

@Composable
fun ErrorDialog(
    onDismissRequest: () -> Unit = {},
    message: String? = null
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(TwentyDp),
            colors = CardDefaults.outlinedCardColors(
                containerColor = MaterialTheme.colors.white
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        top = SixteenDp,
                        start = SixteenDp,
                        end = SixteenDp,
                        bottom = TwentyFourDp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                /**
                 * Error image
                 */
                Image(
                    modifier = Modifier.wrapContentSize(),
                    painter = painterResource(id = R.drawable.ic_error),
                    contentDescription = null,
                )

                /**
                 * Message text
                 */
                Text(
                    text = SomethingGoingWrong,
                    fontSize = EighteenSp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colors.color212529,
                    textAlign = TextAlign.Center
                )

                /**
                 * Detail information
                 */
                message?.let {
                    Text(
                        modifier = Modifier.padding(top = TenDp),
                        text = it,
                        fontSize = SixteenSp,
                        color = MaterialTheme.colors.color75212529,
                        textAlign = TextAlign.Center
                    )
                }

                /**
                 * Confirm button
                 */
                Button(
                    onClick = onDismissRequest,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = TwentyFourDp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colors.colorB066ff,
                    ),
                    shape = RoundedCornerShape(TenDp)
                ) {
                    Text(
                        text = MeanIt,
                        style = TextStyle(
                            fontSize = SixteenSp,
                            color = Color.White,
                        )
                    )
                }
            }
        }
    }
}