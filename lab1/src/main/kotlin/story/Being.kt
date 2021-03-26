package story

class Being(var name: String) {
    var lookAt: Thing? = null

    fun lookAt(thing: Thing, description: String): ActionResult {
        thing.isSomeoneLook = true
        lookAt = thing
        return ActionResult(description, "показалась ему занимательной")
    }

    fun stopLootAt(thing: Thing, description: String): ActionResult {
        thing.isSomeoneLook = false
        lookAt = null
        return ActionResult(description, "")
    }

    data class ActionResult(val actionDescription: String, val resultDescription: String)
}