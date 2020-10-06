package parser.token

import parser.common.IdGenerator
import parser.common.KeywordDictionary.Companion.FUN
import parser.common.KeywordDictionary.Companion.IMPORT
import parser.common.KeywordDictionary.Companion.OB
import parser.common.KtType
import parser.common.ParserException
import parser.defineBody
import java.util.*
import kotlin.collections.ArrayList

class KtFileToken(override val value: String,
                  val classes : MutableList<KtClassToken> = ArrayList(),
                  val functions : MutableList<KtFunctionToken> = ArrayList(),
                  val imports : MutableList<KtImportToken> = ArrayList(),
                  override val children: MutableList<KtToken> = ArrayList(),
                  override val tokenId: Int = IdGenerator.generateId()) : KtToken {
    override val  type : KtType = KtType.FILE
    override val  process : (LinkedList<String>) -> Unit = { lines ->
        val line = lines.first()
        when {
            "^$IMPORT".toRegex().containsMatchIn(line) -> addChild(KtImportToken(listOf(line)))
            "^$FUN".toRegex().containsMatchIn(line) -> {
                //TODO modifiers
                val signatureParts = "\\$OB".toRegex().split(line.replace("^$FUN ".toRegex(), ""), 2)
                lines.removeAt(0)
                val body = defineBody(lines)
                addChild(KtFunctionToken(signatureParts[0], signatureParts[1], body))
            }
        }
    }

    constructor (value: String,
                 body : LinkedList<String>
    ) : this(value) {
        processToken(body)
    }


    override fun addChild(token: KtToken) {
        when(token) {
            is KtClassToken -> classes.add(token)
            is KtFunctionToken -> functions.add(token)
            is KtImportToken -> imports.add(token)
            else -> throw ParserException("Add wrong token as child for KtFileToken $value")
        }
        children.add(token)
    }
}
