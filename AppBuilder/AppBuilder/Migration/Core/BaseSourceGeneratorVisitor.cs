using System.Text;

namespace Api.Migration.Core;

public abstract class BaseSourceGeneratorVisitor: ISyntaxVisitor<string>
{
    protected readonly ISyntaxTokenProvider TokenProvider;

    protected BaseSourceGeneratorVisitor(ISyntaxTokenProvider tokenProvider)
    {
        TokenProvider = tokenProvider;
    }

    #region Var

    public virtual string Visit(in VarDeclaration varDeclaration)
    {
        var builder  = new StringBuilder();
        if (varDeclaration.Modifiers.Any())
        {
            foreach (var mod in varDeclaration.Modifiers)
            {
                builder.Append(mod.Accept(this));
            }
        }

        builder.Append(GenerateVarDeclaration(varDeclaration));

        return builder.ToString();
    }

    public virtual string Visit(ConstModifier constModifier)
    {
        return TokenProvider.GetModifierToken(constModifier) + " ";
    }


    public virtual string Visit(ReadonlyModifier readonlyModifier)
    {
        return TokenProvider.GetModifierToken(readonlyModifier) + " ";
    }


    public virtual string Visit(ProtectedModifier protectedModifier)
    {
        return TokenProvider.GetModifierToken(protectedModifier) + " ";
    }


    public virtual string Visit(in StaticModifier staticModifier)
    {
        return TokenProvider.GetModifierToken(staticModifier) + " ";
    }


    public virtual string Visit(PublicModifier publicModifier)
    {
        return TokenProvider.GetModifierToken(publicModifier) + " ";
    }


    public virtual string Visit(PrivateModifier privateModifier)
    {
        return TokenProvider.GetModifierToken(privateModifier) + " ";
    }


    public string GenerateVarDeclaration(in VarDeclaration varDeclaration)
    {
        if (varDeclaration.HasTypeInference)
            return $"{TokenProvider.GetTypeInferenceToken()} {varDeclaration.Name};";
        return $"{varDeclaration.Type} {varDeclaration.Name};";
    }

    #endregion


}