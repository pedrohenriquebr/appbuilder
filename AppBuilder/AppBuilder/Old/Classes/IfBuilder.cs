/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

namespace Api.Old.Classes;

/// <summary>
/// </summary>
/// <remarks>@authorpsilva</remarks>
public class IfBuilder
{
    private string corpo = "";
    private readonly string expressão = "";

    public IfBuilder(string expressão)
    {
        this.expressão = expressão;
    }

    public virtual void AddCorpo(string corpo)
    {
        this.corpo += corpo;
    }

    public virtual string ToString()
    {
        var codigo = "";
        codigo += "if( " + expressão + " ){\\n";
        codigo += "\\t\\t" + corpo;
        codigo += "\\n\\t}\\n\\n";
        return codigo;
    }
}