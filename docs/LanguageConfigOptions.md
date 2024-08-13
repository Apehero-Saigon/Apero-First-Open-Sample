Splash Screen config full options
==================

```kotlin
val languageConfig = AperoLanguageUiConfig.Builder()
    .setLanguages(
        listOf(
            Language.English,
            Language.German,
            // Other languages
        )
    )
    // Set Primary color of elements on screen such as radio buttons, next button, ads button
    .setPrimaryColor(Color.BLUE)
    
    .build()
```