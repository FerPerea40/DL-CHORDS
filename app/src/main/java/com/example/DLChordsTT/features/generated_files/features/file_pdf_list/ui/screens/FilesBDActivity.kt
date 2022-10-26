package com.example.DLChordsTT.features.generated_files.features.file_pdf_list.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.data.models.AudioProc
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.view_models.AudioProcViewModel
import com.example.DLChordsTT.features.audio_list.ui.components.AlertDialogProcessedAudio
import com.example.DLChordsTT.features.audio_list.ui.components.AlertDialogProcessing2
import com.example.DLChordsTT.features.audio_list.ui.screens.FilesBDScreen
import com.example.DLChordsTT.features.generated_files.features.file_pdf_list.view_models.GeneratedFilesViewModel
import com.example.DLChordsTT.ui.theme.DLChordsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilesBDActivity : ComponentActivity() {
    val audioViewModel: AudioProcViewModel by viewModels()
    val generatedFilesViewModel: GeneratedFilesViewModel by viewModels()
    val audioProcViewModel: AudioProcViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        val receiveName = intent.extras
        var responseWhitLyricChords = receiveName?.getString("response")
        val audio = receiveName?.getSerializable("Audio") as AudioProc

        super.onCreate(savedInstanceState)

        var audioP = AudioProc(
            id = audio.id,
            displayName = audio.displayName,
            artist = audio.artist,
            data = audio.data,
            duration = audio.duration,
            title = audio.title,
            english_nomenclature = audio.english_nomenclature,
            latin_nomenclature = audio.latin_nomenclature,
            chords_lyrics_e = audio.chords_lyrics_e,
            chords_lyrics_l = audio.chords_lyrics_l,
            lyrics = audio.lyrics,

            )



        setContent {
            val openDialogProcessing = remember { mutableStateOf(true) }
            val audioFinal = mutableStateOf<AudioProc>(audioP)
            var contador = 0

            DLChordsTheme {
                Scaffold(
                    isFloatingActionButtonDocked = false,
                    floatingActionButtonPosition = FabPosition.End,
                    backgroundColor = MaterialTheme.colors.background
                ) {

                    if (responseWhitLyricChords != null) {
                        audioProcViewModel.addNewAudioProc(audioP)
                        generatedFilesViewModel.generatePDFs(
                            LocalContext.current,
                            audioP.id,
                            audioP.displayName,
                            audioP.artist,
                            audioP.data,
                            audioP.duration,
                            audioP.title,
                            "",
                            "",
                            "",
                            "",
                            "",
                            responseWhitLyricChords
                        )
                        println("listita con pdf creada ${generatedFilesViewModel.listPDF.size}")
                        if (contador == 0) {
                            generatedFilesViewModel.UploadPDFsInBD(
                                audioP.id,
                                audioP.displayName,
                                audioP.artist,
                                audioP.data,
                                audioP.duration,
                                audioP.title,
                                "",
                                "",
                                "",
                                "",
                                "",
                                generatedFilesViewModel.listPDF
                            )
                            contador++
                        }

                    } else {
                        Column() {
                            Card() {
                                Text(text = "responseWhitLyricChords NULL")
                            }

                        }
                    }

                    if (!generatedFilesViewModel.isUploadingPDFsOnDB.value) {
                        audioFinal.value = generatedFilesViewModel.audiosProc[generatedFilesViewModel.audiosProc.lastIndex]
                        audioProcViewModel.addNewAudioProc(audioFinal.value)
                        openDialogProcessing.value = false
                    }
                  //  audioProcViewModel.addNewAudioProc(audioFinal.value)
                    AlertDialogProcessing2(openDialogProcessing = openDialogProcessing)
                    FilesBDScreen(audio = audioFinal.value, generatedFilesViewModel)
                }
            }
        }

    }
}