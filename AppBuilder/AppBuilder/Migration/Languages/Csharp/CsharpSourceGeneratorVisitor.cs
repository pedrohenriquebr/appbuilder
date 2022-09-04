using Api.Migration.Core;
using AppBuilder.Tests.Java;

namespace Api.Migration.Languages.Csharp;

public class CsharpSourceGeneratorVisitor : BaseSourceGeneratorVisitor
{
    public CsharpSourceGeneratorVisitor(ISyntaxTokenProvider tokenProvider) : base(tokenProvider)
    {
    }
}