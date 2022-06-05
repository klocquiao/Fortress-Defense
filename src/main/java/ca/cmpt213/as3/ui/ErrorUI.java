package ca.cmpt213.as3.ui;

/**
 * ErrorUI class handles all error messages that occur
 * in the application.
 */
public class ErrorUI {
     public static void printTankPlacementError() {
         System.out.println("Error when placing tanks has occurred");
     }

     public static void printIncompatibleNumberOfTanks(int numberOfTanks) {
         System.out.println("Field is not able to support " + numberOfTanks + " tanks");
     }

     public static void printIncorrectCoordinatesEntered() {
         System.out.println("Invalid coordinates entered! Please re-enter your move...");
     }
}
