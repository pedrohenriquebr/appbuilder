/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.util;

/**
 *
 * @author psilva
 */

/**
 * Classe responsável por construir outra classe Modelo dentro do MVC
 * @author psilva
 */
public class Modelo {
    
    private Classe classe;
    
    public Modelo(String nome){
        classe = new Classe(nome);
        classe.setConstrutorPrincipal("public",classe.getNome());
    }
    
}
