package com.example.e_commerceappkotlin.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.view.Display.Mode
import com.example.e_commerceappkotlin.firebase.FirebaseCommon
import com.example.e_commerceappkotlin.util.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFirestoreDatabase() = Firebase.firestore

    @Provides
    @Singleton
    fun provideIntroductionSP(application: Application)
    = application.getSharedPreferences(Constants.INTRODUCTION_SP,MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideFirebaseComman(
        firebaseAuth: FirebaseAuth,
    firestore: FirebaseFirestore
        )
    =FirebaseCommon(firestore,firebaseAuth)
}