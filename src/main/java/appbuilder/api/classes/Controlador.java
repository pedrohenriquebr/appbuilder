/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.api.classes;

import appbuilder.api.vars.Atributo;

/**
 *
 * @author psilva
 */
public class Controlador extends Classe{
    
    private Atributo dao;
    public Controlador(Dao dao, Modelo modelo) {
        super(modelo.getNome()+"Controller");
        
        
    }
    
}
