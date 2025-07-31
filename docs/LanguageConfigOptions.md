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
    // Config language FO screen to initially select a language
    .setAutoSelectLanguage(true/false)
    // Style of next button
    .setNextButtonStyle(ButtonStyle.Normal/ButtonStyle.Outline/ButtonStyle.Solid)
    
    // Using jetpack compose
    .setCustomLanguageItemCompose { language, selected, onSelectLanguage ->
        LanguageItem(
            modifier = Modifier.fillMaxWidth(),
            language = AppLanguage.entries.find { language.code == it.code }
                ?: AppLanguage.English,
            selected = selected,
            onClick = onSelectLanguage,
        )
    }
    // Using XML layouts
    .setCustomLanguageLayoutId(R.layout.layout_language)
    .setCustomChosenLanguageLayoutId(R.layout.layout_language_chosen)
    
    .build()
```