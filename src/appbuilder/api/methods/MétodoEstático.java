/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.api.methods;

/**
 *
 * @author psilva
 */
public class MétodoEstático extends Método {

    private String classe;

    public MétodoEstático(String modAcesso, String tipoRetorno, String nome) {
        super(modAcesso, tipoRetorno, nome);
        addModNacesso("static");
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String callStatic(String classe, String... args) {
        return classe + "." + getChamada(args);
    }

}
