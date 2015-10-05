package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import model.PortaSerial;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lubuntu
 */
public class SupervisorioSSHouse extends javax.swing.JFrame implements Runnable {
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaAlarmes = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);

        tabelaAlarmes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tabelaAlarmes);

        jButton1.setText("jButton1");

        jButton2.setText("jButton2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private final PortaSerial portaSerial;
    private String mensagem;   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabelaAlarmes;
    // End of variables declaration//GEN-END:variables

    
    /**
     * Creates new form SupervisorioSSHouse
     */
    public SupervisorioSSHouse(PortaSerial portaSerial) {
        this.mensagem = null;
        this.portaSerial = portaSerial;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        float maxX = screenSize.width;
        float maxY = screenSize.height;
        initComponents();
    }
    
     private void mostrarAlarmes(/*List resultList*/){
        DefaultTableModel table = (DefaultTableModel)tabelaAlarmes.getModel();
        table.setNumRows(0);
        //Cria o vetor de Strings que sera passado para o Jtable
        Vector<String> tableHeaders = new Vector<>();
        Vector tableData = new Vector();        
        /*Manutencao manutencao = null;*/
        
        tableHeaders.add("Alarme");
        tableHeaders.add("Data");
        tableHeaders.add("Hora");
        
        //for(Object obj: resultList){
            Vector<Object> oneRow = new Vector<Object>();
            /*manutencao = (Manutencao) obj;*/
            oneRow.add("Chama sala");
             
            
            oneRow.add("10/01/15"); 
            oneRow.add("10:53");
            
            tableData.add(oneRow);
        //}
        tabelaAlarmes.setModel(new DefaultTableModel(tableData, tableHeaders));
    }
    
    @Override
    public void run() {
        mostrarAlarmes();
        while(true){
            this.mensagem = this.portaSerial.lerDados();
        }
    }
}
