package com.zibda;

import com.zibda.classfinder.ClassFinder;

import java.util.List;

/**
 * Created by ZeBr on 17.02.2022.
 */
public class Main {

    public static void main(String[] args) {
        ClassFinder classFinder = new ClassFinder();
        List<String> result = classFinder.findClasses(args[0], args[1]);
        System.out.printf("Were found classes: \n%s", result);
    }
}
