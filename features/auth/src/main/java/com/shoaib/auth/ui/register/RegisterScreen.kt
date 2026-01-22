package com.shoaib.auth.ui.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shoaib.design.components.GlassScaffold
import com.shoaib.design.theme.SuperAppDesign

@Composable
fun RegisterScreen(
    onRegisterComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var aadhaar by remember { mutableStateOf("") }
    var pan by remember { mutableStateOf("") }

    val isFormValid = listOf(
        firstName, lastName, age, dob, mobile, email, aadhaar, pan
    ).all { it.isNotBlank() }

    GlassScaffold(modifier = modifier) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Create Account",
                fontSize = 34.sp,
                fontWeight = FontWeight.ExtraBold,
                color = SuperAppDesign.TextPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Please fill in all details to continue",
                fontSize = 16.sp,
                color = SuperAppDesign.TextSecondary
            )

            Spacer(modifier = Modifier.height(24.dp))

            GlassTextField(label = "First Name", value = firstName, onValueChange = { firstName = it })
            GlassTextField(label = "Last Name", value = lastName, onValueChange = { lastName = it })
            GlassTextField(label = "Age", value = age, onValueChange = { age = it }, keyboardType = KeyboardType.Number)
            GlassTextField(label = "Date of Birth", value = dob, onValueChange = { dob = it }, placeholder = "DD/MM/YYYY")
            GlassTextField(label = "Mobile Number", value = mobile, onValueChange = { mobile = it }, keyboardType = KeyboardType.Phone)
            GlassTextField(label = "Email", value = email, onValueChange = { email = it }, keyboardType = KeyboardType.Email)
            GlassTextField(label = "Aadhaar Number", value = aadhaar, onValueChange = { aadhaar = it }, keyboardType = KeyboardType.Number)
            GlassTextField(label = "PAN Number", value = pan, onValueChange = { pan = it })

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onRegisterComplete,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                enabled = isFormValid,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White.copy(alpha = 0.2f),
                    contentColor = Color.White,
                    disabledContainerColor = Color.White.copy(alpha = 0.1f),
                    disabledContentColor = Color.White.copy(alpha = 0.3f)
                )
            ) {
                Text("Register", fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
private fun GlassTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    placeholder: String? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = SuperAppDesign.TextSecondary) },
        placeholder = placeholder?.let { { Text(it, color = SuperAppDesign.TextHint) } },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedBorderColor = Color.White.copy(alpha = 0.5f),
            unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
            cursorColor = Color.White
        )
    )
    Spacer(modifier = Modifier.height(12.dp))
}
