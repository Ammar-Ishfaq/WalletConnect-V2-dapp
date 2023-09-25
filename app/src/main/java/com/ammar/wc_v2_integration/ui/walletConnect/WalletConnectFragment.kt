package com.ammar.wc_v2_integration.ui.walletConnect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ammar.wc_v2_integration.databinding.FragmentWalletConnectBinding
import com.walletconnect.sample.common.ui.theme.WCSampleAppTheme
import com.walletconnect.sample.dapp.ui.routes.host.DappSampleHost
import com.walletconnect.sample.dapp.utils.WC2Lib
import timber.log.Timber

class WalletConnectFragment : Fragment() {

    private var _binding: FragmentWalletConnectBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentWalletConnectBinding.inflate(inflater, container, false).apply {
            composeView.setContent {
                WCSampleAppTheme {
                    DappSampleHost()
                }
            }
        }
        val root: View = binding.root

        //events to manage from the wallet connect
        WC2Lib.getInstance().onSessionApproved.observe(viewLifecycleOwner) {
            Timber.e("Connected with $it")
            WC2Lib.getInstance().disconnectWalletConnect()
        }
        WC2Lib.getInstance().onError.observe(viewLifecycleOwner) {
            Timber.e("Error with $it")
        }



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}