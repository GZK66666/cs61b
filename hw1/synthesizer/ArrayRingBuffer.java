// TODO: Make sure to make this class a part of the synthesizer package
// package <package name>;
package synthesizer;
import java.util.Iterator;

//TODO: Make sure to make this class and all of its methods public
//TODO: Make sure to make this class extend AbstractBoundedQueue<t>
public class ArrayRingBuffer<T> extends AbstractBoudedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;
    /* iterator class */
    public class BoundedQueueIterator implements Iterator<T> {
        private int ptr;

        public BoundedQueueIterator() {
            ptr = first;
        }

        @Override
        public boolean hasNext() {
            return (ptr != last);
        }

        @Override
        public T next() {
            T r = rb[ptr];
            ptr = (ptr + 1) % capacity;
            return r;
        }
    }

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from AbstractBoundedQueue, so
        //       you'll need to use this.capacity to set the capacity.
        rb = (T[]) new Object[capacity];
        this.capacity = capacity;
        first = 0;
        last = 0;
        fillCount = 0;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    @Override
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update last.
        if (isFull()) {
            //抛出异常
            throw new RuntimeException("Ring Buffer Overflow");
        }
        rb[last] = x;
        last = (last + 1) % capacity;
        fillCount += 1;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    @Override
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and update
        if (isEmpty()) {
            //抛出异常
            throw new RuntimeException("Ring Buffer Underflow");
        }
        T r = rb[first];
        first = (first + capacity - 1) % capacity;
        fillCount -= 1;
        return r;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    @Override
    public T peek() {
        // TODO: Return the first item. None of your instance variables should change.
        if (isEmpty()) {
            //抛出异常
            throw new RuntimeException("Ring Buffer Underflow");
        }
        return rb[first];
    }

    @Override
    public Iterator<T> iterator() {
        return new BoundedQueueIterator();
    }

    // TODO: When you get to part 5, implement the needed code to support iteration.
}
