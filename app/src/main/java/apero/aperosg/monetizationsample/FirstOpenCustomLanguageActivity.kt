package apero.aperosg.monetizationsample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.Color
import apero.aperosg.firstopen.app.AperoFO
import apero.aperosg.firstopen.app.AperoFOAdsConfig
import apero.aperosg.firstopen.app.AperoFOCallback
import apero.aperosg.firstopen.app.AperoFOConfig
import apero.aperosg.firstopen.app.AperoLanguageUiConfig
import apero.aperosg.firstopen.app.AperoOnboardPageConfig
import apero.aperosg.firstopen.app.AperoOnboardUiConfig
import apero.aperosg.firstopen.app.AperoSplashUiConfig
import apero.aperosg.firstopen.app.ButtonStyle
import apero.aperosg.firstopen.model.Language

class FirstOpenCustomLanguageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupFirstOpenFlow()
    }

    private fun setupFirstOpenFlow() {
        val callback = object : AperoFOCallback() {
            override fun onConsentResult(canLoadAds: Boolean) {
                // Do something if user consent/doesn't consent
            }

            override fun onLanguageConfirm(language: Language) {
                // Do something when user confirm language
            }

            override fun onOnboardPageChanged(pageIndex: Int, pageSize: Int) {
                // Do something when onboard page change
            }

            override fun onFinished() {
                // Go to next screen
                startActivity(
                    (Intent(
                        this@FirstOpenCustomLanguageActivity,
                        MainActivity::class.java
                    ))
                )
                finish()
            }
        }

        // Set up ads config
        val adsConfig = AperoFOAdsConfig.Builder()
            // Set inter splash ads
            .setInterSplashHighId(BuildConfig.inter_splash_high)
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
            .setNativeOnboard3HighId(BuildConfig.native_onboard_3_high)
            .setNativeOnboard3Id(BuildConfig.native_onboard_3)
            .setNativeOnboard4HighId(BuildConfig.native_onboard_4_high)
            .setNativeOnboard4Id(BuildConfig.native_onboard_4)
            .setNativeOnboardFullscreenHighId(BuildConfig.native_ob_fullscr_high)
            .build()

        // Set up Splash screen config
        val splashConfig = AperoSplashUiConfig.Builder()
            //.setAppIconId(R.drawable.app_icon) // Uncomment this if use common splash screen
            .setCustomSplashLayoutId(R.layout.layout_splash) // Comment this if use common splash screen
            .build()

        // Set up Language FO screen config
        val languageConfig = AperoLanguageUiConfig.Builder()
            .setLanguages(
                listOf(
                    Language.English,
                    Language.German,
                    Language.Spanish,
                    Language.French,
                    Language.Hindi,
                    Language.Japanese,
                    Language.Korean,
                    Language.Portuguese,
                )
            )
            .setNextButtonStyle(ButtonStyle.Solid)
            .setPrimaryColor(0xFF6F55CC) // set primary for button next
            .setTitleColor(0xFFFFFFFF.toInt()) // set title for "Select language"
            .setCustomImageBackground(R.drawable.img_language_background) // set custom image background
            .setCustomLanguageLayoutId(R.layout.layout_language_element) // set language layout
            .setCustomChosenLanguageLayoutId(R.layout.layout_language_element_chosen) // set language layout when user select language
            .build()

        // Set up Onboard screens config
        // Config for onboard screen 1
        val onboard1Config =
            AperoOnboardPageConfig(layoutOnboardContentId = R.layout.layout_onboard_1_test)
        // Config for onboard screen 2
        val onboard2Config =
            AperoOnboardPageConfig(layoutOnboardContentId = R.layout.layout_onboard_2)
        // Config for onboard screen 3
        val onboard3Config =
            AperoOnboardPageConfig(layoutOnboardContentId = R.layout.layout_onboard_3)
        // Config for onboard screen 4
        val onboard4Config =
            AperoOnboardPageConfig(layoutOnboardContentId = R.layout.layout_onboard_4)
        // Combine config for onboard screens
        val onboardConfig = AperoOnboardUiConfig(
            startButtonStyle = ButtonStyle.Outline,
            pages = listOf(onboard1Config, onboard2Config, onboard3Config, onboard4Config),
            nextButtonStyle = ButtonStyle.Normal,
            backgroundGradient = listOf(
                // at lease 2 colours. Otherwise, throw exceptions
                0xFF0F0F27,
                0xFF0F0F26,
                0xFF0F1129,
                0xFF1A244B,
            ),
            primaryColor = 0xFF27B8CD
        )

        // Assemble configs
        val config = AperoFOConfig.Builder()
            .setCallback(callback)
            .setAdsConfig(adsConfig)

            .setSplashUiConfig(splashConfig)
            .setLanguageUiConfig(languageConfig)
            .setOnboardUiConfig(onboardConfig)
            .build()

        // Start first open flow
        AperoFO.startFlow(this, config)
    }
}