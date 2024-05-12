package com.ccink.domain.di

import com.ccink.domain.useCases.PokemonUseCase
import com.ccink.domain.useCases.PokemonUseCaseImpl
import com.ccink.model.di.DataModule
import com.ccink.model.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Module(includes = [DataModule::class, DispatcherModule::class])
class DomainModule {

    @Singleton
    @Provides
    fun providePokemonUseCase(
        repository: PokemonRepository,
        @IoDispatcher
        dispatcher: CoroutineDispatcher
    ): PokemonUseCase =
        PokemonUseCaseImpl(repository, dispatcher)

}

@Module
object DispatcherModule {

    @Singleton
    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher