Onboard config full options
==================

Using XML
```kotlin
// Config each page of the onboard screen, maximum 4 pages
val onboard1Config = AperoOnboardPageConfig(
    // Set up page with XML layout id (typical for plain content with no interactions)
    layoutOnboardContentId = R.layout.layout_onboard_1,
    // Set up page with View (typical if onboard screen has some interactions
    customView = view,
    // Set up page with Composable
    composableContent = { OnboardPageContent() },
)
// Other pages...
val onboard2Config = AperoOnboardPageConfig(
    layoutOnboardContentId = R.layout.layout_onboard_2
)
val onboard3Config = AperoOnboardPageConfig(
    layoutOnboardContentId = R.layout.layout_onboard_3
)
val onboard4Config = AperoOnboardPageConfig(
    layoutOnboardContentId = R.layout.layout_onboard_4
)

val onboardConfig = AperoOnboardUiConfig(
    // Primary color of some elements in onboard screen: indications, buttons...
    primaryColor = yourPrimaryColor,
    // Background color of pages
    backgroundColor = yourBackgroundColor,
    // Declared pages config, up to 4 configs for 4 pages
    pages = listOf(onboard1Config, onboard2Config, onboard3Config, onboard4Config),
    // Style of next button in onboard pages
    nextButtonStyle = ButtonStyle.Normal/ButtonStyle.Outline/ButtonStyle.Solid,
    // Style of start button at the end of onboard
    startButtonStyle = ButtonStyle.Normal/ButtonStyle.Outline/ButtonStyle.Solid,
)
```

Using Jetpack Compose
```kotlin
    // Config for onboard screen 1 
    val onboard1Config = AperoOnboardPageConfig(composableContent = { OnboardContentPage1() })
    // Config for onboard screen 2
    val onboard2Config = AperoOnboardPageConfig(composableContent = { OnboardContentPage2() })
    // Config for onboard screen 3
    val onboard3Config = AperoOnboardPageConfig(composableContent = { OnboardContentPage3() })
    // Combine config for onboard screens
    val onboardConfig = AperoOnboardUiConfig(
        pages = listOf(
            onboard1Config,
            onboard2Config,
            onboard3Config
        ),
        primaryColor = PrimaryColor.toArgb().toLong(),
    )
```