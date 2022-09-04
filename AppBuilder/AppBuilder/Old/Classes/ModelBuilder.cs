/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

namespace Api.Old.Classes;

/// <summary>
/// </summary>
/// <remarks>@authorPedro Henrique Braga da Silva</remarks>
/// <summary>
///     Classe responsável por construir outra classe Modelo dentro do MVC
/// </summary>
/// <remarks>@authorpsilva</remarks>
public class ModelBuilder : ClassBuilder
{
    private AttributeBuilder chave;

    public ModelBuilder(string nome) : base(nome)
    {
    }

    public ModelBuilder(string nome, string pacote) : base(nome, pacote)
    {
    }

    public ModelBuilder(string nome, string pacote, string caminho) : base(nome, pacote, caminho)
    {
    }

    public virtual void SetKey(string nome)
    {
        chave = GetAttribute(nome);
    }

    public virtual AttributeBuilder GetChave()
    {
        return chave;
    }

    /// <summary>
    ///     Método para criar um atributo, que estará associado com seus devidos
    ///     getter e setter
    /// </summary>
    /// <param name="tipo">String, int, etc ...</param>
    /// <param name="nome"></param>
    /// <returns></returns>
    public virtual bool AddAtributo(string tipo, string nome)
    {
        if (nome.IsEmpty()) return false;

        AttributeBuilder atr = new AttributeBuilder("private", tipo, nome);
        var b = base.AddAttribute(atr);
        if (b)
        {
            //adicionar getter e setter (JavaBeans)
            var c = base.AddGetter(atr);
            var d = base.AddSetter(atr);
            return !(!c || !d);
        }

        return false;
    }

    /// <summary>
    ///     Adiciona um atributo com seus getter e setter do tipo int
    /// </summary>
    /// <param name="nome"></param>
    /// <returns></returns>
    public virtual bool AddInteiro(string nome)
    {
        return AddAtributo("int", nome);
    }

    /// <summary>
    ///     Adiciona um atributo com seus getter e setter do tipo String
    /// </summary>
    /// <param name="nome"></param>
    /// <returns></returns>
    public virtual bool AddString(string nome)
    {
        return AddAtributo("String", nome);
    }

    /// <summary>
    ///     Adiciona um atributo com seus getter e setter do tipo float
    /// </summary>
    /// <param name="nome"></param>
    /// <returns></returns>
    public virtual bool AddFloat(string nome)
    {
        return AddAtributo("float", nome);
    }

    /// <summary>
    ///     Adiciona um atributo com seus getter e setter do tipo double
    /// </summary>
    /// <param name="nome"></param>
    /// <returns></returns>
    public virtual bool AddDouble(string nome)
    {
        return AddAtributo("double", nome);
    }

    /// <summary>
    ///     Adiciona vários atributos com seus getters e setters do tipo int
    /// </summary>
    /// <param name="nomes"></param>
    /// <returns></returns>
    public virtual int AddInteiros(params string[] nomes)
    {
        //conta quantos atributos do tipo inteiro foram adicionados com sucesso
        var sucesso = 0;
        foreach (var nome in nomes)
        {
            var resultado = AddInteiro(nome);
            if (resultado) sucesso++;
        }

        return sucesso;
    }

    /// <summary>
    ///     Adiciona vários atributos com seus getters e setters do tipo String
    /// </summary>
    /// <param name="nomes"></param>
    /// <returns>quantos atributos foram adicionados com sucesso</returns>
    public virtual int AddStrings(params string[] nomes)
    {
        //conta quantos atributos do tipo inteiro foram adicionados com sucesso
        var sucesso = 0;
        foreach (var nome in nomes)
        {
            var resultado = AddString(nome);
            if (resultado) sucesso++;
        }

        return sucesso;
    }

    /// <summary>
    ///     Adiciona vários atributos com seus getters e setters do tipo float
    /// </summary>
    /// <param name="nomes"></param>
    /// <returns></returns>
    public virtual int AddFloats(params string[] nomes)
    {
        //conta quantos atributos do tipo inteiro foram adicionados com sucesso
        var sucesso = 0;
        foreach (var nome in nomes)
        {
            var resultado = AddFloat(nome);
            if (resultado) sucesso++;
        }

        return sucesso;
    }

    /// <summary>
    ///     Adiciona vários atributos com seus getters e setters do tipo double
    /// </summary>
    /// <param name="nomes"></param>
    /// <returns></returns>
    public virtual int AddDoubles(params string[] nomes)
    {
        //conta quantos atributos do tipo inteiro foram adicionados com sucesso
        var sucesso = 0;
        foreach (var nome in nomes)
        {
            var resultado = AddDouble(nome);
            if (resultado) sucesso++;
        }

        return sucesso;
    }

    /// <summary>
    ///     Adiciona um construtor para os atributos passados
    /// </summary>
    /// <param name="atributos">lista de atributos</param>
    /// <returns></returns>
    public virtual bool AddConstrutorPara(params string[] atributos)
    {
        var constructorBuilder = new ConstructorBuilder("public", GetName());

        /// <summary>
        /// Para cada nome de atributo passado, eu pego o objeto Atributo
        /// associado a ele. Em seguida, pego um setter associado ao atributo e
        /// coloco dentro o corpo do construtor
        /// </summary>
        foreach (var atr in atributos)
        {
            AttributeBuilder at = GetAttribute(atr);
            constructorBuilder.AddParameters(at.GetTipo(), at.GetName());
            MethodBuilder setter = GetSetter(atr);
            constructorBuilder.AddCorpo(setter.GetCall(atr));
        }

        return base.AddConstrutor(constructorBuilder);
    }

    public static ModelBuilder AddModelo(ModelBuilder modelBuilder)
    {
        return (ModelBuilder)AddClassBuilder(modelBuilder);
    }

    public static ModelBuilder GetModelo(string nome)
    {
        var c = GetStaticCall(nome);
        if (c is ModelBuilder)
            return (ModelBuilder)c;
        return null;
    }
}