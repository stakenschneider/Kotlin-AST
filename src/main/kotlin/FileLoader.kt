object FileLoader {

    fun getResourceAsText(path: String): String {
        return object {}.javaClass.getResource(path).readText()
    }

    fun getResourceName(path: String): String {
        return (object {}.javaClass.getResource(path).file).substringAfterLast('/')
    }

}