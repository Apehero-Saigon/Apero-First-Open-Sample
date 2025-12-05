Onboard config full options
==================

Using XML
```kotlin
// Config each page of the onboard screen, maximum 4 pages
val onboard1Config = OnboardPageConfig(
    // Set up page with XML layout id (typical for plain content with no interactions)
    layoutOnboardContentId = R.layout.layout_onboard_1,
    // Set up page with View (typical if onboard screen has some interactions
    customView = view,
    // Set up page with Composable
    composableContent = { OnboardPageContent() },
)
// Other pages...
val onboard2Config = OnboardPageConfig(
    layoutOnboardContentId = R.layout.layout_onboard_2
)
val onboard3Config = OnboardPageConfig(
    layoutOnboardContentId = R.layout.layout_onboard_3
)

val onboardConfig = OnboardUiConfig(
    // Primary color of some elements in onboard screen: indications, buttons...
    primaryColor = yourPrimaryColor,
    // Background color of pages
    backgroundColor = yourBackgroundColor,
    indicationColor = yourIndicationColor,
    indicationUnselectColor = yourIndicationUnselectColor,
    // Declared pages config, up to 4 configs for 4 pages
    pages = listOf(onboard1Config, onboard2Config, onboard3Config, onboard4Config),
)
```

Using Jetpack Compose
```kotlin

// Set up Onboard screens config
// Config for onboard screen 1
val onboard1Config = OnboardPageConfig(
    layoutOnboardContentId = R.layout.layout_onboard_1,
    buttonUIConfig = ButtonUIConfig(
        buttonTextColor = Color.White,
        buttonStyle = ButtonStyle.Normal,
        buttonBgColor = Color.Blue,
        buttonBgBrush = Brush.verticalGradient(
            listOf(
                Color(0xFF673AB7).copy(0.5f),
                Color(0xFFFF00FD).copy(0.5f),
            )
        )
    )
)
// Config for onboard screen 2
val onboard2Config = OnboardPageConfig(
    layoutOnboardContentId = R.layout.layout_onboard_2,
    buttonUIConfig = ButtonUIConfig(
        buttonTextColor = Color.Red,
        buttonStyle = ButtonStyle.FullSolid,
        buttonBgColor = Color.Magenta,
    ),
)
// Config for onboard screen 3
val onboard3Config = OnboardPageConfig(
    layoutOnboardContentId = R.layout.layout_onboard_3,
    buttonUIConfig = ButtonUIConfig(
        buttonTextColor = Color.Yellow,
        buttonStyle = ButtonStyle.Outline,
        buttonBgColor = Color.Magenta,
    ),
)
// Combine config for onboard screens
val onboardConfig = OnboardUiConfig(
    pages = listOf(onboard1Config, onboard2Config, onboard3Config),
    indicationColor = Color.Green,
    indicationUnselectColor = Color.Yellow,
    backgroundImage = R.drawable.img_onboard_1_test,
)
```

### Using Builder Pattern

The builder pattern provides a flexible way to configure the onboard screen.

```kotlin
val onboardConfig = OnboardUiConfig.Builder()
    // Sets the list of pages for the onboard screen.
    .setPages(listOf(onboard1Config, onboard2Config, onboard3Config))

    // Sets the color of the selected page indicator.
    .setIndicationColor(Color.Green)

    // Sets the color of the unselected page indicators.
    .setIndicationUnselectColor(Color.Yellow)

    // Sets the background color of the onboard screen.
    .setBackgroundColor(Color.White)

    // Sets a brush for the screen background for gradient effects.
    .setBackgroundBrush(
        Brush.verticalGradient(listOf(Color.White, Color.Gray))
    )

    // Sets a drawable resource as the screen background.
    .setBackgroundImage(R.drawable.my_background)
    .build()
```
