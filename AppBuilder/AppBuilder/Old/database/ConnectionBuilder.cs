/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

namespace Api.Old.database;

/// <summary>
/// </summary>
/// <remarks>@authorpsilva</remarks>
public class ConnectionBuilder
{
    private static readonly string user = "root";
    private static readonly string password = "root";
    private static readonly string database = "";
    private static readonly string host = "localhost";

    public static Connection GetConnection()
    {
        return DriverManager.GetConnection("jdbc:mysql://" + host + "/" + database, user, password);
    }
}