package com.example.pokemoncompose.di

import com.ccink.domain.di.DomainModule
import com.example.pokemoncompose.App
import com.example.pokemoncompose.ui.PokemonViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [DomainModule::class])
@Singleton
interface AppComponent {

    fun inject(viewModel: PokemonViewModel)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun app(app: App): Builder

        fun build(): AppComponent
    }
}