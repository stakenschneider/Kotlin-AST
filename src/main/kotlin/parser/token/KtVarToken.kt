package parser.token

import parser.common.IdGenerator
import parser.common.KeywordDictionary.Companion.COLON
import parser.common.KeywordDictionary.Companion.UNDEFINED
import parser.common.KeywordDictionary.Companion.VAL
import parser.common.KeywordDictionary.Companion.VAR
import parser.common.KtType
import parser.common.ParserException

class KtVarToken(override val value: String,
                  var typeToken : KtTypeToken? = null,
                  var variableType: KtVariableTypeToken? = null,
                  var id : KtIdToken? = null,
                 override val children: MutableList<KtToken> = ArrayList(),
                 override val tokenId: Int = IdGenerator.generateId()) : KtToken, KtRightHandExpression{
    override val  type : KtType = KtType.VARIABLE

    override fun addChild(token: KtToken) {
        when(token) {
            is KtTypeToken -> typeToken = token
            is KtIdToken -> id = token
            is KtVariableTypeToken -> variableType = token
            else -> throw ParserException("Add wrong token as child for KtVarToken $value")
        }
        children.add(token)
    }

    constructor (body : List<String>,
        value: String = "variable") : this(value) {
        val line = body.first()
        val argument = COLON.toRegex().split(line,  2)
        if (VAL.toRegex().containsMatchIn(argument[0])) {
            addChild(KtVariableTypeToken(VAL))
            addChild(KtIdToken(argument[0].replace(VAL, "")))
        }
        if (VAR.toRegex().containsMatchIn(argument[0])){
            addChild(KtVariableTypeToken(VAR))
            addChild(KtIdToken(argument[0].replace(VAR, "")))
        }
         addChild(KtTypeToken(if (argument.size == 2) argument[0] else UNDEFINED))
    }

    override val process : (List<String>) -> Unit = {}
}