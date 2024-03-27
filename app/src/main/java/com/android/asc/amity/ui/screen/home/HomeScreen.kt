package com.android.asc.amity.ui.screen.home

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amity.socialcloud.uikit.community.home.activity.AmityCommunityHomePageActivity
import com.android.asc.amity.ui.theme.AndroidAmityTheme

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
    val context = LocalContext.current
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
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    val intent = Intent(context, AmityCommunityHomePageActivity::class.java)
                    context.startActivity(intent)
                }) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Open Amity",
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    AndroidAmityTheme {
        HomeContent(
            uiState = HomeUiState(),
            onUiEvent = {},
        )
    }
}