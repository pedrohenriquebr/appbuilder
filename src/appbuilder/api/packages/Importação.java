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

    private Pacote caminho;
    private String classe;

    public Importação(String classe, String caminho) {
        this.caminho = new Pacote(caminho);
        this.classe = classe;
    }

    public String getCaminho() {
        return caminho.getCaminho();
    }

    public void setCaminho(String caminho) {
        if (caminho.contains(".")) {
            String[] pacotes = caminho.split("\\.");
            String pacote = pacotes[pacotes.length - 1];
            caminho = caminho.replace("." + pacote, "");
            this.caminho = new Pacote(pacote, caminho);
        } else {
            this.caminho = new Pacote(caminho);
        }

    }

    public String getClasse() {
        return this.classe;
    }

    public String toString() {
        return "import " + caminho.getCaminho() + "." + classe;
    }

}
