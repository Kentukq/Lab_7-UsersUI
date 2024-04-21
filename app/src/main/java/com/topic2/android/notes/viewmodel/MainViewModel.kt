package com.topic2.android.notes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.topic2.android.notes.data.repository.Repository
import com.topic2.android.notes.domain.model.ColorModel
import com.topic2.android.notes.domain.model.NoteModel
import com.topic2.android.notes.routing.NotesRouter
import com.topic2.android.notes.routing.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Модель просмотра, используемая для хранения глобального состояния приложения.
 *
 * Эта модель просмотра используется для всех экранов.
 */
class MainViewModel(private val repository: Repository) : ViewModel() {
    val notesNotInTrash: LiveData<List<NoteModel>> by lazy {
        repository.getAllNotesNotInTrash()
    }

    val notesInTrash by lazy { repository.getAllNotesInTrash() }

    private var _noteEntry = MutableLiveData(NoteModel())
    val noteEntry: LiveData<NoteModel> = _noteEntry

    val colors: LiveData<List<ColorModel>> by lazy {
        repository.getAllColors()
    }


    private var _selectedNotes = MutableLiveData<List<NoteModel>>(listOf())
    val selectedNotes: LiveData<List<NoteModel>> = _selectedNotes

    fun onCreateNewNoteClick() {
        _noteEntry.value = NoteModel()
        NotesRouter.navigateTo(Screen.SaveNote)
    }

    fun onNoteClick(note: NoteModel) {
        // TODO - Open SaveNoteScreen in Edit mode
        _noteEntry.value = note
        NotesRouter.navigateTo(Screen.SaveNote)

    }

    fun onNoteCheckedChange(note: NoteModel) {
        viewModelScope.launch(
            Dispatchers.Default
        ) {
            repository.insertNote(note)
        }
    }
    fun onNoteEntryChange(note: NoteModel) {
        _noteEntry.value = note
    }

    fun saveNote(note: NoteModel) {
        viewModelScope.launch(Dispatchers.Default) {
            repository.insertNote(note)

            withContext(Dispatchers.Main) {
                NotesRouter.navigateTo(Screen.Notes)
                _noteEntry.value = NoteModel()
            }
        }
    }

    fun moveNoteToTrash(note: NoteModel) {
        viewModelScope.launch(Dispatchers.Default) {
            repository.moveNoteToTrash(note.id)
            withContext(Dispatchers.Main) {
                NotesRouter.navigateTo(Screen.Notes)
            }
        }
    }
}