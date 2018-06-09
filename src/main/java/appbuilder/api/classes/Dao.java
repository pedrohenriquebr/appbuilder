/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.api.classes;

import appbuilder.api.classes.exceptions.TratamentoDeExceção;
import appbuilder.api.database.BaseDeDados;
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
    private BaseDeDados database;
    private Atributo conexão;
    private Método métodoAdd;
    private Método métodoRemove;
    private Método métodoUpdate;

    private List<Método> metodosSearch = new ArrayList<>();

    public Dao(Modelo modelo, ConnectionFactory factory, BaseDeDados database) throws FileNotFoundException {
        super(modelo.getNome() + "DAO");
        this.modelo = modelo;
        this.factory = factory;
        this.database = database;

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
        conexão = new Atributo("private", "Connection", "con");
        addAtributo(conexão);

        //construtor
        Construtor construtor = new Construtor("public", this.nome);
        TratamentoDeExceção trycatch = tratarExceção("SQLException");
        Variavel stmt = new Variavel("PreparedStatement", "stmt");
        Variavel ret1 = new Variavel("boolean", "ret1");
        Variavel ret2 = new Variavel("boolean", "ret2");
        Variavel ret3 = new Variavel("boolean", "ret3");
        ret2.setClasse(this);
        ret3.setClasse(this);
        ret1.setClasse(this);
        stmt.setClasse(this);

        String createDataBaseQuery = database.getCreateDataBaseQuery();
        String useDataBaseQuery = database.getUseDataBaseQuery();
        String createTableQuery = database.getCreateTableQuery();

        trycatch.addCorpo(conexão.getInicialização(factory.callStatic("getConnection")));
        String executeCreateDataBaseQuery = conexão.call("prepareStatement", "\"" + createDataBaseQuery + "\"");
        String executeUseDataBaseQuery = conexão.call("prepareStatement", "\"" + useDataBaseQuery + "\"");
        String executeCreateTableQuery = conexão.call("prepareStatement", "\"" + createTableQuery + "\"");
        trycatch.addCorpo(stmt.getDeclaração(executeCreateDataBaseQuery));
        trycatch.addCorpo(ret1.getDeclaração(stmt.call("executeUpdate") + "> 0 "));
        trycatch.addCorpo(stmt.getInicialização(executeUseDataBaseQuery));
        trycatch.addCorpo(ret2.getDeclaração(stmt.call("executeUpdate") + "> 0 "));
        trycatch.addCorpo(stmt.getInicialização(executeCreateTableQuery));
        trycatch.addCorpo(ret3.getDeclaração(stmt.call("executeUpdate") + "> 0 "));
        If cond = new If("!(" + ret1.getReferencia() + " && " + ret2.getReferencia() + " && " + ret3.getReferencia() + ")");
        trycatch.addCorpo(cond.toString());

        construtor.addCorpo(trycatch.toString());
        setConstrutorPrincipal(construtor);
        addConstrutor(construtor);

        //ADD 
        addMétodoAdd();
        //REMOVE
        addMétodoRemove();
        //UPDATE
        addMétodoUpdate();

        addMétodo(métodoAdd);
        addMétodo(métodoRemove);
        addMétodo(métodoUpdate);

    }

    private void addMétodoUpdate() {
        Atributo chave = modelo.getChave();
        métodoUpdate = new Método("public", "boolean", "update");
        Parametro paramModelo = new Parametro(this.modelo.getNome(), "modelo");
        paramModelo.setClasse(this);
        métodoUpdate.addParametro(paramModelo);
        métodoUpdate.addExceção(getExceção("SQLException"));

        Variavel stmt = new Variavel("PreparedStatement", "stmt");
        stmt.setClasse(this);

        String updateQuery = database.getUpdateQuery();

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
                argumento = getClasse("Date").getInstancia(var.call("getTimeInMillis")).getInstancia();
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
            argumento = getClasse("Date").getInstancia(var.call("getTimeInMillis")).getInstancia();
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

    private void addMétodoRemove() {
        // COMEÇO - REMOVE 

        Atributo chave = modelo.getChave();
        métodoRemove = new Método("public", "boolean", "remove");
        Parametro paramModelo = new Parametro(this.modelo.getNome(), "modelo");
        paramModelo.setClasse(this);
        métodoRemove.addParametro(paramModelo);
        métodoRemove.addExceção(getExceção("SQLException"));

        Variavel stmt = new Variavel("PreparedStatement", "stmt");
        stmt.setClasse(this);

        String removeQuery = database.getDeleteQuery();

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
            argumento = getClasse("Date").getInstancia(var.call("getTimeInMillis")).getInstancia();
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

    private void addMétodoAdd() {
        //COMEÇO - ADD

        métodoAdd = new Método("public", "boolean", "add");
        Parametro paramModelo = new Parametro(this.modelo.getNome(), "modelo");
        paramModelo.setClasse(this);
        métodoAdd.addParametro(paramModelo);
        métodoAdd.addExceção(getExceção("SQLException"));

        Variavel stmt = new Variavel("PreparedStatement", "stmt");
        stmt.setClasse(this);

        String addQuery = database.getInsertQuery();

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
                argumento = getClasse("Date").getInstancia(
                        var.call("getTimeInMillis")).getInstancia();
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

        //FIM - ADD
    }

    public void addMétodoPesquisa(String nomeAtributo) {
        Atributo atributo = modelo.getAtributo(nomeAtributo);

        if (atributo == null) {
            return;
        }

        Método método = new Método("public", "List<" + modelo.getNome() + ">", "searchBy" + upperCase(nomeAtributo));
        Parametro param = new Parametro(atributo.getTipo(), nomeAtributo.toLowerCase());
        método.addParametro(param);
        param.setClasse(this);
        método.addExceção(getExceção("SQLException"));

        Variavel lista = new Variavel("ArrayList <" + modelo.getNome() + ">", "lista");
        lista.setClasse(this);
        Variavel stmt = new Variavel("PreparedStatement", "stmt");
        stmt.setClasse(this);
        boolean added = database.addSelectQuery(nomeAtributo);
        assert added == true;

        if (!added) {
            System.err.println("addMétodoPesquisa: não foi possível adicionar query de pesquisa !");
        }

        String searchQuery = database.getSelectQuery(nomeAtributo);

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
            argumento = getClasse("Date").getInstancia(
                    var.call("getTimeInMillis")).getInstancia();
        }

        método.addCorpo(stmt.call(metodo, pos + "", argumento));

        Variavel rs = new Variavel("ResultSet", "rs");
        rs.setClasse(this);
        método.addCorpo(rs.getDeclaração(stmt.call("executeQuery")));

        While wh = new While(rs.call("next"));
        Variavel obj = new Variavel(modelo.getNome(), "obj");
        obj.setClasse(this);
        wh.addCorpo(obj.getDeclaração(obj.instancia().getInstancia()));

        //
        pos = 1;
        for (Atributo atr : this.modelo.getAtributos()) {
            String chamada = "get";
            String arg = "";

            if (atr.getTipo().equals("Calendar")) {
                chamada += "Date";
                String tmp = rs.call(chamada, "\"" + atr.getNome().toLowerCase() + "\"");
                Variavel data = new Variavel("Calendar", "data" + pos);
                data.setClasse(this);
                wh.addCorpo(
                        data.getDeclaração(
                                getClasse("Calendar").
                                        callStatic("getInstance")));

                wh.addCorpo(data.call("setTime", tmp));
                arg = data.getReferencia();
                pos++;
            } else {

                if (atr.getTipo().equals("String")) {
                    chamada += "String";
                } else if (atr.getTipo().equals("int")) {
                    chamada += "Int";

                } else if (atr.getTipo().equals("double")) {
                    chamada += "Double";
                } else if (atr.getTipo().equals("float")) {
                    chamada += "Float";
                }

                arg = rs.call(chamada, "\"" + atr.getNome().toLowerCase() + "\"");

            }

            wh.addCorpo(obj.call(modelo.getSetter(atr.getNome()).getNome(), arg));

        }

        wh.addCorpo(lista.call("add", obj.getReferencia()));
        método.addCorpo(wh.toString());
        Variavel tmp = new Variavel("Statement", "closeOperation");
        tmp.setClasse(this);
        método.addCorpo(tmp.getDeclaração("(Statement) " + stmt.getReferencia()));
        método.addCorpo(tmp.call("close"));
        método.addCorpo(rs.call("close"));

        método.setRetorno(lista.getReferencia());
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
