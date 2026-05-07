# Onboard Config Options

Use `OnboardUiConfig` to configure the onboarding screen container and use `OnboardPageConfig` to
configure each onboarding page.

## Basic Setup

```kotlin
val onboard1Config = OnboardPageConfig(
    composableContent = { OnboardScreen1Compose() },
    buttonUIConfig = ButtonUIConfig(
        buttonTextColor = Color.White,
        buttonBgColor = Color.Blue,
        buttonStyle = ButtonStyle.Normal,
    ),
)

val onboard2Config = OnboardPageConfig(
    composableContent = { OnboardScreen2Compose() },
    buttonUIConfig = ButtonUIConfig(
        buttonTextColor = Color.White,
        buttonBgColor = Color.Blue,
        buttonStyle = ButtonStyle.Normal,
    ),
)

val onboard3Config = OnboardPageConfig(
    composableContent = { OnboardScreen3Compose() },
    buttonUIConfig = ButtonUIConfig(
        buttonTextColor = Color.White,
        buttonBgColor = Color.Blue,
        buttonStyle = ButtonStyle.FullSolid,
        buttonFullSolidSpacing = 24.dp,
    ),
)

val onboardConfig = OnboardUiConfig(
    pages = listOf(onboard1Config, onboard2Config, onboard3Config),
    indicationColor = Color.Magenta,
    indicationUnselectColor = Color.Cyan,
    indicationSpacing = 8,
    backgroundColor = Color.White,
)
```

## OnboardUiConfig Properties

| Property | Default | Description |
| --- | --- | --- |
| `pages` | `emptyList()` | List of `OnboardPageConfig` used by the onboarding flow. |
| `indicationColor` | `Color.Black` | Color of the selected indicator. |
| `indicationUnselectColor` | `Color.Gray` | Color of unselected indicators. |
| `indicationSpacing` | `8` | Horizontal spacing between indicators, in dp. |
| `backgroundColor` | `null` | Screen background color. Falls back to `Color.White` when no background is set. |
| `backgroundBrush` | `null` | Screen background brush, such as a gradient. |
| `backgroundImage` | `null` | Drawable resource used as a full-screen background image. |

## OnboardPageConfig Properties

| Property | Default | Description |
| --- | --- | --- |
| `layoutOnboardContentId` | `null` | XML layout resource inflated into the onboarding content area. |
| `customView` | `null` | Existing Android `View` rendered in the onboarding content area. |
| `composableContent` | `null` | Compose content rendered in the onboarding content area. |
| `buttonUIConfig` | `ButtonUIConfig()` | Per-page next button and swipe UI configuration. |

`OnboardContent` checks page content in this order:

```text
layoutOnboardContentId > customView > composableContent
```

Set only one content source per page unless you intentionally want that priority behavior.

## XML Layout Page

```kotlin
val onboardPageConfig = OnboardPageConfig(
    layoutOnboardContentId = R.layout.layout_onboard_1,
    buttonUIConfig = ButtonUIConfig(
        buttonStyle = ButtonStyle.Normal,
        buttonTextColor = Color.White,
        buttonBgColor = Color.Blue,
    ),
)
```

Short constructor:

```kotlin
val onboardPageConfig = OnboardPageConfig(R.layout.layout_onboard_1)
```

## Custom View Page

```kotlin
val onboardPageConfig = OnboardPageConfig(
    customView = welcomeView,
    buttonUIConfig = ButtonUIConfig(
        buttonStyle = ButtonStyle.Outline,
        buttonTextColor = Color.Blue,
    ),
)
```

Short constructor:

```kotlin
val onboardPageConfig = OnboardPageConfig(welcomeView)
```

## Compose Page

```kotlin
val onboardPageConfig = OnboardPageConfig(
    composableContent = { OnboardScreenCompose() },
    buttonUIConfig = ButtonUIConfig(
        buttonStyle = ButtonStyle.Tick,
        tickColor = Color.Cyan,
        buttonTextColor = Color.White,
        buttonBgColor = Color.Blue,
    ),
)
```

Short constructor:

```kotlin
val onboardPageConfig = OnboardPageConfig {
    OnboardScreenCompose()
}
```

## Button and Indicator Behavior

The indicator is rendered at the bottom of the onboarding content area:

- The selected indicator is a rounded bar sized `24.dp x 5.dp`.
- Each unselected indicator is a `5.dp` circle.
- Indicator spacing uses `OnboardUiConfig.indicationSpacing.dp`.
- If `buttonStyle != ButtonStyle.FullSolid`, the next button is aligned to the center end of the
  indicator row.
- If `buttonStyle == ButtonStyle.FullSolid`, the next button is rendered as a separate full-width
  row below the indicator with horizontal `16.dp` padding and bottom `28.dp` padding.
- `buttonFullSolidSpacing` controls vertical spacing between the indicator row and the full-solid
  button.

```kotlin
val buttonUIConfig = ButtonUIConfig(
    tickColor = Color.Cyan,
    buttonTextColor = Color.White,
    buttonBgColor = Color.Blue,
    buttonBgBrush = Brush.horizontalGradient(
        listOf(Color.Blue, Color.Cyan)
    ),
    buttonStyle = ButtonStyle.FullSolid,
    buttonFullSolidSpacing = 24.dp,
    swipeUIConfig = SwipeUIConfig(
        size = DpSize(80.dp, 80.dp),
        parentHeigh = 200.dp,
        color = Color.Red,
        background = Color.Transparent,
    ),
)
```

Available button styles:

```kotlin
ButtonStyle.Normal
ButtonStyle.Outline
ButtonStyle.Tick
ButtonStyle.NormalSolid
ButtonStyle.FullSolid
```

## Builder Pattern

`OnboardUiConfig.Builder()` exposes setters for pages, indication colors, and background. Color
setters accept `Int`, `Long`, or Compose `Color`.

```kotlin
val onboardConfig = OnboardUiConfig.Builder()
    .setPages(listOf(onboard1Config, onboard2Config, onboard3Config))
    .setIndicationColor(Color.Magenta)
    .setIndicationUnselectColor(Color.Cyan)
    .setBackgroundColor(Color.White)
    .setBackgroundBrush(
        Brush.verticalGradient(listOf(Color.White, Color.Gray))
    )
    .setBackgroundImage(R.drawable.onboard_background)
    .build()
```

`indicationSpacing` is available on the `OnboardUiConfig` data class, but the current builder does
not expose a setter for it. Use the data class constructor when you need custom indicator spacing.

## Background Priority

`OnboardPage` applies the background in this order:

```text
backgroundImage > backgroundBrush > backgroundColor > Color.White
```

When `backgroundImage` is set, it is drawn full-screen with `ContentScale.FillBounds`.

## Ads Area Behavior

After the onboarding content and indicator area, `OnboardPage` renders the ad area:

- If `bannerAdGroup` is provided, the banner is shown at the bottom.
- Otherwise, if `nativeAdGroup` is provided and ads are enabled, network is connected, and the ad is
  not in failure state, the native ad is shown.
- If the native ad cannot be shown, the SDK falls back to `SwipeComponent(pageConfig.buttonUIConfig)`.
- Native ad background uses `OnboardUiConfig.backgroundColor` when available, otherwise transparent.
