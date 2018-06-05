/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.main;

import appbuilder.api.classes.Classe;
import appbuilder.api.classes.ConnectionFactory;
import appbuilder.api.classes.Modelo;
import appbuilder.api.database.BaseDeDados;
import appbuilder.api.database.MyConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Filter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 *
 * @author psilva
 */
public class Testes implements Filter {

    private static final Logger logger = Logger.getLogger(Classe.class.getName());

    public static void main(String[] args) throws SQLException {
        Modelo modelo = new Modelo("Pessoa", "main", "br.com.testes");
        modelo.addStrings("código", "nome", "telefone");
        modelo.addAtributo("Calendar", "nascimento");
        modelo.setChave("código");
        ConnectionFactory factory = new ConnectionFactory("factory", "br.com.testes");
        factory.setBaseDeDados("projeto");
        BaseDeDados database = new BaseDeDados(modelo, factory);
        database.buildAll();

    }

    @Override
    public boolean isLoggable(LogRecord lr) {
        if (lr == null) {
            return false;
        }

        return false;
    }

}
