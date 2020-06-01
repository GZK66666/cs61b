public class Palindrome {

    public Deque<Character> wordToDeque(String word){
        Deque<Character> deque = new LinkedListDeque<>();

        for(int i = 0; i < word.length(); i++){
            deque.addLast(word.charAt(i));
        }

        return deque;
    }

    public boolean isPalindrome(String word){
        word = word.toLowerCase();
        Deque deque = wordToDeque(word);

        while(deque.size() >= 2){
            Character first, last;
            first = (Character) deque.removeFirst();
            last = (Character) deque.removeLast();
            if(!first.equals(last)){
                return false;
            }
        }

        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc){
        Deque deque = wordToDeque(word);

        while(deque.size() >= 2){
            Character first, last;
            first = (Character) deque.removeFirst();
            last = (Character) deque.removeLast();
            if(!cc.equalChars(first, last)){
                return false;
            }
        }

        return true;
    }
}
