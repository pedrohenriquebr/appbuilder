package appbuilder.api.methods;

import appbuilder.api.vars.Variavel;
import appbuilder.util.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Pedro Henrique Braga da Silva
 */
public class Parametro extends Variavel {

    public Parametro(String tipo, String nome) {
        super(tipo, nome);
    }

    @Override
    public String getDeclaração() {
        return this.tipo + " " + this.nome;
    }

    @Override
    public String toString() {
        return tipo+" "+getReferencia();
    }

}
