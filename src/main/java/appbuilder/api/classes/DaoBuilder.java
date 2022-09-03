/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.api.classes;

import appbuilder.api.classes.exceptions.ExceptionTreatmentBuilder;
import appbuilder.api.database.DatabaseBuilder;
import appbuilder.api.methods.MethodBuilder;
import appbuilder.api.methods.ParameterBuilder;
import appbuilder.api.vars.AttributeBuilder;
import appbuilder.api.vars.VarBuilder;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author psilva
 */
public class DaoBuilder extends ClassBuilder {

    private ModelBuilder modelBuilder;
    private ConnectionFactory factory;
    private DatabaseBuilder database;
    private AttributeBuilder conexão;
    private MethodBuilder methodBuilderAdd;
    private MethodBuilder methodBuilderRemove;
    private MethodBuilder methodBuilderUpdate;
    private MethodBuilder methodBuilderSelectAll;

    private List<MethodBuilder> metodosSearch = new ArrayList<>();

    public DaoBuilder(ModelBuilder modelBuilder, ConnectionFactory factory, DatabaseBuilder database) throws FileNotFoundException {
        super(modelBuilder.getName() + "DAO");
        this.modelBuilder = modelBuilder;
        this.factory = factory;
        this.database = database;

        addImport("java.sql.Connection");
        addImport("java.sql.SQLException");
        addImport("java.sql.PreparedStatement");
        addImport("java.sql.Statement");
        addImport("java.util.Calendar");
        addImport("java.sql.Date");
        addImport("java.sql.ResultSet");
        addImport("java.util.ArrayList");
        addImport("java.util.List");

        addImport(ClassBuilder.addClassBuilder(factory));
        addImport(ClassBuilder.addClassBuilder(modelBuilder));
        conexão = new AttributeBuilder("private", "Connection", "con");
        addAttribute(conexão);

        //construtor
        ConstructorBuilder constructorBuilder = new ConstructorBuilder("public", this.name);
        ExceptionTreatmentBuilder trycatch = tratarExceção("SQLException");
        VarBuilder stmt = new VarBuilder("PreparedStatement", "stmt");
        VarBuilder ret1 = new VarBuilder("boolean", "ret1");
        VarBuilder ret2 = new VarBuilder("boolean", "ret2");
        VarBuilder ret3 = new VarBuilder("boolean", "ret3");
        ret2.setClasse(this);
        ret3.setClasse(this);
        ret1.setClasse(this);
        stmt.setClasse(this);

        String createDataBaseQuery = database.getCreateDataBaseQuery();
        String useDataBaseQuery = database.getUseDataBaseQuery();
        String createTableQuery = database.getCreateTableQuery();

        trycatch.addCorpo(conexão.getInicialização(factory.callStatic("getConnection")));
        String executeCreateDataBaseQuery = conexão.call("prepareStatement", "\"" + createDataBaseQuery + "\"");
        String executeUseDataBaseQuery = conexão.call("prepareStatement", "\"" + useDataBaseQuery + "\"");
        String executeCreateTableQuery = conexão.call("prepareStatement", "\"" + createTableQuery + "\"");
        trycatch.addCorpo(stmt.getDeclaração(executeCreateDataBaseQuery));
        trycatch.addCorpo(ret1.getDeclaração(stmt.call("executeUpdate") + "> 0 "));
        trycatch.addCorpo(stmt.getInicialização(executeUseDataBaseQuery));
        trycatch.addCorpo(ret2.getDeclaração(stmt.call("executeUpdate") + "> 0 "));
        trycatch.addCorpo(stmt.getInicialização(executeCreateTableQuery));
        trycatch.addCorpo(ret3.getDeclaração(stmt.call("executeUpdate") + "> 0 "));
        
        constructorBuilder.addCorpo(trycatch.toString());
        setPrincipalConstructor(constructorBuilder);
        addConstrutor(constructorBuilder);

        //ADD 
        addMétodoAdd();
        //REMOVE
        addMétodoRemove();
        //UPDATE
        addMétodoUpdate();
        //SELECT ALL
        addMétodoSelectAll();

        addMethod(methodBuilderAdd);
        addMethod(methodBuilderRemove);
        addMethod(methodBuilderUpdate);
        addMethod(methodBuilderSelectAll);
        
       
    }

    private void addMétodoSelectAll() {
        methodBuilderSelectAll = new MethodBuilder("public", "List<"+ modelBuilder.getName()+">", "selectAll");
        methodBuilderSelectAll.addException(getExceção("SQLException"));
        VarBuilder lista = new VarBuilder("ArrayList <" + modelBuilder.getName() + ">", "lista");
        lista.setClasse(this);
        VarBuilder stmt = new VarBuilder("PreparedStatement", "stmt");
        stmt.setClasse(this);

        String selectAllQuery = database.getSelectAllQuery();

        String executeSelectAllQuery = conexão.call("prepareStatement", "\"" + selectAllQuery + "\"");
        methodBuilderSelectAll.addCorpo(lista.getDeclaração(lista.instancia().getInstancia()));
        methodBuilderSelectAll.addCorpo(stmt.getDeclaração(executeSelectAllQuery));
        VarBuilder rs = new VarBuilder("ResultSet","rs");
        rs.setClasse(this);
        
        methodBuilderSelectAll.addCorpo(rs.getDeclaração(stmt.call("executeQuery")));
        
        While wh = new While(rs.call("next"));
        VarBuilder obj = new VarBuilder(modelBuilder.getName(), "obj");
        obj.setClasse(this);
        wh.addCorpo(obj.getDeclaração(obj.instancia().getInstancia()));

        //
        int pos = 1;
        for (AttributeBuilder atr : this.modelBuilder.getAttributes()) {
            String chamada = "get";
            String arg = "";

            if (atr.getTipo().equals("Calendar")) {
                chamada += "Date";
                String tmp = rs.call(chamada, "\"" + atr.getName().toLowerCase() + "\"");
                VarBuilder data = new VarBuilder("Calendar", "data" + pos);
                data.setClasse(this);
                wh.addCorpo(
                        data.getDeclaração(
                                getClasse("Calendar").
                                        callStatic("getInstance")));

                wh.addCorpo(data.call("setTime", tmp));
                arg = data.getReferencia();
                pos++;
            } else {

                if (atr.getTipo().equals("String")) {
                    chamada += "String";
                } else if (atr.getTipo().equals("int")) {
                    chamada += "Int";

                } else if (atr.getTipo().equals("double")) {
                    chamada += "Double";
                } else if (atr.getTipo().equals("float")) {
                    chamada += "Float";
                }

                arg = rs.call(chamada, "\"" + atr.getName().toLowerCase() + "\"");

            }

            wh.addCorpo(obj.call(modelBuilder.getSetter(atr.getName()).getName(), arg));

        }

        wh.addCorpo(lista.call("add", obj.getReferencia()));
        methodBuilderSelectAll.addCorpo(wh.toString());
        VarBuilder tmp = new VarBuilder("Statement", "closeOperation");
        tmp.setClasse(this);
        methodBuilderSelectAll.addCorpo(tmp.getDeclaração("(Statement) " + stmt.getReferencia()));
        methodBuilderSelectAll.addCorpo(tmp.call("close"));
        methodBuilderSelectAll.addCorpo(rs.call("close"));
        
        methodBuilderSelectAll.setReturn(lista.getReferencia());
    }

    private void addMétodoUpdate() {
        AttributeBuilder chave = modelBuilder.getChave();
        methodBuilderUpdate = new MethodBuilder("public", "boolean", "update");
        ParameterBuilder paramModelo = new ParameterBuilder(this.modelBuilder.getName(), "modelo");
        paramModelo.setClasse(this);
        methodBuilderUpdate.addParameters(paramModelo);
        methodBuilderUpdate.addException(getExceção("SQLException"));

        VarBuilder stmt = new VarBuilder("PreparedStatement", "stmt");
        stmt.setClasse(this);

        String updateQuery = database.getUpdateQuery();

        String executeUpdateQuery = conexão.call("prepareStatement", "\"" + updateQuery + "\"");
        methodBuilderUpdate.addCorpo(stmt.getDeclaração(executeUpdateQuery));

        int pos = 1;
        for (AttributeBuilder atributo : modelBuilder.getAttributes()) {
            if (atributo == chave) {
                continue;
            }

            String metodo = "set";
            String argumento = paramModelo.
                    call(
                            modelBuilder.getGetter(
                                    atributo.getName())
                                    .getName());

            if (atributo.getTipo().equals("String")) {
                metodo += "String";
            } else if (atributo.getTipo().equals("int")) {
                metodo += "Int";

            } else if (atributo.getTipo().equals("double")) {
                metodo += "Double";
            } else if (atributo.getTipo().equals("float")) {
                metodo += "Float";
            } else if (atributo.getTipo().equals("Calendar")) {
                metodo += "Date";
                MethodBuilder getter = modelBuilder.getGetter(atributo.getName());
                VarBuilder var = new VarBuilder("Calendar", "tmp" + pos);
                var.setClasse(this);
                methodBuilderUpdate.addCorpo(var.getDeclaração(argumento));
                argumento = getClasse("Date").getInstancia(var.call("getTimeInMillis")).getInstancia();
            }

            methodBuilderUpdate.addCorpo(stmt.call(metodo, pos + "", argumento));
            pos++;
        }

        String metodo = "set";
        String argumento = paramModelo.
                call(
                        modelBuilder.getGetter(
                                chave.getName())
                                .getName());

        if (chave.getTipo().equals("String")) {
            metodo += "String";
        } else if (chave.getTipo().equals("int")) {
            metodo += "Int";

        } else if (chave.getTipo().equals("double")) {
            metodo += "Double";
        } else if (chave.getTipo().equals("float")) {
            metodo += "Float";
        } else if (chave.getTipo().equals("Calendar")) {
            metodo += "Date";
            MethodBuilder getter = modelBuilder.getGetter(chave.getName());
            VarBuilder var = new VarBuilder("Calendar", "tmp" + pos);
            var.setClasse(this);
            methodBuilderUpdate.addCorpo(var.getDeclaração(argumento));
            argumento = getClasse("Date").getInstancia(var.call("getTimeInMillis")).getInstancia();
        }

        methodBuilderUpdate.addCorpo(stmt.call(metodo, pos + "", argumento));

        VarBuilder retAdd = new VarBuilder("int", "retorno");
        retAdd.setClasse(this);
        methodBuilderUpdate.addCorpo(retAdd.getDeclaração(stmt.call("executeUpdate")));
        VarBuilder tmp = new VarBuilder("Statement", "closeOperation");
        tmp.setClasse(this);
        methodBuilderUpdate.addCorpo(tmp.getDeclaração("(Statement) " + stmt.getReferencia()));
        methodBuilderUpdate.addCorpo(tmp.call("close"));
        methodBuilderUpdate.setReturn(retAdd.getReferencia() + " > 0");

    }

    private void addMétodoRemove() {
        // COMEÇO - REMOVE 

        AttributeBuilder chave = modelBuilder.getChave();
        methodBuilderRemove = new MethodBuilder("public", "boolean", "remove");
        ParameterBuilder paramModelo = new ParameterBuilder(this.modelBuilder.getName(), "modelo");
        paramModelo.setClasse(this);
        methodBuilderRemove.addParameters(paramModelo);
        methodBuilderRemove.addException(getExceção("SQLException"));

        VarBuilder stmt = new VarBuilder("PreparedStatement", "stmt");
        stmt.setClasse(this);

        String removeQuery = database.getDeleteQuery();

        String executeRemoveQuery = conexão.call("prepareStatement", "\"" + removeQuery + "\"");
        methodBuilderRemove.addCorpo(stmt.getDeclaração(executeRemoveQuery));

        int pos = 1;
        AttributeBuilder atributo = chave;
        String metodo = "set";
        String argumento = paramModelo.
                call(
                        modelBuilder.getGetter(
                                atributo.getName())
                                .getName());

        if (atributo.getTipo().equals("String")) {
            metodo += "String";
        } else if (atributo.getTipo().equals("int")) {
            metodo += "Int";

        } else if (atributo.getTipo().equals("double")) {
            metodo += "Double";
        } else if (atributo.getTipo().equals("float")) {
            metodo += "Float";
        } else if (atributo.getTipo().equals("Calendar")) {
            metodo += "Date";
            MethodBuilder getter = modelBuilder.getGetter(atributo.getName());
            VarBuilder var = new VarBuilder("Calendar", "tmp" + pos);
            var.setClasse(this);
            methodBuilderRemove.addCorpo(var.getDeclaração(argumento));
            argumento = getClasse("Date").getInstancia(var.call("getTimeInMillis")).getInstancia();
        }

        methodBuilderRemove.addCorpo(stmt.call(metodo, pos + "", argumento));
        pos++;

        VarBuilder retAdd = new VarBuilder("int", "retorno");
        retAdd.setClasse(this);
        methodBuilderRemove.addCorpo(retAdd.getDeclaração(stmt.call("executeUpdate")));
        VarBuilder tmp = new VarBuilder("Statement", "closeOperation");
        tmp.setClasse(this);
        methodBuilderRemove.addCorpo(tmp.getDeclaração("(Statement) " + stmt.getReferencia()));
        methodBuilderRemove.addCorpo(tmp.call("close"));
        methodBuilderRemove.setReturn(retAdd.getReferencia() + " > 0");

    }

    private void addMétodoAdd() {
        //COMEÇO - ADD

        methodBuilderAdd = new MethodBuilder("public", "boolean", "add");
        ParameterBuilder paramModelo = new ParameterBuilder(this.modelBuilder.getName(), "modelo");
        paramModelo.setClasse(this);
        methodBuilderAdd.addParameters(paramModelo);
        methodBuilderAdd.addException(getExceção("SQLException"));

        VarBuilder stmt = new VarBuilder("PreparedStatement", "stmt");
        stmt.setClasse(this);

        String addQuery = database.getInsertQuery();

        String executeInsertQuery = conexão.call("prepareStatement", "\"" + addQuery + "\"");
        methodBuilderAdd.addCorpo(stmt.getDeclaração(executeInsertQuery));

        int pos = 1;
        for (AttributeBuilder atributo : modelBuilder.getAttributes()) {
            String metodo = "set";
            String argumento = paramModelo.
                    call(
                            modelBuilder.getGetter(
                                    atributo.getName())
                                    .getName());

            if (atributo.getTipo().equals("String")) {
                metodo += "String";
            } else if (atributo.getTipo().equals("int")) {
                metodo += "Int";

            } else if (atributo.getTipo().equals("double")) {
                metodo += "Double";
            } else if (atributo.getTipo().equals("float")) {
                metodo += "Float";
            } else if (atributo.getTipo().equals("Calendar")) {
                metodo += "Date";
                MethodBuilder getter = modelBuilder.getGetter(atributo.getName());
                VarBuilder var = new VarBuilder("Calendar", "tmp" + pos);
                var.setClasse(this);
                methodBuilderAdd.addCorpo(var.getDeclaração(argumento));
                argumento = getClasse("Date").getInstancia(
                        var.call("getTimeInMillis")).getInstancia();
            }

            methodBuilderAdd.addCorpo(stmt.call(metodo, pos + "", argumento));
            pos++;
        }

        VarBuilder retAdd = new VarBuilder("int", "retorno");
        retAdd.setClasse(this);
        methodBuilderAdd.addCorpo(retAdd.getDeclaração(stmt.call("executeUpdate")));
        VarBuilder tmp = new VarBuilder("Statement", "closeOperation");
        tmp.setClasse(this);
        methodBuilderAdd.addCorpo(tmp.getDeclaração("(Statement) " + stmt.getReferencia()));
        methodBuilderAdd.addCorpo(tmp.call("close"));
        methodBuilderAdd.setReturn(retAdd.getReferencia() + " > 0");

        //FIM - ADD
    }

    public void addMétodoPesquisa(String nomeAtributo) {
        AttributeBuilder atributo = modelBuilder.getAttribute(nomeAtributo);

        if (atributo == null) {
            return;
        }

        MethodBuilder methodBuilder = new MethodBuilder("public", "List<" + modelBuilder.getName() + ">", "searchBy" + upperCase(nomeAtributo));
        ParameterBuilder param = new ParameterBuilder(atributo.getTipo(), nomeAtributo.toLowerCase());
        methodBuilder.addParameters(param);
        param.setClasse(this);
        methodBuilder.addException(getExceção("SQLException"));

        VarBuilder lista = new VarBuilder("ArrayList <" + modelBuilder.getName() + ">", "lista");
        lista.setClasse(this);
        VarBuilder stmt = new VarBuilder("PreparedStatement", "stmt");
        stmt.setClasse(this);
        boolean added = database.addSelectQuery(nomeAtributo);
        assert added == true;

        if (!added) {
            System.err.println("addMétodoPesquisa: não foi possível adicionar query de pesquisa !");
        }

        String searchQuery = database.getSelectQuery(nomeAtributo);

        String executeSearchQuery = conexão.call("prepareStatement", "\"" + searchQuery + "\"");
        methodBuilder.addCorpo(lista.getDeclaração(lista.instancia().getInstancia()));
        methodBuilder.addCorpo(stmt.getDeclaração(executeSearchQuery));

        int pos = 1;
        String metodo = "set";
        String argumento = param.getReferencia();

        if (atributo.getTipo().equals("String")) {
            metodo += "String";
        } else if (atributo.getTipo().equals("int")) {
            metodo += "Int";
        } else if (atributo.getTipo().equals("double")) {
            metodo += "Double";
        } else if (atributo.getTipo().equals("float")) {
            metodo += "Float";
        } else if (atributo.getTipo().equals("Calendar")) {
            metodo += "Date";
            MethodBuilder getter = modelBuilder.getGetter(atributo.getName());
            VarBuilder var = new VarBuilder("Calendar", "tmp" + pos);
            var.setClasse(this);
            methodBuilder.addCorpo(var.getDeclaração(argumento));
            argumento = getClasse("Date").getInstancia(
                    var.call("getTimeInMillis")).getInstancia();
        }

        methodBuilder.addCorpo(stmt.call(metodo, pos + "", argumento));

        VarBuilder rs = new VarBuilder("ResultSet", "rs");
        rs.setClasse(this);
        methodBuilder.addCorpo(rs.getDeclaração(stmt.call("executeQuery")));

        While wh = new While(rs.call("next"));
        VarBuilder obj = new VarBuilder(modelBuilder.getName(), "obj");
        obj.setClasse(this);
        wh.addCorpo(obj.getDeclaração(obj.instancia().getInstancia()));

        //
        pos = 1;
        for (AttributeBuilder atr : this.modelBuilder.getAttributes()) {
            String chamada = "get";
            String arg = "";

            if (atr.getTipo().equals("Calendar")) {
                chamada += "Date";
                String tmp = rs.call(chamada, "\"" + atr.getName().toLowerCase() + "\"");
                VarBuilder data = new VarBuilder("Calendar", "data" + pos);
                data.setClasse(this);
                wh.addCorpo(
                        data.getDeclaração(
                                getClasse("Calendar").
                                        callStatic("getInstance")));

                wh.addCorpo(data.call("setTime", tmp));
                arg = data.getReferencia();
                pos++;
            } else {

                if (atr.getTipo().equals("String")) {
                    chamada += "String";
                } else if (atr.getTipo().equals("int")) {
                    chamada += "Int";

                } else if (atr.getTipo().equals("double")) {
                    chamada += "Double";
                } else if (atr.getTipo().equals("float")) {
                    chamada += "Float";
                }

                arg = rs.call(chamada, "\"" + atr.getName().toLowerCase() + "\"");

            }

            wh.addCorpo(obj.call(modelBuilder.getSetter(atr.getName()).getName(), arg));

        }

        wh.addCorpo(lista.call("add", obj.getReferencia()));
        methodBuilder.addCorpo(wh.toString());
        VarBuilder tmp = new VarBuilder("Statement", "closeOperation");
        tmp.setClasse(this);
        methodBuilder.addCorpo(tmp.getDeclaração("(Statement) " + stmt.getReferencia()));
        methodBuilder.addCorpo(tmp.call("close"));
        methodBuilder.addCorpo(rs.call("close"));

        methodBuilder.setReturn(lista.getReferencia());
        addMethod(methodBuilder);

    }

    public ModelBuilder getModelo() {
        return modelBuilder;
    }

    public void setModelo(ModelBuilder modelBuilder) {
        this.modelBuilder = modelBuilder;
    }

    public AttributeBuilder getListaDados() {
        return conexão;
    }

    public void setListaDados(AttributeBuilder listaDados) {
        this.conexão = listaDados;
    }

    public MethodBuilder getMétodoAdd() {
        return methodBuilderAdd;
    }

    public void setMétodoAdd(MethodBuilder methodBuilderAdd) {
        this.methodBuilderAdd = methodBuilderAdd;
    }

    public MethodBuilder getMétodoRemove() {
        return methodBuilderRemove;
    }

    public void setMétodoRemove(MethodBuilder methodBuilderRemove) {
        this.methodBuilderRemove = methodBuilderRemove;
    }

    public MethodBuilder getMétodoUpdate() {
        return methodBuilderUpdate;
    }

    public void setMétodoUpdate(MethodBuilder methodBuilderUpdate) {
        this.methodBuilderUpdate = methodBuilderUpdate;
    }

    public List<MethodBuilder> getMetodosSearch() {
        return metodosSearch;
    }

    public void setMetodosSearch(List<MethodBuilder> metodosSearch) {
        this.metodosSearch = metodosSearch;
    }

}
