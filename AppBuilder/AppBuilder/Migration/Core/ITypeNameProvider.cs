namespace Api.Migration.Core;

public interface ITypeNameProvider
{
    public bool Has(string fullName);
    public string Get(string fullName);
}