package hu.bme.aut.guitarscaleviz.model

import hu.aut.bme.guitarscaleviz.model.Scale
import java.io.Serializable

class Fretboard : Serializable {
    var stringCount: Int
        private set
    var fretCount: Int
        private set
    private var string: Array<GuitarString?>
    private var startingNote: Note? = null
    private var tuningType: String? = null
    var instrument: String
        private set

    /*létrehoz egy fretboardot a legmélyebb húr kezdőhangja, a hangolás típusa, a húrok és fretek száma és a hangszer típusa alapján
     * hangszertípus bővíthető később
     * létrehoz annyi húrt, amennyi kell, azokat a szabály szerint behangolja és rárakja a fretboardra
     * Szabály:
     * Bass minden húrjának kezdőhangja standard hangolásban 5 hang távolságra van a nála eggyel arrébb lévőtől
     * (igazából félhang, de a programban most 1 hang a félhang)
     * Guitar minden húrjának kezdőhangja standard hangolásban 5 hang távolságra van, kivéve az utolsó előtti húr, mert az 4
     * Drop hangolásban a legmélyebb húr 2 félhanggal lentebb van
     */
    constructor(
        starting: Note,
        tuning: String?,
        stringCnt: Int,
        instrumentType: String,
        fretCnt: Int
    ) {
        startingNote = starting
        tuningType = tuning
        stringCount = stringCnt
        instrument = instrumentType
        fretCount = fretCnt
        string = arrayOfNulls(stringCount)
        string[0] = GuitarString(startingNote!!, fretCount)
        if (instrument == "Guitar") {
            if (tuningType == "Standard") {
                for (i in 1 until stringCount - 2) {
                    string[i] =
                        GuitarString(Note(startingNote!!.number + 5 * i), fretCount)
                }
                string[stringCount - 2] = GuitarString(
                    Note(string[stringCount - 3]!!.startingNoteNum + 4),
                    fretCount
                )
                string[stringCount - 1] = GuitarString(
                    Note(string[stringCount - 3]!!.startingNoteNum + 9),
                    fretCount
                )
            }
            if (tuningType == "Drop") {
                string[1] =
                    GuitarString(Note(startingNote!!.number + 7), fretCount)
                for (i in 2 until stringCount - 2) {
                    string[i] = GuitarString(
                        Note(string[i - 1]!!.startingNoteNum + 5),
                        fretCount
                    )
                }
                string[stringCount - 2] = GuitarString(
                    Note(string[stringCount - 3]!!.startingNoteNum + 4),
                    fretCount
                )
                string[stringCount - 1] = GuitarString(
                    Note(string[stringCount - 3]!!.startingNoteNum + 9),
                    fretCount
                )
            }
        }
        if (instrument == "Bass") {
            if (tuningType == "Standard") {
                for (i in 1 until stringCount) {
                    string[i] =
                        GuitarString(Note(startingNote!!.number + 5 * i), fretCount)
                }
            }
            if (tuningType == "Drop") {
                string[1] =
                    GuitarString(Note(startingNote!!.number + 7), fretCount)
                for (i in 1 until stringCount - 1) {
                    string[i + 1] = GuitarString(
                        Note(startingNote!!.number + 7 + 5 * i),
                        fretCount
                    )
                }
            }
        }
    }

    //húrok tömbjét, azok és a fretek számát megkapva fretboardot létrehozó ctor, custom hangolásnál használt
    constructor(arrayOfGuitarStrings: Array<GuitarString?>, fretCnt: Int) {
        stringCount = arrayOfGuitarStrings.size
        fretCount = fretCnt
        string = arrayOfGuitarStrings
        instrument = "mindegy" //mindegy mi a hangszer, ha a húrokból rakjuk össze, legalábbis a program számára
    }

    //minden húron keres egy paraméterként kért hangot
    fun search(h: Note?) {
        for (i in 0 until stringCount) {
            string[i]?.search(h)
        }
    }

    //minden húron keres egy paraméterként kapott skálát
    fun search(s: Scale?) {
        for (i in 0 until stringCount) {
            string[i]?.search(s!!)
        }
    }

    //érvényteleníti a korábbi kereséseket a fretboardon
    fun invalidateSearch() {
        for (i in 0 until stringCount) {
            string[i]?.invalidateSearch()
        }
    }

    //getterek
    fun getStringAtIdx(idx: Int): GuitarString? {
        return string[idx]
    }

}