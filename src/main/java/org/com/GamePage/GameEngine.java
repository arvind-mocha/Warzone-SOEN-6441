package org.com.GamePage;

import org.com.Constants.CommonConstants;

import java.util.Scanner;
import java.io.Serializable;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class GameEngine implements Serializable {
    public static void main(String[] args) {
        Scanner l_scanner = new Scanner(System.in);

        var console = System.console();
        console.print(CommonConstants.WELCOME_MESSAGE);
        System.out.println(CommonConstants.HELP_MESSAGE);


    }
}