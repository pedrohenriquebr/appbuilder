using Api.Migration.Core;

namespace AppBuilder.Tests.Csharp;

public static class SourceGeneratorFaker
{
    public static TheoryData<VarDeclaration, string> GenerateVarWithModifiers()
    {
        var data = new TheoryData<VarDeclaration, string>();
        var builder = new VarBuilder();
        data.Add(
            builder
                .New("number")
                .WithType<int>()
                .Build(),
            "int number;");

        data.Add(
            builder
                .New("random")
                .Build(),
            "var random;");
        
        data.Add(
            builder
                .New("_static")
                .WithType<string>()
                .AddModifier(Modifier.Const)
                .Build(),
            "const string _static;");
        
        data.Add(
            builder
                .New("time")
                .WithType<DateTime>()
                .Build(),
            "DateTime time;"
            );
        
        
        return data;
    }

    public static TheoryData<VarDeclaration, string> GenerateVarWithModifier(string mod, string type)
    {
        var data = new TheoryData<VarDeclaration, string>();

        data.Add(
            new VarBuilder()
                .New("opa")
                .AddModifier(mod switch
                {
                    "public" => Modifier.Public,
                    "protected" => Modifier.Protected,
                    "private" => Modifier.Private,
                    "readonly" => Modifier.Readonly,
                    "const" => Modifier.Const,
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
            new VarBuilder()
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
            new VarBuilder().New(name)
                .Var()
                .Build()
        };
    }
}