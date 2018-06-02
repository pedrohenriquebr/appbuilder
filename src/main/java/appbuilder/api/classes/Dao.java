/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.api.classes;

import appbuilder.api.classes.exceptions.TratamentoDeExceção;
import appbuilder.api.methods.Método;
import appbuilder.api.methods.Parametro;
import appbuilder.api.vars.Atributo;
import appbuilder.api.vars.Objeto;
import appbuilder.api.vars.Variavel;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import sun.util.calendar.CalendarUtils;

/**
 *
 * @author psilva
 */
public class Dao extends Classe {

    private Modelo modelo;
    private ConnectionFactory factory;
    private Atributo conexão;
    private Método métodoAdd;
    private Método métodoRemove;
    private Método métodoUpdate;

    private List<Método> metodosSearch = new ArrayList<>();

    public Dao(Modelo modelo, ConnectionFactory factory) throws FileNotFoundException {
        super(modelo.getNome() + "DAO");
        this.modelo = modelo;
        this.factory = factory;
        addImportação("java.sql.Connection");
        addImportação("java.sql.SQLException");
        addImportação("java.sql.PreparedStatement");
        addImportação("java.sql.Statement");
        addImportação("java.util.Calendar");
        addImportação("java.sql.Date");
        addImportação("java.sql.ResultSet");
        addImportação("java.util.ArrayList");
        addImportação("java.util.List");

        addImportação(Classe.addClasse(factory));
        addImportação(Classe.addClasse(modelo));

        Atributo chave = modelo.getChave();

        conexão = new Atributo("private", "Connection", "con");
        addAtributo(conexão);

        //construtor
        Construtor construtor = new Construtor("public", this.nome);
        TratamentoDeExceção trycatch = tratarExceção("SQLException");
        trycatch.addCorpo(conexão.getInicialização(factory.callStatic("getConnection")));
        construtor.addCorpo(trycatch.toString());
        setConstrutorPrincipal(construtor);
        addConstrutor(construtor);

        //COMEÇO - ADD
        {
            métodoAdd = new Método("public", "boolean", "add");
            Parametro paramModelo = new Parametro(this.modelo.getNome(), "modelo");
            paramModelo.setClasse(this);
            métodoAdd.addParametro(paramModelo);
            métodoAdd.addExceção((Exceção) getClasse("SQLException"));

            {
                Variavel stmt = new Variavel("PreparedStatement", "stmt");
                stmt.setClasse(this);

                String addQuery = "INSERT INTO "
                        + modelo.getNome().toLowerCase() + "(";
                int c = 1;
                for (Atributo atr : modelo.getAtributos()) {

                    if (c == 2) {
                        addQuery += ", ";
                        c = 1;
                    }

                    addQuery += atr.getNome().toLowerCase();

                    c++;
                }

                addQuery += ") VALUES (";
                int qtde = modelo.getAtributos().size();
                int contador = 1;
                for (int i = 0; i < qtde; i++) {
                    if (contador == 2) {
                        addQuery += ",";
                        contador = 1;
                    }
                    contador++;
                    addQuery += "?";
                }

                addQuery += ")";

                String executeInsertQuery = conexão.call("prepareStatement", "\"" + addQuery + "\"");
                métodoAdd.addCorpo(stmt.getDeclaração(executeInsertQuery));

                int pos = 1;
                for (Atributo atributo : modelo.getAtributos()) {
                    String metodo = "set";
                    String argumento = paramModelo.
                            call(
                                    modelo.getGetter(
                                            atributo.getNome())
                                            .getNome());

                    if (atributo.getTipo().equals("String")) {
                        metodo += "String";
                    } else if (atributo.getTipo().equals("int")) {
                        metodo += "Int";

                    } else if (atributo.getTipo().equals("double")) {
                        metodo += "Double";
                    } else if (atributo.getTipo().equals("float")) {
                        metodo += "Float";
                    } else if (atributo.getTipo().equals("Calendar")) {
                        metodo += "Date";
                        Método getter = modelo.getGetter(atributo.getNome());
                        Variavel var = new Variavel("Calendar", "tmp" + pos);
                        var.setClasse(this);
                        métodoAdd.addCorpo(var.getDeclaração(argumento));
                        argumento = var.call("getTime");
                    }

                    métodoAdd.addCorpo(stmt.call(metodo, pos + "", argumento));
                    pos++;
                }

                Variavel retAdd = new Variavel("int", "retorno");
                retAdd.setClasse(this);
                métodoAdd.addCorpo(retAdd.getDeclaração(stmt.call("executeUpdate")));
                Variavel tmp = new Variavel("Statement", "closeOperation");
                tmp.setClasse(this);
                métodoAdd.addCorpo(tmp.getDeclaração("(Statement) " + stmt.getReferencia()));
                métodoAdd.addCorpo(tmp.call("close"));
                métodoAdd.setRetorno(retAdd.getReferencia() + " > 0");

            }

        }
        //FIM - ADD

        // COMEÇO - REMOVE 
        {
            métodoRemove = new Método("public", "boolean", "remove");
            Parametro paramModelo = new Parametro(this.modelo.getNome(), "modelo");
            paramModelo.setClasse(this);
            métodoRemove.addParametro(paramModelo);
            métodoRemove.addExceção((Exceção) getClasse("SQLException"));

            {
                Variavel stmt = new Variavel("PreparedStatement", "stmt");
                stmt.setClasse(this);

                String removeQuery = "DELETE  FROM "
                        + modelo.getNome().toLowerCase() + " WHERE "
                        + chave.getNome().toLowerCase() + "=?";

                String executeRemoveQuery = conexão.call("prepareStatement", "\"" + removeQuery + "\"");
                métodoRemove.addCorpo(stmt.getDeclaração(executeRemoveQuery));

                int pos = 1;
                Atributo atributo = chave;
                String metodo = "set";
                String argumento = paramModelo.
                        call(
                                modelo.getGetter(
                                        atributo.getNome())
                                        .getNome());

                if (atributo.getTipo().equals("String")) {
                    metodo += "String";
                } else if (atributo.getTipo().equals("int")) {
                    metodo += "Int";

                } else if (atributo.getTipo().equals("double")) {
                    metodo += "Double";
                } else if (atributo.getTipo().equals("float")) {
                    metodo += "Float";
                } else if (atributo.getTipo().equals("Calendar")) {
                    metodo += "Date";
                    Método getter = modelo.getGetter(atributo.getNome());
                    Variavel var = new Variavel("Calendar", "tmp" + pos);
                    var.setClasse(this);
                    métodoRemove.addCorpo(var.getDeclaração(argumento));
                    argumento = var.call("getTime");
                }

                métodoRemove.addCorpo(stmt.call(metodo, pos + "", argumento));
                pos++;

                Variavel retAdd = new Variavel("int", "retorno");
                retAdd.setClasse(this);
                métodoRemove.addCorpo(retAdd.getDeclaração(stmt.call("executeUpdate")));
                Variavel tmp = new Variavel("Statement", "closeOperation");
                tmp.setClasse(this);
                métodoRemove.addCorpo(tmp.getDeclaração("(Statement) " + stmt.getReferencia()));
                métodoRemove.addCorpo(tmp.call("close"));
                métodoRemove.setRetorno(retAdd.getReferencia() + " > 0");

            }
        }

        //FIM - REMOVE
        //COMEÇO - UPDATE
        {
            métodoUpdate = new Método("public", "boolean", "update");
            Parametro paramModelo = new Parametro(this.modelo.getNome(), "modelo");
            paramModelo.setClasse(this);
            métodoUpdate.addParametro(paramModelo);
            métodoUpdate.addExceção((Exceção) getClasse("SQLException"));

            {
                Variavel stmt = new Variavel("PreparedStatement", "stmt");
                stmt.setClasse(this);

                String updateQuery = "UPDATE "
                        + modelo.getNome().toLowerCase() + " SET ";
                int c = 1;
                for (Atributo atr : modelo.getAtributos()) {
                    if (atr == chave) {
                        continue;
                    }

                    if (c == 2) {
                        updateQuery += ", ";
                        c = 1;
                    }

                    updateQuery += atr.getNome().toLowerCase() + "=?";

                    c++;
                }

                updateQuery += " WHERE " + chave.getNome().toLowerCase() + "=?";

                String executeUpdateQuery = conexão.call("prepareStatement", "\"" + updateQuery + "\"");
                métodoUpdate.addCorpo(stmt.getDeclaração(executeUpdateQuery));

                int pos = 1;
                for (Atributo atributo : modelo.getAtributos()) {
                    if (atributo == chave) {
                        continue;
                    }

                    String metodo = "set";
                    String argumento = paramModelo.
                            call(
                                    modelo.getGetter(
                                            atributo.getNome())
                                            .getNome());

                    if (atributo.getTipo().equals("String")) {
                        metodo += "String";
                    } else if (atributo.getTipo().equals("int")) {
                        metodo += "Int";

                    } else if (atributo.getTipo().equals("double")) {
                        metodo += "Double";
                    } else if (atributo.getTipo().equals("float")) {
                        metodo += "Float";
                    } else if (atributo.getTipo().equals("Calendar")) {
                        metodo += "Date";
                        Método getter = modelo.getGetter(atributo.getNome());
                        Variavel var = new Variavel("Calendar", "tmp" + pos);
                        var.setClasse(this);
                        métodoUpdate.addCorpo(var.getDeclaração(argumento));
                        argumento = var.call("getTime");
                    }

                    métodoUpdate.addCorpo(stmt.call(metodo, pos + "", argumento));
                    pos++;
                }

                String metodo = "set";
                String argumento = paramModelo.
                        call(
                                modelo.getGetter(
                                        chave.getNome())
                                        .getNome());

                if (chave.getTipo().equals("String")) {
                    metodo += "String";
                } else if (chave.getTipo().equals("int")) {
                    metodo += "Int";

                } else if (chave.getTipo().equals("double")) {
                    metodo += "Double";
                } else if (chave.getTipo().equals("float")) {
                    metodo += "Float";
                } else if (chave.getTipo().equals("Calendar")) {
                    metodo += "Date";
                    Método getter = modelo.getGetter(chave.getNome());
                    Variavel var = new Variavel("Calendar", "tmp" + pos);
                    var.setClasse(this);
                    métodoUpdate.addCorpo(var.getDeclaração(argumento));
                    argumento = var.call("getTime");
                }

                métodoUpdate.addCorpo(stmt.call(metodo, pos + "", argumento));

                Variavel retAdd = new Variavel("int", "retorno");
                retAdd.setClasse(this);
                métodoUpdate.addCorpo(retAdd.getDeclaração(stmt.call("executeUpdate")));
                Variavel tmp = new Variavel("Statement", "closeOperation");
                tmp.setClasse(this);
                métodoUpdate.addCorpo(tmp.getDeclaração("(Statement) " + stmt.getReferencia()));
                métodoUpdate.addCorpo(tmp.call("close"));
                métodoUpdate.setRetorno(retAdd.getReferencia() + " > 0");

            }

        }
        //FIM - UPDATE 

        addMétodo(métodoAdd);
        addMétodo(métodoRemove);
        addMétodo(métodoUpdate);

    }

    public void addMétodoPesquisa(String nomeAtributo) {
        Atributo atributo = modelo.getAtributo(nomeAtributo);

        if (atributo == null) {
            return;
        }

        Método método = new Método("public", "List", "searchBy" + camelCase(nomeAtributo));
        Parametro param = new Parametro(atributo.getTipo(), nomeAtributo.toLowerCase());
        método.addParametro(param);
        param.setClasse(this);

        Variavel lista = new Variavel("ArrayList <" + atributo.getTipo() + ">", "lista");
        lista.setClasse(this);
        Variavel stmt = new Variavel("PreparedStatement", "stmt");
        stmt.setClasse(this);
        String searchQuery = "SELECT * FROM "
                + modelo.getNome().toLowerCase() + " WHERE " + nomeAtributo + "=?";

        String executeSearchQuery = conexão.call("prepareStatement", "\"" + searchQuery + "\"");
        método.addCorpo(lista.getDeclaração(lista.instancia().getInstancia()));
        método.addCorpo(stmt.getDeclaração(executeSearchQuery));

        int pos = 1;
        String metodo = "set";
        String argumento = param.getReferencia();

        if (atributo.getTipo().equals("String")) {
            metodo += "String";
        } else if (atributo.getTipo().equals("int")) {
            metodo += "Int";

        } else if (atributo.getTipo().equals("double")) {
            metodo += "Double";
        } else if (atributo.getTipo().equals("float")) {
            metodo += "Float";
        } else if (atributo.getTipo().equals("Calendar")) {
            metodo += "Date";
            Método getter = modelo.getGetter(atributo.getNome());
            Variavel var = new Variavel("Calendar", "tmp" + pos);
            var.setClasse(this);
            método.addCorpo(var.getDeclaração(argumento));
            argumento = var.call("getTime");
        }

        método.addCorpo(stmt.call(metodo, pos + "", argumento));

        addMétodo(método);
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public Atributo getListaDados() {
        return conexão;
    }

    public void setListaDados(Atributo listaDados) {
        this.conexão = listaDados;
    }

    public Método getMétodoAdd() {
        return métodoAdd;
    }

    public void setMétodoAdd(Método métodoAdd) {
        this.métodoAdd = métodoAdd;
    }

    public Método getMétodoRemove() {
        return métodoRemove;
    }

    public void setMétodoRemove(Método métodoRemove) {
        this.métodoRemove = métodoRemove;
    }

    public Método getMétodoUpdate() {
        return métodoUpdate;
    }

    public void setMétodoUpdate(Método métodoUpdate) {
        this.métodoUpdate = métodoUpdate;
    }

    public List<Método> getMetodosSearch() {
        return metodosSearch;
    }

    public void setMetodosSearch(List<Método> metodosSearch) {
        this.metodosSearch = metodosSearch;
    }

}
