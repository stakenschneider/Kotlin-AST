package parser.token

import parser.common.IdGenerator
import parser.common.KtType
import parser.common.ParserException
import parser.defineArgs
import java.util.*
import kotlin.collections.ArrayList

class KtFunctionToken(override val value: String,
                      val modifiers : MutableList<KtModifierToken> = ArrayList(),
                      val args : MutableList<KtVarToken> = ArrayList(),
                      var body : KtBodyToken? = null,
                      override val children: MutableList<KtToken> = ArrayList(),
                      override val tokenId: Int = IdGenerator.generateId()) : KtToken {

    override fun addChild(token: KtToken) {
        when(token) {
            is KtModifierToken -> modifiers.add(token)
            is KtVarToken -> args.add(token)
            is KtBodyToken -> body = token
            else -> throw ParserException("Add wrong token as child for KtFunctionToken $value")
        }
        children.add(token)
    }
    override val  type : KtType = KtType.FUNCTION

    override val  process : (List<String>) -> Unit = {}

    constructor (value: String,
                 args: String,
                 body : LinkedList<String>
    ) : this(value) {
        defineArgs(args).forEach{addChild(KtVarToken(listOf(it)))}
        addChild(KtBodyToken(body))
    }
}