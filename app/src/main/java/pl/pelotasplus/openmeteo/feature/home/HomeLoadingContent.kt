package pl.pelotasplus.openmeteo.feature.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pl.pelotasplus.openmeteo.ui.ds.FullScreenLoader
import pl.pelotasplus.openmeteo.ui.theme.OpenMeteoTheme

@Composable
internal fun HomeLoadingContent(
    modifier: Modifier = Modifier
) {
    FullScreenLoader(modifier)
}

@Preview(showBackground = true)
@Composable
private fun HomeContentLoadingPreview() {
    OpenMeteoTheme {
        HomeLoadingContent()
    }
}
