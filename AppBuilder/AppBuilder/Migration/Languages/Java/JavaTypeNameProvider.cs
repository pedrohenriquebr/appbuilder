using Api.Migration.Core;

namespace Api.Migration.Languages.Csharp;

public class CsharpTypeNameProvider : ITypeNameProvider
{
    private readonly Dictionary<string, string> _names;

    public CsharpTypeNameProvider()
    {
        _names = new Dictionary<string, string>()
        {
            ["System.Int32"] = "int",
            ["System.String"] = "string"
        };
    }

    public bool Has(string fullName) => _names.ContainsKey(fullName);
    public string Get(string fullName) => _names[fullName];
}