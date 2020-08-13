import java.util.LinkedList;
import java.util.Queue;

/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    private static int width;

    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        // TODO: Implement LSD Sort
        int width = 0;
        for (String string : asciis) {
            if (string.length() > width) {
                width = string.length();
            }
        }

        for (int i = 0; i < width; i++) {
            sortHelperLSD(asciis, i);
        }

        return asciis;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        Queue<String>[] queues = new Queue[256];
        for (int i = 0; i < 256; i++) {
            queues[i] = new LinkedList<String>();
        }

        for (String string : asciis) {
            if (string.length() - index - 1 < 0) {
                queues[255].add(string);
            }else {
                char ch = string.charAt(string.length() - index - 1);
                int i = (int) ch;
                queues[i].add(string);
            }
        }

        int i = 0;
        for (Queue queue : queues) {
            while (!queue.isEmpty()) {
                asciis[i++] = (String) queue.remove();
            }
        }

    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        if (index >= width) {
            return;
        }

        Queue<String>[] queues = new Queue[256];
        for (int i = 0; i < 256; i++) {
            queues[i] = new LinkedList<>();
        }

        for (int i = start; i < end; i++) {
            if (index - width + asciis[i].length() < 0) {
                queues[255].add(asciis[i]);
            }else {
                char ch = asciis[i].charAt(index - width + asciis[i].length());
                int i1 = (int) ch;
                queues[i1].add(asciis[i]);
            }
        }

        int i = start;
        for (Queue queue : queues) {
            int start1, end1;
            start1 = i;
            while (!queue.isEmpty()) {
                asciis[i++] = (String) queue.remove();
            }
            end1 = i;

            if (end1 - start1 > 1) {
                sortHelperMSD(asciis, start1, end1, index + 1);
            }

            if (i == end)
                break;
        }

    }

    public static void main(String[] args) {
        String[] strings = {"cba", "bcad", "bac", "abc"};
        width = 4;
        sortHelperMSD(strings, 0, 4, 0);
//        sort(strings);
        for (String string : strings) {
            System.out.println(string);
        }
    }
}
