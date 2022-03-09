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
 * Created by Dziber on 17.02.2022.
 */

public class ClassFinder {

    /**
     * Method to find class name from list of classes/
     *
     * @param classesPath   - path to file with class names
     * @param searchPattern - pattern for search
     * @return - list of classes that were found
     */
    public List<String> findClasses(String classesPath, String searchPattern) {
        List<String> classes = readClasses(classesPath);
        List<String> result = new ArrayList<>();

        for (String className : classes) {
            String classForSearch = className.substring(className.lastIndexOf(".") + 1);
            boolean classIsFound = false;
            boolean isLowerCasePattern = false;
            if (searchPattern.toLowerCase().equals(searchPattern)) {
                classForSearch = classForSearch.toLowerCase();
                isLowerCasePattern = true;
            }
            for (int i = 0; i < searchPattern.length(); i++) {
                char patternLetter = searchPattern.charAt(i);
                if (patternLetter == '*' && !classForSearch.isEmpty()) {
                    classForSearch = classForSearch.substring(1);
                } else if (patternLetter == ' ' && (i == searchPattern.length() - 1)) {
                    if (!classForSearch.isEmpty()) {
                        classIsFound = lastLettersEquals(classForSearch, searchPattern);
                        break;
                    }
                } else {
                    int letterIndex = classForSearch.indexOf(patternLetter);
                    if (isLetterFound(isLowerCasePattern, patternLetter, letterIndex)) {
                        classForSearch = classForSearch.substring(letterIndex + 1);
                    } else {
                        break;
                    }
                }
                classIsFound = (i == (searchPattern.length()-1));
            }
            if (classIsFound) {
                result.add(className);
            }
        }

        ClassNameComparator classNameComparator = new ClassNameComparator();
        result.sort(classNameComparator);
        return result;
    }


    private boolean lastLettersEquals(String classForSearch, String searchPattern) {
        return searchPattern.charAt(searchPattern.trim().length() - 1) == classForSearch.charAt(classForSearch.length() - 1);
    }


    private boolean isLetterFound(boolean isLowerCasePattern, char patternLetter, int letterIndex) {
        if (isLowerCasePattern || Character.isUpperCase(patternLetter)) {
            return letterIndex >= 0;
        } else {
            return letterIndex == 0;
        }
    }


    private List<String> readClasses(String classesPath) {
        Path path = Paths.get(classesPath);
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            //Here must be logger in real life
            System.out.println("Error while reading from file");
            e.printStackTrace();
        }
        lines.removeAll(Arrays.asList("", null));
        return lines;
    }
}
