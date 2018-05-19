package appbuilder.api.methods;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import appbuilder.util.*;
import java.util.*;

/**
 *
 * @author aluno
 */
public class Método {

    private String modAcesso; //modificador de acesso. Ex: public, private , protected
    private List<String> modNacesso = new ArrayList<String>(); // modificador de não-acesso. Ex: final, static, abstract 
    private String tipoRetorno; //tipo de retorno. Ex: int, String, void, char , ...
    private String nome;
    private List<Parametro> parametros = new ArrayList<Parametro>(); // algumMetodo(String arg0, int arg1, byte arg2)
    private String corpo;

    /**
     *
     * @param modAcesso Ex: public, private e protected
     * @param modNacesso Ex: final, static
     * @param tipoRetorno Ex: int, String, void, char
     * @param nome Ex: algumMetodo()
     */
    //public int nome()
    public Método(String modAcesso, String tipoRetorno, String nome) {
        this.modAcesso = modAcesso;
        this.tipoRetorno = tipoRetorno;
        this.nome = nome;
        corpo = "";
    }

    //public static int nome()
    public Método(String modAcesso, String modNacesso, String tipoRetorno, String nome) {
        this(modAcesso, tipoRetorno, nome);
        addModNacesso(modNacesso);
    }

    //public static int nome(int arg0, int arg1,...)
    public Método(String modAcesso, String modNacesso, String tipoRetorno, String nome, List<Parametro> parametros) {
        this(modAcesso, modNacesso, tipoRetorno, nome);
        setParametros(parametros);
    }
    
    
    
    public boolean addParametro(Parametro param) {
        return this.parametros.add(param);
    }

    public boolean addParametro(String tipo, String nome) {
        return addParametro(new Parametro(tipo, nome));
    }

    public Parametro getParametro(int index) {
        return parametros.get(index);
    }

    public String getModAcesso() {
        return modAcesso;
    }

    public void setModAcesso(String modAcesso) {
        this.modAcesso = modAcesso;
    }

    public List<String> getModNacesso() {
        return modNacesso;
    }

    public void setModNacesso(List<String> modNacesso) {
        this.modNacesso = modNacesso;
    }

    public boolean addModNacesso(String mod) {
        return modNacesso.add(mod);
    }

    public String getCorpo() {
        return this.corpo;
    }

    public void setCorpo(String codigo) {
        this.corpo = codigo;
    }

    public String getTipoRetorno() {
        return tipoRetorno;
    }

    public void setTipoRetorno(String tipoRetorno) {
        this.tipoRetorno = tipoRetorno;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Parametro> getParametros() {
        return parametros;
    }

    public void setParametros(List<Parametro> parametros) {
        this.parametros = parametros;
    }

    public String toString() {
        String codigo = "";

        codigo += modAcesso + " ";
        int i = 0;

        if (modNacesso.size() > 0) {
            for (String mod : modNacesso) {
                codigo += mod + " ";
            }
        }

        if (tipoRetorno.length() > 0) {
            codigo += tipoRetorno + " ";
        }

        codigo += nome + "(";

        if (parametros.size() > 0) {
            codigo += "" + parametros.get(i);

            for (i = 1; i < parametros.size(); i++) {
                codigo += " ," + parametros.get(i);
            }

        }

        codigo += "){ \n";

        if (this.corpo.length() > 0) {
            codigo += this.corpo;

        }

        codigo += "} \n\n";

        return codigo;
    }

}
