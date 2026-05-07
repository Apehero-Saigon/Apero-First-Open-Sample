# First Open Library Sample

This sample shows how to integrate the Astronex First Open SDK, configure the first-open flow, and
customize Splash, Language, Onboarding, Welcome, and ads.

> Note: This README is written for `apero.aperosg.astronex:firstopen:4.1.17.7`.

## Flow Overview

Default flow:

```text
Splash -> Language -> Language duplicate -> Onboarding -> Welcome -> Welcome duplicate -> Finish
```

Default flow without Welcome:

```text
Splash -> Language -> Language duplicate -> Onboarding -> Finish
```

Triple-impression flow:

```text
Splash -> Language -> Language duplicate -> Onboarding -> Welcome -> Welcome duplicate -> Inter Start -> Finish
```

Use `FOConfig.Builder().enableTripleImpressionFlow()` only when the product flow requires it.

## Related Docs

- [SDK setup](docs/SetupSDK.md)
- [Splash config](docs/SplashConfigOptions.md)
- [Language config](docs/LanguageConfigOptions.md)
- [Onboard config](docs/OnboardConfigOptions.md)
- [Welcome config](docs/WelcomeConfigOptions.md)
- [Ads config](docs/AdsConfigOptions.md)
- [Monetization](docs/Monetization.md)
- [Google Play Subscription](docs/AppBilling.md)

## Requirements

Add the SDK dependency in the app module:

```kotlin
implementation("apero.aperosg.astronex:firstopen:4.1.17.7")
```

Make sure the required Maven repositories and credentials are configured in `settings.gradle.kts`.
See [SetupSDK.md](docs/SetupSDK.md) for the full repository and `Application` setup.

## Activity Setup

Create a `FirstOpenActivity`, make it the launcher activity, and keep `configChanges`. Some devices
restart the activity after a language change if this line is missing.

```xml
<activity
    android:name=".FirstOpenActivity"
    android:configChanges="locale|layoutDirection|orientation|screenLayout|uiMode|touchscreen|screenSize|smallestScreenSize"
    android:exported="true">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>
</activity>
```

Extend `AstronexFirstOpenActivity` and return `FOConfig` from `onSetupFOConfig()`. The SDK starts
the flow automatically.

```kotlin
class FirstOpenActivity : AstronexFirstOpenActivity() {
    override fun onSetupFOConfig(): FOConfig {
        val callback = object : FOCallback() {
            override fun onConsentResult(canLoadAds: Boolean) {
                // Disable or delay ad loading if consent is not granted.
            }

            override fun onLanguageConfirm(language: Language) {
                // Persist language or update app locale if needed.
            }

            override fun onOnboardPageChanged(pageIndex: Int, pageSize: Int) {
                // Track progress or preload resources for upcoming screens.
            }

            override fun onNotificationPermissionResult(granted: Boolean) {
                // Handle notification permission result.
            }

            override fun onForceUpdateRequired() {
                // The SDK blocks Splash with the force-update dialog.
            }

            override fun onFinished(isFirstOpen: Boolean) {
                startActivity(Intent(this@FirstOpenActivity, MainActivity::class.java))
                finish()
            }
        }

        return FOConfig.Builder()
            .setPrimaryColor(Color.Blue)
            .setCallback(callback)
            .setAdsConfig(createAdsConfig())
            .setAdUiConfig(createNativeAdUiConfig())
            .setSplashUiConfig(createSplashConfig())
            .setLanguageUiConfig(createLanguageConfig())
            .setOnboardUiConfig(createOnboardConfig())
            .setWelcomeUiConfig(createWelcomeConfig())
            .build()
    }
}
```

Sample implementations:

- Compose: [FirstOpenActivity.kt](app/src/main/java/apero/aperosg/monetizationsample/FirstOpenActivity.kt)
- XML/View: [NoneComposableFirstOpenActivity.kt](app/src/main/java/apero/aperosg/monetizationsample/NoneComposableFirstOpenActivity.kt)

## Splash

`SplashUiConfig` controls the Splash UI. Render priority is:

```text
customSplashLayoutId > composableContent > default icon/loading UI
```

Default icon UI:

```kotlin
val splashConfig = SplashUiConfig(
    appIconId = R.drawable.app_icon,
    appIconSizeDp = 180,
    appIconCornerRadiusDp = 32,
)
```

XML Splash:

```kotlin
val splashConfig = SplashUiConfig.Builder()
    .setCustomSplashLayoutId(R.layout.layout_splash)
    .build()
```

Compose Splash:

```kotlin
val splashConfig = SplashUiConfig(
    composableContent = {
        SplashScreenContent()
    },
)
```

If Splash must wait for app initialization, set `waitForInitialization = true` and call
`FOManager.finishSplashInitialization()` after your work finishes.

```kotlin
val splashConfig = SplashUiConfig(
    waitForInitialization = true,
    appIconId = R.drawable.app_icon,
)

loadRemoteConfig {
    FOManager.finishSplashInitialization()
}
```

## Language

`LanguageUiConfig` controls the Language First Open screen.

```kotlin
val languageConfig = LanguageUiConfig(
    languages = listOf(
        Language.English,
        Language.Hindi,
        Language.Japanese,
        Language.Korean,
        Language.Spanish,
    ),
    overrideTitle = "Choose Your Language",
    titleColor = Color.Black,
    itemPrimaryColor = Color.Blue,
    autoSelectLanguage = false,
    nextButtonConfig = ButtonUIConfig(
        buttonStyle = ButtonStyle.Tick,
        buttonTextColor = Color.White,
        buttonBgColor = Color.Blue,
    ),
)
```

Custom XML language item layouts must keep the required tags so the SDK can bind the flag and text.
The XML implementation itself does not need code changes.

```xml
<ImageView
    android:tag="languageFlag"
    android:layout_width="40dp"
    android:layout_height="24dp" />

<TextView
    android:tag="languageName"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content" />
```

Configure the XML layouts:

```kotlin
val languageConfig = LanguageUiConfig.Builder()
    .setCustomLanguageLayoutId(R.layout.layout_language_element)
    .setCustomChosenLanguageLayoutId(R.layout.layout_language_element_chosen)
    .build()
```

Compose language item:

```kotlin
val languageConfig = LanguageUiConfig(
    customLanguageItemCompose = { language, selected, onSelectLanguage ->
        LanguageItem(
            modifier = Modifier.fillMaxWidth(),
            language = language,
            selected = selected,
            onClick = onSelectLanguage,
        )
    },
)
```

For a custom language settings screen, call `FOManager.setLanguage(languageCode)` after the user
selects a language.

## Onboarding

`OnboardUiConfig` configures the onboarding container. `OnboardPageConfig` configures each page.

Content priority for each page:

```text
layoutOnboardContentId > customView > composableContent
```

XML pages:

```kotlin
val onboard1Config = OnboardPageConfig(layoutOnboardContentId = R.layout.layout_onboard_1)
val onboard2Config = OnboardPageConfig(layoutOnboardContentId = R.layout.layout_onboard_2)
val onboard3Config = OnboardPageConfig(layoutOnboardContentId = R.layout.layout_onboard_3)
```

Compose pages:

```kotlin
val buttonUIConfig = ButtonUIConfig(
    buttonTextColor = Color.White,
    buttonBgColor = Color.Blue,
    buttonStyle = ButtonStyle.Normal,
)

val onboard1Config = OnboardPageConfig(
    composableContent = { OnboardScreen1Compose() },
    buttonUIConfig = buttonUIConfig,
)
```

Container config:

```kotlin
val onboardConfig = OnboardUiConfig(
    pages = listOf(onboard1Config, onboard2Config, onboard3Config),
    indicationColor = Color.Magenta,
    indicationUnselectColor = Color.Cyan,
    indicationSpacing = 8,
    backgroundColor = Color.White,
)
```

If `ButtonStyle.FullSolid` is used, the next button is rendered below the indicator row. Other
button styles render the next button at the end of the indicator row.

## Welcome

`WelcomeUiConfig` controls the optional Welcome screen. Content priority is:

```text
viewContentProvider > composableContent
```

Custom View/XML Welcome:

```kotlin
val welcomeConfig = WelcomeUiConfig.Builder()
    .setViewContentProvider { setUpWelcomeScreen() }
    .build()

private fun setUpWelcomeScreen(): View {
    val welcomeScreenView = layoutInflater.inflate(R.layout.layout_welcome_scr, null, false)
    // Set up your welcome screen view here.
    return welcomeScreenView
}
```

Compose Welcome:

```kotlin
val welcomeConfig = WelcomeUiConfig(
    composableContent = {
        WelcomeScreenContent(
            onContinue = {
                FOManager.completeWelcomeScreen()
            }
        )
    },
)
```

Custom Welcome content must call `FOManager.completeWelcomeScreen()` when the user finishes the
screen. To switch to the Welcome duplicate ad slot, call:

```kotlin
FOManager.showWelcomeDupScreen()
```

Disable Welcome only when triple-impression flow is not enabled:

```kotlin
val config = FOConfig.Builder()
    .disableWelcomeScreen()
    .build()
```

## Ads

`FOAdsConfig` provides ad unit IDs. Configure only the placements your flow uses.

```kotlin
val adsConfig = FOAdsConfig.Builder()
    .setLoadAdTimeOut(30_000L)
    .setInterSplashHighId(BuildConfig.inter_splash_high)
    .setInterSplashId(BuildConfig.inter_splash)
    .setBannerSplashId(BuildConfig.banner_splash)
    .setNativeLanguageHighId(BuildConfig.native_language_high)
    .setNativeLanguageId(BuildConfig.native_language)
    .setNativeLanguageDupHighId(BuildConfig.native_language_dup_high)
    .setNativeLanguageDupId(BuildConfig.native_language_dup)
    .setNativeWelcomeHighId(BuildConfig.native_welcome_high)
    .setNativeWelcomeId(BuildConfig.native_welcome)
    .setNativeWelcomeDupHighId(BuildConfig.native_welcome_dup_high)
    .setNativeWelcomeDupId(BuildConfig.native_welcome_dup)
    .setNativeOnboard1HighId(BuildConfig.native_onboard_1_high)
    .setNativeOnboard1Id(BuildConfig.native_onboard_1)
    .setNativeOnboardFullscreenHighId(BuildConfig.native_ob_fullscr_high)
    .setNativeOnboardFullscreenId(BuildConfig.native_ob_fullscr)
    .build()
```

Native ad UI:

```kotlin
val adUiConfig = NativeAdUiConfig(
    tagColor = Color.Blue,
    backgroundColor = Color(0xFFEAF1FF),
    headlineColor = Color(0xFF333333),
    bodyColor = Color.Black,
    buttonTextColor = Color.White,
    buttonColor = Color.Red,
    adFullScrColor = Color.White,
    adFullScrBrush = Brush.verticalGradient(listOf(Color.Red, Color.Yellow)),
)
```

If both `adFullScrColor` and `adFullScrBrush` are set, `adFullScrBrush` is used.

## Background Priority

For screens that support image, brush, and color backgrounds, the effective priority is:

```text
backgroundImage > backgroundBrush > backgroundColor
```

Some screens fall back to `Color.White` when no background is provided. See each config doc for
details.
