package parser.token

import parser.common.IdGenerator
import parser.common.KtType

class KtClassToken(override val value: String,
                   val modifiers : MutableList<KtModifierToken>,
                   override val children: MutableList<KtToken> = ArrayList(),
                   override val tokenId: Int = IdGenerator.generateId()) : KtToken {
    override val  type : KtType = KtType.CLASS
    override fun addChild(token: KtToken) {
        TODO("not implemented")
        //To change body of created functions use File | Settings | File Templates.
    }
    override val process: (List<String>) -> Unit = {}
}
