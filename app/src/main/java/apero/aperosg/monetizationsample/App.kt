package apero.aperosg.monetizationsample

import android.app.Application
import com.astronex.monetization.AstronexMonetization
import com.astronex.monetization.analytics.adjust.AstronexAdjustConfig
import com.astronex.monetization.analytics.appsflyer.AstronexAppsFlyerConfig

class App: Application() {
    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        initAds()
    }

    private fun initAds() {
        AstronexMonetization.init(
            context = this,

            testDeviceIds = listOf(
                "List of test device ids"
            ),
            adjustConfig = AstronexAdjustConfig(
                adjustToken = "Adkjust key here",
                adImpressionEvent = "Ad Impression Event Key here",
                isSandbox = BuildConfig.DEBUG,

                ),
            appsflyerConfig = AstronexAppsFlyerConfig(
                apiKey = "AppFlyer key here",
                isSandbox = BuildConfig.DEBUG
            ),
            onComplete = {}
        )

        // init open resume here
    }

}