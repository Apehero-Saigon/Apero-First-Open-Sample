# Language Config Options

Use `LanguageUiConfig` to configure the Language First Open screen. You can configure it directly
with the Kotlin data class or through `LanguageUiConfig.Builder()`.

## Basic Setup

```kotlin
val languageConfig = LanguageUiConfig(
    languages = listOf(
        Language.English,
        Language.Hindi,
        Language.Japanese,
        Language.Korean,
        Language.Spanish,
        Language.Turkish,
        Language.Portuguese,
    ),
    titleColor = Color.White,
    overrideTitle = "Choose Your Language",
    itemPrimaryColor = Color.Red,
    autoSelectLanguage = false,
    itemPaddingDp = 12,
    nextButtonConfig = ButtonUIConfig(
        buttonTextColor = Color.White,
        buttonBgColor = Color.Blue,
        buttonStyle = ButtonStyle.Tick,
    ),
)
```

## Available Properties

| Property | Default | Description |
| --- | --- | --- |
| `languages` | `listOf(Language.English)` | Languages shown on the screen. |
| `titleColor` | `Color.Black` | Title text color. |
| `overrideTitle` | `null` | Custom title text. When `null`, the SDK uses the default title. |
| `nextButtonConfig` | `ButtonUIConfig()` | Next button style, text color, background, and related button UI. |
| `itemPrimaryColor` | `Color.Black` | Primary color for language item UI. |
| `autoSelectLanguage` | `false` | Auto-selects a language from the device locale when possible. |
| `tapGuide` | `null` | Optional tap guide configuration. |
| `customLanguageLayoutId` | `null` | XML layout for an unselected language item. |
| `customLanguageChosenLayoutId` | `null` | XML layout for a selected language item. |
| `backgroundColor` | `null` | Screen background color. |
| `backgroundBrush` | `null` | Screen background brush, such as a gradient. |
| `backgroundImage` | `null` | Drawable resource used as the screen background. |
| `itemPaddingDp` | `12` | Spacing between language items in dp. |
| `customLanguageItemCompose` | `null` | Custom Compose content for each language item. |

## Builder Pattern

`LanguageUiConfig.Builder()` exposes setters for the same screen options. Color setters accept
`Int`, `Long`, or Compose `Color`.

```kotlin
val languageConfig = LanguageUiConfig.Builder()
    // Languages shown on the screen.
    .setLanguages(listOf(Language.English, Language.Spanish))

    // Title.
    .setTitleColor(Color.Black)
    .changeTitle("Choose Your Language")

    // Language item color.
    .setItemPrimaryColor(Color.Red)

    // Next button.
    .setButtonTextColor(Color.White)
    .setButtonBgColor(Color.Blue)
    .setNextButtonStyle(ButtonStyle.Tick)

    // Auto-select based on device locale.
    .setAutoSelectLanguage(true)

    // Background.
    .setBackgroundColor(Color(0xFFF0F0F0))
    .setBackgroundBrush(
        Brush.verticalGradient(listOf(Color.White, Color.Gray))
    )
    .setBackgroundImage(R.drawable.language_background)

    // Item spacing.
    .setItemPaddingDp(16)
    .build()
```

## Tap Guide

`TapGuide` can be used to display a guide overlay around the language list action area.

```kotlin
val languageConfig = LanguageUiConfig(
    tapGuide = TapGuide(
        size = 80.dp,
        boxAlignment = Alignment.CenterEnd,
        offset = DpOffset(x = 2.8.dp, y = 6.dp),
    )
)
```

The same option is available in the builder:

```kotlin
val languageConfig = LanguageUiConfig.Builder()
    .setTapGuide(
        TapGuide(
            size = 80.dp,
            boxAlignment = Alignment.CenterEnd,
            offset = DpOffset(x = 2.8.dp, y = 6.dp),
        )
    )
    .build()
```

## Custom XML Language Item

Use `customLanguageLayoutId` and `customLanguageChosenLayoutId` when you want XML layouts for
unselected and selected language rows.

```kotlin
val languageConfig = LanguageUiConfig.Builder()
    .setCustomLanguageLayoutId(R.layout.layout_language_item)
    .setCustomChosenLanguageLayoutId(R.layout.layout_language_item_selected)
    .build()
```

The custom XML layout must include views tagged with `languageFlag` and `languageName` so the SDK can
bind the flag and language name.

```xml
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal">

    <ImageView
        android:tag="languageFlag"
        android:layout_width="40dp"
        android:layout_height="24dp" />

    <TextView
        android:tag="languageName"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />
</LinearLayout>
```

## Custom Compose Language Item

Use `customLanguageItemCompose` when your app renders language rows with Jetpack Compose.

```kotlin
val languageConfig = LanguageUiConfig(
    customLanguageItemCompose = { language, selected, onSelectLanguage ->
        LanguageItem(
            modifier = Modifier.fillMaxWidth(),
            language = language,
            selected = selected,
            onClick = onSelectLanguage,
        )
    }
)
```

Builder version:

```kotlin
val languageConfig = LanguageUiConfig.Builder()
    .setCustomLanguageItemCompose { language, selected, onSelectLanguage ->
        LanguageItem(
            language = language,
            selected = selected,
            onClick = onSelectLanguage,
        )
    }
    .build()
```

## Background Priority

If `backgroundImage`, `backgroundBrush`, and `backgroundColor` are all set, they are applied in this
order:

```text
backgroundImage > backgroundBrush > backgroundColor
```

## Builder Defaults

The data class default for `itemPaddingDp` is `12`, while `LanguageUiConfig.Builder()` initializes
`itemPaddingDp` as `10`. Set `.setItemPaddingDp(...)` explicitly if your UI depends on exact spacing.
