package com.example.DLChordsTT.features.music_player.ui.screens

import DLChordsTT.R
import android.app.ProgressDialog
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.data.models.AudioProc
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.view_models.AudioProcViewModel
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.data.models.Audio
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.view_models.AudioViewModel
import com.example.DLChordsTT.features.audio_list.ui.components.timeStampToDuration
import com.example.DLChordsTT.features.generated_files.features.file_pdf_list.data.models.Chord
import com.example.DLChordsTT.features.generated_files.features.file_pdf_list.view_models.GeneratedFilesViewModel
import com.example.DLChordsTT.features.music_player.ui.components.TopAppBarPlayer
import com.example.DLChordsTT.ui.theme.DLChordsTheme
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.json.JSONObject
import java.io.IOException
import kotlin.math.absoluteValue


@Composable
fun PlayerMusicStored(
    context: Context,
    progress: Float,
    onProgressChange: (Float) -> Unit,
    audio: Audio,
    audioViewModel: AudioViewModel,
    audioProcViewModel: AudioProcViewModel,
    generatedFilesViewModel: GeneratedFilesViewModel,
) {

    DLChordsTheme {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),

            ) {
            TopAppBarPlayer(textOnTop = audio.title, audio = audio, audioViewModel = audioViewModel)
            Card(
                shape = DLChordsTheme.shapes.medium,
                modifier = Modifier
                    .width(254.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 38.dp, bottom = 25.dp),

                ) {
                Image(
                    painter = painterResource(id = R.drawable.musicplayer_image),
                    "Player",
                )
            }
            val range = 0f..100f
            Column(
                modifier = Modifier
                    .fillMaxWidth(.7f)
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 40.dp)
            ) {
                Slider(
                    value = progress,
                    valueRange = range,
                    onValueChange = { onProgressChange.invoke(it) },
                    colors = SliderDefaults.colors(
                        thumbColor = DLChordsTheme.colors.secondaryText,
                        activeTrackColor = DLChordsTheme.colors.secondaryText
                    )
                )
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "0:00", style = DLChordsTheme.typography.caption)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = timeStampToDuration(audio.duration.toLong()),
                        style = DLChordsTheme.typography.caption
                    )
                }
            }

            Text(
                modifier = Modifier.padding(start = 45.dp),
                textAlign = TextAlign.Left,
                text = "Opciones de procesamiento",
                style = DLChordsTheme.typography.h5,
                maxLines = 1
            )

            Button(
                onClick = {
                    /////////Esto se hará cuando se termine de procesar el audio
                    val audioP = AudioProc(
                        id = audio.id,
                        displayName = audio.displayName,
                        artist = audio.artist,
                        data = audio.data,
                        duration = audio.duration,
                        title = audio.title,
                    )
                    audioProcViewModel.addNewAudioProc(audioP)

                    generatedFilesViewModel.generatePDFs(context = context, audioProc = audioP,"Aqui van los request","Aqui van los request")

                    // mProgressDialog.setCancelable(true)
                    // mProgressDialog.cancel()

                },
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .padding(vertical = 40.dp)
                    .align(Alignment.CenterHorizontally)
                    .border(1.dp, color = DLChordsTheme.colors.divider, shape = CircleShape),
                shape = CircleShape,
                elevation = ButtonDefaults.elevation(0.dp, 0.dp),
                contentPadding = PaddingValues(20.dp, 12.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = DLChordsTheme.colors.background,
                )
            ) {
                Text(
                    text = "FRAGMENTO DE AUDIO",
                    style = DLChordsTheme.typography.button,
                    maxLines = 1,
                    color = DLChordsTheme.colors.primary
                )
            }
            Button(
                onClick = {
                    ////////Esto se hará cuando se termine de procesar el audio
                    audioProcViewModel.addNewAudioProc(
                        AudioProc(
                            id = audio.id,
                            displayName = audio.displayName,
                            artist = audio.artist,
                            data = audio.data,
                            duration = 344324,
                            title = audio.title,

                            )
                    )

                },
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .padding(bottom = 40.dp)
                    .align(Alignment.CenterHorizontally),
                shape = CircleShape,
                elevation = ButtonDefaults.elevation(0.dp, 0.dp),
                contentPadding = PaddingValues(20.dp, 12.dp),

                ) {
                Text(
                    text = "AUDIO COMPLETO",
                    style = DLChordsTheme.typography.button,
                    maxLines = 1,
                    color = DLChordsTheme.colors.surface
                )
            }
        }

    }
}
