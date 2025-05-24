package com.example.examen2.planes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.examen2.R
import com.example.usecases.IrWhatsAppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlansViewModel @Inject constructor(
    private val irWhatsAppUseCase: IrWhatsAppUseCase
): ViewModel() {
    val plans: List<Plan> = listOf( Plan(
            title       = "Plan FLEX 5",
            oldPrice    = "270",
            newPrice    = "199",
            data        = "5 GB",
            features    = listOf(
                "Llamadas y SMS ilimitados",
                "Hotspot. Comparte tus datos",
                "Redes Sociales ilimitadas",
                "Arma tu plan con más apps",
                "CO₂ Negativo"
            ),
            socialIcons = listOf(
                R.drawable.ic_whatsapp,
                R.drawable.ic_facebook,
                R.drawable.ic_instagram
            ),
            ctaText     = "Quiero este plan"
        ),
    Plan(
    title       = "Plan FLEX 8",
    oldPrice    = "370",
    newPrice    = "299",
    data        = "8 GB",
    features    = listOf(
    "Llamadas ilimitadas",
    "SMS ilimitados",
    "Hotspot",
    "Apps ilimitadas",
    "CO₂ Negativo"
    ),
    socialIcons = listOf(
    R.drawable.ic_whatsapp,
    R.drawable.ic_facebook,
    R.drawable.ic_instagram
    ),
    ctaText     = "Quiero este plan"
    ),
    Plan(
    title       = "Plan FLEX 10",
    oldPrice    = "470",
    newPrice    = "399",
    data        = "10 GB",
    features    = listOf(
    "Todo ilimitado",
    "Hotspot",
    "Social media ilimitada",
    "Apps ilimitadas",
    "CO₂ Negativo"
    ),
    socialIcons = listOf(
    R.drawable.ic_whatsapp,
    R.drawable.ic_facebook,
    R.drawable.ic_instagram
    ),
    ctaText     = "Quiero este plan"
    )
    )
    var currentIndex by mutableStateOf(0)
        private set

    fun prev() { if (currentIndex > 0) currentIndex = currentIndex - 1
    println("currentIndex: $currentIndex") }
    fun next() { if (currentIndex < plans.lastIndex) currentIndex = currentIndex + 1
    println("currentIndex: $currentIndex") }
}
