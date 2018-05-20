package appbuilder.api.classes;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import appbuilder.api.vars.Objeto;
import appbuilder.api.methods.*;
import appbuilder.api.packages.*;
import appbuilder.api.vars.*;
import appbuilder.util.*;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pedro Henrique Braga da Silva
 */
public class Classe {

    protected Pacote pacote;
    protected String modAcesso;//modificador de acesso ,Ex: public, private 
    protected String nome;

    private List<Atributo> atributos = new ArrayList<Atributo>();
    private List<Método> métodos = new ArrayList<Método>();
    private List<Importação> importações = new ArrayList<Importação>();

    //construtor principal
    private Construtor construtorPrincipal;
    //método principal
    private Método métodoMain;

    //classes prontas, usando api de reflection
    protected static Map<String, Classe> classes = new HashMap<>();

    //deixo classes prontas, já predefinidas
    static {
        try {
            addClasse("String", "lang", "java");
            addClasse("Integer", "lang", "java");
            addClasse("Object", "lang", "java");
            addClasse("ArrayList", "util", "java");

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Classe.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Não foi possível carregar as classes");
        }
    }

    public Classe(String nome) {
        this.nome = nome;
        this.modAcesso = "public";
        this.pacote = new Pacote(nome.toLowerCase());

        //adicionar um construtor padrão
        construtorPrincipal = new Construtor("public", this.getNome());
        addConstrutor(construtorPrincipal);
    }

    public Classe(String nome, String pacote) {
        this(nome);
        this.pacote = new Pacote(pacote);
    }

    public Classe(String nome, String pacote, String caminho) {
        this(nome);
        this.pacote = new Pacote(pacote, caminho);
    }

    /**
     * Define se essa classe é principal ou não, ou seja, se tem "public static
     * voi main(String [] args )"
     *
     * @param b
     * @return
     */
    public boolean setPrincipal(boolean b) {
        if (b) {
            métodoMain = new Método("public", "static", "void", "main");
            métodoMain.addParametro("String[]", "args");
            return addMétodo(métodoMain);
        } else {
            boolean rt = métodos.remove(métodoMain);
            métodoMain = null;
            return rt;

        }
    }

    //referente quanto na forma de objeto
    public Objeto getInstancia(String... argumentos) {
        //defino o tipo do objeto, que é instância da classe 
        Objeto obj = new Objeto(this);
        obj.setInstancia(argumentos); //defino os argumentos da instância dele
        return obj;

        //devolvo ele
    }

    /**
     * Retorna uma instância de uma classe já predefinida
     *
     * @param nome
     * @param argumentos
     * @return
     */
    public static Objeto get(String nome, String... argumentos) {
        return classes.get(nome).getInstancia(argumentos);
    }

    public Pacote getPacote() {
        return this.pacote;
    }

    public void setPacote(Pacote pacote) {
        this.pacote = pacote;
    }

    public void setPacote(String pacote) {
        this.pacote = new Pacote(pacote);
    }

    public Construtor getConstrutorPrincipal() {
        return construtorPrincipal;
    }

    public void setConstrutorPrincipal(Construtor construtorPrincipal) {
        this.construtorPrincipal = construtorPrincipal;
    }

    public String getNomeCompleto() {
        return pacote.getCaminho() + "." + getNome();
    }

    public Método getMain() {
        return this.métodoMain;
    }

    public String toString() {
        String codigo = "";

        //pacote
        codigo += this.pacote + ";\n\n";
        //classe
        codigo += this.modAcesso + " class " + this.nome + " { \n\n";

        for (Atributo var : atributos) {
            codigo += var.getDeclaração();
        }

        for (Método met : métodos) {
            codigo += met;
        }

        codigo += "} \n\n";

        return codigo;
    }

    public boolean addAtributo(Atributo atr) {

        return this.atributos.add(atr);
    }

    public boolean addConstrutor(Construtor contrutor) {
        return this.métodos.add(contrutor);
    }

    //leva em consideração que pode ter vários parâmetros de tipos diferentes e o modificador pode ser genérico também, 
    //adicionar construtor genérico
    public Construtor addConstrutor(String modAcesso, Parametro... params) {
        Construtor construtor = new Construtor(modAcesso, nome);
        List<Parametro> lista = new ArrayList<>();

        for (Parametro param : params) {
            lista.add(param);
        }

        construtor.setParametros(lista);

        if (addConstrutor(construtor)) {
            return construtor;
        } else {
            return null;
        }
    }

    //especializa o método acima, para adicionar construtores públicos para reaproveitamento de código
    public Construtor addConstrutorPúblico(Parametro... params) {
        return addConstrutor("public", params);
    }

    //construtor privado
    public Construtor addConstrutorPrivado(Parametro... params) {
        return addConstrutor("private", params);
    }

    public boolean addMétodo(Método metodo) {
        return this.métodos.add(metodo);
    }

    public boolean removeMétodo(Método metodo) {
        return this.métodos.remove(metodo);
    }

    public String getModAcesso() {
        return modAcesso;
    }

    public void setModAcesso(String modAcesso) {
        this.modAcesso = modAcesso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Atributo> getAtributos() {
        return atributos;
    }

    public void setAtributos(List<Atributo> atributos) {
        this.atributos = atributos;
    }

    public List<Método> getMétodos() {
        return métodos;
    }

    public void setMetódos(List<Método> metódos) {
        this.métodos = metódos;
    }

    public List<Importação> getImportações() {
        return importações;
    }

    public void setImportações(List<Importação> importações) {
        this.importações = importações;
    }

    void setConstrutorPrincipal(String apublic, String nome) {
        setConstrutorPrincipal(new Construtor(apublic, nome));
    }

    //retorna o atributo com base no nome
    public Atributo getAtributo(String nome) {

        Atributo atributo = null;

        for (Atributo atr : getAtributos()) {
            if (atr.getNome().equals(nome)) {
                atributo = atr;
            }
        }
        return atributo;
    }

    //retorna o método com base no nome
    public Método getMétodo(String nome) {
        Método método = null;

        for (Método met : getMétodos()) {
            if (met.getNome().equals(nome)) {
                método = met;
            }
        }

        return método;
    }

    //cria uma classe a partir de uma já predefinida
    public static Classe addClasse(String nome, String pacote, String caminho) throws ClassNotFoundException {
        Classe classe = new Classe(nome, pacote, caminho);
        Class predefinida = Class.forName(classe.getNomeCompleto());

        for (Method method : predefinida.getDeclaredMethods()) {
            String name = method.getName();
            String retType = method.getReturnType().getSimpleName();
            Parameter[] params = method.getParameters();
            Método metodo = new Método("public", retType, name);

            for (Parameter param : params) {
                Class cl = param.getType();

                metodo.addParametro(cl.getSimpleName(), param.getName());

            }

            classe.addMétodo(metodo);
        }

        return addClasse(classe);
    }

    public static Classe addClasse(Classe classe) {
        return classes.put(classe.getNomeCompleto(), classe);
    }

    public static Classe getClasse(String nome) {
        return classes.get(nome);
    }

    public Método callStatic(String método, String... args) {
        return getMétodo(nome);
    }
}
