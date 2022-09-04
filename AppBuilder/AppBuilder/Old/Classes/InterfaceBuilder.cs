/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

namespace Api.Old.Classes;

/// <summary>
/// </summary>
/// <remarks>@authorpsilva</remarks>
public class InterfaceBuilder : ClassBuilder
{
    public InterfaceBuilder(string nome) : base(nome)
    {
        SetInterface(true);
    }

    public InterfaceBuilder(string nome, string pacote) : this(nome)
    {
        packageBuilder = new PackageBuilder(pacote);
    }

    public InterfaceBuilder(string name, string _package, string path) : this(name)
    {
        packageBuilder = new PackageBuilder(_package, path);
    }

    public override bool AddAttribute(AttributeBuilder atr)
    {
        return false;
    }

    public override bool AddMethod(MethodBuilder method)
    {
        method.SetBelongsInterface(true);
        return base.AddMethod(method);
    }
}