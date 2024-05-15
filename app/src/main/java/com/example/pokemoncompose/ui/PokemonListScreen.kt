package com.example.pokemoncompose.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.SubcomposeAsyncImage
import com.ccink.model.dto.Pokemon
import com.ccink.resources.EightDp
import com.ccink.resources.NinetySixDp
import com.ccink.resources.SixteenSp
import com.ccink.resources.TwentyDp

@Composable
fun PokemonListScreen(
    pokemonList: List<Pokemon>,
    onSelectedItemClick: (String) -> Unit
) {
        LazyColumn(
            modifier = Modifier.padding(top = TwentyDp),
            verticalArrangement = Arrangement.spacedBy(TwentyDp),
            contentPadding = PaddingValues(TwentyDp)
        ) {
            items(items = pokemonList) { item ->
                ItemCard(
                    item = item,
                    onSelectedItemClick = onSelectedItemClick
                )
            }
        }
}

@Composable
fun ItemCard(
    item: Pokemon,
    onSelectedItemClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onSelectedItemClick(item.name) },
    ) {
        ElevatedCard(
            shape = CardDefaults.elevatedShape,
            elevation = CardDefaults.elevatedCardElevation(EightDp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                /**
                 * Item image
                 */
                ItemImageOnly(
                    modifier = Modifier
                        .padding(EightDp)
                        .height(NinetySixDp),
                    item = item
                )

                /**
                 * Item name
                 */
                Text(
                    text = item.name,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = SixteenSp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Composable
fun ItemImageOnly(
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    item: Pokemon
) {
    SubcomposeAsyncImage(
        modifier = modifier,
        alignment = Alignment.CenterStart,
        contentScale = contentScale,
        model = item.sprites.image,
        contentDescription = item.name,
        loading = { ProgressBar() }
    )
}