package pl.pelotasplus.openmeteo.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pl.pelotasplus.openmeteo.ui.theme.OpenMeteoTheme

@Composable
internal fun HomeLoadingContent(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeContentLoadingPreview() {
    OpenMeteoTheme {
        HomeLoadingContent()
    }
}
