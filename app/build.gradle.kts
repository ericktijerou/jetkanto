plugins {
    id(Plugins.androidApplication)
    kotlin(Plugins.kotlinAndroid)
    id(Plugins.daggerHilt)
    id(Plugins.apollo)
    kotlin(Plugins.kotlinKapt)
    id(Plugins.spotless)
    id(Plugins.kotlinParcelize)
}

android {
    compileSdkVersion(Configs.compileSdkVersion)
    buildToolsVersion(Configs.buildToolsVersion)

    defaultConfig {
        applicationId = Configs.applicationId
        minSdkVersion(Configs.minSdkVersion)
        targetSdkVersion(Configs.targetSdkVersion)
        versionCode = Configs.versionCode
        versionName = Configs.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        javaCompileOptions {
            annotationProcessorOptions {
                arguments.plusAssign(
                    hashMapOf(
                        "room.schemaLocation" to "$projectDir/schemas",
                        "room.incremental" to "true",
                        "room.expandProjection" to "true"
                    )
                )
            }
        }
    }

    buildTypes {
        getByName("debug") {
            buildConfigField(
                "String",
                "API_URL",
                "\"https://run.mocky.io/\""
            )
        }

        getByName("release") {
            isMinifyEnabled = true
            isDebuggable = false
            proguardFile(getDefaultProguardFile("proguard-android.txt"))
            proguardFile(file("proguard-rules.pro"))

            buildConfigField(
                "String",
                "API_URL",
                "\"https://run.mocky.io/\""
            )
        }
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }

    packagingOptions {
        resources.excludes.add("META-INF/*.kotlin_module")
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
        useIR = true
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
        getByName("test").java.srcDirs("src/test/kotlin")
        getByName("androidTest").java.srcDirs("src/androidTest/kotlin")
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
    }
}

apollo {
    generateKotlinModels.set(true)
}

spotless {
    kotlin {
        target("**/*.kt")
        targetExclude("$buildDir/**/*.kt")
        targetExclude("bin/**/*.kt")
        ktlint(Versions.ktlint)
        trimTrailingWhitespace()
        indentWithSpaces()
        endWithNewline()
        licenseHeaderFile(rootProject.file("spotless/copyright.kt"))
    }
    kotlinGradle {
        target("**/*.gradle.kts", "*.gradle.kts")
        ktlint(Versions.ktlint)
        trimTrailingWhitespace()
        indentWithSpaces()
        endWithNewline()
    }
}

dependencies {
    // Kotlin
    implementation(Libs.Kotlin.stdLib)

    // Android
    implementation(Libs.Android.coreKtx)
    implementation(Libs.Android.appcompat)
    implementation(Libs.Android.materialDesign)

    // Coroutines
    implementation(Libs.Coroutines.core)
    implementation(Libs.Coroutines.android)

    // Compose
    implementation(Libs.Compose.foundation)
    implementation(Libs.Compose.layout)
    implementation(Libs.Compose.ui)
    implementation(Libs.Compose.uiUtil)
    implementation(Libs.Compose.material)
    implementation(Libs.Compose.animation)
    implementation(Libs.Compose.iconsExtended)
    implementation(Libs.Compose.tooling)
    implementation(Libs.Activity.activityCompose)
    implementation(Libs.ConstraintLayout.constraintLayoutCompose)

    // Architecture Components
    implementation(Libs.Lifecycle.viewModelCompose)
    implementation(Libs.Lifecycle.liveData)
    implementation(Libs.Room.runtime)
    implementation(Libs.Room.ktx)
    implementation(Libs.Navigation.compose)
    kapt(Libs.Room.compiler)

    // Hilt + Dagger
    implementation(Libs.Dagger.hiltAndroid)
    implementation(Libs.Dagger.hiltNavigation)
    implementation(Libs.Dagger.hiltWork)
    kapt(Libs.Dagger.daggerCompiler)
    kapt(Libs.Dagger.hiltCompiler)

    // Retrofit
    implementation(Libs.Retrofit.retrofit)
    implementation(Libs.Retrofit.retrofitGsonConverter)
    implementation(Libs.Retrofit.retrofitScalarsConverter)
    implementation(Libs.Retrofit.retrofitKotlixConverter)

    // OkHttp
    implementation(Libs.OkHttp.okHttpInterceptor)

    // Accompanist
    implementation(Libs.Accompanist.coil)
    implementation(Libs.Accompanist.insets)

    // Paging
    implementation(Libs.Paging.runtime)
    implementation(Libs.Paging.compose)

    implementation(Libs.DataStore.preferences)
    implementation(Libs.WorkManager.runtime)
    implementation(Libs.ExoPlayer.core)
}
