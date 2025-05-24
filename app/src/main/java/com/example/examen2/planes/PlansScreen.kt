// file: app/src/main/java/com/example/examen2/planes/PlansUI.kt
package com.example.examen2.planes

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.examen2.R
import androidx.hilt.navigation.compose.hiltViewModel


/** Modelo de datos; tú lo completas o importas desde tu VM/domain */
data class Plan(
    val title: String,
    val oldPrice: String,
    val newPrice: String,
    val data: String,
    val features: List<String>,
    val socialIcons: List<Int>, // IDs de drawable
    val ctaText: String
)

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PlansCarousel(
    vm: PlansViewModel = hiltViewModel(),
    onPlanSelect: () -> Unit
) {
    val plans: List<Plan> = vm.plans
    var currentIndex: Int = vm.currentIndex
    val onPrev: () -> Unit = vm::prev
    val onNext: () -> Unit = vm::next
    if (plans.isEmpty()) return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Nuestros planes móviles",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            "La mejor cobertura, increíbles beneficios\n" +
                    "y un compromiso con el planeta.",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Box(modifier = Modifier.weight(1f)
            .fillMaxWidth()
            , contentAlignment = Alignment.Center) {

            // Animación de carrusel basada en el índice
            AnimatedContent(
                targetState = currentIndex,
                transitionSpec = {
                    if (targetState > initialState) {
                        slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec    = tween(300)
                        ) with slideOutHorizontally(
                            targetOffsetX   = { -it },
                            animationSpec   = tween(300)
                        )
                    } else {
                        slideInHorizontally(
                            initialOffsetX = { -it },
                            animationSpec    = tween(300)
                        ) with slideOutHorizontally(
                            targetOffsetX   = { it },
                            animationSpec   = tween(300)
                        )
                    }
                }
            ) { index ->
                PlanCard(plans[index], onPlanSelect)
            }

            // ← Anterior
            if (currentIndex > 0) {
                println(currentIndex)
                println("entre arrow left")
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_left),
                    contentDescription = "Anterior",
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .size(32.dp)
                        .clickable { onPrev() }
                )
            }
            // → Siguiente
            if (currentIndex < plans.lastIndex) {
                Icon(
                    painter = painterResource(com.example.examen2.R.drawable.ic_arrow_right),
                    contentDescription = "Siguiente",
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(32.dp)
                        .clickable { onNext() }
                )
            }
        }
    }
}

@Composable
private fun PlanCard(plan: Plan, onSelectPlan: () -> Unit) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .wrapContentHeight(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(plan.title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))

            // Precio anterior tachado
            Text(
                text = "$${plan.oldPrice} /mes",
                style = MaterialTheme.typography.bodyMedium.copy(
                    textDecoration = TextDecoration.LineThrough
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(4.dp))

            // Precio actual
            Row(verticalAlignment = Alignment.Bottom) {
                Text("Ahora ", style = MaterialTheme.typography.bodyLarge)
                Text(
                    "$${plan.newPrice}",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
                Text("/mes", style = MaterialTheme.typography.bodyLarge)
            }

            Text(plan.data, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(12.dp))

            // Características
            plan.features.forEach { feat ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_check),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(feat, style = MaterialTheme.typography.bodyMedium)
                }
            }

            Spacer(Modifier.height(12.dp))

            // Iconos sociales
            LazyRow {
                items(plan.socialIcons) { iconRes ->
                    Image(
                        painter = painterResource(iconRes),
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp)
                            .padding(end = 8.dp)
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = onSelectPlan,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(plan.ctaText)
            }
            WhatsAppButton()
        }
    }
}

@Composable
fun WhatsAppButton() {
    val context = LocalContext.current
    val phoneNumber = "59174058962"   // Número con código de país, sin "+"
    val message     = "Hola, UCB mobile, preciso su ayuda"
    val uriText     = "https://api.whatsapp.com/send?phone=$phoneNumber&text=${Uri.encode(message)}"

    IconButton(onClick = {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uriText))
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "No se pudo abrir WhatsApp", Toast.LENGTH_SHORT).show()
        }
    }

    ) {
        Image(
            painter = painterResource(R.drawable.ic_whatsapp),
            contentDescription = "WhatsApp",
        )
    }
}

