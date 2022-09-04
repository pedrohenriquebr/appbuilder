/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

using Api.Old.Classes;

namespace Api.Old.vars;

/// <summary>
///     Define a classe Objeto como se fosse uma variável local
/// </summary>
/// <remarks>@authorpsilva</remarks>
public class ObjectBuilder
{
    private ClassBuilder classBuilder;
    private string instancia;

    public ObjectBuilder(ClassBuilder classBuilder)
    {
        SetClasse(classBuilder);
        instancia = "";
    }

    public virtual void SetClasse(ClassBuilder classBuilder)
    {
        this.classBuilder = classBuilder;
    }

    public virtual ClassBuilder GetClasse()
    {
        return classBuilder;
    }

    /// <summary>
    ///     Chamar um método
    /// </summary>
    /// <param name="nome"></param>
    /// <param name="args"></param>
    /// <returns></returns>
    public virtual string Call(string nome, params string[] args)
    {
        if (classBuilder.GetSuperClasse() != null)
        {
            if (!classBuilder.GetSuperClasse().HasMethod(nome) && !classBuilder.HasMethod(nome))
                throw new Exception("classe " + classBuilder.GetName() + " não tem método " + nome);
        }
        else
        {
            if (!classBuilder.HasMethod(nome))
                throw new Exception("classe " + classBuilder.GetName() + " não tem método " + nome);
        }

        return "." + classBuilder.GetMethodBuilder(nome).GetCall(args);
    }

    /// <summary>
    ///     Acessar um método
    /// </summary>
    /// <param name="nome"></param>
    /// <returns></returns>
    public virtual string Get(string nome)
    {
        return "." + classBuilder.GetAttribute(nome).GetName();
    }

    /// <summary>
    ///     Define os argumentos para o objeto
    /// </summary>
    /// <param name="args"></param>
    public virtual void SetInstancia(params string[] args)
    {
        var codigo = "";
        if (classBuilder.IsHasGenerics())
            codigo += "new " + classBuilder.GetName() + "<>(";
        else
            codigo += "new " + classBuilder.GetName() + "(";

        var contador = 1;
        foreach (var arg in args)
        {
            if (contador % 2 == 0)
            {
                codigo += ",";
                contador = 1;
            }

            codigo += arg;
            contador++;
        }

        codigo += ")";
        instancia = codigo;
    }

    public virtual string GetInstancia()
    {
        return instancia;
    }

    public virtual string GetTipo()
    {
        return classBuilder.GetName();
    }

    public static ObjectBuilder Instancia(string nome, params string[] args)
    {
        return ClassBuilder.Get(nome, args);
    }

    public override string ToString()
    {
        return GetInstancia();
    }
}