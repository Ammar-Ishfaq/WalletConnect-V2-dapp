package com.walletconnect.sample.dapp.domain

import com.walletconnect.wcmodal.client.Modal
import com.walletconnect.wcmodal.client.WalletConnectModal
import com.walletconnect.sample.common.tag
import com.walletconnect.sample.dapp.utils.WC2Lib
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber

object DappDelegate : WalletConnectModal.ModalDelegate {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val _wcEventModels: MutableSharedFlow<Modal.Model?> = MutableSharedFlow()
    val wcEventModels: SharedFlow<Modal.Model?> = _wcEventModels.asSharedFlow()

    var selectedSessionTopic: String? = null
        private set

    init {
        WalletConnectModal.setDelegate(this)
    }

    override fun onSessionApproved(approvedSession: Modal.Model.ApprovedSession) {
        selectedSessionTopic = approvedSession.topic
        WC2Lib.getInstance()._onSessionApproved.postValue(
            approvedSession.accounts.firstOrNull()?.let {
                WC2Lib.OnApproved(
                    it
                )
            })
        scope.launch {
            _wcEventModels.emit(approvedSession)
        }
    }

    override fun onSessionRejected(rejectedSession: Modal.Model.RejectedSession) {
        scope.launch {
            _wcEventModels.emit(rejectedSession)
        }
    }

    override fun onSessionUpdate(updatedSession: Modal.Model.UpdatedSession) {
        scope.launch {
            _wcEventModels.emit(updatedSession)
        }
    }

    override fun onSessionEvent(sessionEvent: Modal.Model.SessionEvent) {
        scope.launch {
            _wcEventModels.emit(sessionEvent)
        }
    }

    override fun onSessionDelete(deletedSession: Modal.Model.DeletedSession) {
        deselectAccountDetails()

        scope.launch {
            _wcEventModels.emit(deletedSession)
        }
    }

    override fun onSessionExtend(session: Modal.Model.Session) {
        scope.launch {
            _wcEventModels.emit(session)
        }
    }

    override fun onSessionRequestResponse(response: Modal.Model.SessionRequestResponse) {
        scope.launch {
            _wcEventModels.emit(response)
        }
    }

    fun deselectAccountDetails() {
        selectedSessionTopic = null
    }

    override fun onConnectionStateChange(state: Modal.Model.ConnectionState) {
        Timber.d(tag(this), "onConnectionStateChange($state)")
        scope.launch {
            _wcEventModels.emit(state)
        }
    }

    override fun onError(error: Modal.Model.Error) {
        Timber.d(tag(this), error.throwable.stackTraceToString())
        WC2Lib.getInstance()._onError.postValue(error.throwable.stackTraceToString())
        scope.launch {
            _wcEventModels.emit(error)
        }
    }
}
