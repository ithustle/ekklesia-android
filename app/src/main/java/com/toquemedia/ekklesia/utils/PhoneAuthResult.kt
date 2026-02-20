package com.toquemedia.ekklesia.utils

import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

sealed class PhoneAuthResult {
    data class CodeSent(
        val verificationId: String,
        val token: PhoneAuthProvider.ForceResendingToken
    ) : PhoneAuthResult()
    
    data class VerificationCompleted(val credential: PhoneAuthCredential) : PhoneAuthResult()
    data class VerificationFailed(val error: String) : PhoneAuthResult()
}

data class Country(
    val name: String,
    val code: String,
    val flag: String,
    val dialCode: String
)

val countries = listOf(
    Country("Angola", "AO", "🇦🇴", "+244"),
    Country("Brasil", "BR", "🇧🇷", "+55"),
    Country("Portugal", "PT", "🇵🇹", "+351"),
    Country("Estados Unidos", "US", "🇺🇸", "+1"),
    Country("Reino Unido", "GB", "🇬🇧", "+44"),
    Country("França", "FR", "🇫🇷", "+33"),
    Country("Alemanha", "DE", "🇩🇪", "+49"),
    Country("Espanha", "ES", "🇪🇸", "+34"),
    Country("Itália", "IT", "🇮🇹", "+39"),
    Country("Canadá", "CA", "🇨🇦", "+1"),
    Country("México", "MX", "🇲🇽", "+52"),
    Country("Argentina", "AR", "🇦🇷", "+54"),
    Country("Chile", "CL", "🇨🇱", "+56"),
    Country("Colômbia", "CO", "🇨🇴", "+57"),
    Country("Peru", "PE", "🇵🇪", "+51"),
    Country("Venezuela", "VE", "🇻🇪", "+58"),
    Country("Uruguai", "UY", "🇺🇾", "+598"),
    Country("Paraguai", "PY", "🇵🇾", "+595"),
    Country("Bolívia", "BO", "🇧🇴", "+591"),
    Country("Equador", "EC", "🇪🇨", "+593"),
    Country("Moçambique", "MZ", "🇲🇿", "+258"),
    Country("Cabo Verde", "CV", "🇨🇻", "+238"),
    Country("Guiné-Bissau", "GW", "🇬🇼", "+245"),
    Country("São Tomé", "ST", "🇸🇹", "+239"),
    Country("África do Sul", "ZA", "🇿🇦", "+27"),
    Country("Nigéria", "NG", "🇳🇬", "+234"),
    Country("Egito", "EG", "🇪🇬", "+20"),
    Country("Marrocos", "MA", "🇲🇦", "+212"),
    Country("Quênia", "KE", "🇰🇪", "+254"),
    Country("Gana", "GH", "🇬🇭", "+233"),
    Country("Etiópia", "ET", "🇪🇹", "+251"),
    Country("Uganda", "UG", "🇺🇬", "+256"),
    Country("Ruanda", "RW", "🇷🇼", "+250"),
    Country("Tanzânia", "TZ", "🇹🇿", "+255"),
    Country("China", "CN", "🇨🇳", "+86"),
    Country("Japão", "JP", "🇯🇵", "+81"),
    Country("Coreia do Sul", "KR", "🇰🇷", "+82"),
    Country("Índia", "IN", "🇮🇳", "+91"),
    Country("Indonésia", "ID", "🇮🇩", "+62"),
    Country("Tailândia", "TH", "🇹🇭", "+66"),
    Country("Vietnã", "VN", "🇻🇳", "+84"),
    Country("Filipinas", "PH", "🇵🇭", "+63"),
    Country("Malásia", "MY", "🇲🇾", "+60"),
    Country("Singapura", "SG", "🇸🇬", "+65"),
    Country("Austrália", "AU", "🇦🇺", "+61"),
    Country("Nova Zelândia", "NZ", "🇳🇿", "+64"),
    Country("Rússia", "RU", "🇷🇺", "+7"),
    Country("Ucrânia", "UA", "🇺🇦", "+380"),
    Country("Polônia", "PL", "🇵🇱", "+48"),
    Country("Holanda", "NL", "🇳🇱", "+31"),
    Country("Bélgica", "BE", "🇧🇪", "+32"),
    Country("Suíça", "CH", "🇨🇭", "+41"),
    Country("Áustria", "AT", "🇦🇹", "+43"),
    Country("Suécia", "SE", "🇸🇪", "+46")
)