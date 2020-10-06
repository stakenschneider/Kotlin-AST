package parser.token

import parser.common.IdGenerator
import parser.common.KtType
import parser.common.ParserException

class KtDeclarationToken(override val value: String,
                         var leftHand: KtVarToken? = null,
                         var rightHand: KtRightHandExpression? = null,
                         override val children: MutableList<KtToken> = ArrayList(),
                         override val tokenId: Int = IdGenerator.generateId()) : KtExpressionToken(value, leftHand, rightHand) {

        override fun addChild(token: KtToken) {
            when(token) {
                is KtVarToken -> leftHand = token
                is KtRightHandExpression -> rightHand = token
                else -> throw ParserException("Add wrong token as child for KtDeclarationToken $value")
            }
            children.add(token)
        }

    override val  type : KtType = KtType.DECL

    constructor(value: String,
                body: String) : this(value) {

    }
}