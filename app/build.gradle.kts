plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    namespace = "com.firstdueapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.firstdueapplication"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.firstdueapplication.CustomRunner"
        //testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
        // freeCompilerArgs = ['-Xjvm-default=compatibility']
    }
    buildFeatures {
        viewBinding = true
    }

    /* kotlin {
         jvmToolchain(1_8)
     }*/
}

dependencies {

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")

    testImplementation("junit:junit:4.13.2")

    testImplementation("androidx.test:runner:1.5.2")

    //InstrumentationTest [Non-UI Tests - depends on Context, Asset Manager etc.]
    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("androidx.test:core:1.5.0")
    androidTestImplementation("androidx.test:core-ktx:1.5.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.ext:junit-ktx:1.1.5")

    // espresso test
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test.espresso:espresso-intents:3.5.1")

    // Optional -- Mockito framework
    testImplementation("org.mockito:mockito-core:4.0.0")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.9.0")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")

    // coroutines test
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")

    //arc
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    androidTestImplementation("androidx.arch.core:core-testing:2.2.0")

    //hilt testing
    kaptTest("com.google.dagger:hilt-android-compiler:2.44")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.44")
    testImplementation("com.google.dagger:hilt-android-testing:2.44")
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.44")

    //InstrumentationTest [UI Tests - Ui Interaction]
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test.espresso:espresso-intents:3.5.1")


    // Navigation
    val navigation_version = "2.7.4"
    implementation("androidx.navigation:navigation-fragment-ktx:$navigation_version")
    implementation("androidx.navigation:navigation-ui-ktx:$navigation_version")

    // coroutines
    val corotine_version = "1.7.3"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$corotine_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$corotine_version")

    // room database
    val room_version = "2.5.2"
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    kapt("androidx.room:room-compiler:$room_version")

    // hilt depedency injection
    val hilt_version = "2.44"
    implementation("com.google.dagger:hilt-android:$hilt_version")
    kapt("com.google.dagger:hilt-android-compiler:$hilt_version")

    // life-cycle
    val lifecycle_version = "2.6.2"
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")

    // retrofit
    val retrofit_version = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit_version")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")

    val glide_version = "4.10.0"
    implementation("com.github.bumptech.glide:glide:$glide_version")
    kapt("com.github.bumptech.glide:compiler:$glide_version")
}

