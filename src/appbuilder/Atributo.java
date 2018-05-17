/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder;

/**
 *
 * @author aluno
 */
public class Atributo extends Variavel {

    public Atributo(String tipo, String nome) {
        super(tipo, nome);
        //por padrão
        addModificador("public");
    }

    public Atributo(String tipo, String nome, String valor) {
        super(tipo, nome);
        this.valor = valor;
    }

    public Atributo(String tipo, String nome, String valor, String modificador) {
        super(tipo, nome, valor);
        addModificador(modificador);
    }

    @Override
    public String toString() {
        String codigo = "";

        switch (estado) {
            case DECLARAR: {
                for (String mod : mods) {
                    codigo += mod + " ";
                }

                codigo += tipo + " " + nome + ";\n";
                break;
            }
        }

        return codigo;
    }

}
