using Api.Migration.Core;
using Api.Migration.Languages.Java;

namespace AppBuilder.Tests.Java;

public class SourceGeneratorTests : IClassFixture<Java18TokenProvider>
{
    private readonly Java18SourceGeneratorVisitorVisitor _visitor;

    public SourceGeneratorTests(Java18TokenProvider tokenProvider)
    {
        _visitor = new(tokenProvider);
    }
    
    [Theory]
    [MemberData(nameof(GenerateVar), parameters: new object[] { "myVar" })]
    public void Should_Generate_Var_Declaration(VarDeclaration input)
    {
        string expect = "var myVar;";
        input.Accept(_visitor);

        Assert.Equal(expect, _visitor.GenerateSource());
    }


    [Theory]
    [MemberData(nameof(GenerateVarWithType), parameters: new object[] { "int" })]
    [MemberData(nameof(GenerateVarWithType), parameters: new object[] { "String" })]
    [MemberData(nameof(GenerateVarWithType), parameters: new object[] { "Calendar" })]
    [MemberData(nameof(GenerateVarWithType), parameters: new object[] { "double" })]
    [MemberData(nameof(GenerateVarWithType), parameters: new object[] { "decimal" })]
    public void Should_Generate_Var_With_Type(VarDeclaration input, string expected)
    {
        input.Accept(_visitor);
        Assert.Equal(expected, _visitor.GenerateSource());
    }


    [Theory]
    [MemberData(nameof(GenerateVarWithModifier), parameters: new object[] { "public", "int" })]
    [MemberData(nameof(GenerateVarWithModifier), parameters: new object[] { "protected", "double" })]
    [MemberData(nameof(GenerateVarWithModifier), parameters: new object[] { "private", "long" })]
    [MemberData(nameof(GenerateVarWithModifier), parameters: new object[] { "final", "string" })]
    public void Should_Generate_Var_With_Modifier(VarDeclaration input, string expected)
    {
        input.Accept(_visitor);
        Assert.Equal(expected, _visitor.GenerateSource());
    }

    public static TheoryData<VarDeclaration, string> GenerateVarWithModifier(string mod, string type)
    {
        var data = new TheoryData<VarDeclaration, string>();

        data.Add(
            new VarBuilder(new JavaTypeNameProvider())
                .New("opa")
                .AddModifier(mod switch
                {
                    "public" => Modifier.Public,
                    "protected" => Modifier.Protected,
                    "private" => Modifier.Private,
                    "final" => Modifier.Const,
                    _ => Modifier.Readonly
                })
                .WithType(type)
                .Build(),
            $"{mod} {type} opa;");

        return data;
    }

    public static TheoryData<VarDeclaration, string> GenerateVarWithType(string type)
    {
        var data = new TheoryData<VarDeclaration, string>();

        data.Add(
            new VarBuilder(new JavaTypeNameProvider())
                .New("opa")
                .WithType(type)
                .Build(),
            $"{type} opa;");

        return data;
    }

    public static TheoryData<VarDeclaration> GenerateVar(string name)
    {
        return new TheoryData<VarDeclaration>()
        {
            new VarBuilder(new JavaTypeNameProvider()).New(name)
                .Var()
                .Build()
        };
    }
}