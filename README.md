Apero First Open Library Sample
==================

This is a repository for demonstrating how to use Apero First Open Library.

Apero First Open Library takes care of showing Splash Ads and first open flow 
(Splash -> Language -> Language dup -> Onboarding -> Welcome -> Welcome dup -> Finish)

## Requirements (Skip this if already done)

### Set up Apero Ads Module: Follow the setup steps in [Setup Guide](docs/SetupSDK.md)

To run this sample project, open ``settings.gradle.kts`` in the root of project and provide
given ``username`` and ``password`` inside ``credentials``
block.

```kotlin
maven {
    url = uri("https://artifactory.apero.vn/artifactory/gradle-release/")
    credentials {
        username = "" // Username here
        password = "" // Password here
    }
}
```

### Add library to app module

Inside app module's build.gradle, add implementation for library:

```
implementation("apero.aperosg.firstopen:firstopen:1.0.8")
```

# Table of Contents

1. [Structure](#1-structure)
2. [Configure Splash screen](#2-configure-splash-screen)
3. [Configure Language First Open screen](#3-configure-language-first-open-screen)
4. [Configure Welcome screen (Optional)](#4-configure-welcome-screen-optional)
5. [Configure Onboard screens](#5-configure-onboard-screens)
6. [Customize Ads](#6-customize-ads)
7. [Configure Ads](#7-configure-ads)

# [**1. Structure**](#1-structure)

1. Create a FirstOpenActivity and make it launcher activity in Manifest
   ```xml
    <activity
        android:name=".FirstOpenActivity"
        android:configChanges="locale|layoutDirection|orientation|screenLayout|uiMode|touchscreen|screenSize|smallestScreenSize"
        android:exported="true">
        <intent-filter>
            <action android:name="android.intent.action.MAIN" />
            <category android:name="android.intent.category.LAUNCHER" />
        </intent-filter>
    </activity>
   ```

   **Important: do not forget ``configChanges`` line, Activity on some devices is restarted after
   changing language and causing bugs.**

2. Inside FirstOpenActivity, start setting up flow and launch.

   Details: [Source file](app/src/main/java/apero/aperosg/monetizationsample/FirstOpenActivity.kt)

    ```kotlin
    class FirstOpenActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
    
            setupFirstOpenFlow()
            finish()
        }
    
        private fun setupFirstOpenFlow() {
            // Set up callback
            val callback = object : AperoFOCallback() {
                override fun onConsentResult(canLoadAds: Boolean) {
                    // Do something if user consent/doesn't consent
                }
    
                override fun onLanguageConfirm(language: Language) {
                    // Do something when user confirm language
                    // Such as save language to Preferences...
                }
   
                 override fun onOnboardPageChanged(currentPage: Int, totalPage: Int) {
                       // Do something when onboard page change
                       // Such as preload ad 
                   }    
    
                override fun onFinished() {
                    // Go to next screen
                }
            }
    
            // Set up ads config
            val adsConfig = AperoFOAdsConfig.Builder()
                // Details ads config here
                .build()
    
            // Set up Splash screen config
            val splashConfig = AperoSplashUiConfig.Builder()
                // Details splash screen config here
                .build()
    
            // Set up Language FO screen config
            val languageConfig = AperoLanguageUiConfig.Builder()
                // Details language config here
                .build()
    
            // Set up Welcome screen config
            // Skip if there's no welcome screen
            val welcomeConfig = AperoWelcomeUiConfig.Builder()
                // Details welcome config here
                .build()
    
            // Set up Onboard screens config
            // Config for onboard screen 1
            val onboard1Config = AperoOnboardPageConfig(layoutOnboardContentId = R.layout.layout_onboard_1)
            // Config for onboard screen 2
            val onboard2Config = AperoOnboardPageConfig(layoutOnboardContentId = R.layout.layout_onboard_2)
            // Config for onboard screen 3
            val onboard3Config = AperoOnboardPageConfig(layoutOnboardContentId = R.layout.layout_onboard_3)
            // Combine config for onboard screens
            val onboardConfig = AperoOnboardUiConfig(pagesConfig = listOf(onboard1Config, onboard2Config, onboard3Config))
    
            // Assemble configs
            val config = AperoFOConfig.Builder()
                .setCallback(callback)
                .setAdsConfig(adsConfig)
                .setSplashUiConfig(splashConfig)
                .setLanguageUiConfig(languageConfig)
                .setWelcomeUiConfig(welcomeConfig)
                .setOnboardUiConfig(onboardConfig)
                .build()
    
            // Start first open flow
            AperoFO.startFlow(this, config)
        }
    }
    ```

# [**2. Configure Splash screen**](#2-configure-splash-screen)

Full config options: [Documentation](docs/SplashConfigOptions.md)

This step setups UI for splash screen, you can choose to use prebuilt Splash screen or provide your
own layout

- Use prebuilt splash screen:
   ```kotlin
   val splashConfig = AperoSplashUiConfig.Builder()
   .setAppIconId(R.drawable.app_icon) // Provide app icon
   .build()
   ```
- Use custom layout:
   ```kotlin
  val splashConfig = AperoSplashUiConfig.Builder()
  .setCustomSplashLayoutId(R.layout.layout_splash) // Provide custom layout
  .build()
   ```
- Use Jetpack Compose:
   ```kotlin
  val splashConfig = AperoSplashUiConfig.Builder()
  .setComposableContent {
      SplashScreen() // Composable function
  }
  .build()
   ```
 

## Additional initialization in Splash (Optional if you have other initializations)

If you have any other initializations that needs to be done in Splash screen such as remote configs,
follow these instructions:

- Call ``.setWaitForInitialization(true)`` in SplashUiConfig Builder
- Call ``AperoFO.startFlow()`` to start follow as usual
- Start your initializations and call ``AperoFO.finishSplashInitialization()`` when you're done

Sample
file: [Source file](app/src/main/java/apero/aperosg/monetizationsample/FirstOpenWithSplashInitializationActivity.kt)

# [**3. Configure Language First Open screen**](#3-configure-language-first-open-screen)

This step setups languages in Language screen, you provides list of languages to show in Language FO
screen.

Configure the `AperoLanguageUiConfig` to use your custom your UI.

| Parameter                    | Description                                                                   |
|------------------------------|-------------------------------------------------------------------------------|
| languages                    | Sets the list of languages to be displayed in the Language First Open screen. |
| titleColor                   | Sets the text color for the "Select language" title.                          |
| primaryColor                 | Sets the primary color of the elements on screen.                             |
| nextButtonStyle              | Sets the style for the next button, such as solid, outline, tick or normal.   |
| customLanguageLayoutId       | Sets a custom layout resource ID for language elements in normal status.      |
| customLanguageChosenLayoutId | Sets a custom layout resource ID for language elements when item is selected. |
| backgroundColor              | Sets the screen background using a color.                                     |
| backgroundBrush              | Sets the screen background using a brush.                                     |
| backgroundImage              | Sets the screen background using a drawable resource.                         |
| itemPaddingDp                | Sets a padding between items if using jetpack compose                         |
| customLanguageItemCompose    | Sets a custom language selector with jetpack compose                          |

`Note: If you use all: backgroundImage, backgroundBrush, backgroundColor. Thay will be applied in the following order: backgroundImage > backgroundBrush > backgroundColor`

Full config options: [Documentation](docs/LanguageConfigOptions.md)

```kotlin
val languageConfig = AperoLanguageUiConfig.Builder()
    .setLanguages(
        listOf(
            Language.English,
            Language.German,
            // Other languages
        )
    )
    //.setPrimaryColor(Color.BLUE) // Set Primary color of the screen
    //.setNextButtonStyle(ButtonStyle.Solid) // Set next button style
    .build()
```

List of supported languages:
``Arabic(ar), Bangla(bn), Chinese(zh), Czech(cz), Danish(da),
Dutch(nl), English(en), Finnish(fi), Filipino(fil), French(fr),
German(de), Hindi(hi), Indonesian(in), Italian(it), Japanese(ja),
Korean(ko), Malay(ms), Marathi(mr), Portuguese(pt), Russian(ru),
Spanish(es), Tamil(ta), Telugu(te), Thai(th), Turkish(tr),
Urdu(ur), Vietnamese(vi)``

### Language settings

The library also provides a **Language Settings** screen if user finishes first open flow.
To launch Language Settings screen:

```kotlin
startActivity(Intent(context, LanguageSettingsActivity::class.java))
```

You can also use your custom **Language Settings** screen but
call ``AperoFO.setLanguage(languageCode)`` if you change language.

### Customize Language Selector with XML

If you don't want to use default layout for language xml below:
<p align="center">
<img src="./photo/photo_1.png" height="400" />
</p>

And you want to use your customized language element xml like this
<p align="center">
    <img src="./photo/photo_2.png"  height="400" />
</p>
To implement a custom layout for the language element in the Language First Open screen, follow
these steps:

1. **Create Your Custom Layout**: First, design your custom layout XML file for the language
   element. For instance, create two files named `layout_language_element.xml` &
   `layout_language_element_chosen.xml` under `res/layout` directory. Their content will look like
   the following code snippet:

   ```xml
   <!-- res/layout/custom_language_item.xml -->
    <?xml version="1.0" encoding="utf-8"?>
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent" android:layout_height="wrap_content"
        android:orientation="horizontal" android:background="@color/blue_500" android:padding="16dp">
    
        <!--tag languageFlag is required in your customized layout-->
        <ImageView android:tag="languageFlag" android:layout_width="40dp" android:layout_height="24dp"
            android:layout_marginEnd="8dp" android:contentDescription="Flag"
            android:src="@drawable/app_icon" />
    
        <!--tag languageName is required in your customized layout-->
        <TextView android:tag="languageName" android:layout_width="wrap_content"
            android:layout_height="wrap_content" android:text="Phong-Kaster" android:textSize="16sp"
            android:textColor="@android:color/black" />
    </LinearLayout>
   ```

Note: Ensure that you use the exact [***android:tag=languageFlag***](#) & [
***android:tag=languageName***](#) in the custom layout to enable correct property mapping by the
library.

2. **Set the Custom Layout in the Configuration**: Configure the `AperoLanguageUiConfig` to use your
   custom layout by specifying the layout resource ID.

   ```kotlin
   val languageConfig = AperoLanguageUiConfig.Builder()
       .setLanguages(
           listOf(
               Language.English,
               Language.German,
               // Add additional languages as needed
           )
       )
   
       // set custom image background instead of default background color
        .setCustomImageBackground(R.drawable.img_language_background)
   
       // set custom language layout with layout_language_element.xml 
       .setCustomLanguageLayoutId(R.layout.layout_language_element)
   
       // set custom language layout when user select language layout_language_element_chosen.xml
       .setCustomChosenLanguageLayoutId(R.layout.layout_language_element_chosen) 
       .build()
   ```

By following these steps, you can implement a custom layout for the language element in the Language
First Open screen, giving you greater control over the visual appearance and functionality.

### Customize Language Selector with Jetpack Compose

1. **Create Language Item with Jetpack Compose**: Create a Jetpack Compose composable function with
   3
   params:

| Parameter            | Description                                 |
|----------------------|---------------------------------------------|
| **Language**         | The language item                           |
| **Boolean**          | Whether this language is currently selected |
| **onSelectLanguage** | Callback function triggered on selection    |

2. **Set the Custom Layout in the Configuration**: Configure the `AperoLanguageUiConfig` to use your
   compose

```kotlin
 val languageConfig = AperoLanguageUiConfig.Builder()
    .setCustomLanguageItemCompose { language, selected, onSelectLanguage ->
        LanguageItem(
            modifier = Modifier.fillMaxWidth(),
            language = Language.entries.find { language.code == it.code }
                ?: Language.English,
            selected = selected,
            onClick = onSelectLanguage,
        )
    }
    .build()

```

### Configure the First Open Flow:

After customizing the Language Selector using Jetpack Compose or XML.
You need to ensure your configuration is correctly set up within your
`FirstOpenActivity`.

   ```kotlin
   private fun setupFirstOpenFlow() {
    val config = AperoFOConfig.Builder()
        .setLanguageUiConfig(languageConfig)
        .build()

    AperoFO.startFlow(this, config)
}
   ```

# [**4. Configure Welcome screen (Optional)**](#4-configure-welcome-screen-optional)

This step setups Welcome screen (screen between Language FO and Onboard).

- Upon an event that triggers dup screen, call ``AperoFO.showWelcomeDupScreen()``
- Upon an event that complete welcome screen, call ``AperoFO.completeWelcomeScreen()``

Full config options: [Documentation](docs/WelcomeConfigOptions.md)

Configure the `AperoWelcomeUiConfig` to use your custom your UI.

| Parameter               | Description                                           |
|-------------------------|-------------------------------------------------------|
| **primaryColor**        | Sets the primary color of the elements on screen.     |
| **viewContentProvider** | Sets a custom layout with xml resource ID             |
| **composableContent**   | Sets a custom screen with jetpack compose             |
| **backgroundColor**     | Sets the screen background using a color.             |
| **backgroundBrush**     | Sets the screen background using a brush.             |
| **backgroundImage**     | Sets the screen background using a drawable resource. |

`Note: If you use all: backgroundImage, backgroundBrush, backgroundColor. Thay will be applied in the following order: backgroundImage > backgroundBrush > backgroundColor`

### Using XML:

Refer to
file [Source file](app/src/main/java/apero/aperosg/monetizationsample/FirstOpenWelcomeXMLActivity.kt)

To use an image as background like the following photo:

<p align="center">
<img src="./photo/photo_3.png" height="400" />
</p>

Otherwise, welcome screen has its content as the following layout below:

<p align="center">
<img src="./photo/photo_4.png" height="400" />
</p>

```kotlin
private fun setupFirstOpenFlow() {
    //...
    val welcomeConfig = AperoWelcomeUiConfig.Builder()
        .setViewContentProvider { setUpWelcomeScreen() }
        .build()
    //...
}

private fun setUpWelcomeScreen(): View {
    val welcomeScreenView = layoutInflater.inflate(R.layout.layout_welcome_scr, null, false)
    // Setup your welcome screen layout here
    return welcomeScreenView
}
```

### Using Jetpack Compose:

Refer to
file [Source file](app/src/main/java/apero/aperosg/monetizationsample/FirstOpenWelcomeComposeActivity.kt)

```kotlin
private fun setupFirstOpenFlow() {
    //...
    val welcomeConfig = AperoWelcomeUiConfig.Builder()
        .setComposableContent { WelcomeScreenContent() }
        .build()
    //...
}

@Composable
private fun WelcomeScreenContent() {
    // Set up your welcome screen layout here
}
```

# [**5. Configure Onboard screens**](#5-configure-onboard-screens)

Full config options: [Documentation](docs/OnboardConfigOptions.md)

<details>
    <summary>Onboard screens with two native onboard fullscreen</summary>

### Step 1: Firebase Configuration for Onboard Screens

The onboarding are following with flow:

1. Start
2. Splash
3. Language First Open
4. Language First Open dup
5. Onboard 1 (with native ad)
6. Native Onboard Fullscreen 1
7. Onboard 2 (with banner ad)
8. Native Onboard Fullscreen 2
9. Onboard 3 (with banner ad)
10. Welcome
11. Welcome dup
12. Finish

On the section [configure ad](#configure-ads), take a notice when set
up `native_onboard_1_fullscreen` & `native_onboard_2_fullscreen`

### Step 2: Create Onboarding Page Configurations

Start by defining three **AperoOnboardPageConfig** objects, one for each onboarding screen. Ensure
these configurations are created in the order you
want them to appear: 1, 2, 3.

```kotlin
val onboard1Config = AperoOnboardPageConfig()
val onboard2Config = AperoOnboardPageConfig()
val onboard3Config = AperoOnboardPageConfig()
```

### Step 3: Add UI Content to Each Onboarding Page

To customize the UI for each onboarding screen, you can assign a layout resource ID or a Composable
function to the corresponding *
*AperoOnboardPageConfig** object.

### Using XML Layouts

If your onboarding screens are defined using XML layouts, simply assign the layout resource ID to
each  **AperoOnboardPageConfig** object.

```kotlin
val onboard1Config = AperoOnboardPageConfig(layoutOnboardContentId = R.layout.layout_onboard_1)
val onboard2Config = AperoOnboardPageConfig(layoutOnboardContentId = R.layout.layout_onboard_2)
val onboard3Config = AperoOnboardPageConfig(layoutOnboardContentId = R.layout.layout_onboard_3)
```

### Using Jetpack Compose

If you prefer to define your onboarding screens with Jetpack Compose, you can pass a Composable
function directly to each **AperoOnboardPageConfig**
object:

```kotlin
val onboard1Config = AperoOnboardPageConfig(composableContent = { OnboardScreen1Compose() })
val onboard2Config = AperoOnboardPageConfig(composableContent = { OnboardScreen2Compose() })
val onboard3Config = AperoOnboardPageConfig(composableContent = { OnboardScreen3Compose() })
```

### Step 4: Configure the Onboarding UI

Create an AperoOnboardUiConfig object to customize the appearance of the onboarding screens. Pass
the previously created **AperoOnboardPageConfig**
objects into this configuration.

| Parameter           | Description                                                                             |
|---------------------|-----------------------------------------------------------------------------------------|
| **primaryColor**    | Color of the elements on onboarding screen such as indicators, next button, ads buttons |
| **pages**           | A list of `AperoOnboardPageConfig`. It should contain 3 items.                          |
| **backgroundColor** | Sets the screen background using a color.                                               |
| **backgroundBrush** | Sets the screen background using a brush.                                               |
| **backgroundImage** | Sets the screen background using a drawable resource.                                   |

`Note: If you use all: backgroundImage, backgroundBrush, backgroundColor. Thay will be applied in the following order: backgroundImage > backgroundBrush > backgroundColor`

```kotlin
val onboardConfig = AperoOnboardUiConfig(
    primaryColor = yourPrimaryColor,
    backgroundColor = yourBackgroundColor,
    pagesConfig = listOf(onboard1Config, onboard2Config, onboard3Config)
)
```

</details>

To use linear gradient in Onboard serial screens, we provide background gradient property. Remember,
background gradient requires at least two colours to work properly.

   ```kotlin
   val onboardConfig = AperoOnboardUiConfig(
    startButtonStyle = ButtonStyle.Outline,
    pages = listOf(onboard1Config, onboard2Config, onboard3Config, onboard4Config),
    nextButtonStyle = ButtonStyle.Normal,
    backgroundColor = 0xFF0F0F27,
    backgroundGradient = listOf(
        // at lease 2 colours. Otherwise, throw exceptions
        0xFF0F0F27,
        0xFF0F0F26,
        0xFF0F1129,
        0xFF1A244B,
    ),
    primaryColor = 0xFF27B8CD,
)
   ```

`Note: If both backgroundColor & backgroundGradient are filled with colours. Background gradient will be used instead of background color`

# [**6. Customize Ads**](#6-customize-ads)

### Native ads customization

You can customize Native ad elements using AperoNativeAdUiConfig() with:

| Function            | Description                                            |
|---------------------|--------------------------------------------------------|
| **adTagColor**      | Sets the background color of all ad tag in native ads  |
| **backgroundAd**    | Sets the background color of all standard native ads   |
| **headlineColor**   | Sets the headline text color of all native ads         |
| **bodyColor**       | Sets the body text color of all native ads             |
| **buttonColor**     | Sets the background color of all call-to-action button |
| **buttonTextColor** | Sets the text color of the call-to-action button       |
| **adFullScrColor**  | Sets the background color of full-screen native ads    |
| **adFullScrBrush**  | Sets the brush background of full-screen native ads    |

For example:

```kotlin
val config = AperoFOConfig.Builder()
    // Other configs
    .setAdUiConfig(
        AperoNativeAdUiConfig(
            adTagColor = Color.Blue,
            backgroundAd = Color(0xFFEAF1FF),
            headlineColor = Color(0xFF333333),
            bodyColor = Color.Black,
            buttonTextColor = Color.White,
            buttonColor = Color.Red,
            adFullScrColor = Color.White,
            adFullScrBrush = Brush.verticalGradient(listOf(Color.Red, Color.Yellow)),
        )
    )
    .build()
```

`Note: If both adFullScrColor & adFullScrBrush are set. adFullScrBrush will be used instead of adFullScrColor`

### Set the gradient background for the Native ad's CTA.

You need to create `sdk_bg_cta_native.xml` in the res/drawable folder, and in
`AperoNativeAdUiConfig`,
set `buttonColor` to null.

For example: sdk_bg_cta_native.xml

```xml 
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <corners android:radius="14dp" />

    <gradient android:angle="0" android:endColor="#53F5FF" android:startColor="#835BFF" />
</shape>

```

```kotlin
val config = AperoFOConfig.Builder()
    // Other configs
    .setAdUiConfig(
        AperoNativeAdUiConfig(
            buttonColor = null,
        )
    )
    .build()
```

# [**7. Configure Ads**](#7-configure-ads)

First Open library takes care of showing splash ads and first open ads, to do that you have to
provide the ads id.

Full config options: [Documentation](docs/AdsConfigOptions.md)

Example:

```kotlin
// Set up ads config
val adsConfig = AperoFOAdsConfig.Builder()
    .setInterSplashHighId(BuildConfig.inter_splash_high)
    .setInterSplashId(BuildConfig.inter_splash)
    // More ads id
    .setNativeOnboardFullscreenId(BuildConfig.native_ob_fullscr) // set up native_onboard_1_fullscreen
    .setNativeOnboardFullscreenHighId(BuildConfig.native_ob_fullscr_high)
    .setNativeOnboardFullscreen2Id(BuildConfig.native_ob_fullscr_2) // set up native_onboard_2_fullscreen
    .setNativeOnboardFullscreen2HighId(BuildConfig.native_ob_fullscr_2_high)
    .build()
```

# [**8. Start flow**](#8-start-flow)

After configuring everything, it's time to assemble configs and start the flow

```kotlin
// Assemble configs
val config = AperoFOConfig.Builder()
    .setCallback(callback)
    .setAdsConfig(adsConfig)
    .setAdUiConfig(adUiConfig)
    .setSplashUiConfig(splashConfig)
    .setLanguageUiConfig(languageConfig)
    .setWelcomeUiConfig(welcomeConfig)
    .setOnboardUiConfig(onboardConfig)
    .build()

// Start first open flow
AperoSGFO.startFlow(this, config)
```

[//]: # ()

[//]: # (# [**7. Publish**]&#40;#7-publish&#41;)

[//]: # ()

[//]: # (To publish a new version of the library, you need to follow these steps:)

[//]: # ()

[//]: # (Open Android Studio -> File -> Setting -> Experimental -> Check "Configure all Gradle tasks during)

[//]: # (Gradle Sync")

[//]: # ()

[//]: # (Then, you can publish your library by choose Gradle icon &#40;Elephant icon&#41; -> AperoSG First Open ->)

[//]: # (firstopen -> publishing -> publish )