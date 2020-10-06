package parser

import parser.token.KtFileToken
import parser.token.KtToken
import java.util.*

class Parser(private var text: String, private val fileName: String) {

    private fun prepareText() =
        text.splitInLines()
            .map { it then ::deleteExtraSpace then ::deleteFirstSpace then ::deleteTabs }
            .filter { it.isNotBlank() }

    fun buildAst(): KtToken {
        val formattedLines: List<String> = prepareText()
        return KtFileToken(fileName, LinkedList(formattedLines))
    }
}
