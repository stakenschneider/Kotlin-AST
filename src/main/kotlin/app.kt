import parser.*
import parser.token.KtToken

fun main() {
    val path = "/code.kt"
    val text: String = FileLoader.getResourceAsText(path)
    val name : String = FileLoader.getResourceName(path)
    val ast : KtToken = Parser(text, name).buildAst()
    Drawer().drawAST(ast)
}
