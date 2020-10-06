package parser.token


import parser.common.IdGenerator
import parser.common.KeywordDictionary.Companion.ASSIGN
import parser.common.KeywordDictionary.Companion.IN
import parser.common.KeywordDictionary.Companion.SPACE
import parser.common.KeywordDictionary.Companion.opRegEx
import parser.common.KtType


open class KtExpressionToken(override val value: String,
                        var left : KtRightHandExpression? = null,
                        var right : KtRightHandExpression? = null,
                             override val children: MutableList<KtToken> = ArrayList(),
                             override val tokenId: Int = IdGenerator.generateId()) : KtToken, KtRightHandExpression {
    override fun addChild(token: KtToken) {
        if(left == null) left = token as KtRightHandExpression
        else right = token as KtRightHandExpression
        children.add(token)
    }


    constructor(value : String,
                body : String
    ) : this(value) {
        when (value) {
            ASSIGN -> processAssign(ASSIGN.toRegex().split(body))
            IN -> processIn(" $IN ".toRegex().split(body, 2))
            else -> processRightExpression(body.split(opRegEx, 2))
        }
    }

    override val process : (List<String>) -> Unit = {}

    private fun processAssign(assign : List<String>) {
        addChild(KtVarToken(listOf(assign[0])))
        if (opRegEx.containsMatchIn(assign[1])) {
            addChild(KtExpressionToken(opRegEx.find(assign[1])!!.value, assign[1]))
        } else {
            addChild(defineVarOrStatement(assign[1]))
        }

    }

    private fun processIn(inExp : List<String>) {
        addChild(defineVarOrStatement(inExp[0]))
        addChild(defineVarOrStatement(inExp[1]))
    }

    private fun processRightExpression(parts : List<String>) {
        addChild(defineVarOrStatement(parts[0]))
        addChild(defineVarOrStatement(parts[1]))
    }

    private fun defineVarOrStatement(str : String) : KtToken {
        val token = str.replace(SPACE, "")
        return if (token.contains("'") || token.toIntOrNull() != null)
            KtStatementToken(token)
        else KtIdToken(token)
    }

    override val type : KtType = KtType.EXPRESSION
}