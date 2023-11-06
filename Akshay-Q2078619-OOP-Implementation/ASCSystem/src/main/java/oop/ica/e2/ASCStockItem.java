/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oop.ica.e2;

/**
 *  Class code from Task 3
 * @author Akshay Kumar
 */
public class ASCStockItem {

    /**
     * @var String: The Stock item product code
     */
    private String Code;

    /**
     * @var String: The Stock item product title
     */
    private String Title;

    /**
     * @var String: The Stock item product description
     */
    private String Description;

    /**
     * @var Integer: The Pounds and pence section of stock item unit price
     */
    private double Price;

    /**
     * @var Integer: The quantity of stock item in stock
     */
    private int Quantity;

    /**
     * Class constructor
     *
     * @param Code: String
     * @param Title: String
     * @param Description: String
     * @param Price: Double
     * @param Quantity: Integer
     *
     */
    public ASCStockItem(
            String Code,
            String Title,
            String Description,
            double Price,
            int Quantity) {
        this.Code = Code;
        this.Title = Title;
        this.Description = Description;
        this.Price = Price;
        this.Quantity = Quantity;
    }

    /**
     *
     * Set the stock item product code
     *
     * @method setProductCode
     * @param code String representing product code
     */
    public void setProductCode(String code) {
        this.Code = code;
    }

    /**
     *
     * Set the stock item product title
     *
     * @method setProductTitle
     * @param title String representing product title
     */
    public void setProductTitle(String title) {
        this.Title = title;
    }

    /**
     *
     * Set the stock item product description
     *
     * @method setProductDescription
     * @param description String representing product description
     */
    public void setProductDescription(String description) {
        this.Description = description;
    }

    /**
     *
     * Set the stock item unit price in Pounds
     *
     * @method setPrice
     * @param price Double representing unit price in Pounds
     */
    public void setPrice(double price) {
        this.Price = price;
    }

    /**
     *
     * Set the quantity of stock item in stock
     *
     * @method setQuantityOnStock
     * @param quantity Integer representing quantity of stock item in stock
     */
    public void setQuantityOnStock(int quantity) {
        this.Quantity = quantity;
    }

    /**
     * Get the stock item product code
     *
     * @method getProductCode
     * @return String
     */
    public String getProductCode() {
        return Code;
    }

    /**
     * Get the stock item product title
     *
     * @method getProductTitle
     * @return String
     */
    public String getProductTitle() {
        return Title;
    }

    /**
     * Get the stock item product description
     *
     * @method getProductDescription
     * @return String
     */
    public String getProductDescription() {
        return Description;
    }

    /**
     * Get the price of stock item
     *
     * @method getPrice
     * @return String
     */
    public String getPrice() {
        return String.format("%.2f", Price);
    }

    /**
     * Get the quantity of stock item in stock
     *
     * @method getQuantityOnStock
     * @return Integer
     */
    public int getQuantityOnStock() {
        return Quantity;
    }

    /**
     * Get the Photo Icon of stock item.
     *
     * @return String
     * @method GetPhotoFilename
     */
    public String GetPhotoFilename()
    {
        return this.Code+".jpg";
    }

    
    /**
     * Decrease the quantity on stock of stock item by quantityCount
     *
     * @param quantityCount
     * @method reduceQuantityOnStock
     */
    public void reduceQuantityOnStock(int quantityCount) {
        this.Quantity = (this.Quantity) - quantityCount;
    }

    /**
     * Increase the quantity on stock of stock item by quantityCount
     *
     * @param quantityCount
     * @method increaseQuantityOnStock
     */
    public void increaseQuantityOnStock(int quantityCount) {
        this.Quantity = this.Quantity + quantityCount;
    }

    /**
     * Check if stock item is on stock 
     * return true if inStock
     * return false if outOfStock
     * @method isOnStock from Task 3
     * @return Boolean
     */
    public boolean isOnStock() 
    {
        return this.Quantity > 0;
    }

    /**
     * Check if stock item is out of stock
     *
     * @method isOutOfStock from Task 3
     * @return Boolean
     */
    public boolean isOutOfStock() {
        return !this.isOnStock();
    }

}
