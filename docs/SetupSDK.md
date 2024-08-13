Set up Apero SDK
==================

# Import Module
Put this inside your project level ``settings.gradle``:
~~~
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
~~~

Typically your project level ``settings.gradle`` will look like this:
~~~
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
~~~

# Setup AperoAd
## Setup enviroment with id ads for project

We recommend you to setup 2 environments for your project, and only use test id during development, ids from your admob only use when needed and for publishing to Google Store
* The name must be the same as the name of the marketing request
* Config variant test and release in gradle
* appDev: using id admob test while dev
* appProd: use ids from your admob,  build release (build file .aab)


App module ``build.gradle``:
~~~
productFlavors {
appDev {
      manifestPlaceholders = [ ad_app_id:"AD_APP_ID_TEST" ]
      buildConfigField "String", "ads_inter_turn_on", "\"AD_ID_INTERSTIAL_TEST\""
      buildConfigField "String", "ads_inter_turn_off", "\"AD_ID_INTERSTIAL_TEST\""
      buildConfigField "Boolean", "build_debug", "true"
   }
appProd {
    // ADS CONFIG BEGIN (required)
       manifestPlaceholders = [ ad_app_id:"AD_APP_ID" ]
       buildConfigField "String", "ads_inter_splash", "\"AD_ID_INTERSTIAL\""
       buildConfigField "String", "ads_inter_turn_on", "\"AD_ID_INTERSTIAL\""
       buildConfigField "Boolean", "build_debug", "false"
    // ADS CONFIG END (required)
   }
}
~~~

AndroidManifest.xml
~~~
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
~~~

## Config ads
Create class Application

Configure your mediation here. using PROVIDER_ADMOB or PROVIDER_MAX

*** Note:
- Don't use id ad test for production environment
- Environment:
    - ENVIRONMENT_DEVELOP: for test ads and billing.
    - ENVIRONMENT_PRODUCTION: for prdouctions ads and billing.
~~~
class App : AdsMultiDexApplication(){
    @Override
    public void onCreate() {
        super.onCreate()
        ...
        String environment = BuildConfig.build_debug ? AperoAdConfig.ENVIRONMENT_DEVELOP : AperoAdConfig.ENVIRONMENT_PRODUCTION
	    aperoAdConfig = new AperoAdConfig(this, API_KEY, AperoAdConfig.PROVIDER_ADMOB, environment)

        // Optional: setup Adjust event
        AdjustConfig adjustConfig = new AdjustConfig(true,ADJUST_TOKEN)
        adjustConfig.setEventAdImpression(EVENT_AD_IMPRESSION_ADJUST)
        adjustConfig.setEventNamePurchase(EVENT_PURCHASE_ADJUST)
        aperoAdConfig.setAdjustConfig(adjustConfig)

        // Optional: setup Appsflyer event
        AppsflyerConfig appsflyerConfig = new AppsflyerConfig(true,APPSFLYER_TOKEN)
        aperoAdConfig.setAppsflyerConfig(appsflyerConfig)

        // Optional: enable ads resume
        aperoAdConfig.setIdAdResume(BuildConfig.ads_open_app)

        // Optional: setup list device test - recommended to use
        listTestDevice.add(DEVICE_ID_TEST)
        aperoAdConfig.setListDeviceTest(listTestDevice)
        
        Admob.getInstance().setFan(false)
        Admob.getInstance().setAppLovin(false)
        Admob.getInstance().setColony(false)
        Admob.getInstance().setOpenActivityAfterShowInterAds(true)
        Admob.getInstance().setDisableAdResumeWhenClickAds(true)

        AperoAd.getInstance().init(this, aperoAdConfig, false)
    }
}
~~~