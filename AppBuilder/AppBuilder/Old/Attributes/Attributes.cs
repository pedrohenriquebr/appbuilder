namespace Api.Old.Attributes;

public sealed class Attributes
{
    public static readonly int OVERRIDE = 255;

    public static bool HasAttribute(int value, int attribute)
    {
        return (value & attribute) == attribute;
    }
}