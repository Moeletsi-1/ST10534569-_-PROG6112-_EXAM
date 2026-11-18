/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salesreports;

/**
 *
 * @author moele
 */
class PrintProduct {
    
    public void PrintReport(IProduct product) {
        PrintHeader();
        PrintBody(product);
        PrintFooter();
    }

    private void PrintHeader() {
        System.out.println("PRODUCT SALES");
        System.out.println("----------------------------------------------------------------------");
    }

    private void PrintBody(IProduct product) {
        System.out.printf("%-26s %d%n", "TOTAL UNITS SOLD", product.GetTotalProductsSold());
        System.out.printf("%-26s %.0f%n", "AVERAGE UNITS PER PRODUCT", product.GetAverageProductSold());
        System.out.println("MOST POPULAR PRODUCT: " + product.GetMostPopularProduct());
    }

    private void PrintFooter() {
        System.out.println("----------------------------------------------------------------------");
    }
}

