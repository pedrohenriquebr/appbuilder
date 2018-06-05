/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.api.classes;

import appbuilder.api.classes.exceptions.TratamentoDeExceção;
import appbuilder.api.methods.Parametro;
import appbuilder.api.packages.Pacote;
import appbuilder.api.vars.Objeto;

/**
 *
 * @author psilva
 */
public class Exceção extends Classe {

    public Exceção(String nome) {
        super(nome);
        if (!nome.endsWith("Exception")) {
            setNome(nome + "Exception");
        }

        //delegar todos os construtores
    }
    
    public Exceção(String nome, String pacote) {
        this(nome);
        this.pacote = new Pacote(pacote);
    }

    public Exceção(String nome, String pacote, String caminho) {
        this(nome);
        this.pacote = new Pacote(pacote, caminho);

    }

    public void delegarConstrutores() {
        for (Construtor construtor : super.getConstrutores()) {
            String codigo = "";
            // indica a posição do parâmetro
            int contador = 1;

            for (Parametro param : construtor.getParametros()) {
                // toda vez que chegar no próximo parâmetro, colocar uma vírgula
                if (contador % 2 == 0) {
                    codigo += ",";
                    contador = 1;
                }
                // coloca o parâmetro
                codigo += param.getReferencia();
                contador++;
            }

            construtor.addCorpo("super(" + codigo + ")");

        }
    }

    public static TratamentoDeExceção tratar(Classe classe, String... exceções) {
        TratamentoDeExceção tratamento = new TratamentoDeExceção(classe);

        for (String exp : exceções) {
            tratamento.addExceção(exp);
        }

        return tratamento;
    }

    public static String lançar(Classe classe, String nome, String... args) {
        String codigo = "";

        codigo = "throw ";

        Exceção exp = (Exceção) classe.getClasse(nome);
        Objeto obj = exp.getInstancia(args);
        codigo+= obj.getInstancia()+";";
        return codigo;
    }
}
