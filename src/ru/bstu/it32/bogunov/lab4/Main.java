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

        while(true){
            System.out.println("Enter tag to save or 0 to continue: ");
            if (scanner.hasNextInt())
                if (scanner.nextInt() == 0) break;
            tagsToSave.add(scanner.next());
        }

        //String html = readFromFile();
        String path = "index.html";
        File file = new File(path);
        scanner = new Scanner(file);
        List<String> lines = new ArrayList<>();
        while(scanner.hasNextLine()){
            lines.add(scanner.nextLine());
        }

        for (int i = 0; i < tagsToSave.size(); i++) {
            String replacedLine = lines.get(i).replaceAll("(<\\w+)[^>]*(>)", "$1$2");
            if(containsTag(tagsToSave, replacedLine)) continue;
            lines.set(i,lines.get(i).replaceAll("(<\\w+)[^>]*(>)", "$1$2"));
        }
//        String replacedHtml = replaceWantedTags(tagsToSave, html);
//        html = replaceUnwantedTags(replacedHtml);
//        html = recoverWantedTags(tagsToSave, html);
        StringBuilder html = new StringBuilder();
        for (String line : lines) {
            html.append(line + "\n");
        }
        writeToFile(html.toString());
    }

    private static boolean containsTag(List<String> tagsToSave, String tag){
        for (String s : tagsToSave) {
            String[] words = s.split(" ");
            for (String word : words) {
                if (word.equals("<" + tag + ">")) return true;
            }
        }
        return false;
    }

    private static String recoverWantedTags(List<String> tagsToSave, String html) {
        String regex;
        String replacedHtml = html;
        for (int i = 0; i < tagsToSave.size(); i++) {
            regex = "buffer" + i;
            replacedHtml = replacedHtml.replaceAll(regex, "<" + tagsToSave.get(i));
        }
        return replacedHtml;
    }

    private static String replaceWantedTags(List<String> tagsToSave, String html) {
        String regex;
        String replacedHtml = html;
        for (int i = 0; i < tagsToSave.size(); i++) {
            regex = "<" + tagsToSave.get(i);
            replacedHtml = replacedHtml.replaceAll(regex, "buffer" + i);
        }
        return replacedHtml;
    }

    private static String replaceUnwantedTags(String replacedHtml) {
        String html;
        html = replacedHtml.replaceAll("(<\\w+)[^>]*(>)", "$1$2");
        return html;
    }

    private static void writeToFile(String outputStr){
        String path = "newIndex.html";
        File file = new File(path);
        String[] words = outputStr.split(">");
        for (int i = 0; i < words.length; i++) {
            words[i] += ">";
        }
        try {
            PrintWriter printWriter = new PrintWriter(file);
            for(String str : words)
                printWriter.println(str);
            printWriter.close();

        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
            System.out.println("Can't write to file");
        }
    }

    private static String readFromFile() {
        String path = "index.html";
        File file = new File(path);
        StringBuilder fileInput = new StringBuilder();
        Scanner scanner;
        try {
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                fileInput.append(scanner.nextLine());
            }
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
            System.out.println("Can't open file");
        }
        return fileInput.toString();
    }
}

