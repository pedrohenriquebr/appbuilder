/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

namespace Api.Old.Classes;

/// <summary>
/// </summary>
/// <remarks>@authorpsilva</remarks>
public class ConnectionFactory : ClassBuilder
{
    public ConnectionFactory(string pacote, string caminho) : base("ConnectionFactory", pacote, caminho)
    {
        AddImport("java.sql.SQLException");
        AddImport("java.sql.DriverManager");
        AddImport("java.sql.Connection");
        AttributeBuilder user = new AttributeBuilder("private", "String", "user");
        AttributeBuilder password = new AttributeBuilder("private", "String", "password");
        AttributeBuilder database = new AttributeBuilder("private", "String", "database");
        AttributeBuilder host = new AttributeBuilder("private", "String", "host");
        user.AddModificador("static");
        password.AddModificador("static");
        database.AddModificador("static");
        host.AddModificador("static");
        AddAttribute(user);
        AddAttribute(password);
        AddAttribute(database);
        AddAttribute(host);

        //inicializar
        //os atributos com seus devidos valores padrões
        user.AtivarInicialização("\\\"root\\\"");
        password.AtivarInicialização("\\\"root\\\"");
        database.AtivarInicialização("\\\"project\\\"");
        host.AtivarInicialização("\\\"localhost\\\"");
        MethodBuilder getConnection = new MethodBuilder("public", "static", "Connection", "getConnection");
        getConnection.AddException((ExceptionBuilder)GetClasse("SQLException"));
        getConnection.SetReturn(GetClasse("DriverManager").CallStatic("getConnection",
            "\\\"jdbc:mysql://\\\"+" + host.GetReferencia() + "+\\\"/\\\"+" + database.GetReferencia() + "",
            user.GetReferencia(), password.GetReferencia()));
        AddMethod(getConnection);
    }

    public virtual void SetServidor(string servidor)
    {
        GetAttribute("host").SetValor("\\\"" + servidor + "\\\"");
    }

    public virtual string GetServidor()
    {
        return GetAttribute("host").GetValor().Replace("\\\"", "");
    }

    public virtual void SetSenha(string senha)
    {
        GetAttribute("password").SetValor("\\\"" + senha + "\\\"");
    }

    public virtual string GetSenha()
    {
        return GetAttribute("password").GetValor().Replace("\\\"", "");
    }

    public virtual void SetBaseDeDados(string @base)
    {
        GetAttribute("database").SetValor("\\\"" + @base + "\\\"");
    }

    public virtual string GetBaseDeDados()
    {
        return GetAttribute("database").GetValor().Replace("\\\"", "");
    }

    public virtual void SetUsuário(string usuario)
    {
        GetAttribute("user").SetValor("\\\"" + usuario + "\\\"");
    }

    public virtual string GetUsuário()
    {
        return GetAttribute("user").GetValor().Replace("\\\"", "");
    }
}