package appbuilder.api.methods;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import appbuilder.util.*;
import java.util.*;

/**
 * Essa classe representa toda a estrutura de um método, declaração e chamada
 * com seus devidos parâmetros.Por padrão o corpo é apenas uma quebra linha.O
 * modificador acesso, tipo de retorno e nome, são obrigatórios no construtor.
 *
 * @author Pedro Henrique Braga da Silva
 */
public class Método {

    protected String modAcesso; //modificador de acesso. Ex: public, private , protected
    protected List<String> modNacesso = new ArrayList<String>(); // modificador de não-acesso. Ex: final, static, abstract 
    protected String tipoRetorno; //tipo de retorno. Ex: int, String, void, char , ...
    protected String nome;
    protected List<Parametro> parametros = new ArrayList<Parametro>(); // algumMetodo(String arg0, int arg1, byte arg2)
    protected String corpo;

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
        corpo = "\n";//padrão o corpo é apenas uma quebra linha
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

    /**
     * Retorna a chamada ao método
     *
     * @param params os parametros para o método
     * @return retorna uma String contendo o código de chamada ao método.
     * nome(arg0,arg1,...)
     */
    public String getChamada(String... params) {
        String codigo = "";

        codigo += nome + "(";

        //conta a posição do parâmetro
        int conta = 0;
        for (String param : params) {
            if (conta % 2 == 0) {
                codigo += ", ";
            }

            codigo += param;
        }

        codigo += ")";

        return codigo;
    }

    /**
     * Adicionar um parâmetro
     *
     * @param param objeto do tipo Parametro
     * @return true ou false se foi realizado com sucesso
     */
    public boolean addParametro(Parametro param) {
        return this.parametros.add(param);
    }

    public boolean addParametro(String tipo, String nome) {
        return addParametro(new Parametro(tipo, nome));
    }

    /**
     * Pega um parâmetro com base no seu índice de posição na List
     *
     * @param index o índice de posição, a partir do 0
     * @return um objeto Parametro desejado
     */
    public Parametro getParametro(int index) {
        return parametros.get(index);
    }

    /**
     * Busca um parâmetro com base no seu nome
     *
     * @param nome nome do parâmetro
     * @return o objeto Parametro se foi encontrado ou null caso contrário
     */
    public Parametro getParametro(String nome) {
        Parametro param = null;

        for (Parametro par : parametros) {
            if (par.getNome().equals(nome)) {
                param = par;
            }
        }

        return null;
    }

    /**
     * Retorna o modificador de acesso. Ex: public, private e protected
     *
     * @return uma String contendo o modificador de acesso
     */
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

    /**
     * Adicionar um modificador de não-acesso, Ex: static, final, synchronized
     *
     * @param mod o modificador de não acesso
     * @return true ou false se foi realizado com sucesso
     */
    public boolean addModNacesso(String mod) {
        return modNacesso.add(mod);
    }

    /**
     * Retorna o corpo do método
     *
     * @return String contendo o código do corpo do método
     */
    public String getCorpo() {
        return this.corpo;
    }

    /**
     * Formata o código, colocando \t no começo e um \n logo após o ;
     *
     * @param codigo o código a ser colocado no corpo
     * @return o código formatado
     */
    private String formatar(String codigo) {
        String[] linhas = codigo.split(";\n");
        String formatado = "";
        for (String linha : linhas) {
            //colocar ; de volta
            formatado += "\t";
            if (!linha.endsWith(";\n")) {
                formatado += linha + ";\n";
            } else {
                formatado += linha;
            }

        }
        return formatado;
    }

    /**
     * Define todo o corpo
     *
     * @param codigo
     */
    public void setCorpo(String codigo) {
        this.corpo = formatar(codigo);
    }

    /**
     * Adiciona mais código ao corpo do método
     *
     * @param codigo código a ser adicionado
     */
    public void addCorpo(String codigo) {
        this.corpo += formatar(codigo);
    }

    /**
     * Retorna o tipo de retorno do método
     *
     * @return int, String, double, etc.
     */
    public String getTipoRetorno() {
        return tipoRetorno;
    }

    /**
     * Define o tipo de retorno do método
     *
     * @param tipoRetorno podendo ser String, int, double, etc.
     */
    public void setTipoRetorno(String tipoRetorno) {
        this.tipoRetorno = tipoRetorno;
    }

    /**
     * Retorna o nome do método definido no construtor
     *
     * @return o nome do método
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do método
     *
     * @param nome o nome do método
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna uma List contendo todos os parêmetros (Parametro) do método
     *
     * @return uma List<b>Parametro</b>
     */
    public List<Parametro> getParametros() {
        return parametros;
    }

    /**
     * Define todos os parâmetros
     *
     * @param parametros List de Parametro
     */
    public void setParametros(List<Parametro> parametros) {
        this.parametros = parametros;
    }

    @Override
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

        //indica a posição do parâmetro
        int contador = 1;

        for (Parametro param : parametros) {
            //toda vez que chegar no próximo parâmetro, colocar uma vírgula
            if (contador % 2 == 0) {
                codigo += ", ";
                contador = 1;
            }
            //coloca o parâmetro
            codigo += param;
            contador++;
        }

        codigo += "){ \n";

        if (this.corpo.length() > 0) {
            codigo += this.corpo;

        }

        codigo += "} \n\n";

        return codigo;
    }

}
