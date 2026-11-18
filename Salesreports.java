/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.salesreports;
import java.util.Scanner;

public class Salesreports {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

         String[] products = {
            "Cozy Nights Monarch",
            "Restonics Barbados",
            "Sealy Phuket"
          };
        int numMonths = 3;
         int[][] sales = new int[products.length][numMonths];


        for (int i = 0; i < products.length; i++) {
        for (int j = 0; j < numMonths; j++) {
        System.out.print("Enter number of sales for month " + (j + 1) + " for " + products[i] + " mattress ");
                sales[i][j] = scanner.nextInt();
            }
          System.out.println(); 
        }

       
        IProduct reportData = new Product(products, sales, numMonths);

 
        PrintProduct printer = new PrintProduct();
        printer.PrintReport(reportData);

        scanner.close();
    }
}