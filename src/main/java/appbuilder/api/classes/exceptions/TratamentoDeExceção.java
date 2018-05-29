/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.api.classes.exceptions;

import appbuilder.api.classes.Classe;
import appbuilder.api.classes.Exceção;
import appbuilder.api.vars.Objeto;
import appbuilder.api.vars.Variavel;
import java.util.*;

/**
 *
 * @author psilva
 */
public class TratamentoDeExceção {

    private List<Exceção> exceções = new ArrayList<>();
    private String corpo = "";
    private Classe classe;

    public TratamentoDeExceção(Classe classe) {
        this.classe = classe;
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
        Classe cl = Classe.getClasseEstática(classe.getNomeCompleto(nome));
        if (cl instanceof Exceção) {
            return exceções.add((Exceção) cl);
        }
        return false;
    }

    public String toString() {
        String codigo = "";

        codigo += "try{ \n\n\t";

        codigo += corpo;

        codigo += "}";

        for (Exceção e : exceções) {
            Variavel var = new Variavel(e.getNome(), "exp");
            var.setClasse(this.classe);
            codigo += "catch(" + var.getTipo() + " " + var.getNome() + "){\n\n\t";
            codigo += var.call("printStackTrace") + ";";

            codigo += "\n}";
        }

        return codigo;

    }
}
