Splash Screen config full options
==================

### Using Kotlin Data Class

```kotlin
val languages: List<Language> = listOf(
    Language.English,
    Language.Hindi,
    Language.Japanese,
    Language.Korean,
    Language.Spanish,
    Language.Turkish,
    Language.Portuguese,
)

val languageConfig = LanguageUiConfig(
    languages = languages,
    titleColor = Color.White,
    //Custom the confirm button of language screen
    nextButtonConfig = ButtonUIConfig(
        buttonTextColor = Color(0xFFEE9FFC),
        buttonBgColor = backgroundColor,
        buttonStyle = ButtonStyle.Tick,
    ),
    //Padding between each Language item
    itemPaddingDp = 8,
    autoSelectLanguage = false,
    backgroundColor = backgroundColor,
    customLanguageItemCompose = { language, selected, onClick ->
        LanguageItem(
            language = language,
            selected = selected,
            onClick = onClick
        )
    }
)
```

### Using Builder Pattern

The builder pattern provides a flexible way to configure the language selection screen.

```kotlin
val languageConfig = LanguageUiConfig.Builder()
// Sets the list of languages to be displayed.
    .setLanguages(listOf(Language.English, Language.Spanish))

// Sets the primary color for UI elements like the radio button of the selected language.
    .setItemPrimaryColor(Color.Red)

// Sets the text color of the 'Next' button.
    .setButtonTextColor(Color.White)

// Sets the background color of the 'Next' button.
    .setButtonBgColor(Color.Blue)

// Sets the color of the screen title.
    .setTitleColor(Color.Black)

// Overrides the default title of the screen.
    .changeTitle("Choose Your Language")

// Sets the background color of the screen.
    .setBackgroundColor(Color(0xFFF0F0F0))

// Sets a brush for the screen background for gradient effects.
    .setBackgroundBrush(
        Brush.verticalGradient(listOf(Color.White, Color.Gray))
    )

// Sets a drawable resource as the screen background.
    .setBackgroundImage(R.drawable.my_background)

// If set to `true`, the app will try to auto-select a language from the list based on the device's locale. Defaults to `false`.
    .setAutoSelectLanguage(true)

// Defines the style of the 'Next' button. Can be ButtonStyle.Normal, ButtonStyle.Outline, ButtonStyle.Solid or ButtonStyle.Tick.
    .setNextButtonStyle(ButtonStyle.Solid)

// Sets the padding between language items in DPs.
    .setItemPaddingDp(16)

// (For XML views) Sets a custom layout for the unselected language item.
    .setCustomLanguageLayoutId(R.layout.custom_language_item)

// (For XML views) Sets a custom layout for the selected language item.
    .setCustomChosenLanguageLayoutId(R.layout.custom_language_item_selected)

// (For Jetpack Compose) Provides a custom composable for rendering each language item.
    .setCustomLanguageItemCompose { language, selected, onSelectLanguage ->
        MyCustomLanguageItem(
            language = language,
            isSelected = selected,
            onClick = onSelectLanguage
        )
    }
```
