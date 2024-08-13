package apero.aperosg.monetizationsample

import com.ads.control.admob.Admob
import com.ads.control.ads.AperoAd
import com.ads.control.application.AdsMultiDexApplication
import com.ads.control.config.AdjustConfig
import com.ads.control.config.AperoAdConfig

class App: AdsMultiDexApplication() {
    companion object {
        lateinit var instance: App
        private const val API_KEY =
            "HjmuhANCbqoFeb+7zXSUd2qJYRvRd/fbhLgtZyRSfW6HtnfMDOGlay8afYz7S5lS1aT2fyd5l61Ugt4LQ66qeDZzu7DaAIiH45f7/M5xY+bBjSVQWGIF5UKhpCy6m3j2qYnzHfaXpZU6101LkbrbkTppU4DEaDbefn6Fk/DnVdY="
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        initAds()
    }

    private fun initAds() {
        val environment = if (BuildConfig.DEBUG) {
            AperoAdConfig.ENVIRONMENT_DEVELOP
        } else {
            AperoAdConfig.ENVIRONMENT_PRODUCTION
        }
        aperoAdConfig = AperoAdConfig(this, API_KEY, AperoAdConfig.PROVIDER_ADMOB, environment)
        aperoAdConfig.mediationProvider = AperoAdConfig.PROVIDER_ADMOB

        val adjustConfig = AdjustConfig("")
        adjustConfig.eventAdImpression = ""
        aperoAdConfig.adjustConfig = adjustConfig

        Admob.getInstance().setFan(false)
        Admob.getInstance().setAppLovin(false)
        Admob.getInstance().setColony(false)
        Admob.getInstance().setOpenActivityAfterShowInterAds(true)
        Admob.getInstance().setDisableAdResumeWhenClickAds(true)

        AperoAd.getInstance().init(this, aperoAdConfig, false)
    }
}