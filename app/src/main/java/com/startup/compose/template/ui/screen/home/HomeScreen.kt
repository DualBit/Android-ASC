package com.startup.compose.template.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.startup.compose.template.ui.theme.StartupComposeTemplateTheme

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeContent(
        uiState = uiState,
        onUiEvent = viewModel::onUiEvent,
    )
}

@Composable
fun HomeContent(
    uiState: HomeUiState,
    onUiEvent: (HomeUiEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .navigationBarsPadding()
            .fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(
                    bottom = paddingValues.calculateBottomPadding()
                )
                .fillMaxSize()
                .background(Color.White)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Welcome!",
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    StartupComposeTemplateTheme {
        HomeContent(
            uiState = HomeUiState(),
            onUiEvent = {},
        )
    }
}