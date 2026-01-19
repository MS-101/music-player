package com.example.musicplayer.components

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.musicplayer.ui.theme.MusicPlayerTheme

@Composable
fun NavPanelItem(
    navController: NavController,
    route: String,
    label: String
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val destination = navBackStackEntry?.destination
    val routeActive = destination?.hierarchy?.any { it.route == route } == true

    TextButton(
        onClick = {
            navController.navigate(route) {
                popUpTo(navController.graph.id) {
                    inclusive = true
                }
            }
        }
    ) {
        Text(
            label,
            color = Color.Black,
            fontWeight = if (routeActive) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NavPanelItemPreview() {
    MusicPlayerTheme {
        NavPanelItem(
            navController = rememberNavController(),
            "foo_route",
            "Foo"
        )
    }
}