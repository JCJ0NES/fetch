package com.example.fetch.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fetch.R
import com.example.fetch.model.HiringData
import com.example.fetch.ui.theme.HiringDataTheme

@Composable
fun HomeScreen(
    hiringUiState: HiringUiState, modifier: Modifier = Modifier
) {
    when (hiringUiState) {
        is HiringUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is HiringUiState.Success -> ResultScreen(
            hiringUiState.hiring, modifier = modifier.fillMaxWidth()
        )
        is HiringUiState.Error -> ErrorScreen(modifier = modifier.fillMaxSize())
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ResultScreen(hiring: Map<Int, List<HiringData>>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally) {
            hiring.forEach {
                group -> stickyHeader {
                    Column(
                        modifier = Modifier
                            .height(100.dp)
                            .fillMaxWidth()
                            .background(Color.LightGray),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Row {
                            Text(
                                text = "List ID: ${group.key}",
                                modifier = Modifier.fillMaxWidth(),
                                style = MaterialTheme.typography.headlineLarge,
                                textAlign = TextAlign.Center
                            )
                        }
                        Row(
                            modifier = modifier,
                            horizontalArrangement = Arrangement.SpaceEvenly) {
                            Text(text = "ID", fontWeight = FontWeight.Bold)
                            Text(text = "Name", fontWeight = FontWeight.Bold)
                        }
                    }
                }
                items(group.value.size) { item ->
                    Row(modifier = modifier,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(text = group.value[item].id.toString())
                        Text(text = group.value[item].name)
                    }
                }
            }
    }
}

@Preview
@Composable
fun ResultScreenPreview() {
    HiringDataTheme {
    }
}