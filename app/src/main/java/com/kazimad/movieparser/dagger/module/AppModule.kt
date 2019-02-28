//package com.kazimad.movieparser.dagger.module
//
//import android.app.Application
//import dagger.Module
//import dagger.Provides
//import javax.inject.Singleton
//
//
//@Module
//class AppModule {
//    lateinit var mApplication: Application
//
//    fun AppModule(application: Application) {
//        mApplication = application
//    }
//
//    @Provides
//    @Singleton
//    fun providesApplication(): Application {
//        return mApplication;
//    }
//}