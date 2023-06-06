/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package productinformation;

import static com.sun.xml.internal.fastinfoset.alphabet.BuiltInRestrictedAlphabets.table;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;


public class modifyProduct extends javax.swing.JFrame {
    
    DefaultTableModel model;

    Connection con;
    Statement st;
    PreparedStatement pst;
    ResultSet rs;
   
    public modifyProduct() {
        initComponents();
        getListProducts("SELECT * FROM company_products");
    }
    
    
     public void filter(String qry ){
             model = (DefaultTableModel) product_table.getModel();
             TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model);
             product_table.setRowSorter(trs);
       
             if(qry =="ALL"){
                product_table.setRowSorter(trs);
             }else{
                trs.setRowFilter(RowFilter.regexFilter(qry));
             }
       
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
      
       public void updateProduct(){
    
    try{
        
        String Name = product_name.getText();
        
 
          String Brand =  product_brand.getText();
          String Price = product_price.getText();
          String Quantity = product_qty.getText();
          String Category = product_category.getSelectedItem().toString();
          
           int row = product_table.getSelectedRow();
            String sjb=product_table.getModel().getValueAt(row, 0).toString();
            
             String sql = "update company_products set product_name=?,product_brand=?,product_price=?,product_qty =?,product_category=? where ID= "+sjb+" ";
        
             
             try{
                pst=con.prepareStatement(sql);
                pst.setString(1, Name);
                pst.setString(2, Brand);
                pst.setString(3, Price);
                pst.setString(4, Quantity);
                pst.setString(5, Category);
                
                 pst.execute();
                JOptionPane.showMessageDialog(null, "Successfully Updated");
               
             }
             catch(SQLException ex){
                  JOptionPane.showMessageDialog(null, ex);
             }
             
    }catch (Exception e){
    }
}
      
       public void deleteProducts(){
     int row = product_table.getSelectedRow();
        String sjti=product_table.getModel().getValueAt(row, 0).toString();
             String sql="delete from company_products where ID = "+sjti+"";
        try {
            pst=con.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Deleted Successfully");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        };
 }
      
      public void tableClicked(){
            int index = product_table.getSelectedRow();
        TableModel model = product_table.getModel();
        ID.setText(model.getValueAt(index,0).toString());

        product_name.setText(model.getValueAt(index,1).toString());

        product_brand.setText(model.getValueAt(index,2).toString());
        product_price.setText(model.getValueAt(index,3).toString());
        product_qty.setText(model.getValueAt(index,4).toString());
        product_category.setSelectedItem(model.getValueAt(index,5).toString());
      }

      
      
      
      
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        product_table = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        ID = new javax.swing.JTextField();
        product_price = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        product_name = new javax.swing.JTextField();
        product_qty = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        product_brand = new javax.swing.JTextField();
        product_category = new javax.swing.JComboBox<>();
        back = new javax.swing.JButton();
        update = new javax.swing.JButton();
        delete = new javax.swing.JButton();
        refresh = new javax.swing.JButton();
        filter = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setLayout(null);

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

        jPanel2.add(jScrollPane1);
        jScrollPane1.setBounds(60, 30, 750, 300);

        jLabel11.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 0));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("PRODUCT ID");
        jPanel2.add(jLabel11);
        jLabel11.setBounds(60, 390, 220, 40);

        jLabel6.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("PRODUCT PRICE");
        jPanel2.add(jLabel6);
        jLabel6.setBounds(320, 390, 210, 40);

        ID.setEditable(false);
        jPanel2.add(ID);
        ID.setBounds(60, 430, 220, 40);
        jPanel2.add(product_price);
        product_price.setBounds(320, 430, 210, 40);

        jLabel4.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 0));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("PRODUCT NAME");
        jPanel2.add(jLabel4);
        jLabel4.setBounds(60, 480, 220, 40);

        jLabel7.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 0));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("AVAILABLE  QTY.");
        jPanel2.add(jLabel7);
        jLabel7.setBounds(320, 480, 210, 40);
        jPanel2.add(product_name);
        product_name.setBounds(60, 520, 220, 40);
        jPanel2.add(product_qty);
        product_qty.setBounds(320, 520, 210, 40);

        jLabel12.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 0));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("PRODUCT BRAND");
        jPanel2.add(jLabel12);
        jLabel12.setBounds(580, 390, 210, 40);

        jLabel8.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 0));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("CATEGORY");
        jPanel2.add(jLabel8);
        jLabel8.setBounds(580, 480, 210, 40);

        product_brand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                product_brandActionPerformed(evt);
            }
        });
        jPanel2.add(product_brand);
        product_brand.setBounds(580, 430, 210, 40);

        product_category.setFont(new java.awt.Font("Agency FB", 0, 18)); // NOI18N
        product_category.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select a category", "Drinks", "Gadgets", "Foods", "T-shirts", "Cosmetics", "Appliances", "Shoes", "Bicycles", " " }));
        jPanel2.add(product_category);
        product_category.setBounds(580, 520, 210, 40);

        back.setBackground(new java.awt.Color(255, 255, 51));
        back.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 18)); // NOI18N
        back.setText("BACK");
        back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backActionPerformed(evt);
            }
        });
        jPanel2.add(back);
        back.setBounds(830, 230, 110, 40);

        update.setBackground(new java.awt.Color(255, 255, 51));
        update.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 18)); // NOI18N
        update.setText("UPDATE");
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });
        jPanel2.add(update);
        update.setBounds(830, 80, 110, 40);

        delete.setBackground(new java.awt.Color(255, 255, 51));
        delete.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 18)); // NOI18N
        delete.setText("DELETE");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });
        jPanel2.add(delete);
        delete.setBounds(830, 130, 110, 40);

        refresh.setBackground(new java.awt.Color(255, 255, 51));
        refresh.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 18)); // NOI18N
        refresh.setText("REFRESH");
        refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshActionPerformed(evt);
            }
        });
        jPanel2.add(refresh);
        refresh.setBounds(830, 180, 110, 40);

        filter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Drinks", "Gadgets", "Foods", "T-shirts", "Cosmetics", "Appliances", "Shoes", "Bicycles" }));
        filter.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                filterItemStateChanged(evt);
            }
        });
        filter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                filterMouseClicked(evt);
            }
        });
        jPanel2.add(filter);
        filter.setBounds(60, 340, 190, 30);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/join-background.jpg"))); // NOI18N
        jPanel2.add(jLabel1);
        jLabel1.setBounds(-6, -6, 980, 590);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 968, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void product_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_product_tableMouseClicked
        tableClicked();
    }//GEN-LAST:event_product_tableMouseClicked

    private void product_brandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_product_brandActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_product_brandActionPerformed

    private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
        new HOME().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_backActionPerformed

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed
        updateProduct();
    }//GEN-LAST:event_updateActionPerformed

    private void refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshActionPerformed
       getListProducts("SELECT * FROM company_products");
    }//GEN-LAST:event_refreshActionPerformed

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
        deleteProducts();
    }//GEN-LAST:event_deleteActionPerformed

    private void filterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_filterMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_filterMouseClicked

    private void filterItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_filterItemStateChanged
        String qry = filter.getSelectedItem().toString();
        filter(qry);    
    }//GEN-LAST:event_filterItemStateChanged

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
            java.util.logging.Logger.getLogger(modifyProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(modifyProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(modifyProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(modifyProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new modifyProduct().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ID;
    private javax.swing.JButton back;
    private javax.swing.JButton delete;
    private javax.swing.JComboBox<String> filter;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField product_brand;
    private javax.swing.JComboBox<String> product_category;
    private javax.swing.JTextField product_name;
    private javax.swing.JTextField product_price;
    private javax.swing.JTextField product_qty;
    private javax.swing.JTable product_table;
    private javax.swing.JButton refresh;
    private javax.swing.JButton update;
    // End of variables declaration//GEN-END:variables
}
