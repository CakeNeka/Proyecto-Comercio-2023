package org.cakeneka.windows;

import org.cakeneka.utilities.DuaPersistenceManager;
import org.cakeneka.utilities.DuaGenerator;
import org.cakeneka.components.DuaInputField;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.Desktop;
import java.awt.FontMetrics;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class MainWindow extends javax.swing.JFrame {
    
    private static final String DOCUMENTS_OUTPUT_PATH = "documents.html";
    
    private DuaPersistenceManager database;
    private List<DuaInputField> inputFields;
    private DuaGenerator duaGenerator;
    private String userName;
    /**
     * Creates new form MainWindow
     * @param userName Este nombre lo envía la ventana de Login
     */
    public MainWindow(String userName) {
        // Inicializar los atributos de la clase
        inputFields = new ArrayList<>();
        duaGenerator = new DuaGenerator();
        database = new DuaPersistenceManager();
        this.userName = userName;
    
        // Llamada a los métodos de inicialización
        initComponents();
        addInputFields();    
        setRequiredInputFields(); // Añade todos los campos de entrada de datos a la lista inputFields
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(16);  // Aumenta velocidad vertical de la barra de desplazamiento
        setUserNameLabel(userName);
        setLocationRelativeTo(null); // Para que la ventana aparezca centrada
    }
     
    /**
     * Establece el nombre que se mostrará bajo el texto 'Conectado como:'
     * Añade ... si el nombre es demasiado largo
     * @param name nombre del usuario
     */
    private void setUserNameLabel(String name) {
        FontMetrics fontMetrics = userNameLabel.getFontMetrics(userNameLabel.getFont());
        int maxWidth = userNameLabel.getWidth();
        userNameLabel.setText(name);
        
        while (fontMetrics.stringWidth(name + "...") > maxWidth) {
            name = name.substring(0, name.length() -1);
            userNameLabel.setText(name + "...");
        }
    }
    
    /**
     * Añade todos los JTextField, jSpinner y jComboBox a la lista 'inputFields'
     */
    private void addInputFields() {
        inputFields.add(aduanaTf);
        inputFields.add(declaracionTf);
        inputFields.add(exportadorNombreTf);
        inputFields.add(exportadorDireccionTf);
        inputFields.add(exportadorCifTf);
        inputFields.add(formulariosTf);
        inputFields.add(listaDeCargaTf);
        inputFields.add(partidasSpn);
        inputFields.add(totalBultosSpn);
        inputFields.add(numReferenciaTf);
        inputFields.add(destinatarioTf);
        inputFields.add(responsableFinancieroTf);
        inputFields.add(paisPrimerDestinoTf);
        inputFields.add(paisTransaccionTf);
        inputFields.add(pacTf);
        inputFields.add(declaranteRepresentanteTf);
        inputFields.add(paisExpedicionTf);
        inputFields.add(codPExpedExportTf);
        inputFields.add(paisOrigenTf);
        inputFields.add(codPaisDestinoSpn);
        inputFields.add(paisDestinoTf);
        inputFields.add(idNacMedTransPartidaTf);
        inputFields.add(ctrSpn);
        inputFields.add(condicionesEntregaTf);
        inputFields.add(idNacMedTransActFronteraTf);
        inputFields.add(divisaImporteTotalTf);
        inputFields.add(tipoCambioTf);
        inputFields.add(naturalezaTransacc1Spn);
        inputFields.add(naturalezaTransacc2Spn);
        inputFields.add(modoTransporteSpn);
        inputFields.add(modoTransporte2Spn);
        inputFields.add(lugarCargaTf);
        inputFields.add(datosFinancierosBancariosTf);
        inputFields.add(aduanaSalidaSpn);
        inputFields.add(locMercanciasSpn);
        inputFields.add(bultosDescMercanciasSpn);
        inputFields.add(partidaSpn);
        inputFields.add(codMercanciasSpn);
        inputFields.add(codPaisOrigenTf);
        inputFields.add(masaBrutaSpn);
        inputFields.add(regimenCb);
        inputFields.add(masaNetaSpn);
        inputFields.add(contingenteTf);
        inputFields.add(docCargoPrecedenteTf);
        inputFields.add(udsSuplementariasTf);
        inputFields.add(precioFacturadoSpn);
        inputFields.add(certificadosAutorizacionesTf);
        inputFields.add(valorEstadisticoTf);
        inputFields.add(calculoTributosSpn);
        inputFields.add(aplazamientoPagoTf);
        inputFields.add(idDepositoTf);
        inputFields.add(obligadoPrincipalTf);
        inputFields.add(aduanasPasoPrevistasTf);
        inputFields.add(garantiaTf);
        inputFields.add(aduanasDestinoPaisTf);
        inputFields.add(lugarFechaTf);
    }
    
    /**
     * Define la obligatoriedad de los campos
     */
    private void setRequiredInputFields(){
        boolean[] requiredFields = {
            true,true,true,true,true,false,false,true,true,false,true,false,false,false, //15 (ultimo > pais transac)
            false,true,true,true,true,false,true,false,true,true,false,true,true,        //14 (ultimo > tipo cambio)
            true,true,true,true,false,false,true,true,true,false,true,true,true,true,    //14
            true,false,false,false,true,true,true,true,false,false,false,false,          //12
            false,false,true                                                             //3
        };
        
        for (int i = 0; i < requiredFields.length; i++) {
            inputFields.get(i).setRequired(requiredFields[i]);
        }
    }
        
    /**
     * Cambia la foto de perfil de la pantalla principal
     * @param i índice de la foto de perfil
     */
    private void setPfp(int i) {
        String pfpPath = "src/org/cakeneka/resources/pfp_" + i + ".png";
        ImageIcon newIcon = new ImageIcon(pfpPath);
        pfpLabel.setIcon(newIcon);
    }
    
    /**
     * Devuelve una lista con todos los campos del dua en forma de String.
     * @return 
     */
    private List<String> getStringFields() {
        List<String> values = new ArrayList<>();
        inputFields.stream().forEach(i -> values.add(i.getField()));
        return values;
    }
    
    /**
     * Comprueba si todos los campos obligatorios están rellenos
     * @return true si todos los campos obligatorios están rellenos, false si no
     */
    private boolean validateFields() {        
        return inputFields.stream().noneMatch(i -> i.isRequired() && (i.getField() == null || i.getField().isEmpty()));
        
        /* 
        CÓDIGO EQUIVALENTE
        for (DuaInputField inputField : inputFields) {
            if (inputField.isRequired() && (inputField.getField().isEmpty() || inputField.getField() == null)){
                return false;
            }
        }
        return true;
        */
    }
    
    /**
     * Cambia el contenido de los JTextField, JSpinner y JComboBox por 
     * los contenidos de la lista.
     * Este método se utiliza desde LoadDataWindow cuando se selecciona la
     * opción de 'Cargar'.
     * @param ls Lista con la que se actualizan los campos.
     */
    public void updateFields(List<String> ls){
        if (ls.size() == inputFields.size()) {
            for (int i = 0; i < inputFields.size(); i++) {
                System.out.println(ls.get(i));
                inputFields.get(i).setField(ls.get(i));
            }
        }
    }
    
    /**
     * Guarda los datos de los JTextField, JSpinner y JComboBox en la base de datos
     */
    private void saveCurrentData(){
        try {
            database.addSaveState(getStringFields());
            JOptionPane.showMessageDialog(this, "Datos guardados correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "No ha sido posible guardar los datos actuales", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Código generado
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        duaImportacionPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        aduanaTf = new org.cakeneka.components.DuaTextField();
        declaracionTf = new org.cakeneka.components.DuaTextField();
        exportadorNombreTf = new org.cakeneka.components.DuaTextField();
        exportadorDireccionTf = new org.cakeneka.components.DuaTextField();
        exportadorCifTf = new org.cakeneka.components.DuaTextField();
        listaDeCargaTf = new org.cakeneka.components.DuaTextField();
        numReferenciaTf = new org.cakeneka.components.DuaTextField();
        destinatarioTf = new org.cakeneka.components.DuaTextField();
        responsableFinancieroTf = new org.cakeneka.components.DuaTextField();
        paisPrimerDestinoTf = new org.cakeneka.components.DuaTextField();
        paisTransaccionTf = new org.cakeneka.components.DuaTextField();
        pacTf = new org.cakeneka.components.DuaTextField();
        declaranteRepresentanteTf = new org.cakeneka.components.DuaTextField();
        paisExpedicionTf = new org.cakeneka.components.DuaTextField();
        codPExpedExportTf = new org.cakeneka.components.DuaTextField();
        paisOrigenTf = new org.cakeneka.components.DuaTextField();
        paisDestinoTf = new org.cakeneka.components.DuaTextField();
        idNacMedTransPartidaTf = new org.cakeneka.components.DuaTextField();
        condicionesEntregaTf = new org.cakeneka.components.DuaTextField();
        idNacMedTransActFronteraTf = new org.cakeneka.components.DuaTextField();
        divisaImporteTotalTf = new org.cakeneka.components.DuaTextField();
        tipoCambioTf = new org.cakeneka.components.DuaTextField();
        lugarCargaTf = new org.cakeneka.components.DuaTextField();
        datosFinancierosBancariosTf = new org.cakeneka.components.DuaTextField();
        codPaisOrigenTf = new org.cakeneka.components.DuaTextField();
        contingenteTf = new org.cakeneka.components.DuaTextField();
        docCargoPrecedenteTf = new org.cakeneka.components.DuaTextField();
        udsSuplementariasTf = new org.cakeneka.components.DuaTextField();
        certificadosAutorizacionesTf = new org.cakeneka.components.DuaTextField();
        valorEstadisticoTf = new org.cakeneka.components.DuaTextField();
        aplazamientoPagoTf = new org.cakeneka.components.DuaTextField();
        idDepositoTf = new org.cakeneka.components.DuaTextField();
        obligadoPrincipalTf = new org.cakeneka.components.DuaTextField();
        aduanasPasoPrevistasTf = new org.cakeneka.components.DuaTextField();
        garantiaTf = new org.cakeneka.components.DuaTextField();
        aduanasDestinoPaisTf = new org.cakeneka.components.DuaTextField();
        lugarFechaTf = new org.cakeneka.components.DuaTextField();
        partidasSpn = new org.cakeneka.components.DuaSpinner();
        totalBultosSpn = new org.cakeneka.components.DuaSpinner();
        ctrSpn = new org.cakeneka.components.DuaSpinner();
        codPaisDestinoSpn = new org.cakeneka.components.DuaSpinner();
        naturalezaTransacc1Spn = new org.cakeneka.components.DuaSpinner();
        naturalezaTransacc2Spn = new org.cakeneka.components.DuaSpinner();
        modoTransporteSpn = new org.cakeneka.components.DuaSpinner();
        modoTransporte2Spn = new org.cakeneka.components.DuaSpinner();
        aduanaSalidaSpn = new org.cakeneka.components.DuaSpinner();
        locMercanciasSpn = new org.cakeneka.components.DuaSpinner();
        bultosDescMercanciasSpn = new org.cakeneka.components.DuaSpinner();
        partidaSpn = new org.cakeneka.components.DuaSpinner();
        codMercanciasSpn = new org.cakeneka.components.DuaSpinner();
        masaBrutaSpn = new org.cakeneka.components.DuaSpinner();
        masaNetaSpn = new org.cakeneka.components.DuaSpinner();
        precioFacturadoSpn = new org.cakeneka.components.DuaSpinner();
        calculoTributosSpn = new org.cakeneka.components.DuaSpinner();
        formulariosTf = new org.cakeneka.components.DuaTextField();
        jLabel66 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        regimenCb = new org.cakeneka.components.DuaComboBox();
        jPanel1 = new javax.swing.JPanel();
        jLabel69 = new javax.swing.JLabel();
        pfp1Btn = new javax.swing.JButton();
        pfp3Btn = new javax.swing.JButton();
        pfp2Btn = new javax.swing.JButton();
        pfp4Btn = new javax.swing.JButton();
        backgroundPanel1 = new org.cakeneka.components.BackgroundPanel();
        pfpLabel = new javax.swing.JLabel();
        generateDocumentationBtn = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        userNameLabel = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        saveMenuItem = new javax.swing.JMenuItem();
        loadMenuItem = new javax.swing.JMenuItem();
        appMenu = new javax.swing.JMenu();
        infoMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Project D.U.A.");
        setAutoRequestFocus(false);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);

        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jLabel1.setText("Aduana*");

        jLabel2.setText("Declaración*");

        jLabel3.setText("Lista de carga");

        jLabel4.setText("Exportador");

        jLabel5.setText("Total bultos*");

        jLabel6.setText("Partidas*");

        jLabel7.setText("Destinatario*");

        jLabel8.setText("Nº Referencia");

        jLabel9.setText("Pais primer destino");

        jLabel10.setText("Responsable financiero");

        jLabel11.setText("Pais transacción");

        jLabel12.setText("P.A.C.");

        jLabel13.setText("Pais de expedición/exportación*");

        jLabel14.setText("Declarante/Representante*");

        jLabel15.setText("País de origen*");

        jLabel16.setText("Cód  P. exped/export*");

        jLabel17.setText("País de destino*");

        jLabel18.setText("Cód País de destino");

        jLabel19.setText("medio transporte a la partida");

        jLabel20.setText("Identidad y nacionalidad");

        jLabel21.setText("Ctr.");

        jLabel22.setText("Condiciones de entrega*");

        jLabel23.setText("Divisa e importe total factura*");

        jLabel24.setText("Tipo cambio*");

        jLabel25.setText("Identidad y nacionalidad medio");

        jLabel26.setText("transporte activo en la frontera");

        jLabel27.setText("Naturaleza transacc");

        jLabel28.setText("Modo transporte");

        jLabel29.setText("Modo transporte");

        jLabel34.setText("Lugar Carga");

        jLabel35.setText("Datos financieros y bancarios");

        jLabel36.setText("Aduana de salida");

        jLabel37.setText("Localización de las mercancias");

        jLabel38.setText("Bultos y descripción de");

        jLabel39.setText("las mercancías");

        jLabel40.setText("Partida");

        jLabel41.setText("Código de las mercancías");

        jLabel42.setText("Cód país de origen*");

        jLabel43.setText("Masa Bruta (kg)");

        jLabel44.setText("Régimen");

        jLabel45.setText("Masa neta (kg)");

        jLabel46.setText("Contingente");

        jLabel48.setText("Unidades suplementarias");

        jLabel49.setText("Precio facturado");

        jLabel47.setText("documento precedente");

        jLabel51.setText("Documento de cargo/");

        jLabel50.setText("Certificados y autorizaciones*");

        jLabel52.setText("Indicaciones especiales/");

        jLabel53.setText("Documentos presentados/");

        jLabel54.setText("Valor estadístico*");

        jLabel55.setText("Calculo de los tributos");

        jLabel56.setText("Aplazamiento de pago");

        jLabel57.setText("Identificación depósito");

        jLabel58.setText("Obligado principal");

        jLabel59.setText("Garantía");

        jLabel60.setText("Aduana de destino (y país)");

        jLabel61.setText("Aduanas de paso previstas");

        jLabel62.setText("Lugar y fecha*");

        jLabel63.setText("Nombre*");

        jLabel64.setText("Dirección*");

        jLabel65.setText("CIF/EORI*");

        totalBultosSpn.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        ctrSpn.setModel(new javax.swing.SpinnerNumberModel(0, 0, 1, 1));

        modoTransporteSpn.setModel(new javax.swing.SpinnerNumberModel(1, 1, 4, 1));

        modoTransporte2Spn.setModel(new javax.swing.SpinnerNumberModel(1, 1, 4, 1));

        bultosDescMercanciasSpn.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        partidaSpn.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        masaBrutaSpn.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        masaNetaSpn.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        jLabel66.setText("Formularios");

        jLabel32.setText("Los campos marcados con * son obligatorios");

        regimenCb.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10", "24", "31" }));

        javax.swing.GroupLayout duaImportacionPanelLayout = new javax.swing.GroupLayout(duaImportacionPanel);
        duaImportacionPanel.setLayout(duaImportacionPanelLayout);
        duaImportacionPanelLayout.setHorizontalGroup(
            duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                            .addGap(216, 216, 216)
                            .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel65)
                                .addComponent(jLabel64))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(exportadorDireccionTf, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                                .addComponent(exportadorCifTf, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                                    .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel54, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel55))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(valorEstadisticoTf, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(calculoTributosSpn, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel62)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(lugarFechaTf, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel60)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(aduanasDestinoPaisTf, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel59)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(garantiaTf, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel57)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(idDepositoTf, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel56)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(aplazamientoPagoTf, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                                    .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel52)
                                        .addComponent(jLabel50)
                                        .addComponent(jLabel53))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(certificadosAutorizacionesTf, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                                    .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel51)
                                        .addComponent(jLabel47))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(docCargoPrecedenteTf, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel43)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(masaBrutaSpn, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel42)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(codPaisOrigenTf, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(listaDeCargaTf, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(partidasSpn, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel7)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(destinatarioTf, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel8)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(numReferenciaTf, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel9)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(paisPrimerDestinoTf, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel10)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(responsableFinancieroTf, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel12)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(pacTf, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel11)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(paisTransaccionTf, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel13)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(paisExpedicionTf, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel14)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(declaranteRepresentanteTf, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel15)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(paisOrigenTf, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel16)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(codPExpedExportTf, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel17)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(paisDestinoTf, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel18)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(codPaisDestinoSpn, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel20)
                                    .addGap(288, 288, 288))
                                .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel22)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(condicionesEntregaTf, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel24)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(tipoCambioTf, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel29)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(modoTransporte2Spn, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, duaImportacionPanelLayout.createSequentialGroup()
                                        .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel37)
                                            .addComponent(jLabel36))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(aduanaSalidaSpn, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                                            .addComponent(locMercanciasSpn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                                                .addComponent(jLabel34)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lugarCargaTf, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                                                .addComponent(jLabel35)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(datosFinancierosBancariosTf, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                                    .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel38)
                                        .addComponent(jLabel39))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(bultosDescMercanciasSpn, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel44)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(regimenCb, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel46)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(contingenteTf, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel48)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(udsSuplementariasTf, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel49)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(precioFacturadoSpn, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                                    .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel58, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel61))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(aduanasPasoPrevistasTf, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                                        .addComponent(obligadoPrincipalTf, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(totalBultosSpn, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                                    .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel21)
                                        .addComponent(jLabel19))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(idNacMedTransPartidaTf, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                                        .addComponent(ctrSpn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                                    .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel40)
                                        .addComponent(jLabel41))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(partidaSpn, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                                        .addComponent(codMercanciasSpn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                                    .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel26)
                                        .addComponent(jLabel23)
                                        .addComponent(jLabel25))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(idNacMedTransActFronteraTf, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(divisaImporteTotalTf, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)))
                                .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                                    .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel2))
                                    .addGap(6, 6, 6)
                                    .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel32)
                                        .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(aduanaTf, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, duaImportacionPanelLayout.createSequentialGroup()
                                                .addComponent(jLabel63)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(exportadorNombreTf, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(declaracionTf, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE))))))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, duaImportacionPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, duaImportacionPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel27)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(naturalezaTransacc1Spn, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(42, 42, 42)
                                    .addComponent(naturalezaTransacc2Spn, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, duaImportacionPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel45)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(masaNetaSpn, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                                .addComponent(jLabel66)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(formulariosTf, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                                .addComponent(jLabel28)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(modoTransporteSpn, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(144, Short.MAX_VALUE))
        );
        duaImportacionPanelLayout.setVerticalGroup(
            duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(duaImportacionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel32)
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(aduanaTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(declaracionTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel63)
                    .addComponent(exportadorNombreTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel64)
                    .addComponent(exportadorDireccionTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel65)
                    .addComponent(exportadorCifTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel66)
                    .addComponent(formulariosTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(listaDeCargaTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(partidasSpn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(totalBultosSpn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(numReferenciaTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(destinatarioTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(responsableFinancieroTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(paisPrimerDestinoTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(paisTransaccionTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(pacTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(declaranteRepresentanteTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(paisExpedicionTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(codPExpedExportTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(paisOrigenTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(codPaisDestinoSpn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(paisDestinoTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(idNacMedTransPartidaTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(ctrSpn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(condicionesEntregaTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(idNacMedTransActFronteraTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(divisaImporteTotalTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(tipoCambioTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(naturalezaTransacc1Spn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(naturalezaTransacc2Spn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(modoTransporteSpn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(modoTransporte2Spn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(lugarCargaTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(datosFinancierosBancariosTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(aduanaSalidaSpn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(locMercanciasSpn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(bultosDescMercanciasSpn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(partidaSpn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(codMercanciasSpn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(codPaisOrigenTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(masaBrutaSpn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(regimenCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(masaNetaSpn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(contingenteTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(docCargoPrecedenteTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(udsSuplementariasTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel49)
                    .addComponent(precioFacturadoSpn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel50)
                    .addComponent(certificadosAutorizacionesTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel54)
                    .addComponent(valorEstadisticoTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel55)
                    .addComponent(calculoTributosSpn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel56)
                    .addComponent(aplazamientoPagoTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel57)
                    .addComponent(idDepositoTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel58)
                    .addComponent(obligadoPrincipalTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel61)
                    .addComponent(aduanasPasoPrevistasTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel59)
                    .addComponent(garantiaTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel60)
                    .addComponent(aduanasDestinoPaisTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(duaImportacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel62)
                    .addComponent(lugarFechaTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(duaImportacionPanel);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 625, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("DUA Exportación", jPanel2);

        jLabel69.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel69.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel69.setText("Elige una foto de perfil");

        pfp1Btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/cakeneka/resources/pfp_1.png"))); // NOI18N
        pfp1Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pfp1BtnActionPerformed(evt);
            }
        });

        pfp3Btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/cakeneka/resources/pfp_3.png"))); // NOI18N
        pfp3Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pfp3BtnActionPerformed(evt);
            }
        });

        pfp2Btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/cakeneka/resources/pfp_2.png"))); // NOI18N
        pfp2Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pfp2BtnActionPerformed(evt);
            }
        });

        pfp4Btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/cakeneka/resources/pfp_4.png"))); // NOI18N
        pfp4Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pfp4BtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel69, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(pfp1Btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(pfp2Btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(pfp3Btn)
                        .addGap(18, 18, 18)
                        .addComponent(pfp4Btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(79, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pfp2Btn)
                    .addComponent(pfp1Btn)
                    .addComponent(pfp3Btn)
                    .addComponent(pfp4Btn))
                .addContainerGap(195, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Foto de perfil", jPanel1);

        pfpLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pfpLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/cakeneka/resources/pfp_0.png"))); // NOI18N

        generateDocumentationBtn.setText("<html><p style=\"text-align: center;\">Generar<br>Documentación</p></html>");
        generateDocumentationBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        generateDocumentationBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateDocumentationBtnActionPerformed(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(51, 51, 51));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("Conectado como:");

        userNameLabel.setForeground(new java.awt.Color(51, 51, 51));
        userNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        userNameLabel.setText("as");

        javax.swing.GroupLayout backgroundPanel1Layout = new javax.swing.GroupLayout(backgroundPanel1);
        backgroundPanel1.setLayout(backgroundPanel1Layout);
        backgroundPanel1Layout.setHorizontalGroup(
            backgroundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(backgroundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(generateDocumentationBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(userNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(backgroundPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(pfpLabel)
                        .addGap(0, 12, Short.MAX_VALUE)))
                .addContainerGap())
        );
        backgroundPanel1Layout.setVerticalGroup(
            backgroundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pfpLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(userNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(generateDocumentationBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        fileMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/cakeneka/resources/fileIcon.png"))); // NOI18N
        fileMenu.setText("Archivo");

        saveMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/cakeneka/resources/saveIcon.png"))); // NOI18N
        saveMenuItem.setText("Guardar");
        saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveMenuItem);

        loadMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/cakeneka/resources/loadIcon.png"))); // NOI18N
        loadMenuItem.setText("Cargar");
        loadMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(loadMenuItem);

        jMenuBar1.add(fileMenu);

        appMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/cakeneka/resources/SettingsIcon.png"))); // NOI18N
        appMenu.setText("Aplicación");

        infoMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/cakeneka/resources/infoIcon.png"))); // NOI18N
        infoMenuItem.setText("Información");
        infoMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                infoMenuItemActionPerformed(evt);
            }
        });
        appMenu.add(infoMenuItem);

        jMenuBar1.add(appMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(backgroundPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 635, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addContainerGap())
            .addComponent(backgroundPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loadMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadMenuItemActionPerformed
        LoadDataWindow slw = new LoadDataWindow(this);
        slw.setVisible(true);
    }//GEN-LAST:event_loadMenuItemActionPerformed

    private void saveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMenuItemActionPerformed
        String[] options = { "Aceptar", "Cancelar" };

        int choice = JOptionPane.showOptionDialog(this, "¿Desea guardar los datos actuales?", 
                "Guardar datos", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        
        if (choice == 0){
            saveCurrentData();
        } 
    }//GEN-LAST:event_saveMenuItemActionPerformed

    private void infoMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_infoMenuItemActionPerformed
        InfoWindow iw = new InfoWindow(this);
        iw.setVisible(true);
    }//GEN-LAST:event_infoMenuItemActionPerformed

    private void generateDocumentationBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateDocumentationBtnActionPerformed
        List<String> stringFields = getStringFields();
        if (validateFields()) {
            try {
                duaGenerator.generateDocuments(stringFields, DOCUMENTS_OUTPUT_PATH);    // Generar documento html
                File initialDirectory = new File(DOCUMENTS_OUTPUT_PATH);            
                Desktop.getDesktop().open(initialDirectory);                                     // Abrir documento html
            } catch (IOException ex) {
               JOptionPane.showMessageDialog(this, "Se ha producido un error al generar la documentación", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Tienes que rellenar todos los campos obligatorios");
        }
    }//GEN-LAST:event_generateDocumentationBtnActionPerformed

    private void pfp1BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pfp1BtnActionPerformed
        setPfp(1);
    }//GEN-LAST:event_pfp1BtnActionPerformed

    private void pfp2BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pfp2BtnActionPerformed
        setPfp(2);
    }//GEN-LAST:event_pfp2BtnActionPerformed

    private void pfp3BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pfp3BtnActionPerformed
        setPfp(3);
    }//GEN-LAST:event_pfp3BtnActionPerformed

    private void pfp4BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pfp4BtnActionPerformed
        setPfp(4);
    }//GEN-LAST:event_pfp4BtnActionPerformed

  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.cakeneka.components.DuaSpinner aduanaSalidaSpn;
    private org.cakeneka.components.DuaTextField aduanaTf;
    private org.cakeneka.components.DuaTextField aduanasDestinoPaisTf;
    private org.cakeneka.components.DuaTextField aduanasPasoPrevistasTf;
    private org.cakeneka.components.DuaTextField aplazamientoPagoTf;
    private javax.swing.JMenu appMenu;
    private org.cakeneka.components.BackgroundPanel backgroundPanel1;
    private org.cakeneka.components.DuaSpinner bultosDescMercanciasSpn;
    private org.cakeneka.components.DuaSpinner calculoTributosSpn;
    private org.cakeneka.components.DuaTextField certificadosAutorizacionesTf;
    private org.cakeneka.components.DuaSpinner codMercanciasSpn;
    private org.cakeneka.components.DuaTextField codPExpedExportTf;
    private org.cakeneka.components.DuaSpinner codPaisDestinoSpn;
    private org.cakeneka.components.DuaTextField codPaisOrigenTf;
    private org.cakeneka.components.DuaTextField condicionesEntregaTf;
    private org.cakeneka.components.DuaTextField contingenteTf;
    private org.cakeneka.components.DuaSpinner ctrSpn;
    private org.cakeneka.components.DuaTextField datosFinancierosBancariosTf;
    private org.cakeneka.components.DuaTextField declaracionTf;
    private org.cakeneka.components.DuaTextField declaranteRepresentanteTf;
    private org.cakeneka.components.DuaTextField destinatarioTf;
    private org.cakeneka.components.DuaTextField divisaImporteTotalTf;
    private org.cakeneka.components.DuaTextField docCargoPrecedenteTf;
    private javax.swing.JPanel duaImportacionPanel;
    private org.cakeneka.components.DuaTextField exportadorCifTf;
    private org.cakeneka.components.DuaTextField exportadorDireccionTf;
    private org.cakeneka.components.DuaTextField exportadorNombreTf;
    private javax.swing.JMenu fileMenu;
    private org.cakeneka.components.DuaTextField formulariosTf;
    private org.cakeneka.components.DuaTextField garantiaTf;
    private javax.swing.JButton generateDocumentationBtn;
    private org.cakeneka.components.DuaTextField idDepositoTf;
    private org.cakeneka.components.DuaTextField idNacMedTransActFronteraTf;
    private org.cakeneka.components.DuaTextField idNacMedTransPartidaTf;
    private javax.swing.JMenuItem infoMenuItem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private org.cakeneka.components.DuaTextField listaDeCargaTf;
    private javax.swing.JMenuItem loadMenuItem;
    private org.cakeneka.components.DuaSpinner locMercanciasSpn;
    private org.cakeneka.components.DuaTextField lugarCargaTf;
    private org.cakeneka.components.DuaTextField lugarFechaTf;
    private org.cakeneka.components.DuaSpinner masaBrutaSpn;
    private org.cakeneka.components.DuaSpinner masaNetaSpn;
    private org.cakeneka.components.DuaSpinner modoTransporte2Spn;
    private org.cakeneka.components.DuaSpinner modoTransporteSpn;
    private org.cakeneka.components.DuaSpinner naturalezaTransacc1Spn;
    private org.cakeneka.components.DuaSpinner naturalezaTransacc2Spn;
    private org.cakeneka.components.DuaTextField numReferenciaTf;
    private org.cakeneka.components.DuaTextField obligadoPrincipalTf;
    private org.cakeneka.components.DuaTextField pacTf;
    private org.cakeneka.components.DuaTextField paisDestinoTf;
    private org.cakeneka.components.DuaTextField paisExpedicionTf;
    private org.cakeneka.components.DuaTextField paisOrigenTf;
    private org.cakeneka.components.DuaTextField paisPrimerDestinoTf;
    private org.cakeneka.components.DuaTextField paisTransaccionTf;
    private org.cakeneka.components.DuaSpinner partidaSpn;
    private org.cakeneka.components.DuaSpinner partidasSpn;
    private javax.swing.JButton pfp1Btn;
    private javax.swing.JButton pfp2Btn;
    private javax.swing.JButton pfp3Btn;
    private javax.swing.JButton pfp4Btn;
    private javax.swing.JLabel pfpLabel;
    private org.cakeneka.components.DuaSpinner precioFacturadoSpn;
    private org.cakeneka.components.DuaComboBox regimenCb;
    private org.cakeneka.components.DuaTextField responsableFinancieroTf;
    private javax.swing.JMenuItem saveMenuItem;
    private org.cakeneka.components.DuaTextField tipoCambioTf;
    private org.cakeneka.components.DuaSpinner totalBultosSpn;
    private org.cakeneka.components.DuaTextField udsSuplementariasTf;
    private javax.swing.JLabel userNameLabel;
    private org.cakeneka.components.DuaTextField valorEstadisticoTf;
    // End of variables declaration//GEN-END:variables
}
