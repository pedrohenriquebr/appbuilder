/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.api.classes;

import appbuilder.api.methods.MethodBuilder;
import appbuilder.api.vars.AttributeBuilder;

/**
 *
 * @author Pedro Henrique Braga da Silva
 */
/**
 * Classe responsável por construir outra classe Modelo dentro do MVC
 *
 * @author psilva
 */
public class ModelBuilder extends ClassBuilder {

    private AttributeBuilder chave;
    
    public ModelBuilder(String nome) {
        super(nome);
    }

    public ModelBuilder(String nome, String pacote) {
        super(nome, pacote);
    }

    public ModelBuilder(String nome, String pacote, String caminho) {
        super(nome, pacote, caminho);
    }

    public void setKey(String nome) {
        chave = getAttribute(nome);
    }

    public AttributeBuilder getChave() {
        return this.chave;
    }

    /**
     * Método para criar um atributo, que estará associado com seus devidos
     * getter e setter
     *
     * @param tipo String, int, etc ...
     * @param nome
     * @return
     */
    public boolean addAtributo(String tipo, String nome) {
        if (nome.isEmpty()) {
            return false;
        }

        AttributeBuilder atr = new AttributeBuilder("private", tipo, nome);
        boolean b = super.addAttribute(atr);

        if (b) {

            //adicionar getter e setter (JavaBeans)
            boolean c = super.addGetter(atr);
            boolean d = super.addSetter(atr);

            return !(!c || !d);
        } else {
            return false;
        }
    }

    /**
     * Adiciona um atributo com seus getter e setter do tipo int
     *
     * @param nome
     * @return
     */
    public boolean addInteiro(String nome) {
        return addAtributo("int", nome);
    }

    /**
     * Adiciona um atributo com seus getter e setter do tipo String
     *
     * @param nome
     * @return
     */
    public boolean addString(String nome) {
        return addAtributo("String", nome);
    }

    /**
     * Adiciona um atributo com seus getter e setter do tipo float
     *
     * @param nome
     * @return
     */
    public boolean addFloat(String nome) {
        return addAtributo("float", nome);
    }

    /**
     * Adiciona um atributo com seus getter e setter do tipo double
     *
     * @param nome
     * @return
     */
    public boolean addDouble(String nome) {
        return addAtributo("double", nome);
    }

    /**
     * Adiciona vários atributos com seus getters e setters do tipo int
     *
     * @param nomes
     * @return
     */
    public int addInteiros(String... nomes) {
        //conta quantos atributos do tipo inteiro foram adicionados com sucesso
        int sucesso = 0;

        for (String nome : nomes) {
            boolean resultado = addInteiro(nome);
            if (resultado) {
                sucesso++;
            }

        }

        return sucesso;
    }

    /**
     * Adiciona vários atributos com seus getters e setters do tipo String
     *
     * @param nomes
     * @return quantos atributos foram adicionados com sucesso
     */
    public int addStrings(String... nomes) {
        //conta quantos atributos do tipo inteiro foram adicionados com sucesso
        int sucesso = 0;

        for (String nome : nomes) {
            boolean resultado = addString(nome);
            if (resultado) {
                sucesso++;
            }

        }

        return sucesso;
    }

    /**
     * Adiciona vários atributos com seus getters e setters do tipo float
     *
     * @param nomes
     * @return
     */
    public int addFloats(String... nomes) {
        //conta quantos atributos do tipo inteiro foram adicionados com sucesso
        int sucesso = 0;

        for (String nome : nomes) {
            boolean resultado = addFloat(nome);
            if (resultado) {
                sucesso++;
            }

        }

        return sucesso;
    }

    /**
     * Adiciona vários atributos com seus getters e setters do tipo double
     *
     * @param nomes
     * @return
     */
    public int addDoubles(String... nomes) {
        //conta quantos atributos do tipo inteiro foram adicionados com sucesso
        int sucesso = 0;

        for (String nome : nomes) {
            boolean resultado = addDouble(nome);
            if (resultado) {
                sucesso++;
            }

        }

        return sucesso;
    }

    /**
     * Adiciona um construtor para os atributos passados
     *
     * @param atributos lista de atributos
     * @return
     */
    public boolean addConstrutorPara(String... atributos) {
        ConstructorBuilder constructorBuilder = new ConstructorBuilder("public", getName());
        /**
         * Para cada nome de atributo passado, eu pego o objeto Atributo
         * associado a ele. Em seguida, pego um setter associado ao atributo e
         * coloco dentro o corpo do construtor
         */
        for (String atr : atributos) {
            AttributeBuilder at = getAttribute(atr);
            constructorBuilder.addParameters(at.getTipo(), at.getName());
            MethodBuilder setter = getSetter(atr);

            constructorBuilder.addCorpo(setter.getCall(atr));
        }

        return super.addConstrutor(constructorBuilder);
    }

    public static ModelBuilder addModelo(ModelBuilder modelBuilder) {
        return (ModelBuilder) ClassBuilder.addClassBuilder(modelBuilder);
    }

    public static ModelBuilder getModelo(String nome) {
        ClassBuilder c = ClassBuilder.getStaticCall(nome);

        if (c instanceof ModelBuilder) {
            return (ModelBuilder) c;
        } else {
            return null;
        }
    }
}
