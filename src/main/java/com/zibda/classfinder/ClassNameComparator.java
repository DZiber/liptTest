package com.zibda.classfinder;

import java.util.Comparator;

/**
 * Created by ZeBr on 20.02.2022.
 */
public class ClassNameComparator implements Comparator<String> {

    @Override
    public int compare(String nameOne, String nameTwo) {
        nameOne = nameOne.substring(nameOne.lastIndexOf(".") + 1);
        nameTwo = nameTwo.substring(nameTwo.lastIndexOf(".") + 1);
        return nameOne.compareTo(nameTwo);
    }
}

