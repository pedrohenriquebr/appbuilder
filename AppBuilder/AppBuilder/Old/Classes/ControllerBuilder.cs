/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

namespace Api.Old.Classes;

/// <summary>
/// </summary>
/// <remarks>@authorpsilva</remarks>
public class ControllerBuilder : ClassBuilder
{
    private readonly AttributeBuilder daoVar;

    public ControllerBuilder(DaoBuilder dao, ModelBuilder modelBuilder) : base(modelBuilder.GetName() + "Controller")
    {
        AddImport(dao);
        daoVar = new AttributeBuilder("private", dao.GetName(), "dao");
        AddAttribute(daoVar);
    }
}