package com.example.denniknotification

data class Category(
    val id: String,
    val color: String,
    val icon: String
)

val categories = listOf(
    // PRODUKTÍVNE (zelené)
    Category("praca", "#4ade80", "💼"),       // Práca, bloky, učenie
    Category("skola", "#60a5fa", "📚"),       // Škola, štúdium
    Category("citanie", "#74b9ff", "📖"),     // Knihy, články, učenie sa

    // ŽIVOTNÉ (modré)
    Category("jedlo", "#fdcb6e", "🍽️"),       // Raňajky, obed, večera, olovrant
    Category("domacnost", "#00b894", "🏠"),   // Upratovanie, varenie, hygiena
    Category("sport", "#f472b6", "🏃"),       // Cvičenie, fitko, bazén, prechádzka

    // SOCIÁLNE (fialové)
    Category("social", "#6c5ce7", "👥"),      // Rodina, kamaráti, debaty

    // ZÁBAVA (červené/žlté - NEPRODUKTÍVNE)
    Category("anime", "#ff6b6b", "🎬"),       // Anime, seriály, filmy
    Category("hry", "#a78bfa", "🎮"),         // Hry (PC, mobil, konzoly)
    Category("skrol", "#fbbf24", "📱"),       // Sociálne siete, doomscrolling

    /* ostatné */
    Category("other", "#9ca3af", "📌")
)