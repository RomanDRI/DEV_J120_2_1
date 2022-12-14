package Dictionary;

import java.io.FileNotFoundException;
import java.io.IOException;



public class Result {
    public static void main(String[] args) throws IOException {
        if(args.length == 0) {
            System.out.println("Задайте аргумент в виде файла формата txt. Это можно сделать в командной строке или " +
                    "в компиляторе Run -> edit Configuration -> Program arguments (пример text.txt).");
            System.exit(1);
        }


        DictionaryCalculation fd = new DictionaryCalculation();
            for (String file : args) {
                try {
                    fd.read(file);
                } catch (IOException e) {
                    System.out.println(file + " не существует или не может быть прочтен");
                    System.exit(1);
                }
            }
        try {
            fd.save();
        } catch (FileNotFoundException e) {
            System.out.println("Проблема с записью файла");
            System.exit(1);
        }
    }
}
