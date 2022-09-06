using Api.Migration.Core;
using Api.Migration.Core.Vars;
using Api.Migration.Languages.Csharp;

namespace AppBuilder.Tests.Csharp;

public static class SourceGeneratorFaker
{
    public static TheoryData<VarDeclaration, string> GenerateVarWithModifiers()
    {
        var data = new TheoryData<VarDeclaration, string>();
        var builder = new VarBuilder(new CsharpTypeNameProvider());
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

        data.Add(
            builder
                .New("time")
                .Const()
                .WithType<string>()
                .Build(),
            "const string time;"
            );
        

        data.Add(
            builder
                .New<int>("number_static")
                .Static()
                .Build(),
            "static int number_static;"
            );
        
        return data;
    }

    public static TheoryData<VarDeclaration, string> GenerateVarWithModifier(string mod, string type)
    {
        var data = new TheoryData<VarDeclaration, string>();

        data.Add(
            new VarBuilder(new CsharpTypeNameProvider())
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
            new VarBuilder(new CsharpTypeNameProvider())
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
            new VarBuilder(new CsharpTypeNameProvider()).New(name)
                .Var()
                .Build()
        };
    }
}