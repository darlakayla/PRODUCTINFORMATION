/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package productinformation;

import config.dbconnector;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;



public class listproducts extends javax.swing.JFrame {

    Connection con;
    Statement st;
    PreparedStatement pst;
    ResultSet rs;
   
     public String destination = "";
    File selectedFile;
    public String oldpath;
    String path;
    
    
    public listproducts() {
        initComponents();
        getListProducts("SELECT * FROM company_products");
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
     
      public void getListProducts(String qry){

try{

pst = openConnection().prepareStatement(qry);
rs = pst.executeQuery();
DefaultTableModel tm = (DefaultTableModel)product_table.getModel();
tm.setColumnIdentifiers(new Object[]{"ID", "Name", "Brand", "Price","Availability", "Category"});
tm.setRowCount(0);

while(rs.next()){
Object obj[] = {rs.getInt("ID"), rs.getString("product_name"), rs.getString("product_brand"), rs.getString("product_price"), rs.getString("product_qty"), rs.getString("product_category")};
tm.addRow(obj);
}

}catch(SQLException e){
System.out.println(e);
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
      
       public ImageIcon ResizeImage(String ImagePath, byte[] pic, JLabel label) {
        ImageIcon MyImage = null;
        if (ImagePath != null) {
            MyImage = new ImageIcon(ImagePath);
        } else {
            MyImage = new ImageIcon(pic);
        }

        int newHeight = getHeightFromWidth(ImagePath, label.getWidth());

        Image img = MyImage.getImage();
        Image newImg = img.getScaledInstance(label.getWidth(), newHeight, Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImg);
        return image;
    }
   
     
       public void table() {
        int row = product_table.getSelectedRow();
        int cc = product_table.getSelectedColumn();
        ImageIcon format;
        String tc = product_table.getModel().getValueAt(row, 0).toString();
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/productinformation", "root", "");
            String sql = "SELECT * from company_products where ID=" + tc + "";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("product_name");
                String brand = rs.getString("product_brand");
                String pprice = rs.getString("product_price");
                String quant = rs.getString("product_qty");
                String category = rs.getString("product_category");
                  
                image.setIcon(ResizeImage(rs.getString("img_pic"), null, image));
                oldpath = rs.getString("img_pic");
                ID.setText("" + id);
                 product_name.setText(name);
                  product_brand.setText(brand);
                 product_price.setText(pprice);
                 product_quant.setText(quant);
                 product_category.setText(category);
                 

            }
            ps.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
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
        public void update(){
        int result=0;
         try {
         con = DriverManager.getConnection("jdbc:mysql://localhost:3306/productinformation", "root", "");
         int row = product_table.getSelectedRow();
         String value = (product_table.getModel().getValueAt(row, 0).toString());
         String sql = "UPDATE company_products SET product_name=?,product_brand =? ,product_price=?,product_qty =?,product_category =?,img_pic=? where ID="+value;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, product_name.getText());
             ps.setString(2, product_brand.getText()); 
            ps.setString(3, product_price.getText()); 
            ps.setString(4, product_quant.getText());
            ps.setString(5, product_category.getText());
             
            ps.setString(6, destination);
            ps.execute();
             imageUpdater(oldpath, path);
           
           File existingFile = new File(oldpath);
            if (existingFile.exists()) {
                existingFile.delete();
            }
           reset();
           JOptionPane.showMessageDialog(null, "Successfully Updated!");
           }catch(SQLException e){
             JOptionPane.showMessageDialog(null,"Database Connection Error!"+e);
           }
     }
      
       
       
         

           public void displayData(){
       
        try{
       
            dbconnector dbc = new dbconnector();
            ResultSet rs = dbc.getdata("SELECT ID as 'ID', product_name as 'Product Name',product_brand as 'Brand', product_price as 'Price', product_qty as 'Quantity', product_category as 'Category  ' FROM company_products");
           
            product_table.setModel(DbUtils.resultSetToTableModel(rs));
       
        }catch(SQLException ex){
            System.out.println("Error Message: "+ex);
       
        }
    }
           public void reset(){
        
        ID.setText("");
        product_name.setText("");
        product_brand.setText(""); 
         product_price.setText(""); 
          product_quant.setText(""); 
         image.setIcon(null);
        
        
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
           
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        back = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        product_name = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        product_price = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        product_quant = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        product_table = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        ID = new javax.swing.JTextField();
        product_brand = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        panelimg = new javax.swing.JPanel();
        image = new javax.swing.JLabel();
        product_category = new javax.swing.JTextField();
        update = new javax.swing.JButton();
        browse = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 204, 204));
        jPanel1.setLayout(null);

        jPanel2.setBackground(new java.awt.Color(255, 153, 153));
        jPanel2.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Agency FB", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("SYSTEM");
        jPanel2.add(jLabel1);
        jLabel1.setBounds(110, 320, 130, 40);

        jLabel3.setFont(new java.awt.Font("Agency FB", 1, 48)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("INFORMATION");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(70, 270, 220, 40);

        jLabel10.setFont(new java.awt.Font("Agency FB", 1, 48)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("PRODUCT");
        jPanel2.add(jLabel10);
        jLabel10.setBounds(100, 220, 150, 40);

        back.setBackground(new java.awt.Color(255, 255, 255));
        back.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/back.PNG"))); // NOI18N
        back.setText("BACK");
        back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backActionPerformed(evt);
            }
        });
        jPanel2.add(back);
        back.setBounds(20, 10, 120, 50);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(0, 0, 350, 680);

        jLabel4.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("PRODUCT NAME");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(470, 340, 230, 40);
        jPanel1.add(product_name);
        product_name.setBounds(470, 380, 230, 40);

        jLabel6.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("PRODUCT PRICE");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(740, 240, 230, 40);
        jPanel1.add(product_price);
        product_price.setBounds(740, 280, 230, 40);

        jLabel7.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("AVAILABLE  QTY.");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(740, 340, 230, 40);
        jPanel1.add(product_quant);
        product_quant.setBounds(740, 380, 230, 40);

        product_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        product_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                product_tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(product_table);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(550, 10, 440, 220);

        jLabel11.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("PRODUCT ID");
        jPanel1.add(jLabel11);
        jLabel11.setBounds(470, 240, 230, 40);

        ID.setEditable(false);
        jPanel1.add(ID);
        ID.setBounds(470, 280, 230, 40);
        jPanel1.add(product_brand);
        product_brand.setBounds(470, 490, 230, 40);

        jLabel8.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("CATEGORY");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(750, 450, 230, 40);

        jLabel12.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("PRODUCT BRAND");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(470, 450, 230, 40);

        panelimg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        image.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelimg.add(image, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 130, 120));

        jPanel1.add(panelimg);
        panelimg.setBounds(400, 20, 130, 120);
        jPanel1.add(product_category);
        product_category.setBounds(750, 490, 230, 40);

        update.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        update.setText("UPDATE");
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });
        jPanel1.add(update);
        update.setBounds(420, 180, 90, 31);

        browse.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        browse.setText("BROWSE");
        browse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseActionPerformed(evt);
            }
        });
        jPanel1.add(browse);
        browse.setBounds(420, 150, 90, 31);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1001, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 544, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
        new HOME().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_backActionPerformed

    private void product_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_product_tableMouseClicked
       table(); 
    }//GEN-LAST:event_product_tableMouseClicked

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed
      update();
    }//GEN-LAST:event_updateActionPerformed

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
            java.util.logging.Logger.getLogger(listproducts.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(listproducts.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(listproducts.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(listproducts.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new listproducts().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ID;
    private javax.swing.JButton back;
    private javax.swing.JButton browse;
    private javax.swing.JLabel image;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelimg;
    private javax.swing.JTextField product_brand;
    private javax.swing.JTextField product_category;
    private javax.swing.JTextField product_name;
    private javax.swing.JTextField product_price;
    private javax.swing.JTextField product_quant;
    private javax.swing.JTable product_table;
    private javax.swing.JButton update;
    // End of variables declaration//GEN-END:variables
}
