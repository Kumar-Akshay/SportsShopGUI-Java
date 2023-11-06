package oop.ica.e2;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Akshay Kumar
 */
public class ASCTableModel extends AbstractTableModel {

    // Declare the Coumns for JTable
    private final String[] _columnNames = {"Code", "Title", "Description", "Price", "Quantity"};
    // Declare the ArrayList to Map the JTable
    private final ArrayList<ASCStockItem> _stockItemsList;

    /**
     * Class constructor Set the _stockItemsList
     * @param stockItemsList
     */
    public ASCTableModel(ArrayList<ASCStockItem> stockItemsList) 
    {
        _stockItemsList = stockItemsList;
    }

    /**
     * getRowCount method return the total count of row
     * @return Int
     */
    @Override
    public int getRowCount() {
        return _stockItemsList.size();
    }

    /**
     * getColumnCount method return the total count of columns
     * @return Int
     */
    @Override
    public int getColumnCount() {
        return _columnNames.length;
    }

    /**
     * getValueAt method Get the specific column value from the _stockItemsList
     *
     * @param row
     * @param column
     */
    @Override
    public Object getValueAt(int row, int column) {
        ASCStockItem ascStockItem = _stockItemsList.get(row);
        return switch (column) {
            case 0 ->
                ascStockItem.getProductCode();
            case 1 ->
                ascStockItem.getProductTitle();
            case 2 ->
                ascStockItem.getProductDescription();
            case 3 ->
                ascStockItem.getPrice();
            case 4 ->
                ascStockItem.getQuantityOnStock();
            default ->
                null;
        };
    }

    /**
     * getColumnName method return Column name based on cloumnIndex
     * @param cloumnIndex
     * @return String[]
     */
    @Override
    public String getColumnName(int cloumnIndex) {
        return _columnNames[cloumnIndex];
    }

    /**
     * setValueAt method Update the specific column value in the JTable as well
     * as _stockItemsList
     *
     * @param value
     */
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        ASCStockItem stockItem = _stockItemsList.get(rowIndex);
        // based on columnIndex I need to set the property value
        switch (columnIndex) {
            case 0 ->
                stockItem.setProductCode((String) value);
            case 1 ->
                stockItem.setProductTitle((String) value);
            case 2 ->
                stockItem.setProductDescription((String) value);
            case 3 ->
                stockItem.setPrice((Double) value);
            case 4 ->
                stockItem.setQuantityOnStock((Integer) value);
        }
        // Update the JTable by calling the fireTableCellUpdated
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    // Helper Methods
    /**
     * getColumns method return all Columns names
     *
     * @return String[]
     */
    public String[] getColumns() {
        return _columnNames;
    }

    /**
     * getStockItemAtRow method get the ASCStockItem item based on row number
     * @param row int
     * @return ASCStockItem
     */
    public ASCStockItem getStockItemAtRow(int row) {
        return _stockItemsList.get(row);
    }

    /**
     * addStockItem method Add the ASCStockItem item in the _stockItemsList and
     * Update the JTable
     *
     * @param stockItem ASCStockItem
     */
    public void addStockItem(ASCStockItem stockItem) {
        // get the _stockItemsList size
        int newIndex = _stockItemsList.size();
        _stockItemsList.add(stockItem);
        // call the fireTableRowsInserted method to update the JTable
        fireTableRowsInserted(newIndex, newIndex);
    }

    /**
     * removeStockItem method Remove the ASCStockItem item in the
     * _stockItemsList and Update the JTable
     *
     * @param row Int
     */
    public void removeStockItem(int row) {
        // remove the item from _stockItemsList
        _stockItemsList.remove(row);
        // call the fireTableRowsDeleted method to update the JTable
        fireTableRowsDeleted(row, row);
    }
}
