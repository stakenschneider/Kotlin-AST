package parser.token

import parser.common.IdGenerator
import parser.common.KeywordDictionary
import parser.common.KeywordDictionary.Companion.CB
import parser.common.KeywordDictionary.Companion.ELSE
import parser.common.KeywordDictionary.Companion.IF
import parser.common.KeywordDictionary.Companion.OBF
import parser.common.KtType
import parser.common.ParserException
import parser.defineBody
import java.util.*
import kotlin.collections.ArrayList

class KtElseToken(override val value : String,
                var elseBody : KtBodyToken? = null,
                override val children: MutableList<KtToken> = ArrayList(),
                override val tokenId: Int = IdGenerator.generateId()) : KtToken {
    override fun addChild(token: KtToken) {
        when(token) {
            is KtBodyToken -> elseBody = token
            else -> throw ParserException("Add wrong token as child for KtElseToken $value")
        }
        children.add(token)
    }
    override val process: (LinkedList<String>) -> Unit = {}
    constructor(body : LinkedList<String>, value : String = ELSE) : this(value) {
        addChild(KtBodyToken(defineBody(body)))
    }
    override val type : KtType = KtType.ELSE
}