package com.mycompany.salesreports;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author moele
 */
import java.util.Scanner;

interface IProduct {
    int GetTotalProductsSold();
    double GetAverageProductSold();
    String GetMostPopularProduct();
}

class Product implements IProduct {
    private String[] products;  
    private int[][] sales;       
    private int numMonths;

    public Product(String[] products, int[][] sales, int numMonths) {
        this.products = products;
        this.sales = sales;
        this.numMonths = numMonths;
    }

    @Override
    public int GetTotalProductsSold() {
        int grandTotal = 0;
        for (int i = 0; i < sales.length; i++) {
            for (int j = 0; j < sales[i].length; j++) {
                grandTotal += sales[i][j];
            }
        }
        return grandTotal;
    }

    @Override
    public double GetAverageProductSold() {
        int grandTotal = GetTotalProductsSold();
        int totalEntries = products.length * numMonths;
        return (double) grandTotal / totalEntries;
    }

    @Override
    public String GetMostPopularProduct() {
        int maxTotalSales = 0;
        int mostPopularIndex = 0;

        for (int i = 0; i < sales.length; i++) {
            int productTotal = 0;
            for (int j = 0; j < sales[i].length; j++) {
                productTotal += sales[i][j];
            }
            
            if (productTotal > maxTotalSales) {
                maxTotalSales = productTotal;
                mostPopularIndex = i;
            }
        }

        return products[mostPopularIndex] + "  " + maxTotalSales;
    }
}
