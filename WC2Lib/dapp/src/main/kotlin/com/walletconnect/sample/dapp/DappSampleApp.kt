package com.walletconnect.sample.dapp

import android.app.Application
import com.walletconnect.android.Core
import com.walletconnect.android.CoreClient
import com.walletconnect.android.relay.ConnectionType
import com.walletconnect.sample.common.WALLET_CONNECT_PROD_RELAY_URL
import com.walletconnect.sample.common.tag
import com.walletconnect.wcmodal.client.Modal
import com.walletconnect.wcmodal.client.WalletConnectModal
import timber.log.Timber

open class DappSampleApp : Application() {

    override fun onCreate() {
        super.onCreate()

        val serverUri =
            "wss://$WALLET_CONNECT_PROD_RELAY_URL?projectId=5c5eea0af0876592f35813c046742af3"
        val appMetaData = Core.Model.AppMetaData(
            name = "Kotlin Dapp",
            description = "Kotlin Dapp Implementation",
            url = "wheelco.in",
            icons = listOf("https://wheelco.in/wp-content/uploads/2022/12/Grupo-536.png?alt=media"),
            redirect = "kotlin-dapp-wc://request"
        )

        CoreClient.initialize(
            relayServerUrl = serverUri,
            connectionType = ConnectionType.AUTOMATIC,
            application = this,
            metaData = appMetaData,
        ) {
        }

        WalletConnectModal.initialize(
            Modal.Params.Init(core = CoreClient)
        ) { error ->
            Timber.e(tag(this), error.throwable.stackTraceToString())
        }
        println("DappSampleApp => mainClassExecute")

    }
}
