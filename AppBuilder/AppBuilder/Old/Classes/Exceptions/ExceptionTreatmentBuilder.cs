/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

using Api.Old.vars;

namespace Api.Old.Classes.Exceptions;

/// <summary>
/// </summary>
/// <remarks>@authorpsilva</remarks>
public class ExceptionTreatmentBuilder
{
    private readonly ClassBuilder classBuilder;
    private string corpo = "";
    private readonly IList<ExceptionBuilder> exceptions = new List<ExceptionBuilder>();

    public ExceptionTreatmentBuilder(ClassBuilder classBuilder)
    {
        this.classBuilder = classBuilder;
    }

    public virtual void addBody(string codigo)
    {
        corpo += codigo;
    }

    public virtual void SetBody(string corpo)
    {
        this.corpo = corpo;
    }

    public virtual string GetBody()
    {
        return corpo;
    }

    public virtual bool AddExceção(string nome)
    {
        var cl = ClassBuilder.GetStaticCall(classBuilder.GetNomeCompleto(nome));
        if (cl is ExceptionBuilder) return exceptions.Add((ExceptionBuilder)cl);

        return false;
    }

    public virtual string ToString()
    {
        var codigo = "";
        codigo += "try{ \\n\\n";
        codigo += "\\t" + corpo;
        codigo += "\\n\\t}";
        foreach (var e in exceptions)
        {
            VarBuilder var = new VarBuilder(e.GetName(), "exp");
            var.SetClasse(classBuilder);
            codigo += "catch(" + var.GetTipo() + " " + var.GetName() + "){\\n\\n";
            codigo += "\\t" + var.Call("printStackTrace") + ";";
            codigo += "\\n}";
        }

        return codigo;
    }
}