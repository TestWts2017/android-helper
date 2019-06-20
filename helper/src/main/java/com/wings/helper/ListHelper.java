package com.wings.helper;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Purpose: Add, remove & update operation on list types
 *
 * @author HetalD
 * Created On June 14,2019
 * Modified On June 20,2019
 */

public class ListHelper {
    /**
     * Add item into list
     *
     * @param addList   List to perform add operation
     * @param itemToAdd Item to add
     * @return ArrayList
     */
    public static ArrayList<Object> addItemToList(ArrayList<Object> addList, Object itemToAdd) {
        if (itemToAdd != null) {
            addList.add(itemToAdd);
        }
        return addList;
    }

    /**
     * Remove item from list by item name
     *
     * @param removeList   List to perform remove operation
     * @param itemToRemove Item to remove
     * @return ArrayList
     */
    public static ArrayList<Object> removeListByItemAction(ArrayList<Object> removeList, Object itemToRemove) {
        if (removeList != null && removeList.size() > 0) {
            removeList.remove(itemToRemove);
        }
        return removeList;
    }


    /**
     * Remove item from list by item index
     *
     * @param removeList List to perform remove operation
     * @param index      Index to remove data
     * @return ArrayList
     */
    public static ArrayList<Object> removeListByIndexAction(ArrayList<Object> removeList, int index) {
        if (removeList != null && removeList.size() > 0) {
            if (index < removeList.size()) {
                removeList.remove(index);
            }
        }
        return removeList;
    }

    /**
     * Update List by item index
     *
     * @param updateList List to perform update operation
     * @param newItem    new item to update
     * @param oldIndex   old index of the list
     * @return ArrayList
     */
    public static ArrayList<Object> updateListByIndexAction(ArrayList<Object> updateList, Object newItem, int oldIndex) {
        if (updateList != null && updateList.size() > 0) {
            if (updateList.size() > oldIndex) {
                updateList.remove(oldIndex);
                updateList.add(oldIndex, newItem);
            }
        }
        return updateList;
    }

    /**
     * Update List by item name
     *
     * @param updateList List to perform update operation
     * @param newItem    new item to update
     * @param oldItem    old item of the list
     * @return ArrayList
     */

    public static ArrayList<Object> updateListByItemAction(ArrayList<Object> updateList, Object newItem, Object oldItem) {
        if (updateList != null && updateList.size() > 0) {
            int oldItemIndex = updateList.indexOf(oldItem);
            updateList.remove(oldItem);
            updateList.add(oldItemIndex, newItem);
        }
        return updateList;
    }

    /**
     * Get item from the list
     *
     * @param arrayList List to perform retrieve operation
     * @param findItem  Item to retrieve - Object type should same as in the array list
     * @return ArrayList
     */
    public static ArrayList<Object> getList(ArrayList<Object> arrayList, Object findItem) {

        ArrayList<Object> finalList = new ArrayList<>();

        if (arrayList != null) {
            for (Object i : arrayList) {
                ArrayList<Object> list = new ArrayList<>();
                if (i.toString().toLowerCase().contains(findItem.toString())) {
                    list.add(i);
                }
                finalList.addAll(list);
            }
        }
        return finalList;
    }

    /**
     * Add item into HashMap
     *
     * @param hashMap HashMap to perform add operation
     * @param key     Unique Key to  put in the HashMap - Unique Key to  put in the HashMap - Key type should same as key type mention in the hashMap
     * @param value   value to put in the HashMap - value to put in the HashMap - Value of data type should same as value type mention in the hashMap
     * @return HashMap
     */
    public static HashMap<Object, Object> addItemToHashMap(HashMap<Object, Object> hashMap, Object key, Object value) {

        try {
            if (key != null && value != null) {
                hashMap.put(key, value);
            }
        } catch (ClassCastException exception) {
            Log.d("Exception", "Not Valid Type", exception);
        }
        return hashMap;
    }

    /**
     * Remove item from HashMap
     *
     * @param hashMap HashMap to perform remove operation
     * @param key     Unique Key to remove item from HashMap
     * @return HashMap
     */
    public static HashMap<Object, Object> removeItemFromHashMap(HashMap<Object, Object> hashMap, Object key) {
        if (hashMap != null && hashMap.size() > 0) {
            hashMap.remove(key);
        }
        return hashMap;
    }

    /**
     * Update item into HashMap
     *
     * @param hashMap  HashMap to perform update operation
     * @param key      Unique Key to Update item into HashMap
     * @param newValue new value of the data corresponding to the key
     * @return HashMap
     */
    public static HashMap<Object, Object> updateItemToHashMap(HashMap<Object, Object> hashMap, Object key, Object newValue) {
        if (hashMap != null) {
            hashMap.put(key, newValue);
        }
        return hashMap;
    }


    /**
     * Add item into linked LinkedHashMap
     *
     * @param linkedHashMap LinkedHashMap to perform add operation
     * @param key           Unique Key to  put in the LinkedHashMap
     * @param value         value to put in the LinkedHashMap
     * @return LinkedHashMap
     */

    public static LinkedHashMap<Object, Object> addItemToLinkedHashMap(LinkedHashMap<Object, Object> linkedHashMap, Object key, Object value) {

        if (key != null && value != null) {
            linkedHashMap.put(key, value);
        }
        return linkedHashMap;
    }

    /**
     * Remove item from linked LinkedHashMap
     *
     * @param linkedHashMap LinkedHashMap to perform remove operation
     * @param key           Unique Key to remove item from LinkedHashMap
     * @return LinkedHashMap
     */
    public static LinkedHashMap<Object, Object> removeItemToLinkedHashMap(LinkedHashMap<Object, Object> linkedHashMap, Object key) {
        if (linkedHashMap != null && linkedHashMap.size() > 0) {
            linkedHashMap.remove(key);
        }
        return linkedHashMap;
    }

    /**
     * Update item into LinkedHashMap
     *
     * @param linkedHashMap LinkedHashMap to perform update operation
     * @param key           Unique Key to Update item into LinkedHashMap
     * @param newValue      new value of the data corresponding to the key
     * @return LinkedHashMap
     */
    public static LinkedHashMap<Object, Object> updateItemToHashMap(LinkedHashMap<Object, Object> linkedHashMap, Object key, Object newValue) {
        if (linkedHashMap != null) {
            linkedHashMap.put(key, newValue);
        }
        return linkedHashMap;
    }

    /**
     * Get HashMap keys
     *
     * @param hashMap HashMap to get key
     * @return List
     */
    public static List<Object> getHashMapKeys(HashMap<Object, Object> hashMap) {
        List<Object> keyList = new ArrayList<>();
        if (hashMap != null) {
            Set<Object> keys = hashMap.keySet();
            keyList.addAll(keys);
        }
        return keyList;

    }

    /**
     * Get item from the HashMap by unique key
     *
     * @param hashMap HashMap to retrieve item by unique key
     * @return List
     */

    public static List<Object> getHashMapValueByKey(HashMap<Object, Object> hashMap, Object key) {

        List<Object> list = new ArrayList<>();
        if (hashMap != null) {
            list.add(hashMap.get(key));
        }

        return list;
    }


    /**
     * finding key corresponding to value in HashMap
     *
     * @param hashMap HashMap to get data
     * @param value   Item of the HashMap to find
     * @return HashMap
     */
    public static HashMap<Object, Object> getHashMapByValue(HashMap<Object, Object> hashMap, Object value) {

        HashMap<Object, Object> hashMap1 = new HashMap<>();
        HashMap<Object, Object> hashMap2 = new HashMap<>();

        Object key = null;
        for (Map.Entry entry : hashMap.entrySet()) {
            if (value.equals(entry.getValue())) {
                key = entry.getKey();
                hashMap1.put(key, value);
            }
        }
        hashMap2.putAll(hashMap1);
        return hashMap2;
    }


    /**
     * Add item into HashSet
     *
     * @param hashSet HashSet to add data
     * @param value   Value to add in the HashSet
     * @return HashSet
     */
    public static HashSet<Object> addItemToHashSet(HashSet<Object> hashSet, Object value) {

        try {
            if (value != null) {
                hashSet.add(value);
            }
        } catch (ClassCastException exception) {
            Log.d("Exception", "Not Valid Type", exception);
        }
        return hashSet;
    }


    /**
     * Remove item from HashSet
     *
     * @param hashSet HashSet to remove data
     * @param value   Value to remove from the HashSet
     * @return HashSet
     */
    public static HashSet<Object> removeItemFromHashSet(HashSet<Object> hashSet, Object value) {

        try {
            if (hashSet != null && hashSet.size() > 0) {
                hashSet.remove(value);
            }
        } catch (ClassCastException exception) {
            Log.d("Exception", "Not Valid Type", exception);
        }
        return hashSet;
    }

    /**
     * Update item into the HashSet
     *
     * @param hashSet HashSet to update the data
     * @param newItem New item to update
     * @param oldItem Old item - which will replaced with new item
     * @return HashSet
     */
    public static HashSet<Object> updateItemToHashSet(HashSet<Object> hashSet, Object newItem, Object oldItem) {

        try {
            if (hashSet != null) {
                for (Object i : hashSet) {
                    if (i.equals(oldItem)) {
                        hashSet.remove(i);
                        hashSet.add(newItem);
                        break;
                    }
                }
            }
        } catch (ClassCastException exception) {
            Log.d("Exception", "Not Valid Type", exception);
        }
        return hashSet;
    }

    /**
     * Get item from HashSet
     *
     * @param hashSet  HashSet to get item
     * @param findItem Item of the HashSet to find
     * @return HashSet
     */
    public static HashSet<Object> getHashSet(HashSet<Object> hashSet, Object findItem) {
        HashSet<Object> finalHashSet = new HashSet<>();

        if (hashSet != null) {
            for (Object i : hashSet) {
                ArrayList<Object> list = new ArrayList<>();
                if (i.toString().toLowerCase().contains(findItem.toString())) {
                    list.add(i);
                }
                finalHashSet.addAll(list);
            }
        }
        return finalHashSet;
    }
}
