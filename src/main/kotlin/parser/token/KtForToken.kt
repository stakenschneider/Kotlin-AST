package parser.token

import parser.common.IdGenerator
import parser.common.KeywordDictionary.Companion.CB
import parser.common.KeywordDictionary.Companion.FOR
import parser.common.KeywordDictionary.Companion.IN
import parser.common.KeywordDictionary.Companion.OBF
import parser.common.KtType
import parser.common.ParserException
import parser.defineBody
import java.util.*
import kotlin.collections.ArrayList

class KtForToken(override val value : String,
                 var condition : KtExpressionToken? = null,
                 var forBody : KtBodyToken? = null,
                 override val children: MutableList<KtToken> = ArrayList(),
                 override val tokenId: Int = IdGenerator.generateId()) : KtToken {
    override fun addChild(token: KtToken) {
        when(token) {
            is KtExpressionToken -> condition = token
            is KtBodyToken -> forBody = token
            else -> throw ParserException("Add wrong token as child for KtIfToken $value")
        }
        children.add(token)
    }
    override val type : KtType = KtType.FOR
    override val process: (MutableList<String>) -> Unit = {}
    constructor(cond : String, body : LinkedList<String>, value : String = FOR) : this(value) {
        addChild(KtExpressionToken(IN, "\\$CB|\\$OBF".toRegex().replace(cond, "")))
        addChild(KtBodyToken(defineBody(body)))
    }
}