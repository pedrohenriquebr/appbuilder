using Api.Migration.Core;
using Api.Migration.Languages.Csharp;

namespace AppBuilder.Tests.Csharp;

public class TokenProviderTests : IClassFixture<CsharpTokenProvider>
{
    private readonly CsharpTokenProvider _tokenProvider;

    public TokenProviderTests(CsharpTokenProvider tokenProvider)
    {
        _tokenProvider = tokenProvider;
    }

    [Theory]
    [MemberData(nameof(GenerateModifier))]
    public void Should_Returns_The_Right_Modifier_Token(Modifier mod, string expected)
    {
        var result = _tokenProvider.GetModifierToken(mod);

        Assert.Equal(expected, result);
    }

    public static TheoryData<Modifier, string> GenerateModifier()
    {
        var data = new TheoryData<Modifier, string>();

        data.Add(Modifier.Const, "const");
        data.Add(Modifier.Private, "private");
        data.Add(Modifier.Protected, "protected");
        data.Add(Modifier.Public, "public");
        data.Add(Modifier.Readonly, "readonly");
        data.Add(Modifier.Static, "static");

        return data;
    }
}