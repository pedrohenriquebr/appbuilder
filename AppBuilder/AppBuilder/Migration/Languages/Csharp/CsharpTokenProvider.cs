using Api.Migration.Core;

namespace Api.Migration.Languages.Csharp;

public class CsharpTokenProvider : ISyntaxTokenProvider
{
    private readonly Dictionary<Modifier, string> _modifiersTokens;
    private static string TypeInferenceToken { get; } = "var";

    public CsharpTokenProvider()
    {
        _modifiersTokens = new Dictionary<Modifier, string>()
        {
            [Modifier.Const] = "const",
            [Modifier.Private] = "private",
            [Modifier.Readonly] = "readonly",
            [Modifier.Protected] = "protected",
            [Modifier.Static] = "static",
            [Modifier.Public] = "public"
        };
    }


    public string GetModifierToken(Modifier mod)
    {
        return _modifiersTokens[mod];
    }

    public string GetTypeInferenceToken()
    {
        return TypeInferenceToken;
    }
}