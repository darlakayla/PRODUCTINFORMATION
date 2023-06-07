/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package productinformation;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class add_product extends javax.swing.JFrame {

    Connection con;
    Statement st;
    PreparedStatement pst;
    ResultSet rs;
    
    public String destination = "";
    File selectedFile;
    public String oldpath;
    String path;
    
    public add_product() {
        initComponents();
    }
    
    
    public void reset(){
             
        product_name.setText("");
        product_brand.setText(""); 
         product_price.setText(""); 
          product_quant.setText(""); 
         image.setIcon(null);
        
        
    }
    
     public Connection openConnection(){
    if(con == null){
    String url = "jdbc:mysql://localhost/";
    String dbName = "productinformation";
    String driver = "com.mysql.jdbc.Driver";
    String userName = "root";
    String password = "";

    try{
    Class.forName(driver);
    this.con = (Connection) DriverManager.getConnection(url+dbName, userName, password);

    System.out.println("Connection Successful");

    }catch(ClassNotFoundException | SQLException sqle){
    System.out.println("Connection Failed!\n"+sqle+"");
    }
    }

    return con;

        }
     
      public void addProduct(){  
        
        try{
              con = DriverManager.getConnection("jdbc:mysql://localhost:3306/productinformation", "root", "");
            String sql = "INSERT INTO company_products ( product_name,product_brand, product_price,product_qty,product_category, img_pic)values (?,?,?,?,?,?)"; 
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, product_name.getText());
            ps.setString(2, product_brand.getText());  
            ps.setString(3, product_price.getText());
             ps.setString(4, product_quant.getText());
            ps.setString(5, product_category.getSelectedItem().toString());
            ps.setString(6, destination);
            ps.executeUpdate();           
            Files.copy(selectedFile.toPath(), new File(destination).toPath(), StandardCopyOption.REPLACE_EXISTING);  
              reset();
               
              
              
              if (product_name.getText().isEmpty() || product_brand.getText().isEmpty() || product_price.getText().isEmpty()){
                 JOptionPane.showMessageDialog(null, "All fields are required");
             }
             
              else if(product_category.getSelectedItem().equals("Select a category")){
                  JOptionPane.showMessageDialog(null, "Please Select Category");
             }
             
             else{
                  
                 JOptionPane.showMessageDialog(null,"Save Succesfully");
                 reset();
             }
            
            
              
        }
        
        catch(Exception e){
          
            System.out.println(e);   
        }
    }
 public void imageUpdater(String existingFilePath, String newFilePath){
        File existingFile = new File(existingFilePath);
        if (existingFile.exists()) {
            String parentDirectory = existingFile.getParent();
            File newFile = new File(newFilePath);
            String newFileName = newFile.getName();
            File updatedFile = new File(parentDirectory, newFileName);
            existingFile.delete();
            try {
                Files.copy(newFile.toPath(), updatedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Image updated successfully.");
            } catch (IOException e) {
                System.out.println("Error occurred while updating the image: ");
            }
        } else {
            try{
                Files.copy(selectedFile.toPath(), new File(destination).toPath(), StandardCopyOption.REPLACE_EXISTING);
            }catch(IOException e){
                System.out.println("Error on update!");
            }
        }
   }
 
        public static int getHeightFromWidth(String imagePath, int desiredWidth) {
        try {
            // Read the image file
            File imageFile = new File(imagePath);
            BufferedImage image = ImageIO.read(imageFile);
            
            // Get the original width and height of the image
            int originalWidth = image.getWidth();
            int originalHeight = image.getHeight();
            
            // Calculate the new height based on the desired width and the aspect ratio
            int newHeight = (int) ((double) desiredWidth / originalWidth * originalHeight);
            
            return newHeight;
        } catch (IOException ex) {
            System.out.println("No image found!");
        }
        
        return -1;
    }
        
        public  ImageIcon ResizeImage(String ImagePath, byte[] pic, JLabel label) {
    ImageIcon MyImage = null;
        if(ImagePath !=null){
            MyImage = new ImageIcon(ImagePath);
        }else{
            MyImage = new ImageIcon(pic);
        }
        
    int newHeight = getHeightFromWidth(ImagePath, label.getWidth());

    Image img = MyImage.getImage();
    Image newImg = img.getScaledInstance(label.getWidth(), newHeight, Image.SCALE_SMOOTH);
    ImageIcon image = new ImageIcon(newImg);
    return image;
}
        
         public int FileChecker(String path){
        File file = new File(path);
        String fileName = file.getName();
        
        Path filePath = Paths.get("src/image", fileName);
        boolean fileExists = Files.exists(filePath);
        
        if (fileExists) {
            return 1;
        } else {
            return 0;
        }
    
    }
            public void img(){
    JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                
                
                
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    try {
                        selectedFile = fileChooser.getSelectedFile();
                        destination = "src/img/" + selectedFile.getName();
                        path  = selectedFile.getAbsolutePath();
                        
                        
                        if(FileChecker(path) == 1){
                          JOptionPane.showMessageDialog(null, "File Already Exist, Rename or Choose another!");
                            destination = "";
                            path="";
                        }else{
                            image.setIcon(ResizeImage(path, null, image));
                            System.out.println(""+destination);
                           browse.setVisible(true);
                            
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "FILE ERROR"+ex);
                    }
                }
    
            }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        back = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        product_name = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        product_brand = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        product_price = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        product_quant = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        product_category = new javax.swing.JComboBox<>();
        add = new javax.swing.JButton();
        back1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        image = new javax.swing.JLabel();
        browse = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 204, 102));
        jPanel1.setLayout(null);

        jPanel2.setBackground(new java.awt.Color(51, 102, 255));
        jPanel2.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Algerian", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 0));
        jLabel1.setText("SYSTEM");
        jPanel2.add(jLabel1);
        jLabel1.setBounds(100, 270, 160, 40);

        jLabel2.setFont(new java.awt.Font("Algerian", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 0));
        jLabel2.setText("PRODUCT");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(90, 150, 180, 40);

        jLabel3.setFont(new java.awt.Font("Algerian", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 0));
        jLabel3.setText("INFORMATION");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(50, 210, 250, 40);

        back.setBackground(new java.awt.Color(255, 255, 51));
        back.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 18)); // NOI18N
        back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/back.PNG"))); // NOI18N
        back.setText("BACK");
        back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backActionPerformed(evt);
            }
        });
        jPanel2.add(back);
        back.setBounds(20, 440, 140, 40);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(0, 0, 350, 500);

        jLabel4.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 0));
        jLabel4.setText("PRODUCT NAME:");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(400, 160, 150, 40);
        jPanel1.add(product_name);
        product_name.setBounds(550, 160, 250, 40);

        jLabel5.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 0));
        jLabel5.setText("PRODUCT BRAND:");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(400, 210, 150, 40);
        jPanel1.add(product_brand);
        product_brand.setBounds(550, 210, 250, 40);

        jLabel6.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 0));
        jLabel6.setText("PRODUCT PRICE:");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(400, 260, 150, 40);
        jPanel1.add(product_price);
        product_price.setBounds(550, 260, 250, 40);

        jLabel7.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 0));
        jLabel7.setText("AVAILABLE  QTY.");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(400, 310, 150, 40);
        jPanel1.add(product_quant);
        product_quant.setBounds(550, 310, 250, 40);

        jLabel8.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 0));
        jLabel8.setText("CATEGORY:");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(400, 360, 150, 40);

        product_category.setFont(new java.awt.Font("Agency FB", 0, 18)); // NOI18N
        product_category.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select a category", "Drinks", "Gadgets", "Foods", "T-shirts", "Cosmetics", "Appliances", "Shoes", "Bicycles", " " }));
        jPanel1.add(product_category);
        product_category.setBounds(550, 360, 250, 40);

        add.setBackground(new java.awt.Color(255, 255, 51));
        add.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 18)); // NOI18N
        add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/addProduct.PNG"))); // NOI18N
        add.setText("ADD PRODUCT");
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });
        jPanel1.add(add);
        add.setBounds(590, 430, 210, 40);

        back1.setBackground(new java.awt.Color(255, 255, 51));
        back1.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 18)); // NOI18N
        back1.setText("CLEAR");
        back1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                back1ActionPerformed(evt);
            }
        });
        jPanel1.add(back1);
        back1.setBounds(440, 430, 140, 40);

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        image.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.add(image, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 130, 110));

        jPanel1.add(jPanel3);
        jPanel3.setBounds(650, 30, 130, 110);

        browse.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        browse.setText("BROWSE");
        browse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseActionPerformed(evt);
            }
        });
        jPanel1.add(browse);
        browse.setBounds(530, 100, 100, 30);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 836, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
       new HOME().setVisible(true);
       this.dispose();
    }//GEN-LAST:event_backActionPerformed

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
        addProduct();
    }//GEN-LAST:event_addActionPerformed

    private void back1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_back1ActionPerformed
       product_name.setText("");
       product_brand.setText("");
       product_price.setText("");
       product_quant.setText("");
       product_category.setSelectedItem("Select a category");
      
    }//GEN-LAST:event_back1ActionPerformed

    private void browseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseActionPerformed
      img();
    }//GEN-LAST:event_browseActionPerformed

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
            java.util.logging.Logger.getLogger(add_product.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(add_product.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(add_product.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(add_product.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new add_product().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add;
    private javax.swing.JButton back;
    private javax.swing.JButton back1;
    private javax.swing.JButton browse;
    private javax.swing.JLabel image;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField product_brand;
    private javax.swing.JComboBox<String> product_category;
    private javax.swing.JTextField product_name;
    private javax.swing.JTextField product_price;
    private javax.swing.JTextField product_quant;
    // End of variables declaration//GEN-END:variables
}
