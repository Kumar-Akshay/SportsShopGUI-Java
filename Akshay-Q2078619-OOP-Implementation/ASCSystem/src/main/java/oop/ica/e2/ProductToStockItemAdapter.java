package oop.ica.e2;

/**
 *
 * @author Akshay Kumar
 */
public class ProductToStockItemAdapter extends ASCStockItem {
    // Declare the TSProcut object
    private final TSProduct _tsProduct;
    /**
     * Class constructor
     * Call the base class constructor and Set the TSProduct Class constructor
     * @param num
     * @param make
     * @param mdl
     * @param clr
     * @param notes
     * @param price
     * @param stk
     */
    public ProductToStockItemAdapter(String num, String make, String mdl, String clr, String notes, double price, int stk) 
    {
        // instantiate base class object
        // Combine the make + mdl to make the composite property of title
        super(num,"("+mdl+")"+make,notes,price,stk);
        
        //Assign the Product with TSProduct
        _tsProduct = new TSProduct(num,make,mdl,clr,notes,price,stk);
    }
    /**
    * @method getProductCode
    * Get the ProductCode as SkuNumber for TSProduct class
    * @return String
    */
    @Override
    public String getProductCode() {
        return _tsProduct.getSkuNumber();
    }
    /**
     * @method GetPhotoFilename
     * Get the SkuNumber append with .jpg to make the photoFileName
     * @return String
     */
    @Override
    public String GetPhotoFilename() {
        return _tsProduct.getSkuNumber()+".jpg";
    }
   
}
