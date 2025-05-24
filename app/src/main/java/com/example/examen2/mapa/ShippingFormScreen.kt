// file: ShippingFormScreen.kt
package com.example.examen2.mapa

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun ShippingFormScreen() {
    val context = LocalContext.current

    var phoneRef by remember { mutableStateOf("") }
    var selectedPos by remember { mutableStateOf(LatLng(-16.4897, -68.1193)) } // ejemplo Cochabamba

    val cameraState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(selectedPos, 14f)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Dónde enviaremos su SIM",
            style = androidx.compose.material3.MaterialTheme.typography.headlineSmall
        )
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = phoneRef,
            onValueChange = { phoneRef = it },
            label = { Text("Teléfono de referencia") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = selectedPos.latitude.toString(),
                onValueChange = {},
                label = { Text("Latitud") },
                enabled = false,
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = selectedPos.longitude.toString(),
                onValueChange = {},
                label = { Text("Longitud") },
                enabled = false,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(Modifier.height(16.dp))

        // Mapa
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraState,
                onMapClick = { latLng ->
                    selectedPos = latLng
                    // reubica cámara suavemente
                    cameraState.animate(CameraUpdateFactory.newLatLng(latLng))
                }
            ) {
                Marker(
                    position = selectedPos,
                    title = "Envio aquí"
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                // aquí procesas el envío, p.ej. llamas a tu UseCase
                Toast.makeText(context, "Enviando SIM a $phoneRef", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Confirmar envío")
        }
    }
}
