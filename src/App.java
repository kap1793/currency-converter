//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.*;
public class App {
    private final Scanner keyboard = new Scanner(System.in);

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
                    printConverterFromMenu();
                    int converterFromMenuSelection = promptForMenuSelection("Please choose a currency to convert.");
                    if(converterFromMenuSelection == 1){
                        while(true){
                            printConverterToMenu();
                            break;
                        }
                    }
                }
            }

        }
    }
    private int promptForMenuSelection(String prompt){
        System.out.print(prompt);
        int menuSelection;
        try{
            menuSelection = Integer.parseInt(keyboard.nextLine());
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
    }
    private void printConverterToMenu(){
        System.out.println("1:EUR");
        System.out.println("2:JPY");
        System.out.println("3:GBP");
        System.out.println("4:AUD");
        System.out.println("5:CAD");

    }
}