package com.anton.sber

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.anton.sber.ui.navigation.SberAppNavigation
import com.anton.sber.ui.screen.achievement.AchievementDestination
import com.anton.sber.ui.screen.achievement.AchievementViewModel
import com.anton.sber.ui.screen.control_panel.ControlPanelDestination
import com.anton.sber.ui.theme.SberTheme


@Composable
fun SberApp(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val achievementViewModel: AchievementViewModel = hiltViewModel()
    var selectedTabIndex by rememberSaveable {
        mutableStateOf(AchievementDestination.route)
    }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            SberButtonNavigation(
                onClick = {
                    navController.navigate(it)
                    selectedTabIndex = it
                },
                selectedTabIndex = selectedTabIndex
            )
        },
        topBar = { SberTopBar() }
    ) {
        SberAppNavigation(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            navController = navController,
            achievementViewModel = achievementViewModel
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SberTopBar(
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier.fillMaxWidth(),
        title = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium))
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.sign),
                    contentDescription = null,
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        dimensionResource(R.dimen.padding_low)
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ring),
                        contentDescription = null
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.exit),
                        contentDescription = null
                    )
                }
            }
        }
    )
}

@Composable
fun SberButtonNavigation(
    selectedTabIndex: String,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    val navigationItemContentList = listOf(
        ButtonNavigationItemContent(
            ControlPanelDestination.route,
            ControlPanelDestination.icon,
            ControlPanelDestination.titleRes
        ),
        ButtonNavigationItemContent(
            AchievementDestination.route,
            AchievementDestination.icon,
            AchievementDestination.titleRes
        )
    )
    NavigationBar(
        modifier = modifier
    ) {
        navigationItemContentList.forEach { buttonNavigationItemContent ->
            NavigationBarItem(
                selected = selectedTabIndex == buttonNavigationItemContent.route,
                onClick = {
                    onClick(buttonNavigationItemContent.route)
                },
                icon = {
                    Icon(
                        imageVector = buttonNavigationItemContent.icon,
                        contentDescription = stringResource(buttonNavigationItemContent.titleRes)
                    )
                },
                label = {
                    Text(
                        text = stringResource(buttonNavigationItemContent.titleRes)
                    )
                }
            )
        }
    }
}


data class ButtonNavigationItemContent(
    val route: String,
    val icon: ImageVector,
    val titleRes: Int
)


@Composable
@Preview()
fun SberTopBarPreview() {
    SberTheme {
        SberTopBar()
    }
}
