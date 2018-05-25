/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.api.classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author psilva
 */
public class Exceção extends Classe {

    public Exceção(String nome) {
        super(nome);
        setSuperClasse(nome);
    }

}
