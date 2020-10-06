package parser.token

import parser.common.IdGenerator
import parser.common.KtType

class KtTypeToken(override val value : String,
                  override val children: MutableList<KtToken> = ArrayList(),
                  override val tokenId: Int = IdGenerator.generateId()) : KtToken {
    override fun addChild(token: KtToken) {}
    override val type : KtType = KtType.TYPE
    override val process: (List<String>) -> Unit = {}
}