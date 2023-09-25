package com.walletconnect.sample.dapp.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.walletconnect.sample.dapp.ui.routes.composable_routes.session.SessionViewModel

class WC2Lib private constructor() {

    data class OnApproved(val chainWithAddress: String)

    internal val _onSessionApproved = MutableLiveData<OnApproved>()
    val onSessionApproved: LiveData<OnApproved> = _onSessionApproved

    internal val _onError = MutableLiveData<String>()
    val onError: LiveData<String> = _onError


    fun disconnectWalletConnect() {
        SessionViewModel.getInstance().disconnect()
    }

    companion object {
        @Volatile
        private var INSTANCE: WC2Lib? = null

        fun getInstance(): WC2Lib {
            return INSTANCE ?: synchronized(this) {
                val instance = WC2Lib()
                INSTANCE = instance
                instance
            }
        }
    }
}
