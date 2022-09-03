package appbuilder.api.packages;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Pedro Henrique Braga da Silva
 */
public class ImportBuilder {

    private PackageBuilder caminho;
    private String classe;

    public ImportBuilder(String classe, String caminho) {
        this.caminho = new PackageBuilder(caminho);
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
            this.caminho = new PackageBuilder(pacote, caminho);
        } else {
            this.caminho = new PackageBuilder(caminho);
        }

    }

    public String getClasse() {
        return this.classe;
    }

    public String toString() {
        return "import " + caminho.getCaminho() + "." + classe;
    }

}
