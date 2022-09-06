using Api.Common;

namespace Api.Migration.Core;

public abstract class Modifier : Enumeration, ISyntaxElement

{
    public static readonly Modifier Public = new PublicModifier();
    public static readonly Modifier Private = new PrivateModifier();
    public static readonly Modifier Static = new StaticModifier();
    public static readonly Modifier Protected = new ProtectedModifier();
    public static readonly Modifier Readonly = new ReadonlyModifier();
    public static readonly Modifier Const = new ConstModifier();

    protected Modifier(int i, string name) : base(i, name)
    {
    }


    public abstract R Accept<R>(in ISyntaxVisitor<R> visitor);
}

public class ConstModifier : Modifier
{
    public ConstModifier() : base(6, "const")
    {
    }

    public override R Accept<R>(in ISyntaxVisitor<R> visitor)
    {
        return visitor.Visit(this);
    }
}

public class ReadonlyModifier : Modifier
{
    public ReadonlyModifier() : base(5, "readonly")
    {
    }

    public override R Accept<R>(in ISyntaxVisitor<R> visitor)
    {
        return visitor.Visit(this);
    }
}

public class ProtectedModifier : Modifier
{
    public ProtectedModifier() : base(4, "protected")
    {
        
    }

    public override R Accept<R>(in ISyntaxVisitor<R> visitor)
    {
        return visitor.Visit(this);
    }
}

public class StaticModifier : Modifier
{
    public StaticModifier() : base(3, "static")
    {
    }

    public override R Accept<R>(in ISyntaxVisitor<R> visitor)
    {
        return visitor.Visit(this);
    }
}

public class PublicModifier : Modifier
{
    public PublicModifier() : base(1, "public")
    {
    }

    public override R Accept<R>(in ISyntaxVisitor<R> visitor)
    {
        return visitor.Visit(this);
    }
}

public class PrivateModifier : Modifier
{
    public PrivateModifier() : base(2, "private")
    {
    }

    public override R Accept<R>(in ISyntaxVisitor<R> visitor)
    {
        return visitor.Visit(this);
    }
}
