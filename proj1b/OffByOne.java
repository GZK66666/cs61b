import org.junit.Test;
import static org.junit.Assert.*;

public class OffByOne implements CharacterComparator{
    @Override
    public boolean equalChars(char x, char y){
        return (Math.abs(x-y) == 1);
    }

    @Test
    public void testequalChars(){
        OffByOne offByOne = new OffByOne();
        assertTrue(offByOne.equalChars('a', 'b'));
        assertFalse(offByOne.equalChars('a', 'B'));
        assertFalse(offByOne.equalChars('a', 'k'));
    }
}
