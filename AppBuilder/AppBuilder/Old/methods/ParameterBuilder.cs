namespace Api.Old.methods;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/// <summary>
/// </summary>
/// <remarks>@authorPedro Henrique Braga da Silva</remarks>
public class ParameterBuilder : VarBuilder
{
    public ParameterBuilder(string tipo, string nome) : base(tipo, nome)
    {
    }

    public override string GetDeclaração()
    {
        return this.tipo + " " + this.nome;
    }

    public override string ToString()
    {
        return tipo + " " + GetReferencia();
    }
}