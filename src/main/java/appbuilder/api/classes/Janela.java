/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.api.classes;

import appbuilder.api.methods.MethodBuilder;
import appbuilder.api.methods.ParameterBuilder;
import appbuilder.api.packages.PackageBuilder;
import appbuilder.api.vars.AttributeBuilder;
import appbuilder.api.vars.VarBuilder;

/**
 *
 * @author aluno
 */
public class Janela extends ClassBuilder {

    private MethodBuilder initComponents;
    private VarBuilder layout = null;
    private VarBuilder container = null;
    private String titulo = "JanelaPrincipal";
    private VarBuilder gbc = null;
    private VarBuilder panel = null;
    private VarBuilder gbcPanel = null;

    private AttributeBuilder dao = null;

    static {
        try {
            ClassBuilder.addClassBuilder("JFrame", "swing", "javax");
            ClassBuilder.addClassBuilder("JButton", "swing", "javax");
            ClassBuilder.addClassBuilder("JLabel", "swing", "javax");
            ClassBuilder.addClassBuilder("JTextField", "swing", "javax");
            ClassBuilder.addClassBuilder("Insets", "awt", "java");
            ClassBuilder.addClassBuilder("JPanel", "swing", "javax");
            ClassBuilder.addClassBuilder("GridBagLayout", "awt", "java");
            ClassBuilder.addClassBuilder("GridBagConstraints", "awt", "java");
            ClassBuilder.addClassBuilder("Container", "awt", "java");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();;
        }
    }

    public Janela(String nome) {
        super(nome);
        if (!nome.endsWith("JFrame")) {
            setName(nome + "JFrame");
        }

        addImport("javax.swing.JFrame");
        addImport("javax.swing.JButton");
        addImport("javax.swing.JLabel");
        addImport("javax.swing.JTextField");
        addImport("java.awt.Insets");
        addImport("java.awt.GridBagLayout");
        addImport("java.awt.GridBagConstraints");
        addImport("java.awt.Container");
        addImport("javax.swing.JPanel");

        setSuperClass("JFrame");
        setPrincipalConstructor(getConstructors().get(0));
        //só vou usar o primeiro construtor
        for (ConstructorBuilder c : getConstructors()) {
            if (!(c == getPrincipalConstructor())) {
                removeConstrutor(c);
            }
        }

        delegarConstrutores();

        initComponents = new MethodBuilder("public", "void", "initComponents");
        addMethod(this.initComponents);
        //configurações gerais da janela 
        initComponents.addCorpo(getMethodBuilder("setDefaultCloseOperation")
                .getCall("javax.swing.WindowConstants.EXIT_ON_CLOSE"));
        initComponents.addCorpo(getMethodBuilder("setSize").getCall("500", "400"));
        container = new VarBuilder("Container", "container");
        container.setClasse(this);
        layout = new VarBuilder("GridBagLayout", "layout");
        layout.setClasse(this);
        gbc = new VarBuilder("GridBagConstraints", "gbc");
        gbc.setClasse(this);
        panel = new VarBuilder("JPanel", "panel");
        panel.setClasse(this);
        gbcPanel = new VarBuilder("GridBagConstraints", "gbcPanel");
        gbcPanel.setClasse(this);

        initComponents.addCorpo(container.getDeclaração(getMethodBuilder("getContentPane").getCall()));
        initComponents.addCorpo(layout.getDeclaração(layout.instancia().getInstancia()));
        initComponents.addCorpo(container.call("setLayout", layout.getReferencia()));
        initComponents.addCorpo(getMethodBuilder("setLocationRelativeTo").getCall("null"));
        initComponents.addCorpo(getMethodBuilder("setTitle").getCall("\"" + this.titulo + "\""));

        initComponents.addCorpo(gbc.getDeclaração(gbc.instancia().getInstancia()));
        initComponents.addCorpo(gbc.set("insets", ClassBuilder.get("java.awt.Insets", "5", "5", "5", "5").getInstancia()));
        initComponents.addCorpo(gbc.set("gridx", "0"));

        initComponents.addCorpo(panel.getDeclaração(panel.instancia().getInstancia()));
        initComponents.addCorpo(gbcPanel.getDeclaração(gbcPanel.instancia().getInstancia()));
        initComponents.addCorpo(panel.call("setLayout", ClassBuilder.get("java.awt.GridBagLayout").getInstancia()));
        initComponents.addCorpo(gbcPanel.set("insets", ClassBuilder.get("java.awt.Insets", "5", "5", "5", "5").getInstancia()));
        initComponents.addCorpo(gbcPanel.set("anchor", "GridBagConstraints.CENTER"));

        ConstructorBuilder c = getPrincipalConstructor();
        c.addCorpo(initComponents.getCall());

    }

    public Janela(String nome, String pacote, String caminho) {
        this(nome);
        this.packageBuilder = new PackageBuilder(pacote, caminho);
    }

    public Janela(String nome, String pacote) {
        this(nome);
        this.packageBuilder = new PackageBuilder(pacote);
    }

    public void addDao(DaoBuilder dao) {
        addImport(dao);
        ConstructorBuilder cprincipal = getPrincipalConstructor();
        this.dao = new AttributeBuilder("private", dao.getName(), "dao");
        this.dao.ativarInicialização(dao.getInstancia().getInstancia());
        addAttribute(this.dao);
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
    public AttributeBuilder addComponent(String tipo, String nome, String texto) {
        AttributeBuilder component = new AttributeBuilder(tipo, nome);
        addAttribute(component);
        component.ativarInicialização(
                component.instancia("\"" + texto + "\"").
                        getInstancia());

        return component;
    }

    public void delegarConstrutores() {
        for (ConstructorBuilder constructorBuilder : super.getConstructors()) {
            String codigo = "";
            // indica a posição do parâmetro
            int contador = 1;

            for (ParameterBuilder param : constructorBuilder.getParameters()) {
                // toda vez que chegar no próximo parâmetro, colocar uma vírgula
                if (contador % 2 == 0) {
                    codigo += ",";
                    contador = 1;
                }
                // coloca o parâmetro
                codigo += param.getReferencia();
                contador++;
            }

            constructorBuilder.addCorpo("super(" + codigo + ")");

        }
    }

    public void setTitutlo(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void addCampoEntrada(String nomeCampo) {
        AttributeBuilder jlabel = addComponent("JLabel", "lb" + upperCase(nomeCampo), upperCase(nomeCampo) + ":");
        AttributeBuilder jtext = addComponent("JTextField", "txt" + upperCase(nomeCampo), "");

        VarBuilder tmpPanel = new VarBuilder("JPanel", "tmpPanel" + getAttributes().size());
        VarBuilder tmpGbc = new VarBuilder("GridBagConstraints", "tmpGbc" + getAttributes().size());

        tmpPanel.setClasse(this);
        tmpGbc.setClasse(this);
        initComponents.addCorpo(tmpPanel.getDeclaração(tmpPanel.instancia().getInstancia()));
        initComponents.addCorpo(tmpGbc.getDeclaração(tmpGbc.instancia().getInstancia()));
        initComponents.addCorpo(tmpPanel.call("setLayout", ClassBuilder.get("java.awt.GridBagLayout").getInstancia()));
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
        AttributeBuilder btnAdd = addComponent("JButton", "btnAdd", "Adicionar");
        AttributeBuilder btnUpdate = addComponent("JButton", "btnUpdate", "Atualizar");
        AttributeBuilder btnDelete = addComponent("JButton", "btnDelete", "Deletar");
        AttributeBuilder btnSearch = addComponent("JButton", "btnSearch", "Pesquisar");
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

    public void loadModelo(ModelBuilder modelBuilder) {
        for (AttributeBuilder atr : modelBuilder.getAttributes()) {
            addCampoEntrada(atr.getName());
        }

        loadButtons();
        initComponents.addCorpo(getMethodBuilder("pack").getCall());
    }

}
