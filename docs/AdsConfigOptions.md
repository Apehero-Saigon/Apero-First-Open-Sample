Ads config full options
==================

```kotlin
val adsConfig = AperoFOAdsConfig.Builder()
    //Splash screen
    .setInterSplashHighId(BuildConfig.inter_splash_high)
    .setInterSplashId(BuildConfig.inter_splash)
    .setBannerSplashId(BuildConfig.banner_splash)
    //Language screen
    .setNativeLanguageHighId(BuildConfig.native_language_high)
    .setNativeLanguageId(BuildConfig.native_language)
    .setNativeLanguageDupHighId(BuildConfig.native_language_dup_high)
    .setNativeLanguageDupId(BuildConfig.native_language_dup)
    //Onboarding screen
    .setNativeOnboardFullscreenHighId(BuildConfig.native_ob_fullscr_high)
    .setNativeOnboardFullscreenId(BuildConfig.native_ob_fullscr)
    .setNativeOnboardFullscreen2HighId(BuildConfig.native_ob_fullscr_2_high)
    .setNativeOnboardFullscreen2Id(BuildConfig.native_ob_fullscr_2)
    .setNativeOnboard1HighId(BuildConfig.native_onb_1_high)
    .setNativeOnboard1Id(BuildConfig.native_onb_1)
    .setBannerOnboardId(BuildConfig.banner_all)
    .setBannerOnboard2Id(BuildConfig.banner_all)
    // Set native welcome
    .setNativeWelcomeHighId(BuildConfig.native_welcome_high)
    .setNativeWelcomeId(BuildConfig.native_welcome)
    .setNativeWelcomeDupHighId(BuildConfig.native_welcome_dup_high)
    .setNativeWelcomeDupId(BuildConfig.native_welcome_dup)
    //inter start
    .setInterStartId(BuildConfig.inter_start)

    .build()
```