import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class BTreeTest {
    private var bTree: BTree<Int> = BTree()

    @BeforeEach
    fun initTable() {
        bTree = BTree()
    }

    @Test
    fun `sort the tree`() {
        val values = intArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8)
        for (v in values) {
            bTree.put(v)
        }
        assertEquals( "0, 1, 2, 3, 4, 5, 6, 7, 8, ", bTree.toString())
    }

    @Test
    fun `put and get the max value from the tree`() {
        val values = intArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8)
        for (v in values) {
            bTree.put(v)
        }
        for (v in values) {
            assertEquals(v, bTree.get(v))
        }
        assertEquals( "0, 1, 2, 3, 4, 5, 6, 7, 8, ", bTree.toString())
        assertEquals("[1, 3, 5](4)\n" +
                    "[0](0)[2](0)[4](0)[6, 7, 8](0)\n",
            bTree.toStringByBuckets())
    }

    @Test
    fun `put and get the min value from the tree`() {
        val values = intArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8)
        for (v in values) {
            bTree.put(v)
        }
        bTree.put(-1)
        for (v in values) {
            assertEquals(v, bTree.get(v))
        }
        assertEquals("-1, 0, 1, 2, 3, 4, 5, 6, 7, 8, ", bTree.toString())
        assertEquals("[1, 3, 5](4)\n" +
                    "[-1, 0](0)[2](0)[4](0)[6, 7, 8](0)\n",
            bTree.toStringByBuckets())
    }

    @Test
    fun `recurrent split`() {
        val values = intArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8)
        for (v in values) {
            bTree.put(v)
        }
        assertEquals("0, 1, 2, 3, 4, 5, 6, 7, 8, ", bTree.toString())
        assertEquals("[1, 3, 5](4)\n" +
                    "[0](0)[2](0)[4](0)[6, 7, 8](0)\n",
            bTree.toStringByBuckets())

        bTree.put(9)
        assertEquals("0, 1, 2, 3, 4, 5, 6, 7, 8, 9, ", bTree.toString())
        assertEquals("[3](2)\n" +
                    "[1](2)[5, 7](3)\n" +
                    "[0](0)[2](0)[4](0)[6](0)[8, 9](0)\n",
            bTree.toStringByBuckets())
    }

    @Test
    fun `put and get the middle value from the tree`() {
        val values = intArrayOf(0, 1, 2, 3, 5, 6, 7, 8, 9)
        for (v in values) {
            bTree.put(v)
        }
        bTree.put(4)
        for (v in values) {
            assertEquals(v, bTree.get(v))
        }
        assertEquals("0, 1, 2, 3, 4, 5, 6, 7, 8, 9, ", bTree.toString())
        assertEquals("[1, 3, 6](4)\n" +
                    "[0](0)[2](0)[4, 5](0)[7, 8, 9](0)\n",
            bTree.toStringByBuckets())
    }

    @Test
    fun `is the tree empty`() {
        val values = intArrayOf(0)
        assertTrue(bTree.isEmpty())
        for (v in values) {
            bTree.put(v)
        }
        assertFalse(bTree.isEmpty())
    }

    @Test
    fun `remove root from the tree`() {
        val values = intArrayOf(0)
        for (v in values) {
            bTree.put(v)
        }
        bTree.remove(0)
        assertTrue(bTree.isEmpty())
    }

    @Test
    fun `remove not single list from the tree`() {
        val values = intArrayOf(0, 1, 2)
        for (v in values) {
            bTree.put(v)
        }
        bTree.remove(1)
        assertEquals( "0, 2, ", bTree.toString(),)
        assertEquals("[0, 2](0)\n", bTree.toStringByBuckets())
    }

    @Test
    fun `remove single list (prev Node) from the tree`() {
        val values = intArrayOf(1, 2, 3, 4, 0)
        for (v in values) {
            bTree.put(v)
        }
        bTree.remove(4)
        bTree.remove(3)
        assertEquals( "0, 1, 2, ", bTree.toString())
        assertEquals("[1](2)\n" +
                    "[0](0)[2](0)\n",
            bTree.toStringByBuckets())
    }

    @Test
    fun `remove single list (next Node) from the tree`() {
        val values = intArrayOf(0, 1, 2, 3)
        for (v in values) {
            bTree.put(v)
        }
        bTree.remove(0)
        assertEquals( "1, 2, 3, ", bTree.toString())
        assertEquals("[2](2)\n" +
                    "[1](0)[3](0)\n",
            bTree.toStringByBuckets())
    }

    @Test
    fun `remove single list (not next and not prev Node) from the tree`() {
        val values = intArrayOf(0, 1, 2, 3, 4, 5, 6, 7)
        for (v in values) {
            bTree.put(v)
        }
        bTree.remove(2)
        assertEquals("0, 1, 3, 4, 5, 6, 7, ", bTree.toString())
        assertEquals("[3, 5](3)\n" +
                    "[0, 1](0)[4](0)[6, 7](0)\n",
            bTree.toStringByBuckets())
    }

}