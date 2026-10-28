package com.example

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.CreamSurface
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GreatVibesFontFamily
import com.example.ui.theme.MarsalaDark
import com.example.ui.theme.MarsalaPrimary
import com.example.ui.theme.MontserratFontFamily
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.PlayfairFontFamily
import com.example.ui.theme.RoseAccent
import com.example.ui.theme.RosePill
import com.example.ui.theme.RoseSoft
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.WhatsAppGreen

// Constantes de contacto
private const val SALON_WHATSAPP_NUMBER = "5492983351206"
private const val SALON_DISPLAY_PHONE = "2983 35-1206"
private const val SALON_INTENT_DIAL = "+5492983351206"

// Modelo de datos para servicios
data class ServiceItem(
    val id: String,
    val category: String,
    val name: String,
    val price: String,
    val priceValue: Int
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                LumiereMainApp()
            }
        }
    }
}

@Composable
fun LumiereMainApp() {
    val navController = rememberNavController()
    // Lista de servicios seleccionados para agendar turno
    val selectedServices = remember { mutableStateListOf<ServiceItem>() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = CreamBackground,
        contentWindowInsets = WindowInsets(0.dp)
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "pantalla1",
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable("pantalla1") {
                PantallaInicio(
                    onIniciarClick = {
                        navController.navigate("pantalla2")
                    }
                )
            }
            composable("pantalla2") {
                PantallaPrecios(
                    selectedServices = selectedServices,
                    onToggleService = { item ->
                        if (selectedServices.any { it.id == item.id }) {
                            selectedServices.removeAll { it.id == item.id }
                        } else {
                            selectedServices.add(item)
                        }
                    },
                    onVerContactoClick = {
                        navController.navigate("pantalla3")
                    },
                    onVolverClick = {
                        navController.popBackStack()
                    }
                )
            }
            composable("pantalla3") {
                PantallaContacto(
                    selectedServices = selectedServices,
                    onVolverClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

// ==========================================
// PANTALLA 1: INICIO / PORTADA
// ==========================================
@Composable
fun PantallaInicio(onIniciarClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        // Contenido central con logo e identidad de marca
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 480.dp)
        ) {
            // Emblema circular con el logotipo generado
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .shadow(12.dp, CircleShape, spotColor = MarsalaPrimary.copy(alpha = 0.35f))
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(2.5.dp, GoldAccent, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.lumiere_logo),
                    contentDescription = "Logo Lumière Peluquería",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Título de la marca
            Text(
                text = "LUMIÈRE",
                fontSize = 38.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = PlayfairFontFamily,
                letterSpacing = 4.sp,
                color = TextDark,
                textAlign = TextAlign.Center
            )

            // Subtítulo con tipografía cursiva caligráfica
            Text(
                text = "— Peluquería —",
                fontSize = 32.sp,
                fontFamily = GreatVibesFontFamily,
                color = MarsalaPrimary,
                textAlign = TextAlign.Center
            )

            // Divisor ornamental con corazón
            OrnamentalHeartDivider(modifier = Modifier.padding(vertical = 14.dp))

            // Pilares de servicios destacados
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                PilarServicioCard(
                    icon = Icons.Default.Favorite,
                    titulo = "ALISADO",
                    subtitulo = "Brillo y sedosidad"
                )
                PilarServicioCard(
                    icon = Icons.Default.Palette,
                    titulo = "COLOR",
                    subtitulo = "Tendencia & vida"
                )
                PilarServicioCard(
                    icon = Icons.Default.ContentCut,
                    titulo = "CORTE",
                    subtitulo = "Estilo a medida"
                )
            }

            Spacer(modifier = Modifier.height(22.dp))

            // Tarjeta con mensaje de bienvenida
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = CreamSurface),
                border = androidx.compose.foundation.BorderStroke(1.dp, RoseSoft),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Tu cabello en las mejores manos",
                        fontFamily = MontserratFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = TextDark,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Productos profesionales, asesoramiento personalizado y resultados que te encantarán.",
                        fontFamily = MontserratFontFamily,
                        fontSize = 12.5.sp,
                        color = TextMuted,
                        textAlign = TextAlign.Center,
                        lineHeight = 18.sp
                    )
                }
            }
        }

        // Botón principal de acceso a la pantalla 2
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 480.dp)
        ) {
            Button(
                onClick = onIniciarClick,
                colors = ButtonDefaults.buttonColors(containerColor = MarsalaPrimary),
                shape = RoundedCornerShape(32.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .testTag("start_button")
            ) {
                Text(
                    text = "VER SERVICIOS Y PRECIOS",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = MontserratFontFamily,
                    letterSpacing = 1.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

// ==========================================
// PANTALLA 2: LISTA DE PRECIOS
// ==========================================
@Composable
fun PantallaPrecios(
    selectedServices: List<ServiceItem>,
    onToggleService: (ServiceItem) -> Unit,
    onVerContactoClick: () -> Unit,
    onVolverClick: () -> Unit
) {
    BackHandler { onVolverClick() }

    var searchQuery by remember { mutableStateOf("") }

    // Catálogo completo de servicios
    val allServices = remember {
        listOf(
            // ALISADO
            ServiceItem("ali_1", "Alisado ♡", "Alisado corto", "$25.000", 25000),
            ServiceItem("ali_2", "Alisado ♡", "Alisado media melena", "$30.000", 30000),
            ServiceItem("ali_3", "Alisado ♡", "Alisado largo", "$35.000", 35000),
            ServiceItem("ali_4", "Alisado ♡", "Alisado extra largo", "$40.000", 40000),

            // COLOR
            ServiceItem("col_1", "Color ♡", "Color completo", "$30.000", 30000),
            ServiceItem("col_2", "Color ♡", "Retoque de raíz", "$20.000", 20000),
            ServiceItem("col_3", "Color ♡", "Balayage / Babylights", "$45.000", 45000),
            ServiceItem("col_4", "Color ♡", "Mechas tradicionales", "$40.000", 40000),
            ServiceItem("col_5", "Color ♡", "Matización", "$15.000", 15000),

            // CORTE
            ServiceItem("cor_1", "Corte ♡", "Corte femenino", "$15.000", 15000),
            ServiceItem("cor_2", "Corte ♡", "Corte + lavado", "$18.000", 18000),
            ServiceItem("cor_3", "Corte ♡", "Corte flequillo", "$8.000", 8000),
            ServiceItem("cor_4", "Corte ♡", "Corte puntas", "$10.000", 10000)
        )
    }

    val filteredServices = remember(searchQuery) {
        if (searchQuery.isBlank()) {
            allServices
        } else {
            allServices.filter {
                it.name.contains(searchQuery, ignoreCase = true) ||
                it.category.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    val totalSelectedPrice = selectedServices.sumOf { it.priceValue }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Cabecera elegante con botón volver
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onVolverClick,
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(RoseSoft.copy(alpha = 0.5f))
                    .testTag("back_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = MarsalaPrimary
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "LUMIÈRE",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = PlayfairFontFamily,
                    letterSpacing = 3.sp,
                    color = TextDark
                )
                Text(
                    text = "— Lista de Precios —",
                    fontSize = 20.sp,
                    fontFamily = GreatVibesFontFamily,
                    color = MarsalaPrimary
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.size(44.dp))
        }

        // Campo de búsqueda sutil
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = {
                Text(
                    "Buscar servicio (ej. alisado, balayage, flequillo)...",
                    fontSize = 13.sp,
                    color = TextMuted,
                    fontFamily = MontserratFontFamily
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = MarsalaPrimary
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(24.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White.copy(alpha = 0.9f),
                focusedBorderColor = MarsalaPrimary,
                unfocusedBorderColor = RoseSoft
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 6.dp)
                .testTag("search_services_input")
        )

        // Lista de Servicios agrupados
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            val categories = filteredServices.map { it.category }.distinct()

            items(categories) { categoryTitle ->
                val itemsInCategory = filteredServices.filter { it.category == categoryTitle }

                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = CreamSurface),
                    border = androidx.compose.foundation.BorderStroke(1.2.dp, RoseSoft),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        // Etiqueta distintiva de categoría
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(14.dp))
                                .background(RoseSoft.copy(alpha = 0.65f))
                                .padding(horizontal = 16.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = categoryTitle,
                                fontSize = 22.sp,
                                fontFamily = GreatVibesFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = MarsalaPrimary
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Filas de servicios
                        itemsInCategory.forEachIndexed { index, service ->
                            val isSelected = selectedServices.any { it.id == service.id }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (isSelected) RosePill else Color.Transparent)
                                    .clickable { onToggleService(service) }
                                    .padding(horizontal = 8.dp, vertical = 8.dp)
                                    .testTag("service_item_${service.id}"),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    // Indicador de selección táctil
                                    Box(
                                        modifier = Modifier
                                            .size(22.dp)
                                            .clip(CircleShape)
                                            .background(if (isSelected) MarsalaPrimary else Color.Transparent)
                                            .border(
                                                1.5.dp,
                                                if (isSelected) MarsalaPrimary else RoseAccent,
                                                CircleShape
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (isSelected) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = "Seleccionado",
                                                tint = Color.White,
                                                modifier = Modifier.size(14.dp)
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.width(10.dp))

                                    Text(
                                        text = service.name,
                                        fontSize = 14.5.sp,
                                        fontFamily = MontserratFontFamily,
                                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                        color = TextDark,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }

                                Text(
                                    text = service.price,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = MontserratFontFamily,
                                    color = MarsalaPrimary
                                )
                            }

                            if (index < itemsInCategory.size - 1) {
                                HorizontalDivider(
                                    modifier = Modifier.padding(horizontal = 8.dp),
                                    color = RoseSoft.copy(alpha = 0.5f),
                                    thickness = 0.8.dp
                                )
                            }
                        }
                    }
                }
            }

            // Nota final poética y distintiva de la marca
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Tu pelo, nuestra mejor versión ♡",
                        fontSize = 25.sp,
                        fontFamily = GreatVibesFontFamily,
                        color = MarsalaPrimary,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Toca cualquier servicio para seleccionarlo o solicita tu turno directamente.",
                        fontSize = 11.5.sp,
                        fontFamily = MontserratFontFamily,
                        color = TextMuted,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        // Barra inferior interactiva con botón de navegación a la pantalla 3 (Contacto)
        Surface(
            color = Color.White,
            shadowElevation = 10.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Si hay servicios seleccionados, mostramos el resumen dinámico
                AnimatedVisibility(
                    visible = selectedServices.isNotEmpty(),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${selectedServices.size} seleccionado(s)",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = MontserratFontFamily,
                            color = TextMuted
                        )
                        Text(
                            text = "Estimado: $${formatPrice(totalSelectedPrice)}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = MontserratFontFamily,
                            color = MarsalaPrimary
                        )
                    }
                }

                Button(
                    onClick = onVerContactoClick,
                    colors = ButtonDefaults.buttonColors(containerColor = MarsalaPrimary),
                    shape = RoundedCornerShape(30.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 5.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .testTag("go_to_contact_button")
                ) {
                    Text(
                        text = if (selectedServices.isEmpty()) "SOLICITAR TURNO" else "SOLICITAR TURNO (${selectedServices.size})",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = MontserratFontFamily,
                        letterSpacing = 1.sp,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

// ==========================================
// PANTALLA 3: CONTACTO Y WHATSAPP
// ==========================================
@Composable
fun PantallaContacto(
    selectedServices: List<ServiceItem>,
    onVolverClick: () -> Unit
) {
    BackHandler { onVolverClick() }

    val context = LocalContext.current

    // Preparamos el mensaje personalizado de WhatsApp
    val mensajeWhatsApp = remember(selectedServices) {
        if (selectedServices.isEmpty()) {
            "¡Hola Lumière Peluquería! Quisiera consultar por un turno disponible."
        } else {
            val listaText = selectedServices.joinToString(", ") { "${it.name} (${it.price})" }
            val total = selectedServices.sumOf { it.priceValue }
            "¡Hola Lumière Peluquería! Quisiera pedir un turno para: $listaText. Total estimado: $${formatPrice(total)}."
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp, vertical = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Cabecera superior con botón volver
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onVolverClick,
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(RoseSoft.copy(alpha = 0.5f))
                    .testTag("contact_back_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = MarsalaPrimary
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Contacto",
                fontSize = 18.sp,
                fontFamily = MontserratFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = MarsalaPrimary
            )
            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.size(44.dp))
        }

        // Cuerpo central idéntico al diseño original
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 480.dp)
        ) {
            // Nombre de la peluquería en tipografía Serif
            Text(
                text = "LUMIÈRE",
                fontSize = 38.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = PlayfairFontFamily,
                letterSpacing = 4.sp,
                color = TextDark,
                textAlign = TextAlign.Center
            )

            // Subtítulo en caligrafía
            Text(
                text = "— Peluquería —",
                fontSize = 30.sp,
                fontFamily = GreatVibesFontFamily,
                color = MarsalaPrimary,
                textAlign = TextAlign.Center
            )

            OrnamentalHeartDivider(modifier = Modifier.padding(vertical = 12.dp))

            Spacer(modifier = Modifier.height(10.dp))

            // Etiqueta destacada "Mi contacto"
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .background(RoseSoft)
                    .padding(horizontal = 34.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Mi contacto",
                    fontSize = 30.sp,
                    fontFamily = GreatVibesFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = MarsalaPrimary
                )
            }

            Spacer(modifier = Modifier.height(26.dp))

            // Número en pantalla con botón para copiar
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CreamSurface),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, RoseAccent),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "✆",
                        fontSize = 26.sp,
                        color = MarsalaPrimary
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "2983351206",
                        fontSize = 32.sp,
                        fontFamily = GreatVibesFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = MarsalaPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = {
                            copyToClipboard(context, "2983351206")
                        },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copiar número",
                            tint = RoseAccent,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            OrnamentalHeartDivider(modifier = Modifier.padding(vertical = 18.dp))

            // Frase cariñosa
            Text(
                text = "♡ ¡Te espero! ♡",
                fontSize = 36.sp,
                fontFamily = GreatVibesFontFamily,
                color = MarsalaPrimary,
                textAlign = TextAlign.Center
            )

            // Resumen de servicios si fueron marcados
            if (selectedServices.isNotEmpty()) {
                Spacer(modifier = Modifier.height(14.dp))
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = RosePill),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "Servicios seleccionados:",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = MontserratFontFamily,
                            color = MarsalaDark
                        )
                        selectedServices.forEach { item ->
                            Text(
                                text = "• ${item.name} (${item.price})",
                                fontSize = 12.sp,
                                fontFamily = MontserratFontFamily,
                                color = TextDark
                            )
                        }
                    }
                }
            }
        }

        // Botones de acción (WhatsApp + Opciones alternativas)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 480.dp)
        ) {
            // Botón oficial y destacado de WhatsApp
            Button(
                onClick = {
                    abrirWhatsAppDirecto(
                        context = context,
                        telefono = SALON_WHATSAPP_NUMBER,
                        mensaje = mensajeWhatsApp
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen),
                shape = RoundedCornerShape(32.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp)
                    .testTag("whatsapp_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Chat,
                    contentDescription = "WhatsApp",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "ENVIAR WHATSAPP",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = MontserratFontFamily,
                    letterSpacing = 0.5.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Botón de llamada o acción secundaria
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = {
                        llamarTelefono(context, SALON_INTENT_DIAL)
                    },
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MarsalaPrimary),
                    border = androidx.compose.foundation.BorderStroke(1.dp, RoseAccent),
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                        .testTag("call_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Llamar",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = MontserratFontFamily
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                OutlinedButton(
                    onClick = {
                        copyToClipboard(context, SALON_DISPLAY_PHONE)
                    },
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MarsalaPrimary),
                    border = androidx.compose.foundation.BorderStroke(1.dp, RoseAccent),
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                        .testTag("copy_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Copiar",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = MontserratFontFamily
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = onVolverClick,
                modifier = Modifier.testTag("back_to_catalog_button")
            ) {
                Text(
                    text = "← Volver a la lista de precios",
                    color = TextMuted,
                    fontSize = 13.5.sp,
                    fontFamily = MontserratFontFamily
                )
            }
        }
    }
}

// ==========================================
// COMPONENTES AUXILIARES DE DISEÑO
// ==========================================
@Composable
fun PilarServicioCard(
    icon: ImageVector,
    titulo: String,
    subtitulo: String
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CreamSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, RoseSoft),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.width(100.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 12.dp, horizontal = 6.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(RoseSoft.copy(alpha = 0.6f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = titulo,
                    tint = MarsalaPrimary,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = titulo,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = MontserratFontFamily,
                letterSpacing = 1.sp,
                color = MarsalaPrimary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitulo,
                fontSize = 9.sp,
                fontFamily = MontserratFontFamily,
                color = TextMuted,
                textAlign = TextAlign.Center,
                lineHeight = 12.sp
            )
        }
    }
}

@Composable
fun OrnamentalHeartDivider(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 28.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = RoseSoft,
            thickness = 1.dp
        )
        Text(
            text = "  ♡  ",
            fontSize = 15.sp,
            color = MarsalaPrimary,
            fontWeight = FontWeight.Bold
        )
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = RoseSoft,
            thickness = 1.dp
        )
    }
}

// ==========================================
// ACCIONES DEL SISTEMA (WHATSAPP, LLAMADA, PORTAPAPELES)
// ==========================================
private fun abrirWhatsAppDirecto(context: Context, telefono: String, mensaje: String) {
    val urlEncoded = Uri.encode(mensaje)

    // Intento 1: Abrir la app de WhatsApp directamente
    val intentApp = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse("whatsapp://send?phone=$telefono&text=$urlEncoded")
    }

    // Intento 2: Abrir vía HTTPS (funciona con WhatsApp o navegador web)
    val intentWeb = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse("https://api.whatsapp.com/send?phone=$telefono&text=$urlEncoded")
    }

    try {
        context.startActivity(intentApp)
    } catch (e: Exception) {
        try {
            context.startActivity(intentWeb)
        } catch (e2: Exception) {
            copyToClipboard(context, telefono)
            Toast.makeText(
                context,
                "Número copiado al portapapeles: $telefono",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}

private fun llamarTelefono(context: Context, telefono: String) {
    try {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$telefono")
        }
        context.startActivity(intent)
    } catch (e: Exception) {
        copyToClipboard(context, telefono)
        Toast.makeText(context, "Número copiado: $telefono", Toast.LENGTH_SHORT).show()
    }
}

private fun copyToClipboard(context: Context, texto: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
    val clip = ClipData.newPlainText("Teléfono Lumière", texto)
    clipboard?.setPrimaryClip(clip)
    Toast.makeText(context, "Copiado: $texto", Toast.LENGTH_SHORT).show()
}

private fun formatPrice(price: Int): String {
    return String.format("%,d", price).replace(',', '.')
}
