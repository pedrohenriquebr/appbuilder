package appbuilder.api.vars;

import appbuilder.api.classes.ClassBuilder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Atributo é sinônimo de variável de instância
 *
 * @author aluno
 */
public class AttributeBuilder extends VarBuilder {

    private boolean inicializar = false;
    private boolean estático = false;
    private String modAcesso = "public";

    public AttributeBuilder(String tipo, String nome) {
        super(tipo, nome);
        //por padrão
        addModificador(modAcesso);
    }

    public String getModAcesso() {
        return this.modAcesso;
    }

    public void setModAcesso(String mod) {
        this.modAcesso = mod;
    }

    public void ativarInicialização(String valor) {
        setValor(valor);
        inicializar = true;
    }

    public void desativarInicialização() {
        inicializar = false;
    }

    public AttributeBuilder(String modificador, String tipo, String nome) {
        super(modificador, tipo, nome, "");

    }

    public AttributeBuilder(String modificador, String tipo, String nome, String valor) {
        super(modificador, tipo, nome, valor);
        this.classBuilder = ClassBuilder.getStaticCall(tipo);
    }

    @Override
    public String getReferencia() {
        if (this.getMods().contains("static")) {
            estático = true;
            return getClasse().getName() + "." + nome;
        }

        return "this." + nome;
    }

    @Override
    public String getInicialização(String valor) {
        return getReferencia() + " = " + valor + ";\n";
    }

    @Override
    public String getDeclaração() {
        String codigo = "";
        for (String mod : mods) {
            codigo += mod + " ";
        }

        codigo += tipo + " " + nome;

        if (inicializar) {
            codigo += " = " + valor;
        }
        codigo += ";\n";

        return codigo;
    }

    @Override
    public String toString() {
        return getDeclaração();
    }

}
