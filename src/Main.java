import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        System.out.println("式:");

        String inputString = null;
        try {
            inputString = bufferedReader.readLine();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Pattern pattern = Pattern.compile("[0-9]+|([^0-9])");
        Matcher matcher = pattern.matcher(inputString);
        int groupCount = matcher.groupCount();

        while (matcher.find()) {
            for (int i = 0; i< groupCount; i++){
                System.out.println(matcher.group(i));
            }
        }

    }
}
