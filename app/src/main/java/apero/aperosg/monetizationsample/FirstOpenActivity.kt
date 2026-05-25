package apero.aperosg.monetizationsample

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.astronex.firstopen.app.AppInfo
import com.astronex.firstopen.app.ButtonStyle
import com.astronex.firstopen.app.ButtonUIConfig
import com.astronex.firstopen.app.FOAdsConfig
import com.astronex.firstopen.app.FOCallback
import com.astronex.firstopen.app.FOConfig
import com.astronex.firstopen.app.FOD0AdsConfig
import com.astronex.firstopen.app.FOManager
import com.astronex.firstopen.app.LanguageUiConfig
import com.astronex.firstopen.app.NativeAdUiConfig
import com.astronex.firstopen.app.OnboardPageConfig
import com.astronex.firstopen.app.OnboardUiConfig
import com.astronex.firstopen.app.PrepareUiConfig
import com.astronex.firstopen.app.SplashUiConfig
import com.astronex.firstopen.app.SwipeUIConfig
import com.astronex.firstopen.app.WelcomeUiConfig
import com.astronex.firstopen.data.repository.RemoteConfigRepository
import com.astronex.firstopen.model.Language
import com.astronex.firstopen.model.Language.Companion.sortPriority
import com.astronex.firstopen.ui.activity.AstronexFirstOpenActivity
import com.astronex.firstopen.ui.component.foundation.CenterBox
import com.astronex.firstopen.ui.component.foundation.CenterColumn
import kotlinx.coroutines.delay

class FirstOpenActivity : AstronexFirstOpenActivity() {

    override fun onProvideAppInfo(): AppInfo = AppInfo(
        packageName = BuildConfig.APPLICATION_ID,
        versionCode = BuildConfig.VERSION_CODE,
        versionName = BuildConfig.VERSION_NAME,
    )

    override fun setUpAdConfig():
            FOD0AdsConfig = FOD0AdsConfig(
        interSplashHighId = BuildConfig.inter_splash_high,
        interSplashId = BuildConfig.inter_splash,
        bannerSplashId = BuildConfig.banner_splash,
        bannerOnboard2HighId = BuildConfig.banner_ob_high,
        bannerOnboard2Id = BuildConfig.banner_ob,
        bannerOnboard3HighId = BuildConfig.banner_ob_high,
        bannerOnboard3Id = BuildConfig.banner_ob,
        nativeLanguageHighId = BuildConfig.native_language_high,
        nativeLanguageId = BuildConfig.native_language,
        nativeLanguageDupHighId = BuildConfig.native_language_dup_high,
        nativeLanguageDupId = BuildConfig.native_language_dup,
        nativeOnboard1HighId = BuildConfig.native_onboard_1_high,
        nativeOnboard1Id = BuildConfig.native_onboard_1,
        nativeOnboardFullscreenHighId = BuildConfig.native_ob_fullscr_high,
        nativeOnboardFullscreenId = BuildConfig.native_ob_fullscr,
        nativeOnboardFullscreen2HighId = BuildConfig.native_ob_fullscr_2_high,
        nativeOnboardFullscreen2Id = BuildConfig.native_ob_fullscr_2,
        nativeWelcomeId = null,
        nativeWelcomeHighId = null,
        nativeWelcomeDupId = null,
        nativeWelcomeDupHighId = null,
        nativeFullscrFlexHighId = BuildConfig.native_ob_fullscr_2,
        nativeFullscrFlexId = BuildConfig.native_ob_fullscr_2,
    )
//    FOD7AdsConfig = FOD7AdsConfig(
//    interSplashHighId = BuildConfig.inter_splash_high,
//    interSplashId = BuildConfig.inter_splash,
//    bannerSplashId = BuildConfig.banner_splash,
//    bannerOnboard2HighId = BuildConfig.banner_splash,
//    bannerOnboard2Id = BuildConfig.banner_onboard2,
//    bannerOnboard3HighId = BuildConfig.banner_splash,
//    bannerOnboard3Id = BuildConfig.banner_onboard3,
//    nativeLanguageHighId = BuildConfig.native_language_high,
//    nativeLanguageId = BuildConfig.native_language,
//    nativeLanguageDupHighId = BuildConfig.native_language_dup_high,
//    nativeLanguageDupId = BuildConfig.native_language_dup,
//    nativeOnboard1HighId = BuildConfig.native_onboard_1_high,
//    nativeOnboard1Id = BuildConfig.native_onboard_1,
//    nativeOnboardFullscreenHighId = BuildConfig.native_ob_fullscr_high,
//    nativeOnboardFullscreenId = BuildConfig.native_ob_fullscr,
//    nativeOnboardFullscreen2HighId = BuildConfig.native_ob_fullscr_2_high,
//    nativeOnboardFullscreen2Id = BuildConfig.native_ob_fullscr_2,
//    )

    override fun onSetupFOConfig(): FOConfig {

        val callback = object : FOCallback() {
            override fun onConsentResult(canLoadAds: Boolean) {
                // Do something if user consent/doesn't consent
            }

            override fun onLanguageConfirm(language: Language) {
                // Do something when user confirm language
            }

            override fun onOnboardPageChanged(pageIndex: Int, pageSize: Int) {
                // Do something when onboard page change
            }

            override fun onFinished(isFirstOpen: Boolean) {
                // Go to next screen
                startActivity((Intent(this@FirstOpenActivity, MainActivity::class.java)))
                finish()
            }

            override suspend fun onRemoteConfigResult(remoteConfigRepository: RemoteConfigRepository) {
                val flag = remoteConfigRepository.getBooleanConfig("my_flag", false)
                val text = remoteConfigRepository.getStringConfig("my_text", "")
                val count = remoteConfigRepository.getIntConfig("my_count", 0)
                val price = remoteConfigRepository.getLongConfig("my_price", 0L)
                val ratio = remoteConfigRepository.getFloatConfig("my_ratio", 0f)

                //Init open resume here
            }
        }

        // Set up Splash screen config
        val splashConfig = SplashUiConfig.Builder()
            //.setAppIconId(R.drawable.app_icon) // Uncomment this if use common splash screen
            .setCustomSplashLayoutId(R.layout.layout_splash) // Comment this if use common splash screen
            .build()

        // Set up Language FO screen config
        val languageConfig = LanguageUiConfig(
            languages = (
                    listOf(
                        Language.English,
                        Language.German,
                        Language.Spanish,
                        Language.French,
                        Language.Hindi,
                        Language.Japanese,
                        Language.Vietnamese,
                        Language.Korean,
                        Language.Portuguese,
                    ).sortPriority()
                    ),
            titleColor = Color.Red,
            itemPrimaryColor = Color.Red,
            nextButtonConfig = ButtonUIConfig(
                buttonStyle = ButtonStyle.Tick,
                buttonTextColor = Color.Red,
                buttonBgColor = Color.Blue,
            ),
            backgroundBrush = Brush.verticalGradient(
                listOf(
                    Color(0xFF673AB7).copy(0.5f),
                    Color(0xFFFF00FD).copy(0.5f),
                )
            )

        )

        // Set up Onboard screens config
        // Config for onboard screen 1
        val onboard1Config = OnboardPageConfig(
            layoutOnboardContentId = R.layout.layout_onboard_1,
            buttonUIConfig = ButtonUIConfig(
                buttonTextColor = Color.White,
                buttonStyle = ButtonStyle.Normal,
                buttonBgColor = Color.Blue,
                buttonBgBrush = Brush.verticalGradient(
                    listOf(
                        Color(0xFF673AB7).copy(0.5f),
                        Color(0xFFFF00FD).copy(0.5f),
                    )
                ),
            )
        )
        // Config for onboard screen 2
        val onboard2Config = OnboardPageConfig(
            layoutOnboardContentId = R.layout.layout_onboard_2,
            buttonUIConfig = ButtonUIConfig(
                buttonTextColor = Color.White,
                buttonStyle = ButtonStyle.Normal,
                buttonBgColor = Color.Blue,
                buttonBgBrush = Brush.verticalGradient(
                    listOf(
                        Color(0xFF673AB7).copy(0.5f),
                        Color(0xFFFF00FD).copy(0.5f),
                    )
                ),
            )
        )
        // Config for onboard screen 3
        val onboard3Config = OnboardPageConfig(
            layoutOnboardContentId = R.layout.layout_onboard_3,
            buttonUIConfig = ButtonUIConfig(
                buttonTextColor = Color.Yellow,
                buttonStyle = ButtonStyle.Outline,
                buttonBgColor = Color.Magenta,
            ),
        )
        // Combine config for onboard screens
        val onboardConfig = OnboardUiConfig(
            pages = listOf(onboard1Config, onboard2Config, onboard3Config),
            indicationColor = Color.Green,
            indicationUnselectColor = Color.Yellow,
            backgroundImage = R.drawable.img_onboard_1_test,
        )

        val welcomeUiConfig = WelcomeUiConfig(
            composableContent = {
                CenterBox(
                    Modifier
                        .fillMaxSize()
                        .background(Color.Red)
                        .clickable {
                            FOManager.completeWelcomeScreen()
                        }) {

                }
            },
            backgroundColor = Color(0xFF50105B),
            enableWelcomeScreen = false
        )

        val prepareUiConfig = PrepareUiConfig(
            composableContent = {
                CenterColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                        .background(Color.Green),
                    content = {
                        var dots by remember { mutableIntStateOf(0) }

                        LaunchedEffect(Unit) {
                            while (true) {
                                delay(200)
                                if (dots == 3) {
                                    dots = 0
                                } else dots++
                            }
                        }

                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            content = {
                                Text(
                                    text = "Preparing your data",
                                    color = Color.Black,
                                )

                                Text(
                                    modifier = Modifier.width(50.dp),
                                    text = " .".repeat(dots),
                                    textAlign = TextAlign.Start,
                                    color = Color.Black,
                                )
                            }
                        )
                    }
                )
            }
        )

        val adConfig = NativeAdUiConfig(
            buttonColor = Color.Red,
            backgroundColor = Color.White,
        )

        // Assemble configs
        val config = FOConfig.Builder()
            .setPrimaryColor(Color.Magenta)
            .setCallback(callback)
            .setAdUiConfig(adConfig)
            .setSplashUiConfig(splashConfig)
            .setLanguageUiConfig(languageConfig)
            .setOnboardUiConfig(onboardConfig)
            .setWelcomeUiConfig(welcomeUiConfig)
            .setPrepareUiConfig(prepareUiConfig)
            .build()

        return config
    }
}

@Composable
private fun SplashScreen(modifier: Modifier = Modifier) {
    CenterBox(Modifier.fillMaxSize()) {
        Text(
            text = "SPLASH SCREEN",
        )
    }
}
