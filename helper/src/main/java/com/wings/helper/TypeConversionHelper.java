package com.wings.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;


/**
 * Purpose: Type Conversion and ordering the list
 * ex: StringArrayList to String
 *
 * @author HetalD
 * Created On June 12,2019
 * Modified On June 19,2019
 */

public class TypeConversionHelper {

    /**
     * Convert String ArrayList to String
     *
     * @param stringList   String ArrayList to convert into string
     * @param appendString String that will separate each string ex: ","
     * @return return StringBuilder
     */
    public static StringBuilder convertStringListToString(List<String> stringList, String appendString) {
        StringBuilder stringBuilder = new StringBuilder();
        if (stringList != null) {
            for (String s : stringList) {
                stringBuilder.append(s).append(appendString);
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }

        return stringBuilder;
    }

    /**
     * Convert Integer ArrayList to String
     *
     * @param integerList  Integer ArrayList to convert into string
     * @param appendString String that will separate each string ex: ","
     * @return return StringBuilder
     */
    public static StringBuilder convertIntegerListToString(List<Integer> integerList, String appendString) {
        StringBuilder stringBuilder = new StringBuilder();
        if (integerList != null) {
            for (Integer s : integerList) {
                stringBuilder.append(s).append(appendString);
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }

        return stringBuilder;
    }

    /**
     * Convert String to String ArrayList
     *
     * @param inputString      Input string to convert into String ArrayList
     * @param inputSplitString Input string that split input string
     * @return return ArrayList
     */
    public static List<String> convertStringToList(String inputString, String inputSplitString) {
        return new ArrayList<>(Arrays.asList(inputString.split(inputSplitString)));
    }

    /**
     * Convert Integer to Integer ArrayList
     *
     * @param inputIntegerArray Input Integer array to convert into ArrayList
     * @return return ArrayList
     */
    public static List<Integer> convertIntegerArrayToList(int[] inputIntegerArray) {

        List<Integer> integerList = new ArrayList<>();
        for (int i : inputIntegerArray) {
            integerList.add(i);
        }

        return integerList;
    }


    /**
     * Convert String to String ArrayList
     *
     * @param inputStringArray Input String array to convert into ArrayList
     * @return return ArrayList
     */
    public static List<String> convertStringArrayToList(String[] inputStringArray) {
        return Arrays.asList(inputStringArray);
    }

    /**
     * Sort string list in alphabetical manner
     *
     * @param inputList String list to order
     * @return List
     */
    public static List<String> sortStringListInAlphabetical(List<String> inputList) {
        Collections.sort(inputList);
        return inputList;
    }

    /**
     * Sort integer list in ascending order
     *
     * @param inputList integer list to order
     * @return List
     */
    public static List<Integer> sortIntegerListInAscending(List<Integer> inputList) {
        Collections.sort(inputList);
        return inputList;
    }

    /**
     * Sort list in reverse alphabetical manner
     *
     * @param inputList List to order
     * @return List
     */
    public static List<String> sortStringListInReverseInAlphabetical(List<String> inputList) {
        Collections.sort(inputList, Collections.reverseOrder());
        return inputList;
    }

    /**
     * Sort integer list in descending order
     *
     * @param inputList Integer list to order
     * @return List
     */
    public static List<Integer> sortIntegerListDescending(List<Integer> inputList) {
        Collections.sort(inputList, Collections.reverseOrder());
        return inputList;
    }

    /**
     * Sort HashMap in ascending order
     *
     * @param hashMap HashMap to order
     * @return TreeHashMap
     */

    public static TreeMap<Object, Object> sortHashMapInAscendingByKey(HashMap<Object, Object> hashMap) {
        return new TreeMap<>(hashMap);
    }
}