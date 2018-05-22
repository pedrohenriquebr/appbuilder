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
import java.lang.reflect.Modifier;
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
    private boolean éInterface;

    private List<Atributo> atributos = new ArrayList<Atributo>();
    private List<Método> métodos = new ArrayList<Método>();
    private List<Importação> importações = new ArrayList<Importação>();

    //construtor principal
    private Construtor construtorPrincipal;
    //método principal
    private Método métodoMain;

    //classes prontas, usando api de reflection
    //nome totalmente qualificado com o objeto da classe pronta
    public static Map<String, Classe> classes = new HashMap<>();

    //Associa nome da classe com o nome totalmente qualificado
    public Map<String, String> nomesCompletos = new HashMap<>();

    //deixo classes prontas, já predefinidas
    static {
        /*  try {
            addClasse("String", "lang", "java");
            addClasse("Integer", "lang", "java");
            addClasse("Object", "lang", "java");
            addClasse("ArrayList", "util", "java");

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Classe.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Não foi possível carregar as classes");
        }
        
         */
    }
    private List<String> modsNAcesso;

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
    
   
    public void setInterface(boolean b ){
        this.éInterface = true;
    }
    
    public boolean éInterface(){
        return this.éInterface;
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
        //importações

        for (Importação importação : importações) {
            codigo += importação + ";\n\n";
        }
        //classe
        codigo += this.modAcesso;

        //indica se é uma interface ou não
        String tipo
                = this.modsNAcesso.indexOf("interface") >= 0
                ? /* se verdadeiro, então é interface*/ "interface"
                : /*caso contrário, é uma classe */ "class";

        //pegar os modificadores de não-acesso
        for (String mod : modsNAcesso) {
            //pula modificador de interface, pra não repetir
            if (mod.equals("interface")) {
                continue;
            }

            //se é uma interface, então não precisa colocar o abstract
            if (tipo.equals("interface") && mod.equals("abstract")) {
                continue;
            }

            codigo += " " + mod + " ";
        }

        codigo += " " + tipo + " " + this.nome + " { \n\n";

        for (Atributo var : atributos) {
            codigo += var.getDeclaração();
        }

        for (Método met : métodos) {
            codigo += met;
        }

        codigo += "} \n\n";

        return codigo;
    }

    /**
     * Adiciona um atributo à classe. Todo atributo tem uma classe na qual ele
     * pertenc, então é passado para setClasse a classe que está adicionando o
     * atributo
     *
     * @param atr atributo
     * @return true ou false se a operação foi realizada com sucesso
     */
    public boolean addAtributo(Atributo atr) {
        //pega o tipo de atributo
        String tipo = atr.getTipo();
        atr.setClasse(this);

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

    //cria uma classe a partir de uma já predefinida, classes prontas da linguagem Java
    public static Classe addClasse(String nome, String pacote, String caminho) throws ClassNotFoundException {
        Classe classe = new Classe(nome, pacote, caminho);
        Class predefinida = Class.forName(classe.getNomeCompleto());
        List<String> modifiers = modifiersFromInt(predefinida.getModifiers());

        //coloca o modificador de acesso
        classe.setModAcesso(modifiers.get(0));

        //modificadores de não-acesso
        List<String> mnacesso = new ArrayList<>();

        for (int k = 1; k < modifiers.size(); k++) {
            String mod = modifiers.get(k);
            mnacesso.add(mod);

        }

        //coloca os modificadores de não-acesso
        classe.setModNAcesso(mnacesso);

        for (Method method : predefinida.getDeclaredMethods()) {
            String name = method.getName();
            String retType = method.getReturnType().getSimpleName();
            Parameter[] params = method.getParameters();

            List<String> mods = modifiersFromInt(method.getModifiers());
            //por padrão o modificador de acesso é public
            Método metodo = new Método("public", retType, name);

            //em teoria o primeiro modificador é de acesso
            metodo.setModAcesso(mods.get(0));

            List<String> modsnacesso = new ArrayList<>();

            //começando a partir do segundo modificador
            for (int j = 1; j < mods.size(); j++) {
                modsnacesso.add(mods.get(j));
            }

            //coloca todos os mdificadores de não-acesso 
            metodo.setModNacesso(modsnacesso);

            for (Parameter param : params) {
                Class cl = param.getType();

                metodo.addParametro(cl.getSimpleName(), param.getName());
            }

            classe.addMétodo(metodo);
        }

        return addClasse(classe);
    }

    private static int modifierFromString(String s) {
        int m = 0x0;
        if ("public".equals(s)) {
            m |= Modifier.PUBLIC;
        } else if ("protected".equals(s)) {
            m |= Modifier.PROTECTED;
        } else if ("private".equals(s)) {
            m |= Modifier.PRIVATE;
        } else if ("static".equals(s)) {
            m |= Modifier.STATIC;
        } else if ("final".equals(s)) {
            m |= Modifier.FINAL;
        } else if ("transient".equals(s)) {
            m |= Modifier.TRANSIENT;
        } else if ("volatile".equals(s)) {
            m |= Modifier.VOLATILE;
        }
        return m;
    }

    private static List<String> modifiersFromInt(int modifiers) {
        List<String> modNames = Arrays.asList(Modifier.toString(modifiers).split("\\s"));
        return modNames;
    }

    //adicionar uma classe criada pelo usuário
    public static Classe addClasse(Classe classe) {
        return classes.put(classe.getNomeCompleto(), classe);
    }

    /**
     * Adiciona o nome da classe com o nome totalmente qualificado dela
     *
     * @param nome
     * @param classe
     * @return o caminhoCompleto
     */
    public String addNomeCompleto(String nome, String nomeCompleto) {

        return this.nomesCompletos.put(nome, nomeCompleto);
    }

    public String getNomeCompleto(String nome) {
        return this.nomesCompletos.get(nome);
    }

    public static Classe getClasseEstática(String nome) {
        return classes.get(nome);
    }

    //acessar uma classe importada
    public Classe getClasse(String nome) {
        return classes.get(nomesCompletos.get(nome));
    }

    public Método callStatic(String método, String... args) {
        return getMétodo(nome);
    }

    public boolean addImportação(Classe classe) {
        addNomeCompleto(classe.getNome(), classe.getNomeCompleto());
        return this.importações.add(new Importação(classe.getNome(), classe.getPacote().getCaminho()));
    }

    public String getImportação(String classe) {
        String caminho = "";
        for (Importação importa : importações) {
            if (importa.getClasse().equals(classe)) {

                caminho = importa.getCaminho();
            }
        }

        return caminho;
    }

    public void setModNAcesso(List<String> modsnacesso) {
        this.modsNAcesso = modsnacesso;
    }
    
    
    public List<String> getModNAcesso() {

        return this.modsNAcesso;
    }

}
