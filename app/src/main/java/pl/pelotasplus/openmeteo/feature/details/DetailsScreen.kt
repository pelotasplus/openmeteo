package pl.pelotasplus.openmeteo.feature.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.pelotasplus.openmeteo.core.ObserveEffects

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = viewModel()
) {
    ObserveEffects(viewModel.effect) { effect ->
        when (effect) {
            else -> TODO()
        }
    }

    DetailsContent(
        modifier = modifier
    )
}

@Composable
private fun DetailsContent(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text("Details Content")
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailsContentPreview() {
    DetailsContent()
}
