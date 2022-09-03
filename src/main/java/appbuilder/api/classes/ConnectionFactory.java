/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.api.classes;

import appbuilder.api.methods.MethodBuilder;
import appbuilder.api.vars.AttributeBuilder;

/**
 *
 * @author psilva
 */
public class ConnectionFactory extends ClassBuilder {

    public ConnectionFactory(String pacote, String caminho) {
        super("ConnectionFactory", pacote, caminho);
        addImport("java.sql.SQLException");
        addImport("java.sql.DriverManager");
        addImport("java.sql.Connection");

        AttributeBuilder user = new AttributeBuilder("private", "String", "user");
        AttributeBuilder password = new AttributeBuilder("private", "String", "password");
        AttributeBuilder database = new AttributeBuilder("private", "String", "database");
        AttributeBuilder host = new AttributeBuilder("private", "String", "host");

        user.addModificador("static");
        password.addModificador("static");
        database.addModificador("static");
        host.addModificador("static");
        addAttribute(user);
        addAttribute(password);
        addAttribute(database);
        addAttribute(host);

        //inicializar
        //os atributos com seus devidos valores padrões
        user.ativarInicialização("\"root\"");
        password.ativarInicialização("\"root\"");
        database.ativarInicialização("\"project\"");
        host.ativarInicialização("\"localhost\"");

        MethodBuilder getConnection = new MethodBuilder("public", "static", "Connection", "getConnection");
        getConnection.addException((ExceptionBuilder) getClasse("SQLException"));
        getConnection.setReturn(getClasse("DriverManager").
                callStatic("getConnection",
                        "\"jdbc:mysql://\"+" + host.getReferencia() + "+\"/\"+" + database.getReferencia() + "",
                        user.getReferencia(), password.getReferencia()));

        addMethod(getConnection);
    }

    public void setServidor(String servidor) {
        getAttribute("host").setValor("\"" + servidor + "\"");
    }

    public String getServidor() {
        return getAttribute("host").getValor().replace("\"", "");
    }

    public void setSenha(String senha) {
        getAttribute("password").setValor("\"" + senha + "\"");
    }

    public String getSenha() {
        return getAttribute("password").getValor().replace("\"", "");
    }

    public void setBaseDeDados(String base) {
        getAttribute("database").setValor("\"" + base + "\"");
    }

    public String getBaseDeDados() {
        return getAttribute("database").getValor().replace("\"", "");
    }

    public void setUsuário(String usuario) {
        getAttribute("user").setValor("\"" + usuario + "\"");
    }

    public String getUsuário() {
        return getAttribute("user").getValor().replace("\"", "");
    }

}
