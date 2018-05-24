/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.api.classes;

import appbuilder.api.methods.Método;
import appbuilder.api.vars.Atributo;

/**
 *
 * @author psilva
 */
public class Interface extends Classe {

    public Interface(String nome) {
        super(nome);
        setInterface(true);
        removeConstrutor(this.getConstrutorPrincipal());
    }

    @Override
    public boolean addAtributo(Atributo atr) {
        return false;
    }

    @Override
    public boolean addMétodo(Método metodo) {
        metodo.setDeInterface(true);
        return super.addMétodo(metodo);
    }

}
