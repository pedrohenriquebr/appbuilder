using Api.Old.Classes;

namespace Api.Old.vars;

/// <summary>
///     Deve ficar subentendido que essa variável é local
/// </summary>
/// <remarks>@authorpsilva</remarks>
public class VarBuilder
{
    /// <summary>
    ///     A classe em que a variável está sendo usada
    /// </summary>
    protected ClassBuilder classBuilder;

    protected IList<string> mods = new List(); //modificadores static, final, 

    /// <summary>
    /// </summary>
    protected string nome;

    /// <summary>
    /// </summary>
    protected string tipo;

    /// <summary>
    /// </summary>
    protected string valor;

    /// <summary>
    ///     Constrói a variável com seu atributos básicos, o mínimo para ter uma
    ///     variável é o seu tipo e nome.Valor da variável por padrão é "null".
    /// </summary>
    /// <param name="tipo">String, int, Integer, double, boolean, etc.</param>
    /// <param name="nome">qualquer nome a princípio</param>
    public VarBuilder(string tipo, string nome)
    {
        this.nome = nome;
        this.tipo = tipo;
        valor = "null";
    }

    /// <summary>
    ///     Nesse ponto a variável está completa. Os modificadores são opcionais.
    /// </summary>
    /// <param name="tipo">String, int, Integer, double, boolean, etc.</param>
    /// <param name="nome">qualquer nome a princípio</param>
    /// <param name="valor">autoexplicativo</param>
    public VarBuilder(string tipo, string nome, string valor) : this(tipo, nome)
    {
        SetValor(valor);
    }

    /// <summary>
    ///     A variável agora tem modificadores que são opcionais.
    /// </summary>
    /// <param name="modificador">
    ///     static, final, public, private, e entre outros,
    ///     dependendo do contexto
    /// </param>
    /// <param name="tipo">String, int, double, nomeDeClasse, etc.</param>
    /// <param name="nome">autoexplicativo</param>
    /// <param name="valor">autoexplicativo</param>
    public VarBuilder(string modificador, string tipo, string nome, string valor) : this(tipo, nome, valor)
    {
        AddModificador(modificador);
    }

    /// <summary>
    /// </summary>
    /// <param name="mod">
    ///     modificador tanto de acesso quanto de não acesso, dependendo
    ///     do contexto
    /// </param>
    /// <returns>true ou false se a operação foi realizada</returns>
    public virtual bool AddModificador(string mod)
    {
        return mods.Add(mod);
    }

    /// <summary>
    /// </summary>
    /// <returns>retorna uma List com todos os nomes dos modificadores</returns>
    public virtual IList<string> GetMods()
    {
        return mods;
    }

    /// <summary>
    /// </summary>
    /// <param name="mods">List contendo os nomes dos modificadores</param>
    public virtual void SetMods(IList<string> mods)
    {
        this.mods = mods;
    }

    /// <summary>
    /// </summary>
    /// <returns></returns>
    public virtual string GetValor()
    {
        return valor;
    }

    /// <summary>
    /// </summary>
    /// <param name="valor"></param>
    public virtual void SetValor(string valor)
    {
        this.valor = valor;
    }

    /// <summary>
    /// </summary>
    /// <returns></returns>
    public virtual string GetName()
    {
        return nome;
    }

    /// <summary>
    /// </summary>
    /// <param name="nome"></param>
    public virtual void SetNome(string nome)
    {
        this.nome = nome;
    }

    /// <summary>
    /// </summary>
    /// <returns>retorna o tipo da variável: String, int, double, nomeDaClasse</returns>
    public virtual string GetTipo()
    {
        return tipo;
    }

    /// <summary>
    /// </summary>
    /// <param name="tipo"></param>
    public virtual void SetTipo(string tipo)
    {
        this.tipo = tipo;
    }

    /// <summary>
    ///     Declaração da variável
    /// </summary>
    /// <returns>"tipo" "nome" ;</returns>
    public virtual string GetDeclaração()
    {
        return tipo + " " + nome + ";\\n";
    }

    /// <summary>
    ///     Declaração com inicialização
    /// </summary>
    /// <param name="valor"></param>
    /// <returns>"tipo" "nome" = "valor"</returns>
    public virtual string GetDeclaração(string valor)
    {
        return tipo + " " + nome + " = " + valor + ";\\n";
    }

    /// <summary>
    ///     Inicialização da variável
    /// </summary>
    /// <param name="valor"></param>
    /// <returns>"nome" = "valor" ;</returns>
    public virtual string GetInicialização(string valor)
    {
        this.valor = valor;
        return GetReferencia() + " = " + valor + ";\\n";
    }

    /// <summary>
    ///     Referência da variável
    /// </summary>
    /// <returns>"nome"</returns>
    public virtual string GetReferencia()
    {
        return nome;
    }

    /// <summary>
    ///     Retorna um objeto do mesmo tipo da variável
    /// </summary>
    /// <param name="args">argumentos passados para o construtor</param>
    /// <returns>objeto da instância da classe ou tipo da variável</returns>
    public virtual ObjectBuilder Instancia(params string[] args)
    {
        ClassBuilder cl = classBuilder.GetClasse(GetTipo());
        if (cl == null)
        {
            var nomeCompleto = GetTipo();
            var dividido = nomeCompleto.Split("\\\\.");
            var nome = dividido[dividido.length - 1];
            var pacoteCompleto = nomeCompleto.Substring(0, nomeCompleto.LastIndexOf("."));
            var pacote = pacoteCompleto.Substring(pacoteCompleto.LastIndexOf(".") + 1);
            var acimaDoPacote = pacoteCompleto.Substring(0, pacoteCompleto.LastIndexOf("."));
            try
            {
                cl = ClassBuilder.AddClassBuilder(nome, pacote, acimaDoPacote);
                if (cl != null) classBuilder.AddImport(GetTipo());
            }
            catch (ClassNotFoundException ex)
            {
                Logger.GetLogger(typeof(VarBuilder).GetName()).Log(Level.SEVERE, null, ex);
                System.@out.Println("Problema ao adicionar classe " + GetTipo());
            }

            return ClassBuilder.Get(GetTipo(), args);
        }

        return cl.GetInstancia(args);
    }

    public virtual ClassBuilder GetClasse()
    {
        return classBuilder;
    }

    public virtual void SetClasse(ClassBuilder classBuilder)
    {
        this.classBuilder = classBuilder;
    }

    /// <summary>
    ///     Considera que o atributo é um objeto e que contém métodos e atributos a
    ///     serem chamados. Call serve para chamar algum método.
    /// </summary>
    /// <param name="método"></param>
    /// <param name="args"></param>
    /// <returns></returns>
    public virtual string Call(string método, params string[] args)
    {
        //no caso aqui é como se fosse acessar uma classe que foi importada
        ClassBuilder cl = classBuilder.GetClasse(GetTipo());

        //instância dela 
        var obj = new ObjectBuilder(cl);
        var codigo = "";
        codigo = obj.Call(método, args);
        return GetReferencia() + codigo;
    }

    /// <summary>
    ///     Considera que o atributo é um objeto e que contém métodos e atributos a
    ///     serem chamados. Get serve para acessar um atributo.
    /// </summary>
    /// <param name="atr"></param>
    /// <returns></returns>
    public virtual string Get(string atr)
    {
        ClassBuilder cl = classBuilder.GetClasse(GetTipo());
        var obj = new ObjectBuilder(cl);
        var codigo = "";
        if (!cl.HasAttribute(atr)) throw new Exception("classe " + cl.GetName() + " não tem atributo " + atr);

        codigo = obj[atr];
        return GetReferencia() + codigo;
    }

    public virtual string Set(string atr, string valor)
    {
        ClassBuilder cl = classBuilder.GetClasse(GetTipo());
        var obj = new ObjectBuilder(cl);
        var codigo = "";
        if (!cl.HasAttribute(atr)) throw new Exception("classe " + cl.GetName() + " não tem atributo " + atr);

        codigo = obj[atr] + " = " + valor + ";\\n";
        return GetReferencia() + codigo;
    }

    /// <summary>
    /// </summary>
    /// <returns></returns>
    public override
        ///
        /// Retorna a declaração
        //////String ToString()
    {
        return GetDeclaração();
    }
}