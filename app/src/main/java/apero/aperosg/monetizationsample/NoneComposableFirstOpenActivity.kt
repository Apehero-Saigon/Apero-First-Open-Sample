package apero.aperosg.monetizationsample

import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.astronex.firstopen.app.ButtonStyle
import com.astronex.firstopen.app.ButtonUIConfig
import com.astronex.firstopen.app.FOAdsConfig
import com.astronex.firstopen.model.Language
import com.astronex.firstopen.app.FOCallback
import com.astronex.firstopen.app.FOConfig
import com.astronex.firstopen.app.FOManager
import com.astronex.firstopen.app.LanguageUiConfig
import com.astronex.firstopen.app.OnboardPageConfig
import com.astronex.firstopen.app.OnboardUiConfig
import com.astronex.firstopen.app.SplashUiConfig
import com.astronex.firstopen.app.WelcomeUiConfig
import java.util.Locale

class NoneComposableFirstOpenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupFirstOpenFlow()
    }

    private fun setupFirstOpenFlow() {
        val callback = object : FOCallback() {
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

            override fun onFinished() {
                // Go to next screen
                startActivity((Intent(this@NoneComposableFirstOpenActivity, MainActivity::class.java)))
                finish()
            }
        }

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
            .setNativeOnboardFullscreen2HighId(BuildConfig.native_ob_fullscr_2_high)
            .setNativeOnboardFullscreen2Id(BuildConfig.native_ob_fullscr_2)
            .setBannerOnboard2Id(BuildConfig.banner_ob)
            .setBannerOnboard3Id(BuildConfig.banner_ob)
            .setInterStartId(BuildConfig.inter_start)
            .build()

        // Set up Splash screen config
        val splashConfig = SplashUiConfig.Builder()
            //.setAppIconId(R.drawable.app_icon) // Uncomment this if use common splash screen
            .setCustomSplashLayoutId(R.layout.layout_splash) // Comment this if use common splash screen
            .build()

        // Set up Language FO screen config
        val languageConfig = LanguageUiConfig.Builder()
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
        // Config for onboard screen 1
        val onboard1Config = OnboardPageConfig(layoutOnboardContentId = R.layout.layout_onboard_1)
        // Config for onboard screen 2
        val onboard2Config = OnboardPageConfig(layoutOnboardContentId = R.layout.layout_onboard_2)
        // Config for onboard screen 3
        val onboard3Config = OnboardPageConfig(layoutOnboardContentId = R.layout.layout_onboard_3)
        // Combine config for onboard screens
        val onboardConfig = OnboardUiConfig(pages = listOf(onboard1Config, onboard2Config, onboard3Config))

        // Set up onboard welcome
        val welcomeConfig = WelcomeUiConfig.Builder()
            .setBackgroundImage(R.drawable.img_language_background) // for using a image as background. Now, xml layout no longer include "android:background="@drawable/img_custom_language_background"
            .setViewContentProvider { setUpWelcomeScreen() }
            .build()

        // Assemble configs
        val config = FOConfig.Builder()
            .setCallback(callback)
            .setAdsConfig(adsConfig)
            .setSplashUiConfig(splashConfig)
            .setLanguageUiConfig(languageConfig)
            .setOnboardUiConfig(onboardConfig)
            .setWelcomeUiConfig(welcomeConfig)
            .build()

        // Start first open flow
        FOManager.startFlow(this, config)
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
            FOManager.showWelcomeDupScreen()
            //----------------------------
        }
        nextButton.setOnClickListener {
            if (checkbox.isChecked) {
                // Finish welcome screen and move to next screen
                FOManager.completeWelcomeScreen()
            } else {
                Toast.makeText(this, R.string.please_check_the_checkbox, Toast.LENGTH_SHORT).show()
            }
        }
        return welcomeScreenView
    }
}