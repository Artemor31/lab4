package ru.bstu.it32.bogunov.lab4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        List<String> tagsToSave = new ArrayList<>();

        while(true) {
            System.out.println("Enter tag to save or 0 to continue: ");
            if (scanner.hasNextInt())
                if (scanner.nextInt() == 0) break;
            tagsToSave.add(scanner.next());
        }

        String path = "index.html";
        File file = new File(path);
        scanner = new Scanner(file);
        List<String> lines = new ArrayList<>();
        while(scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }

        // for all lines
        for (int i = 0; i < lines.size(); i++) {

            // for all tags to save
            for (int j = 0; j < tagsToSave.size(); j++) {

                // create new line and try to replace in it current loop tag
                String replacedLine = lines.get(i).
                        replaceAll("(<" + tagsToSave.get(j) + "+)[^>]*(>)", "$1$2");

                // if this line contains current loop tag
                if (!replacedLine.equals(lines.get(i)))
                    break;
                // if we complete loop and didn't find requested tags in current line
                if(j == tagsToSave.size() - 1)
                    lines.set(i, lines.get(i).replaceAll("(<\\w+)[^>]*(>)", "$1$2"));
            }
        }
        ///////// write to file preparations
        StringBuilder html = new StringBuilder();
        for (String line : lines) {
            html.append(line).append("\n");
        }
        writeToFile(html.toString());
    }

    private static void writeToFile(String outputStr){
        String path = "newIndex.html";
        File file = new File(path);
        String[] words = outputStr.split(">");
        for (int i = 0; i < words.length; i++) {
            words[i] += ">";
        }
        StringBuilder fullFile = new StringBuilder();
        for(String str : words)
            fullFile.append(str);
        try {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.println(fullFile);
            printWriter.close();

        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
            System.out.println("Can't write to file");
        }
    }
}

