/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

namespace Api.Old.Classes;

/// <summary>
/// </summary>
/// <remarks>@authorpsilva</remarks>
public class While
{
    private string condição = "";
    private string corpo = "";

    public While(string condição)
    {
        this.condição = condição;
    }

    public virtual string GetCondição()
    {
        return condição;
    }

    public virtual void SetCondição(string condição)
    {
        this.condição = condição;
    }

    public virtual void AddCorpo(string corpo)
    {
        this.corpo += MethodBuilder.FormatCode(corpo);
    }

    public virtual string GetCorpo()
    {
        return corpo;
    }

    public virtual void SetCorpo(string corpo)
    {
        this.corpo = MethodBuilder.FormatCode(corpo);
    }

    public virtual string ToString()
    {
        var codigo = "";
        codigo += "while(" + condição + "){\\n\\n";
        codigo += "\\t" + corpo;
        codigo += "}\\n";
        return codigo;
    }
}