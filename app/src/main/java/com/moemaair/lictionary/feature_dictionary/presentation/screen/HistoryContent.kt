package com.moemaair.lictionary.feature_dictionary.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.moemaair.lictionary.feature_dictionary.presentation.MainViewModel
import com.moemaair.lictionary.feature_dictionary.presentation.components.HistoryCard
import com.moemaair.lictionary.navigation.Screen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryContent (
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {

   androidx.compose.material.Surface() {
       Scaffold(
           topBar = {
               TopAppBar( elevation = 7.dp) {
                   Row(
                       modifier = Modifier
                           .fillMaxWidth()
                           .padding(6.dp),
                       verticalAlignment = Alignment.CenterVertically,
                       horizontalArrangement = Arrangement.SpaceBetween
                   )
                   {
                       IconButton(onClick = {
                           navController.navigate(Screen.Home.route)
                       }) {
                           Icon(imageVector =  Icons.Default.ArrowBack, contentDescription ="" )
                       }
                       Text(text = "History")
                   }

               }
           },
           content = {
               HistoryCard()
           }
       )
   }
}