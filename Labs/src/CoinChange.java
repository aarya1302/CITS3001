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

    public static void main(String[] args){
        int[] denominations = {12,15,17,231,421,527,1};
        Map<Integer,Integer> change = greedyChange(2134, denominations);
        Integer[] keys = change.keySet().toArray(new Integer[0]);
        Arrays.sort(keys);
        for(Integer i: keys)
            System.out.println(i+":"+change.get(i));

    }
}

