/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.api.classes;

import appbuilder.api.methods.Método;
import appbuilder.api.methods.Parametro;
import appbuilder.api.packages.Pacote;
import appbuilder.api.vars.Variavel;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aluno
 */
public class Janela extends Classe {

    private Método initComponents;
    private Variavel layout;

    public Janela(String nome) {
        super(nome);
        if (!nome.endsWith("JFrame")) {
            setNome(nome + "JFrame");
        }

        try {
            addImportação(Classe.addClasse("JFrame", "swing", "javax"));

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Janela.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Não foi possível adicionar importação "
                    + "para a janela " + getNome());
        }

        setSuperClasse("JFrame");
        setConstrutorPrincipal(getConstrutores().get(0));
        //só vou usar o primeiro construtor
        for (Construtor c : getConstrutores()) {
            if (!(c == getConstrutorPrincipal())) {
                removeConstrutor(c);
            }
        }

        delegarConstrutores();

        try {
            addImportação(Classe.addClasse("GroupLayout", "swing", "javax"));
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Janela.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Problema ao adicionar importações para Janela " + getNome());
        }

        this.initComponents = new Método("public", "void", "initComponents");
        this.layout = new Variavel("javax.swing.GroupLayout", "layout");

        this.layout.setClasse(this);
        addMétodo(this.initComponents);

        this.initComponents.addCorpo(getMétodo("setDefaultCloseOperation").getChamada("javax.swing.WindowConstants.EXIT_ON_CLOSE"));
        this.initComponents.addCorpo(layout.getDeclaração(layout.instancia(getMétodo("getContentPane").getChamada()).getInstancia()));
        this.initComponents.addCorpo(""
                + "        getContentPane().setLayout(layout);\n"
                + "        layout.setHorizontalGroup(\n"
                + "            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)\n"
                + "            .addGap(0, 608, Short.MAX_VALUE)\n"
                + "        );\n"
                + "        layout.setVerticalGroup(\n"
                + "            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)\n"
                + "            .addGap(0, 472, Short.MAX_VALUE)\n"
                + "        );\n"
                + "\n"
                + "        pack();");

    }

    public Janela(String nome, String pacote, String caminho) {
        this(nome);
        this.pacote = new Pacote(pacote, caminho);
    }

    public Janela(String nome, String pacote) {
        this(nome);
        this.pacote = new Pacote(pacote);
    }

    public void delegarConstrutores() {
        for (Construtor construtor : super.getConstrutores()) {
            String codigo = "";
            // indica a posição do parâmetro
            int contador = 1;

            for (Parametro param : construtor.getParametros()) {
                // toda vez que chegar no próximo parâmetro, colocar uma vírgula
                if (contador % 2 == 0) {
                    codigo += ",";
                    contador = 1;
                }
                // coloca o parâmetro
                codigo += param.getReferencia();
                contador++;
            }

            construtor.addCorpo("super(" + codigo + ")");

        }
    }

}
