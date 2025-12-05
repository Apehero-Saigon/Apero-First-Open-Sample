Monetization Sample
==================

**Advertisements made easy**

This is a repository for demonstrating how to use Apero Monetization Library.
Apero Monetization Library makes it easy to implement multi-floors ads, to load/reload and show ads.

## Requirements (Skip this if already done)

### Add Kotlin to project: [Android Documentation](https://developer.android.com/kotlin/add-kotlin#add)

# Table of Contents

1. [Ads structure](#ads-structure)
2. [Declare ads](#declare-ads)
2. [Load and show ads](#load-and-show-ads)
2. [Turn on/off ads with config](#turn-onoff-ads-with-config)
3. [Debug with ads log](#debug-with-ads-log)
4. [Advanced topics](#advance-topics)

## Ads structure

Ads inside library are packaged into **AdObject** and **AdGroup**

- An **AdObject** (which is tied to an ad id) is a part of an **AdGroup**
- An **AdGroup** is a set of multiple **AdObject** with different eCPM floors, sorted from high floor &rarr; medium floor &rarr; all price. When
  loading and showing ads, **AdGroup** will prioritize higher floors first.

All ads are written so that it can handle if developers request ads **many times without showing them**.
This means if previous ads hasn't been shown, no requests for new ads will be made.

## Declare ads

All ads objects inside the app are stored in a singleton class which lives as long as application lives.
This helps avoid ads objects being cleared if it hasn't been shown.

### Create a Kotlin singleton object in your project, name it ``AdsProvider``

### Start declaring your ads inside ``AdsProvider``

Every **AdGroup** has two common parameters: ``pairs of ads id and its name`` and ``the name of AdGroup``.
The number of ``pairs of ads id and its name`` is the number of floors, ordered by priority.
The ``name of AdGroup`` is useful for debugging.
For example:

```kotlin
object AdsProvider {
    ...
    val nativeLanguage = NativeAdGroup(
        BuildConfig.native_language_high to "native_language_high",
        BuildConfig.native_language_medium to "native_language_medium",
        BuildConfig.native_language to "native_language",
        name = "native_language_group",
    )
    ...
}
```

In the example above, we have a native ad in Language screen which has 3 floors.

### Details for each ad format:

- Banner
    ```kotlin
    val bannerAd = BannerAdGroup(
        BuildConfig.banner to "banner",
        // More floors if needed
        name = "banner_group",
        onImpression = {adId, adName ->}, // Callback after ad is shown
        isCollapsible = false, // true if banner is collapsible, default is false, can delete this line if it's false
    )
    ```
- Native
    ```kotlin
    val nativeAd = NativeAdGroup(
        BuildConfig.native_ad to "native_ad",
        // More floors if needed
        name = "native_ad_group",
        onImpression = {}, // Additional callback when ad is shown if needed, delete this line not used
        onAdClick = {}, // Additional callback when ad is clicked if needed, delete this line not used
        isFullscreen = false, // true if ad is fullscreen native, default is false, can delete this line if it's false
        reloadAfterVideo = true // true if you want to reload and show ad after ad shown video completely, default is true, can delete this line if it's true
    )
    ```
- Interstitial
    ```kotlin
    val interstitialAd = InterstitialAdGroup(
        BuildConfig.inter_ad to "inter_ad",
        // More floors if needed
        name = "inter_ad_group",
    )
    ```
- Reward video
    ```kotlin
    val rewardVideoAd = RewardAdGroup(
        BuildConfig.reward_video_ad to "reward_video_ad",
        // More floors if needed
        name = "reward_video_group",
        rewardType = RewardAdUnit.RewardAdType.RewardVideo,
    )
    ```
- Reward interstitial
    ```kotlin
    val rewardVideoAd = RewardAdGroup(
        BuildConfig.reward_inter_ad to "reward_inter_ad",
        // More floors if needed
        name = "reward_inter_group",
        rewardType = RewardAdUnit.RewardAdType.RewardInterstitial,
    )
    ```
- App open
    ```kotlin
    val appOpenAd = AppOpenAdGroup(
        BuildConfig.app_open_ad to "app_open_ad",
        // More floors if needed
        name = "app_open_ad"
    )
    ```

## Load and show ads

1. [Implementation in Kotlin](./docs/KotlinImplementation.md)
2. [Implementation in Java](./docs/JavaImplementation.md)

## Turn on/off ads with config

- Every **AdGroup** have a common function ``.config()``, it receives a number of boolean parameters equals to the number of floors of that **AdGroup
  **.
- If an **AdUnit** floor is turned off, any calls to ``loadAds()`` and ``showAds()`` will be ignored.
- An **AdGroup** is disabled if all its **AdUnit** are disabled.
- Number of parameters matters, the number of parameters must match the number of floors. Otherwise the app will crash.
- Order matters, the order must be the same as when we declare the ads.
- Default all floors are enabled.

For example:

```kotlin
class AdsProvider {
    val nativeLanguage = NativeAdGroup(
        BuildConfig.native_language_high to "native_language_high",
        BuildConfig.native_language_medium to "native_language_medium",
        BuildConfig.native_language to "native_language",
        name = "native_language_group",
    )
}

AdsProvider.nativeLanguage.config(false, true, false)
```

The above code will do the following things: turn off ``native_language_high``, turn on ``native_language_medium``, turn off ``native_language``.

## Debug with ads log

Every **AdGroup** has built-in ads logging to help with debugging. These logs are printed out:

```text
// Print when there's a request to load ad unit
loadAd: *ad_name* *ad_id*
// Print when the request to load ad unit is ignored because it's disabled
loadAd: *ad_name* *ad_id* is disabled
// Print when ad starts loading
loadAd: *ad_name* *ad_id* loading
// Print when ad is requested to load but previous ad hasn't been shown
loadAd: *ad_name* *ad_id* doesn't need to be loaded
// Print when request is canceled
loadAd: *ad_name* *ad_id* canceled: *message*

// Print when ad failed to load
onAdFailedToLoad: *ad_name* *ad_id* *message*
// Print when ad is loaded
onAdLoaded: *ad_name* *ad_id*
// Print on ad impression
onAdImpresison: *ad_name* *ad_id*
// Print when ad is clicked
onAdClicked: *ad_name* *ad_id*
// Print when ad failed to show (for inter, reward and app open only)
onAdFailedToShow: *ad_name* *ad_id*
// Print when ad showed (for inter, reward and app open only)
onAdShown: *ad_name* *ad_id*
```

## Advance topics

1. [Duplicate ads](./docs/AdvancedDuplicate.md)
2. [Increase impression](./docs/AdvanceIncreaseImpression.md)