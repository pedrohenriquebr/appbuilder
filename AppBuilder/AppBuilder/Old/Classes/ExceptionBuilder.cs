/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

namespace Api.Old.Classes;

/// <summary>
/// </summary>
/// <remarks>@authorpsilva</remarks>
public class ExceptionBuilder : ClassBuilder
{
    public ExceptionBuilder(string nome) : base(nome)
    {
        if (!nome.EndsWith("Exception")) SetName(nome + "Exception"); //delegar todos os construtores
    }

    public ExceptionBuilder(string nome, string pacote) : this(nome)
    {
        packageBuilder = new PackageBuilder(pacote);
    }

    public ExceptionBuilder(string nome, string pacote, string caminho) : this(nome)
    {
        packageBuilder = new PackageBuilder(pacote, caminho);
    }

    public virtual void DelegarConstrutores()
    {
        foreach (var constructorBuilder in base.GetConstructors())
        {
            var codigo = "";

            // indica a posição do parâmetro
            var contador = 1;
            foreach (ParameterBuilder param in constructorBuilder.GetParameters())
            {
                // toda vez que chegar no próximo parâmetro, colocar uma vírgula
                if (contador % 2 == 0)
                {
                    codigo += ",";
                    contador = 1;
                }


                // coloca o parâmetro
                codigo += param.GetReferencia();
                contador++;
            }

            constructorBuilder.AddCorpo("super(" + codigo + ")");
        }
    }

    public static ExceptionTreatmentBuilder Tratar(ClassBuilder classBuilder, params string[] exceções)
    {
        ExceptionTreatmentBuilder tratamento = new ExceptionTreatmentBuilder(classBuilder);
        foreach (var exp in exceções) tratamento.AddExceção(exp);

        return tratamento;
    }

    public static string Lançar(ClassBuilder classBuilder, string nome, params string[] args)
    {
        var codigo = "";
        codigo = "throw ";
        var exp = (ExceptionBuilder)classBuilder.GetClasse(nome);
        ObjectBuilder obj = exp.GetInstancia(args);
        codigo += obj.GetInstancia() + ";";
        return codigo;
    }
}