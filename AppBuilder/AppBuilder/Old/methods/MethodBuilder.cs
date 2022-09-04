namespace Api.Old.methods;

/// <summary>
///     Essa classe representa toda a estrutura de um método, declaração e chamada
///     com seus devidos parâmetros.Por padrão o corpo é apenas uma quebra linha.O
///     modificador acesso, tipo de retorno e nome, são obrigatórios no construtor.
/// </summary>
/// <remarks>@authorPedro Henrique Braga da Silva</remarks>
public class MethodBuilder : ICloneable
{
    protected bool _belongsInterface;
    protected string _return = "";
    protected string accessMod; // modificador de acesso. Ex: public, private , protected
    protected string body = "";
    private readonly IList<ExceptionBuilder> exceptions = new List();
    protected string name;
    protected IList<string> nonAccessMod = new List<string>(); // modificador de não-acesso. Ex: final, static,

    protected IList<ParameterBuilder>
        parameterBuilders = new List<ParameterBuilder>(); // algumMetodo(String arg0, int arg1, byte arg2)

    // abstract
    protected string returnType; // tipo de retorno. Ex: int, String, void, char , ...

    /// <summary>
    /// </summary>
    /// <param name="accessMod">Ex: public, private e protected</param>
    /// <param name="returnType">Ex: int, String, void, char</param>
    /// <param name="name">Ex: algumMetodo()</param>
    // public int nome()
    public MethodBuilder(string accessMod, string returnType, string name)
    {
        this.accessMod = accessMod.Trim();
        this.returnType = returnType.Trim();
        this.name = name.Trim();
        body = ""; // padrão o corpo é apenas uma quebra linha
    }

    // public static int nome()
    public MethodBuilder(string accessMod, string nonAccessMod, string returnType, string name) : this(accessMod,
        returnType, name)
    {
        AddModNacesso(nonAccessMod);
    }

    // public static int nome(int arg0, int arg1,...)
    public MethodBuilder(string accessMod, string nonAccessMod, string returnType, string name,
        IList<ParameterBuilder> parameterBuilders) : this(accessMod, nonAccessMod, returnType, name)
    {
        SetParameters(parameterBuilders);
    }

    public override object Clone()
    {
        return base.Clone();
    }

    public virtual bool AddException(ExceptionBuilder exp)
    {
        return exceptions.Add(exp);
    }

    public virtual bool RemoveException(ExceptionBuilder exp)
    {
        return exceptions.Remove(exp);
    }

    public virtual void SetBelongsInterface(bool b)
    {
        _belongsInterface = b;
        if (b) body = "";
    }

    public virtual bool BelongsInterface()
    {
        return _belongsInterface;
    }

    /// <summary>
    ///     Retorna a chamada ao método
    /// </summary>
    /// <param name="params">os parametros para o método</param>
    /// <returns>
    ///     retorna uma String contendo o código de chamada ao método.
    ///     nome(arg0,arg1,...)
    /// </returns>
    public virtual string GetCall(params string[] @params)
    {
        var codigo = "";
        codigo += name + "(";

        // conta a posição do parâmetro
        var conta = 1;
        foreach (var param in @params)
        {
            if (conta % 2 == 0)
            {
                codigo += ", ";
                conta = 1;
            }

            codigo += param;
            conta++;
        }

        codigo += ")";
        return codigo;
    }

    public virtual string GetStaticCall(string _class, params string[] @params)
    {
        return _class + "." + GetCall(@params);
    }

    /// <summary>
    ///     Adicionar um parâmetro
    /// </summary>
    /// <param name="param">objeto do tipo Parametro</param>
    /// <returns>true ou false se foi realizado com sucesso</returns>
    public virtual bool AddParameters(ParameterBuilder param)
    {
        return parameterBuilders.Add(param);
    }

    public virtual bool AddParameters(string type, string name)
    {
        return AddParameters(new ParameterBuilder(type, name));
    }

    /// <summary>
    ///     Pega um parâmetro com base no seu índice de posição na List
    /// </summary>
    /// <param name="index">o índice de posição, a partir do 0</param>
    /// <returns>um objeto Parametro desejado</returns>
    public virtual ParameterBuilder GetParameter(int index)
    {
        return parameterBuilders[index];
    }

    /// <summary>
    ///     Busca um parâmetro com base no seu nome
    /// </summary>
    /// <param name="nome">nome do parâmetro</param>
    /// <returns>o objeto Parametro se foi encontrado ou null caso contrário</returns>
    public virtual ParameterBuilder GetParameter(string nome)
    {
        ParameterBuilder param = null;
        foreach (var par in parameterBuilders)
            if (par.GetName().Equals(nome))
                param = par;

        return param;
    }

    /// <summary>
    ///     Retorna o modificador de acesso. Ex: public, private e protected
    /// </summary>
    /// <returns>uma String contendo o modificador de acesso</returns>
    public virtual string GetAccessMod()
    {
        return accessMod;
    }

    public virtual void SetAccessMod(string accessMod)
    {
        this.accessMod = accessMod;
    }

    public virtual IList<string> GetNonAccessMod()
    {
        return nonAccessMod;
    }

    public virtual void SetNonAccessMod(IList<string> nonAccessMod)
    {
        this.nonAccessMod = nonAccessMod;
    }

    public virtual bool RemoveModNacesso(string mod)
    {
        return nonAccessMod.Remove(mod);
    }

    /// <summary>
    ///     Adicionar um modificador de não-acesso, Ex: static, final, synchronized
    /// </summary>
    /// <param name="mod">o modificador de não acesso</param>
    /// <returns>true ou false se foi realizado com sucesso</returns>
    public virtual bool AddModNacesso(string mod)
    {
        return nonAccessMod.Add(mod);
    }

    /// <summary>
    ///     Retorna o corpo do método
    /// </summary>
    /// <returns>String contendo o código do corpo do método</returns>
    public virtual string GetBody()
    {
        return body;
    }

    /// <summary>
    ///     Formata o código, colocando \t no começo e um \n logo após o ;
    /// </summary>
    /// <param name="codigo">o código a ser colocado no corpo</param>
    /// <returns>o código formatado</returns>
    public static string FormatCode(string codigo)
    {
        var linhas = codigo.Split(";\\n");
        var formatado = "";
        foreach (var linha in linhas)
        {
            // colocar ; de volta
            formatado += "\\t";
            if (!linha.EndsWith(";\\n") || (!linha.EndsWith(";") && !linha.EndsWith("}")))
                formatado += linha + ";\\n";
            else
                formatado += linha;
        }

        return formatado;
    }

    /// <summary>
    ///     Define todo o corpo
    /// </summary>
    /// <param name="codigo"></param>
    public virtual void SetBody(string codigo)
    {
        body = FormatCode(codigo);
    }

    /// <summary>
    ///     Adiciona mais código ao corpo do método
    /// </summary>
    /// <param name="codigo">código a ser adicionado</param>
    public virtual void AddCorpo(string codigo)
    {
        body += FormatCode(codigo);
    }

    /// <summary>
    ///     Retorna o tipo de retorno do método
    /// </summary>
    /// <returns>int, String, double, etc.</returns>
    public virtual string GetReturnType()
    {
        return returnType;
    }

    /// <summary>
    ///     Define o tipo de retorno do método
    /// </summary>
    /// <param name="returnType">podendo ser String, int, double, etc.</param>
    public virtual void SetReturnType(string returnType)
    {
        this.returnType = returnType;
    }

    /// <summary>
    ///     Retorna o nome do método definido no construtor
    /// </summary>
    /// <returns>o nome do método</returns>
    public virtual string GetName()
    {
        return name;
    }

    /// <summary>
    ///     Define o nome do método
    /// </summary>
    /// <param name="name">o nome do método</param>
    public virtual void SetName(string name)
    {
        this.name = name;
    }

    /// <summary>
    ///     Retorna uma List contendo todos os parêmetros (Parametro) do método
    /// </summary>
    /// <returns>uma List<b>Parametro</b></returns>
    public virtual IList<ParameterBuilder> GetParameters()
    {
        return parameterBuilders;
    }

    /// <summary>
    ///     Define todos os parâmetros
    /// </summary>
    /// <param name="parameterBuilders">List de Parametro</param>
    public virtual void SetParameters(IList<ParameterBuilder> parameterBuilders)
    {
        this.parameterBuilders = parameterBuilders;
    }

    /// <summary>
    ///     Define o retorno do método
    /// </summary>
    /// <param name="valor"></param>
    /// <returns></returns>
    public virtual void SetReturn(string valor)
    {
        _return = valor;
    }

    public virtual string GetReturn()
    {
        return _return;
    }

    public override string ToString()
    {
        var code = "";
        code += GetSignature();

        // se fôr de interface, não tem corpo
        //verificar se tem delegação de tratamento de exceções
        if (exceptions.Count > 0)
        {
            code += " throws ";
            var counter = 1;
            foreach (ExceptionBuilder e in exceptions)
            {
                if (counter % 2 == 0)
                {
                    code += ", ";
                    counter = 1;
                }

                code += e.GetName();
                counter++;
            }
        }

        if (_belongsInterface)
        {
            code += ";\\n\\n";
        }
        else
        {
            code += "{ \\n";
            if (!body.IsEmpty()) code += body;

            if (!_return.IsEmpty()) code += FormatCode("return " + _return + ";\\n");

            code += "} \\n\\n";
        }

        return code;
    }

    public virtual string GetSignature()
    {
        var code = "";
        code += accessMod + " ";
        if (nonAccessMod.Count > 0)
            foreach (var mod in nonAccessMod)
                code += mod + " ";

        if (returnType.Length() > 0) code += returnType + " ";

        code += name + "(";

        // indica a posição do parâmetro
        var counter = 1;
        foreach (var param in parameterBuilders)
        {
            // toda vez que chegar no próximo parâmetro, colocar uma vírgula
            if (counter % 2 == 0)
            {
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
}