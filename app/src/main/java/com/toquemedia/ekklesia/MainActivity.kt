package com.toquemedia.ekklesia

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.toquemedia.ekklesia.model.BottomBarItem
import com.toquemedia.ekklesia.model.EkklesiaBottomNavigation
import com.toquemedia.ekklesia.model.ekklesiaBottomBarItems
import com.toquemedia.ekklesia.ui.composables.EkklesiaModalSheet
import com.toquemedia.ekklesia.ui.navigation.navigateToBible
import com.toquemedia.ekklesia.ui.navigation.navigateToCommunity
import com.toquemedia.ekklesia.ui.screens.EkklesiaNavHost
import com.toquemedia.ekklesia.ui.theme.EkklesiaTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    // Login bem-sucedido
                } else {
                    // Falha no login
                }
            }
    }

    private fun checkPermission() {
        val permissionCheck =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)

        if (permissionCheck != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        checkPermission()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // Adicione o ID do cliente
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = FirebaseAuth.getInstance()

        val googleSignInLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                if (task.isSuccessful) {
                    val account = task.result
                    firebaseAuthWithGoogle(account.idToken!!)
                } else {
                    // Gerenciar falhas no login
                }
            }

        setContent {
            EkklesiaTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    val navController = rememberNavController()
                    val bottomSheetState = rememberModalBottomSheetState(
                        initialValue = ModalBottomSheetValue.Hidden,
                        skipHalfExpanded = true
                    )
                    val sheetContent = remember { mutableStateOf<@Composable () -> Unit>({ }) }
                    val scope = rememberCoroutineScope()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    val currentRoute = currentDestination?.route
                    val selectedItem = remember(currentDestination) {
                        when (currentRoute) {
                            "home" -> BottomBarItem.Home
                            "bible" -> BottomBarItem.Bible
                            else -> BottomBarItem.Community
                        }
                    }

                    EkklesiaApp(
                        tabSelected = selectedItem,
                        sheetState = bottomSheetState,
                        sheetContent = sheetContent.value,
                        onTabItemChange = {
                            when(it.label) {
                                BottomBarItem.Bible.label -> navController.navigateToBible()
                                BottomBarItem.Community.label -> navController.navigateToCommunity()
                            }
                        },
                        isLoginActive = auth.currentUser != null
                    ) {
                        EkklesiaNavHost(
                            navController = navController,
                            isLoginActive = auth.currentUser != null,
                            showDevocionalModal = { content ->
                                sheetContent.value = content
                                scope.launch { bottomSheetState.show() }
                            },
                            hideDevocionalModal = {
                                scope.launch { bottomSheetState.hide() }
                            },
                            onClickLogin = {
                                val signInIntent = googleSignInClient.signInIntent
                                googleSignInLauncher.launch(signInIntent)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EkklesiaApp(
    modifier: Modifier = Modifier,
    sheetState: ModalBottomSheetState,
    sheetContent: @Composable () -> Unit,
    tabSelected: BottomBarItem = ekklesiaBottomBarItems.first(),
    onTabItemChange: (BottomBarItem) -> Unit,
    isLoginActive: Boolean,
    content: @Composable () -> Unit,
) {
    EkklesiaModalSheet(
        sheetState = sheetState,
        sheetContent = sheetContent,
    ) {
        Scaffold(
            bottomBar = {
                if (isLoginActive) {
                    EkklesiaBottomNavigation(
                        barItems = ekklesiaBottomBarItems,
                        currentScreen = tabSelected,
                        onTabSelected = onTabItemChange
                    )
                }
            }
        ) { padding ->
            Box(
                modifier = modifier
                    .padding(padding)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                content()
            }
        }
    }
}