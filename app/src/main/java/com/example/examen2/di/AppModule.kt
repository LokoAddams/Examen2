package com.example.examen2.di


import android.content.Context
import com.example.usecases.IrWhatsAppUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideIrWhatsAppUseCase(): IrWhatsAppUseCase {
        return IrWhatsAppUseCase()
    }
}