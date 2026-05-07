# Splash Config Options

Use `SplashUiConfig` to configure the Splash screen UI. Attach it to the first-open flow through
`FOConfig.Builder().setSplashUiConfig(...)`.

## Basic Setup

```kotlin
val splashConfig = SplashUiConfig(
    appIconId = R.drawable.app_icon,
    appIconSizeDp = 200,
    appIconCornerRadiusDp = 40,
)

val config = FOConfig.Builder()
    .setSplashUiConfig(splashConfig)
    .build()
```

## Available Properties

| Property | Default | Description |
| --- | --- | --- |
| `waitForInitialization` | `false` | When `true`, Splash waits until `FOManager.finishSplashInitialization()` is called before showing ads or continuing. |
| `requestNotification` | `true` | Config flag for notification permission intent. In the current Splash implementation, the actual permission gate is driven by common remote config. |
| `customSplashLayoutId` | `null` | XML layout resource rendered as the whole Splash content. |
| `composableContent` | `null` | Compose content rendered as the whole Splash content. |
| `appIconId` | `null` | Drawable shown by the default Splash UI. |
| `appIconSizeDp` | `200` | Default Splash icon size in dp. |
| `appIconCornerRadiusDp` | `40` | Default Splash icon corner radius in dp. |

## Render Priority

`SplashFragment` renders Splash content in this order:

```text
customSplashLayoutId > composableContent > default icon/loading UI
```

If `customSplashLayoutId` is set, Compose content and default icon fields are ignored. If
`composableContent` is set, the default icon/loading UI is ignored.

## Default Splash UI

When neither XML nor Compose content is provided, the SDK renders:

- `appIconId`, if provided.
- A black `CircularProgressIndicator`.
- The SDK text resource `preparing_your_experience`.

```kotlin
val splashConfig = SplashUiConfig(
    appIconId = R.drawable.app_icon,
    appIconSizeDp = 180,
    appIconCornerRadiusDp = 32,
)
```

Builder version:

```kotlin
val splashConfig = SplashUiConfig.Builder()
    .setAppIconId(R.drawable.app_icon)
    .setAppIconSizeDp(180)
    .setAppIconCornerRadiusDp(32)
    .build()
```

## Custom XML Splash

Use `customSplashLayoutId` when your Splash screen is built with XML.

```kotlin
val splashConfig = SplashUiConfig(
    customSplashLayoutId = R.layout.layout_splash,
)
```

Builder version:

```kotlin
val splashConfig = SplashUiConfig.Builder()
    .setCustomSplashLayoutId(R.layout.layout_splash)
    .build()
```

## Custom Compose Splash

Use `composableContent` when your Splash screen is built with Jetpack Compose.

```kotlin
val splashConfig = SplashUiConfig(
    composableContent = {
        SplashScreenContent()
    },
)
```

Builder version:

```kotlin
val splashConfig = SplashUiConfig.Builder()
    .setComposableContent {
        SplashScreenContent()
    }
    .build()
```

## Wait for App Initialization

Use `waitForInitialization = true` when Splash must wait for app work such as remote config,
experiment setup, or other startup tasks.

```kotlin
override fun onSetupFOConfig(): FOConfig {
    val splashConfig = SplashUiConfig(
        waitForInitialization = true,
        appIconId = R.drawable.app_icon,
    )

    loadRemoteConfig {
        FOManager.finishSplashInitialization()
    }

    return FOConfig.Builder()
        .setSplashUiConfig(splashConfig)
        .build()
}
```

Builder version:

```kotlin
val splashConfig = SplashUiConfig.Builder()
    .setWaitForInitialization(true)
    .build()
```

How it works:

- `FOManager.setSplashWaitForInit(splashUiConfig)` sets the internal splash initialization flow to
  `false` when `waitForInitialization` is `true`.
- `SplashFragment` waits for that flow before showing ads or navigating away from Splash.
- Call `FOManager.finishSplashInitialization()` exactly once your app initialization has completed.

## Splash Flow Behavior

`SplashFragment` also coordinates startup work around this UI:

- Requests CMP consent and calls `FOCallback.onConsentResult(canRequestAds)`.
- Waits for monetization ads initialization before starting the Splash flow.
- Shows a force-update dialog and stops the flow when `AppUpdateChecker.shouldForceUpdate()` returns
  `true`.
- Loads Splash banner and interstitial ads.
- Preloads next-screen native ads when the user has not completed onboarding.
- Requests notification permission when the SDK common config allows it.
- Calls `FOCallback.onFinished(false)` and finishes the host activity if the user has already
  completed onboarding.

## Builder Methods

```kotlin
SplashUiConfig.Builder()
    .setWaitForInitialization(true)
    .setRequestNotification(true)
    .setCustomSplashLayoutId(R.layout.layout_splash)
    .setComposableContent { SplashScreenContent() }
    .setAppIconId(R.drawable.app_icon)
    .setAppIconSizeDp(200)
    .setAppIconCornerRadiusDp(40)
    .build()
```
