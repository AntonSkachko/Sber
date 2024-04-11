package com.anton.sber.ui.screen.defaultScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.HeartBroken
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anton.sber.R
import com.anton.sber.ui.navigation.NavigationDestination
import com.anton.sber.ui.theme.SberTheme

object FirstDefaultDestination: NavigationDestination {
    override val route: String = "first_screen"
    override val titleRes: Int = R.string.first_default_screen
}

@Composable
fun FirstDefaultScreen(
    modifier: Modifier = Modifier,
    navigateTo: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_medium))
    ) {

        Row(
            horizontalArrangement = Arrangement
                .spacedBy(dimensionResource(id = R.dimen.padding_medium)),
            modifier = Modifier
                .padding(
                    top = dimensionResource(id = R.dimen.padding_medium),
                    bottom = dimensionResource(id = R.dimen.padding_large)
                )
                .horizontalScroll(scrollState),

        ) {
            RoutesItem(
                text = "Избранные платежи",
                imageVector = Icons.Filled.HeartBroken
            )
            RoutesItem(
                text = "Программа лояльности",
                imageVector = Icons.Filled.Star,
                modifier = Modifier
                    .clickable { navigateTo() }
            )
            RoutesItem(
                text = "Ерип",
                imageVector = Icons.Filled.ArrowForward
            )
        }
        ProductPart()
    }
}

@Composable
fun ProductPart(
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier
    ) {
        Text(
            text = "Продукты",
            modifier = Modifier
                .padding(bottom = dimensionResource(id = R.dimen.padding_large)),
            style = MaterialTheme.typography.displayLarge
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.first_height_of_card)),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background,
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 1.dp
            ),
        ) {
        }
    }
}


@Composable
fun RoutesItem(
    modifier: Modifier = Modifier,
    text: String,
    imageVector: ImageVector
) {
    Card(
        modifier = modifier
            .size(dimensionResource(id = R.dimen.size_of_box)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        ),
    ) {
        Column(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.space_between))
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = null,
                modifier.size(dimensionResource(id = R.dimen.first_size_of_icon))
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun FirstDefaultScreenPreview() {
    SberTheme {
        FirstDefaultScreen(
            navigateTo = {}
        )
    }
}
