using Api.Migration.Core;
using Api.Migration.Languages.Csharp;

namespace AppBuilder.Tests.Csharp;

public class SourceGeneratorTests : IClassFixture<CsharpTokenProvider>
{
    private readonly CsharpSourceGeneratorVisitor _visitor;

    public SourceGeneratorTests(CsharpTokenProvider tokenProvider)
    {
        _visitor = new(tokenProvider);
    }

    [Theory]
    [MemberData(nameof(SourceGeneratorFaker.GenerateVar), parameters: new object[] { "myVar" }, MemberType = typeof(SourceGeneratorFaker))]
    public void Should_Generate_Var_Declaration(VarDeclaration input)
    {
        string expect = "var myVar;";
        Assert.Equal(expect, input.Accept(_visitor));
    }


    [Theory]
    [MemberData(nameof(SourceGeneratorFaker.GenerateVarWithType), parameters: new object[] { "int" }, MemberType = typeof(SourceGeneratorFaker))]
    [MemberData(nameof(SourceGeneratorFaker.GenerateVarWithType), parameters: new object[] { "string" }, MemberType = typeof(SourceGeneratorFaker))]
    [MemberData(nameof(SourceGeneratorFaker.GenerateVarWithType), parameters: new object[] { "Guid" }, MemberType = typeof(SourceGeneratorFaker))]
    [MemberData(nameof(SourceGeneratorFaker.GenerateVarWithType), parameters: new object[] { "double" }, MemberType = typeof(SourceGeneratorFaker))]
    [MemberData(nameof(SourceGeneratorFaker.GenerateVarWithType), parameters: new object[] { "decimal" }, MemberType = typeof(SourceGeneratorFaker))]
    public void Should_Generate_Var_With_Type(VarDeclaration input, string expected)
    {
        Assert.Equal(expected, input.Accept(_visitor));
    }


    [Theory]
    [MemberData(nameof(SourceGeneratorFaker.GenerateVarWithModifier), parameters: new object[] { "public", "int" }, MemberType = typeof(SourceGeneratorFaker))]
    [MemberData(nameof(SourceGeneratorFaker.GenerateVarWithModifier), parameters: new object[] { "protected", "double" }, MemberType = typeof(SourceGeneratorFaker))]
    [MemberData(nameof(SourceGeneratorFaker.GenerateVarWithModifier), parameters: new object[] { "private", "long" }, MemberType = typeof(SourceGeneratorFaker))]
    [MemberData(nameof(SourceGeneratorFaker.GenerateVarWithModifier), parameters: new object[] { "readonly", "string" }, MemberType = typeof(SourceGeneratorFaker))]
    [MemberData(nameof(SourceGeneratorFaker.GenerateVarWithModifier), parameters: new object[] { "const", "decimal" }, MemberType = typeof(SourceGeneratorFaker))]
    public void Should_Generate_Var_With_Modifier(VarDeclaration input, string expected)
    {
        Assert.Equal(expected, input.Accept(_visitor));
    }
    
    
    [Theory]
    [MemberData(nameof(SourceGeneratorFaker.GenerateVarWithModifiers), MemberType = typeof(SourceGeneratorFaker))]
    public void Should_Generate_Var_With_Modifiers(VarDeclaration input, string expected)
    {
        Assert.Equal(expected, input.Accept(_visitor));
    }
}