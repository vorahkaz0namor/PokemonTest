package com.example.pokemoncompose.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.ccink.domain.onFailure
import com.ccink.domain.onSuccess
import com.ccink.domain.useCases.PokemonUseCase
import com.ccink.model.dto.Pokemon
import com.ccink.model.model.BaseModel
import com.ccink.model.model.Resource
import com.ccink.resources.*
import com.example.pokemoncompose.App
import com.example.pokemoncompose.flowBus.FlowBus
import com.example.pokemoncompose.flowBus.PokemonEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class PokemonViewModel : ViewModel() {
    @Inject
    lateinit var pokeUseCase: PokemonUseCase
    private val _pokeState: MutableStateFlow<PokeState> =
        MutableStateFlow(PokeState().loading())
    val pokeState: StateFlow<PokeState>
        get() = _pokeState.asStateFlow()
    private val _selectedItem = MutableStateFlow<Pokemon?>(null)

    init {
        App.getAppComponent().inject(this)
        setSelectedItem()
        subscribeToFlowBus()
        sendEvent(PokemonEvent.LoadData)
    }

    fun sendEvent(event: PokemonEvent) {
        viewModelScope.launch { FlowBus.send(event) }
    }

    private fun backToMainScreen() {
        _selectedItem.value = null
    }

    private fun showSingleItem(name: String) {
        _selectedItem.value =
            pokeState.value.pokemonList
                .find { it.name == name }
    }

    private fun setSelectedItem() {
        viewModelScope.launch {
            _selectedItem.collectLatest {
                pokeState.value
                    .copy(selectedItem = it)
                    .updatePokeState()
            }
        }
    }

    private suspend fun loadData() {
        pokeUseCase.readPokemons().handlingIncomingData { pokemons ->
            pokeState.value.copy(
                pokemonList = pokemons.sortedBy { it.id }
            ).updatePokeState()
        }
    }

    private fun subscribeToFlowBus() {
        viewModelScope.launch {
            FlowBus.subscribe<PokemonEvent> { event ->
                when (event) {
                    is PokemonEvent.LoadData ->
                        executeTheAction { loadData() }
                    is PokemonEvent.ShowItemCard ->
                        showSingleItem(event.name)
                    is PokemonEvent.BackToPokemonMainScreen ->
                        backToMainScreen()
                }
            }
        }
    }

    /**
     * Updates pokemon state
     */
    private fun PokeState.updatePokeState() {
        _pokeState.value = this
    }

    private inline fun PokemonViewModel.executeTheAction(
        action: () -> Unit
    ) {
            pokeState.value.loading().updatePokeState()
            action()
            if (!pokeState.value.error) {
                pokeState.value.display().updatePokeState()
            }
    }

    /**
     * Handling UseCase response depending on Success or Failure
     */
    private inline fun <T> Resource<BaseModel<T>>
            .handlingIncomingData(action: (value: T) -> Unit) {
        onSuccess {
            it.results?.let { data -> action(data) }
                ?: pokeState.value.error(NoData).updatePokeState()
        }
            .onFailure {
                pokeState.value.error(IOError).updatePokeState()
            }
    }
}

@Composable
fun PokemonViewModel.WithCallbackState(
    navigateUp: () -> Boolean,
    navigateToDetailScreen: () -> Unit,
    composable: @Composable (PokeState, CallbackState) -> Unit
) {
    val pokeState by this.pokeState.collectAsStateWithLifecycle()
    val callbackState = CallbackState(
        onBackButtonClick = {
            sendEvent(PokemonEvent.BackToPokemonMainScreen)
            navigateUp()
        },
        onSelectedItemClick = { itemName: String ->
            sendEvent(PokemonEvent.ShowItemCard(name = itemName))
            navigateToDetailScreen()
        }
    )
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement =
            if (pokeState.loading)
                Arrangement.Center
            else
                Arrangement.Top,
        modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        pokeState.HandlingLoadState {
            composable(pokeState, callbackState)
        }
    }
}
