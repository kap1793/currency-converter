//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.math.BigDecimal;
import java.util.*;
public class App {
    //test
    private final Scanner KEYBOARD = new Scanner(System.in);
    //Exchange rates for 1/13/25, in future update will add support to pull rates from API
    private final BigDecimal USD_TO_EUR_RATE = new BigDecimal(".98");
    private final BigDecimal USD_TO_JPY_RATE = new BigDecimal("157.68");
    private final BigDecimal USD_TO_GBP_RATE = new BigDecimal(".82");
    private final BigDecimal USD_TO_AUD_RATE = new BigDecimal("1.62");
    private final BigDecimal USD_TO_CAD_RATE = new BigDecimal("1.44");

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }
    private void run(){
        while(true) {
            //Main menu loop
            printMainMenu();
            int mainMenuSelection = promptForMenuSelection("Please select an option.");
            if(mainMenuSelection == 1){
                while(true){
                    //Converter From Menu Loop. Currently only USD, will add support for other currencies.
                    printConverterFromMenu();
                    int converterFromMenuSelection = promptForMenuSelection("Please choose a currency to convert.");
                    if(converterFromMenuSelection == 1){
                        while(true){
                            //Converter to menu loop. Will add other currencies
                            printConverterToMenu();
                            int converterToMenuSelection = promptForMenuSelection("Please choose desired currency.");
                            if(converterToMenuSelection == 1){
                                System.out.println(currencyConverter(new BigDecimal("1"), USD_TO_EUR_RATE) + " EUR");
                            }
                            if(converterToMenuSelection == 2){
                                System.out.println(currencyConverter(new BigDecimal("1"), USD_TO_JPY_RATE) + " JYP");
                            }
                            if(converterToMenuSelection == 3){
                                System.out.println(currencyConverter(new BigDecimal("1"), USD_TO_GBP_RATE) + " GBP");
                            }
                            if(converterToMenuSelection == 4){
                                System.out.println(currencyConverter(new BigDecimal("1"), USD_TO_AUD_RATE) + " AUD");
                            }
                            if(converterToMenuSelection == 5){
                                System.out.println(currencyConverter(new BigDecimal("1"), USD_TO_CAD_RATE) + " CAD");
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