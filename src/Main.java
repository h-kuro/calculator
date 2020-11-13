import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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

            Pattern pattern = Pattern.compile("[0-9]+|([^0-9])");
            Matcher matcher = pattern.matcher(inputString);
            int groupCount = matcher.groupCount();

            ArrayList<String> elements = new ArrayList<String>();

            while (matcher.find()) {
                for (int i = 0; i < groupCount; i++) {
                    elements.add(matcher.group(i));
                }
            }

            int operatorPoint = 2147483647;
            for (String operator : operators) {
                int temp = elements.indexOf(operator);
                if (temp == -1) temp = 2147483647;
                operatorPoint = Math.min(operatorPoint, temp);
            }

            if (operatorPoint == 2147483647) {
                System.out.println("式を入力してください");
                continue;
            }

            switch (elements.get(operatorPoint)) {
                case "+" -> elements.set(operatorPoint, String.valueOf(Integer.parseInt(elements.get(operatorPoint - 1)) + Integer.parseInt(elements.get(operatorPoint + 1))));
                case "-" -> elements.set(operatorPoint, String.valueOf(Integer.parseInt(elements.get(operatorPoint - 1)) - Integer.parseInt(elements.get(operatorPoint + 1))));
                case "*" -> elements.set(operatorPoint, String.valueOf(Integer.parseInt(elements.get(operatorPoint - 1)) * Integer.parseInt(elements.get(operatorPoint + 1))));
                case "/" -> {
                    if (Integer.parseInt(elements.get(operatorPoint + 1)) == 0) {
                        elements.set(operatorPoint, "0除算エラー");
                        break;
                    }
                    elements.set(operatorPoint, String.valueOf(Integer.parseInt(elements.get(operatorPoint - 1)) / Integer.parseInt(elements.get(operatorPoint + 1))));
                }
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
    }
}
