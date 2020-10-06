package parser

import parser.common.KeywordDictionary.Companion.CB
import parser.common.KeywordDictionary.Companion.CBF
import parser.common.KeywordDictionary.Companion.COMMA
import parser.common.KeywordDictionary.Companion.OBF
import parser.common.ParserException
import java.util.*
import kotlin.collections.ArrayList

fun deleteExtraSpace(str : String) : String = str.replace("\\s+".toRegex(), " ")
fun deleteTabs(str : String) : String  = str.replace("\\t".toRegex(), "")
fun String.splitInLines() : List<String>  = this.lines()
fun deleteFirstSpace(str : String) : String = str.replace("^\\s".toRegex(), "")
infix fun <T> T.then(func: (T) -> T): T = func(this)
fun defineBody(body: LinkedList<String>) : LinkedList<String> {
    var deepCounter = 1
    val result = LinkedList<String>()
    for(line in body) {
        deepCounter -=  CBF.toRegex().findAll(line).count()
        deepCounter += "\\$OBF".toRegex().findAll(line).count()
        if (deepCounter<=0) {
            result.add(line)
            result.forEach { _ -> body.removeAt(0) }
            return result
        } else result.add(line)
    }
    throw ParserException("Could not define body")
}
fun defineArgs(line: String) : LinkedList<String>  {
    val args = LinkedList("\\$CB".toRegex().split(line).first().split(COMMA.toRegex()))
    return if (args.size > 1) args else LinkedList()
}


