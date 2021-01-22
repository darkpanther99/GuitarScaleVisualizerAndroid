package hu.bme.aut.guitarscaleviz.model

import hu.aut.bme.guitarscaleviz.model.Scale
import java.io.Serializable
import java.util.*

class GuitarString : Serializable {
    var fretCount: Int
        private set
    private var notes: ArrayList<Note>
    var startingNote: Note
        private set

    //kezdohang+fretszam parameterû ctor
    constructor(starting: Note, fretCnt: Int) {
        fretCount = fretCnt
        notes = ArrayList(fretCount)
        startingNote = starting
        for (i in 0..fretCount) {
            notes.add(i, Note(i + startingNote.number))
        }
    }

    //kezdohang neve+fretszam paraméterû ctor
    constructor(startingNoteStr: String?, fretCnt: Int) {
        fretCount = fretCnt
        notes = ArrayList(fretCount)
        startingNote = Note(startingNoteStr!!)
        for (i in 0..fretCount) {
            notes.add(i, Note(i + startingNote.number))
        }
    }

    //húron megkeres egy adott hangot
    fun search(h: Note?) {
        for (iter in notes) {
            if (iter.number == h?.number) {
                iter.inScale = true
            }
        }
    }

    //a korábban megkeresett hangokat érvényteleníti a húron
    fun invalidateSearch() {
        for (iter in notes) {
            iter.inScale = false
            iter.isRootNote = false
        }
    }

    //egy teljes skálát megkeres a húron
    fun search(s: Scale) {
        searchRootNote(s.get(0))
        for (i in 1 until s.scaleNoteCount) {
            search(s.get(i))
        }
    }

    fun searchRootNote(h: Note?) {
        for (iter in notes) {
            if (iter.number == h?.number) {
                iter.inScale = true
                iter.isRootNote = true
            }
        }
    }

    //ez a fura getter azért van, hogy elkerülje a hosszú láncolását az osztálymetódushívásoknak a Fretboard konstruktorában.
    val startingNoteNum: Int
        get() = startingNote.number


    fun noteAtIdx(index: Int): Note {
        return notes[index]
    }

}