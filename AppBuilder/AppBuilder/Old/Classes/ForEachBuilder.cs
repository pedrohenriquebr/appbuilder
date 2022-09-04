/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

namespace Api.Old.Classes;

/// <summary>
/// </summary>
/// <remarks>@authorpsilva</remarks>
public class ForEachBuilder
{
    private string corpo = "";
    private VarBuilder elemento;
    private VarBuilder lista;

    public ForEachBuilder(VarBuilder elemento, VarBuilder lista)
    {
        this.elemento = elemento;
        this.lista = lista;
    }

    public virtual string ToString()
    {
        var codigo = "";
        codigo += "for(" + elemento.GetTipo() + " " + elemento.GetName();
        codigo += " : " + lista.GetReferencia() + "){\\n";
        codigo += corpo;
        codigo += "}";
        return codigo;
    }

    public virtual string GetCorpo()
    {
        return corpo;
    }

    public virtual void SetCorpo(string corpo)
    {
        this.corpo = corpo;
    }

    public virtual VarBuilder GetElemento()
    {
        return elemento;
    }

    public virtual void SetElemento(VarBuilder elemento)
    {
        this.elemento = elemento;
    }

    public virtual VarBuilder GetLista()
    {
        return lista;
    }

    public virtual void SetLista(VarBuilder lista)
    {
        this.lista = lista;
    }
}