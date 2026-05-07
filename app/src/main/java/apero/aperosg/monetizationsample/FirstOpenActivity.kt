package apero.aperosg.monetizationsample

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.astronex.firstopen.app.ButtonStyle
import com.astronex.firstopen.app.ButtonUIConfig
import com.astronex.firstopen.app.FOAdsConfig
import com.astronex.firstopen.app.FOCallback
import com.astronex.firstopen.app.FOConfig
import com.astronex.firstopen.app.FOManager
import com.astronex.firstopen.app.LanguageUiConfig
import com.astronex.firstopen.app.NativeAdUiConfig
import com.astronex.firstopen.app.OnboardPageConfig
import com.astronex.firstopen.app.OnboardUiConfig
import com.astronex.firstopen.app.SplashUiConfig
import com.astronex.firstopen.app.SwipeUIConfig
import com.astronex.firstopen.app.WelcomeUiConfig
import com.astronex.firstopen.model.Language
import com.astronex.firstopen.ui.activity.AstronexFirstOpenActivity
import com.astronex.firstopen.ui.component.foundation.CenterBox

class FirstOpenActivity : AstronexFirstOpenActivity() {



    // Set up ads config
    val adsConfig = FOAdsConfig.Builder()
        // Set inter splash ads
        .setInterSplashHighId(BuildConfig.inter_splash_high)
//            .setInterSplashHigh2Id(BuildConfig.inter_splash_high_2)
        .setInterSplashId(BuildConfig.inter_splash)
        // Set banner splash
        .setBannerSplashId(BuildConfig.banner_splash)
        // Set native language
        .setNativeLanguageHighId(BuildConfig.native_language_high)
        .setNativeLanguageId(BuildConfig.native_language)
        // Set native language dup
        .setNativeLanguageDupHighId(BuildConfig.native_language_dup_high)
        .setNativeLanguageDupId(BuildConfig.native_language_dup)
        // Set native welcome
        .setNativeWelcomeHighId(BuildConfig.native_welcome_high)
        .setNativeWelcomeId(BuildConfig.native_welcome)
        // Set native welcome dup
        .setNativeWelcomeDupHighId(BuildConfig.native_welcome_dup_high)
        .setNativeWelcomeDupId(BuildConfig.native_welcome_dup)
        // Set native onboard
        .setNativeOnboard1HighId(BuildConfig.native_onboard_1_high)
        .setNativeOnboard1Id(BuildConfig.native_onboard_1)
        .setNativeOnboardFullscreenHighId(BuildConfig.native_ob_fullscr_high)
        .setNativeOnboardFullscreenId(BuildConfig.native_ob_fullscr)
        .build()

    val adCallback = object : FOCallback() {
        override fun onConsentResult(canLoadAds: Boolean) {
            // Do something if user consent/doesn't consent
            // Disable ads here if user doesn't consent
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
    }

    override fun onSetupFOConfig(): FOConfig {
        val adUiConfig = NativeAdUiConfig(
            headlineColor = Color.Blue,
            bodyColor = Color.Black,
            buttonTextColor = Color.White,
            buttonColor = Color.Yellow,
            tagColor = Color.Red,
            adFullScrColor = Color.Gray,
            backgroundColor = Color.Gray,

        )

        // Set up Splash screen config
        val splashConfig = SplashUiConfig(
            composableContent = {
                SplashScreen()
            }
        )

        // Set up Language FO screen config
        val languageConfig = LanguageUiConfig(
            languages = listOf(
                Language.English,
                Language.German,
                Language.Spanish,
                Language.French,
                Language.Hindi,
                Language.Japanese,
                Language.Korean,
                Language.Portuguese,
            ),
            titleColor = Color.Red,
            itemPrimaryColor = Color.Red,
            nextButtonConfig = ButtonUIConfig(
                tickColor = Color.Cyan,
                buttonStyle = ButtonStyle.Tick,
                buttonTextColor = Color.Red,
                buttonBgColor = Color.Blue,
            ),
            autoSelectLanguage = false,
            backgroundBrush = Brush.verticalGradient(
                listOf(
                    Color(0xFF673AB7).copy(0.5f),
                    Color(0xFFFF00FD).copy(0.5f),
                )
            ),
            customLanguageItemCompose = { language, selected, onClick ->
                val language = Language.getByCode(language.code)
                LanguageItem(
                    modifier = Modifier.fillMaxWidth(),
                    language = Language.entries.find { it.code == language.code }
                        ?: Language.English,
                    selected = selected,
                    onClick = onClick,
                )
            }
        )

        val buttonUIConfig = ButtonUIConfig(
            buttonTextColor = Color.Cyan,
            buttonStyle = ButtonStyle.Normal,
            swipeUIConfig = SwipeUIConfig(
                background = Color.Transparent,
                color = Color.Red,
            )
        )
        // Set up Onboard screens config
        // Config for onboard screen 1
        val onboard1Config = OnboardPageConfig(
            composableContent = { OnboardScreen1Compose() },
            buttonUIConfig = buttonUIConfig,
        )
        // Config for onboard screen 2
        val onboard2Config = OnboardPageConfig(
            composableContent = { OnboardScreen2Compose() },
            buttonUIConfig = buttonUIConfig,
        )
        // Config for onboard screen 3
        val onboard3Config = OnboardPageConfig(
            composableContent = { OnboardScreen3Compose() },
            buttonUIConfig = buttonUIConfig,
        )
        // Combine config for onboard screens
        val onboardConfig = OnboardUiConfig(
            pages = listOf(
                onboard1Config,
                onboard2Config,
                onboard3Config,
            ),
            indicationColor = Color.Magenta,
            indicationUnselectColor = Color.Cyan,
            backgroundImage = R.drawable.img_language_background,
        )

        val welcomeUiConfig = WelcomeUiConfig(
            composableContent = {
                CenterBox(Modifier
                    .fillMaxSize()
                    .background(Color.Red)
                    .clickable {
                        FOManager.completeWelcomeScreen()
                    }) {

                }
            },
            backgroundColor = Color(0xFF50105B),
        )

        // Assemble configs
        val config = FOConfig.Builder()
            .setPrimaryColor(Color.Blue)
            .setAdsConfig(adsConfig)
            .setCallback(adCallback)
            .setAdUiConfig(adUiConfig)
            .setSplashUiConfig(splashConfig)
            .setLanguageUiConfig(languageConfig)
            .setOnboardUiConfig(onboardConfig)
            .disableWelcomeScreen() //Disable/enable welcome screen
            .setWelcomeUiConfig(welcomeUiConfig)

        return config.build()
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
