/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.main;

import appbuilder.api.classes.*;
import static appbuilder.api.classes.Classe.classes;
import appbuilder.api.methods.*;
import appbuilder.api.vars.*;
import appbuilder.util.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.*;
import java.sql.Connection;

/**
 *
 * @author aluno
 */
public class AppBuilder {

    /**
     * @param args the command line argumentss
     */
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        //classe Pai ou superclasse

        Log.setEstado(false);

        Modelo pessoa = new Modelo("Pessoa");
        pessoa.addStrings("rg", "nomeCompleto", "endereço", "dataNascimento");

        Método verificar = new Método("public", "boolean", "verificar");
        Variavel var = new Variavel("String", "codigo");
        verificar.addCorpo(var.getDeclaração());
        verificar.addCorpo(var.getInicialização(pessoa.getAtributo("rg").call("length")));
        verificar.setRetorno("true");

        pessoa.addMétodo(verificar);

        System.out.println(pessoa);

    }
}
