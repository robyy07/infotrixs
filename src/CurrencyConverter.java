import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Scanner;

public class CurrencyConverter {
    public static void main(String[] args) throws Exception{

        Boolean running = true;
        do {

            HashMap<Integer, String> currencyCodes = new HashMap<>();

            // Add currency codes
            currencyCodes.put(1, "INR");
            currencyCodes.put(2, "USD");
            currencyCodes.put(3, "CAD");
            currencyCodes.put(4, "EUR");
            currencyCodes.put(5, "HKD");

            Integer from, to;

            String fromCode, toCode;
            double amount;

            Scanner sc = new Scanner(System.in);

            System.out.println("Welcome to the currency converter!");

            System.out.println("Currency converting FROM?");
            System.out.println("1:INR\t 2:USD\t 3:CAD\t 4:EUR\t 5:HKD");

            from = sc.nextInt();
            while (from < 1 || from > 5) {
                System.out.println("Please select a valid currency(1-5)");
                System.out.println("1:INR\t 2:USD\t 3:CAD\t 4:EUR\t 5:HKD");
                from = sc.nextInt();
            }
            fromCode = currencyCodes.get(from);

            System.out.println("Currency converting TO?");
            System.out.println("1:INR\t 2:USD\t 3:CAD\t 4:EUR\t 5:HKD");

            to = sc.nextInt();
            while (to < 1 || to > 5) {
                System.out.println("Please select a valid currency(1-5)");
                System.out.println("1:INR\t 2:USD\t 3:CAD\t 4:EUR\t 5:HKD");
                to = sc.nextInt();
            }
            toCode = currencyCodes.get(to);
            System.out.println("Amount you wish to convert?");
            amount = sc.nextFloat();

            sendHttpGETRequest(fromCode, toCode, amount);

            System.out.println("Would you like to make another conversion?");
            System.out.println("1:Yes \t Any other integer:No");
            if(sc.nextInt() != 1){
                running = false;
            }
        } while (running);
        System.out.println("Thank you !!");
    }

    private static void sendHttpGETRequest(String fromCode, String toCode, double amount) throws IOException {
        DecimalFormat f = new DecimalFormat("00.00");
        String GET_URL = "https://v6.exchangerate-api.com/v6/5c38e6b13e9fb4104bf6be9b/pair/" + toCode + "/" + fromCode;
        URL url = new URL(GET_URL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        int responseCode = httpURLConnection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) { // SUCCESS
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            var obj = new JSONObject(response.toString());
            Double exchangeRate = obj.getDouble("conversion_rate");
            System.out.println(obj); // Print the entire JSON response for reference
            System.out.println("Exchange rate from " + fromCode + " to " + toCode + ": " + exchangeRate);
            System.out.println();
            System.out.println(f.format(amount) + " " + fromCode + " = " + f.format(amount/exchangeRate) + " " + toCode);
        } else {
            System.out.println("GET request failed! Response Code: " + responseCode);
        }
    }

}
