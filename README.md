# Тонкости работы  
## Задание 1. 
Написать функцию, создающую резервную копию всех файлов в директории(без поддиректорий) во вновь созданную папку ./backup  
### Class Backup  
```java
package org.example;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class Backup {

    public void backup() throws IOException {
        Files.createDirectories(Path.of("./backup"));
        DirectoryStream<Path> currentDir = Files.newDirectoryStream(Path.of("."));
        for (Path file: currentDir) {
            if(Files.isDirectory(file))
                continue;
            Files.copy(file, Path.of("./backup" + file.toString()));
        }
    }
}
```
### Class Main 
```java
package org.example;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        Backup bc = new Backup();      
        try {           
             bc.backup();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
```
![task_1](https://github.com/MaksimZ91/JavaCoreLesson5/assets/72209139/7fb59dd2-1d83-4ed0-9a19-712332efcb87)  

## Задание 2. 
Предположить, что числа в исходном массиве из 9 элементов имеют диапазон[0, 3], и представляют собой, например, состояния ячеек поля для игры в крестикинолики,  
где 0 – это пустое поле, 1 – это поле с крестиком, 2 – это поле с ноликом, 3 – резервное значение. Такое предположение позволит хранить в одном числе типа int всё поле 3х3.   
Реализовать функционал с записью в файл и обратно игрового поля. Выводить в консоль игровое поле после импорта, заменяя числа символами X, O, •(пусто)   
### Class XO 
```java
package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.IntStream;

public class XO {

    private static final int xCode = 1;
    private static final int oCode = 2;
    private static final int nullCode = 0;    private static final int firstSwitch = 2;
    private static final int secondSwitch = 5;
    private static final int  thirdSwitch = 8;


    public void saveTable(int[] array) throws IOException {
        String gameTable = decode(array);
        try (FileWriter fr = new FileWriter("game_table.txt")) {
            fr.write(gameTable);
            fr.flush();
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    public String read(String fileName) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
        return sb.toString();
    }

    public void saveArrayCode(int[] array) throws IOException {
        StringBuilder sb = new StringBuilder();
        IntStream.range(0, array.length).forEach(index -> sb.append(array[index]).append(" "));
        try (FileWriter fr = new FileWriter("array_code.txt")) {
            fr.write(sb.toString());
            fr.flush();
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    public int[] code(String gameTable) {
        int[] array = new int[9];
        int index = 0;
        for (int i = 0; i < gameTable.length() - 1; i++) {
            if (gameTable.charAt(i) == '|') continue;
            switch (gameTable.charAt(i)) {
                case 'X' -> {
                    array[index] = xCode;
                    index++;
                }
                case 'O' -> {
                    array[index] = oCode;
                    index++;
                }
                case '_' -> {
                    array[index] = nullCode;
                    index++;
                }
            }
        }
        return array;
    }

    public String decode(int[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append("|");
            switch (array[i]) {
                case xCode -> sb.append("X");
                case oCode -> sb.append("O");
                default -> sb.append("_");
            }
            if (i == firstSwitch || i == secondSwitch || i == thirdSwitch) {
                sb.append("|");
                sb.append(System.lineSeparator());
            }
        }
        return sb.toString();
    }
}
```
### Class Main 
```java
package org.example;

import java.io.IOException;
import java.util.Arrays;


public class Main {
    private static int[] array = {0, 1, 2, 3, 0, 1, 2, 3, 0};

    public static void main(String[] args) {       
        XO xo = new XO();
        try {
            xo.saveTable(array);
            System.out.println("Читаем файл game_table.txt");
            String gameTable = xo.read("game_table.txt");
            System.out.println(gameTable);
            System.out.println("Преобразоваваем игровое поле обратно в массив");
            int[] newArrayCodeGameTable = xo.code(gameTable);
            System.out.println(Arrays.toString(newArrayCodeGameTable));
            System.out.println("Сохраняем преобразованный массив в файл array_code.txt");
            xo.saveArrayCode(newArrayCodeGameTable);
            System.out.println("Читаем преобразованный массив из файла game_table.txt");
            String arrayCode = xo.read("array_code.txt");
            System.out.println("Преобразовываем прочтенную строку в массив строк");
            String[] buffArray = arrayCode.split(" ");
            System.out.println("Конвертируем массив строк в числа и сохраняем в файл game_table.txt");
            xo.saveTable(convert(buffArray));
            System.out.println("Снова читаем файл game_table.txt");
            System.out.println(xo.read("game_table.txt"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private static int[] convert(String[] array){
        int[] result = new int[9];
        int index = 0;
        for (String item: array) {
            try {
                int i = Integer.parseInt(item);
                result[index] = i;
                index++;
            } catch (RuntimeException ignored){

            }
        }
        return  result;
    }
}
```
![task_2 1](https://github.com/MaksimZ91/JavaCoreLesson5/assets/72209139/c8ae1c3c-d40d-4f98-a415-70104af1ea45)
![task_2](https://github.com/MaksimZ91/JavaCoreLesson5/assets/72209139/ed05f086-a785-448a-981f-6f0e50362578)
