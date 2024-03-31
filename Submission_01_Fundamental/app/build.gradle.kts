plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    namespace = "com.dicoding.submission_01_fundamental"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dicoding.submission_01_fundamental"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            buildConfigField ("String", "TOKEN", "\"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\"")
            buildConfigField("String", "BASE_URL", "\"https://api.github.com/\"")
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "TOKEN", "\"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\"")
            buildConfigField("String", "BASE_URL", "\"https://api.github.com/\"")
        }

    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.lifeCycleLiveData)
    implementation(libs.lifeCycleViewModel)
    implementation(libs.activityKtx)
    implementation(libs.fragmentKtx)
    implementation(libs.glide)
    implementation(libs.retrofit)
    implementation(libs.gson)
    implementation(libs.roomRuntime)
    implementation(libs.loggingInterceptor)
    kapt(libs.roomCompiler)
    implementation(libs.room)
    implementation(libs.dsPref)
    implementation(libs.coroutinesCore)
    implementation(libs.coroutinesAndro)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}