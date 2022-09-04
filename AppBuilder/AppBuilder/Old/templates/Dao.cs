/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

namespace Api.Old.templates;

/// <summary>
/// </summary>
/// <remarks>@authorpsilva</remarks>
public class Dao
{
    private Connection con;

    public Dao()
    {
        try
        {
            con = DriverManager.GetConnection("jdbc:mysql://localhost/db", "user", "password");
        }
        catch (SQLException ex)
        {
            Logger.GetLogger(typeof(Dao).GetName()).Log(Level.SEVERE, null, ex);
        }
    }

    public virtual bool Adicionou(ModelBuilder m)
    {
        PreparedStatement stmt = con.PrepareCall("INSER INTO ");
        stmt.SetDate(0, new Date(Calendar.GetInstance().GetTimeInMillis()));
        return stmt.ExecuteUpdate() > 0;
    }

    public virtual ModelBuilder PesquisarPorNome(string nome)
    {
        PreparedStatement stmt = con.PrepareStatement("SELECT * FROM tabela WHERE nome=?");
        stmt.SetString(1, nome);
        Calendar c = Calendar.GetInstance();
        ResultSet rs = stmt.ExecuteQuery();
        List<ModelBuilder> m = new List();
        while (rs.Next())
        {
            string g = rs.GetString("");
        }

        return null;
    }
}