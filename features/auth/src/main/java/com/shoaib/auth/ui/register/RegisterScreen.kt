package com.shoaib.auth.ui.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

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
        firstName,
        lastName,
        age,
        dob,
        mobile,
        email,
        aadhaar,
        pan
    ).all { it.isNotBlank() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        RegisterHeader()

        Spacer(modifier = Modifier.height(24.dp))

        RegisterFormFields(
            firstName = firstName,
            onFirstNameChange = { firstName = it },
            lastName = lastName,
            onLastNameChange = { lastName = it },
            age = age,
            onAgeChange = { age = it },
            dob = dob,
            onDobChange = { dob = it },
            mobile = mobile,
            onMobileChange = { mobile = it },
            email = email,
            onEmailChange = { email = it },
            aadhaar = aadhaar,
            onAadhaarChange = { aadhaar = it },
            pan = pan,
            onPanChange = { pan = it }
        )

        Spacer(modifier = Modifier.height(24.dp))

        RegisterSubmitButton(
            enabled = isFormValid,
            onClick = onRegisterComplete
        )
    }
}

@Composable
private fun RegisterHeader() {
    Text(
        text = "Create Account",
        style = MaterialTheme.typography.headlineLarge
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = "Please fill in all details to continue",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
private fun RegisterFormFields(
    firstName: String,
    onFirstNameChange: (String) -> Unit,
    lastName: String,
    onLastNameChange: (String) -> Unit,
    age: String,
    onAgeChange: (String) -> Unit,
    dob: String,
    onDobChange: (String) -> Unit,
    mobile: String,
    onMobileChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    aadhaar: String,
    onAadhaarChange: (String) -> Unit,
    pan: String,
    onPanChange: (String) -> Unit
) {
    RegisterField(
        label = "First Name",
        value = firstName,
        onValueChange = onFirstNameChange
    )

    RegisterField(
        label = "Last Name",
        value = lastName,
        onValueChange = onLastNameChange
    )

    RegisterField(
        label = "Age",
        value = age,
        onValueChange = onAgeChange,
        keyboardType = KeyboardType.Number
    )

    RegisterField(
        label = "Date of Birth",
        value = dob,
        onValueChange = onDobChange,
        placeholder = "DD/MM/YYYY",
        keyboardType = KeyboardType.Number
    )

    RegisterField(
        label = "Mobile Number",
        value = mobile,
        onValueChange = onMobileChange,
        keyboardType = KeyboardType.Phone
    )

    RegisterField(
        label = "Email",
        value = email,
        onValueChange = onEmailChange,
        keyboardType = KeyboardType.Email
    )

    RegisterField(
        label = "Aadhaar Number",
        value = aadhaar,
        onValueChange = onAadhaarChange,
        keyboardType = KeyboardType.Number
    )

    RegisterField(
        label = "PAN Number",
        value = pan,
        onValueChange = onPanChange,
        keyboardType = KeyboardType.Ascii
    )
}

@Composable
private fun RegisterField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    placeholder: String? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        placeholder = placeholder?.let { { Text(it) } },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )

    Spacer(modifier = Modifier.height(12.dp))
}

@Composable
private fun RegisterSubmitButton(
    enabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(56.dp),
        enabled = enabled
    ) {
        Text("Register")
    }
}
