/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.api.classes;

import appbuilder.api.vars.VarBuilder;

/**
 *
 * @author psilva
 */
public class ForEachBuilder {

    private String corpo= "" ;
    private VarBuilder elemento;
    private VarBuilder lista;

    public ForEachBuilder(VarBuilder elemento, VarBuilder lista) {
        this.elemento = elemento;
        this.lista = lista;
    }

    public String toString() {
        String codigo = "";

        codigo += "for(" + elemento.getTipo() + " " + elemento.getName();
        codigo += " : " + lista.getReferencia() + "){\n";

        codigo += corpo;
        codigo += "}";
        return codigo;
    }

    public String getCorpo() {
        return corpo;
    }

    public void setCorpo(String corpo) {
        this.corpo = corpo;
    }

    public VarBuilder getElemento() {
        return elemento;
    }

    public void setElemento(VarBuilder elemento) {
        this.elemento = elemento;
    }

    public VarBuilder getLista() {
        return lista;
    }

    public void setLista(VarBuilder lista) {
        this.lista = lista;
    }
    
    

}
