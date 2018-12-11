package com.mariuszzareba.jmsproject;

import java.io.*;
import java.net.URL;

public class TextFileAsString {

    // Let's make use of fact that xml is in reality a text file,
    // so we can load it's content line by line to BufferedReader buffer

    public static void main(String[] args) {
    }

    public String getTextFileAsString(String fileName) throws IOException {

        ClassLoader classLoader = TextFileAsString.class.getClassLoader();

        URL resourceURL = classLoader.getResource(fileName);
        if (resourceURL == null) {
            // check if getResource method returns significant value
            // as getFile method throws NullPointerException
            throw new FileNotFoundException(fileName + " not found. Check if file exists in resources folder");
        }
        File textFile = new File(resourceURL.getFile());

        Reader fileReader = new FileReader(textFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        StringBuilder sb = new StringBuilder();  // using StringBuilder, more efficient when concatenating in a loop
        String line = bufferedReader.readLine();

        while (line != null) {                    // null indicating EOF
            sb.append(line).append("\n");
            line = bufferedReader.readLine();
        }

        String textFileString = sb.toString();
        bufferedReader.close();

        return textFileString;
    }


}