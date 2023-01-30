package com.moemaair.lictionary


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.moemaair.lictionary.feature_dictionary.presentation.MainViewModel
import com.moemaair.lictionary.feature_dictionary.presentation.WordInfoItem
import com.moemaair.lictionary.ui.theme.LictionaryTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LictionaryTheme {
                MainScreen()
            }
        }

    }
}

@Composable
fun MainScreen() {
    val viewModel: MainViewModel = hiltViewModel()
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is MainViewModel.UIEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBar("Lictionary", backgroundColor = MaterialTheme.colors.primaryVariant)
        }
    ){
        Column{
            Box(modifier = Modifier
                .fillMaxSize()
                .background(if (isSystemInDarkTheme()) Color.Black else Color.LightGray.copy(alpha = 0.2f)))
            {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .height(90.dp)
                    .background(MaterialTheme.colors.primary)
                )
                OutlinedTextField(
                    value = viewModel.searchQuery.value,
                    onValueChange = viewModel::onSearch,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .padding(10.dp, 60.dp)
                        .shadow(5.dp),
                    placeholder = { Text(text = "Search for words...", color = Color.LightGray)},
                    trailingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "")},
                   colors = TextFieldDefaults.textFieldColors(
                       backgroundColor = Color.White,
                       textColor = Color.Black,
                       trailingIconColor = MaterialTheme.colors.primaryVariant,
                       focusedIndicatorColor = Color.Transparent

                   ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search
                    )
                )

                LazyColumn(modifier = Modifier)
                {
                    items(state.wordInfoItems.size) { i ->
                        val wordInfo = state.wordInfoItems[i]
                        if(i > 0) {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        WordInfoItem(wordInfo = wordInfo)
                        if(i < state.wordInfoItems.size - 1) {
                            Divider()
                        }
                    }

                }

                if(state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

            }


        }

    }
}



@Composable
fun AppBar(title : String, backgroundColor: Color) {
    TopAppBar(
        elevation = 0.dp,
        backgroundColor = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(imageVector = Icons.Filled.Menu, contentDescription = "")
            Text(text = title)
            Spacer(modifier = Modifier.size(20.dp))
        }

    }
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LictionaryTheme {
        MainScreen()
    }
}