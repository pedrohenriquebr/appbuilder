/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.api.classes;

import appbuilder.api.methods.Método;
import appbuilder.api.vars.Atributo;

/**
 *
 * @author Pedro Henrique Braga da Silva
 */
/**
 * Classe responsável por construir outra classe Modelo dentro do MVC
 *
 * @author psilva
 */
public class Modelo extends Classe {

    public Modelo(String nome) {
        super(nome);
        setConstrutorPrincipal(new Construtor("public",getNome()));
        addConstrutor(getConstrutorPrincipal());
    }

    public Modelo(String nome, String pacote) {
        super(nome, pacote);
    }

    public Modelo(String nome, String pacote, String caminho) {
        super(nome, pacote, caminho);
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

        Atributo atr = new Atributo("private", tipo, nome);
        boolean b = super.addAtributo(atr);

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
        Construtor construtor = new Construtor("public", getNome());
        /**
         * Para cada nome de atributo passado, eu pego o objeto Atributo
         * associado a ele. Em seguida, pego um setter associado ao atributo e
         * coloco dentro o corpo do construtor
         */
        for (String atr : atributos) {
            Atributo at = getAtributo(atr);
            construtor.addParametro(at.getTipo(), at.getNome());
            Método setter = getSetter(atr);

            construtor.addCorpo(setter.getChamada(atr));
        }

        return super.addConstrutor(construtor);
    }

    public static Modelo addModelo(Modelo modelo) {
        return (Modelo) Classe.addClasse(modelo);
    }

    public static Modelo getModelo(String nome) {
        Classe c = Classe.getClasseEstática(nome);

        if (c instanceof Modelo) {
            return (Modelo) c;
        } else {
            return null;
        }
    }
}
