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
public class IfBuilder {

    private String expressão= "";
    private String corpo= "";

    public IfBuilder(String expressão) {
        this.expressão = expressão;
    }
    
    public void addCorpo(String corpo ){
        this.corpo += corpo;
    }

    public String toString() {
        String codigo = "";

        codigo += "if( " + expressão + " ){\n";
        codigo += "\t\t"+corpo;
        codigo += "\n\t}\n\n";

        return codigo;
    }

}
