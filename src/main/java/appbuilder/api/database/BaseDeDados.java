/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.api.database;

import appbuilder.api.classes.ConnectionFactory;
import appbuilder.api.classes.Modelo;
import appbuilder.api.vars.Atributo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author pedro
 */
public class BaseDeDados {

    private Modelo modelo;
    private ConnectionFactory factory;

    private String createTableQuery = "";
    private String insertQuery = "";
    private String deleteQuery = "";
    private List<String> selectQueries = new ArrayList<>();
    private Map<String, String> mapSelectQueries = new HashMap<>();
    private String updateQuery = "";

    public BaseDeDados(Modelo modelo, ConnectionFactory factory) {
        this.modelo = modelo;
        this.factory = factory;

        addUpateQuery();
        addDeleteQuery();
        addInsertQuery();

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
        return mapSelectQueries.get(nomeAtributo);
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
