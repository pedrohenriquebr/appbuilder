/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.api.vars;

import appbuilder.api.classes.Classe;
import appbuilder.api.methods.*;
import appbuilder.api.vars.*;
import java.util.*;

/**
 * Define a classe Objeto como se fosse uma variável local
 *
 * @author psilva
 */
public class Objeto {

    private Classe classe;
    private String instancia;

    public Objeto(Classe classe) {
        setClasse(classe);
        instancia = "";
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public Classe getClasse() {
        return this.classe;
    }

    /**
     * Chamar um método
     *
     * @param nome
     * @param args
     * @return
     */
    public String call(String nome, String... args) {

        if (classe.getSuperClasse() != null) {
            if (!classe.getSuperClasse().temMétodo(nome) && !classe.temMétodo(nome)) {
                throw new RuntimeException("classe " + classe.getNome() + " não tem método " + nome);
            }
        } else {
            if (!classe.temMétodo(nome)) {
                throw new RuntimeException("classe " + classe.getNome() + " não tem método " + nome);
            }
        }

        return "." + classe.getMétodo(nome).getChamada(args);
    }

    /**
     * Acessar um método
     *
     * @param nome
     * @return
     */
    public String get(String nome) {
        return "." + classe.getAtributo(nome).getNome();
    }

    /**
     * Define os argumentos para o objeto
     *
     * @param args
     */
    public void setInstancia(String... args) {
        String codigo = "";

        if (classe.isUsaGenerics()) {
            codigo += "new " + classe.getNome() + "<>(";
        } else {
            codigo += "new " + classe.getNome() + "(";

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
        return this.classe.getNome();
    }

    public static Objeto instancia(String nome, String... args) {
        return Classe.get(nome, args);
    }

    @Override
    public String toString() {
        return getInstancia();
    }

}
