/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

namespace Api.Old.templates;

/// <summary>
/// </summary>
/// <remarks>@authorpsilva</remarks>
public class SuperTemplate
{
    public virtual int DoIt(string task)
    {
        if (task.Equals("run"))
            return 1;
        return 0;
    }
}