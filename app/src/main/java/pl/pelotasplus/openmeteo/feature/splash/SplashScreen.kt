package pl.pelotasplus.openmeteo.feature.splash

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import pl.pelotasplus.openmeteo.R
import pl.pelotasplus.openmeteo.ui.ds.FullScreenLoader
import pl.pelotasplus.openmeteo.ui.theme.OpenMeteoTheme

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    permissionsGranted: () -> Unit
) {
    val permissionState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        )
    )

    if (!permissionState.allPermissionsGranted) {
        Column(
            modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(stringResource(R.string.splash_location_permission_message))
            Button(
                modifier = Modifier.padding(top = 16.dp),
                onClick = { permissionState.launchMultiplePermissionRequest() }
            ) {
                Text(stringResource(R.string.splash_location_permission_cta))
            }
        }
    } else {
        FullScreenLoader(modifier)
        permissionsGranted()
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashScreenPreview() {
    OpenMeteoTheme {
        SplashScreen(
            permissionsGranted = {}
        )
    }
}
