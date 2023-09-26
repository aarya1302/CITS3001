import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.*;

public class FindPath {
    public static String[] findPath(String startWord, String endWord, String dictionary[]) {

        int swIndex = -1;
        int ewIndex = -1;


        int[] dist = new int[dictionary.length];

        int[] parent = new int[dictionary.length];
        Arrays.fill(parent, -1);
        String[] seq = new String[dictionary.length];


        int count = 0;
        // finding index of startWord and endWord
        while (count < dictionary.length) {

            if (dictionary[count].equals(startWord)) {
                swIndex = count;
            }
            if (dictionary[count].equals(endWord)) {
                ewIndex = count;
            }
            count = count + 1;
        }
        if(swIndex == -1 || ewIndex == -1){
            return [];
        }


        Queue<Integer> queue = new LinkedList<>();

        // performing BFS on the dictionary
        queue.add(swIndex);
        while (!queue.isEmpty()) {
            int src = queue.poll();
            String srcStr = dictionary[src];

            if(srcStr.equals(endWord)){
                break;
            }

            // for every letter in the current word being explored
                // create a word by switching each letter in the word to another letter in the alphabet
                    // creating a new word
                        // for every word then there will be  26^n possibilities
            for (int j = 0; j < srcStr.length(); j++) {
                char[] srcChar = srcStr.toCharArray();

                for(char c = 'A'; c < 'Z'; c++){
                    srcChar[j] = c;
                    String x = new String(srcChar);


                    for (int i = 0; i < dictionary.length; i++) {
                        if (dictionary[i].contains(x) && parent[i] == -1) {
                            queue.add(i);
                            parent[i] = src;
                        }
                    }

                }


            }

        }
        // if path not found return
        if (parent[ewIndex] == -1){
            return  new String[0];

        }
        return buildPath(parent, dictionary, swIndex, ewIndex);


    }

    public static String[] buildPath(int[] parent, String[] dict, int sWIndex, int eWIndex){
        Deque<String> deqSeq = new ArrayDeque<>();
        int i = eWIndex;
        while(i != sWIndex){
            deqSeq.addFirst(dict[i]);
            i = parent[i];
        }
        deqSeq.addFirst(dict[sWIndex]);
        String[] seq = deqSeq.toArray(new String[0]);
        return seq;
    }
    public static void main(String[] args) {
        String[] dictionary = {"AIM", "ARM", "ART", "RIM", "RAM", "RAT", "ROT", "RUM", "RUN", "BOT", "JAM", "JOB", "JAB", "LAB", "LOB", "LOG", "SUN"};
        String startWord = "AIM";
        String endWord = "BOT";

        String srcStr = "AIM";
        String[] path = findPath(startWord, endWord, dictionary);
        for(int i =0; i < path.length; i++){
            System.out.println(path[i]);
        }
    }
}
