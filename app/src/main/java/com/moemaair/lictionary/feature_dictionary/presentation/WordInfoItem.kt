package com.moemaair.lictionary.feature_dictionary.presentation

import android.annotation.SuppressLint
import android.content.res.Resources.Theme
import android.media.MediaPlayer
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*

import androidx.compose.ui.graphics.Color
import com.moemaair.lictionary.feature_dictionary.domain.model.WordInfo

@SuppressLint("RestrictedApi")
@Composable
fun WordInfoItem(
    wordInfo: WordInfo,
    audioIcon: ImageVector,
) {
    var context = LocalContext.current.applicationContext
    var mediaPlayer = MediaPlayer()

    var audioUrl: String? by remember {
        mutableStateOf("")
    }
    val audio = wordInfo.phonetics.forEach{ it ->
        if (!(it.audio == "")){
            audioUrl = it.audio
        }
    }
    var phonetic: String? by remember {
        mutableStateOf("")
    }
    val textPhonetic = wordInfo.phonetics.forEach{it ->  phonetic = it.text}

    Column(modifier = Modifier) {

        Row(modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start) {
            //word
            Text(
                text = wordInfo.word,
                color = if(!isSystemInDarkTheme()) Color.Black else Color.White,
                style = MaterialTheme.typography.headlineMedium
            )

            //audio icon
            IconButton(onClick = {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(audioUrl.toString())
                mediaPlayer.prepare()
                mediaPlayer.start()
            }) {

                if ((audioUrl?.isNotEmpty() == true)) {
                    Icon(imageVector = audioIcon, contentDescription = "")
                }

            }
        }



        Text(text = phonetic.toString(), color = Color.Green, fontWeight = FontWeight.Normal)
        Spacer(modifier = Modifier.height(10.dp))

        wordInfo.meanings.forEach { meaning ->
            Text(text = meaning.partOfSpeech,color = Color.Red, fontWeight = FontWeight.Bold)
            meaning.definitions.forEachIndexed { i, definition ->
                SelectionContainer {
                    Text(text = "${definition.definition}" , color = if(!isSystemInDarkTheme()) Color.Black else Color.White)
                }
                Spacer(modifier = Modifier.height(8.dp))
                definition.example?.let { example ->
                    Text(text = "Example: $example",
                        fontWeight = FontWeight.Light,
                        color = Color.Gray, fontStyle = FontStyle.Italic)
                }
                definition
                Spacer(modifier = Modifier.height(8.dp))
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }

}