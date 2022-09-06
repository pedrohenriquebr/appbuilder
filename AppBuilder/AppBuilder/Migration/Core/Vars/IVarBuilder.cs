namespace Api.Migration.Core.Vars;

public interface IVarBuilder
{
    public IVarBuilder New();
    public IVarBuilder New<T>(string name);
    public IVarBuilder New(string name);
    public IVarBuilder WithType<T>();
    public IVarBuilder WithType(Type type);
    public IVarBuilder WithType(string type);
    public IVarBuilder WithName(string name);
    public IVarBuilder WithValue(string value);
    public IVarBuilder AddModifier(Modifier name);
    public IVarBuilder Const();
    public IVarBuilder Var();
    public IVarBuilder ReadOnly();
    public IVarBuilder Public();
    public IVarBuilder Private();
    public IVarBuilder Static();
    public IVarBuilder Protected();
    public VarDeclaration Build();
}