package com.zibda.classfinder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ZeBr on 17.02.2022.
 */
public class ClassFinder {

    public List<String> findClasses(String classesPath, String searchPattern) {
        List<String> classes = readClasses(classesPath);
        List<String> result = new ArrayList<>();

        for (String className : classes) {
            String classForSearch = className.substring(className.lastIndexOf(".") + 1);
            if (searchPattern.toLowerCase().equals(searchPattern)) {
                classForSearch = classForSearch.toLowerCase();
            }

            boolean classNotMatch = false;
            for (int i = 0; i < searchPattern.length(); i++) {
                char patternLetter = searchPattern.charAt(i);
                int foundLetterIndex = classForSearch.indexOf(patternLetter);

                if (foundLetterIndex >= 0) {
                    classForSearch = classForSearch.substring(foundLetterIndex + 1);
                } else {
                    classNotMatch = true;
                    break;
                }
            }

            if (!classNotMatch) {
                result.add(className);
            }

        }


        return result;
    }


    private List<String> readClasses(String classesPath) {
        Path path = Paths.get(classesPath);
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            //Must be logger in real life
            System.out.println("Error while reading from file");
            e.printStackTrace();
        }
        lines.removeAll(Arrays.asList("", null));
        return lines;
    }
}
