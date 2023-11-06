package oop.ica.e2;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;

/**
 *
 * @author Akshay Kumar
 */
public class SportsShopGUI extends javax.swing.JFrame {

    /**
     * @constant String: The delimiter for stock item props in string
     */
    private static final String DELIMITER = ",";

    /**
     * @constant String: project relative file patch
     */
    public static final String RELATIVE_FILE_PATH = "src/main/java/oop/ica/e2/";

    /**
     * @constant String: Input data file name StockItem AND TSProduct
     */
    private static final String STOCK_INPUT_FILE_PATH = "files/AsherSportsConsortium3.csv";
    private static final String PRODUCTS_INPUT_FILE_PATH = "files/ts_products.txt";

    /**
     * @constant String: Output data file name
     */
    private static final String OUTPUT_FILE_PATH = "files/asc_output.txt";

    /**
     * @constant ArrayList<ASCStockItem>: Create the object of _stockItemsList
     */
    private ArrayList<ASCStockItem> _stockItemsList = new ArrayList<>();

    /**
     * @constant ASCTableModel: Create the object of ASCTableModel
     */
    private ASCTableModel model = null;

    /**
     * Creates new form SportsShopGUI
     *
     * @throws java.io.FileNotFoundException
     */
    public SportsShopGUI() throws FileNotFoundException {
        initComponents();
        try {
            // Load the Stock Items and TSProduct in the Array List
            LoadStockItems();
            LoadProducts();
            // Check if _stockItemsList is empty return error message
            if (_stockItemsList.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Data Error: Unable to Proceed");
                System.exit(0);
            }
            // Create the ASCTableModel object  and Set the ascStockItem JTable
            model = new ASCTableModel(_stockItemsList);
            ascStockItem.setModel(model);
            // Add a ListSelectionListener to the JTable to display the selected photo
            ascStockItem.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
                int rowIndex = ascStockItem.getSelectedRow();
                if (rowIndex >= 0) {
                    ASCStockItem selectedItem = model.getStockItemAtRow(rowIndex);
                    String imageFileName = selectedItem.GetPhotoFilename();
                    String path = RELATIVE_FILE_PATH + "/photos/" + imageFileName;
                    if ((new File(path)).exists()) 
                    {
                        // Load the Image into BufferImage and Set it on UI
                        BufferedImage image = loadImage(path);
                        photoLabel.setIcon(new ImageIcon(image));
                        itemLabel.setText(selectedItem.getProductTitle());
                    } else 
                    {
                        // If Image does not found set the Image not available
                        itemLabel.setText("");
                        photoLabel.setIcon(new ImageIcon());
                        photoLabel.setText("Image not available");
                    }
                }
            });
        } catch (FileNotFoundException ex) {
            // warn user with exception of File Read Error
            JOptionPane.showMessageDialog(null, "File does not exist, Error: Unable to Proceed");
            System.exit(0);
        }
    }
    
    /**
     * Bring the Task3 method Build and array list of stock item out of string
     * LoadStockItems() from Task 3
     *
     */
    
    private void LoadStockItems() throws FileNotFoundException {

        String pathname = RELATIVE_FILE_PATH + STOCK_INPUT_FILE_PATH;
        File inputFile = new File(pathname);

        if (!inputFile.exists() || !inputFile.isFile()) {
            JOptionPane.showMessageDialog(null, "Stock Items data file (" + STOCK_INPUT_FILE_PATH + ") does not exist as a data file.");
            System.exit(0);
        }

        try (Scanner fileScanner = new Scanner(inputFile)) 
        {
            this.buildStockItemArrayList(fileScanner);
        }

        if (this._stockItemsList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No data available", "Error", JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }
    }

    /**
     * Bring the Task3 method Build and array list of stock item out of string
     * loaded from file
     *
     * @param fileScanner : Scanner
     */
    private void buildStockItemArrayList(Scanner fileScanner) {
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine().trim();
            if (!line.isEmpty()) {
                var item = this.createStockItemFromLine(line);
                _stockItemsList.add(item);
            }
        }
    }

    /**
     * Bring the Task3 method Create a single StockItem instance from one line
     * of of file string
     *
     * @param line: String
     * @return ASCStockItem object
     */
    private ASCStockItem createStockItemFromLine(String line) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String[] lineArray = line.split(DELIMITER);
        String productCode = lineArray[0];
        String title = lineArray[1];
        String description = lineArray[2];
        int pound = Integer.parseInt(lineArray[3]);
        String pence = decimalFormat.format(Double.parseDouble(lineArray[4]) / 100);
        double Price = pound + Double.parseDouble(pence);
        int Quantity = Integer.parseInt(lineArray[5]);

        return new ASCStockItem(productCode, title, description, Price, Quantity);
    }

    private void LoadProducts() {
        //instantiate local object
        String pathname = RELATIVE_FILE_PATH + PRODUCTS_INPUT_FILE_PATH;
        File inputFile = new File(pathname);
        Scanner fileScanner;

        //check if referenced file exists and is a file
        if (inputFile.exists() && inputFile.isFile()) {
            try {
                //link file to file scanner to the file object
                fileScanner = new Scanner(inputFile);

                //loop through input file
                while (fileScanner.hasNextLine()) {
                    //read line
                    String line = fileScanner.nextLine();

                    //check that line is not just whitespace
                    if (line.trim().length() > 0) {

                        //not blank so parse tokens
                        String skuNumber = line.split(DELIMITER)[0];
                        String make = line.split(DELIMITER)[1];
                        String model = line.split(DELIMITER)[2];
                        String colour = line.split(DELIMITER)[3];
                        String notes = line.split(DELIMITER)[4];
                        double price = Double.parseDouble(line.split(DELIMITER)[5]);
                        int stock = Integer.parseInt(line.split(DELIMITER)[6]);

                        //add new object to arraylist
                        _stockItemsList.add(
                                new ProductToStockItemAdapter(skuNumber, make, model, colour, notes, price, stock)
                        );
                    }
                }
                //close the file
                fileScanner.close();

                //Otherwise warn user file is invalid
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Input File Error.");
                System.exit(0);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Stock Items data file (" + PRODUCTS_INPUT_FILE_PATH + ") does not exist as a data file.");
            System.exit(0);
        }
    }

    /**
     * Bring the Task3 method SaveData Save An Array of ASCTableModel to output
     * file
     *
     * @throws IOException
     */
    public void saveData() throws IOException {
        Path path = Paths.get(RELATIVE_FILE_PATH + OUTPUT_FILE_PATH);
        Files.deleteIfExists(path);
        try (BufferedOutputStream buffer = new BufferedOutputStream(
                Files.newOutputStream(path, CREATE, WRITE)
        )) {
            byte outputStringBytes[] = this.buildOutputString().getBytes();
            buffer.write(outputStringBytes, 0, outputStringBytes.length);
        }
    }

    /**
     * Bring the Task3 method buildOutputString Build a string of stock items
     * from the instance array of ASCTableModel
     *
     * @return outputString: String
     */
    private String buildOutputString() 
    {
        String outputString = "";
        for (int i = 0; i < this._stockItemsList.size(); i++) {
            outputString += this.formatStockItemString(
                    _stockItemsList.get(i)
            );
        }
        return outputString;
    }

    /**
     * Bring the Task3 method formatStockItemString Create a single line string
     * representation of a ASCTableModel from an instance
     *
     * @param stockItem: StockItem
     * @return String
     */
    private String formatStockItemString(ASCStockItem stockItem) {
        double price = Double.parseDouble(stockItem.getPrice());
        int pounds = (int) price;
        int pence = (int) Math.round((price - pounds) * 100);
        return new Formatter().format("%s,%s,%s,%s,%s,%s\r%n",
                stockItem.getProductCode(), stockItem.getProductTitle(),
                stockItem.getProductDescription(), pounds, pence, stockItem.getQuantityOnStock()
        ).toString();
    }

    /**
     * @Method loadImage Load the Image from the file
     * @param String: fileName
     * @return BufferedImage
     */
    private BufferedImage loadImage(String fileName) {
        try {
            return ImageIO.read(new File(fileName));

        } catch (IOException e) {
            System.out.println("Failed to load image: " + fileName);
            return null;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ascStockItem = new javax.swing.JTable();
        titleLabel2 = new javax.swing.JLabel();
        photoPanel = new javax.swing.JPanel();
        photoLabel = new javax.swing.JLabel();
        buyButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        BuyXButton = new javax.swing.JButton();
        AddYButton = new javax.swing.JButton();
        quitButton = new javax.swing.JButton();
        itemLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        titleLabel.setText("Ashers Sports Consortium");

        ascStockItem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Code", "Title", "Description", "Price", "Quantity"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        ascStockItem.setColumnSelectionAllowed(true);
        ascStockItem.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(ascStockItem);
        ascStockItem.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        if (ascStockItem.getColumnModel().getColumnCount() > 0) {
            ascStockItem.getColumnModel().getColumn(0).setMinWidth(20);
            ascStockItem.getColumnModel().getColumn(0).setMaxWidth(100);
            ascStockItem.getColumnModel().getColumn(4).setMinWidth(20);
            ascStockItem.getColumnModel().getColumn(4).setMaxWidth(60);
        }

        titleLabel2.setText("Item Photo");

        photoPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        photoPanel.setMaximumSize(new java.awt.Dimension(300, 300));
        photoPanel.setMinimumSize(new java.awt.Dimension(300, 300));

        photoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        photoLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        javax.swing.GroupLayout photoPanelLayout = new javax.swing.GroupLayout(photoPanel);
        photoPanel.setLayout(photoPanelLayout);
        photoPanelLayout.setHorizontalGroup(
            photoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(photoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(photoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        photoPanelLayout.setVerticalGroup(
            photoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, photoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(photoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                .addContainerGap())
        );

        buyButton.setText("Buy");
        buyButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buyButtonMouseClicked(evt);
            }
        });

        addButton.setText("Add");
        addButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addButtonMouseClicked(evt);
            }
        });

        BuyXButton.setText("BuyX");
        BuyXButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuyXButtonActionPerformed(evt);
            }
        });

        AddYButton.setText("AddY");
        AddYButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddYButtonActionPerformed(evt);
            }
        });

        quitButton.setBackground(new java.awt.Color(255, 0, 51));
        quitButton.setForeground(new java.awt.Color(255, 255, 255));
        quitButton.setText("Quit");
        quitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                quitButtonMouseClicked(evt);
            }
        });
        quitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitButtonActionPerformed(evt);
            }
        });

        itemLabel.setFont(new java.awt.Font("Segoe UI", 1, 8)); // NOI18N
        itemLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        itemLabel.setText(" ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(itemLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(titleLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(photoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8))))
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(buyButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BuyXButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AddYButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(quitButton))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(titleLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(photoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(titleLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(itemLabel)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buyButton)
                    .addComponent(addButton)
                    .addComponent(BuyXButton)
                    .addComponent(AddYButton)
                    .addComponent(quitButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void quitButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_quitButtonMouseClicked
        try {
            // save the _stockItemList before quit
            saveData();
        } catch (IOException ex) {
            return;
        }
        System.exit((0));
    }//GEN-LAST:event_quitButtonMouseClicked

    /**
     * @Method RowSelectionIndex get the selected row index
     * @return Int
     */
    private int RowSelectionIndex() {
        int rowIndex = ascStockItem.getSelectedRow();
        if (rowIndex < 0) {
            JOptionPane.showMessageDialog(this, "Please select an item from table.",
                    "No Item Selected", JOptionPane.ERROR_MESSAGE);
        }
        return rowIndex;
    }

    /**
     * @Method RowSelectionWarning Show the low stock warning based on selected
     * item
     * @return ASCStockItem
     */
    private ASCStockItem RowSelectionWarning() {
        int rowIndex = RowSelectionIndex();
        if (rowIndex < 0) {
            return null;
        }
        var item = model.getStockItemAtRow(rowIndex);
        if (item.getQuantityOnStock() < 5) {
            String info = String.format("'%s' has only %d units of stock", item.getProductTitle(), item.getQuantityOnStock());
            JOptionPane.showMessageDialog(this, info, "Low Stock Warning", JOptionPane.WARNING_MESSAGE);
        }
        return item;
    }

    /**
     * @Method GetQuantityOptions
     * @param item
     * @param status
     * @return String[]
     */
    private String[] GetQuantityOptions(ASCStockItem item, String status) {
        if ("add".equals(status)) {
            return new String[]{"5", "6", "7", "8", "9", "10"};
        }
        int Quantity = item.getQuantityOnStock();
        String[] options = new String[Quantity];
        for (int i = 0; i < Quantity; i++) {
            options[i] = Integer.toString(i + 1);
        }
        return options;
    }


    private void buyButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buyButtonMouseClicked

        ASCStockItem item = RowSelectionWarning();
        if (item == null) {
            return;
        }
        int rowIndex = RowSelectionIndex();
        if (item.isOnStock()) {
            item.reduceQuantityOnStock(1);
            model.setValueAt(item.getQuantityOnStock(), rowIndex, 4);
            String info = String.format("Item: %s\nPrice: %s\nUnit bought: %d\nStock remaining : %d",
                    item.getProductTitle(), item.getPrice(), 1, item.getQuantityOnStock());
            JOptionPane.showMessageDialog(this, info, "Confirmation of Sale", JOptionPane.INFORMATION_MESSAGE);

        } else {
            JOptionPane.showMessageDialog(this, "Seleted Item out of stock", "No Sale", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_buyButtonMouseClicked

    private void addButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addButtonMouseClicked
        ASCStockItem item = RowSelectionWarning();
        if (item == null) {
            return;
        }
        int rowIndex = RowSelectionIndex();
        item.increaseQuantityOnStock(1);
        model.setValueAt(item.getQuantityOnStock(), rowIndex, 4);
        String info = String.format("Item: %s\nPrice: %s\nUnit Added: %d\nStock remaining : %d",
                item.getProductTitle(), item.getPrice(), 1, item.getQuantityOnStock());
        JOptionPane.showMessageDialog(this, info, "Stock Added", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_addButtonMouseClicked

    private void BuyXButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuyXButtonActionPerformed

        ASCStockItem item = RowSelectionWarning();
        if (item == null) {
            return;
        }
        
        if (item.isOutOfStock()) 
        {
            JOptionPane.showMessageDialog(this, "Seleted Item out of stock", "No Sale", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
                // retrive the quantity options
        String[] options = GetQuantityOptions(item, "");
         // creating the popup dialog for quantity
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("<html>Please select the quantity you wish to buy of: <BR>" + item.getProductTitle() + "</html>");
        panel.add(label, BorderLayout.PAGE_START);
         // adding the combobox
        JComboBox<String> comboBox = new JComboBox<>(options);
        panel.add(comboBox, BorderLayout.CENTER);
         // show the dialog to user and takes the user input as quantity
        int selectedOption = JOptionPane.showConfirmDialog(this, panel, "Quantity to purchase", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (selectedOption != JOptionPane.CLOSED_OPTION && selectedOption == JOptionPane.OK_OPTION) {
            int rowIndex = RowSelectionIndex();
            String selectedValue = (String) comboBox.getSelectedItem();
            int buyQuantity = Integer.parseInt(selectedValue);
            item.reduceQuantityOnStock(buyQuantity);
            model.setValueAt(item.getQuantityOnStock(), rowIndex, 4);
            String info = String.format("Item: %s\nPrice: %s\nUnit Bought: %d\nStock remaining : %d",
                    item.getProductTitle(), item.getPrice(), buyQuantity, item.getQuantityOnStock());
            JOptionPane.showMessageDialog(this, info, "Confirmation of Sale", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_BuyXButtonActionPerformed

    private void AddYButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddYButtonActionPerformed
        ASCStockItem item = RowSelectionWarning();
        if (item == null) {
            return;
        }

        // retrive the quantity options
        String[] options = GetQuantityOptions(item, "add");
        // creating the popup dialog for quantity
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("<html>Please select the quantity you wish to add of: <BR>" + item.getProductTitle() + "</html>");
        panel.add(label, BorderLayout.PAGE_START);
        // adding the combobox
        JComboBox<String> comboBox = new JComboBox<>(options);
        panel.add(comboBox, BorderLayout.CENTER);
        // show the dialog to user and takes the user input as quantity
        int selectedOption = JOptionPane.showConfirmDialog(this, panel, "Quantity to Add", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (selectedOption != JOptionPane.CLOSED_OPTION && selectedOption == JOptionPane.OK_OPTION) {
            int rowIndex = RowSelectionIndex();
            // retrive selection item and update the quantity in Array list and Jtable
            String selectedValue = (String) comboBox.getSelectedItem();
            int addQuantity = Integer.parseInt(selectedValue);
            item.increaseQuantityOnStock(addQuantity);
            model.setValueAt(item.getQuantityOnStock(), rowIndex, 4);
            String info = String.format("Item: %s\nPrice: %s\nUnit Added: %d\nStock remaining : %d",
                    item.getProductTitle(), item.getPrice(), addQuantity, item.getQuantityOnStock());
            JOptionPane.showMessageDialog(this, info, "Stock Added", JOptionPane.INFORMATION_MESSAGE);

        }

    }//GEN-LAST:event_AddYButtonActionPerformed

    private void quitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quitButtonActionPerformed

        try {
            // Save the Array List into the File
            saveData();
            System.exit(0);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "File Save Error", "Quit", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_quitButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SportsShopGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SportsShopGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SportsShopGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SportsShopGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new SportsShopGUI().setVisible(true);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(SportsShopGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddYButton;
    private javax.swing.JButton BuyXButton;
    private javax.swing.JButton addButton;
    private javax.swing.JTable ascStockItem;
    private javax.swing.JButton buyButton;
    private javax.swing.JLabel itemLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel photoLabel;
    private javax.swing.JPanel photoPanel;
    private javax.swing.JButton quitButton;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JLabel titleLabel2;
    // End of variables declaration//GEN-END:variables
}
