
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.touchlab.skie)
    alias(libs.plugins.kotest.multiplatform)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }
    
    sourceSets {
        commonMain.dependencies {
            // put your Multiplatform dependencies here
            api(libs.koin.core)
            implementation(libs.koin.composeVM)
            implementation(libs.touchlab.skie.annotations)
            implementation(libs.lifecycle.viewmodel)
            implementation(libs.datastore.preferences)
        }
    }
    
    // Fix for build error 'Cannot locate tasks that match ':shared:testClasses' as task 'testClasses' not found in project ':shared'.'
    // see https://stackoverflow.com/questions/78133592/kmm-project-build-error-testclasses-not-found-in-project-shared
    task("testClasses")
}

android {
    namespace = "com.appmilla.kmpjetpackdemo.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    dependencies {
        implementation(libs.androidx.core.ktx)
        implementation(libs.koin.android)
    }
}
