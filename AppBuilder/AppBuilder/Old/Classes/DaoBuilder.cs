/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

namespace Api.Old.Classes;

/// <summary>
/// </summary>
/// <remarks>@authorpsilva</remarks>
public class DaoBuilder : ClassBuilder
{
    private AttributeBuilder conexão;
    private DatabaseBuilder database;
    private ConnectionFactory factory;
    private MethodBuilder methodBuilderAdd;
    private MethodBuilder methodBuilderRemove;
    private MethodBuilder methodBuilderSelectAll;
    private MethodBuilder methodBuilderUpdate;
    private IList<MethodBuilder> metodosSearch = new List();
    private ModelBuilder modelBuilder;

    public DaoBuilder(ModelBuilder modelBuilder, ConnectionFactory factory, DatabaseBuilder database) : base(
        modelBuilder.GetName() + "DAO")
    {
        this.modelBuilder = modelBuilder;
        this.factory = factory;
        this.database = database;
        AddImport("java.sql.Connection");
        AddImport("java.sql.SQLException");
        AddImport("java.sql.PreparedStatement");
        AddImport("java.sql.Statement");
        AddImport("java.util.Calendar");
        AddImport("java.sql.Date");
        AddImport("java.sql.ResultSet");
        AddImport("java.util.ArrayList");
        AddImport("java.util.List");
        AddImport(AddClassBuilder(factory));
        AddImport(AddClassBuilder(modelBuilder));
        conexão = new AttributeBuilder("private", "Connection", "con");
        AddAttribute(conexão);

        //construtor
        var constructorBuilder = new ConstructorBuilder("public", name);
        ExceptionTreatmentBuilder trycatch = TratarExceção("SQLException");
        VarBuilder stmt = new VarBuilder("PreparedStatement", "stmt");
        VarBuilder ret1 = new VarBuilder("boolean", "ret1");
        VarBuilder ret2 = new VarBuilder("boolean", "ret2");
        VarBuilder ret3 = new VarBuilder("boolean", "ret3");
        ret2.SetClasse(this);
        ret3.SetClasse(this);
        ret1.SetClasse(this);
        stmt.SetClasse(this);
        string createDataBaseQuery = database.GetCreateDataBaseQuery();
        string useDataBaseQuery = database.GetUseDataBaseQuery();
        string createTableQuery = database.GetCreateTableQuery();
        trycatch.AddCorpo(conexão.GetInicialização(factory.CallStatic("getConnection")));
        string executeCreateDataBaseQuery = conexão.Call("prepareStatement", "\\\"" + createDataBaseQuery + "\\\"");
        string executeUseDataBaseQuery = conexão.Call("prepareStatement", "\\\"" + useDataBaseQuery + "\\\"");
        string executeCreateTableQuery = conexão.Call("prepareStatement", "\\\"" + createTableQuery + "\\\"");
        trycatch.AddCorpo(stmt.GetDeclaração(executeCreateDataBaseQuery));
        trycatch.AddCorpo(ret1.GetDeclaração(stmt.Call("executeUpdate") + "> 0 "));
        trycatch.AddCorpo(stmt.GetInicialização(executeUseDataBaseQuery));
        trycatch.AddCorpo(ret2.GetDeclaração(stmt.Call("executeUpdate") + "> 0 "));
        trycatch.AddCorpo(stmt.GetInicialização(executeCreateTableQuery));
        trycatch.AddCorpo(ret3.GetDeclaração(stmt.Call("executeUpdate") + "> 0 "));
        constructorBuilder.AddCorpo(trycatch.ToString());
        SetPrincipalConstructor(constructorBuilder);
        AddConstrutor(constructorBuilder);

        //ADD 
        AddMétodoAdd();

        //REMOVE
        AddMétodoRemove();

        //UPDATE
        AddMétodoUpdate();

        //SELECT ALL
        AddMétodoSelectAll();
        AddMethod(methodBuilderAdd);
        AddMethod(methodBuilderRemove);
        AddMethod(methodBuilderUpdate);
        AddMethod(methodBuilderSelectAll);
    }

    private void AddMétodoSelectAll()
    {
        methodBuilderSelectAll = new MethodBuilder("public", "List<" + modelBuilder.GetName() + ">", "selectAll");
        methodBuilderSelectAll.AddException(GetExceção("SQLException"));
        VarBuilder lista = new VarBuilder("ArrayList <" + modelBuilder.GetName() + ">", "lista");
        lista.SetClasse(this);
        VarBuilder stmt = new VarBuilder("PreparedStatement", "stmt");
        stmt.SetClasse(this);
        string selectAllQuery = database.GetSelectAllQuery();
        string executeSelectAllQuery = conexão.Call("prepareStatement", "\\\"" + selectAllQuery + "\\\"");
        methodBuilderSelectAll.AddCorpo(lista.GetDeclaração(lista.Instancia().GetInstancia()));
        methodBuilderSelectAll.AddCorpo(stmt.GetDeclaração(executeSelectAllQuery));
        VarBuilder rs = new VarBuilder("ResultSet", "rs");
        rs.SetClasse(this);
        methodBuilderSelectAll.AddCorpo(rs.GetDeclaração(stmt.Call("executeQuery")));
        var wh = new While(rs.Call("next"));
        VarBuilder obj = new VarBuilder(modelBuilder.GetName(), "obj");
        obj.SetClasse(this);
        wh.AddCorpo(obj.GetDeclaração(obj.Instancia().GetInstancia()));

        //
        var pos = 1;
        foreach (AttributeBuilder atr in modelBuilder.GetAttributes())
        {
            var chamada = "get";
            var arg = "";
            if (atr.GetTipo().Equals("Calendar"))
            {
                chamada += "Date";
                string tmp = rs.Call(chamada, "\\\"" + atr.GetName().ToLowerCase() + "\\\"");
                VarBuilder data = new VarBuilder("Calendar", "data" + pos);
                data.SetClasse(this);
                wh.AddCorpo(data.GetDeclaração(GetClasse("Calendar").CallStatic("getInstance")));
                wh.AddCorpo(data.Call("setTime", tmp));
                arg = data.GetReferencia();
                pos++;
            }
            else
            {
                if (atr.GetTipo().Equals("String"))
                    chamada += "String";
                else if (atr.GetTipo().Equals("int"))
                    chamada += "Int";
                else if (atr.GetTipo().Equals("double"))
                    chamada += "Double";
                else if (atr.GetTipo().Equals("float")) chamada += "Float";

                arg = rs.Call(chamada, "\\\"" + atr.GetName().ToLowerCase() + "\\\"");
            }

            wh.AddCorpo(obj.Call(modelBuilder.GetSetter(atr.GetName()).GetName(), arg));
        }

        wh.AddCorpo(lista.Call("add", obj.GetReferencia()));
        methodBuilderSelectAll.AddCorpo(wh.ToString());
        VarBuilder tmp = new VarBuilder("Statement", "closeOperation");
        tmp.SetClasse(this);
        methodBuilderSelectAll.AddCorpo(tmp.GetDeclaração("(Statement) " + stmt.GetReferencia()));
        methodBuilderSelectAll.AddCorpo(tmp.Call("close"));
        methodBuilderSelectAll.AddCorpo(rs.Call("close"));
        methodBuilderSelectAll.SetReturn(lista.GetReferencia());
    }

    private void AddMétodoUpdate()
    {
        AttributeBuilder chave = modelBuilder.GetChave();
        methodBuilderUpdate = new MethodBuilder("public", "boolean", "update");
        ParameterBuilder paramModelo = new ParameterBuilder(modelBuilder.GetName(), "modelo");
        paramModelo.SetClasse(this);
        methodBuilderUpdate.AddParameters(paramModelo);
        methodBuilderUpdate.AddException(GetExceção("SQLException"));
        VarBuilder stmt = new VarBuilder("PreparedStatement", "stmt");
        stmt.SetClasse(this);
        string updateQuery = database.GetUpdateQuery();
        string executeUpdateQuery = conexão.Call("prepareStatement", "\\\"" + updateQuery + "\\\"");
        methodBuilderUpdate.AddCorpo(stmt.GetDeclaração(executeUpdateQuery));
        var pos = 1;
        foreach (AttributeBuilder atributo in modelBuilder.GetAttributes())
        {
            if (atributo == chave) continue;

            var metodo = "set";
            string argumento = paramModelo.Call(modelBuilder.GetGetter(atributo.GetName()).GetName());
            if (atributo.GetTipo().Equals("String"))
            {
                metodo += "String";
            }
            else if (atributo.GetTipo().Equals("int"))
            {
                metodo += "Int";
            }
            else if (atributo.GetTipo().Equals("double"))
            {
                metodo += "Double";
            }
            else if (atributo.GetTipo().Equals("float"))
            {
                metodo += "Float";
            }
            else if (atributo.GetTipo().Equals("Calendar"))
            {
                metodo += "Date";
                MethodBuilder getter = modelBuilder.GetGetter(atributo.GetName());
                VarBuilder var = new VarBuilder("Calendar", "tmp" + pos);
                var.SetClasse(this);
                methodBuilderUpdate.AddCorpo(var.GetDeclaração(argumento));
                argumento = GetClasse("Date").GetInstancia(var.Call("getTimeInMillis")).GetInstancia();
            }

            methodBuilderUpdate.AddCorpo(stmt.Call(metodo, pos + "", argumento));
            pos++;
        }

        var metodo = "set";
        string argumento = paramModelo.Call(modelBuilder.GetGetter(chave.GetName()).GetName());
        if (chave.GetTipo().Equals("String"))
        {
            metodo += "String";
        }
        else if (chave.GetTipo().Equals("int"))
        {
            metodo += "Int";
        }
        else if (chave.GetTipo().Equals("double"))
        {
            metodo += "Double";
        }
        else if (chave.GetTipo().Equals("float"))
        {
            metodo += "Float";
        }
        else if (chave.GetTipo().Equals("Calendar"))
        {
            metodo += "Date";
            MethodBuilder getter = modelBuilder.GetGetter(chave.GetName());
            VarBuilder var = new VarBuilder("Calendar", "tmp" + pos);
            var.SetClasse(this);
            methodBuilderUpdate.AddCorpo(var.GetDeclaração(argumento));
            argumento = GetClasse("Date").GetInstancia(var.Call("getTimeInMillis")).GetInstancia();
        }

        methodBuilderUpdate.AddCorpo(stmt.Call(metodo, pos + "", argumento));
        VarBuilder retAdd = new VarBuilder("int", "retorno");
        retAdd.SetClasse(this);
        methodBuilderUpdate.AddCorpo(retAdd.GetDeclaração(stmt.Call("executeUpdate")));
        VarBuilder tmp = new VarBuilder("Statement", "closeOperation");
        tmp.SetClasse(this);
        methodBuilderUpdate.AddCorpo(tmp.GetDeclaração("(Statement) " + stmt.GetReferencia()));
        methodBuilderUpdate.AddCorpo(tmp.Call("close"));
        methodBuilderUpdate.SetReturn(retAdd.GetReferencia() + " > 0");
    }

    private void AddMétodoRemove()
    {
        // COMEÇO - REMOVE 
        AttributeBuilder chave = modelBuilder.GetChave();
        methodBuilderRemove = new MethodBuilder("public", "boolean", "remove");
        ParameterBuilder paramModelo = new ParameterBuilder(modelBuilder.GetName(), "modelo");
        paramModelo.SetClasse(this);
        methodBuilderRemove.AddParameters(paramModelo);
        methodBuilderRemove.AddException(GetExceção("SQLException"));
        VarBuilder stmt = new VarBuilder("PreparedStatement", "stmt");
        stmt.SetClasse(this);
        string removeQuery = database.GetDeleteQuery();
        string executeRemoveQuery = conexão.Call("prepareStatement", "\\\"" + removeQuery + "\\\"");
        methodBuilderRemove.AddCorpo(stmt.GetDeclaração(executeRemoveQuery));
        var pos = 1;
        AttributeBuilder atributo = chave;
        var metodo = "set";
        string argumento = paramModelo.Call(modelBuilder.GetGetter(atributo.GetName()).GetName());
        if (atributo.GetTipo().Equals("String"))
        {
            metodo += "String";
        }
        else if (atributo.GetTipo().Equals("int"))
        {
            metodo += "Int";
        }
        else if (atributo.GetTipo().Equals("double"))
        {
            metodo += "Double";
        }
        else if (atributo.GetTipo().Equals("float"))
        {
            metodo += "Float";
        }
        else if (atributo.GetTipo().Equals("Calendar"))
        {
            metodo += "Date";
            MethodBuilder getter = modelBuilder.GetGetter(atributo.GetName());
            VarBuilder var = new VarBuilder("Calendar", "tmp" + pos);
            var.SetClasse(this);
            methodBuilderRemove.AddCorpo(var.GetDeclaração(argumento));
            argumento = GetClasse("Date").GetInstancia(var.Call("getTimeInMillis")).GetInstancia();
        }

        methodBuilderRemove.AddCorpo(stmt.Call(metodo, pos + "", argumento));
        pos++;
        VarBuilder retAdd = new VarBuilder("int", "retorno");
        retAdd.SetClasse(this);
        methodBuilderRemove.AddCorpo(retAdd.GetDeclaração(stmt.Call("executeUpdate")));
        VarBuilder tmp = new VarBuilder("Statement", "closeOperation");
        tmp.SetClasse(this);
        methodBuilderRemove.AddCorpo(tmp.GetDeclaração("(Statement) " + stmt.GetReferencia()));
        methodBuilderRemove.AddCorpo(tmp.Call("close"));
        methodBuilderRemove.SetReturn(retAdd.GetReferencia() + " > 0");
    }

    private void AddMétodoAdd()
    {
        //COMEÇO - ADD
        methodBuilderAdd = new MethodBuilder("public", "boolean", "add");
        ParameterBuilder paramModelo = new ParameterBuilder(modelBuilder.GetName(), "modelo");
        paramModelo.SetClasse(this);
        methodBuilderAdd.AddParameters(paramModelo);
        methodBuilderAdd.AddException(GetExceção("SQLException"));
        VarBuilder stmt = new VarBuilder("PreparedStatement", "stmt");
        stmt.SetClasse(this);
        string addQuery = database.GetInsertQuery();
        string executeInsertQuery = conexão.Call("prepareStatement", "\\\"" + addQuery + "\\\"");
        methodBuilderAdd.AddCorpo(stmt.GetDeclaração(executeInsertQuery));
        var pos = 1;
        foreach (AttributeBuilder atributo in modelBuilder.GetAttributes())
        {
            var metodo = "set";
            string argumento = paramModelo.Call(modelBuilder.GetGetter(atributo.GetName()).GetName());
            if (atributo.GetTipo().Equals("String"))
            {
                metodo += "String";
            }
            else if (atributo.GetTipo().Equals("int"))
            {
                metodo += "Int";
            }
            else if (atributo.GetTipo().Equals("double"))
            {
                metodo += "Double";
            }
            else if (atributo.GetTipo().Equals("float"))
            {
                metodo += "Float";
            }
            else if (atributo.GetTipo().Equals("Calendar"))
            {
                metodo += "Date";
                MethodBuilder getter = modelBuilder.GetGetter(atributo.GetName());
                VarBuilder var = new VarBuilder("Calendar", "tmp" + pos);
                var.SetClasse(this);
                methodBuilderAdd.AddCorpo(var.GetDeclaração(argumento));
                argumento = GetClasse("Date").GetInstancia(var.Call("getTimeInMillis")).GetInstancia();
            }

            methodBuilderAdd.AddCorpo(stmt.Call(metodo, pos + "", argumento));
            pos++;
        }

        VarBuilder retAdd = new VarBuilder("int", "retorno");
        retAdd.SetClasse(this);
        methodBuilderAdd.AddCorpo(retAdd.GetDeclaração(stmt.Call("executeUpdate")));
        VarBuilder tmp = new VarBuilder("Statement", "closeOperation");
        tmp.SetClasse(this);
        methodBuilderAdd.AddCorpo(tmp.GetDeclaração("(Statement) " + stmt.GetReferencia()));
        methodBuilderAdd.AddCorpo(tmp.Call("close"));
        methodBuilderAdd.SetReturn(retAdd.GetReferencia() + " > 0"); //FIM - ADD
    }

    public virtual void AddMétodoPesquisa(string nomeAtributo)
    {
        AttributeBuilder atributo = modelBuilder.GetAttribute(nomeAtributo);
        if (atributo == null) return;

        MethodBuilder methodBuilder = new MethodBuilder("public", "List<" + modelBuilder.GetName() + ">",
            "searchBy" + UpperCase(nomeAtributo));
        ParameterBuilder param = new ParameterBuilder(atributo.GetTipo(), nomeAtributo.ToLowerCase());
        methodBuilder.AddParameters(param);
        param.SetClasse(this);
        methodBuilder.AddException(GetExceção("SQLException"));
        VarBuilder lista = new VarBuilder("ArrayList <" + modelBuilder.GetName() + ">", "lista");
        lista.SetClasse(this);
        VarBuilder stmt = new VarBuilder("PreparedStatement", "stmt");
        stmt.SetClasse(this);
        bool added = database.AddSelectQuery(nomeAtributo);
        if (!added) System.err.Println("addMétodoPesquisa: não foi possível adicionar query de pesquisa !");

        string searchQuery = database.GetSelectQuery(nomeAtributo);
        string executeSearchQuery = conexão.Call("prepareStatement", "\\\"" + searchQuery + "\\\"");
        methodBuilder.AddCorpo(lista.GetDeclaração(lista.Instancia().GetInstancia()));
        methodBuilder.AddCorpo(stmt.GetDeclaração(executeSearchQuery));
        var pos = 1;
        var metodo = "set";
        string argumento = param.GetReferencia();
        if (atributo.GetTipo().Equals("String"))
        {
            metodo += "String";
        }
        else if (atributo.GetTipo().Equals("int"))
        {
            metodo += "Int";
        }
        else if (atributo.GetTipo().Equals("double"))
        {
            metodo += "Double";
        }
        else if (atributo.GetTipo().Equals("float"))
        {
            metodo += "Float";
        }
        else if (atributo.GetTipo().Equals("Calendar"))
        {
            metodo += "Date";
            MethodBuilder getter = modelBuilder.GetGetter(atributo.GetName());
            VarBuilder var = new VarBuilder("Calendar", "tmp" + pos);
            var.SetClasse(this);
            methodBuilder.AddCorpo(var.GetDeclaração(argumento));
            argumento = GetClasse("Date").GetInstancia(var.Call("getTimeInMillis")).GetInstancia();
        }

        methodBuilder.AddCorpo(stmt.Call(metodo, pos + "", argumento));
        VarBuilder rs = new VarBuilder("ResultSet", "rs");
        rs.SetClasse(this);
        methodBuilder.AddCorpo(rs.GetDeclaração(stmt.Call("executeQuery")));
        var wh = new While(rs.Call("next"));
        VarBuilder obj = new VarBuilder(modelBuilder.GetName(), "obj");
        obj.SetClasse(this);
        wh.AddCorpo(obj.GetDeclaração(obj.Instancia().GetInstancia()));

        //
        pos = 1;
        foreach (AttributeBuilder atr in modelBuilder.GetAttributes())
        {
            var chamada = "get";
            var arg = "";
            if (atr.GetTipo().Equals("Calendar"))
            {
                chamada += "Date";
                string tmp = rs.Call(chamada, "\\\"" + atr.GetName().ToLowerCase() + "\\\"");
                VarBuilder data = new VarBuilder("Calendar", "data" + pos);
                data.SetClasse(this);
                wh.AddCorpo(data.GetDeclaração(GetClasse("Calendar").CallStatic("getInstance")));
                wh.AddCorpo(data.Call("setTime", tmp));
                arg = data.GetReferencia();
                pos++;
            }
            else
            {
                if (atr.GetTipo().Equals("String"))
                    chamada += "String";
                else if (atr.GetTipo().Equals("int"))
                    chamada += "Int";
                else if (atr.GetTipo().Equals("double"))
                    chamada += "Double";
                else if (atr.GetTipo().Equals("float")) chamada += "Float";

                arg = rs.Call(chamada, "\\\"" + atr.GetName().ToLowerCase() + "\\\"");
            }

            wh.AddCorpo(obj.Call(modelBuilder.GetSetter(atr.GetName()).GetName(), arg));
        }

        wh.AddCorpo(lista.Call("add", obj.GetReferencia()));
        methodBuilder.AddCorpo(wh.ToString());
        VarBuilder tmp = new VarBuilder("Statement", "closeOperation");
        tmp.SetClasse(this);
        methodBuilder.AddCorpo(tmp.GetDeclaração("(Statement) " + stmt.GetReferencia()));
        methodBuilder.AddCorpo(tmp.Call("close"));
        methodBuilder.AddCorpo(rs.Call("close"));
        methodBuilder.SetReturn(lista.GetReferencia());
        AddMethod(methodBuilder);
    }

    public virtual ModelBuilder GetModelo()
    {
        return modelBuilder;
    }

    public virtual void SetModelo(ModelBuilder modelBuilder)
    {
        this.modelBuilder = modelBuilder;
    }

    public virtual AttributeBuilder GetListaDados()
    {
        return conexão;
    }

    public virtual void SetListaDados(AttributeBuilder listaDados)
    {
        conexão = listaDados;
    }

    public virtual MethodBuilder GetMétodoAdd()
    {
        return methodBuilderAdd;
    }

    public virtual void SetMétodoAdd(MethodBuilder methodBuilderAdd)
    {
        this.methodBuilderAdd = methodBuilderAdd;
    }

    public virtual MethodBuilder GetMétodoRemove()
    {
        return methodBuilderRemove;
    }

    public virtual void SetMétodoRemove(MethodBuilder methodBuilderRemove)
    {
        this.methodBuilderRemove = methodBuilderRemove;
    }

    public virtual MethodBuilder GetMétodoUpdate()
    {
        return methodBuilderUpdate;
    }

    public virtual void SetMétodoUpdate(MethodBuilder methodBuilderUpdate)
    {
        this.methodBuilderUpdate = methodBuilderUpdate;
    }

    public virtual IList<MethodBuilder> GetMetodosSearch()
    {
        return metodosSearch;
    }

    public virtual void SetMetodosSearch(IList<MethodBuilder> metodosSearch)
    {
        this.metodosSearch = metodosSearch;
    }
}