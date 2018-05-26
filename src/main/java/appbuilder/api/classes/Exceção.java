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
public class Exceção extends Classe {

    public Exceção(String nome) {
        super(nome);
        addImportação(Classe.getClasseEstática("java.lang.Exception"));
        setSuperClasse("Exception");
    }

}
