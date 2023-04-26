package com.epam.workshops;

import java.util.Scanner;

/**
 * Contains main, starts the app
 *
 * @author Marcin Ogorzalek
 */
class AppStarter {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CoffeMaker coffeMaker = new CoffeMaker();

        while (true) {
            System.out.print("Coffee Maker> ");
            coffeMaker.callCommand(scanner.nextLine());
        }
    }
}

