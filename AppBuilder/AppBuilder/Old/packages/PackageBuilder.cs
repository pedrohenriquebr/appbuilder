namespace Api.Old.packages;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/// <summary>
/// </summary>
/// <remarks>@authoraluno</remarks>
public class PackageBuilder
{
    private string caminho;
    private string nome;

    /// <summary>
    /// </summary>
    /// <param name="nome">nome do pacote. Ex: java</param>
    public PackageBuilder(string nome)
    {
        this.nome = nome.ToLowerCase();
        caminho = nome.ToLowerCase();
    }

    public PackageBuilder() : this("pacote")
    {
    }

    /// <summary>
    /// </summary>
    /// <param name="nome">nome do pacote. Ex: io</param>
    /// <param name="caminho">caminho completo do pacote. Ex: java.io, java.sql</param>
    public PackageBuilder(string nome, string caminho)
    {
        this.nome = nome;
        if (!caminho.Equals("")) this.caminho = caminho + "." + nome;
    }

    public virtual void SetNome(string nome)
    {
        this.nome = nome;
    }

    public virtual string GetNome()
    {
        return nome;
    }

    public virtual void SetCaminho(string caminho)
    {
        this.caminho = caminho;
    }

    public virtual string GetCaminho()
    {
        return caminho;
    }

    public virtual string ToString()
    {
        return "package " + caminho;
    }
}