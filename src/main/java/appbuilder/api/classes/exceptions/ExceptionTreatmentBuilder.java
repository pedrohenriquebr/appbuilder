/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.api.classes.exceptions;

import appbuilder.api.classes.ClassBuilder;
import appbuilder.api.classes.ExceptionBuilder;
import appbuilder.api.vars.VarBuilder;
import java.util.*;

/**
 *
 * @author psilva
 */
public class ExceptionTreatmentBuilder {

    private List<ExceptionBuilder> exceptions = new ArrayList<ExceptionBuilder>();
    private String corpo = "";
    private ClassBuilder classBuilder;

    public ExceptionTreatmentBuilder(ClassBuilder classBuilder) {
        this.classBuilder = classBuilder;
    }

    public void addCorpo(String codigo) {
        corpo += codigo;
    }

    public void setCorpo(String corpo) {
        this.corpo = corpo;
    }

    public String getCorpo() {
        return this.corpo;
    }

    public boolean addExceção(String nome) {
        ClassBuilder cl = ClassBuilder.getStaticCall(classBuilder.getNomeCompleto(nome));
        if (cl instanceof ExceptionBuilder) {
            return exceptions.add((ExceptionBuilder) cl);
        }
        return false;
    }

    public String toString() {
        String codigo = "";

        codigo += "try{ \n\n";

        codigo += "\t" + corpo;

        codigo += "\n\t}";

        for (ExceptionBuilder e : exceptions) {
            VarBuilder var = new VarBuilder(e.getName(), "exp");
            var.setClasse(this.classBuilder);
            codigo += "catch(" + var.getTipo() + " " + var.getName() + "){\n\n";
            codigo += "\t" + var.call("printStackTrace") + ";";

            codigo += "\n}";
        }

        return codigo;

    }
}
