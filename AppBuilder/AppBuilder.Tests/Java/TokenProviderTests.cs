using Api.Migration.Core;
using Api.Migration.Languages.Java;

namespace AppBuilder.Tests.Java;

public class TokenProviderTests : IClassFixture<Java18TokenProvider>
{
    private readonly Java18TokenProvider _tokenProvider;

    public TokenProviderTests(Java18TokenProvider tokenProvider)
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

        data.Add(Modifier.Const, "final");
        data.Add(Modifier.Private, "private");
        data.Add(Modifier.Protected, "protected");
        data.Add(Modifier.Public, "public");
        data.Add(Modifier.Readonly, "");
        data.Add(Modifier.Static, "static");

        return data;
    }
}