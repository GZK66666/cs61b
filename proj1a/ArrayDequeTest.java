import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayDequeTest {
    @Test
    public void testIsEmpty(){
        ArrayDeque<Character> arrayDeque = new ArrayDeque<>();
        assertTrue(arrayDeque.isEmpty());
        arrayDeque.addFirst('A');
        assertFalse(arrayDeque.isEmpty());
    }

    @Test
    public void testAddAndget(){
        ArrayDeque<Integer> arrayDeque = new ArrayDeque<>();

        for (int i = 0; i < 16; i++){
            arrayDeque.addLast(i);
        }

        for (int i = 0; i < 16; i++){
            assertEquals((Integer) i, arrayDeque.get(i));
        }
    }
}
