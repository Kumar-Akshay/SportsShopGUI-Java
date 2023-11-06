/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oop.ica.e2;

/**
 * Copy from other project
 * @author Akshay Kumar
 */
public class TSProduct {
    //fields
    private String skuNumber;
    private String make;
    private String model;
    private String colour;
    private String notes;
    private double price;
    private int stock;
    
    //constructor
    public TSProduct(String num, String make, String mdl, String clr, String notes, double price, int stk){
        this.skuNumber = num;
        this.make = make;
        this.model = mdl;
        this.colour = clr;
        this.notes = notes;
        this.price = price;
        this.stock = stk;
    }
    
    //accessors
    public String getSkuNumber(){ return skuNumber;}
    public String getMake(){ return make;}
    public String getModel(){ return model;}
    public String getColour(){ return colour;}
    public String getNotes(){ return notes;}
    public double getPrice(){ return price;}
    public int getStock(){ return stock;}

    //mutators
    public void setPrice(double price){this.price=price;}
    public void setStock(int stock){this.stock=stock;}
    
    //services
    public void increaseStock(){this.stock++;}
    public void decreaseStock(){this.stock--;}
}
