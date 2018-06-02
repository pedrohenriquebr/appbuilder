/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.menus;

import appbuilder.api.classes.Classe;
import appbuilder.api.classes.Modelo;
import appbuilder.api.methods.Método;
import appbuilder.api.packages.Pacote;
import appbuilder.api.projects.Manifesto;
import appbuilder.api.projects.Projeto;
import appbuilder.api.vars.Atributo;
import appbuilder.api.vars.Variavel;
import appbuilder.util.ClassBuilder;
import java.awt.Component;
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.ListModel;

/**
 *
 * @author psilva
 */
public class BuildingMenu extends javax.swing.JFrame {

    private Projeto proj;
    private List<Atributo> atributos = new ArrayList<>();
    private Map<Atributo, Boolean> mapa = new HashMap<Atributo, Boolean>();
    private DefaultListModel<String> lista = new DefaultListModel<>();

    private ClassBuilder builder;

    private boolean selecionouAtributo = false;
    private int ultimoIndiceSelecionado = -1;
    private File executavel;

    public BuildingMenu(Projeto proj) {
        initComponents();
        this.proj = proj;
        builder = new ClassBuilder(proj.getCaminho());

        this.listAtributos.setModel(lista);

        //deixar escondido
        this.btnAtualizar.setVisible(false);
        this.btnExcluir.setVisible(false);

        lista.addListDataListener(comboTipos);

    }

    /**
     * Adiciona no mapa de atributos
     *
     * @param atributo
     * @param filtrador
     */
    public void adicionarAtributo(Atributo atributo, boolean filtrador) {

        System.out.println("adicionando atributo : " + atributo.getDeclaração());
        atributos.add(atributo);
        mapa.put(atributo, filtrador);

    }

    public boolean carregarAtributo(int indice) {

        Atributo atributo = atributos.get(indice);
        if (atributo == null) {
            return false;
        }

        this.txtNomeAtributo.setText(atributo.getNome());

        int i = 0;
        if (atributo.getTipo().equals("String")) {
            i = 0;
        } else if (atributo.getTipo().equals("int")) {
            i = 1;
        } else {
            i = 2;
        }

        this.comboTipos.setSelectedIndex(i);
        this.checkFiltrador.setSelected(mapa.get(atributo));

        System.out.println("Atributo carregado : " + atributo.getDeclaração());
        return true;
    }

    public void limparPainel() {
        listAtributos.clearSelection();;
        this.btnAdicionar.setVisible(true);
        this.btnAtualizar.setVisible(false);
        this.btnExcluir.setVisible(false);
        this.txtNomeAtributo.setText("");
        this.comboTipos.setSelectedIndex(0);
        this.checkFiltrador.setSelected(false);
    }

    public boolean painelPreenchido(JPanel painel) {

        for (Component componente : painel.getComponents()) {

            if (componente instanceof JTextField) {
                JTextField campo = (JTextField) componente;
                if (campo.getText().trim().isEmpty()) {

                    return false;
                }
            } else if (componente instanceof JPasswordField) {
                JPasswordField campo = (JPasswordField) componente;

                if (!(campo.getPassword().length > 0)) {

                    return false;
                }
            }
        }

        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelModelo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNomeModelo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtNomeAtributo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        comboTipos = new javax.swing.JComboBox<>();
        checkFiltrador = new javax.swing.JCheckBox();
        btnAdicionar = new javax.swing.JButton();
        panelAtributos = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listAtributos = new javax.swing.JList<>();
        btnAtualizar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnConstruir = new javax.swing.JButton();
        btnExecutar = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuProjeto = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        menuEditar = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Construção do Projeto");

        panelModelo.setBorder(javax.swing.BorderFactory.createTitledBorder("Modelo"));

        jLabel1.setText("Nome:");

        jLabel2.setText("Atributo:");

        jLabel3.setText("Tipo: ");

        comboTipos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"Texto","Inteiro","Real"}));

        checkFiltrador.setText("Filtrador");

        btnAdicionar.setText("adicionar");
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        panelAtributos.setBorder(javax.swing.BorderFactory.createTitledBorder("Atributos"));

        listAtributos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listAtributos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listAtributosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(listAtributos);

        javax.swing.GroupLayout panelAtributosLayout = new javax.swing.GroupLayout(panelAtributos);
        panelAtributos.setLayout(panelAtributosLayout);
        panelAtributosLayout.setHorizontalGroup(
            panelAtributosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAtributosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelAtributosLayout.setVerticalGroup(
            panelAtributosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAtributosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        btnAtualizar.setText("atualizar");
        btnAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarActionPerformed(evt);
            }
        });

        btnExcluir.setText("excluir");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelModeloLayout = new javax.swing.GroupLayout(panelModelo);
        panelModelo.setLayout(panelModeloLayout);
        panelModeloLayout.setHorizontalGroup(
            panelModeloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelModeloLayout.createSequentialGroup()
                .addGroup(panelModeloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelModeloLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(panelModeloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelModeloLayout.createSequentialGroup()
                                .addComponent(btnAdicionar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnAtualizar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnExcluir))
                            .addGroup(panelModeloLayout.createSequentialGroup()
                                .addGroup(panelModeloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1)
                                    .addComponent(txtNomeModelo)
                                    .addComponent(txtNomeAtributo, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE))
                                .addGap(41, 41, 41)
                                .addGroup(panelModeloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addGroup(panelModeloLayout.createSequentialGroup()
                                        .addComponent(comboTipos, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(checkFiltrador))))))
                    .addGroup(panelModeloLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panelAtributos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        panelModeloLayout.setVerticalGroup(
            panelModeloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelModeloLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNomeModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelModeloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelModeloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNomeAtributo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboTipos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkFiltrador))
                .addGap(18, 18, 18)
                .addGroup(panelModeloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdicionar)
                    .addComponent(btnAtualizar)
                    .addComponent(btnExcluir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelAtributos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        btnConstruir.setText("Construir");
        btnConstruir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConstruirActionPerformed(evt);
            }
        });

        btnExecutar.setText("Executar");
        btnExecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExecutarActionPerformed(evt);
            }
        });

        menuProjeto.setText("Projeto");

        jMenuItem1.setText("Configurações");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        menuProjeto.add(jMenuItem1);

        jMenuBar1.add(menuProjeto);

        menuEditar.setText("Editar");
        jMenuBar1.add(menuEditar);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnConstruir)
                        .addGap(30, 30, 30)
                        .addComponent(btnExecutar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConstruir)
                    .addComponent(btnExecutar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed

        new Thread(new Runnable() {
            public void run() {
                new ProjectSettings(proj).setVisible(true);
            }
        }).start();


    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        // TODO add your handling code here:

        if (!painelPreenchido(panelModelo)) {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos !");
            return;
        }

        String nomeAtributo = txtNomeAtributo.getText();
        String tipo = (String) comboTipos.getSelectedItem();
        boolean filtrador = checkFiltrador.isSelected();
        lista.addElement(nomeAtributo + "   :   "
                + tipo + "  :   "
                + (filtrador == true ? "Filtrador" : "Comum"));

        Atributo atributo;
        String tipoAtributo = "";

        if (tipo.equals("Texto")) {
            tipoAtributo = "String";
        } else if (tipo.equals("Inteiro")) {
            tipoAtributo = "int";
        } else {
            tipoAtributo = "double";
        }

        atributo = new Atributo(tipoAtributo, nomeAtributo);

        adicionarAtributo(atributo, filtrador);
        limparPainel();

    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void listAtributosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listAtributosMouseClicked
        // TODO add your handling code here:
        if (lista.size() == 0) {
            return;
        }

        int indice = listAtributos.getSelectedIndex();
        if (selecionouAtributo && indice == ultimoIndiceSelecionado) {
            //clicou pela segunda vez
            //limpa as caixas de texto
            limparPainel();
            this.btnAdicionar.setVisible(true);
            selecionouAtributo = !selecionouAtributo;
            ultimoIndiceSelecionado = -1;
            return;
        }

        this.btnAdicionar.setVisible(false);

        if (selecionouAtributo && indice != ultimoIndiceSelecionado) {

            ultimoIndiceSelecionado = indice;
            selecionouAtributo = true;
        } else {
            selecionouAtributo = !selecionouAtributo;
        }

        ultimoIndiceSelecionado = indice;
        if (indice < 0) {
            return;
        }

        boolean sucesso = carregarAtributo(indice);

        if (sucesso) {
            this.btnAtualizar.setVisible(true);
            this.btnExcluir.setVisible(true);
        }

        System.out.println("Clicou na lista de atributos ! indice: " + indice);
    }//GEN-LAST:event_listAtributosMouseClicked

    private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarActionPerformed
        // TODO add your handling code here:
        if (!painelPreenchido(panelModelo)) {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos !");
            return;
        }

        int indice = listAtributos.getSelectedIndex();
        Atributo atributo = atributos.get(indice);

        String nome = txtNomeAtributo.getText().trim();
        String tipo = ((String) comboTipos.getSelectedItem()).trim();
        boolean filtrador = checkFiltrador.isSelected();

        String tipoAtributo = "";
        if (tipo.equals("Texto")) {
            tipoAtributo = "String";
        } else if (tipo.equals("Inteiro")) {
            tipoAtributo = "int";
        } else {
            tipoAtributo = "double";
        }

        atributo.setNome(nome);
        atributo.setTipo(tipoAtributo);
        Boolean b = mapa.get(atributo);
        b = filtrador;

        lista.setElementAt(nome + "   :   "
                + tipo + "  :   "
                + (filtrador == true ? "Filtrador" : "Comum"), indice);

        System.out.println("atualizando atributo: " + atributo.getDeclaração() + ", filtrador: " + b.booleanValue());
    }//GEN-LAST:event_btnAtualizarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        // TODO add your handling code here:

        int indice = listAtributos.getSelectedIndex();
        Atributo atr = atributos.get(indice);
        lista.remove(indice);
        mapa.remove(atr);
        atributos.remove(indice);

        limparPainel();
        System.out.println("Excluído: " + atr.getDeclaração());
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnConstruirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConstruirActionPerformed
        // TODO add your handling code here:
        if (txtNomeModelo.getText().isEmpty() || lista.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Preencha o campo do nome do modelo e/ou adicione atributos !");
            return;
        }

        //pacote do modelo terá o seguinte formato : br.com.<nome_do_projeto>.models
        Pacote pacotePrincipal = proj.getPacotePrincipal();
        Pacote pacoteDeModelos = new Pacote("models", pacotePrincipal.getCaminho());
        Pacote pacoteMain = new Pacote("main", pacotePrincipal.getCaminho());
        Modelo modelo = new Modelo(txtNomeModelo.getText(), pacoteDeModelos.getNome(), pacotePrincipal.getCaminho());

        boolean retorno = true;
        for (Atributo atributo : atributos) {

            retorno = modelo.addAtributo(atributo.getTipo(), atributo.getNome());
            System.out.println("encontrado atributo :" + atributo.getDeclaração() + ": " + (retorno == true ? "sucesso" : "falha"));
        }

        Classe.addClasse(modelo);
        Classe principal = new Classe("Principal", pacoteMain.getNome(), proj.getPacotePrincipal().getCaminho());
        principal.addImportação(
                Classe.getClasseEstática(modelo.getNomeCompleto()));
        try {
            principal.addImportação(Classe.addClasse("Scanner","util","java"));
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BuildingMenu.class.getName()).log(Level.SEVERE, null, ex);
        }

        principal.setPrincipal(true);

        Método main = principal.getMain();
        Variavel var = new Variavel(modelo.getNome(), "modelo");
        
        var.setClasse(principal);
        main.addCorpo(var.getDeclaração(var.instancia().getInstancia()));
        main.addCorpo("Scanner scan = new Scanner(System.in)");
        main.addCorpo("System.out.println(\"Olá Mundo!\")");
        

        List<Classe> classes = new ArrayList<>();
        classes.add(principal);
        classes.add(modelo);

        Manifesto manifesto = new Manifesto();
        manifesto.setClassePrincipal(principal.getNomeCompleto());
        List<File> codigoFonte = null;
        List<File> compilados = null;

        try {
            manifesto.write(proj.getCaminho());
        } catch (IOException ex) {
            Logger.getLogger(BuildingMenu.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Erro ao escrever manifesto do projeto !");
        }

        builder = new ClassBuilder(proj.getCaminho());

        try {
            codigoFonte = builder.build(classes);
        } catch (IOException ex) {
            Logger.getLogger(BuildingMenu.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Erro ao construir projeto !");
        }

        try {
            compilados = builder.compile(codigoFonte);
        } catch (IOException ex) {
            Logger.getLogger(BuildingMenu.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Erro ao compilar os códigos fonte !");
        }

        try {
            this.executavel = builder.packJar(proj.getNome(), manifesto);
        } catch (IOException ex) {
            Logger.getLogger(BuildingMenu.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Erro ao empacotar o projeto !");
        }

        System.out.println("Tudo OK!");

    }//GEN-LAST:event_btnConstruirActionPerformed

    private void btnExecutarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExecutarActionPerformed
        try {
            // TODO add your handling code here:
            builder.executeJar(executavel);
        } catch (IOException ex) {
            Logger.getLogger(BuildingMenu.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Erro ao executar projeto !");
        }
    }//GEN-LAST:event_btnExecutarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        EventQueue.invokeLater(
                new Runnable() {
            @Override
            public void run() {
                Projeto proj = new Projeto(System.getProperty("user.home") + "/Documentos/Testes", "MeuApp");
                proj.setPacotePrincipal(new Pacote("br.com." + proj.getNome()));
                new BuildingMenu(proj).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnConstruir;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnExecutar;
    private javax.swing.JCheckBox checkFiltrador;
    private javax.swing.JComboBox<String> comboTipos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> listAtributos;
    private javax.swing.JMenu menuEditar;
    private javax.swing.JMenu menuProjeto;
    private javax.swing.JPanel panelAtributos;
    private javax.swing.JPanel panelModelo;
    private javax.swing.JTextField txtNomeAtributo;
    private javax.swing.JTextField txtNomeModelo;
    // End of variables declaration//GEN-END:variables
}
