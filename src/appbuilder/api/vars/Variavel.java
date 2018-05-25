package appbuilder.api.vars;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import appbuilder.api.classes.Classe;
import appbuilder.util.*;
import java.util.*;

/**
 * Deve ficar subentendido que essa variável é local
 *
 * @author psilva
 */
public class Variavel {

    /**
     *
     */
    protected String nome;

    /**
     *
     */
    protected String tipo;

    /**
     *
     */
    protected List<String> mods = new ArrayList<>();//modificadores static, final, 

    /**
     *
     */
    protected String valor;
    
    /**
     * A classe em que a variável está sendo usada
     */
    
    protected Classe classe;
    
    /**
     * Constrói a variável com seu atributos básicos, o mínimo para ter uma
     * variável é o seu tipo e nome.Valor da variável por padrão é "null".
     *
     * @param tipo String, int, Integer, double, boolean, etc.
     * @param nome qualquer nome a princípio
     */
    public Variavel(String tipo, String nome) {
        this.nome = nome;
        this.tipo = tipo;
        this.valor = "null";
    }

    /**
     * Nesse ponto a variável está completa. Os modificadores são opcionais.
     *
     * @param tipo String, int, Integer, double, boolean, etc.
     * @param nome qualquer nome a princípio
     * @param valor autoexplicativo
     */
    public Variavel(String tipo, String nome, String valor) {
        this(tipo, nome);
        setValor(valor);
    }

    /**
     * A variável agora tem modificadores que são opcionais.
     *
     * @param modificador static, final, public, private, e entre outros,
     * dependendo do contexto
     * @param tipo String, int, double, nomeDeClasse, etc.
     * @param nome autoexplicativo
     * @param valor autoexplicativo
     */
    public Variavel(String modificador, String tipo, String nome, String valor) {
        this(tipo, nome, valor);
        addModificador(modificador);
    }

    /**
     *
     * @param mod modificador tanto de acesso quanto de não acesso, dependendo
     * do contexto
     * @return true ou false se a operação foi realizada
     */
    public boolean addModificador(String mod) {
        return mods.add(mod);
    }

    /**
     *
     * @return retorna uma List com todos os nomes dos modificadores
     */
    public List<String> getMods() {
        return mods;
    }

    /**
     *
     * @param mods List contendo os nomes dos modificadores
     */
    public void setMods(List<String> mods) {
        this.mods = mods;
    }

    /**
     *
     * @return
     */
    public String getValor() {
        return valor;
    }

    /**
     *
     * @param valor
     */
    public void setValor(String valor) {
        this.valor = valor;
    }

    /**
     *
     * @return
     */
    public String getNome() {
        return nome;
    }

    /**
     *
     * @param nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     *
     * @return retorna o tipo da variável: String, int, double, nomeDaClasse
     */
    public String getTipo() {
        return tipo;
    }

    /**
     *
     * @param tipo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Declaração da variável
     *
     * @return "tipo" "nome" ;
     */
    public String getDeclaração() {

        return tipo + " " + nome + ";\n";
    }

    /**
     * Declaração com inicialização
     *
     * @param valor
     * @return "tipo" "nome" = "valor"
     */
    public String getDeclaração(String valor) {
        return tipo + " " + nome + " = " + valor + ";\n";
    }

    /**
     * Inicialização da variável
     *
     * @param valor
     * @return "nome" = "valor" ;
     */
    public String getInicialização(String valor) {

        this.valor = valor;
        return getReferencia() + " = " + valor + ";\n";

    }

    /**
     * Referência da variável
     *
     * @return "nome"
     */
    public String getReferencia() {
        return this.nome;
    }

    /**
     * Retorna um objeto do mesmo tipo da variável
     *
     * @param args argumentos passados para o construtor
     * @return objeto da instância da classe ou tipo da variável
     */
    public Objeto instancia(String... args) {
        return Objeto.instancia(this.tipo, args);
    }
    
    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    /**
     *
     * @return
     */
    @Override
    /**
     * Retorna a declaração
     */
    public String toString() {
        return getDeclaração();
    }
}
