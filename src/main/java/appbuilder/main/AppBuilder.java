/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.main;

import java.io.IOException;

import appbuilder.api.annotations.Anotação;
import appbuilder.api.classes.Classe;
import appbuilder.api.classes.Construtor;
import appbuilder.api.classes.Exceção;
import appbuilder.api.classes.Interface;
import appbuilder.api.classes.Modelo;
import appbuilder.api.classes.exceptions.TratamentoDeExceção;
import appbuilder.api.methods.Método;
import appbuilder.api.templates.Template;
import appbuilder.api.vars.Atributo;
import appbuilder.api.vars.Variavel;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 *
 * @author aluno
 */
public class AppBuilder implements Filter {

    private static final Logger logger = Logger.getLogger(Classe.class.getName());

    public static void main(String[] args) throws CloneNotSupportedException {
        Classe cl = new Modelo("Pessoa");
        cl.addImportação(Classe.getClasseEstática("java.lang.RuntimeException"));
        
    }

    @Override
    public boolean isLoggable(LogRecord lr) {
        if (lr == null) {
            return false;
        }

        if (lr.getMessage().contains("adicionado método:") && lr.getMessage().contains("")) {
            return true;
        }

        return false;
    }

}
