plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "apero.aperosg.monetizationsample"
    compileSdk = 34

    defaultConfig {
        applicationId = "apero.aperosg.monetizationsample"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    flavorDimensions += "default"
    productFlavors {
        val testInterId = "\"ca-app-pub-3940256099942544/1033173712\""
        val testAppOpenId = "\"ca-app-pub-3940256099942544/9257395921\""
        val testNativeId = "\"ca-app-pub-3940256099942544/2247696110\""
        val testBannerId = "\"ca-app-pub-3940256099942544/6300978111\""
        val testRewardId = "\"ca-app-pub-3940256099942544/5224354917\""
        val testRewardInterId = "\"ca-app-pub-3940256099942544/5354046379\""
        val testCollapsibleBannerId = "\"ca-app-pub-3940256099942544/2014213617\""

        create("appDev") {
            manifestPlaceholders["ad_app_id"] = "ca-app-pub-3940256099942544~3347511713"
            buildConfigField("String", "inter_splash", testInterId)
            buildConfigField("String", "inter_splash_high", testInterId)
            buildConfigField("String", "banner_splash", testBannerId)

            buildConfigField("String", "native_language", testNativeId)
            buildConfigField("String", "native_language_high", testNativeId)
            buildConfigField("String", "native_language_dup", testNativeId)
            buildConfigField("String", "native_language_dup_high", testNativeId)

            buildConfigField("String", "native_welcome", testNativeId) // TODO: replace with real ad id
            buildConfigField("String", "native_welcome_high", testNativeId) // TODO: replace with real ad id
            buildConfigField("String", "native_welcome_dup", testNativeId) // TODO: replace with real ad id
            buildConfigField("String", "native_welcome_dup_high", testNativeId) // TODO: replace with real ad id

            buildConfigField("String", "native_onboard_1", testNativeId)
            buildConfigField("String", "native_onboard_1_high", testNativeId)
            buildConfigField("String", "native_onboard_3", testNativeId)
            buildConfigField("String", "native_onboard_3_high", testNativeId)
            buildConfigField("String", "native_onboard_4", testNativeId)
            buildConfigField("String", "native_onboard_4_high", testNativeId)
            buildConfigField("String", "native_ob_fullscr", testNativeId)
            buildConfigField("String", "native_ob_fullscr_high", testNativeId)

            buildConfigField("Boolean", "dev", "true")
        }

        create("appProduct") {
            manifestPlaceholders["ad_app_id"] = "ca-app-pub-3940256099942544~3347511713" // TODO: replace with real app id
            buildConfigField("String", "inter_splash", testInterId) // TODO: replace with real ad id
            buildConfigField("String", "inter_splash_high", testInterId) // TODO: replace with real ad id
            buildConfigField("String", "banner_splash", testBannerId) // TODO: replace with real ad id

            buildConfigField("String", "native_language", testNativeId) // TODO: replace with real ad id
            buildConfigField("String", "native_language_high", testNativeId) // TODO: replace with real ad id
            buildConfigField("String", "native_language_dup", testNativeId) // TODO: replace with real ad id
            buildConfigField("String", "native_language_dup_high", testNativeId) // TODO: replace with real ad id

            buildConfigField("String", "native_welcome", testNativeId) // TODO: replace with real ad id
            buildConfigField("String", "native_welcome_high", testNativeId) // TODO: replace with real ad id
            buildConfigField("String", "native_welcome_dup", testNativeId) // TODO: replace with real ad id
            buildConfigField("String", "native_welcome_dup_high", testNativeId) // TODO: replace with real ad id

            buildConfigField("String", "native_onboard_1", testNativeId) // TODO: replace with real ad id
            buildConfigField("String", "native_onboard_1_high", testNativeId) // TODO: replace with real ad id

            buildConfigField("String", "native_onboard_3", testNativeId) // TODO: replace with real ad id
            buildConfigField("String", "native_onboard_3_high", testNativeId) // TODO: replace with real ad id
            buildConfigField("String", "native_onboard_4", testNativeId)
            buildConfigField("String", "native_onboard_4_high", testNativeId)

            buildConfigField("String", "native_ob_fullscr", testNativeId) // TODO: replace with real ad id
            buildConfigField("String", "native_ob_fullscr_high", testNativeId) // TODO: replace with real ad id

            buildConfigField("Boolean", "dev", "false")
        }
    }
}

dependencies {
    implementation("apero.aperosg.firstopen:firstopen:1.0.1-alpha02")

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.activity:activity-ktx:1.9.1")

    implementation(libs.material)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.runtime)
    implementation(libs.compose.foundation)
    implementation(libs.compose.animation)
    implementation(libs.compose.material3)
    implementation(libs.compose.viewbinding)
    implementation(libs.compose.shimmer)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.constraintlayout.compose)
    debugImplementation(libs.compose.ui.tooling)

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}