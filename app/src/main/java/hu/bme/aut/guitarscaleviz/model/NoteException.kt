package hu.bme.aut.guitarscaleviz.model

class NoteException(override val message: String) : Exception() {

    override fun printStackTrace() {
        println(message)
    }

}