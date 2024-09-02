package com.example.notepad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : ComponentActivity() {

    private val fileName = "notes.txt"

    // OnCreate wird gestartet wenn die App geöffnet wird
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotepadApp()
        }
    }
    // Benutzeroberfläche
    @Composable
    fun NotepadApp() {
        var noteText by remember { mutableStateOf("") }

        LaunchedEffect(Unit) {
            noteText = loadNote()
        }
        // Textfeld zur Eingabe
        Column(modifier = Modifier.fillMaxSize().padding(40.dp)) {
            OutlinedTextField(
                value = noteText,
                onValueChange = { noteText = it },
                label = { Text("Notizen") },
                modifier = Modifier.weight(1f),
                maxLines = 10
            )
            // Knopf zum Speichern
            Button(
                onClick = { saveNote(noteText) },
                modifier = Modifier.padding(top = 40.dp)
            ) {
                Text("Speichern")
            }
        }
    }

    // Laden der Notiz
    private fun loadNote(): String {
        return try {
            val fileInputStream: FileInputStream = openFileInput(fileName) // öffnen
            val inputStreamReader = fileInputStream.bufferedReader() // lesen
            inputStreamReader.use { it.readText() }
        } catch (e: IOException) {
            e.printStackTrace() // bei Exceptions: Ausgabe StackTrace bzw. Leere
            ""
        }
    }

    // Speichern der Notiz
    private fun saveNote(noteText: String) {
        try {
            val fileOutputStream: FileOutputStream = openFileOutput(fileName, MODE_PRIVATE) // öffnen
            fileOutputStream.write(noteText.toByteArray()) // schreiben
            fileOutputStream.close() // schließen
        } catch (e: IOException) {
            e.printStackTrace() // bei Exceptions: Ausgabe StackTrace
        }
    }
}