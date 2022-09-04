using Api.Migration.Core;

namespace AppBuilder.Tests.Java;

public abstract class BaseSourceGeneratorVisitor : ISyntaxVisitor
{
    protected string SourceCode { get; set; }
    protected readonly ISyntaxTokenProvider TokenProvider;

    protected BaseSourceGeneratorVisitor(ISyntaxTokenProvider tokenProvider)
    {
        TokenProvider = tokenProvider;
        SourceCode = "";
    }

    #region Var

    public virtual void VisitVarDeclaration(in VarDeclaration varDeclaration)
    {
        SourceCode += GenerateVarDeclaration(varDeclaration);
    }

    public virtual void VisitConstModifier(ConstModifier constModifier)
    {
        SourceCode += TokenProvider.GetModifierToken(constModifier) + " ";
    }


    public virtual void VisitReadonlyModifier(ReadonlyModifier readonlyModifier)
    {
        SourceCode += TokenProvider.GetModifierToken(readonlyModifier) + " ";
    }


    public virtual void VisitProtectedModifier(ProtectedModifier protectedModifier)
    {
        SourceCode += TokenProvider.GetModifierToken(protectedModifier) + " ";
    }


    public virtual void VisitStaticModifier(in StaticModifier staticModifier)
    {
        SourceCode += TokenProvider.GetModifierToken(staticModifier) + " ";
    }


    public virtual void VisitPublicModifier(PublicModifier publicModifier)
    {
        SourceCode += TokenProvider.GetModifierToken(publicModifier) + " ";
    }


    public virtual void VisitPrivateModifier(PrivateModifier privateModifier)
    {
        SourceCode += TokenProvider.GetModifierToken(privateModifier) + " ";
    }


    public string GenerateVarDeclaration(in VarDeclaration varDeclaration)
    {
        if (varDeclaration.HasTypeInference)
            return $"{TokenProvider.GetTypeInferenceToken()} {varDeclaration.Name};";
        return $"{varDeclaration.Type} {varDeclaration.Name};";
    }

    #endregion


    public virtual string GenerateSource() => SourceCode;
}