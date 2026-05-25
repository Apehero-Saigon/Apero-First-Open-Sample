package apero.aperosg.monetizationsample

import android.content.ContextWrapper
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import com.astronex.firstopen.app.AppInfo
import com.astronex.firstopen.app.FOAdsConfig
import com.astronex.firstopen.app.FOCallback
import com.astronex.firstopen.app.FOConfig
import com.astronex.firstopen.app.FOD0AdsConfig
import com.astronex.firstopen.app.FOManager
import com.astronex.firstopen.app.LanguageUiConfig
import com.astronex.firstopen.app.OnboardPageConfig
import com.astronex.firstopen.app.OnboardUiConfig
import com.astronex.firstopen.app.SplashUiConfig
import com.astronex.firstopen.app.WelcomeUiConfig
import com.astronex.firstopen.data.repository.RemoteConfigRepository
import com.astronex.firstopen.model.Language
import com.astronex.firstopen.ui.activity.AstronexFirstOpenActivity
import java.util.Locale

class NoneComposableFirstOpenActivity : AstronexFirstOpenActivity() {

    override fun onSetupFOConfig(): FOConfig {
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

            override fun onFinished(isFirstOpen: Boolean) {
                // Go to next screen
                startActivity((Intent(this@NoneComposableFirstOpenActivity, MainActivity::class.java)))
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
            .setSplashUiConfig(splashConfig)
            .setLanguageUiConfig(languageConfig)
            .setOnboardUiConfig(onboardConfig)
            .setWelcomeUiConfig(welcomeConfig)
            .build()

        return config
    }

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

    override fun onProvideAppInfo(): AppInfo = AppInfo(
        packageName = BuildConfig.APPLICATION_ID,
        versionCode = BuildConfig.VERSION_CODE,
        versionName = BuildConfig.VERSION_NAME,
    )

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
