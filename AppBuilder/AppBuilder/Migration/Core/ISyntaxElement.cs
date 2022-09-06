namespace Api.Migration.Core;

public interface ISyntaxElement
{
    public R Accept<R>(in ISyntaxVisitor<R> visitor);
}