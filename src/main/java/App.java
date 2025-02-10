//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
@RestController


public class App {
    private final Scanner KEYBOARD = new Scanner(System.in);
    private final String BASE_URL = "https://v6.exchangerate-api.com/v6/";
    String APIKey = "";
    private RestTemplate restTemplate = new RestTemplate();
    String fromCurrency = "";
    String toCurrency = "";
    BigDecimal currencyAmount = new BigDecimal (0);

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }
    public BigDecimal getExchangeRate (String fromCurrency, String toCurrency){
        BigDecimal exchangeRate = new BigDecimal(0);
        try{
            String response = restTemplate.getForObject(BASE_URL + APIKey + "/pair/" + fromCurrency + "/" + toCurrency, String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response);
            exchangeRate = BigDecimal.valueOf(root.path("conversion_rate").asDouble());
        }catch(RestClientResponseException e){
            System.out.println(e.getRawStatusCode() + ": " + e.getStatusText());
        }catch(ResourceAccessException e){
            System.out.println(e.getMessage());
        }catch(JsonProcessingException e){
            System.out.println(e.getMessage());
        }
        return exchangeRate;
    }

    private void run(){
        System.out.print("In order to calculate exchange rates please first visit https://app.exchangerate-api.com/ " +
                "to create an account to receive your free API Key.\n");
        while(true) {
            //Main menu loop
            printMainMenu();
            int mainMenuSelection = promptForMenuSelection("Please select an option.\n");
            if(mainMenuSelection == 1){
                while(true){
                    currencyAmount = promptForCurrencyAmount("Please type an amount to convert\n");
                    APIKey = promptForAPIKey("Please paste your API Key here.\n");
                    //Converter From Menu Loop. Currently only USD, will add support for other currencies.
                    printConverterFromMenu();
                    int converterFromMenuSelection = promptForMenuSelection("Please choose a currency to convert.\n");
                    if(converterFromMenuSelection == 1){
                        while(true){
                            //Converter to menu loop. Will add other currencies
                            printConverterToMenu();
                            int converterToMenuSelection = promptForMenuSelection("Please choose desired currency.\n");
                            if(converterToMenuSelection == 1){
                                BigDecimal desiredExchangeRate = getExchangeRate("USD", "EUR");
                                System.out.println(currencyConverter(currencyAmount,desiredExchangeRate).setScale(2, RoundingMode.HALF_UP) + " EUR");
                            }
                            if(converterToMenuSelection == 2){
                                BigDecimal desiredExchangeRate = getExchangeRate("USD", "JPY");
                                System.out.println(currencyConverter(currencyAmount, desiredExchangeRate).setScale(2,RoundingMode.HALF_UP) + " JPY");
                            }
                            if(converterToMenuSelection == 3){
                                BigDecimal desiredExchangeRate = getExchangeRate("USD", "GBP");
                                System.out.println(currencyConverter(currencyAmount, desiredExchangeRate).setScale(2,RoundingMode.HALF_UP) + " GBP");
                            }
                            if(converterToMenuSelection == 4){
                                BigDecimal desiredExchangeRate = getExchangeRate("USD", "AUD");
                                System.out.println(currencyConverter(currencyAmount, desiredExchangeRate).setScale(2,RoundingMode.HALF_UP) + " AUD");
                            }
                            if(converterToMenuSelection == 5){
                                BigDecimal desiredExchangeRate = getExchangeRate("USD", "CAD");
                                System.out.println(currencyConverter(currencyAmount, desiredExchangeRate).setScale(2,RoundingMode.HALF_UP) + " CAD");
                            }
                            if(converterToMenuSelection == 0){
                                break;
                            }
                        }
                    }
                    if(converterFromMenuSelection == 0){
                        break;
                    }
                }
            }
            if(mainMenuSelection == 0){
                break;
            }

        }
    }
    private BigDecimal promptForCurrencyAmount(String prompt){
        System.out.print(prompt);
        BigDecimal currencyAmount;
        try{
            currencyAmount = new BigDecimal(Double.parseDouble(KEYBOARD.nextLine()));
        }catch(NumberFormatException e){
            currencyAmount = new BigDecimal(0);
        }
        return currencyAmount;
    }
    private int promptForMenuSelection(String prompt){
        System.out.print(prompt);
        int menuSelection;
        try{
            menuSelection = Integer.parseInt(KEYBOARD.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }
    private String promptForAPIKey(String prompt){
        System.out.print(prompt);
        String APIKey;
        try{
            APIKey = KEYBOARD.nextLine().trim();
        }catch(NoSuchElementException e){
            APIKey = "";
        };
        return APIKey;
    }
    private void printMainMenu(){
        System.out.println("1: Convert Currency");
        System.out.println("0: Exit");
        System.out.println();
    }
    private void printConverterFromMenu(){
            System.out.println("1: USD");
            System.out.println("0: Return to main menu.");
    }
    private void printConverterToMenu(){
        System.out.println("1:EUR");
        System.out.println("2:JPY");
        System.out.println("3:GBP");
        System.out.println("4:AUD");
        System.out.println("5:CAD");
        System.out.println("0:Return to previous menu.");
    }
    private BigDecimal currencyConverter(BigDecimal currency, BigDecimal exchangeRate){
        return currency.multiply(exchangeRate);
    }
}