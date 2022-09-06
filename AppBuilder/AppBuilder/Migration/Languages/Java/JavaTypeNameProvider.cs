using Api.Migration.Core;

namespace Api.Migration.Languages.Java;

public class JavaTypeNameProvider : ITypeNameProvider
{
    private readonly Dictionary<string, string> _names;

    public JavaTypeNameProvider()
    {
        _names = new Dictionary<string, string>()
        {
            ["System.Int32"] = "int",
            ["System.String"] = "string",
            ["System.DateTime"] = "LocalDateTime"
        };
    }

    public bool Has(string fullName) => _names.ContainsKey(fullName);
    public string Get(string fullName) => _names[fullName];
}