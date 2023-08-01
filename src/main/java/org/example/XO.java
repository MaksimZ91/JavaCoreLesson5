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
