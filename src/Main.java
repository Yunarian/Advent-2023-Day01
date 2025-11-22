import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        ArrayList<String> lines = getFileData("src/data");

        int partOneAnswer = 0;
        int partTwoAnswer = 0;
        for (int i = 0; i < lines.size(); i++) {
            partOneAnswer += getPartOneNumber(lines.get(i));
            partTwoAnswer += getPartTwoNumber(lines.get(i));
        }

        System.out.println("Part one answer: " + partOneAnswer);
        System.out.println("Part two answer: " + partTwoAnswer);
    }

    public static int getPartOneNumber(String line) {
        int sum = 0;
        boolean firstNumFound = false;
        boolean lastNumFound = false;
        // first number from the front
        for (int i = 0; i < line.length(); i++) {
            char letter = line.charAt(i);
            if (Character.isDigit(letter) && !firstNumFound) {
                sum += Integer.parseInt(String.valueOf(letter)) * 10;
                firstNumFound = true;
            }
        }

        // the number from the back
        for (int i = line.length() - 1; i >= 0; i--) {
            char letter = line.charAt(i);
            if (Character.isDigit(letter) && !lastNumFound) {
                sum += Integer.parseInt(String.valueOf(letter));
                lastNumFound = true;
            }
        }
        return sum;
    }

    public static int getPartTwoNumber(String line) {
        int sum = 0;
        boolean firstNumFound = false;
        boolean lastNumFound = false;
        int firstNumValue = -1;
        int lastNumValue = -1;
        int firstNumIndex = Integer.MAX_VALUE;
        int lastNumIndex = -1;
        int frontIndexTyped = Integer.MAX_VALUE;
        int backIndexTyped = Integer.MAX_VALUE;
        int valueTypedFront = -1;
        int valueTypedBack = -1;

        String[] typedNumber = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        String[] reversedTypedNumber = {"orez", "eno", "owt", "eerht", "ruof", "evif", "xis", "neves", "thgie", "enin"};
        String reversed = "";
        for (int i = line.length() - 1; i >= 0; i--) {
            reversed += line.substring(i, i + 1);
        }
        // index 0 = "zero", index 1 = "one" ... index 9 = "nine"
        int[] numberTypedIndex = {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE};
        int[] reversedNumberTypedIndex = {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE};
        // finds the index where the typed version of each number occurs in the line (from the front)
        for (int i = 0; i < 10; i++) {
            if (line.indexOf(typedNumber[i]) != -1) {
                numberTypedIndex[i] = line.indexOf(typedNumber[i]);
                if (frontIndexTyped > numberTypedIndex[i]) {
                    frontIndexTyped = numberTypedIndex[i];
                    valueTypedFront = i;
                }
            }
        }

        // finds the index where the typed version of each number occurs in the line (from the back)
        for (int i = 0; i < 10; i++) {
            if (reversed.indexOf(reversedTypedNumber[i]) != -1) {
                reversedNumberTypedIndex[i] = reversed.indexOf(reversedTypedNumber[i]);
                if (backIndexTyped > reversedNumberTypedIndex[i]) {
                    backIndexTyped = reversedNumberTypedIndex[i];
                    valueTypedBack = i;
                }
            }
        }

        // first numerical number from the front
        for (int i = 0; i < line.length(); i++) {
            char letter = line.charAt(i);
            if (Character.isDigit(letter) && !firstNumFound) {
                firstNumIndex = i;
                firstNumValue = Integer.parseInt(String.valueOf(letter));
                firstNumFound = true;
            }
        }

        // first numerical number from the back
        for (int i = 0; i < reversed.length(); i++) {
            char letter = reversed.charAt(i);
            if (Character.isDigit(letter) && !lastNumFound) {
                lastNumIndex = i;
                lastNumValue = Integer.parseInt(String.valueOf(letter));
                lastNumFound = true;
            }
        }

        // determines whether the first number is in text or numerical
        if (firstNumIndex < frontIndexTyped) {
            sum += firstNumValue * 10;
        } else {
            sum += valueTypedFront * 10;
        }

        // determines whether the last number is in text or numerical
        if (lastNumIndex != -1 && lastNumIndex < backIndexTyped) {
            sum += lastNumValue;
        } else {
            sum += valueTypedBack;
        }

        return sum;
    }

    public static ArrayList<String> getFileData(String fileName) {
        ArrayList<String> fileData = new ArrayList<String>();
        try {
            File f = new File(fileName);
            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                String line = s.nextLine();
                if (!line.equals(""))
                    fileData.add(line);
            }
            return fileData;
        }
        catch (FileNotFoundException e) {
            return fileData;
        }
    }
}