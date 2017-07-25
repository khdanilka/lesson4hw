import com.sun.tools.doclets.internal.toolkit.util.DocFinder;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final Random rn = new Random();
    private static final Scanner sc = new Scanner(System.in);
    private static final int PAGE_SIZE = 1800;

    public static void main(String[] args) {


        System.out.println("task 1");
        try {
            task1();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println();
        System.out.println("task 2");
        try {
            task2();
        } catch (Exception e) {
            e.printStackTrace();
        }


        /* Написать консольное приложение, которое умеет постранично читать текстовые файлы
        // (размером > 10 mb), вводим страницу, программа выводит ее в консоль (за страницу можно
        // принять 1800 символов). Время чтения файла должно находится в разумных пределах
        // (программа не должна загружаться дольше 10 секунд), ну и чтение тоже не должно занимать
        // >5 секунд.*/

        System.out.println("task 3");
        System.out.println("Введите номер страницы: ");
        if (sc.hasNextInt()) {
            int i = sc.nextInt();
            try (RandomAccessFile raf = new RandomAccessFile("shakespeare.txt", "r")) {
                if (i * PAGE_SIZE > raf.length() || i == 0) System.out.println("такой страницы нет");
                else
                    for (int j = (i - 1) * PAGE_SIZE; j < PAGE_SIZE * i; j++) {
                        raf.seek(j);
                        System.out.print((char) raf.read());
                    }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Вы ввели не целое число");
        }

    }


    //Прочитать файл (около 50 байт) в байтовый массив и вывести этот массив в консоль;
    public static void task1() throws Exception{
        File f = new File("50byte.txt");
        createFileStream(f.getName(),50);

        FileInputStream in = new FileInputStream(f.getName());
        byte[] buffer = new byte[in.available()];
        in.read(buffer);

        for(int i =0; i<buffer.length; i ++){
            System.out.print((char)buffer[i]);
        }
        in.close();
    }


    //Последовательно сшить 5 файлов в один (файлы также ~100 байт)
    public static void task2() throws Exception{
        String fileName = "puzzle";
        ArrayList<FileInputStream> ali = new ArrayList<>();
        for(int i = 0; i < 5; i++ ){
            createFileStream(fileName+i,122);
            ali.add(new FileInputStream(fileName+i));
        }
        SequenceInputStream sin = new SequenceInputStream(Collections.enumeration(ali));
        FileOutputStream bw = (new FileOutputStream(fileName + "_common.txt"));
        int x;
        while ((x = sin.read()) != -1) {
            bw.write((char) x);
        }
        System.out.println("успех! смотрите файл " + fileName +"_common.txt");
        sin.close();
    }


    public static void createFileStream(String pathName, int size){
        try(FileOutputStream out = new FileOutputStream(pathName))
        {
            for(int i = 0; i <= size; i ++){
                int buf = rn.nextInt(122);
                out.write(buf);
            }
        }catch (IOException io){
            System.out.println("что то пошло не так с чтением" + io);
        }
    }

}
