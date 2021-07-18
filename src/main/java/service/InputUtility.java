package service;

import java.util.Scanner;

public class InputUtility {

    private static final Scanner sc = new Scanner(System.in);

    public static String inputStringValueWithScanner(String inviteMessage) {
        System.out.print(inviteMessage);
        return sc.nextLine();
    }

    public static int inputIntValueWithScanner(String inviteMessage, String messageForWrongType) {
        System.out.print(inviteMessage);
        while (!sc.hasNextInt()) {
            System.out.print(messageForWrongType + System.lineSeparator() + inviteMessage);
            sc.nextLine();
        }
        return Integer.parseInt(sc.nextLine());
    }

}
