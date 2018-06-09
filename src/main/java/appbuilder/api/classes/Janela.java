/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.api.classes;

import appbuilder.api.methods.Método;
import appbuilder.api.methods.Parametro;
import appbuilder.api.packages.Pacote;
import appbuilder.api.vars.Atributo;
import appbuilder.api.vars.Objeto;
import appbuilder.api.vars.Variavel;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aluno
 */
public class Janela extends Classe {

    private Método initComponents;
    private Variavel layout = null;
    private Variavel container = null;
    private String titulo = "JanelaPrincipal";
    private Variavel gbc = null;
    private Variavel panel = null;
    private Variavel gbcPanel = null;

    static {
        try {
            Classe.addClasse("JFrame", "swing", "javax");
            Classe.addClasse("JButton", "swing", "javax");
            Classe.addClasse("JLabel", "swing", "javax");
            Classe.addClasse("JTextField", "swing", "javax");
            Classe.addClasse("Insets", "awt", "java");
            Classe.addClasse("JPanel", "swing", "javax");
            Classe.addClasse("GridBagLayout", "awt", "java");
            Classe.addClasse("GridBagConstraints", "awt", "java");
            Classe.addClasse("Container", "awt", "java");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();;
        }
    }

    public Janela(String nome) {
        super(nome);
        if (!nome.endsWith("JFrame")) {
            setNome(nome + "JFrame");
        }

        addImportação("javax.swing.JFrame");
        addImportação("javax.swing.JButton");
        addImportação("javax.swing.JLabel");
        addImportação("javax.swing.JTextField");
        addImportação("java.awt.Insets");
        addImportação("java.awt.GridBagLayout");
        addImportação("java.awt.GridBagConstraints");
        addImportação("java.awt.Container");
        addImportação("javax.swing.JPanel");

        setSuperClasse("JFrame");
        setConstrutorPrincipal(getConstrutores().get(0));
        //só vou usar o primeiro construtor
        for (Construtor c : getConstrutores()) {
            if (!(c == getConstrutorPrincipal())) {
                removeConstrutor(c);
            }
        }

        delegarConstrutores();

        initComponents = new Método("public", "void", "initComponents");
        addMétodo(this.initComponents);
        //configurações gerais da janela 
        initComponents.addCorpo(getMétodo("setDefaultCloseOperation")
                .getChamada("javax.swing.WindowConstants.EXIT_ON_CLOSE"));
        initComponents.addCorpo(getMétodo("setSize").getChamada("500", "400"));
        container = new Variavel("Container", "container");
        container.setClasse(this);
        layout = new Variavel("GridBagLayout", "layout");
        layout.setClasse(this);
        gbc = new Variavel("GridBagConstraints", "gbc");
        gbc.setClasse(this);
        panel = new Variavel("JPanel", "panel");
        panel.setClasse(this);
        gbcPanel = new Variavel("GridBagConstraints", "gbcPanel");
        gbcPanel.setClasse(this);

        initComponents.addCorpo(container.getDeclaração(getMétodo("getContentPane").getChamada()));
        initComponents.addCorpo(layout.getDeclaração(layout.instancia().getInstancia()));
        initComponents.addCorpo(container.call("setLayout", layout.getReferencia()));
        initComponents.addCorpo(getMétodo("setLocationRelativeTo").getChamada("null"));
        initComponents.addCorpo(getMétodo("setTitle").getChamada("\"" + this.titulo + "\""));

        initComponents.addCorpo(gbc.getDeclaração(gbc.instancia().getInstancia()));
        initComponents.addCorpo(gbc.set("insets", Classe.get("java.awt.Insets", "5", "5", "5", "5").getInstancia()));
        initComponents.addCorpo(gbc.set("gridx", "0"));

        initComponents.addCorpo(panel.getDeclaração(panel.instancia().getInstancia()));
        initComponents.addCorpo(gbcPanel.getDeclaração(gbcPanel.instancia().getInstancia()));
        initComponents.addCorpo(panel.call("setLayout", Classe.get("java.awt.GridBagLayout").getInstancia()));
        initComponents.addCorpo(gbcPanel.set("insets", Classe.get("java.awt.Insets", "5", "5", "5", "5").getInstancia()));
        initComponents.addCorpo(gbcPanel.set("anchor", "GridBagConstraints.CENTER"));

        Construtor c = getConstrutorPrincipal();
        c.addCorpo(initComponents.getChamada());

    }

    public Janela(String nome, String pacote, String caminho) {
        this(nome);
        this.pacote = new Pacote(pacote, caminho);
    }

    public Janela(String nome, String pacote) {
        this(nome);
        this.pacote = new Pacote(pacote);
    }

    /**
     * Adicionado um componente que já vem com a inicialização junto com a
     * declaração ativada
     *
     * @param tipo
     * @param nome
     * @param texto
     * @return
     */
    public Atributo addComponent(String tipo, String nome, String texto) {
        Atributo component = new Atributo(tipo, nome);
        addAtributo(component);
        component.ativarInicialização(
                component.instancia("\"" + texto + "\"").
                        getInstancia());

        return component;
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

    public void setTitutlo(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void addCampoEntrada(String nomeCampo) {
        Atributo jlabel = addComponent("JLabel", "lb" + upperCase(nomeCampo), upperCase(nomeCampo) + ":");
        Atributo jtext = addComponent("JTextField", "txt" + upperCase(nomeCampo), "");

        Variavel tmpPanel = new Variavel("JPanel", "tmpPanel" + getAtributos().size());
        Variavel tmpGbc = new Variavel("GridBagConstraints", "tmpGbc" + getAtributos().size());

        tmpPanel.setClasse(this);
        tmpGbc.setClasse(this);
        initComponents.addCorpo(tmpPanel.getDeclaração(tmpPanel.instancia().getInstancia()));
        initComponents.addCorpo(tmpGbc.getDeclaração(tmpGbc.instancia().getInstancia()));
        initComponents.addCorpo(tmpPanel.call("setLayout", Classe.get("java.awt.GridBagLayout").getInstancia()));
        initComponents.addCorpo(tmpGbc.set("anchor", "GridBagConstraints.WEST"));
        initComponents.addCorpo(tmpGbc.set("gridx", "0"));
        initComponents.addCorpo(tmpPanel.call("add", jlabel.getReferencia(), tmpGbc.getReferencia()));
        initComponents.addCorpo(tmpGbc.set("anchor", "GridBagConstraints.CENTER"));
        initComponents.addCorpo(jtext.call("setColumns", "25"));
        initComponents.addCorpo(jtext.call("setToolTipText", "\"" + upperCase(nomeCampo) + " por favor\""));
        initComponents.addCorpo(tmpPanel.call("add", jtext.getReferencia(), tmpGbc.getReferencia()));
        initComponents.addCorpo(container.call("add", tmpPanel.getReferencia(), gbc.getReferencia()));
    }

    public void loadButtons() {
        Atributo btnAdd = addComponent("JButton", "btnAdd", "Adicionar");
        Atributo btnUpdate = addComponent("JButton", "btnUpdate", "Atualizar");
        Atributo btnDelete = addComponent("JButton", "btnDelete", "Deletar");
        Atributo btnSearch = addComponent("JButton", "btnSearch", "Pesquisar");
        initComponents.addCorpo(gbcPanel.set("gridx", "0"));
        initComponents.addCorpo(panel.call("add", btnAdd.getReferencia(), gbcPanel.getReferencia()));
        initComponents.addCorpo(gbcPanel.set("gridx", "1"));
        initComponents.addCorpo(panel.call("add", btnUpdate.getReferencia(), gbcPanel.getReferencia()));
        initComponents.addCorpo(gbcPanel.set("gridx", "2"));
        initComponents.addCorpo(panel.call("add", btnDelete.getReferencia(), gbcPanel.getReferencia()));
        initComponents.addCorpo(gbcPanel.set("gridx", "3"));
        initComponents.addCorpo(panel.call("add", btnSearch.getReferencia(), gbcPanel.getReferencia()));
        initComponents.addCorpo(container.call("add", panel.getReferencia(), gbc.getReferencia()));
    }

    public void loadModelo(Modelo modelo) {
        for (Atributo atr : modelo.getAtributos()) {
            addCampoEntrada(atr.getNome());
        }

        loadButtons();
        initComponents.addCorpo(getMétodo("pack").getChamada());
    }

}
