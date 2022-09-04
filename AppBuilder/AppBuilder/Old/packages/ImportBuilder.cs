namespace Api.Old.packages;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/// <summary>
/// </summary>
/// <remarks>@authorPedro Henrique Braga da Silva</remarks>
public class ImportBuilder
{
    private PackageBuilder caminho;
    private readonly string classe;

    public ImportBuilder(string classe, string caminho)
    {
        this.caminho = new PackageBuilder(caminho);
        this.classe = classe;
    }

    public virtual string GetCaminho()
    {
        return caminho.GetCaminho();
    }

    public virtual void SetCaminho(string caminho)
    {
        if (caminho.Contains("."))
        {
            var pacotes = caminho.Split("\\\\.");
            var pacote = pacotes[pacotes.Length - 1];
            caminho = caminho.Replace("." + pacote, "");
            this.caminho = new PackageBuilder(pacote, caminho);
        }
        else
        {
            this.caminho = new PackageBuilder(caminho);
        }
    }

    public virtual string GetClasse()
    {
        return classe;
    }

    public virtual string ToString()
    {
        return "import " + caminho.GetCaminho() + "." + classe;
    }
}