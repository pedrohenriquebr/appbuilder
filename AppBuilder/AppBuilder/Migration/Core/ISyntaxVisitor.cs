using System.Linq.Expressions;

namespace Api.Migration.Core;

public interface ISyntaxVisitor<out TReturn>
{
    #region Var

    public TReturn Visit(in VarDeclaration varDeclaration);

    #endregion

    #region Modifiers

    public TReturn Visit(ConstModifier constModifier);
    public TReturn Visit(ReadonlyModifier readonlyModifier);
    public TReturn Visit(ProtectedModifier protectedModifier);
    public TReturn Visit(in StaticModifier visitor);
    public TReturn Visit(PublicModifier publicModifier);
    public TReturn Visit(PrivateModifier privateModifier);

    #endregion
}