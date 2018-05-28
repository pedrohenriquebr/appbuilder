package appbuilder.api.classes;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import appbuilder.api.methods.Método;
import appbuilder.api.methods.Parametro;
import appbuilder.api.packages.Importação;
import appbuilder.api.packages.Pacote;
import appbuilder.api.vars.Atributo;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import appbuilder.api.vars.Objeto;
import appbuilder.main.AppBuilder;
import appbuilder.util.Log;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author Pedro Henrique Braga da Silva
 */
public class Classe {

    protected Pacote pacote;
    protected String modAcesso;// modificador de acesso ,Ex: public, private
    protected String nome;
    private boolean éInterface;
    private static final Logger logger = Logger.getLogger(Level.INFO.getName());

    private List<Atributo> atributos = new ArrayList<Atributo>();
    private List<Método> métodos = new ArrayList<Método>();
    private List<Importação> importações = new ArrayList<Importação>();
    private List<Interface> interfaces = new ArrayList<Interface>();

    // modificadores de não-acesso
    private List<String> modsNAcesso = new ArrayList<>();

    // construtor principal
    private Construtor construtorPrincipal;
    // método principal
    private Método métodoMain;

    /**
     * classes prontas, usando api de reflection /*nome totalmente qualificado
     * com a classe pronta
     */
    private static Map<String, Classe> classes = new HashMap<>();

    /**
     * Associa nome da classe com o nome totalmente qualificado Uma espécie de
     * apelido, Ex: String -> java.lang.String
     */
    private Map<String, String> nomesCompletos = new HashMap<>();

    /**
     * A superclasse
     */
    private Classe superClasse;

    // serve para indicar se está executando o trecho estático de adição de classes
    private static boolean CONTEXTO_ESTÁTICO = false;

    // deixo classes prontas, já predefinidas
    static {
        Handler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter());
        logger.addHandler(handler);
        logger.setFilter(new AppBuilder());
        logger.log(Level.INFO, "começo: adicionando classes");
        CONTEXTO_ESTÁTICO = true;
        /* try {
           
            addClasse("Exception", "lang", "java");
            addClasse("String", "lang", "java");

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Level.INFO.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Não foi possível carregar as classes");
        } */

        logger.log(Level.INFO, "fim: adicionando classes");

        CONTEXTO_ESTÁTICO = false;
    }

    public Classe(String nome) {
        this.nome = nome;
        this.modAcesso = "public";
        this.pacote = new Pacote(nome.toLowerCase());

        if (!CONTEXTO_ESTÁTICO) {
            //addImportação(Classe.getClasseEstática("java.lang.String"));
        }

    }

    public Classe(String nome, String pacote) {
        this(nome);
        this.pacote = new Pacote(pacote);
    }

    public Classe(String nome, String pacote, String caminho) {
        this(nome);
        this.pacote = new Pacote(pacote, caminho);
    }

    private String camelCase(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

    /**
     * Define a superclasse com base nos pacotes ou classes já importadas Não
     * pode ter uma super classe que não esteja dentro das importações
     *
     * @param nomeClasse
     * @param superClasse
     */
    public void setSuperClasse(String nomeClasse) {
        Classe classe = getClasse(nomeClasse);

        logger.log(Level.INFO, "Superclasse definida: " + classe.getNome());
        try {

            setSuperClasse(classe);

        } catch (NullPointerException ex) {
            ex.printStackTrace();
            ;
            logger.log(Level.INFO, "superclasse nula!");
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setSuperClasse(Classe superClasse) throws CloneNotSupportedException {
        this.superClasse = superClasse;

        List<String> meusAtributos = new ArrayList<>();

        for (Atributo atr : this.getAtributos()) {
            meusAtributos.add(atr.getNome());
        }

        for (Atributo atr : superClasse.getAtributos()) {
            if (!meusAtributos.contains(atr.getNome())) {
                addAtributo(atr);
            }
        }

        List<String> meusMétodos = new ArrayList<>();

        for (Método met : this.getMétodos()) {
            meusMétodos.add(met.getNome());
        }

        for (Método met : superClasse.getMétodos()) {
            if (!meusMétodos.contains(met.getNome())) {
                logger.log(Level.WARNING, "Adicionando método : " + met.getNome());
                if (met instanceof Construtor) {
                    logger.log(Level.INFO, "construtor encontrado: " + met.getAssinatura());
                } else {
                    addMétodo(met);
                }
            }
        }

    }

    public Classe getSuperClasse() {
        return this.superClasse;
    }

    /**
     * Quando true, ele adiciona automaticamente o modificador interface Quando
     * false, ele remove automaticamenteo o modificador interface
     *
     * @param b
     */
    public void setInterface(boolean b) {
        this.éInterface = b;

        if (b) {
            addModNAcesso("interface");
        } else {
            removeModNAcesso("interface");
        }
    }

    public boolean éInterface() {
        return this.éInterface;
    }

    public boolean addInterface(Interface in) {

        for (Método met : in.getMétodos()) {
            // remove aquele flag de ser método de uma interface
            // e implementa
            try {
                Método metodo = (Método) met.clone();
                metodo.setDeInterface(false);
                addMétodo(metodo);
            } catch (CloneNotSupportedException c) {
                c.printStackTrace();
                ;
            }
        }

        return this.interfaces.add(in);
    }

    public boolean removeInterface(Interface in) {
        return this.interfaces.remove(in);
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

    // referente quanto na forma de objeto
    public Objeto getInstancia(String... argumentos) {
        // defino o tipo do objeto, que é instância da classe
        Objeto obj = new Objeto(this);
        obj.setInstancia(argumentos); // defino os argumentos da instância dele
        return obj;

        // devolvo ele
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

        /**
         * PACOTE
         */
        codigo += this.pacote + ";\n\n";
        /**
         * IMPORTAÇÕES
         */
        for (Importação importação : importações) {
            // não exibir, por padrão já vem importado
            if (!importação.getCaminho().equals("java.lang")) {
                codigo += importação + ";\n\n";
            }
        }

        /**
         * MODIFICADOR DE ACESSO
         */
        codigo += this.modAcesso;

        /**
         * INTERFACE
         */
        // indica se é uma interface ou não
        String tipo = éInterface == true ? /* se verdadeiro, então é interface */ "interface"
                : /* caso contrário, é uma classe */ "class";

        /**
         * MODIFICADORES DE NÃO-ACESSO
         */
        for (String mod : modsNAcesso) {
            // pula modificador de interface, pra não repetir
            if (mod.equals("interface")) {
                continue;
            }

            // se é uma interface, então não precisa colocar o abstract
            if (tipo.equals("interface") && mod.equals("abstract")) {
                continue;
            }

            codigo += " " + mod + " ";
        }

        /**
         * TIPO DE CLASSE
         */
        // tipo indentifica se é interface ou classe
        codigo += " " + tipo + " " + this.nome;
        logger.log(Level.INFO, "exibir nome da superclasse");
        // coloca o extends caso tenha uma superclasse
        /**
         * SUPERCLASSE
         */
        if (superClasse != null) {
            logger.log(Level.INFO, "exibindo superclasse superclasse existe");
            if (!superClasse.getNome().equals("Object")) {
                codigo += " extends " + superClasse.getNome();
            }
        }

        logger.log(Level.INFO, "Verificando se implementa alguma interface");
        /**
         * INTERFACES
         */
        if (interfaces.size() > 0) {
            codigo += " implements ";

            int contador = 1;
            for (Interface in : interfaces) {
                if (contador % 2 == 0) {
                    contador = 1;
                    codigo += ", ";
                }
                codigo += in.getNome();
                contador++;
            }

        }

        codigo += " { \n\n";

        /**
         * ATRIBUTOS
         */
        for (Atributo var : atributos) {

            if (superClasse != null) {

                if (!superClasse.temAtributo(var.getNome())) {
                    codigo += var.getDeclaração();
                }

            } else {
                codigo += var.getDeclaração();
            }
        }

        logger.log(Level.INFO, "exibir métodos");
        /**
         * MÉTODOS
         */
        for (Método met : métodos) {

            // verifica se a superclasse não tem esse método
            // se tiver, não precisa exibir
            if (superClasse != null) {

                if (!superClasse.temMétodo(met)) {
                    codigo += met;
                    logger.log(Level.INFO, "superclasse não tem " + met.getAssinatura());
                } else {
                    logger.log(Level.INFO, "superclasse tem " + met.getAssinatura());
                }
            } else {
                codigo += met;
            }
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
        atr.setClasse(this);
        return this.atributos.add(atr);
    }

    public boolean addConstrutor(Construtor construtor) {
        return this.métodos.add(construtor);
    }

    public boolean removeConstrutor(Construtor construtor) {
        return this.métodos.remove(construtor);
    }

    // leva em consideração que pode ter vários parâmetros de tipos diferentes e o
    // modificador pode ser genérico também,
    // adicionar construtor genérico
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

    // especializa o método acima, para adicionar construtores públicos para
    // reaproveitamento de código
    public Construtor addConstrutorPúblico(Parametro... params) {
        return addConstrutor("public", params);
    }

    // construtor privado
    public Construtor addConstrutorPrivado(Parametro... params) {
        return addConstrutor("private", params);
    }

    public boolean addMétodo(Método metodo) {
        return this.métodos.add(metodo);
    }

    public boolean removeMétodo(Método metodo) {
        return this.métodos.remove(metodo);
    }

    public boolean addGetter(Atributo atributo) {
        Método getter = new Método("public", atributo.getTipo(), "get" + camelCase(atributo.getNome()));
        getter.setRetorno(atributo.getReferencia());

        return addMétodo(getter);
    }

    public boolean addSetter(Atributo atributo) {
        Método setter = new Método("public", "void", "set" + camelCase(atributo.getNome()));
        setter.addParametro(atributo.getTipo(), atributo.getNome());
        setter.setCorpo(atributo.getInicialização(atributo.getNome()));

        return addMétodo(setter);
    }

    // adicionar construtores associados a atributos
    /**
     * Pegar um getter associado a um atributo
     *
     * @param atributo nome do atributo já declarado no modelo
     * @return
     */
    public Método getGetter(String atributo) {
        Método método = null;
        String camelCase = "get" + camelCase(atributo);
        método = getMétodo(camelCase);

        return método;
    }

    // retorna o setter com base no nome do atributo associado
    public Método getSetter(String atributo) {
        Método método = null;
        String camelCase = "set" + camelCase(atributo);
        método = getMétodo(camelCase);

        return método;
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

    public void setConstrutorPrincipal(String apublic, String nome) {
        setConstrutorPrincipal(new Construtor(apublic, nome));
    }

    /**
     * Retorna objeto do atributo com base no nome passado
     *
     * @param nome nome do atributo
     * @return Atributo com o nome especificado
     */
    public Atributo getAtributo(String nome) {

        Atributo atributo = null;

        for (Atributo atr : getAtributos()) {
            if (atr.getNome().equals(nome)) {
                atributo = atr;
            }
        }

        return atributo;
    }

    public boolean temAtributo(String nome) {
        return temAtributo(getAtributo(nome));
    }

    public boolean temAtributo(Atributo atributo) {
        return this.atributos.contains(atributo);
    }

    // retorna o método com base no nome
    public Método getMétodo(String nome) {
        Método método = null;

        for (Método met : getMétodos()) {
            if (met.getNome().equals(nome)) {
                método = met;
            }
        }

        return método;
    }

    public boolean temMétodo(Método metodo) {
        return this.métodos.contains(metodo);
    }

    // cria uma classe a partir de uma já predefinida, classes prontas da linguagem
    // Java
    public static Classe addClasse(String nome, String pacote, String caminho) throws ClassNotFoundException {
        Classe classe = new Classe(nome, pacote, caminho);
        Classe classePai = null;
        logger.log(Level.INFO, "adicionando classe " + classe.getNomeCompleto());

        logger.log(Level.INFO, classe.getNome() + " sendo pesquisada");
        // caso contrário, continua
        Class<?> predefinida = Class.forName(classe.getNomeCompleto());
        List<String> modifiers = modifiersFromInt(predefinida.getModifiers());
        Class<?> superClasse = predefinida.getSuperclass();

        if (superClasse != null) {
            // defino a superclasse para minha metaclasse
            // infelizmente teve recursividade
            String nomePacoteCompleto = superClasse.getPackage().getName();
            String nomeClasseCompleto = superClasse.getName();
            String[] nomePacoteDividido = nomePacoteCompleto.split("\\.");
            String[] nomeClasseDividido = nomeClasseCompleto.split("\\.");

            logger.log(Level.INFO, classe.getNomeCompleto() + " tem superclasse " + superClasse.getName());

            /**
             * superClasse.getName() retorna o nome totalmente qualificado:
             * java.lang.String superClasse.getPackage().getName() retorna o
             * nome totalmente qualificado: java.lang O nome da classe é a
             * última palavra, então eu precisei dividir por "." E precisei
             * fazer recursividade
             */
            try {
                classePai = Classe.addClasse(
                        nomeClasseDividido[nomeClasseDividido.length - 1],
                        nomePacoteDividido[nomePacoteDividido.length - 1], superClasse.getPackage().getName()
                                .replace("." + nomePacoteDividido[nomePacoteDividido.length - 1], ""));

                classe.setSuperClasse(classePai);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }

        // coloca o modificador de acesso
        classe.setModAcesso(modifiers.get(0));

        // modificadores de não-acesso
        List<String> mnacesso = new ArrayList<>();

        for (int k = 1; k < modifiers.size(); k++) {
            String mod = modifiers.get(k);
            mnacesso.add(mod);

        }

        // coloca os modificadores de não-acesso
        classe.setModNAcesso(mnacesso);

        if (mnacesso.contains("interface")) {
            classe.setInterface(true);
            classe.removeConstrutor(classe.getConstrutorPrincipal());
        }

        logger.log(Level.INFO, "adicionando métodos");
        // pega os métodos declarados
        for (Method method : predefinida.getDeclaredMethods()) {
            String name = method.getName();
            String retType = method.getReturnType().getSimpleName();
            Parameter[] params = method.getParameters();

            List<String> mods = modifiersFromInt(method.getModifiers());
            // por padrão o modificador de acesso é public
            Método metodo = new Método("public", retType, name);
            logger.log(Level.INFO, "adicionando método: " + metodo.getAssinatura());

            // em teoria o primeiro modificador é de acesso
            List<String> modsnacesso = new ArrayList<>();

            // começando a partir do segundo modificador
            for (int j = 1; j < mods.size(); j++) {
                modsnacesso.add(mods.get(j));
            }

            // coloca todos os mdificadores de não-acesso
            metodo.setModNacesso(modsnacesso);

            for (Parameter param : params) {
                Class<?> cl = param.getType();

                metodo.addParametro(cl.getSimpleName(), param.getName());
            }

            if (classe.éInterface) {
                metodo.setDeInterface(true);
                logger.log(Level.INFO, classe.getNome() + " é interface ");
            }

            classe.addMétodo(metodo);
            logger.log(Level.INFO, "adicionado método: {0}", metodo.getAssinatura());

            /**
             *
             * if (classePai != null) {
             *
             * if (classePai.temMétodo(metodo)) { logger.log(Level.WARNING,
             * "CLASSE PAI TEM O MESMO MÉTODO!"); } }
             */
        }

        int contador = 0;
        // construtores declarados
        for (Constructor<?> constructor : predefinida.getDeclaredConstructors()) {
            List<String> mods = modifiersFromInt(constructor.getModifiers());

            Construtor c = new Construtor(mods.get(0), classe.getNome());
            Parameter[] params = constructor.getParameters();

            for (Parameter p : params) {
                c.addParametro(new Parametro(p.getType().getSimpleName(), p.getName()));
            }

            for (int h = 1; h < mods.size(); h++) {
                c.addModNacesso(mods.get(h));
            }
            contador++;

            if (contador == 1) {
                logger.log(Level.INFO, "primeiro construtor encontrado: {0}", c.getAssinatura());
            }

            classe.addConstrutor(c);
        }

        addClasse(classe);

        return classe;
    }

    // adicionar uma metaclasse criada pelo usuário
    public static Classe addClasse(Classe classe) {
        logger.log(Level.INFO, "adicionado metaclasse " + classe.getNome());
        return classes.put(classe.getNomeCompleto(), classe);
    }

    private static List<String> modifiersFromInt(int modifiers) {
        List<String> modNames = Arrays.asList(Modifier.toString(modifiers).split("\\s"));
        return modNames;
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

    // acessar uma classe importada
    public Classe getClasse(String nome) {
        Classe cl = classes.get(nomesCompletos.get(nome));
        logger.log(Level.INFO, "convertendo nome simples em nome completo: " + nome + "=" + nomesCompletos.get(nome));
        logger.log(Level.INFO, "classe reconhecida: " + cl.getNomeCompleto());
        return cl;
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

    /**
     * Adiciona um modificador de não-acesso
     *
     * @param mod
     * @return
     */
    public boolean addModNAcesso(String mod) {
        return this.modsNAcesso.add(mod);
    }

    public boolean removeModNAcesso(String mod) {
        return this.modsNAcesso.remove(mod);
    }

    public List<String> getModNAcesso() {

        return this.modsNAcesso;
    }

}
