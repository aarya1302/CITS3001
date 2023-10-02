import java.util.*;

/* Question 1 */
//Write a java method to apply a greedy algorithm to calculate coin change. The function should be called greedyChange, and will take an amount (a non-negative int) and an array of unique denominations d1,...dn (positive ints) as parameters.
//
//        It should then return a Map from int to int showing how much of each denomination to use to get the amount. The denominations will be such that the greedy algorithm will be guaranteed to give an optimal solution. Note, if a denomination is not required in the solution, it should be included with, and map to 0.
//
//        java.util.* is automatically imported so all classes in java.util are available for you to use.
//

/* Greedy Algorithm which returns a Map for least number of denominations required for a certain amount
    Greedy Step : Take the highest denomination which is less than or equal to the value
    Explanation: Using insert Sort, sort the array of denominations ascending order
                 Using for loop iterate through the denomination from the last to first value; last value is highest
                 Add each denomination to map
                 Increment denomination value in map while it is less than or equal to value
    Performance: Optimal for some denominations
                 Time Complexity: Insert sort : n2; for loop is n;
                                  therefore O(n^2);
 */

public class CoinChange{
    public static int[] insertSort(int arr[]) {
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
        return arr;
    }
    public static Map<Integer,Integer> greedyChange(int amount, int[] denominations){
        //fill in code here
        Map<Integer, Integer> change = new HashMap<>();
        int[] sortedDenom = insertSort(denominations);
        int value = amount;
        for (int i = sortedDenom.length-1; i >= 0 ; i--){
            int key = sortedDenom[i];
            change.put(key, 0);
            while (value >= key){
                int currVal = change.get(key);
                currVal++;
                change.put(key, currVal);
                value -= key;
            }
        }
        return change;

    }
    public static boolean loopInvariant(int value, Map<Integer, Integer> change, int amount) {
        if(value < 0) return false; // value should be non-negative

        int totalInMap = 0;
        for(int key: change.keySet()) {
            if(change.get(key) < 0) return false; // counts should be non-negative
            totalInMap += key * change.get(key); // Calculate the total amount in change map
        }

        return totalInMap + value == amount; // The total amount in the map plus the remaining value should be equal to the original amount
    }

    /**
     * Apply the exact algorithm to calculate coin change.
     * @param amount a non-negative integer which is required to be made up.
     * @param denominations the available coin types (unique positive integers)
     * @return a map of each denomination to the number of times it is used in the solution.
     * **/

    /**
     * For this part of the question I used dynamic programming to populate at
     */
    public static Map<Integer,Integer> exactChange(int amount, int[] denominations){
        //fill in code here
        Map<Integer, Integer> change  = new HashMap<>();
        denominations = insertSort(denominations);

        // loop to fill up the Map
        for(int i = 0; i < denominations.length; i++){
            change.put(denominations[i], 0);
        }
        // dp array to store lengths of change
        int[][] lenChange = new int[denominations.length+1][amount+1];

        // loop to fill up dynamic programming array

        for(int d = 0; d < lenChange.length; d++){
            for(int v =0; v <= amount; v++){

                if(d>0){
                    if(v >= denominations[d-1] && v>0){
                        int len_incl = lenChange[d][v-denominations[d -1]] + 1;

                        int len_excl = lenChange[d-1][v];

                        if(len_incl > len_excl && len_excl>0){
                            lenChange[d][v] = len_excl;
                        }else{
                            lenChange[d][v] = len_incl;
                        }

                    }else if (v < denominations[d -1 ] && v > 0){
                        lenChange[d][v] = lenChange[d-1][v];
                    }
                }



            }
        }

        int d = lenChange.length -1;
        int v = amount;
        while(d > 0){
            if(lenChange[d][v] != lenChange[d -1][v]){
                change.put(denominations[d -1], change.get(denominations[d -1]) +1);
                v = v - denominations[d-1];
            }else{
                d = d -1;
            }

        }



        return change;

    }
    public static void main(String[] args){
        int[] denominations = {1,2,3};
        Map<Integer,Integer> change = exactChange(5, denominations);
        Integer[] keys = change.keySet().toArray(new Integer[0]);
        Arrays.sort(keys);
        for(Integer i: keys)
            System.out.println(i+":"+change.get(i));

    }
}

