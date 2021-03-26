package story

class Book(name: String, description: String, private val information: Array<String>) : Thing(name, description){
    override fun toString(): String{
      return "\"$name\" - $description книга"
    }
    fun getInformation(): String{
        return "в ней встречается " + information.joinToString { it }
    }
}
