//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonProcessingException;
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
    private final String API_KEY = "6650f5e60e6a42b505df84e7";
    private RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    public BigDecimal getExchangeRate (String fromCurrency, String toCurrency){
        BigDecimal exchangeRate = new BigDecimal(0);
        try{
            String response = restTemplate.getForObject(BASE_URL + API_KEY + "/pair/" + fromCurrency + "/" + toCurrency, String.class);
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
        while(true) {
            //Main menu loop
            printMainMenu();
            int mainMenuSelection = promptForMenuSelection("Please select an option.\n");
            if(mainMenuSelection == 1){
                while(true){
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
                                System.out.println(currencyConverter(new BigDecimal("1"),desiredExchangeRate).setScale(2, RoundingMode.HALF_UP) + " EUR");
                            }
                            if(converterToMenuSelection == 2){
                                BigDecimal desiredExchangeRate = getExchangeRate("USD", "JPY");
                                System.out.println(currencyConverter(new BigDecimal("1"), desiredExchangeRate).setScale(2,RoundingMode.HALF_UP) + " JPY");
                            }
                            if(converterToMenuSelection == 3){
                                BigDecimal desiredExchangeRate = getExchangeRate("USD", "GBP");
                                System.out.println(currencyConverter(new BigDecimal("1"), desiredExchangeRate).setScale(2,RoundingMode.HALF_UP) + " GBP");
                            }
                            if(converterToMenuSelection == 4){
                                BigDecimal desiredExchangeRate = getExchangeRate("USD", "AUD");
                                System.out.println(currencyConverter(new BigDecimal("1"), desiredExchangeRate).setScale(2,RoundingMode.HALF_UP) + " AUD");
                            }
                            if(converterToMenuSelection == 5){
                                BigDecimal desiredExchangeRate = getExchangeRate("USD", "CAD");
                                System.out.println(currencyConverter(new BigDecimal("1"), desiredExchangeRate).setScale(2,RoundingMode.HALF_UP) + " CAD");
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
    private void printMainMenu(){
        System.out.println("1: Choose a currency to convert");
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