# Set Up Apero SDK

## Import Module

Put this inside your project-level `settings.gradle.kts`:

```kotlin
maven { url = uri("https://dl-maven-android.mintegral.com/repository/mbridge_android_sdk_oversea") }
maven { url = uri("https://android-sdk.is.com/") }
maven { url = uri("https://artifact.bytedance.com/repository/pangle") }
maven {
    url = uri("https://artifactory.apero.vn/artifactory/gradle-release/")
    credentials {
        username = "" // Contact us for account
        password = "" // Contact us for account
    }
}
```

Typically your project-level `settings.gradle.kts` looks like this:

```kotlin
pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://dl-maven-android.mintegral.com/repository/mbridge_android_sdk_oversea") }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://maven.google.com") }
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
        maven { url = uri("https://dl-maven-android.mintegral.com/repository/mbridge_android_sdk_oversea") }
        maven { url = uri("https://android-sdk.is.com/") }
        maven { url = uri("https://artifact.bytedance.com/repository/pangle") }
        maven {
            url = uri("https://artifactory.apero.vn/artifactory/gradle-release/")
            credentials {
                username = "" // Contact us for account
                password = "" // Contact us for account
            }
        }
    }
}

rootProject.name = "App Name"
include(":app")
```

## Set Up Astronex Monetization

### Set Up Environments and Ad IDs

We recommend two environments for your project. Use test ad IDs during development, and use real
AdMob IDs only when needed for release builds.

- The flavor name must match the marketing request.
- Configure both test and release variants in Gradle.
- `appDev`: uses AdMob test IDs during development.
- `appProd`: uses real AdMob IDs for release builds such as `.aab`.

App module `build.gradle`:

```groovy
productFlavors {
    appDev {
        manifestPlaceholders = [ad_app_id: "AD_APP_ID_TEST"]
        buildConfigField "String", "ads_inter_turn_on", "\"AD_ID_INTERSTITIAL_TEST\""
        buildConfigField "String", "ads_inter_turn_off", "\"AD_ID_INTERSTITIAL_TEST\""
        buildConfigField "Boolean", "build_debug", "true"
    }
    appProd {
        // ADS CONFIG BEGIN (required)
        manifestPlaceholders = [ad_app_id: "AD_APP_ID"]
        buildConfigField "String", "ads_inter_splash", "\"AD_ID_INTERSTITIAL\""
        buildConfigField "String", "ads_inter_turn_on", "\"AD_ID_INTERSTITIAL\""
        buildConfigField "Boolean", "build_debug", "false"
        // ADS CONFIG END (required)
    }
}
```

`AndroidManifest.xml`:

```xml
<meta-data
    android:name="com.google.android.gms.ads.APPLICATION_ID"
    android:value="${ad_app_id}" />
<meta-data
    android:name="com.facebook.sdk.ApplicationId"
    android:value="@string/facebook_app_id" />
<meta-data
    android:name="com.facebook.sdk.AutoInitEnabled"
    android:value="true" />
<meta-data
    android:name="com.facebook.sdk.AutoLogAppEventsEnabled"
    android:value="true" />
<meta-data
    android:name="com.facebook.sdk.AdvertiserIDCollectionEnabled"
    android:value="true" />
```

### Configure Monetization in Application

Create an `Application` class and initialize monetization in `onCreate()`.

```kotlin
class App : Application() {
    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        initAds()
    }

    private fun initAds() {
        AstronexMonetization.init(
            context = this,
            testDeviceIds = listOf(
                "List of test device ids",
            ),
            adjustConfig = AstronexAdjustConfig(
                adjustToken = "Adjust token here",
                adImpressionEvent = "Ad Impression Event Key here",
                isSandbox = BuildConfig.DEBUG,
            ),
            appsflyerConfig = AstronexAppsFlyerConfig(
                apiKey = "Appsflyer key here",
                isSandbox = BuildConfig.DEBUG,
            ),
        ) {
            // Called after monetization initialization completes.
            AppOpenResumeManager.setUpAppOpenResume(
                BuildConfig.open_resume,
                "open_resume",
                true,
                MainActivity::class,
                FirstOpenActivity::class,
            )
        }
    }
}
```

Register the `Application` class in `AndroidManifest.xml`:

```xml
<application
    android:name=".App"
    ...>
    ...
</application>
```

Notes:

- Use real Adjust and AppsFlyer keys for production builds.
- Keep `isSandbox = BuildConfig.DEBUG` so debug builds use sandbox analytics behavior.
- Replace `"List of test device ids"` with your real test device IDs during development.
- `setUpAppOpenResume(...)` receives the app-open ad ID, ad name, enable flag, target activity, and
  activity that should be excluded from app-open resume ads.
