package com.shoaib.cards.ui.requestAddOnCard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.shoaib.cards.ui.components.PrimaryActionButton
import com.shoaib.cards.ui.components.ScreenTopBar
import com.shoaib.cards.ui.requestAddOnCard.components.AddOnFormField
import com.shoaib.cards.ui.requestAddOnCard.components.DateOfBirthField
import com.shoaib.cards.ui.requestAddOnCard.components.RelationshipDropdownField
import com.shoaib.cards.viewmodel.RequestAddOnCardViewModel
import com.shoaib.cards.viewmodel.SnackbarEvent
import com.shoaib.design.components.GlassScaffold
import com.shoaib.design.components.SuperLoading
import com.shoaib.design.components.ThemedSnackBarHost
import kotlinx.coroutines.flow.flowOf

@Composable
fun RequestAddOnCardScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    val viewModel: RequestAddOnCardViewModel = hiltViewModel()
    GlassScaffold(modifier = modifier) { innerPadding ->
        RequestAddOnCardContent(
            viewModel = viewModel,
            onBackClick = onBackClick,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}

@Composable
private fun RequestAddOnCardContent(
    viewModel: RequestAddOnCardViewModel?,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var isErrorSnackbar by remember { mutableStateOf(false) }
    var customerID by remember { mutableStateOf("") }
    var nameOnCard by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }
    var relationship by remember { mutableStateOf("") }
    val relationshipOptions = listOf(
        "Spouse",
        "Parent",
        "Sibling",
        "Child",
        "Friend",
        "Other"
    )
    val isLoading by (viewModel?.isLoading ?: flowOf(false)).collectAsState(initial = false)

    LaunchedEffect(viewModel) {
        viewModel ?: return@LaunchedEffect
        viewModel.snackbarEvent.collect { event ->
            when (event) {
                is SnackbarEvent.Success -> {
                    isErrorSnackbar = false
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                }
                is SnackbarEvent.Error -> {
                    isErrorSnackbar = true
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            ScreenTopBar(onBackClick = onBackClick, title = "Request Add-On Card")

            Spacer(Modifier.height(24.dp))

            AddOnFormField(
                value = customerID,
                onValueChange = { customerID = it },
                label = "Customer ID"
            )
            AddOnFormField(
                value = nameOnCard,
                onValueChange = { nameOnCard = it },
                label = "Name on card"
            )
            DateOfBirthField(
                value = dateOfBirth,
                onDateChange = { dateOfBirth = it },
                label = "Date of birth"
            )
            RelationshipDropdownField(
                value = relationship,
                onValueChange = { relationship = it },
                options = relationshipOptions,
                label = "Relationship"
            )

            Spacer(Modifier.height(24.dp))

            PrimaryActionButton(
                text = "Submit",
                onClick = {
                    viewModel?.submitRequest(
                        customerID = customerID.trim(),
                        nameOnCard = nameOnCard.trim(),
                        dateOfBirth = dateOfBirth.trim(),
                        relationship = relationship.trim()
                    )
                },
                enabled = customerID.isNotBlank() &&
                    nameOnCard.isNotBlank() &&
                    dateOfBirth.isNotBlank() &&
                    relationship.isNotBlank()
            )

            Spacer(Modifier.height(32.dp))
        }

        ThemedSnackBarHost(
            hostState = snackbarHostState,
            isError = isErrorSnackbar,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )

        if (isLoading) {
            SuperLoading(
                modifier = Modifier.fillMaxSize(),
                message = "Submitting request..."
            )
        }
    }
}
