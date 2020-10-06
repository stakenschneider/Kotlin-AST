package parser.token



import parser.common.IdGenerator
import parser.common.KeywordDictionary.Companion.IMPORT
import parser.common.KtType

class KtImportToken(override val value : String = IMPORT,
                    var statement : KtStatementToken? = null,
                    override val children: MutableList<KtToken> = ArrayList(),
                    override val tokenId: Int = IdGenerator.generateId()) : KtToken {

    override val  process : (List<String>) -> Unit = { }

    constructor (body : List<String>,
                 value: String = ""): this(value) {
        addChild(KtStatementToken("^$IMPORT ".toRegex().replace(body.first(), "")))
    }

    override fun addChild(token: KtToken) {
        statement = token as KtStatementToken
        children.add(token)
    }

    override val type : KtType = KtType.IMPORT
}