package com.toquemedia.ekklesia.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.ui.theme.PrincipalColor
import com.toquemedia.ekklesia.utils.countries

@Composable
fun PhoneAuthScreen(
    state: AuthUiState,
    onSendCode: (String) -> Unit = {},
    onVerifyCode: (String) -> Unit = {},
    onCompleteProfile: (String, String) -> Unit = {_ ,_ -> },
    onBackToLogin: () -> Unit = {}
) {
    var phoneNumber by remember { mutableStateOf("") }
    var verificationCode by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var selectedCountry by remember { mutableStateOf(countries[0]) }
    var showCountryPicker by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.seedfy_logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(132.dp)
                    .padding(bottom = 8.dp)
            )

            Text(
                text = when {
                    state.needsProfileCompletion -> "Complete seu perfil"
                    !state.codeSent -> "Digite seu telefone"
                    else -> "Digite o código SMS"
                },
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            when {
                state.needsProfileCompletion -> {
                    OutlinedTextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        label = { Text("Primeiro nome") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        label = { Text("Último nome") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Button(
                        onClick = { onCompleteProfile(firstName, lastName) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        enabled = firstName.isNotEmpty() && lastName.isNotEmpty() && !state.isLoading,
                        colors = ButtonDefaults.buttonColors(containerColor = PrincipalColor)
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.White
                            )
                        } else {
                            Text("Continuar", fontSize = 16.sp)
                        }
                    }
                }
                !state.codeSent -> {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = selectedCountry.dialCode,
                            onValueChange = { },
                            label = { Text("Código") },
                            modifier = Modifier.weight(0.33f),
                            readOnly = true,
                            trailingIcon = {
                                IconButton(onClick = { showCountryPicker = true }) {
                                    Text(selectedCountry.flag)
                                }
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        OutlinedTextField(
                            value = phoneNumber,
                            onValueChange = { phoneNumber = it },
                            label = { Text("Número de telefone") },
                            placeholder = { Text("923 456 789") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            modifier = Modifier.weight(0.65f),
                            singleLine = true
                        )
                    }

                    DropdownMenu(
                        expanded = showCountryPicker,
                        onDismissRequest = { showCountryPicker = false }
                    ) {
                        countries.forEach { country ->
                            DropdownMenuItem(
                                text = {
                                    Row {
                                        Text(country.flag)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("${country.name} ${country.dialCode}")
                                    }
                                },
                                onClick = {
                                    selectedCountry = country
                                    showCountryPicker = false
                                }
                            )
                        }
                    }

                    Button(
                        onClick = { onSendCode(selectedCountry.dialCode + phoneNumber) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        enabled = phoneNumber.isNotEmpty() && !state.isLoading,
                        colors = ButtonDefaults.buttonColors(containerColor = PrincipalColor)
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.White
                            )
                        } else {
                            Text("Enviar código", fontSize = 16.sp)
                        }
                    }
                }
                else -> {
                    OutlinedTextField(
                        value = verificationCode,
                        onValueChange = { verificationCode = it },
                        label = { Text("Código de verificação") },
                        placeholder = { Text("123456") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Button(
                        onClick = { onVerifyCode(verificationCode) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        enabled = verificationCode.isNotEmpty() && !state.isLoading,
                        colors = ButtonDefaults.buttonColors(containerColor = PrincipalColor)
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.White
                            )
                        } else {
                            Text("Verificar código", fontSize = 16.sp)
                        }
                    }

                    TextButton(
                        onClick = {
                            state.resendCode?.invoke()
                        },
                        enabled = !state.isLoading
                    ) {
                        Text("Reenviar código")
                    }
                }
            }

            TextButton(onClick = onBackToLogin) {
                Text("Voltar para login")
            }

            state.error?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PhoneAuthScreenPrev() {
    PhoneAuthScreen(state = AuthUiState())
}