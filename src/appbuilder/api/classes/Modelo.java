/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.api.classes;

import appbuilder.api.vars.Atributo;
import appbuilder.api.classes.Classe;
import appbuilder.api.methods.Método;
import appbuilder.api.methods.Parametro;

/**
 *
 * @author Pedro Henrique Braga da Silva
 */
/**
 * Classe responsável por construir outra classe Modelo dentro do MVC
 *
 * @author psilva
 */
public class Modelo extends Classe {

    public Modelo(String nome) {
        super(nome);
    }
    
    private String camelCase(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

    /**
     * Método para criar um atributo, que estará associado com seus devidos
     * getter e setter
     *
     * @param tipo String, int, etc ...
     * @param nome
     * @return
     */
    public boolean addAtributo(String tipo, String nome) {
        Atributo atr = new Atributo("private", tipo, nome);
        boolean b = super.addAtributo(atr);

        if (b) {

            //adicionar getter e setter (JavaBeans)
            Método getter = new Método("public", tipo, "get" + camelCase(nome));
            getter.setCorpo("return " + atr.getReferencia() + ";\n");

            Método setter = new Método("public", "void", "set" + camelCase(nome));
            Parametro param = new Parametro(tipo, nome);
            setter.addParametro(param);
            setter.setCorpo(atr.getInicialização(nome));

            boolean c = super.addMétodo(getter);
            boolean d = super.addMétodo(setter);

            if (!c || !d) {
                return false;
            }

            return true;
        } else {
            return false;
        }
    }
    
    //só recebem um parâmetro
    public boolean addInteiro(String nome) {
        return addAtributo("int", nome);
    }

    public boolean addString(String nome) {
        return addAtributo("String", nome);
    }

    public boolean addFloat(String nome) {
        return addAtributo("float", nome);
    }

    public boolean addDouble(String nome) {
        return addAtributo("double", nome);
    }

    //recebem mais de um parâmetro
    public int addInteiros(String... nomes) {
        //conta quantos atributos do tipo inteiro foram adicionados com sucesso
        int sucesso = 0;

        for (String nome : nomes) {
            boolean resultado = addInteiro(nome);
            if (resultado) {
                sucesso++;
            }

        }

        return sucesso;
    }

    public int addStrings(String... nomes) {
        //conta quantos atributos do tipo inteiro foram adicionados com sucesso
        int sucesso = 0;

        for (String nome : nomes) {
            boolean resultado = addString(nome);
            if (resultado) {
                sucesso++;
            }

        }

        return sucesso;
    }

    public int addFloats(String... nomes) {
        //conta quantos atributos do tipo inteiro foram adicionados com sucesso
        int sucesso = 0;

        for (String nome : nomes) {
            boolean resultado = addFloat(nome);
            if (resultado) {
                sucesso++;
            }

        }

        return sucesso;
    }

    public int addDoubles(String... nomes) {
        //conta quantos atributos do tipo inteiro foram adicionados com sucesso
        int sucesso = 0;

        for (String nome : nomes) {
            boolean resultado = addDouble(nome);
            if (resultado) {
                sucesso++;
            }

        }

        return sucesso;
    }
    
    
    /**
     * Pegar um getter associado a um atributo
     * @param atributo nome do atributo já declarado no modelo
     * @return 
     */
    public Método getGetter(String atributo) {
        Método método = null;
        String camelCase = "get" + camelCase(atributo);
        método = super.getMétodo(camelCase);

        return método;
    }

    //retorna o setter com base no nome do atributo associado
    public Método getSetter(String atributo) {
        Método método = null;
        String camelCase = "set" + camelCase(atributo);
        método = super.getMétodo(camelCase);

        return método;
    }

}
