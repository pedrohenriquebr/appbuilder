package appbuilder.api.methods;

import appbuilder.api.classes.ExceptionBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Essa classe representa toda a estrutura de um método, declaração e chamada
 * com seus devidos parâmetros.Por padrão o corpo é apenas uma quebra linha.O
 * modificador acesso, tipo de retorno e nome, são obrigatórios no construtor.
 *
 * @author Pedro Henrique Braga da Silva
 */
public class MethodBuilder implements Cloneable {

    protected String accessMod; // modificador de acesso. Ex: public, private , protected
    protected List<String> nonAccessMod = new ArrayList<String>(); // modificador de não-acesso. Ex: final, static,
    // abstract
    protected String returnType; // tipo de retorno. Ex: int, String, void, char , ...
    protected String name;
    protected List<ParameterBuilder> parameterBuilders = new ArrayList<ParameterBuilder>(); // algumMetodo(String arg0, int arg1, byte arg2)
    protected String body = "";
    protected String _return = "";
    protected boolean _belongsInterface;
    private List<ExceptionBuilder> exceptions = new ArrayList<>();

    /**
     * @param accessMod  Ex: public, private e protected
     * @param returnType Ex: int, String, void, char
     * @param name       Ex: algumMetodo()
     */
    // public int nome()
    public MethodBuilder(String accessMod, String returnType, String name) {
        this.accessMod = accessMod.trim();
        this.returnType = returnType.trim();
        this.name = name.trim();
        body = "";// padrão o corpo é apenas uma quebra linha
    }

    // public static int nome()
    public MethodBuilder(String accessMod, String nonAccessMod, String returnType, String name) {
        this(accessMod, returnType, name);
        addModNacesso(nonAccessMod);
    }

    // public static int nome(int arg0, int arg1,...)
    public MethodBuilder(String accessMod, String nonAccessMod, String returnType, String name, List<ParameterBuilder> parameterBuilders) {
        this(accessMod, nonAccessMod, returnType, name);
        setParameters(parameterBuilders);
    }

    public boolean addException(ExceptionBuilder exp) {
        return this.exceptions.add(exp);
    }

    public boolean removeException(ExceptionBuilder exp) {

        return this.exceptions.remove(exp);
    }

    public void setBelongsInterface(boolean b) {
        this._belongsInterface = b;

        if (b) {
            body = "";
        }
    }

    public boolean belongsInterface() {
        return this._belongsInterface;
    }

    /**
     * Retorna a chamada ao método
     *
     * @param params os parametros para o método
     * @return retorna uma String contendo o código de chamada ao método.
     * nome(arg0,arg1,...)
     */
    public String getCall(String... params) {
        var codigo = "";

        codigo += name + "(";

        // conta a posição do parâmetro
        var conta = 1;
        for (String param : params) {
            if (conta % 2 == 0) {
                codigo += ", ";
                conta = 1;
            }

            codigo += param;
            conta++;
        }

        codigo += ")";

        return codigo;
    }

    public String getStaticCall(String _class, String... params) {
        return _class + "." + getCall(params);
    }

    /**
     * Adicionar um parâmetro
     *
     * @param param objeto do tipo Parametro
     * @return true ou false se foi realizado com sucesso
     */
    public boolean addParameters(ParameterBuilder param) {
        return this.parameterBuilders.add(param);
    }

    public boolean addParameters(String type, String name) {
        return addParameters(new ParameterBuilder(type, name));
    }

    /**
     * Pega um parâmetro com base no seu índice de posição na List
     *
     * @param index o índice de posição, a partir do 0
     * @return um objeto Parametro desejado
     */
    public ParameterBuilder getParameter(int index) {
        return parameterBuilders.get(index);
    }

    /**
     * Busca um parâmetro com base no seu nome
     *
     * @param nome nome do parâmetro
     * @return o objeto Parametro se foi encontrado ou null caso contrário
     */
    public ParameterBuilder getParameter(String nome) {
        ParameterBuilder param = null;

        for (ParameterBuilder par : parameterBuilders) {
            if (par.getName().equals(nome)) {
                param = par;
            }
        }
        return param;
    }

    /**
     * Retorna o modificador de acesso. Ex: public, private e protected
     *
     * @return uma String contendo o modificador de acesso
     */
    public String getAccessMod() {
        return accessMod;
    }

    public void setAccessMod(String accessMod) {
        this.accessMod = accessMod;
    }

    public List<String> getNonAccessMod() {
        return nonAccessMod;
    }

    public void setNonAccessMod(List<String> nonAccessMod) {
        this.nonAccessMod = nonAccessMod;
    }

    public boolean removeModNacesso(String mod) {
        return this.nonAccessMod.remove(mod);
    }

    /**
     * Adicionar um modificador de não-acesso, Ex: static, final, synchronized
     *
     * @param mod o modificador de não acesso
     * @return true ou false se foi realizado com sucesso
     */
    public boolean addModNacesso(String mod) {
        return nonAccessMod.add(mod);
    }

    /**
     * Retorna o corpo do método
     *
     * @return String contendo o código do corpo do método
     */
    public String getBody() {
        return this.body;
    }

    /**
     * Formata o código, colocando \t no começo e um \n logo após o ;
     *
     * @param codigo o código a ser colocado no corpo
     * @return o código formatado
     */
    public static String formatCode(String codigo) {
        String[] linhas = codigo.split(";\n");
        String formatado = "";
        for (String linha : linhas) {
            // colocar ; de volta
            formatado += "\t";

            if (!linha.endsWith(";\n") || !linha.endsWith(";") && !linha.endsWith("}")) {
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
    public void setBody(String codigo) {
        this.body = formatCode(codigo);
    }

    /**
     * Adiciona mais código ao corpo do método
     *
     * @param codigo código a ser adicionado
     */
    public void addCorpo(String codigo) {
        this.body += formatCode(codigo);
    }

    /**
     * Retorna o tipo de retorno do método
     *
     * @return int, String, double, etc.
     */
    public String getReturnType() {
        return returnType;
    }

    /**
     * Define o tipo de retorno do método
     *
     * @param returnType podendo ser String, int, double, etc.
     */
    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    /**
     * Retorna o nome do método definido no construtor
     *
     * @return o nome do método
     */
    public String getName() {
        return name;
    }

    /**
     * Define o nome do método
     *
     * @param name o nome do método
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retorna uma List contendo todos os parêmetros (Parametro) do método
     *
     * @return uma List<b>Parametro</b>
     */
    public List<ParameterBuilder> getParameters() {
        return parameterBuilders;
    }

    /**
     * Define todos os parâmetros
     *
     * @param parameterBuilders List de Parametro
     */
    public void setParameters(List<ParameterBuilder> parameterBuilders) {
        this.parameterBuilders = parameterBuilders;
    }

    /**
     * Define o retorno do método
     *
     * @param valor
     * @return
     */
    public void setReturn(String valor) {
        this._return = valor;
    }

    public String getReturn() {
        return this._return;
    }

    @Override
    public String toString() {
        var code = "";

        code += getSignature();
        // se fôr de interface, não tem corpo
        //verificar se tem delegação de tratamento de exceções

        if (exceptions.size() > 0) {
            code += " throws ";
            int counter = 1;
            for (ExceptionBuilder e : exceptions) {
                if (counter % 2 == 0) {
                    code += ", ";
                    counter = 1;
                }

                code += e.getName();
                counter++;
            }
        }

        if (_belongsInterface) {
            code += ";\n\n";
        } else {

            code += "{ \n";
            if (!this.body.isEmpty()) {
                code += this.body;

            }

            if (!this._return.isEmpty()) {
                code += formatCode("return " + this._return + ";\n");
            }

            code += "} \n\n";
        }
        return code;
    }

    public String getSignature() {
        var code = "";

        code += accessMod + " ";
        if (nonAccessMod.size() > 0) {
            for (String mod : nonAccessMod) {
                code += mod + " ";
            }
        }

        if (returnType.length() > 0) {
            code += returnType + " ";
        }

        code += name + "(";

        // indica a posição do parâmetro
        int counter = 1;

        for (ParameterBuilder param : parameterBuilders) {
            // toda vez que chegar no próximo parâmetro, colocar uma vírgula
            if (counter % 2 == 0) {
                code += ", ";
                counter = 1;
            }
            // coloca o parâmetro
            code += param;
            counter++;
        }

        code += ")";

        return code;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
