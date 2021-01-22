package hu.bme.aut.guitarscaleviz.model

import java.io.Serializable

class Note : Serializable {
    //getterek és setterek
    var number = 0
        private set
    var name: String? = null
        private set
    var inScale: Boolean
    var isRootNote: Boolean

    /*Sztringes ctor
     * Ha a megadott sztring első karaktere kicsi, a program nagybetűsíti a konzisztens megjelenítés miatt,
     * viszont mindkettőt nem, mivel a félhangok jele flat módban szigorúan kis b betű.
     */
    constructor(nameString: String) {
        var namestr = nameString
        inScale = false
        isRootNote = false
        if (namestr.length == 0 || namestr.length>2) {
            throw NoteException("Non-existent note!")
        }
        //lekezeli a félhangokat, a hang betűjének ASCII kódjával kicsit trükközve, emiatt az Ab és a D# hangokat külön lekezeli
        if (namestr.length == 2) {
            if (flatinsteadofsharp == false) {
                if (namestr[1] == 'b') {
                   if (namestr[0] == 'A') {
                       namestr = "G#"
                    } else {
                       ((namestr[0].toInt() - 1).toChar()).toString()+"#"
                    }
                }
            } else {
                if (namestr[1] == '#') {
                    if (namestr[0] == 'G') {
                        namestr = "Ab"
                    } else {
                        ((namestr[0].toInt() + 1).toChar()).toString()+"b"
                    }
                }
            }
        }
        if (Character.isUpperCase(namestr[0])) {
            name = namestr
        } else {
            var c = namestr[0]
            c = Character.toUpperCase(c)
            val s1 = c.toString()
            name = if (namestr.length == 1) {
                s1
            } else {
                val s2 = namestr[1].toString()
                s1 + s2
            }
        }
        when (name) {
            "E" -> number = 0
            "F" -> number = 1
            "F#", "Gb" -> number = 2
            "G" -> number = 3
            "G#", "Ab" -> number = 4
            "A" -> number = 5
            "A#", "Bb" -> number = 6
            "B" -> number = 7
            "C" -> number = 8
            "C#", "Db" -> number = 9
            "D" -> number = 10
            "D#", "Eb" -> number = 11
            else -> {
                number = -1
                throw NoteException("Non-existent note!")
            }
        }
    }

    //számos ctor
    constructor(num: Int) {
        inScale = false
        isRootNote = false
        this.number = num % 12
        when (this.number) {
            0 -> name = "E"
            1 -> name = "F"
            2 -> name = if (flatinsteadofsharp) {
                "Gb"
            } else {
                "F#"
            }
            3 -> name = "G"
            4 -> name = if (flatinsteadofsharp) {
                "Ab"
            } else {
                "G#"
            }
            5 -> name = "A"
            6 -> name = if (flatinsteadofsharp) {
                "Bb"
            } else {
                "A#"
            }
            7 -> name = "B"
            8 -> name = "C"
            9 -> name = if (flatinsteadofsharp) {
                "Db"
            } else {
                "C#"
            }
            10 -> name = "D"
            11 -> name = if (flatinsteadofsharp) {
                "Eb"
            } else {
                "D#"
            }
            else -> {
                name = "hiba"
                throw NoteException("Non-existent note!")
            }
        }
    }

    companion object {
        private var flatinsteadofsharp = false

        //static függvény, ami megváltoztatja, hogy Flat(b) vagy Sharp(#) jelölésű félhangok legyenek.
        fun flatInstead(b: Boolean) {
            flatinsteadofsharp = b
        }
    }
}