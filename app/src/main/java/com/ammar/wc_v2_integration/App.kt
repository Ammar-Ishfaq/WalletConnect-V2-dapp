package com.ammar.wc_v2_integration

import com.walletconnect.sample.dapp.DappSampleApp

class App : DappSampleApp() {
    override fun onCreate() {
        super.onCreate()
        println("Hello app")
    }
}