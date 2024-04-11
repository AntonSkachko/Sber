package com.anton.sber.ui.screen.defaultScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anton.sber.R
import com.anton.sber.ui.navigation.NavigationDestination
import com.anton.sber.ui.theme.SberTheme

object SecondDefaultDestination : NavigationDestination {
    override val route: String = "second_destination"
    override val titleRes: Int = R.string.first_default_screen
}

@Composable
fun SecondDefaultScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    navigateToSberAchievement: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.padding_large)
        )
    ) {
        BackHeadline(
            navigateTo = { navigateBack() },
            text = "Программа лояльности"
        )
        SberAim(
            modifier = Modifier
                .clickable { navigateToSberAchievement() }
        )
        SberUnloading()
    }
}

@Composable
fun BackHeadline(
    navigateTo: () -> Unit,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable { navigateTo() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_low))
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBackIosNew,
            contentDescription = null,
            modifier = Modifier
        )
        Text(
            text = text,
            style = MaterialTheme.typography.displayLarge
        )
    }
}

@Composable
fun SberAim(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(dimensionResource(id = R.dimen.second_height_of_card)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        ),
        shape = RoundedCornerShape(
            dimensionResource(id = R.dimen.card_shape)
        ),

    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {

            Row(
                modifier = Modifier
                    .padding(
                        start = dimensionResource(id = R.dimen.padding_medium),
                        end = dimensionResource(id = R.dimen.padding_medium)
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large))

            ) {
                Icon(
                    painter = painterResource(id = R.drawable.aim),
                    contentDescription = null,
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.second_size_of_image))
                        .weight(1f),
                )
                Column(
                    modifier = Modifier
                        .weight(3f)
                ) {
                    Text(
                        text = "СберЦели",
                        style = MaterialTheme.typography.displayMedium
                    )
                    Text(
                        text = "Выполняйте задания и получайте баллы!",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Icon(
                    imageVector = Icons.Filled.ArrowForwardIos,
                    contentDescription = null,
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.second_size_of_icon))
                        .weight(1f)
                )
            }
        }
    }
}

@Composable
fun SberUnloading(
    modifier: Modifier = Modifier
) {
    val scrollableState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(13f)) {
                Text(
                    text = "Это ваш март!",
                    style = MaterialTheme.typography.displayMedium
                )
                Text(
                    text = "Функция находиться в дальнейшей разработке",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Icon(
                imageVector = Icons.Filled.ArrowForwardIos,
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .size(dimensionResource(id = R.dimen.second_size_of_card))
            )
        }

        Row(
            modifier = Modifier
                .horizontalScroll(scrollableState),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
        ) {
            for (i in 1..3) {
                Card(
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.second_size_of_card)),
                    shape = RoundedCornerShape(
                        dimensionResource(id = R.dimen.card_shape)
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.background,
                    )

                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Пусто",
                            style = MaterialTheme.typography.displayLarge
                        )
                    }
                }
            }
        }

    }

}

@Composable
@Preview(showSystemUi = true)
fun SberAimPreview() {
    SberTheme {
        SecondDefaultScreen(navigateBack = { }) {

        }
    }
}
