namespace Api.Old.vars;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/// <summary>
///     Atributo é sinônimo de variável de instância
/// </summary>
/// <remarks>@authoraluno</remarks>
public class MemberBuilder : VarBuilder
{
    private bool estático;
    private bool inicializar;
    private string modAcesso = "public";

    public MemberBuilder(string tipo, string nome) : base(tipo, nome)
    {
        //por padrão
        AddModificador(modAcesso);
    }

    public MemberBuilder(string modificador, string tipo, string nome) : base(modificador, tipo, nome, "")
    {
    }

    public MemberBuilder(string modificador, string tipo, string nome, string valor) : base(modificador, tipo, nome,
        valor)
    {
        classBuilder = ClassBuilder.GetStaticCall(tipo);
    }

    public virtual string GetModAcesso()
    {
        return modAcesso;
    }

    public virtual void SetModAcesso(string mod)
    {
        modAcesso = mod;
    }

    public virtual void AtivarInicialização(string valor)
    {
        SetValor(valor);
        inicializar = true;
    }

    public virtual void DesativarInicialização()
    {
        inicializar = false;
    }

    public override string GetReferencia()
    {
        if (GetMods().Contains("static"))
        {
            estático = true;
            return GetClasse().GetName() + "." + nome;
        }

        return "this." + nome;
    }

    public override string GetInicialização(string valor)
    {
        return GetReferencia() + " = " + valor + ";\\n";
    }

    public override string GetDeclaração()
    {
        var codigo = "";
        foreach (var mod in mods) codigo += mod + " ";

        codigo += tipo + " " + nome;
        if (inicializar) codigo += " = " + valor;

        codigo += ";\\n";
        return codigo;
    }

    public override string ToString()
    {
        return GetDeclaração();
    }
}