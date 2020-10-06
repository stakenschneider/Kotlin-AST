package parser.common

object IdGenerator {
    private var id : Int = 0
    fun generateId() : Int {
        return ++id
    }
}