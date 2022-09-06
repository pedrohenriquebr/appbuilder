namespace Api.Migration.Core;

public class VarDeclaration : ISyntaxElement
{
    public string Name { get; set; }
    public string Value { get; set; }
    public bool HasTypeInference { get; set; }

    private string _typename = "";

    public string Type
    {
        get => _typename;
        set
        {
            _typename = value;
            HasTypeInference = false;
        }
    }

    public List<Modifier> Modifiers { get; set; }

    public VarDeclaration()
    {
        Modifiers = new List<Modifier>();
        HasTypeInference = true;
    }

    public VarDeclaration(string name) : this()
    {
        this.Name = name;
    }

    public R Accept<R>(in ISyntaxVisitor<R> visitor)
    {
        return visitor.Visit(this);
    }

}