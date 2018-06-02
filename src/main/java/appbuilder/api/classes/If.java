/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.api.classes;

/**
 *
 * @author psilva
 */
public class If {

    private String expressão= "";
    private String corpo= "";

    public If(String expressão) {
        this.expressão = expressão;
    }

    public String toString() {
        String codigo = "";

        codigo += "\t\tif( " + expressão + " ){\n";
        codigo += corpo;
        codigo += "}\n\n";

        return codigo;
    }

}
