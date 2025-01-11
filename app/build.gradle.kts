plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.dicoding.pp_stokbaju"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dicoding.pp_stokbaju"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    // Retrofit & Gson
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    // Gson (untuk parsing JSON)
    implementation (libs.gson)
    // OkHttp (untuk logging dan interceptor)
    implementation (libs.okhttp)
    implementation (libs.logging.interceptor)
    // RecyclerView
    implementation (libs.recyclerview)
    // Glide (untuk menampilkan gambar dari URL)
    implementation (libs.glide)
    annotationProcessor (libs.compiler)
    androidTestImplementation (libs.junit.v115)
    androidTestImplementation (libs.espresso.core.v351)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}