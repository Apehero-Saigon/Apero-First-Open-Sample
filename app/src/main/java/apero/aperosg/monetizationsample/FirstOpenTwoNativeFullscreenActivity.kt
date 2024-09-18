package apero.aperosg.monetizationsample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import apero.aperosg.firstopen.app.AperoFO
import apero.aperosg.firstopen.app.AperoFOAdsConfig
import apero.aperosg.firstopen.app.AperoFOCallback
import apero.aperosg.firstopen.app.AperoFOConfig
import apero.aperosg.firstopen.app.AperoLanguageUiConfig
import apero.aperosg.firstopen.app.AperoOnboardPageConfig
import apero.aperosg.firstopen.app.AperoOnboardUiConfig
import apero.aperosg.firstopen.app.AperoSplashUiConfig
import apero.aperosg.firstopen.model.Language

/**
 * # Note: open Firebase Remote Config, add key enable_two_native_onboard_fullscreen = true to enable 2 native onboard fullscreen
 */
class FirstOpenTwoNativeFullscreenActivity : AppCompatActivity() {


    private val callback = object : AperoFOCallback() {
        override fun onConsentResult(canLoadAds: Boolean) {
            // Do something if user consent/doesn't consent
        }

        override fun onLanguageConfirm(language: Language) {
            // Do something when user confirm language
        }

        override fun onFinished() {
            // Go to next screen
            startActivity(
                (Intent(
                    this@FirstOpenTwoNativeFullscreenActivity,
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
        // Set up two native onboard fullscreen
        .setNativeOnboardFullscreenId(BuildConfig.native_ob_fullscr)
        .setNativeOnboardFullscreenMediumId(BuildConfig.native_ob_fullscr_medium)
        .setNativeOnboardFullscreenHighId(BuildConfig.native_ob_fullscr_high)
        .setNativeOnboardFullscreen2Id(BuildConfig.native_ob_fullscr_2)
        .setNativeOnboardFullscreen2MediumId(BuildConfig.native_ob_fullscr_2_medium)
        .setNativeOnboardFullscreen2HighId(BuildConfig.native_ob_fullscr_2_high)
        .build()

    // Set up Splash screen config
    private val screenSplash = AperoSplashUiConfig.Builder()
        //.setAppIconId(R.drawable.app_icon) // Uncomment this if use common splash screen
        .setCustomSplashLayoutId(R.layout.layout_splash) // Comment this if use common splash screen
        .build()

    // Set up Language FO screen config
    private val screenLanguage = AperoLanguageUiConfig.Builder()
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
        .build()

    // Set up Onboard screens config
    private val screenOnboard1 =
        AperoOnboardPageConfig(composableContent = { OnboardScreen1Compose() })
    private val screenOnboard2 =
        AperoOnboardPageConfig(composableContent = { OnboardScreen2Compose() })
    private val screenOnboard3 =
        AperoOnboardPageConfig(composableContent = { OnboardScreen3Compose() })
    private val screenOnboards =
        AperoOnboardUiConfig(pages = listOf(screenOnboard1, screenOnboard2, screenOnboard3))


    // Assemble configs
    private val config = AperoFOConfig.Builder()
        .setCallback(callback)
        .setAdsConfig(adsConfig)
        .setSplashUiConfig(screenSplash)
        .setLanguageUiConfig(screenLanguage)
        .setOnboardUiConfig(screenOnboards)
        .build()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AperoFO.startFlow(this, config)
    }
}