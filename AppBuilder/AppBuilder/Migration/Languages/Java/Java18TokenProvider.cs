using Api.Migration.Core;

namespace Api.Migration.Languages.Java;

public class Java18TokenProvider : ISyntaxTokenProvider
{
    private readonly Dictionary<Modifier, string> _modifiersTokens;
    private static string TypeInferenceToken { get; } = "var";

    public Java18TokenProvider()
    {
        _modifiersTokens = new Dictionary<Modifier, string>()
        {
            [Modifier.Const] = "final",
            [Modifier.Private] = "private",
            [Modifier.Readonly] = "",
            [Modifier.Protected] = "protected",
            [Modifier.Static] = "static",
            [Modifier.Public] = "public"
        };
    }
    
    public string GetModifierToken(Modifier mod)
    {
        return this._modifiersTokens[mod];
    }

    public string GetTypeInferenceToken()
    {
        return TypeInferenceToken;
    }
}