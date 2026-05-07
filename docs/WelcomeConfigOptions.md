# Welcome Config Options

Use `WelcomeUiConfig` to configure the optional Welcome screen after onboarding. Attach it to the
first-open flow through `FOConfig.Builder().setWelcomeUiConfig(...)`.

## Basic Setup

```kotlin
val welcomeConfig = WelcomeUiConfig(
    composableContent = {
        WelcomeScreenContent(
            onContinue = {
                FOManager.completeWelcomeScreen()
            }
        )
    },
    backgroundColor = Color.White,
)

val config = FOConfig.Builder()
    .setWelcomeUiConfig(welcomeConfig)
    .build()
```

## Available Properties

| Property | Default | Description |
| --- | --- | --- |
| `enableWelcomeScreen` | `true` | Enables the Welcome screen. Disabling only works when triple-impression flow is also disabled. |
| `viewContentProvider` | `null` | Provides an Android `View` used as the Welcome content. |
| `composableContent` | `null` | Provides Compose content used as the Welcome content. |
| `backgroundColor` | `null` | Screen background color. |
| `backgroundBrush` | `null` | Screen background brush, such as a gradient. |
| `backgroundImage` | `null` | Drawable resource used as the screen background. |

## Content Priority

`WelcomeScreen` renders custom content in this order:

```text
viewContentProvider > composableContent
```

Use only one content provider per screen. If both are provided, the View content is used and the
Compose content is ignored.

## Custom View Welcome

Use `viewContentProvider` when your Welcome screen is built with XML or Android Views.

```kotlin
val welcomeConfig = WelcomeUiConfig(
    viewContentProvider = {
        layoutInflater.inflate(R.layout.layout_welcome, null, false).apply {
            findViewById<View>(R.id.continueButton).setOnClickListener {
                FOManager.completeWelcomeScreen()
            }
        }
    },
)
```

Builder version:

```kotlin
val welcomeConfig = WelcomeUiConfig.Builder()
    .setViewContentProvider {
        layoutInflater.inflate(R.layout.layout_welcome, null, false).apply {
            findViewById<View>(R.id.continueButton).setOnClickListener {
                FOManager.completeWelcomeScreen()
            }
        }
    }
    .build()
```

## Custom Compose Welcome

Use `composableContent` when your Welcome screen is built with Jetpack Compose.

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

Builder version:

```kotlin
val welcomeConfig = WelcomeUiConfig.Builder()
    .setComposableContent {
        WelcomeScreenContent(
            onContinue = {
                FOManager.completeWelcomeScreen()
            }
        )
    }
    .build()
```

## Complete Welcome Screen

Custom Welcome content must call `FOManager.completeWelcomeScreen()` when the user finishes the
screen.

When the complete event is received, `WelcomeFragment`:

- Logs `welcome_scr_next_click`.
- Shows `interStart` first when triple-impression flow is enabled.
- Otherwise may navigate to the Prepare screen when remaining native ads and prepare config require
  it.
- Marks first-open as onboarded.
- Calls `FOCallback.onFinished()`.
- Finishes the host activity.

## Welcome Duplicate Screen

Call `FOManager.showWelcomeDupScreen()` when you want to switch from the normal Welcome ad slot to
the Welcome duplicate slot.

```kotlin
FOManager.showWelcomeDupScreen()
```

When the duplicate event is received, `WelcomeFragment`:

- Loads `nativeWelcomeDup`.
- Logs `welcome_dup_scr`.
- Sets the internal `alternative` state to `true`.
- Renders ads from `nativeWelcomeDup` instead of `nativeWelcome`.

## Disable Welcome Screen

You can disable Welcome through `FOConfig.Builder`:

```kotlin
val config = FOConfig.Builder()
    .disableWelcomeScreen()
    .build()
```

Or through `WelcomeUiConfig.Builder`:

```kotlin
val welcomeConfig = WelcomeUiConfig.Builder()
    .disableWelcomeScreen()
    .build()
```

Important: disabling the Welcome screen only works when `enableTripleImpressionFlow` is `false`.
Triple-impression flow keeps Welcome enabled.

## Background

Welcome uses `CoreLayout` with these background inputs:

```kotlin
val welcomeConfig = WelcomeUiConfig(
    backgroundColor = Color.White,
    backgroundBrush = Brush.verticalGradient(
        listOf(Color.White, Color.LightGray)
    ),
    backgroundImage = R.drawable.welcome_background,
)
```

Builder version:

```kotlin
val welcomeConfig = WelcomeUiConfig.Builder()
    .setBackgroundColor(Color.White)
    .setBackgroundBrush(
        Brush.verticalGradient(listOf(Color.White, Color.LightGray))
    )
    .setBackgroundImage(R.drawable.welcome_background)
    .build()
```

## Ads Area Behavior

`WelcomeScreen` renders a native ad in the bottom bar:

- Normal Welcome uses `AdsProvider.nativeWelcome`.
- Duplicate Welcome uses `AdsProvider.nativeWelcomeDup`.
- If the selected ad group is skipped, the bottom ad bar is not rendered.
- The ad layout is selected from common config `force_layout_welcome`.

## Builder Methods

```kotlin
WelcomeUiConfig.Builder()
    .disableWelcomeScreen()
    .setViewContentProvider { welcomeView }
    .setComposableContent { WelcomeScreenContent() }
    .setBackgroundColor(Color.White)
    .setBackgroundBrush(Brush.verticalGradient(listOf(Color.White, Color.Gray)))
    .setBackgroundImage(R.drawable.welcome_background)
    .build()
```
