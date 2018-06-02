/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.menus;

import appbuilder.api.packages.Pacote;
import appbuilder.api.projects.Projeto;
import java.awt.Component;
import javax.swing.*;

/**
 *
 * @author psilva
 */
public class ProjectMenu extends javax.swing.JFrame {

    private String path = "";
    private final int altura = 270;
    private final int largura = 480;
    private final int alturaParaPainel = 445;

    private boolean painelBd = false;
    private boolean senhaVisivel = false;

    public ProjectMenu(String path) {
        initComponents();
        this.path = path;
        txtCaminho.setText(path);
        panelBancoDeDados.setVisible(false);
        this.setSize(largura, altura);
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

    public void exibirPainelBancoDeDados(boolean valor) {
        this.panelBancoDeDados.setVisible(valor);
        if (valor) {
            this.setSize(largura, alturaParaPainel);
        } else {
            this.setSize(largura, altura);
        }
    }

    public void exibirSenha(boolean valor) {
        //não sei como 
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelInformacoes = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCaminho = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtNomeProjeto = new javax.swing.JTextField();
        btnOkInfo = new javax.swing.JButton();
        checkInterfaceGráfica = new javax.swing.JCheckBox();
        checkBancoDeDados = new javax.swing.JCheckBox();
        panelBancoDeDados = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtNomeUsuario = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtSenha = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        txtBaseDeDados = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Menu do Projeto");
        setResizable(false);

        panelInformacoes.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações Básicas do projeto"));
        panelInformacoes.setToolTipText("Informções básicas do projeto");

        jLabel1.setText("Caminho: ");

        txtCaminho.setEditable(false);

        jLabel2.setText("Nome do projeto");

        btnOkInfo.setText("Ok");
        btnOkInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkInfoActionPerformed(evt);
            }
        });

        checkInterfaceGráfica.setText("Iterface Gráfica");

        checkBancoDeDados.setText("Banco de Dados");
        checkBancoDeDados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBancoDeDadosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelInformacoesLayout = new javax.swing.GroupLayout(panelInformacoes);
        panelInformacoes.setLayout(panelInformacoesLayout);
        panelInformacoesLayout.setHorizontalGroup(
            panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInformacoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addComponent(txtCaminho)
                    .addComponent(jLabel2)
                    .addComponent(txtNomeProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnOkInfo, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(checkBancoDeDados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(checkInterfaceGráfica, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(44, 44, 44))
        );
        panelInformacoesLayout.setVerticalGroup(
            panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInformacoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInformacoesLayout.createSequentialGroup()
                        .addComponent(checkInterfaceGráfica)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(checkBancoDeDados)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnOkInfo)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInformacoesLayout.createSequentialGroup()
                        .addGap(0, 13, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(6, 6, 6)
                        .addComponent(txtCaminho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNomeProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31))))
        );

        panelBancoDeDados.setBorder(javax.swing.BorderFactory.createTitledBorder("Credenciais do Banco de Dados"));

        jLabel3.setText("Usuário: ");

        jLabel4.setText("Senha:");

        jLabel5.setText("Base de Dados");

        javax.swing.GroupLayout panelBancoDeDadosLayout = new javax.swing.GroupLayout(panelBancoDeDados);
        panelBancoDeDados.setLayout(panelBancoDeDadosLayout);
        panelBancoDeDadosLayout.setHorizontalGroup(
            panelBancoDeDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBancoDeDadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBancoDeDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBancoDeDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtNomeUsuario, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                        .addComponent(txtBaseDeDados, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(panelBancoDeDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelBancoDeDadosLayout.setVerticalGroup(
            panelBancoDeDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBancoDeDadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBancoDeDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelBancoDeDadosLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(25, 25, 25))
                    .addGroup(panelBancoDeDadosLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelBancoDeDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNomeUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtBaseDeDados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelInformacoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelBancoDeDados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelInformacoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelBancoDeDados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void checkBancoDeDadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBancoDeDadosActionPerformed
        // TODO add your handling code here:
        painelBd = !painelBd;
        exibirPainelBancoDeDados(painelBd);
    }//GEN-LAST:event_checkBancoDeDadosActionPerformed

    private void btnOkInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkInfoActionPerformed
        // TODO add your handling code here:

        boolean tudoOk = true;

        boolean infoOk = painelPreenchido(panelInformacoes);
        boolean bdOk = painelPreenchido(panelBancoDeDados);

        if (checkBancoDeDados.isSelected()) {
            tudoOk = infoOk && bdOk;
        } else {
            tudoOk = infoOk;
        }

        if (!tudoOk) {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos !");
        } else {
            JOptionPane.showMessageDialog(null, "Tudo Ok!");

            Projeto proj = new Projeto(txtCaminho.getText(), txtNomeProjeto.getText());

            if (checkBancoDeDados.isSelected()) {
                proj.setBaseDeDados(txtBaseDeDados.getText());
                proj.setUsuario(txtNomeUsuario.getText());
                proj.setSenha(new String(txtSenha.getPassword()));
            }
            proj.setPacotePrincipal(new Pacote("br.com." + proj.getNome()));
            new BuildingMenu(proj).setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_btnOkInfoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOkInfo;
    private javax.swing.JCheckBox checkBancoDeDados;
    private javax.swing.JCheckBox checkInterfaceGráfica;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel panelBancoDeDados;
    private javax.swing.JPanel panelInformacoes;
    private javax.swing.JTextField txtBaseDeDados;
    private javax.swing.JTextField txtCaminho;
    private javax.swing.JTextField txtNomeProjeto;
    private javax.swing.JTextField txtNomeUsuario;
    private javax.swing.JPasswordField txtSenha;
    // End of variables declaration//GEN-END:variables
}