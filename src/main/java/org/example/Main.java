package org.example;

import java.io.IOException;
import java.util.Arrays;


public class Main {
    private static int[] array = {0, 1, 2, 3, 0, 1, 2, 3, 0};

    public static void main(String[] args) {
        Backup bc = new Backup();
        XO xo = new XO();
        try {
            //Написать функцию, создающую резервную копию всех файлов в директории(без поддиректорий) во вновь созданную папку ./backup
             bc.backup();

            /*Предположить, что числа в исходном массиве из 9 элементов имеют диапазон[0, 3],
            и представляют собой, например, состояния ячеек поля для игры в крестикинолики, где 0 – это пустое поле,
             1 – это поле с крестиком, 2 – это поле с ноликом, 3 – резервное значение.
             Такое предположение позволит хранить в одном числе типа int всё поле 3х3.
             Реализовать функционал с записью в файл и обратно игрового поля. Выводить в консоль игровое поле после импорта,
             заменяя числа символами X, O, •(пусто)
             */
            // - Преобразуем игровое поле из масива и сохранем в файл game_table.txt
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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int[] convert(String[] array) {
        int[] result = new int[9];
        int index = 0;
        for (String item : array) {
            try {
                int i = Integer.parseInt(item);
                result[index] = i;
                index++;
            } catch (RuntimeException ignored) {

            }
        }
        return result;
    }
}