package com.shoaib.pere_super_app_bank

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoaib.api.AuthRepository
import com.shoaib.api.model.Result
import com.shoaib.navigation.AuthRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * MainViewModel - Determines the app's start destination
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _startDestination = MutableStateFlow<Any?>(null)
    val startDestination = _startDestination.asStateFlow()

    init {
        determineStartDestination()
    }

    private fun determineStartDestination() {
        viewModelScope.launch {
            val isAuthenticated = authRepository.validateToken().getOrElse { false }
            
            if (!isAuthenticated) {
                _startDestination.value = AuthRoute.Login
                return@launch
            }

            // User is authenticated, check PIN status
            _startDestination.value = AuthRoute.Pin
        }
    }
}
