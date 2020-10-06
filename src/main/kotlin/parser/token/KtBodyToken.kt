package parser.token

import parser.common.IdGenerator
import parser.common.KeywordDictionary
import parser.common.KeywordDictionary.Companion.ASSIGN
import parser.common.KeywordDictionary.Companion.FOR
import parser.common.KeywordDictionary.Companion.IF
import parser.common.KeywordDictionary.Companion.OB
import parser.common.KeywordDictionary.Companion.RETURN
import parser.common.KtType
import parser.common.ParserException
import parser.defineArgs
import java.util.*
import kotlin.collections.ArrayList

class KtBodyToken(override val value: String,
                  val expressions : MutableList<KtExpressionToken> = ArrayList(),
                  val ifTokens : MutableList<KtIfToken> = ArrayList(),
                  val forTokens : MutableList<KtForToken> = ArrayList(),
                  val returnTokens : MutableList<KtReturnToken> = ArrayList(),
                  override val children: MutableList<KtToken> = ArrayList(),
                  override val tokenId: Int = IdGenerator.generateId()) : KtToken {
    override fun addChild(token: KtToken) {
        when(token) {
            is KtExpressionToken -> expressions.add(token)
            is KtIfToken -> ifTokens.add(token)
            is KtForToken -> forTokens.add(token)
            is KtReturnToken -> returnTokens.add(token)
            else -> throw ParserException("Add wrong token as child for KtBodyToken $value")
        }
        children.add(token)
    }

    constructor(body : LinkedList<String>,
                value : String = ""
    ) : this(value) {
        processToken(body)
    }

    override val process : (LinkedList<String>) -> Unit = { lines ->
        val line = lines.first()
        when {
            "[^$ASSIGN]$ASSIGN[^$ASSIGN]".toRegex().containsMatchIn(line) -> addChild(KtExpressionToken(ASSIGN, line))
            "^$FOR".toRegex().containsMatchIn(line) -> {
                val deletedSignature = lines.removeAt(0)
                addChild(KtForToken(".+\\$OB".toRegex().replace(line, ""), lines))
                lines.push(deletedSignature)
            }
            "^$IF".toRegex().containsMatchIn(line) -> {
                val deletedSignature = lines.removeAt(0)
                addChild(KtIfToken(".+\\$OB".toRegex().replace(line, ""), lines))
                lines.push(deletedSignature)
            }
            KeywordDictionary.controlFlowKeywords.containsMatchIn(line) -> addChild(KtReturnToken(
                listOf(KeywordDictionary.controlFlowKeywords.replace(line, "")),
                KeywordDictionary.controlFlowKeywords.find(line)!!.value
            ))
        }
    }

    override val  type : KtType = KtType.BODY
}