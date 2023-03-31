package main;

import frames.MainMenu;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static String path = "resources/cars.txt";

    public static void main(String[] args) {


        // opens the new main menu
        new MainMenu(new Point(0, 0));
    }

}