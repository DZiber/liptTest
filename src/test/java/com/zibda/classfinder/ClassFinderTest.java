package com.zibda.classfinder;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Dziber on 20.02.2022.
 */
public class ClassFinderTest {
    private ClassFinder classFinder = new ClassFinder();
    private final String TEST_FILE_PATH = "src/test/resources/Input_sample.txt";
    private ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Test
    public void camelCase() throws Exception {
        List<String> expectedResult = Arrays.asList("c.d.FooBar", "a.b.FooBarBaz");
        assertEquals(expectedResult, classFinder.findClasses(TEST_FILE_PATH, "FoBa"));
    }


    @Test
    public void lowerCase() throws Exception {
        List<String> expectedResult = Arrays.asList("a.b.FooBarBaz");
        assertEquals(expectedResult, classFinder.findClasses(TEST_FILE_PATH, "fbb"));
    }


    @Test
    public void wildcard() throws Exception {
        List<String> expectedResult = Arrays.asList("a.b.FooBarBaz");
        assertEquals(expectedResult, classFinder.findClasses(TEST_FILE_PATH, "B*rBaz"));
    }

    @Test
    public void spaceInTheEnd() throws Exception {
        List<String> expectedResult = Arrays.asList("c.d.FooBar");
        assertEquals(expectedResult, classFinder.findClasses(TEST_FILE_PATH, "FBar "));
    }


    @Test
    public void spaceInTheMiddle() throws Exception {
        List<String> expectedResult = Collections.emptyList();
        assertEquals(expectedResult, classFinder.findClasses(TEST_FILE_PATH, "F Bar"));
    }

    @Test
    public void spaceFromBegin() throws Exception {
        List<String> expectedResult = Collections.emptyList();
        assertEquals(expectedResult, classFinder.findClasses(TEST_FILE_PATH, " FBar"));
    }


    @Test
    public void wildcardOnly() throws Exception {
        List<String> expectedResult = Arrays.asList("c.d.FooBar", "a.b.FooBarBaz", "liptsoft.MindReader",
                "ScubaArgentineOperator", "TelephoneOperator", "liptsoft.WishMaker", "YourEyesAreSpinningInTheirSockets",
                "YoureLeavingUsHere", "YouveComeToThisPoint");
        assertEquals(expectedResult, classFinder.findClasses(TEST_FILE_PATH, "*"));
    }

    @Test
    public void twoWildcards() throws Exception {
        List<String> expectedResult = Arrays.asList("c.d.FooBar", "a.b.FooBarBaz");
        assertEquals(expectedResult, classFinder.findClasses(TEST_FILE_PATH, "Fo*Ba*"));
    }


    @Test
    public void lowerCaseAndSpace() throws Exception {
        List<String> expectedResult = Arrays.asList("liptsoft.MindReader");
        assertEquals(expectedResult, classFinder.findClasses(TEST_FILE_PATH, "mreader "));
    }


    @Test
    public void wildcardLowerCase() throws Exception {
        List<String> expectedResult = Arrays.asList("YourEyesAreSpinningInTheirSockets", "YoureLeavingUsHere", "YouveComeToThisPoint");
        assertEquals(expectedResult, classFinder.findClasses(TEST_FILE_PATH, "you*"));
    }


    @Test
    public void wildcardLowerCaseSpace() throws Exception {
        List<String> expectedResult = Arrays.asList("ScubaArgentineOperator", "TelephoneOperator");
        assertEquals(expectedResult, classFinder.findClasses(TEST_FILE_PATH, "*operator "));
    }


    @Test
    public void wrongPatternCamelCase() throws Exception {
        List<String> expectedResult = Collections.emptyList();
        assertEquals(expectedResult, classFinder.findClasses(TEST_FILE_PATH, "BaFo"));
    }


    @Test
    public void wrongPatternLowerCase() throws Exception {
        List<String> expectedResult = Collections.emptyList();
        assertEquals(expectedResult, classFinder.findClasses(TEST_FILE_PATH, "bafo"));
    }


    @Test
    public void readFileError() throws Exception {
        System.setOut(new PrintStream(outputStreamCaptor));
        classFinder.findClasses("src/test/resources/Error_to_read.txt", "FoBa");
        assertEquals("Error while reading from file", outputStreamCaptor.toString().trim());
    }


    @Test
    public void emptyFile() throws Exception {
        String emptyFilePath = "src/test/resources/Empty_file.txt";
        List<String> expectedResult = Collections.emptyList();
        assertEquals(expectedResult, classFinder.findClasses(emptyFilePath, "FoBa"));
    }


    @Test
    public void wrongPath() throws Exception {
        System.setOut(new PrintStream(outputStreamCaptor));
        classFinder.findClasses("path", "FoBa");
        assertEquals("Error while reading from file", outputStreamCaptor.toString().trim());
    }
}