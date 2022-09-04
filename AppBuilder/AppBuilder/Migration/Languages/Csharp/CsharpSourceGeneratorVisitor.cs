using Api.Migration.Core;

namespace Api.Migration.Languages.Csharp;

public class CsharpSourceGeneratorVisitor : BaseSourceGeneratorVisitor
{
    public CsharpSourceGeneratorVisitor(ISyntaxTokenProvider tokenProvider) : base(tokenProvider)
    {
    }
}