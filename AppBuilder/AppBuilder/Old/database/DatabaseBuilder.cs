/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

namespace Api.Old.database;

/// <summary>
///     Classe responsável por construir os comandos SQL de forma dinâmica, recebendo
///     classe Modelo e ConnectionFactory.
/// </summary>
/// <remarks>@authorpedro</remarks>
public class DatabaseBuilder
{
    private string createDataBaseQuery = "";
    private string createTableQuery = "";
    private string deleteQuery = "";
    private ConnectionFactory factory;
    private string insertQuery = "";
    private Map<string, string> mapSelectQueries = new HashMap();
    private ModelBuilder modelBuilder;
    private string selectAllQuery = "";
    private readonly IList<string> selectQueries = new List();
    private string updateQuery = "";
    private readonly string useDataBaseQuery = "";

    public DatabaseBuilder(ModelBuilder modelBuilder, ConnectionFactory factory)
    {
        this.modelBuilder = modelBuilder;
        this.factory = factory;
        if (this.modelBuilder.GetChave() == null) throw new Exception("modelo não tem uma chave definida !");

        AddUpateQuery();
        AddDeleteQuery();
        AddInsertQuery();
        AddSelectAllQuery();
        AddCreateDataBaseQuery();
        AddCreateTableQuery();
        useDataBaseQuery = "USE " + this.factory.GetBaseDeDados().ToLowerCase();
    }

    public virtual bool BuildAll()
    {
        Connection con = ConnectionBuilder.GetConnection();
        var b1 = false;
        var b2 = false;
        var b3 = false;
        PreparedStatement stmt = con.PrepareStatement(GetCreateDataBaseQuery());
        b1 = stmt.ExecuteUpdate() > 0;
        PreparedStatement st = con.PrepareStatement(GetUseDataBaseQuery());
        b3 = st.ExecuteUpdate() > 0;
        PreparedStatement stmt2 = con.PrepareStatement(GetCreateTableQuery());
        b2 = stmt2.ExecuteUpdate() > 0;
        return b1 && b2;
    }

    public virtual string GetUseDataBaseQuery()
    {
        return useDataBaseQuery;
    }

    private void AddCreateTableQuery()
    {
        createTableQuery = "CREATE TABLE IF NOT EXISTS " + modelBuilder.GetName().ToLowerCase() + "(";
        var contador = 1;
        foreach (AttributeBuilder atributo in modelBuilder.GetAttributes())
        {
            if (contador == 2)
            {
                createTableQuery += ", ";
                contador = 1;
            }

            string nome = atributo.GetName().ToLowerCase();
            var tipo = "";
            if (atributo.GetTipo().Equals("String"))
                tipo = "VARCHAR(255)";
            else if (atributo.GetTipo().Equals("int"))
                tipo = "INT";
            else if (atributo.GetTipo().Equals("double"))
                tipo = "DOUBLE";
            else if (atributo.GetTipo().Equals("float"))
                tipo = "FLOAT";
            else if (atributo.GetTipo().Equals("Calendar"))
                tipo = "DATE";
            else
                throw new Exception("atributo " + nome + " com tipo desconhecido para criar query da tabela!");

            createTableQuery += nome + " " + tipo + " NOT NULL";
            if (modelBuilder.GetChave() == atributo) createTableQuery += " PRIMARY KEY";

            contador++;
        }

        createTableQuery += ")";
    }

    public virtual string GetCreateTableQuery()
    {
        return createTableQuery;
    }

    private void AddCreateDataBaseQuery()
    {
        createDataBaseQuery = "CREATE DATABASE IF NOT EXISTS ? CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci";
        createDataBaseQuery = createDataBaseQuery.Replace("?", factory.GetBaseDeDados().ToLowerCase());
    }

    public virtual string GetCreateDataBaseQuery()
    {
        return createDataBaseQuery;
    }

    public virtual bool AddSelectQuery(string nomeAtributo)
    {
        var selectQuery = "SELECT * FROM " + modelBuilder.GetName().ToLowerCase() + " WHERE " +
                          nomeAtributo.Trim().ToLowerCase() + "=?";
        if (mapSelectQueries.ContainsKey(nomeAtributo)) return false;

        bool b = selectQueries.Add(selectQuery);
        mapSelectQueries.Put(nomeAtributo.Trim().ToLowerCase(), selectQuery);
        return b;
    }

    public virtual string GetSelectQuery(string nomeAtributo)
    {
        return mapSelectQueries[nomeAtributo.Trim().ToLowerCase()];
    }

    public virtual string GetSelectAllQuery()
    {
        return selectAllQuery;
    }

    private void AddSelectAllQuery()
    {
        selectAllQuery = "SELECT * FROM " + modelBuilder.GetName().ToLowerCase();
    }

    private void AddUpateQuery()
    {
        updateQuery = "UPDATE " + modelBuilder.GetName().ToLowerCase() + " SET ";
        var c = 1;
        foreach (AttributeBuilder atr in modelBuilder.GetAttributes())
        {
            if (atr == modelBuilder.GetChave()) continue;

            if (c == 2)
            {
                updateQuery += ", ";
                c = 1;
            }

            updateQuery += atr.GetName().ToLowerCase() + "=?";
            c++;
        }

        updateQuery += " WHERE " + modelBuilder.GetChave().GetName().ToLowerCase() + "=?";
    }

    private void AddDeleteQuery()
    {
        deleteQuery = "DELETE  FROM " + modelBuilder.GetName().ToLowerCase() + " WHERE " +
                      modelBuilder.GetChave().GetName().ToLowerCase() + "=?";
    }

    private void AddInsertQuery()
    {
        insertQuery = "INSERT INTO " + modelBuilder.GetName().ToLowerCase() + "(";
        var c = 1;
        foreach (AttributeBuilder atr in modelBuilder.GetAttributes())
        {
            if (c == 2)
            {
                insertQuery += ", ";
                c = 1;
            }

            insertQuery += atr.GetName().ToLowerCase();
            c++;
        }

        insertQuery += ") VALUES (";
        int qtde = modelBuilder.GetAttributes().Count;
        var contador = 1;
        for (var i = 0; i < qtde; i++)
        {
            if (contador == 2)
            {
                insertQuery += ",";
                contador = 1;
            }

            contador++;
            insertQuery += "?";
        }

        insertQuery += ")";
    }

    public virtual string GetInsertQuery()
    {
        return insertQuery;
    }

    public virtual IList<string> GetSelectQueries()
    {
        return selectQueries;
    }

    public virtual string GetDeleteQuery()
    {
        return deleteQuery;
    }

    public virtual string GetUpdateQuery()
    {
        return updateQuery;
    }
}