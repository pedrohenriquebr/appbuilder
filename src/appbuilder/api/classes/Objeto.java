/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.api.classes;

import appbuilder.api.methods.Método;
import appbuilder.api.vars.Variavel;

/**
 *
 * @author psilva
 */
public class Objeto extends Variavel {

    private Classe classe;
    private String instancia;

    public Objeto(String tipo, String nome) {
        super(tipo, nome);
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public Classe getClasse() {
        return this.classe;
    }

    public String chamarMétodo(String nome, String... args) {
        return nome + "." + classe.getMétodo(nome).getChamada(args);
    }

    public String acessarAtributo(String nome) {
        return nome + "." + classe.getAtributo(nome).getReferencia();
    }

    public void setInstancia(String... args) {
        String codigo = "";
        codigo += "new " + classe.getNome() + "(";

        int contador = 1;
        for (String arg : args) {
            if (contador % 2 == 0) {
                codigo += ",";
            }

            codigo += arg;
        }

        codigo += ")";
        this.instancia = codigo;
    }

    public String getInstancia() {
        return this.instancia;
    }

    @Override
    public String toString() {
        return getInstancia();
    }

}
