package com.kazimad.movieparser.dagger.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ContextModule (var context: Context) {
    @Provides
    @Singleton
    internal fun providesContext(): Context {
        return context
    }
}