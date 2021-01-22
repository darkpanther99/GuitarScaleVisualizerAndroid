package hu.aut.bme.guitarscaleviz.model

import hu.bme.aut.guitarscaleviz.model.Note
import java.io.Serializable

class Scale : Serializable {
    private var startingNote: Note? = null
    private var scaleType: String? = null

    //getter
    var scaleNoteCount: Int
        private set
    private var notes: Array<Note?>

    //hangok tömbjét átvéve létrehoz egy megadott fokú skálát ctor
    constructor(notesArray: Array<Note?>) {
        scaleNoteCount = notesArray.size
        notes = notesArray
    }

    //létrehoz egy hétfokú alap skálát a kezdőhangból és a skálatipusbol ctor
    constructor(kezdo: Note, mod: String?) : this(kezdo, mod, 7) {}

    //kezdőhang, skálatípus és skálafok paraméterű ctor
    constructor(starting: Note, type: String?, noteCount: Int) {
        scaleNoteCount = noteCount
        notes = arrayOfNulls<Note>(scaleNoteCount)
        startingNote = starting
        scaleType = type
        notes[0] = startingNote
        if (scaleNoteCount == 7) {
            when (scaleType) {
                "Major"-> {
                    notes[1] = Note(starting.number + 2)
                    notes[2] = Note(starting.number + 4)
                    notes[3] = Note(starting.number + 5)
                    notes[4] = Note(starting.number + 7)
                    notes[5] = Note(starting.number + 9)
                    notes[6] = Note(starting.number + 11)
                }
                "Dorian"-> {
                    notes[1] = Note(starting.number + 2)
                    notes[2] = Note(starting.number + 3)
                    notes[3] = Note(starting.number + 5)
                    notes[4] = Note(starting.number + 7)
                    notes[5] = Note(starting.number + 9)
                    notes[6] = Note(starting.number + 10)
                }
                "Phrygian"-> {
                    notes[1] = Note(starting.number + 1)
                    notes[2] = Note(starting.number + 3)
                    notes[3] = Note(starting.number + 5)
                    notes[4] = Note(starting.number + 7)
                    notes[5] = Note(starting.number + 8)
                    notes[6] = Note(starting.number + 10)
                }
                "Lydian"-> {
                    notes[1] = Note(starting.number + 2)
                    notes[2] = Note(starting.number + 4)
                    notes[3] = Note(starting.number + 6)
                    notes[4] = Note(starting.number + 7)
                    notes[5] = Note(starting.number + 9)
                    notes[6] = Note(starting.number + 11)
                }
                "Mixolydian"-> {
                    notes[1] = Note(starting.number + 2)
                    notes[2] = Note(starting.number + 4)
                    notes[3] = Note(starting.number + 5)
                    notes[4] = Note(starting.number + 7)
                    notes[5] = Note(starting.number + 9)
                    notes[6] = Note(starting.number + 10)
                }
                "Minor"-> {
                    notes[1] = Note(starting.number + 2)
                    notes[2] = Note(starting.number + 3)
                    notes[3] = Note(starting.number + 5)
                    notes[4] = Note(starting.number + 7)
                    notes[5] = Note(starting.number + 8)
                    notes[6] = Note(starting.number + 10)
                }
                "Locrian"-> {
                    notes[1] = Note(starting.number + 1)
                    notes[2] = Note(starting.number + 3)
                    notes[3] = Note(starting.number + 5)
                    notes[4] = Note(starting.number + 6)
                    notes[5] = Note(starting.number + 8)
                    notes[6] = Note(starting.number + 10)
                }
                else -> {
                }
            }
        }
    }

    //visszaadja a skála kért hangját
    operator fun get(idx: Int): Note? {
        if (idx >= scaleNoteCount || idx < 0) {
            throw ArrayIndexOutOfBoundsException("Rossz eleres!")
        }
        return notes[idx]
    }
}