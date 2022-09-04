namespace Api.Migration.Core;

public class VarDeclaration : ISyntaxElement
{
    public string Name { get; set; }
    public string Value { get; set; }
    public bool HasTypeInference { get; set; }
    public string Type { get; set; }
    public List<Modifier> Modifiers { get; set; }

    public VarDeclaration()
    {
        Modifiers = new List<Modifier>();
        HasTypeInference = true;
    }

    public VarDeclaration(string name)
    {
        this.Name = name;
        Modifiers = new List<Modifier>();
    }

    public void Accept(in ISyntaxVisitor visitor)
    {
        if (Modifiers.Any())
        {
            foreach (var mod in Modifiers)
            {
                mod.Accept(visitor);
            }
        }

        visitor.VisitVarDeclaration(this);
    }
}