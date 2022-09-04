/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

namespace Api.Old.Classes;

/// <summary>
/// </summary>
/// <remarks>@authoraluno</remarks>
public class Janela : ClassBuilder
{
    private VarBuilder container = null;
    private AttributeBuilder dao = null;
    private VarBuilder gbc = null;
    private VarBuilder gbcPanel = null;
    private MethodBuilder initComponents;
    private VarBuilder layout = null;
    private VarBuilder panel = null;
    private string titulo = "JanelaPrincipal";

    static Janela()
    {
        try
        {
            AddClassBuilder("JFrame", "swing", "javax");
            AddClassBuilder("JButton", "swing", "javax");
            AddClassBuilder("JLabel", "swing", "javax");
            AddClassBuilder("JTextField", "swing", "javax");
            AddClassBuilder("Insets", "awt", "java");
            AddClassBuilder("JPanel", "swing", "javax");
            AddClassBuilder("GridBagLayout", "awt", "java");
            AddClassBuilder("GridBagConstraints", "awt", "java");
            AddClassBuilder("Container", "awt", "java");
        }
        catch (ClassNotFoundException ex)
        {
            ex.PrintStackTrace();
        }
    }

    public Janela(string nome) : base(nome)
    {
        if (!nome.EndsWith("JFrame")) SetName(nome + "JFrame");

        AddImport("javax.swing.JFrame");
        AddImport("javax.swing.JButton");
        AddImport("javax.swing.JLabel");
        AddImport("javax.swing.JTextField");
        AddImport("java.awt.Insets");
        AddImport("java.awt.GridBagLayout");
        AddImport("java.awt.GridBagConstraints");
        AddImport("java.awt.Container");
        AddImport("javax.swing.JPanel");
        SetSuperClass("JFrame");
        SetPrincipalConstructor(GetConstructors()[0]);

        //só vou usar o primeiro construtor
        foreach (var c in GetConstructors())
            if (!(c == GetPrincipalConstructor()))
                RemoveConstrutor(c);

        DelegarConstrutores();
        initComponents = new MethodBuilder("public", "void", "initComponents");
        AddMethod(initComponents);

        //configurações gerais da janela 
        initComponents.AddCorpo(GetMethodBuilder("setDefaultCloseOperation")
            .GetCall("javax.swing.WindowConstants.EXIT_ON_CLOSE"));
        initComponents.AddCorpo(GetMethodBuilder("setSize").GetCall("500", "400"));
        container = new VarBuilder("Container", "container");
        container.SetClasse(this);
        layout = new VarBuilder("GridBagLayout", "layout");
        layout.SetClasse(this);
        gbc = new VarBuilder("GridBagConstraints", "gbc");
        gbc.SetClasse(this);
        panel = new VarBuilder("JPanel", "panel");
        panel.SetClasse(this);
        gbcPanel = new VarBuilder("GridBagConstraints", "gbcPanel");
        gbcPanel.SetClasse(this);
        initComponents.AddCorpo(container.GetDeclaração(GetMethodBuilder("getContentPane").GetCall()));
        initComponents.AddCorpo(layout.GetDeclaração(layout.Instancia().GetInstancia()));
        initComponents.AddCorpo(container.Call("setLayout", layout.GetReferencia()));
        initComponents.AddCorpo(GetMethodBuilder("setLocationRelativeTo").GetCall("null"));
        initComponents.AddCorpo(GetMethodBuilder("setTitle").GetCall("\\\"" + titulo + "\\\""));
        initComponents.AddCorpo(gbc.GetDeclaração(gbc.Instancia().GetInstancia()));
        initComponents.AddCorpo(gbc["insets"] = Get("java.awt.Insets", "5", "5", "5", "5").GetInstancia());
        initComponents.AddCorpo(gbc["gridx"] = "0");
        initComponents.AddCorpo(panel.GetDeclaração(panel.Instancia().GetInstancia()));
        initComponents.AddCorpo(gbcPanel.GetDeclaração(gbcPanel.Instancia().GetInstancia()));
        initComponents.AddCorpo(panel.Call("setLayout", ClassBuilder["java.awt.GridBagLayout"].GetInstancia()));
        initComponents.AddCorpo(gbcPanel["insets"] = Get("java.awt.Insets", "5", "5", "5", "5").GetInstancia());
        initComponents.AddCorpo(gbcPanel["anchor"] = "GridBagConstraints.CENTER");
        var c = GetPrincipalConstructor();
        c.AddCorpo(initComponents.GetCall());
    }

    public Janela(string nome, string pacote, string caminho) : this(nome)
    {
        packageBuilder = new PackageBuilder(pacote, caminho);
    }

    public Janela(string nome, string pacote) : this(nome)
    {
        packageBuilder = new PackageBuilder(pacote);
    }

    public virtual void AddDao(DaoBuilder dao)
    {
        AddImport(dao);
        var cprincipal = GetPrincipalConstructor();
        this.dao = new AttributeBuilder("private", dao.GetName(), "dao");
        this.dao.AtivarInicialização(dao.GetInstancia().GetInstancia());
        AddAttribute(this.dao);
    }

    /// <summary>
    ///     Adicionado um componente que já vem com a inicialização junto com a
    ///     declaração ativada
    /// </summary>
    /// <param name="tipo"></param>
    /// <param name="nome"></param>
    /// <param name="texto"></param>
    /// <returns></returns>
    public virtual AttributeBuilder AddComponent(string tipo, string nome, string texto)
    {
        AttributeBuilder component = new AttributeBuilder(tipo, nome);
        AddAttribute(component);
        component.AtivarInicialização(component.Instancia("\\\"" + texto + "\\\"").GetInstancia());
        return component;
    }

    public virtual void DelegarConstrutores()
    {
        foreach (var constructorBuilder in base.GetConstructors())
        {
            var codigo = "";

            // indica a posição do parâmetro
            var contador = 1;
            foreach (ParameterBuilder param in constructorBuilder.GetParameters())
            {
                // toda vez que chegar no próximo parâmetro, colocar uma vírgula
                if (contador % 2 == 0)
                {
                    codigo += ",";
                    contador = 1;
                }


                // coloca o parâmetro
                codigo += param.GetReferencia();
                contador++;
            }

            constructorBuilder.AddCorpo("super(" + codigo + ")");
        }
    }

    public virtual void SetTitutlo(string titulo)
    {
        this.titulo = titulo;
    }

    public virtual string GetTitulo()
    {
        return titulo;
    }

    public virtual void AddCampoEntrada(string nomeCampo)
    {
        AttributeBuilder jlabel = AddComponent("JLabel", "lb" + UpperCase(nomeCampo), UpperCase(nomeCampo) + ":");
        AttributeBuilder jtext = AddComponent("JTextField", "txt" + UpperCase(nomeCampo), "");
        VarBuilder tmpPanel = new VarBuilder("JPanel", "tmpPanel" + GetAttributes().Count);
        VarBuilder tmpGbc = new VarBuilder("GridBagConstraints", "tmpGbc" + GetAttributes().Count);
        tmpPanel.SetClasse(this);
        tmpGbc.SetClasse(this);
        initComponents.AddCorpo(tmpPanel.GetDeclaração(tmpPanel.Instancia().GetInstancia()));
        initComponents.AddCorpo(tmpGbc.GetDeclaração(tmpGbc.Instancia().GetInstancia()));
        initComponents.AddCorpo(tmpPanel.Call("setLayout", ClassBuilder["java.awt.GridBagLayout"].GetInstancia()));
        initComponents.AddCorpo(tmpGbc["anchor"] = "GridBagConstraints.WEST");
        initComponents.AddCorpo(tmpGbc["gridx"] = "0");
        initComponents.AddCorpo(tmpPanel.Call("add", jlabel.GetReferencia(), tmpGbc.GetReferencia()));
        initComponents.AddCorpo(tmpGbc["anchor"] = "GridBagConstraints.CENTER");
        initComponents.AddCorpo(jtext.Call("setColumns", "25"));
        initComponents.AddCorpo(jtext.Call("setToolTipText", "\\\"" + UpperCase(nomeCampo) + " por favor\\\""));
        initComponents.AddCorpo(tmpPanel.Call("add", jtext.GetReferencia(), tmpGbc.GetReferencia()));
        initComponents.AddCorpo(container.Call("add", tmpPanel.GetReferencia(), gbc.GetReferencia()));
    }

    public virtual void LoadButtons()
    {
        AttributeBuilder btnAdd = AddComponent("JButton", "btnAdd", "Adicionar");
        AttributeBuilder btnUpdate = AddComponent("JButton", "btnUpdate", "Atualizar");
        AttributeBuilder btnDelete = AddComponent("JButton", "btnDelete", "Deletar");
        AttributeBuilder btnSearch = AddComponent("JButton", "btnSearch", "Pesquisar");
        initComponents.AddCorpo(gbcPanel["gridx"] = "0");
        initComponents.AddCorpo(panel.Call("add", btnAdd.GetReferencia(), gbcPanel.GetReferencia()));
        initComponents.AddCorpo(gbcPanel["gridx"] = "1");
        initComponents.AddCorpo(panel.Call("add", btnUpdate.GetReferencia(), gbcPanel.GetReferencia()));
        initComponents.AddCorpo(gbcPanel["gridx"] = "2");
        initComponents.AddCorpo(panel.Call("add", btnDelete.GetReferencia(), gbcPanel.GetReferencia()));
        initComponents.AddCorpo(gbcPanel["gridx"] = "3");
        initComponents.AddCorpo(panel.Call("add", btnSearch.GetReferencia(), gbcPanel.GetReferencia()));
        initComponents.AddCorpo(container.Call("add", panel.GetReferencia(), gbc.GetReferencia()));
    }

    public virtual void LoadModelo(ModelBuilder modelBuilder)
    {
        foreach (AttributeBuilder atr in modelBuilder.GetAttributes()) AddCampoEntrada(atr.GetName());

        LoadButtons();
        initComponents.AddCorpo(GetMethodBuilder("pack").GetCall());
    }
}