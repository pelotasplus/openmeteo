package pl.pelotasplus.openmeteo.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(),
    goToDetails: () -> Unit
) {
    HomeContent(
        modifier = modifier,
        onGoToDetailsClick = {
            goToDetails()
        }
    )
}

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    onGoToDetailsClick: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text("Home Content")

        Button(
            onClick = onGoToDetailsClick,
            content = {
                Text("Go to details")
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeContentPreview() {
    HomeContent()
}
