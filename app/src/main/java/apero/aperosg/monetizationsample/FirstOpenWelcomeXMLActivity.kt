package apero.aperosg.monetizationsample

import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import apero.aperosg.firstopen.app.AperoFO
import apero.aperosg.firstopen.app.AperoFOAdsConfig
import apero.aperosg.firstopen.app.AperoFOCallback
import apero.aperosg.firstopen.app.AperoFOConfig
import apero.aperosg.firstopen.app.AperoLanguageUiConfig
import apero.aperosg.firstopen.app.AperoOnboardPageConfig
import apero.aperosg.firstopen.app.AperoOnboardUiConfig
import apero.aperosg.firstopen.app.AperoSplashUiConfig
import apero.aperosg.firstopen.app.AperoWelcomeUiConfig
import apero.aperosg.firstopen.model.Language
import java.util.Locale

class FirstOpenWelcomeXMLActivity: AppCompatActivity() {

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
                startActivity((Intent(this@FirstOpenWelcomeXMLActivity, MainActivity::class.java)))
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
            .build()

        // Set up welcome screen (if exist)
        val welcomeConfig = AperoWelcomeUiConfig.Builder()
            .setViewContentProvider { setUpWelcomeScreen() }
            .build()

        // Set up Onboard screens config
        val onboard1Config = AperoOnboardPageConfig(layoutOnboardContentId = R.layout.layout_onboard_1)
        val onboard2Config = AperoOnboardPageConfig(layoutOnboardContentId = R.layout.layout_onboard_2)
        val onboard3Config = AperoOnboardPageConfig(layoutOnboardContentId = R.layout.layout_onboard_3)
        val onboard4Config = AperoOnboardPageConfig(layoutOnboardContentId = R.layout.layout_onboard_4)

        val onboardConfig = AperoOnboardUiConfig(
            pages = listOf(onboard1Config, onboard2Config, onboard3Config, onboard4Config),
        )

        // Assemble configs
        val config = AperoFOConfig.Builder()
            .setCallback(callback)
            .setAdsConfig(adsConfig)

            .setSplashUiConfig(splashConfig)
            .setLanguageUiConfig(languageConfig)
            .setWelcomeUiConfig(welcomeConfig)
            .setOnboardUiConfig(onboardConfig)

            .build()

        // Start first open flow
        AperoFO.startFlow(this, config)
    }

    /** Set up custom welcome screen content
     * Provide your own logic here, this is a sample code */
    private fun setUpWelcomeScreen(): View {
        // Set up localized context for welcome screen to enable translation
        val localizedConfig = resources.configuration.apply { setLocale(Locale.getDefault()) }
        val localizedContext = ContextWrapper(this).createConfigurationContext(localizedConfig)

        val welcomeScreenView = LayoutInflater.from(localizedContext).inflate(R.layout.layout_welcome_scr, null, false)
        val checkbox = welcomeScreenView.findViewById<CheckBox>(R.id.checkbox)
        val nextButton = welcomeScreenView.findViewById<View>(R.id.button)
        checkbox.setOnClickListener {
            //------- Important ----------
            // Show welcome dup screen when user click checkbox
            AperoFO.showWelcomeDupScreen()
            //----------------------------
        }
        nextButton.setOnClickListener {
            if (checkbox.isChecked) {
                // Finish welcome screen and move to next screen
                AperoFO.completeWelcomeScreen()
            } else {
                Toast.makeText(this, R.string.please_check_the_checkbox, Toast.LENGTH_SHORT).show()
            }
        }
        return welcomeScreenView
    }
}