package org.example;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("Please provide the XML absolute path");

        Scanner sc = new Scanner(System.in);
        String xmlFilePath = sc.next();
        sc.close();

        Solution sol = new Solution(xmlFilePath);
        sol.build();
    }
}

