package ru.bstu.it32.bogunov.lab4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        List<String> tagsToSave = new ArrayList<>();

        while(true){
            System.out.println("Enter tag to save or 0 to stop");
            if (scanner.hasNextInt())
                if (scanner.nextInt() == 0) break;
            tagsToSave.add(scanner.next());
        }
//        String html = "<meta charset=\"UTF-8\">" +
//                        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
//                        "<title>PixelBit</title><link href=\"https://fonts.googleapis.com/css2?family=Lobster&family=Yusei+Magic&display=swap\" rel=\"stylesheet\">" +
//                        "<link rel=\"stylesheet\" href=\"style.css\">" +
//                        "<script src=\"https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.0/jquery.min.js\" integrity=\"sha512-k2WPPrSgRFI6cTaHHhJdc8kAXaRM4JBFEDo1pPGGlYiOyv4vnA0Pp0G5XMYYxgAPmtmv/IIaQA6n5fLAyJaFMA==\" crossorigin=\"anonymous\"></script>";

        String html = readFromFile();
        String regex;
        String replacedHtml = null;
        for (int i = 0; i < tagsToSave.size(); i++) {
            regex = "<" + tagsToSave.get(i);
            replacedHtml = html.replaceAll(regex, "buffer" + i);
        }

        // удаление ненужных атрибутов
        html = replacedHtml.replaceAll("(<\\w+)[^>]*(>)", "$1$2");
        System.out.println(html);


        // обратная замена buffer на те теги
        for (int i = 0; i < tagsToSave.size(); i++) {
            regex = "buffer" + i;
            replacedHtml = html.replaceAll(regex, "<" + tagsToSave.get(i));
        }
    }

    public static String readFromFile() throws FileNotFoundException {
        String path = "C:\\Users\\artem\\IdeaProjects\\lab4\\src\\ru\\bstu\\it32\\bogunov\\index.html";
        File file = new File(path);
        String fileInput = "";
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()){
             fileInput += scanner.nextLine();
        }
        return fileInput;
    }
}

