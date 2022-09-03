/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.api.classes;

import appbuilder.api.classes.exceptions.ExceptionTreatmentBuilder;
import appbuilder.api.methods.ParameterBuilder;
import appbuilder.api.packages.PackageBuilder;
import appbuilder.api.vars.ObjectBuilder;

/**
 *
 * @author psilva
 */
public class ExceptionBuilder extends ClassBuilder {

    public ExceptionBuilder(String nome) {
        super(nome);
        if (!nome.endsWith("Exception")) {
            setName(nome + "Exception");
        }

        //delegar todos os construtores
    }
    
    public ExceptionBuilder(String nome, String pacote) {
        this(nome);
        this.packageBuilder = new PackageBuilder(pacote);
    }

    public ExceptionBuilder(String nome, String pacote, String caminho) {
        this(nome);
        this.packageBuilder = new PackageBuilder(pacote, caminho);

    }

    public void delegarConstrutores() {
        for (ConstructorBuilder constructorBuilder : super.getConstructors()) {
            String codigo = "";
            // indica a posição do parâmetro
            int contador = 1;

            for (ParameterBuilder param : constructorBuilder.getParameters()) {
                // toda vez que chegar no próximo parâmetro, colocar uma vírgula
                if (contador % 2 == 0) {
                    codigo += ",";
                    contador = 1;
                }
                // coloca o parâmetro
                codigo += param.getReferencia();
                contador++;
            }

            constructorBuilder.addCorpo("super(" + codigo + ")");

        }
    }

    public static ExceptionTreatmentBuilder tratar(ClassBuilder classBuilder, String... exceções) {
        ExceptionTreatmentBuilder tratamento = new ExceptionTreatmentBuilder(classBuilder);

        for (String exp : exceções) {
            tratamento.addExceção(exp);
        }

        return tratamento;
    }

    public static String lançar(ClassBuilder classBuilder, String nome, String... args) {
        String codigo = "";

        codigo = "throw ";

        ExceptionBuilder exp = (ExceptionBuilder) classBuilder.getClasse(nome);
        ObjectBuilder obj = exp.getInstancia(args);
        codigo+= obj.getInstancia()+";";
        return codigo;
    }
}
