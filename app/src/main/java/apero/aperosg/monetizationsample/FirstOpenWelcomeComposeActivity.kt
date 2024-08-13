package apero.aperosg.monetizationsample

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import apero.aperosg.firstopen.ui.component.modifier.rounded

class FirstOpenWelcomeComposeActivity : AppCompatActivity() {
    private var checkboxChecked by mutableStateOf(false)

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
                startActivity((Intent(this@FirstOpenWelcomeComposeActivity, MainActivity::class.java)))
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
            .setComposableContent { WelcomeScreenContent() }
            .build()

        // Set up Onboard screens config
        val onboard1Config = AperoOnboardPageConfig(layoutOnboardContentId = R.layout.layout_onboard_1)
        val onboard2Config = AperoOnboardPageConfig(layoutOnboardContentId = R.layout.layout_onboard_2)
        val onboard3Config = AperoOnboardPageConfig(layoutOnboardContentId = R.layout.layout_onboard_3)
        val onboard4Config = AperoOnboardPageConfig(layoutOnboardContentId = R.layout.layout_onboard_4)
        val onboardConfig = AperoOnboardUiConfig(pages = listOf(onboard1Config, onboard2Config, onboard3Config, onboard4Config),)

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

    @Composable
    private fun WelcomeScreenContent() {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.welcome_title),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
            ) {
                Checkbox(
                    checked = checkboxChecked,
                    onCheckedChange = {
                        checkboxChecked = it
                        //------- Important ----------
                        // Show welcome dup screen when user click checkbox
                        AperoFO.showWelcomeDupScreen()
                        //----------------------------
                    }
                )

                Text(text = stringResource(R.string.welcome_checkbox_title), fontSize = 14.sp, color = Color.Black)
            }

            Text(
                text = stringResource(R.string.next),
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .rounded(10.dp)
                    .background(Color(0xFF335CFF))
                    .clickable { AperoFO.completeWelcomeScreen() }
                    .padding(12.dp)
            )
        }
    }
}