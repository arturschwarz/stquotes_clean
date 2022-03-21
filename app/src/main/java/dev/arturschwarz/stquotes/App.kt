package dev.arturschwarz.stquotes

import android.app.Application
import dev.arturschwarz.stquotes.di.appModule
import org.koin.core.context.GlobalContext

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        GlobalContext.startKoin {
            modules(appModule)
        }
    }

}