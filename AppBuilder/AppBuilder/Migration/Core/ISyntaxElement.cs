namespace Api.Migration.Core;

public interface ISyntaxElement
{
    public void Accept(in ISyntaxVisitor visitor);
}