namespace Api.Migration.Core;

public interface ISyntaxVisitor
{
    #region Var

    public void VisitVarDeclaration(in VarDeclaration varDeclaration);

    #endregion

    #region Modifiers

    public void VisitConstModifier(ConstModifier constModifier);
    public void VisitReadonlyModifier(ReadonlyModifier readonlyModifier);
    public void VisitProtectedModifier(ProtectedModifier protectedModifier);
    public void VisitStaticModifier(in StaticModifier visitor);
    public void VisitPublicModifier(PublicModifier publicModifier);
    public void VisitPrivateModifier(PrivateModifier privateModifier);

    #endregion
}