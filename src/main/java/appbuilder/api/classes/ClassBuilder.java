package appbuilder.api.classes;

import appbuilder.api.classes.exceptions.ExceptionTreatmentBuilder;

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

import appbuilder.api.methods.MethodBuilder;
import appbuilder.api.methods.ParameterBuilder;
import appbuilder.api.packages.ImportBuilder;
import appbuilder.api.packages.PackageBuilder;
import appbuilder.api.vars.AttributeBuilder;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import appbuilder.api.vars.ObjectBuilder;
import appbuilder.index.Testes;

import java.lang.reflect.Field;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.SimpleFormatter;

/**
 * @author Pedro Henrique Braga da Silva
 */
public class ClassBuilder {

    protected PackageBuilder packageBuilder;
    protected String accessModifier;// modificador de acesso ,Ex: public, private
    protected String name;
    private boolean isInterface;
    private static final Logger logger = Logger.getLogger(Level.INFO.getName());

    private List<AttributeBuilder> attributes = new ArrayList<AttributeBuilder>();
    private List<MethodBuilder> methods = new ArrayList<MethodBuilder>();
    private List<ImportBuilder> imports = new ArrayList<ImportBuilder>();
    private List<InterfaceBuilder> interfaces = new ArrayList<InterfaceBuilder>();

    // modificadores de não-acesso
    private List<String> nonAccessModifiers = new ArrayList<>();

    // construtor principal
    private ConstructorBuilder principalConstructorBuilder;
    // método principal
    private MethodBuilder mainMethod;
    private static final Handler handler = new ConsoleHandler();

    /**
     * classes prontas, usando api de reflection /*nome totalmente qualificado
     * com a classe pronta
     */
    private static Map<String, ClassBuilder> classes = new HashMap<>();

    /**
     * Associa nome da classe com o nome totalmente qualificado Uma espécie de
     * apelido, Ex: String -> java.lang.String
     */
    private Map<String, String> fullNames = new HashMap<>();

    /**
     * A superclasse
     */
    private ClassBuilder superClassBuilder;

    // serve para indicar se está executando o trecho estático de adição de classes
    private static boolean STATIC_CONTEXT = false;

    // deixo classes prontas, já predefinidas
    static {

        handler.setFormatter(new SimpleFormatter());
        logger.addHandler(handler);
        logger.setFilter(new Testes());
        logger.log(Level.INFO, "começo: adicionando classes");
        STATIC_CONTEXT = true;
        try {
            //suporte para classe Exceção
            addClassBuilder("Object", "lang", "java");
            addClassBuilder("Exception", "lang", "java");
            addClassBuilder("RuntimeException", "lang", "java");
            addClassBuilder("List", "util", "java");
            addClassBuilder("ArrayList", "util", "java");
            addClassBuilder("SQLException", "sql", "java");
            addClassBuilder("NullPointerException", "lang", "java");
            addClassBuilder("CloneNotSupportedException", "lang", "java");
            addClassBuilder("Connection", "sql", "java");
            addClassBuilder("DriverManager", "sql", "java");
            addClassBuilder("PreparedStatement", "sql", "java");
            addClassBuilder("Statement", "sql", "java");
            addClassBuilder("ResultSet", "sql", "java");
            addClassBuilder("Date", "sql", "java");
            addClassBuilder("Calendar", "util", "java");
            addClassBuilder("Runnable", "lang", "java");
            addClassBuilder("Thread", "lang", "java");
            addClassBuilder("EventQueue", "awt", "java");

        } catch (ClassNotFoundException ex) {
            logger.log(Level.SEVERE, ex.getMessage() + "");
            System.out.println("Não foi possível carregar as classes");
        }

        logger.log(Level.INFO, "fim: adicionando classes");

        STATIC_CONTEXT = false;
    }

    private boolean _isArray = false;
    private boolean hasGenerics = false;

    public ClassBuilder(String name) {
        this.name = name;
        this.accessModifier = "public";
        this.packageBuilder = new PackageBuilder(name.toLowerCase());

        if (!STATIC_CONTEXT) {
            addImport("java.lang.Object");
            addImport("java.lang.Runnable");
            addImport("java.lang.Exception");
            addImport("java.lang.RuntimeException");
            addImport("java.lang.Thread");
        }

    }

    public ClassBuilder(String name, String pacote) {
        this(name);
        this.packageBuilder = new PackageBuilder(pacote);
    }

    public ClassBuilder(String name, String pacote, String caminho) {
        this(name);
        this.packageBuilder = new PackageBuilder(pacote, caminho);
    }

    public static Handler getHandler() {
        return handler;
    }

    public void setHasGenerics(boolean v) {
        this.hasGenerics = v;
    }

    public boolean isHasGenerics() {
        return this.hasGenerics;
    }

    public String upperCase(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

    /**
     * Define a superclasse com base nos pacotes ou classes já importadas Não
     * pode ter uma super classe que não esteja dentro das importações
     *
     * @param className
     * @param superClasse
     */
    public void setSuperClass(String className) {
        ClassBuilder classBuilder = getClasse(className);

        logger.log(Level.INFO, "Superclasse definida: " + classBuilder.getName());
        try {

            setSuperClass(classBuilder);

        } catch (NullPointerException ex) {
            ex.printStackTrace();
            ;
            logger.log(Level.INFO, "superclasse nula!");
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setSuperClass(ClassBuilder superClassBuilder) throws CloneNotSupportedException {
        this.superClassBuilder = superClassBuilder;

        List<String> ownAttributes = new ArrayList<>();

        for (AttributeBuilder atr : this.getAttributes()) {
            ownAttributes.add(atr.getName());
        }

        for (AttributeBuilder atr : superClassBuilder.getAttributes()) {
            if (!ownAttributes.contains(atr.getName())) {
                addAttribute(atr);
            }
        }

        List<String> ownMethods = new ArrayList<>();

        for (MethodBuilder met : this.getMethods()) {
            ownMethods.add(met.getName());
        }

        for (MethodBuilder met : superClassBuilder.getMethods()) {
            if (!this.findMethod(met)) {
                logger.log(Level.WARNING, getName() + ": Adicionando método : " + met.getSignature());
                if (met instanceof ConstructorBuilder) {
                    logger.log(Level.INFO, getName() + ": construtor encontrado: " + met.getSignature());
                    ConstructorBuilder constructorBuilder = (ConstructorBuilder) met.clone();
                    constructorBuilder.setName(getName());

                    addConstrutor(constructorBuilder);

                } else {
                    addMethod(met);
                }
            }
        }

    }

    public ClassBuilder getSuperClasse() {
        return this.superClassBuilder;
    }

    public void setVetor(boolean b) {
        this._isArray = b;
        setSuperClass("Object");
    }

    public boolean isArray() {
        return this._isArray;
    }

    /**
     * Quando true, ele adiciona automaticamente o modificador interface Quando
     * false, ele remove automaticamenteo o modificador interface
     *
     * @param b
     */
    public void setInterface(boolean b) {
        this.isInterface = b;

        if (b) {
            addNonAccessMod("interface");
        } else {
            removeNonAccessMod("interface");
        }
    }

    public boolean éUmaInterface() {
        return this.isInterface;
    }

    public boolean addInterface(InterfaceBuilder in) {

        for (MethodBuilder met : in.getMethods()) {
            // remove aquele flag de ser método de uma interface
            // e implementa
            try {
                MethodBuilder metodo = (MethodBuilder) met.clone();
                metodo.setBelongsInterface(false);
                metodo.removeModNacesso("abstract");
                addMethod(metodo);
            } catch (CloneNotSupportedException c) {
                c.printStackTrace();
                ;
            }
        }

        return this.interfaces.add(in);
    }

    public boolean removeInterface(InterfaceBuilder in) {
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
            mainMethod = new MethodBuilder("public", "static", "void", "main");
            mainMethod.addParameters("String[]", "args");
            return addMethod(mainMethod);
        } else {
            boolean rt = methods.remove(mainMethod);
            mainMethod = null;
            return rt;

        }
    }

    // referente quanto na forma de objeto
    public ObjectBuilder getInstancia(String... argumentos) {
        // defino o tipo do objeto, que é instância da classe
        ObjectBuilder obj = new ObjectBuilder(this);
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
    public static ObjectBuilder get(String nome, String... argumentos) {
        return classes.get(nome).getInstancia(argumentos);
    }

    public PackageBuilder getPacote() {
        return this.packageBuilder;
    }

    public void setPacote(PackageBuilder packageBuilder) {
        this.packageBuilder = packageBuilder;
    }

    public void setPacote(String pacote) {
        this.packageBuilder = new PackageBuilder(pacote);
    }

    public ConstructorBuilder getPrincipalConstructor() {
        return principalConstructorBuilder;
    }

    public void setPrincipalConstructor(ConstructorBuilder principalConstructorBuilder) {
        this.principalConstructorBuilder = principalConstructorBuilder;
    }

    public String getNomeCompleto() {
        return packageBuilder.getCaminho() + "." + getName();
    }

    public MethodBuilder getMain() {
        return this.mainMethod;
    }

    @Override
    public String toString() {
        String codigo = "";

        /**
         * PACOTE
         */
        codigo += this.packageBuilder + ";\n\n";
        /**
         * IMPORTAÇÕES
         */
        for (ImportBuilder importação : imports) {
            // não exibir, por padrão já vem importado
            if (!importação.getCaminho().equals("java.lang")) {
                codigo += importação + ";\n\n";
            }
        }

        /**
         * MODIFICADOR DE ACESSO
         */
        codigo += this.accessModifier;

        /**
         * INTERFACE
         */
        // indica se é uma interface ou não
        String tipo = isInterface == true ? /* se verdadeiro, então é interface */ "interface"
                : /* caso contrário, é uma classe */ "class";

        /**
         * MODIFICADORES DE NÃO-ACESSO
         */
        for (String mod : nonAccessModifiers) {
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
        codigo += " " + tipo + " " + this.name;
        logger.log(Level.INFO, "exibir nome da superclasse");
        // coloca o extends caso tenha uma superclasse
        /**
         * SUPERCLASSE
         */
        if (superClassBuilder != null) {
            logger.log(Level.INFO, "exibindo superclasse superclasse existe");
            if (!superClassBuilder.getName().equals("Object")) {
                codigo += " extends " + superClassBuilder.getName();
            }
        }

        logger.log(Level.INFO, "Verificando se implementa alguma interface");
        /**
         * INTERFACES
         */
        if (interfaces.size() > 0) {
            codigo += " implements ";

            int contador = 1;
            for (InterfaceBuilder in : interfaces) {
                if (contador % 2 == 0) {
                    contador = 1;
                    codigo += ", ";
                }
                codigo += in.getName();
                contador++;
            }

        }

        codigo += " { \n\n";

        /**
         * ATRIBUTOS
         */
        for (AttributeBuilder var : attributes) {

            if (superClassBuilder != null) {

                if (!superClassBuilder.hasAttribute(var.getName())) {
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
        for (MethodBuilder met : methods) {

            // verifica se a superclasse não tem esse método
            // se tiver, não precisa exibir
            if (superClassBuilder != null) {

                if (!superClassBuilder.findMethod(met)) {
                    codigo += met;
                    logger.log(Level.INFO, getName() + ": superclasse não tem " + met.getSignature());
                } else {
                    logger.log(Level.INFO, getName() + ": superclasse tem " + met.getSignature());
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
    public boolean addAttribute(AttributeBuilder atr) {
        atr.setClasse(this);
        return this.attributes.add(atr);
    }

    public boolean addConstrutor(ConstructorBuilder constructorBuilder) {
        return this.methods.add(constructorBuilder);
    }

    public boolean removeConstrutor(ConstructorBuilder constructorBuilder) {
        return this.methods.remove(constructorBuilder);
    }

    // leva em consideração que pode ter vários parâmetros de tipos diferentes e o
    // modificador pode ser genérico também,
    // adicionar construtor genérico
    public ConstructorBuilder addConstrutor(String modAcesso, ParameterBuilder... params) {
        ConstructorBuilder constructorBuilder = new ConstructorBuilder(modAcesso, name);
        List<ParameterBuilder> lista = new ArrayList<>();

        for (ParameterBuilder param : params) {
            lista.add(param);
        }

        constructorBuilder.setParameters(lista);

        if (addConstrutor(constructorBuilder)) {
            return constructorBuilder;
        } else {
            return null;
        }
    }

    // especializa o método acima, para adicionar construtores públicos para
    // reaproveitamento de código
    public ConstructorBuilder addConstrutorPúblico(ParameterBuilder... params) {
        return addConstrutor("public", params);
    }

    // construtor privado
    public ConstructorBuilder addConstrutorPrivado(ParameterBuilder... params) {
        return addConstrutor("private", params);
    }

    public boolean addMethod(MethodBuilder metodo) {
        return this.methods.add(metodo);
    }

    public boolean removeMétodo(MethodBuilder metodo) {
        return this.methods.remove(metodo);
    }

    public boolean addGetter(AttributeBuilder atributo) {
        MethodBuilder getter = new MethodBuilder("public", atributo.getTipo(), "get" + upperCase(atributo.getName()));
        getter.setReturn(atributo.getReferencia());

        return addMethod(getter);
    }

    public boolean addSetter(AttributeBuilder atributo) {
        MethodBuilder setter = new MethodBuilder("public", "void", "set" + upperCase(atributo.getName()));
        setter.addParameters(atributo.getTipo(), atributo.getName());
        setter.setBody(atributo.getInicialização(atributo.getName()));

        return addMethod(setter);
    }

    // adicionar construtores associados a atributos

    /**
     * Pegar um getter associado a um atributo
     *
     * @param atributo nome do atributo já declarado no modelo
     * @return
     */
    public MethodBuilder getGetter(String atributo) {
        MethodBuilder methodBuilder = null;
        String camelCase = "get" + upperCase(atributo);
        methodBuilder = getMethodBuilder(camelCase);

        return methodBuilder;
    }

    // retorna o setter com base no nome do atributo associado
    public MethodBuilder getSetter(String atributo) {
        MethodBuilder methodBuilder = null;
        String camelCase = "set" + upperCase(atributo);
        methodBuilder = getMethodBuilder(camelCase);

        return methodBuilder;
    }

    public String getAccessModifier() {
        return accessModifier;
    }

    public void setAccessModifier(String accessModifier) {
        this.accessModifier = accessModifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AttributeBuilder> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributeBuilder> attributes) {
        this.attributes = attributes;
    }

    public List<MethodBuilder> getMethods() {
        return methods;
    }

    public void setMethods(List<MethodBuilder> m) {
        this.methods = m;
    }

    public List<ImportBuilder> getImports() {
        return imports;
    }

    public void setImports(List<ImportBuilder> imports) {
        this.imports = imports;
    }

    public void setPrincipalConstructor(String _public, String name) {
        setPrincipalConstructor(new ConstructorBuilder(_public, name));
    }

    /**
     * Retorna objeto do atributo com base no nome passado
     *
     * @param nome nome do atributo
     * @return Atributo com o nome especificado
     */
    public AttributeBuilder getAttribute(String nome) {

        AttributeBuilder attr = null;

        for (AttributeBuilder atr : getAttributes()) {
            if (atr.getName().equals(nome)) {
                attr = atr;
            }
        }

        return attr;
    }

    public boolean hasAttribute(String name) {
        return hasAttribute(getAttribute(name));
    }

    public boolean hasAttribute(AttributeBuilder attr) {
        return this.attributes.contains(attr);
    }

    // retorna o método com base no nome
    public MethodBuilder getMethodBuilder(String nome) {
        MethodBuilder methodBuilder = null;

        for (MethodBuilder met : getMethods()) {
            if (met.getName().equals(nome)) {
                methodBuilder = met;
            }
        }

        if (superClassBuilder != null) {
            for (MethodBuilder met : superClassBuilder.getMethods()) {
                if (met.getName().equals(nome)) {
                    methodBuilder = met;
                }
            }

        }
        return methodBuilder;
    }

    public boolean hasMethod(MethodBuilder method) {
        return this.methods.contains(method);
    }

    public boolean hasMethod(String método) {
        boolean retorno = false;

        for (MethodBuilder m : this.methods) {
            if (m.getName().equals(método)) {
                return true;
            }
        }

        return retorno;
    }

    public boolean findMethod(MethodBuilder method) {
        boolean ret = false;
        for (MethodBuilder met : getMethods()) {
            //compare name
            String nome = met.getName();
            //comparar os parâmetros e quantity de parâmetros
            List<ParameterBuilder> params = met.getParameters();
            int quantity = params.size();//quantity de parâmetros
            int countParams = 0; //quantity de parâmetros iguais

            if (quantity == method.getParameters().size()) {
                for (int j = 0; j < quantity; j++) {
                    ParameterBuilder p1 = params.get(j);
                    ParameterBuilder p2 = method.getParameter(j);

                    if (p1.getTipo().equals(p2.getTipo())) {
                        countParams++;
                    }
                }
            }

            //para ser igual deve ter: o nome, parâmetros e tipo de retorno igual
            if (method.getName().equals(nome)
                    && method.getParameters().size() == quantity
                    && countParams == quantity
                    && method.getReturnType().equals(met.getReturnType())) {
                return true;
            }

        }

        return ret;
    }

    // cria uma classe a partir de uma já predefinida, classes prontas da linguagem
    // Java
    public static ClassBuilder addClassBuilder(String nome, String pacote, String caminho) throws ClassNotFoundException {
        ClassBuilder classBuilder = null;
        ClassBuilder classBuilderPai = null;
        if (nome.endsWith("Exception") && !nome.equals("Exception")) {
            logger.log(Level.INFO, nome + ": é uma classe de exceção ");
            classBuilder = new ExceptionBuilder(nome, pacote, caminho);
        } else {
            classBuilder = new ClassBuilder(nome, pacote, caminho);
            ;
        }

        logger.log(Level.INFO, nome + ": adicionando classe " + classBuilder.getNomeCompleto());

        logger.log(Level.INFO, classBuilder.getName() + ": sendo pesquisada");
        // caso contrário, continua
        Class<?> predefinida = Class.forName(classBuilder.getNomeCompleto());
        List<String> modifiers = modifiersFromInt(predefinida.getModifiers());
        Class<?> superClasse = predefinida.getSuperclass();

        if (predefinida.isInterface()) {
            classBuilder = new InterfaceBuilder(nome, pacote, caminho);
        }

        if (superClasse != null) {
            // defino a superclasse para minha metaclasse
            // infelizmente teve recursividade
            String nomePacoteCompleto = superClasse.getPackage().getName();
            String nomeClasseCompleto = superClasse.getName();
            String[] nomePacoteDividido = nomePacoteCompleto.split("\\.");
            String[] nomeClasseDividido = nomeClasseCompleto.split("\\.");

            logger.log(Level.INFO, classBuilder.getNomeCompleto() + " tem superclasse " + superClasse.getName());
            /**
             * superClasse.getName() retorna o nome totalmente qualificado:
             * java.lang.String superClasse.getPackage().getName() retorna o
             * nome totalmente qualificado: java.lang O nome da classe é a
             * última palavra, então eu precisei dividir por "." E precisei
             * fazer recursividade
             */
            try {
                classBuilderPai = ClassBuilder.addClassBuilder(
                        nomeClasseDividido[nomeClasseDividido.length - 1],
                        nomePacoteDividido[nomePacoteDividido.length - 1], superClasse.getPackage().getName()
                                .replace("." + nomePacoteDividido[nomePacoteDividido.length - 1], ""));

                classBuilder.setSuperClass(classBuilderPai);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }

        // coloca o modificador de acesso
        classBuilder.setAccessModifier(modifiers.get(0));

        // modificadores de não-acesso
        List<String> mnacesso = new ArrayList<>();

        for (int k = 1; k < modifiers.size(); k++) {
            String mod = modifiers.get(k);
            mnacesso.add(mod);

        }

        // coloca os modificadores de não-acesso
        classBuilder.setNonAccessMod(mnacesso);

        if (mnacesso.contains("interface")) {
            classBuilder.setInterface(true);
            classBuilder.removeConstrutor(classBuilder.getPrincipalConstructor());
        }

        logger.log(Level.INFO, "adicionando métodos");
        // pega os métodos declarados
        for (Method method : predefinida.getDeclaredMethods()) {
            String name = method.getName();
            String retType = method.getReturnType().getSimpleName();
            Parameter[] params = method.getParameters();

            List<String> mods = modifiersFromInt(method.getModifiers());
            // por padrão o modificador de acesso é public
            MethodBuilder _metodo = new MethodBuilder("public", retType, name);
            logger.log(Level.INFO, "adicionando método: " + _metodo.getSignature());

            // em teoria o primeiro modificador é de acesso
            List<String> modsnacesso = new ArrayList<>();

            // começando a partir do segundo modificador
            for (int j = 1; j < mods.size(); j++) {
                modsnacesso.add(mods.get(j));
            }

            // coloca todos os mdificadores de não-acesso
            _metodo.setNonAccessMod(modsnacesso);

            for (Parameter param : params) {
                Class<?> cl = param.getType();

                _metodo.addParameters(cl.getSimpleName(), param.getName());
            }

            if (classBuilder.isInterface) {
                _metodo.setBelongsInterface(true);
                logger.log(Level.INFO, classBuilder.getName() + " é interface ");
            }

            classBuilder.addMethod(_metodo);
            logger.log(Level.INFO, classBuilder.getName() + ": adicionado método: {0}", _metodo.getSignature());

            /**
             *
             * if (classePai != null) {
             *
             * if (classePai.temMétodo(metodo)) { logger.log(Level.WARNING,
             * "CLASSE PAI TEM O MESMO MÉTODO!"); } }
             */
        }

        //atributos públicos
        for (Field atributo : predefinida.getDeclaredFields()) {
            String tipo = atributo.getType().getName();
            List<String> mods = modifiersFromInt(atributo.getModifiers());
            String modAcesso = mods.get(0);
            AttributeBuilder atr = new AttributeBuilder(modAcesso, tipo, atributo.getName());
            for (int h = 1; h < mods.size(); h++) {
                atr.addModificador(mods.get(h));
            }

            classBuilder.addAttribute(atr);
        }

        int contador = 0;
        // construtores declarados

        for (java.lang.reflect.Constructor<?> constructor : predefinida.getDeclaredConstructors()) {

            List<String> mods = modifiersFromInt(constructor.getModifiers());

            ConstructorBuilder c = new ConstructorBuilder(mods.get(0), classBuilder.getName());
            Parameter[] params = constructor.getParameters();

            for (Parameter p : params) {
                c.addParameters(new ParameterBuilder(p.getType().getSimpleName(), p.getName()));
            }

            for (int h = 1; h < mods.size(); h++) {
                c.addModNacesso(mods.get(h));
            }
            contador++;

            if (contador == 1) {
                logger.log(Level.INFO, "primeiro construtor encontrado: {0}", c.getSignature());
            }

            if (classBuilder.findMethod(c)) {
                continue;
            }

            classBuilder.addConstrutor(c);
        }

        addClassBuilder(classBuilder);

        return classBuilder;
    }

    // adicionar uma metaclasse criada pelo usuário
    public static ClassBuilder addClassBuilder(ClassBuilder classBuilder) {
        logger.log(Level.INFO, "adicionado metaclasse " + classBuilder.getName());

        if (classes.get(classBuilder.getNomeCompleto()) != null) {
            return classes.replace(classBuilder.getNomeCompleto(), classBuilder);
        }

        classes.put(classBuilder.getNomeCompleto(), classBuilder);
        return classBuilder;
    }

    private static List<String> modifiersFromInt(int modifiers) {
        return Arrays.asList(Modifier.toString(modifiers).split("\\s"));
    }

    /**
     * Adiciona o nome da classe com o nome totalmente qualificado dela
     *
     * @param name
     * @return o caminhoCompleto
     */
    public String addFullName(String name, String fullName) {
        if (this.fullNames.containsKey(name)) {
            return "";
        }

        return this.fullNames.put(name, fullName);
    }

    public String getNomeCompleto(String name) {
        return this.fullNames.get(name);
    }

    public static ClassBuilder getStaticCall(String name) {
        return classes.get(name);
    }

    // acessar uma classe importada
    public ClassBuilder getClasse(String name) {
        ClassBuilder cl = null;
        boolean generics = false;

        if (name.contains("<") && name.contains(">")) {
            name = name.split("<")[0].trim();
            generics = true;
        }

        if (name.equals(getName()) || name.equals(getNomeCompleto())) {
            return this;
        }

        if (name.contains(".")) {
            cl = classes.get(name);
        } else {
            cl = classes.get(fullNames.get(name));
        }

        if (cl != null) {
            logger.log(Level.INFO, "convertendo nome simples em nome completo: " + name + "=" + fullNames.get(name));
            logger.log(Level.INFO, "classe reconhecida: " + cl.getNomeCompleto());
        }

        if (generics) {
            cl.setHasGenerics(true);
        }

        return cl;
    }

    public ExceptionBuilder getExceção(String nome) {
        return (ExceptionBuilder) getClasse(nome);
    }

    public String callStatic(String método, String... args) {
        return getMethodBuilder(método).getStaticCall(getName(), args);
    }

    public boolean addImport(ClassBuilder classBuilder) {
        addFullName(classBuilder.getName(), classBuilder.getNomeCompleto());
        return this.imports.add(new ImportBuilder(classBuilder.getName(), classBuilder.getPacote().getCaminho()));
    }

    public boolean addImport(String fullPackage) {
        return addImport(ClassBuilder.getStaticCall(fullPackage));
    }

    public String getImport(String classe) {
        String path = "";
        for (ImportBuilder _import : imports) {
            if (_import.getClasse().equals(classe)) {

                path = _import.getCaminho();
            }
        }

        return path;
    }

    public void setNonAccessMod(List<String> modsnacesso) {
        this.nonAccessModifiers = modsnacesso;
    }

    /**
     * Adiciona um modificador de não-acesso
     *
     * @param mod
     * @return
     */
    public boolean addNonAccessMod(String mod) {
        return this.nonAccessModifiers.add(mod);
    }

    public boolean removeNonAccessMod(String mod) {
        return this.nonAccessModifiers.remove(mod);
    }

    public List<String> getNonAccessMod() {

        return this.nonAccessModifiers;
    }

    public List<ConstructorBuilder> getConstructors() {
        List<ConstructorBuilder> list = new ArrayList<>();
        for (MethodBuilder m : getMethods()) {
            if (m instanceof ConstructorBuilder) {
                list.add((ConstructorBuilder) m);
            }
        }
        return list;
    }

    public ExceptionTreatmentBuilder tratarExceção(String... exceções) {
        if (exceções.length == 0) {
            return null;
        }

        ExceptionTreatmentBuilder tratamento = ExceptionBuilder.tratar(this, exceções);

        return tratamento;
    }

    public String lançarExceção(String nome, String... args) {
        return ExceptionBuilder.lançar(this, nome, args);
    }
}
