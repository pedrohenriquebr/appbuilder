/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.api.vars;

import appbuilder.api.classes.ClassBuilder;

/**
 * Define a classe Objeto como se fosse uma variável local
 *
 * @author psilva
 */
public class ObjectBuilder {

    private ClassBuilder classBuilder;
    private String instancia;

    public ObjectBuilder(ClassBuilder classBuilder) {
        setClasse(classBuilder);
        instancia = "";
    }

    public void setClasse(ClassBuilder classBuilder) {
        this.classBuilder = classBuilder;
    }

    public ClassBuilder getClasse() {
        return this.classBuilder;
    }

    /**
     * Chamar um método
     *
     * @param nome
     * @param args
     * @return
     */
    public String call(String nome, String... args) {

        if (classBuilder.getSuperClasse() != null) {
            if (!classBuilder.getSuperClasse().hasMethod(nome) && !classBuilder.hasMethod(nome)) {
                throw new RuntimeException("classe " + classBuilder.getName() + " não tem método " + nome);
            }
        } else {
            if (!classBuilder.hasMethod(nome)) {
                throw new RuntimeException("classe " + classBuilder.getName() + " não tem método " + nome);
            }
        }

        return "." + classBuilder.getMethodBuilder(nome).getCall(args);
    }

    /**
     * Acessar um método
     *
     * @param nome
     * @return
     */
    public String get(String nome) {
        return "." + classBuilder.getAttribute(nome).getName();
    }

    /**
     * Define os argumentos para o objeto
     *
     * @param args
     */
    public void setInstancia(String... args) {
        String codigo = "";

        if (classBuilder.isHasGenerics()) {
            codigo += "new " + classBuilder.getName() + "<>(";
        } else {
            codigo += "new " + classBuilder.getName() + "(";

        }

        int contador = 1;
        for (String arg : args) {
            if (contador % 2 == 0) {
                codigo += ",";
                contador = 1;
            }

            codigo += arg;
            contador++;
        }

        codigo += ")";

        instancia = codigo;
    }

    public String getInstancia() {
        return this.instancia;
    }

    public String getTipo() {
        return this.classBuilder.getName();
    }

    public static ObjectBuilder instancia(String nome, String... args) {
        return ClassBuilder.get(nome, args);
    }

    @Override
    public String toString() {
        return getInstancia();
    }

}
