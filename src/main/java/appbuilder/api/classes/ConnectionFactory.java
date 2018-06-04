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
public class ConnectionFactory extends Classe {

    public ConnectionFactory(String pacote, String caminho) {
        super("ConnectionFactory", pacote, caminho);
        addImportação("java.sql.SQLException");
        addImportação("java.sql.DriverManager");
        addImportação("java.sql.Connection");

        Atributo user = new Atributo("private", "String", "user");
        Atributo password = new Atributo("private", "String", "password");
        Atributo database = new Atributo("private", "String", "database");
        Atributo host = new Atributo("private", "String", "host");

        user.addModificador("static");
        password.addModificador("static");
        database.addModificador("static");
        host.addModificador("static");
        addAtributo(user);
        addAtributo(password);
        addAtributo(database);
        addAtributo(host);

        //inicializar
        //os atributos com seus devidos valores padrões
        user.ativarInicialização("\"root\"");
        password.ativarInicialização("\"root\"");
        database.ativarInicialização("\"project\"");
        host.ativarInicialização("\"localhost\"");

        Método getConnection = new Método("public", "static", "Connection", "getConnection");
        getConnection.addExceção((Exceção) getClasse("SQLException"));
        getConnection.setRetorno(getClasse("DriverManager").
                callStatic("getConnection",
                        "\"jdbc:mysql://\"+" + host.getReferencia() + "+\"/\"+" + database.getReferencia() + "",
                        user.getReferencia(), password.getReferencia()));

        addMétodo(getConnection);
    }

    public void setServidor(String servidor) {
        getAtributo("host").setValor("\"" + servidor + "\"");
    }

    public String getServidor() {
        return getAtributo("host").getValor().replace("\"", "");
    }

    public void setSenha(String senha) {
        getAtributo("password").setValor("\"" + senha + "\"");
    }

    public String getSenha() {
        return getAtributo("password").getValor().replace("\"", "");
    }

    public void setBaseDeDados(String base) {
        getAtributo("database").setValor("\"" + base + "\"");
    }

    public String getBaseDeDados() {
        return getAtributo("database").getValor().replace("\"", "");
    }

    public void setUsuário(String usuario) {
        getAtributo("user").setValor("\"" + usuario + "\"");
    }

    public String getUsuário() {
        return getAtributo("user").getValor().replace("\"", "");
    }

}
