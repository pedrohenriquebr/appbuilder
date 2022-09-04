namespace Api.Migration.Core;

public interface ISyntaxTokenProvider
{
    public string GetModifierToken(Modifier mod);
    public string GetTypeInferenceToken();
}