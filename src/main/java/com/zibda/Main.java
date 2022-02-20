package com.zibda;


import com.zibda.classfinder.ClassFinder;

import java.util.List;

/**
 * Created by Dziber on 17.02.2022.
 */
public class Main {

    public static void main(String[] args) {
        if (args.length == 2) {
            ClassFinder classFinder = new ClassFinder();
            List<String> result = classFinder.findClasses(args[0], args[1]);
            System.out.printf("Were found classes: \n%s\n", result);
        } else {
            System.err.println("Wrong count of parameters. Must be <filename> '<pattern>'");
        }
    }
}
