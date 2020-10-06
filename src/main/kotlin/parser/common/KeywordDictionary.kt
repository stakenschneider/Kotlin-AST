package parser.common

class KeywordDictionary {
    companion object  {
        const val PLUS = "+"
        const val MINUS = "-"
        const val INCR = "++"
        const val DECR = "--"
        const val MULT = "*"
        const val DIV = "/"
        const val PROC = "%"
        const val FOR = "for"
        const val IF = "if"
        const val ELSE = "else"
        const val RETURN = "return"
        const val BREAK = "break"
        const val CONTINUE = "continue"
        const val FUN = "fun"
        const val CLASS = "class"
        const val IMPORT = "import"
        const val OB = "("
        const val CB = ")"
        const val OBF = "{"
        const val CBF = "}"
        const val OBS = "["
        const val CBS = "]"
        const val STRING = "\""
        const val COLON = ":"
        const val COMMA = ","
        const val SEMICOLON = ";"
        const val EQ = "=="
        const val NEQ = "!="
        const val GREATER = ">"
        const val LESSER = "<"
        const val GT = ">="
        const val LT = "<="
        const val ASSIGN = "="
        const val VAR = "var"
        const val VAL = "val"
        const val UNDEFINED = "undefined"
        const val IN = "in"
        const val SPACE = " "
        const val ARROW ="->"
        const val NEXT_LINE = "\n"
        const val LABEL = "label"
        const val DIGRAPH = "digraph"

        val opRegEx = "$GT|$LT|[$MULT$PLUS$MINUS$DIV$PROC$LESSER$GREATER$EQ$NEQ]".toRegex()
        val controlFlowKeywords = "$RETURN|$BREAK|$CONTINUE".toRegex()
        val breakKey = "$BREAK|$RETURN".toRegex()
    }
}