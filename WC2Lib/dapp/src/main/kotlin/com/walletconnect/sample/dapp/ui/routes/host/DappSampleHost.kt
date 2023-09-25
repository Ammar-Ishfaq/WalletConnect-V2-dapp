@file:OptIn(ExperimentalMaterialApi::class, ExperimentalMaterialNavigationApi::class)

package com.walletconnect.sample.dapp.ui.routes.host

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.walletconnect.sample.dapp.R
import com.walletconnect.sample.dapp.ui.DappSampleEvents
import com.walletconnect.sample.dapp.ui.DappSampleNavGraph
import com.walletconnect.sample.dapp.ui.routes.Route


@Composable
fun DappSampleHost() {
//    val modalSheetState = rememberModalBottomSheetState(
//        initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true
//    )
//    val bottomSheetNavigator = BottomSheetNavigator(modalSheetState)
//    val navController = rememberNavController(bottomSheetNavigator)
//
//    ModalBottomSheetLayout(
//        bottomSheetNavigator = bottomSheetNavigator,
//        sheetBackgroundColor = Color.Transparent,
//        sheetElevation = 0.dp,
//        scrimColor = Color.Unspecified,
//        sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
//    ) {
//        NavHost(
//            navController = navController, startDestination = "home"
//        ) {
//            composable("home") {
//                Text(text = "Home Screen")
//                Button(
//                    onClick = {
//                        navController.openWalletConnectModal()
//
//                    }, modifier = Modifier.padding(16.dp)
//                ) {
//                    Text(text = "Click Me")
//                }
//            }
//            walletConnectModalGraph(navController)
//            walletConnectModalGraph(navController)
//        }
//    }


    val scaffoldState: ScaffoldState = rememberScaffoldState()
    var isOfflineState by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true
    )
    val bottomSheetNavigator = BottomSheetNavigator(sheetState)
    val navController = rememberNavController(bottomSheetNavigator)
    val viewModel: DappSampleViewModel = viewModel()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                DappSampleEvents.Disconnect -> navController.navigate(Route.ChainSelection.path)
                is DappSampleEvents.RequestError -> scaffoldState.snackbarHostState.showSnackbar(
                    event.exceptionMsg
                )

                DappSampleEvents.SessionExtend -> scaffoldState.snackbarHostState.showSnackbar("Session extended")
                is DappSampleEvents.ConnectionEvent -> {
                    isOfflineState = !event.isAvailable
                }

                else -> Unit
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            if (isOfflineState) {
                ConnectionIndicator()
            }
            DappSampleNavGraph(
                bottomSheetNavigator = bottomSheetNavigator,
                navController = navController,
                startDestination = Route.ChainSelection.path
            )

        }
    }
}

@Composable
private fun ConnectionIndicator() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF3496ff))
            .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        Text(text = "No internet connection", color = Color.White)
        Spacer(modifier = Modifier.width(4.dp))
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_offline),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            colorFilter = ColorFilter.tint(color = Color.White)
        )
    }
}
