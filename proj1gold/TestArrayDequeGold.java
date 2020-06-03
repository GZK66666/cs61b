import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    private static final int nCall = 100;
    private static String message = "";

    private void randomAdd(double random, Integer number, ArrayDequeSolution<Integer> solution, StudentArrayDeque<Integer> student) {
        if (random > 0.5) {
             solution.addFirst(number);
             student.addFirst(number);
             message += "\naddFirst(" + number + ")";
        }else {
            solution.addLast(number);
            student.addLast(number);
            message += "\naddLast(" + number + ")";
        }
    }

    private void randomRemove(double random, ArrayDequeSolution<Integer> solution, StudentArrayDeque<Integer> student) {
        Integer expected = 0;
        Integer actual = 0;

        if (random > 0.5) {
            expected = solution.removeFirst();
            actual = student.removeFirst();
            message += "\nremoveFirst()";
        }else {
            expected = solution.removeLast();
            actual = student.removeLast();
            message += "\nremoveLast()";
        }

        assertEquals(message, expected, actual);
    }

    @Test
    public void TestDeque() {
        ArrayDequeSolution<Integer> solution = new ArrayDequeSolution<>();
        StudentArrayDeque<Integer> student = new StudentArrayDeque<>();

        for (Integer i = 0; i < nCall; i++) {
            double random = StdRandom.uniform();
            if (solution.isEmpty()) {
                randomAdd(random, i, solution, student);
            }else {
                double random1 = StdRandom.uniform();
                if (random1 > 0.5) {
                    randomAdd(random, i, solution, student);
                }else {
                    randomRemove(random, solution, student);
                }
            }
        }
    }
}
