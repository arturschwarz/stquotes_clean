package dev.arturschwarz.stquotes.di

import dev.arturschwarz.stquotes.data.QuotesRepository
import dev.arturschwarz.stquotes.viewmodel.MainScreenViewModel
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.json.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // Singletons
    single {
        HttpClient(OkHttp) {
            install(JsonFeature) {
                serializer = GsonSerializer()
            }
        }
    }
    single { QuotesRepository(get()) }

    // ViewModels
    viewModel { MainScreenViewModel() }

}