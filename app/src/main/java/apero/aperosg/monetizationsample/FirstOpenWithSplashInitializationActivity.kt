package apero.aperosg.monetizationsample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import apero.aperosg.firstopen.app.AperoFO
import apero.aperosg.firstopen.app.AperoFOAdsConfig
import apero.aperosg.firstopen.app.AperoFOCallback
import apero.aperosg.firstopen.app.AperoFOConfig
import apero.aperosg.firstopen.app.AperoLanguageUiConfig
import apero.aperosg.firstopen.app.AperoOnboardPageConfig
import apero.aperosg.firstopen.app.AperoOnboardUiConfig
import apero.aperosg.firstopen.app.AperoSplashUiConfig
import apero.aperosg.firstopen.model.Language
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FirstOpenWithSplashInitializationActivity : AppCompatActivity() {

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

            override fun onFinished() {
                // Go to next screen
                startActivity((Intent(this@FirstOpenWithSplashInitializationActivity, MainActivity::class.java)))
                finish()
            }
        }

        // Set up ads config
        val adsConfig = AperoFOAdsConfig.Builder()
            .setInterSplashHighId(BuildConfig.inter_splash_high)
            .setInterSplashId(BuildConfig.inter_splash)
            .setBannerSplashId(BuildConfig.banner_splash)
            .build()

        // Set up Splash screen config
        val splashConfig = AperoSplashUiConfig.Builder()
            .setWaitForInitialization(true)
            .setAppIconId(R.drawable.app_icon)
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
            .build()

        // Set up Onboard screens config
        val onboard1Config = AperoOnboardPageConfig(layoutOnboardContentId = R.layout.layout_onboard_1)
        val onboard2Config = AperoOnboardPageConfig(layoutOnboardContentId = R.layout.layout_onboard_2)
        val onboard3Config = AperoOnboardPageConfig(layoutOnboardContentId = R.layout.layout_onboard_3)
        val onboardConfig = AperoOnboardUiConfig(pages = listOf(onboard1Config, onboard2Config, onboard3Config))

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

        // Call to start other splash initializations
        initSplash()
    }

    private fun initSplash() {
        lifecycleScope.launch {
            // Do other initializations here
            delay(5000) // TODO: delete this line

            // After finish initialization, call this function to continue splash
            AperoFO.finishSplashInitialization()
        }
    }
}