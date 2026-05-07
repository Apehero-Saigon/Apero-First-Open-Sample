# Ads Config Options

Use `FOAdsConfig.Builder()` to provide ad unit IDs for the first-open flow. Configure only the
placements that your app uses. Any placement left as `null` is skipped by the SDK.

## Basic Setup

```kotlin
val adsConfig = FOAdsConfig.Builder()
    // Ad load timeout. Default is 30_000 ms.
    .setLoadAdTimeOut(30_000L)

    // Splash screen
    .setInterSplashHighId(BuildConfig.inter_splash_high)
    .setInterSplashId(BuildConfig.inter_splash)
    .setBannerSplashId(BuildConfig.banner_splash)

    // Language screen
    .setNativeLanguageHighId(BuildConfig.native_language_high)
    .setNativeLanguageId(BuildConfig.native_language)

    // Language duplicate screen
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

    // Welcome duplicate screen
    .setNativeWelcomeDupHighId(BuildConfig.native_welcome_dup_high)
    .setNativeWelcomeDupId(BuildConfig.native_welcome_dup)

    // Onboarding pages
    .setNativeOnboard1HighId(BuildConfig.native_onboard_1_high)
    .setNativeOnboard1Id(BuildConfig.native_onboard_1)
    .setNativeOnboard2HighId(BuildConfig.native_onboard_2_high)
    .setNativeOnboard2Id(BuildConfig.native_onboard_2)
    .setNativeOnboard3HighId(BuildConfig.native_onboard_3_high)
    .setNativeOnboard3Id(BuildConfig.native_onboard_3)

    // Fullscreen onboarding ad
    .setNativeOnboardFullscreenHighId(BuildConfig.native_ob_fullscr_high)
    .setNativeOnboardFullscreenId(BuildConfig.native_ob_fullscr)

    // Flexible fullscreen native ad
    .setNativeFullscrFlexHighId(BuildConfig.native_fullscr_flex_high)
    .build()
```

## Available Builder Methods

| Method | Placement |
| --- | --- |
| `setLoadAdTimeOut(timeOut)` | Ad load timeout in milliseconds. |
| `setInterSplashHighId(id)` | High-floor interstitial on Splash. |
| `setInterSplashId(id)` | Normal interstitial on Splash. |
| `setBannerSplashId(id)` | Banner on Splash. |
| `setNativeLanguageHighId(id)` | High-floor native ad on Language. |
| `setNativeLanguageId(id)` | Normal native ad on Language. |
| `setNativeLanguageDupHighId(id)` | High-floor native ad on Language duplicate. |
| `setNativeLanguageDupId(id)` | Normal native ad on Language duplicate. |
| `setNativeLanguageDupInvisibleId(id)` | Invisible preload slot for Language duplicate. |
| `setNativeWelcomeHighId(id)` | High-floor native ad on Welcome. |
| `setNativeWelcomeId(id)` | Normal native ad on Welcome. |
| `setNativeWelcomeDupHighId(id)` | High-floor native ad on Welcome duplicate. |
| `setNativeWelcomeDupId(id)` | Normal native ad on Welcome duplicate. |
| `setNativeOnboard1HighId(id)` | High-floor native ad on Onboard page 1. |
| `setNativeOnboard1Id(id)` | Normal native ad on Onboard page 1. |
| `setNativeOnboard2HighId(id)` | High-floor native ad on Onboard page 2. |
| `setNativeOnboard2Id(id)` | Normal native ad on Onboard page 2. |
| `setNativeOnboard3HighId(id)` | High-floor native ad on Onboard page 3. |
| `setNativeOnboard3Id(id)` | Normal native ad on Onboard page 3. |
| `setNativeOnboardFullscreenHighId(id)` | High-floor fullscreen native ad on Onboarding. |
| `setNativeOnboardFullscreenId(id)` | Normal fullscreen native ad on Onboarding. |
| `setNativeFullscrFlexHighId(id)` | High-floor flexible fullscreen native ad. |

## Internal or Disabled Slots

`FOAdsConfig` still has fields for these placements, but their builder setters are currently
commented out in the source file, so do not use them from app code unless the SDK exposes them again:

- `bannerOnboard2Id`
- `bannerOnboard3Id`
- `nativeOnboardFullscreen2HighId`
- `nativeOnboardFullscreen2Id`
- `interStartId`
- `nativeObFullscrInvisible1Id`
- `nativeObFullscrInvisible2Id`

## Notes

- Use the `BuildConfig` names from your own app module; the names above are examples.
- High-floor IDs should be configured before normal IDs for the same placement.
- If the user does not consent to ads, handle that in `FOCallback.onConsentResult(canLoadAds)`.
