object Libs {
    object Android {
        const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
        const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
        const val materialDesign = "com.google.android.material:material:${Versions.materialDesign}"
    }

    object Kotlin {
        const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    }

    object Dagger {
        const val daggerCompiler = "com.google.dagger:hilt-android-compiler:${Versions.daggerHilt}"
        const val hiltCompiler = "androidx.hilt:hilt-compiler:${Versions.daggerHiltAndroid}"
        const val hiltViewModel = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.daggerHiltAndroid}"
        const val hiltNavigation = "androidx.hilt:hilt-navigation-fragment:${Versions.daggerHiltAndroid}"
        const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.daggerHilt}"
    }

    object Activity {
        const val activityCompose = "androidx.activity:activity-compose:${Versions.composeActivity}"
    }

    object ConstraintLayout {
        const val constraintLayoutCompose = "androidx.constraintlayout:constraintlayout-compose:${Versions.composeConstraint}"
    }

    object Lifecycle {
        const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.composeViewModel}"
        const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    }

    object Room {
        const val compiler = "androidx.room:room-compiler:${Versions.room}"
        const val ktx = "androidx.room:room-ktx:${Versions.room}"
        const val runtime = "androidx.room:room-runtime:${Versions.room}"
    }

    object Paging {
        const val runtime = "androidx.paging:paging-runtime:${Versions.paging}"
        const val compose = "androidx.paging:paging-compose:${Versions.composePaging}"
    }

    object DataStore {
        const val preferences = "androidx.datastore:datastore-preferences:1.0.0-alpha08"
    }

    object Compose {
        const val runtime = "androidx.compose.runtime:runtime:${Versions.compose}"
        const val ui = "androidx.compose.ui:ui:${Versions.compose}"
        const val uiUtil = "androidx.compose.ui:ui-util:${Versions.compose}"
        const val tooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
        const val foundation = "androidx.compose.foundation:foundation:${Versions.compose}"
        const val layout = "androidx.compose.foundation:foundation-layout:${Versions.compose}"
        const val material = "androidx.compose.material:material:${Versions.compose}"
        const val icons = "androidx.compose.material:material-icons-core:${Versions.compose}"
        const val iconsExtended =
            "androidx.compose.material:material-icons-extended:${Versions.compose}"
        const val composeUiTest = "androidx.compose.ui:ui-test:${Versions.compose}"
        const val composeLifecycle = "androidx.compose.runtime:runtime-livedata:${Versions.compose}"
        const val animation = "androidx.compose.animation:animation:${Versions.compose}"
    }

    object Navigation {
        const val compose = "androidx.navigation:navigation-compose:${Versions.navigationCompose}"
    }

    object Retrofit {
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
        const val retrofitScalarsConverter =
            "com.squareup.retrofit2:converter-scalars:${Versions.retrofit}"
        const val retrofitKotlixConverter =
            "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.retrofitKotlinxVersion}"

    }

    object OkHttp {
        const val okHttpInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    }

    object Coroutines {
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        const val android =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    }

    object Accompanist {
        private const val version = "0.6.2"
        const val coil = "dev.chrisbanes.accompanist:accompanist-coil:$version"
        const val insets = "dev.chrisbanes.accompanist:accompanist-insets:$version"
    }
}

object ClassPaths {
    const val gradlePlugin = "com.android.tools.build:gradle:${Versions.gradle}"
    const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val daggerPlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.daggerHilt}"
    const val apolloPlugin = "com.apollographql.apollo:apollo-gradle-plugin:${Versions.apollo}"
    const val spotlessPlugin = "com.diffplug.spotless:spotless-plugin-gradle:${Versions.spotless}"
}

object Plugins {
    const val androidApplication = "com.android.application"
    const val kotlinAndroid = "android"
    const val kotlinParcelize = "kotlin-parcelize"
    const val daggerHilt = "dagger.hilt.android.plugin"
    const val ktLint = "org.jlleitschuh.gradle.ktlint"
    const val apollo = "com.apollographql.apollo"
    const val kotlinKapt = "kapt"
    const val spotless = "com.diffplug.spotless"
}

object Configs {
    const val applicationId = "com.ericktijerou.jetkanto"
    const val buildToolsVersion = "30.0.3"
    const val compileSdkVersion = 30
    const val minSdkVersion = 21
    const val targetSdkVersion = 30
    const val versionCode = 1
    const val versionName = "1.0.0"
}

object Versions {
    internal const val appcompat = "1.3.0-beta01"
    internal const val gradle = "7.0.0-alpha11"
    internal const val daggerHilt = "2.31-alpha"
    internal const val daggerHiltAndroid = "1.0.0-alpha03"
    internal const val coroutines = "1.4.2"
    internal const val materialDesign = "1.3.0"
    internal const val coreKtx = "1.5.0-beta01"
    internal const val coil = "1.1.1"
    internal const val lifecycle = "2.3.0"
    internal const val room = "2.3.0-beta01"
    internal const val okhttp = "4.9.1"
    internal const val composeViewModel = "1.0.0-alpha01"
    internal const val composeActivity = "1.3.0-alpha04"
    internal const val composeConstraint = "1.0.0-alpha03"
    internal const val navigationCompose = "1.0.0-alpha09"
    internal const val spotless = "5.10.0"
    internal const val paging = "3.0.0-alpha13"
    internal const val composePaging = "1.0.0-alpha08"
    internal const val retrofit = "2.6.1"
    internal const val retrofitKotlinxVersion = "0.4.0"
    const val kotlin = "1.4.31"
    const val apollo = "2.5.3"
    const val compose = "1.0.0-beta02"
    const val ktlint = "0.40.0"
}
