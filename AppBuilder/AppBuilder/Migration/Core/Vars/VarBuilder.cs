namespace Api.Migration.Core.Vars;

public class VarBuilder : IVarBuilder
{
    private VarDeclaration _varDeclaration;
    private ITypeNameProvider _typeNameProvider;
    public VarBuilder(ITypeNameProvider typeNameProvider)
    {
        _typeNameProvider = typeNameProvider;
        _varDeclaration = new VarDeclaration();
    }

    public IVarBuilder New()
    {
        _varDeclaration = new VarDeclaration();
        return this;
    }

    public IVarBuilder New(string name)
    {
        _varDeclaration = new VarDeclaration(name);
        return this;
    }

    public IVarBuilder WithType<T>()
    {
        return WithType(typeof(T));
    }

    public IVarBuilder WithType(Type type)
    {
        if (type.FullName != null && _typeNameProvider.Has(type.FullName))
            return WithType(_typeNameProvider.Get(type.FullName));
        return WithType(type.Name);
    }

    public IVarBuilder WithType(string type)
    {
        _varDeclaration.Type = type;
        return this;
    }

    public IVarBuilder WithName(string name)
    {
        _varDeclaration.Name = name;
        return this;
    }

    public IVarBuilder WithValue(string value)
    {
        _varDeclaration.Value = value;
        return this;
    }

    public IVarBuilder AddModifier(Modifier name)
    {
        _varDeclaration.Modifiers.Add(name);
        return this;
    }

    public IVarBuilder Const()
    {
        _varDeclaration.Modifiers.Add(Modifier.Const);
        return this;
    }

    public IVarBuilder Var()
    {
        _varDeclaration.HasTypeInference = true;
        return this;
    }

    public IVarBuilder ReadOnly()
    {
        _varDeclaration.Modifiers.Add(Modifier.Readonly);
        return this;
    }

    public VarDeclaration Build()
    {
        return _varDeclaration;
    }

    public IVarBuilder New<T>(string name)
    {
        return New(name)
            .WithType<T>();
    }

    public IVarBuilder Public()
    {
        return AddModifier(Modifier.Public);
    }

    public IVarBuilder Private()
    {
        return AddModifier(Modifier.Private);
    }

    public IVarBuilder Static()
    {
        return AddModifier(Modifier.Static);
    }

    public IVarBuilder Protected()
    {
        return AddModifier(Modifier.Protected);
    }
}