import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String[] operators = {"+", "-", "*", "/"};

        while (true) {
            System.out.print("式:");

            String inputString = null;
            try {
                inputString = bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            inputString = Objects.requireNonNull(inputString).replace(" ", "");

            if (inputString.equals("end")) break;

            Pattern pattern = Pattern.compile("([0-9]+)|[^0-9]");
            Matcher matcher = pattern.matcher(inputString);
            int groupCount = matcher.groupCount();

            ArrayList<String> elements = new ArrayList<String>();

            String last = "";
            boolean sign = false;
            while (matcher.find()) {
                for (int i = 0; i < groupCount; i++) {
                    if ((last.equals("") || Arrays.asList(operators).contains(last)) && matcher.group(i).equals("-")) {
                        sign = true;
                    } else if (sign) {
                        elements.add('-' + matcher.group(i));
                        sign = false;
                    } else if (matcher.group(i).equals(".") || last.equals(".")) {
                        elements.set(elements.size() - 1, elements.get(elements.size() - 1) + matcher.group(i));
                    } else {
                        elements.add(matcher.group(i));
                    }
                    last = matcher.group(i);
                }
            }

            int operatorPoint = 2147483647;
            for (String operator : operators) {
                int temp = elements.indexOf(operator);
                if (temp == -1) temp = 2147483647;
                operatorPoint = Math.min(operatorPoint, temp);
            }

            if (operatorPoint == 2147483647) {
                System.out.println("式エラー");
                continue;
            }

            if (elements.get(operatorPoint - 1).contains(".") || elements.get(operatorPoint + 1).contains(".")) {
                elements.set(operatorPoint,
                        calculation(
                                Double.parseDouble(elements.get(operatorPoint - 1)),
                                Double.parseDouble(elements.get(operatorPoint + 1)),
                                elements.get(operatorPoint)
                        ));
            } else {
                elements.set(operatorPoint,
                        calculation(
                                Integer.parseInt(elements.get(operatorPoint - 1)),
                                Integer.parseInt(elements.get(operatorPoint + 1)),
                                elements.get(operatorPoint)
                        ));
            }

            elements.remove(operatorPoint + 1);
            elements.remove(operatorPoint - 1);

            System.out.println(elements.get(0));
        }
        try {
            bufferedReader.close();
            inputStreamReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("終了");
    }

    private static String calculation(int a, int b, String operator) {
        String result = "";
        switch (operator) {
            case "+" -> result = String.valueOf(a + b);
            case "-" -> result = String.valueOf(a - b);
            case "*" -> result = String.valueOf(a * b);
            case "/" -> {
                if (b == 0) {
                    result = "0除算エラー";
                    break;
                }
                result = String.valueOf(a / b);
            }
            default -> result = "式エラー";
        }
        return result;
    }

    private static String calculation(double a, double b, String operator) {
        String result = "";
        switch (operator) {
            case "+" -> result = String.valueOf(a + b);
            case "-" -> result = String.valueOf(a - b);
            case "*" -> result = String.valueOf(a * b);
            case "/" -> {
                if (b == 0) {
                    result = "0除算エラー";
                    break;
                }
                result = String.valueOf(a / b);
            }
            default -> result = "式エラー";
        }
        return result;
    }
}
