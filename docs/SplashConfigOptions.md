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
    
    // Set true to wait for other initializations in app such as remote config, api...
    // After initialization done, call AperoFO.finishSplashInitialization() to continue splash
    .setWaitForInitialization(boolean)
    
    // Build the config
    .build()
```