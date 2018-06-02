/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.api.classes;

import appbuilder.api.methods.Método;

/**
 *
 * @author psilva
 */
public class While {
    private String condição= "";
    private String corpo= "";
    
    public While(String condição ){
        this.condição = condição;
    }

    public String getCondição() {
        return condição;
    }

    public void setCondição(String condição) {
        this.condição = condição;
    }
    
    public void addCorpo(String corpo ){
        this.corpo += Método.formatar(corpo);
    }

    public String getCorpo() {
        return corpo;
    }

    public void setCorpo(String corpo) {
        this.corpo = Método.formatar(corpo);
    }
    
    
    
    
    public String toString(){
        String codigo = "";
        
        codigo+="while("+condição+"){\n\n";
        codigo+=corpo;
        codigo+="\t}\n";
        
        return codigo;
    }
}
