package appbuilder.api.packages;

import appbuilder.util.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Pedro Henrique Braga da Silva
 */
public class Importação {
    
    private String caminho;
    
    public Importação(String caminho ){
        this.caminho  = caminho;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }
    
    
    public String toString(){
        return "import "+caminho + ";";
    }
    
}
