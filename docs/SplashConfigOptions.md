Splash Screen config full options
==================

```kotlin
val splashConfig = AperoSplashUiConfig.Builder()
    // App icon in splash screen
    .setAppIconId(R.drawable.app_icon)
    // App icon size in splash screen
    .setAppIconSizeDp(200)
    // App icon rounded corner radius size in splash screen
    .setAppIconCornerRadiusDp(40)
    
    // Custom splash screen layout, will ignore all app icon configs if provided
    .setCustomSplashLayoutId(R.layout.layout_splash)
    
    // Build the config
    .build()
```