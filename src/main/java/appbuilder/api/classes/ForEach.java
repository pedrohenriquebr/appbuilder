/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.api.classes;

import appbuilder.api.vars.Variavel;

/**
 *
 * @author psilva
 */
public class ForEach {

    private String corpo= "" ;
    private Variavel elemento;
    private Variavel lista;

    public ForEach(Variavel elemento, Variavel lista) {
        this.elemento = elemento;
        this.lista = lista;
    }

    public String toString() {
        String codigo = "";

        codigo += "for(" + elemento.getTipo() + " " + elemento.getNome();
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

    public Variavel getElemento() {
        return elemento;
    }

    public void setElemento(Variavel elemento) {
        this.elemento = elemento;
    }

    public Variavel getLista() {
        return lista;
    }

    public void setLista(Variavel lista) {
        this.lista = lista;
    }
    
    

}
