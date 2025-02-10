package org.com.Controller;

import org.com.Constants.CommonConstants;

import java.io.Serializable;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class GameEngine implements Serializable {
    public static void main(String[] args) {
            var console = System.console();
            console.print(CommonConstants.WELCOME_MESSAGE);
    }
}