import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph
import guru.nidi.graphviz.parse.Parser;
import parser.common.KeywordDictionary.Companion.ARROW
import parser.common.KeywordDictionary.Companion.ASSIGN
import parser.common.KeywordDictionary.Companion.CBF
import parser.common.KeywordDictionary.Companion.CBS
import parser.common.KeywordDictionary.Companion.LABEL
import parser.common.KeywordDictionary.Companion.NEXT_LINE
import parser.common.KeywordDictionary.Companion.OBS
import parser.common.KeywordDictionary.Companion.SEMICOLON
import parser.common.KeywordDictionary.Companion.SPACE
import parser.common.KeywordDictionary.Companion.STRING
import java.io.IOException
import java.io.File
import parser.common.KeywordDictionary.Companion.DIGRAPH
import parser.common.KeywordDictionary.Companion.OBF
import parser.token.KtToken
import java.nio.file.Paths
import java.nio.file.Files

class Drawer(private val dots : MutableList<Pair<Int, List<String>>> = ArrayList(),
             private val links: MutableList<Pair<String, String>> = ArrayList()) {

    private val FILE_LOCATION: String = "src/main/resources/ast.dot"
    private val OUTPUT_LOCATION: String = "src/main/resources/output.svg"

    fun drawAST(ast: KtToken) {
        this.prepareDrawingData(ast)
        this.generateASTDotFile()
        try {
            val graph : MutableGraph = Parser().read(File(FILE_LOCATION))
            Graphviz
                .fromGraph(graph.setDirected(true))
                .width(2500)
                .height(2500)
                .render(Format.SVG)
                .toFile(File(OUTPUT_LOCATION))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun prepareDrawingData(node: KtToken) {
        val dotId : Int = node.tokenId
        val label : MutableList<String> = mutableListOf(node.type.name)
        label.add(node.value)
        if (node.children.isNotEmpty()) {
            node.children.forEach { child ->
                links.add(Pair(dotId.toString(), child.tokenId.toString()))
                this.prepareDrawingData(child)
            }
        }
        dots.add(Pair(dotId, label))
    }

    private fun generateASTDotFile() {
        val sb = StringBuilder()
        sb.append(DIGRAPH)
            .append(OBF)
            .append(NEXT_LINE)
            .append(this.generateDeclareString())
            .append(this.generateLinksString())
            .append(NEXT_LINE)
            .append(CBF)
        try {
            Files.write(Paths.get(FILE_LOCATION), sb.toString().toByteArray())
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun generateDeclareString(): String {
        val sb = StringBuilder()

        dots.forEach { dot ->
            sb.append(dot.first)
                .append(OBS)
                .append(LABEL)
                .append(ASSIGN)
                .append(STRING)
                .append((SPACE + dot.second.joinToString("\n")))
                .append(STRING)
                .append(CBS)
                .append(SEMICOLON)
                .append(NEXT_LINE)
        }

        return sb.toString()
    }

    private fun generateLinksString(): String {
        val sb = StringBuilder()

        links.forEach { link ->
            sb.append(link.first)
                .append(SPACE)
                .append(ARROW)
                .append(SPACE)
                .append(link.second)
                .append(NEXT_LINE)
        }

        return sb.toString()
    }
}
