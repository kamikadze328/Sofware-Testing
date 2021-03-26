import org.junit.jupiter.api.Test
import story.Being
import story.Book

import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue


class StoryTest {

    @Test
    fun `creating new book`(){
        val name = "Путеводитель по Галактике для автостопщиков"
        val description = "очень неоднородная"
        val theHitchhikersGuide = Book(name, description, arrayOf("информация"))

        assertEquals(theHitchhikersGuide.name, name)
        assertEquals(theHitchhikersGuide.description, description)
        assertFalse(theHitchhikersGuide.isSomeoneLook)
    }

    @Test
    fun `creating new human being`(){
        val name = "редактору"
        val editor = Being(name)

        assertNull(editor.lookAt)
        assertEquals(editor.name, name)
    }

    @Test
    fun `looking at book`(){
        val theHitchhikersGuide = Book("Путеводитель по Галактике для автостопщиков", "очень неоднородная", arrayOf("информация"))
        val editor = Being("редактору")
        val description = "которая в какой-то момент просто попалась на глаза"
        val lookingResult = editor.lookAt(theHitchhikersGuide, description)

        assertEquals(lookingResult.actionDescription, description)
        assertEquals(editor.lookAt, theHitchhikersGuide)
        assertTrue(theHitchhikersGuide.isSomeoneLook)
    }

    @Test
    fun `check and print script`() {
        val theHitchhikersGuide = Book("Путеводитель по Галактике для автостопщиков", "очень неоднородная", arrayOf("информация"))
        println(theHitchhikersGuide)
        println(theHitchhikersGuide.getInformation())

        assertFalse(theHitchhikersGuide.isSomeoneLook)

        val editor = Being("редактору")
        val actionDescription = "которая в какой-то момент просто попалась на глаза"
        val lookingResult = editor.lookAt(theHitchhikersGuide, actionDescription)
        println(lookingResult.actionDescription)
        println(lookingResult.resultDescription)

        assertEquals(lookingResult.actionDescription, actionDescription)
        assertEquals(editor.lookAt, theHitchhikersGuide)
        assertTrue(theHitchhikersGuide.isSomeoneLook)
    }
}