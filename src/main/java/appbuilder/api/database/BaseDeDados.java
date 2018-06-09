/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.api.database;

import appbuilder.api.classes.ConnectionFactory;
import appbuilder.api.classes.Modelo;
import appbuilder.api.vars.Atributo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe responsável por construir os comandos SQL de forma dinâmica, recebendo
 * classe Modelo e ConnectionFactory.
 *
 * @author pedro
 */
public class BaseDeDados {

    private Modelo modelo;
    private ConnectionFactory factory;

    private String insertQuery = "";
    private String deleteQuery = "";
    private List<String> selectQueries = new ArrayList<>();
    private Map<String, String> mapSelectQueries = new HashMap<>();
    private String updateQuery = "";
    private String selectAllQuery = "";

    private String createDataBaseQuery = "";
    private String createTableQuery = "";
    private String useDataBaseQuery = "";

    public BaseDeDados(Modelo modelo, ConnectionFactory factory) {
        this.modelo = modelo;
        this.factory = factory;

        if (this.modelo.getChave() == null) {
            throw new RuntimeException("modelo não tem uma chave definida !");
        }

        addUpateQuery();
        addDeleteQuery();
        addInsertQuery();
        addSelectAllQuery();

        addCreateDataBaseQuery();
        addCreateTableQuery();

        this.useDataBaseQuery = "USE " + this.factory.getBaseDeDados().toLowerCase();

    }

    public boolean buildAll() throws SQLException {
        Connection con = MyConnectionFactory.getConnection();

        assert con != null : "conexão jdbc nula !! ";
        boolean b1 = false;
        boolean b2 = false;
        boolean b3 = false;
        PreparedStatement stmt = con.prepareStatement(getCreateDataBaseQuery());
        b1 = stmt.executeUpdate() > 0;
        assert b1 : "não deu pra criar base de dados";
        PreparedStatement st = con.prepareStatement(getUseDataBaseQuery());
        b3 = st.executeUpdate() > 0;
        assert b1 : "não deu pra usar a base de dados !";

        PreparedStatement stmt2 = con.prepareStatement(getCreateTableQuery());
        b2 = stmt2.executeUpdate() > 0;
        assert b2 : "não deu pra criar a tabela ";

        return b1 && b2;
    }

    public String getUseDataBaseQuery() {
        return this.useDataBaseQuery;
    }

    private void addCreateTableQuery() {
        this.createTableQuery = "CREATE TABLE IF NOT EXISTS " + this.modelo.getNome().toLowerCase() + "(";

        int contador = 1;

        for (Atributo atributo : this.modelo.getAtributos()) {
            if (contador == 2) {
                this.createTableQuery += ", ";
                contador = 1;
            }

            String nome = atributo.getNome().toLowerCase();
            String tipo = "";
            if (atributo.getTipo().equals("String")) {
                tipo = "VARCHAR(255)";
            } else if (atributo.getTipo().equals("int")) {
                tipo = "INT";
            } else if (atributo.getTipo().equals("double")) {
                tipo = "DOUBLE";
            } else if (atributo.getTipo().equals("float")) {
                tipo = "FLOAT";
            } else if (atributo.getTipo().equals("Calendar")) {
                tipo = "DATE";
            } else {
                throw new RuntimeException("atributo " + nome + " com tipo desconhecido para criar query da tabela!");
            }

            this.createTableQuery += nome + " " + tipo + " NOT NULL";
            if (this.modelo.getChave() == atributo) {
                this.createTableQuery += " PRIMARY KEY";
            }
            contador++;
        }

        this.createTableQuery += ")";
    }

    public String getCreateTableQuery() {
        return this.createTableQuery;
    }

    private void addCreateDataBaseQuery() {
        this.createDataBaseQuery = "CREATE DATABASE IF NOT EXISTS ? CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci";
        this.createDataBaseQuery = this.createDataBaseQuery.replace("?", factory.getBaseDeDados().toLowerCase());
    }

    public String getCreateDataBaseQuery() {
        return this.createDataBaseQuery;
    }

    public boolean addSelectQuery(String nomeAtributo) {
        String selectQuery = "SELECT * FROM "
                + this.modelo.getNome().toLowerCase() + " WHERE " + nomeAtributo.trim().toLowerCase() + "=?";
        if (mapSelectQueries.containsKey(nomeAtributo)) {
            return false;
        }
        boolean b = selectQueries.add(selectQuery);
        mapSelectQueries.put(nomeAtributo.trim().toLowerCase(), selectQuery);
        return b;
    }

    public String getSelectQuery(String nomeAtributo) {
        return mapSelectQueries.get(nomeAtributo.trim().toLowerCase());
    }

    public String getSelectAllQuery() {
        return this.selectAllQuery;
    }

    private void addSelectAllQuery() {
        this.selectAllQuery = "SELECT * FROM "
                + this.modelo.getNome().toLowerCase();

    }

    private void addUpateQuery() {
        this.updateQuery = "UPDATE "
                + modelo.getNome().toLowerCase() + " SET ";
        int c = 1;
        for (Atributo atr : modelo.getAtributos()) {
            if (atr == this.modelo.getChave()) {
                continue;
            }

            if (c == 2) {
                this.updateQuery += ", ";
                c = 1;
            }

            this.updateQuery += atr.getNome().toLowerCase() + "=?";

            c++;
        }

        this.updateQuery += " WHERE " + this.modelo.getChave().getNome().toLowerCase() + "=?";
    }

    private void addDeleteQuery() {

        this.deleteQuery = "DELETE  FROM "
                + this.modelo.getNome().toLowerCase() + " WHERE "
                + this.modelo.getChave().getNome().toLowerCase() + "=?";
    }

    private void addInsertQuery() {
        this.insertQuery = "INSERT INTO "
                + this.modelo.getNome().toLowerCase() + "(";
        int c = 1;
        for (Atributo atr : this.modelo.getAtributos()) {

            if (c == 2) {
                this.insertQuery += ", ";
                c = 1;
            }

            this.insertQuery += atr.getNome().toLowerCase();

            c++;
        }

        this.insertQuery += ") VALUES (";
        int qtde = this.modelo.getAtributos().size();
        int contador = 1;
        for (int i = 0; i < qtde; i++) {
            if (contador == 2) {
                this.insertQuery += ",";
                contador = 1;
            }
            contador++;
            this.insertQuery += "?";
        }

        this.insertQuery += ")";
    }

    public String getInsertQuery() {
        return this.insertQuery;
    }

    public List<String> getSelectQueries() {
        return this.selectQueries;
    }

    public String getDeleteQuery() {
        return this.deleteQuery;
    }

    public String getUpdateQuery() {
        return this.updateQuery;
    }
}
