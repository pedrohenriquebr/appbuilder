namespace Api.Old.Classes;

/// <summary>
/// </summary>
/// <remarks>@authorPedro Henrique Braga da Silva</remarks>
public class ClassBuilder
{
    private static readonly Logger logger = Logger.GetLogger(Level.INFO.GetName());
    private static readonly Handler handler = new ConsoleHandler();

    /// <summary>
    ///     classes prontas, usando api de reflection /*nome totalmente qualificado
    ///     com a classe pronta
    /// </summary>
    private static Map<string, ClassBuilder> classes = new HashMap();

    // serve para indicar se está executando o trecho estático de adição de classes
    private static readonly bool STATIC_CONTEXT;

    private bool _isArray;
    protected string accessModifier; // modificador de acesso ,Ex: public, private
    private IList<AttributeBuilder> attributes = new List<AttributeBuilder>();

    /// <summary>
    ///     Associa nome da classe com o nome totalmente qualificado Uma espécie de
    ///     apelido, Ex: String -> java.lang.String
    /// </summary>
    private Map<string, string> fullNames = new HashMap();

    private bool hasGenerics;
    private IList<ImportBuilder> imports = new List<ImportBuilder>();
    private readonly IList<InterfaceBuilder> interfaces = new List<InterfaceBuilder>();

    private bool isInterface;

    // método principal
    private MethodBuilder mainMethod;
    private IList<MethodBuilder> methods = new List<MethodBuilder>();

    protected string name;

    // modificadores de não-acesso
    private IList<string> nonAccessModifiers = new List();

    protected PackageBuilder packageBuilder;

    // construtor principal
    private ConstructorBuilder principalConstructorBuilder;

    /// <summary>
    ///     A superclasse
    /// </summary>
    private ClassBuilder superClassBuilder;

    // deixo classes prontas, já predefinidas
    static ClassBuilder()
    {
        handler.SetFormatter(new SimpleFormatter());
        logger.AddHandler(handler);
        logger.SetFilter(new Testes());
        logger.Log(Level.INFO, "começo: adicionando classes");
        STATIC_CONTEXT = true;
        try
        {
            //suporte para classe Exceção
            AddClassBuilder("Object", "lang", "java");
            AddClassBuilder("Exception", "lang", "java");
            AddClassBuilder("RuntimeException", "lang", "java");
            AddClassBuilder("List", "util", "java");
            AddClassBuilder("ArrayList", "util", "java");
            AddClassBuilder("SQLException", "sql", "java");
            AddClassBuilder("NullPointerException", "lang", "java");
            AddClassBuilder("CloneNotSupportedException", "lang", "java");
            AddClassBuilder("Connection", "sql", "java");
            AddClassBuilder("DriverManager", "sql", "java");
            AddClassBuilder("PreparedStatement", "sql", "java");
            AddClassBuilder("Statement", "sql", "java");
            AddClassBuilder("ResultSet", "sql", "java");
            AddClassBuilder("Date", "sql", "java");
            AddClassBuilder("Calendar", "util", "java");
            AddClassBuilder("Runnable", "lang", "java");
            AddClassBuilder("Thread", "lang", "java");
            AddClassBuilder("EventQueue", "awt", "java");
        }
        catch (ClassNotFoundException ex)
        {
            logger.Log(Level.SEVERE, ex.GetMessage() + "");
            System.@out.Println("Não foi possível carregar as classes");
        }

        logger.Log(Level.INFO, "fim: adicionando classes");
        STATIC_CONTEXT = false;
    }

    public ClassBuilder(string name)
    {
        this.name = name;
        accessModifier = "public";
        packageBuilder = new PackageBuilder(name.ToLowerCase());
        if (!STATIC_CONTEXT)
        {
            AddImport("java.lang.Object");
            AddImport("java.lang.Runnable");
            AddImport("java.lang.Exception");
            AddImport("java.lang.RuntimeException");
            AddImport("java.lang.Thread");
        }
    }

    public ClassBuilder(string name, string pacote) : this(name)
    {
        packageBuilder = new PackageBuilder(pacote);
    }

    public ClassBuilder(string name, string pacote, string caminho) : this(name)
    {
        packageBuilder = new PackageBuilder(pacote, caminho);
    }

    public static Handler GetHandler()
    {
        return handler;
    }

    public virtual void SetHasGenerics(bool v)
    {
        hasGenerics = v;
    }

    public virtual bool IsHasGenerics()
    {
        return hasGenerics;
    }

    public virtual string UpperCase(string line)
    {
        return Character.ToUpperCase(line.CharAt(0)) + line.Substring(1);
    }

    /// <summary>
    ///     Define a superclasse com base nos pacotes ou classes já importadas Não
    ///     pode ter uma super classe que não esteja dentro das importações
    /// </summary>
    /// <param name="className"></param>
    /// <param name="superClasse"></param>
    public virtual void SetSuperClass(string className)
    {
        var classBuilder = GetClasse(className);
        logger.Log(Level.INFO, "Superclasse definida: " + classBuilder.GetName());
        try
        {
            SetSuperClass(classBuilder);
        }
        catch (NullPointerException ex)
        {
            ex.PrintStackTrace();
            logger.Log(Level.INFO, "superclasse nula!");
        }
    }

    public virtual void SetSuperClass(ClassBuilder superClassBuilder)
    {
        this.superClassBuilder = superClassBuilder;
        IList<string> ownAttributes = new List();
        foreach (AttributeBuilder atr in GetAttributes()) ownAttributes.Add(atr.GetName());

        foreach (AttributeBuilder atr in superClassBuilder.GetAttributes())
            if (!ownAttributes.Contains(atr.GetName()))
                AddAttribute(atr);

        IList<string> ownMethods = new List();
        foreach (MethodBuilder met in GetMethods()) ownMethods.Add(met.GetName());

        foreach (MethodBuilder met in superClassBuilder.GetMethods())
            if (!FindMethod(met))
            {
                logger.Log(Level.WARNING, GetName() + ": Adicionando método : " + met.GetSignature());
                if (met is ConstructorBuilder)
                {
                    logger.Log(Level.INFO, GetName() + ": construtor encontrado: " + met.GetSignature());
                    var constructorBuilder = (ConstructorBuilder)met.Clone();
                    constructorBuilder.SetName(GetName());
                    AddConstrutor(constructorBuilder);
                }
                else
                {
                    AddMethod(met);
                }
            }
    }

    public virtual ClassBuilder GetSuperClasse()
    {
        return superClassBuilder;
    }

    public virtual void SetVetor(bool b)
    {
        _isArray = b;
        SetSuperClass("Object");
    }

    public virtual bool IsArray()
    {
        return _isArray;
    }

    /// <summary>
    ///     Quando true, ele adiciona automaticamente o modificador interface Quando
    ///     false, ele remove automaticamenteo o modificador interface
    /// </summary>
    /// <param name="b"></param>
    public virtual void SetInterface(bool b)
    {
        isInterface = b;
        if (b)
            AddNonAccessMod("interface");
        else
            RemoveNonAccessMod("interface");
    }

    public virtual bool ÉUmaInterface()
    {
        return isInterface;
    }

    public virtual bool AddInterface(InterfaceBuilder @in)
    {
        foreach (MethodBuilder met in @in.GetMethods())
            // remove aquele flag de ser método de uma interface
            // e implementa
            try
            {
                MethodBuilder metodo = (MethodBuilder)met.Clone();
                metodo.SetBelongsInterface(false);
                metodo.RemoveModNacesso("abstract");
                AddMethod(metodo);
            }
            catch (CloneNotSupportedException c)
            {
                c.PrintStackTrace();
            }

        return interfaces.Add(@in);
    }

    public virtual bool RemoveInterface(InterfaceBuilder @in)
    {
        return interfaces.Remove(@in);
    }

    /// <summary>
    ///     Define se essa classe é principal ou não, ou seja, se tem "public static
    ///     voi main(String [] args )"
    /// </summary>
    /// <param name="b"></param>
    /// <returns></returns>
    public virtual bool SetPrincipal(bool b)
    {
        if (b)
        {
            mainMethod = new MethodBuilder("public", "static", "void", "main");
            mainMethod.AddParameters("String[]", "args");
            return AddMethod(mainMethod);
        }

        var rt = methods.Remove(mainMethod);
        mainMethod = null;
        return rt;
    }

    // referente quanto na forma de objeto
    public virtual ObjectBuilder GetInstancia(params string[] argumentos)
    {
        // defino o tipo do objeto, que é instância da classe
        ObjectBuilder obj = new ObjectBuilder(this);
        obj.SetInstancia(argumentos); // defino os argumentos da instância dele
        return obj; // devolvo ele
    }

    /// <summary>
    ///     Retorna uma instância de uma classe já predefinida
    /// </summary>
    /// <param name="nome"></param>
    /// <param name="argumentos"></param>
    /// <returns></returns>
    public static ObjectBuilder Get(string nome, params string[] argumentos)
    {
        return classes[nome].GetInstancia(argumentos);
    }

    public virtual PackageBuilder GetPacote()
    {
        return packageBuilder;
    }

    public virtual void SetPacote(PackageBuilder packageBuilder)
    {
        this.packageBuilder = packageBuilder;
    }

    public virtual void SetPacote(string pacote)
    {
        packageBuilder = new PackageBuilder(pacote);
    }

    public virtual ConstructorBuilder GetPrincipalConstructor()
    {
        return principalConstructorBuilder;
    }

    public virtual void SetPrincipalConstructor(ConstructorBuilder principalConstructorBuilder)
    {
        this.principalConstructorBuilder = principalConstructorBuilder;
    }

    public virtual string GetNomeCompleto()
    {
        return packageBuilder.GetCaminho() + "." + GetName();
    }

    public virtual MethodBuilder GetMain()
    {
        return mainMethod;
    }

    public override string ToString()
    {
        var codigo = "";

        /// <summary>
        /// PACOTE
        /// </summary>
        codigo += packageBuilder + ";\\n\\n";

        /// <summary>
        /// IMPORTAÇÕES
        /// </summary>
        foreach (ImportBuilder importação in imports)
            // não exibir, por padrão já vem importado
            if (!importação.GetCaminho().Equals("java.lang"))
                codigo += importação + ";\\n\\n";


        /// <summary>
        /// MODIFICADOR DE ACESSO
        /// </summary>
        codigo += accessModifier;

        /// <summary>
        /// INTERFACE
        /// </summary>
        // indica se é uma interface ou não
        var tipo = isInterface ? "interface" : "class";

        /// <summary>
        /// MODIFICADORES DE NÃO-ACESSO
        /// </summary>
        foreach (var mod in nonAccessModifiers)
        {
            // pula modificador de interface, pra não repetir
            if (mod.Equals("interface")) continue;


            // se é uma interface, então não precisa colocar o abstract
            if (tipo.Equals("interface") && mod.Equals("abstract")) continue;

            codigo += " " + mod + " ";
        }


        /// <summary>
        /// TIPO DE CLASSE
        /// </summary>
        // tipo indentifica se é interface ou classe
        codigo += " " + tipo + " " + name;
        logger.Log(Level.INFO, "exibir nome da superclasse");

        // coloca o extends caso tenha uma superclasse
        /// <summary>
        /// SUPERCLASSE
        /// </summary>
        if (superClassBuilder != null)
        {
            logger.Log(Level.INFO, "exibindo superclasse superclasse existe");
            if (!superClassBuilder.GetName().Equals("Object")) codigo += " extends " + superClassBuilder.GetName();
        }

        logger.Log(Level.INFO, "Verificando se implementa alguma interface");

        /// <summary>
        /// INTERFACES
        /// </summary>
        if (interfaces.Count > 0)
        {
            codigo += " implements ";
            var contador = 1;
            foreach (InterfaceBuilder in in interfaces)
            {
                if (contador % 2 == 0)
                {
                    contador = 1;
                    codigo += ", ";
                }

                codigo += @in.GetName();
                contador++;
            }
        }

        codigo += " { \\n\\n";

        /// <summary>
        /// ATRIBUTOS
        /// </summary>
        foreach (AttributeBuilder var in attributes)
            if (superClassBuilder != null)
            {
                if (!superClassBuilder.HasAttribute(var.GetName())) codigo += var.GetDeclaração();
            }
            else
            {
                codigo += var.GetDeclaração();
            }

        logger.Log(Level.INFO, "exibir métodos");

        /// <summary>
        /// MÉTODOS
        /// </summary>
        foreach (MethodBuilder met in methods)
            // verifica se a superclasse não tem esse método
            // se tiver, não precisa exibir
            if (superClassBuilder != null)
            {
                if (!superClassBuilder.FindMethod(met))
                {
                    codigo += met;
                    logger.Log(Level.INFO, GetName() + ": superclasse não tem " + met.GetSignature());
                }
                else
                {
                    logger.Log(Level.INFO, GetName() + ": superclasse tem " + met.GetSignature());
                }
            }
            else
            {
                codigo += met;
            }

        codigo += "} \\n\\n";
        return codigo;
    }

    /// <summary>
    ///     Adiciona um atributo à classe. Todo atributo tem uma classe na qual ele
    ///     pertenc, então é passado para setClasse a classe que está adicionando o
    ///     atributo
    /// </summary>
    /// <param name="atr">atributo</param>
    /// <returns>true ou false se a operação foi realizada com sucesso</returns>
    public virtual bool AddAttribute(AttributeBuilder atr)
    {
        atr.SetClasse(this);
        return attributes.Add(atr);
    }

    public virtual bool AddConstrutor(ConstructorBuilder constructorBuilder)
    {
        return methods.Add(constructorBuilder);
    }

    public virtual bool RemoveConstrutor(ConstructorBuilder constructorBuilder)
    {
        return methods.Remove(constructorBuilder);
    }

    // leva em consideração que pode ter vários parâmetros de tipos diferentes e o
    // modificador pode ser genérico também,
    // adicionar construtor genérico
    public virtual ConstructorBuilder AddConstrutor(string modAcesso, params ParameterBuilder[] @params)
    {
        var constructorBuilder = new ConstructorBuilder(modAcesso, name);
        IList<ParameterBuilder> lista = new List();
        foreach (ParameterBuilder param in @params) lista.Add(param);

        constructorBuilder.SetParameters(lista);
        if (AddConstrutor(constructorBuilder))
            return constructorBuilder;
        return null;
    }

    // especializa o método acima, para adicionar construtores públicos para
    // reaproveitamento de código
    public virtual ConstructorBuilder AddConstrutorPúblico(params ParameterBuilder[] @params)
    {
        return AddConstrutor("public", @params);
    }

    // construtor privado
    public virtual ConstructorBuilder AddConstrutorPrivado(params ParameterBuilder[] @params)
    {
        return AddConstrutor("private", @params);
    }

    public virtual bool AddMethod(MethodBuilder metodo)
    {
        return methods.Add(metodo);
    }

    public virtual bool RemoveMétodo(MethodBuilder metodo)
    {
        return methods.Remove(metodo);
    }

    public virtual bool AddGetter(AttributeBuilder atributo)
    {
        MethodBuilder getter = new MethodBuilder("public", atributo.GetTipo(), "get" + UpperCase(atributo.GetName()));
        getter.SetReturn(atributo.GetReferencia());
        return AddMethod(getter);
    }

    public virtual bool AddSetter(AttributeBuilder atributo)
    {
        MethodBuilder setter = new MethodBuilder("public", "void", "set" + UpperCase(atributo.GetName()));
        setter.AddParameters(atributo.GetTipo(), atributo.GetName());
        setter.SetBody(atributo.GetInicialização(atributo.GetName()));
        return AddMethod(setter);
    }

    // adicionar construtores associados a atributos
    /// <summary>
    ///     Pegar um getter associado a um atributo
    /// </summary>
    /// <param name="atributo">nome do atributo já declarado no modelo</param>
    /// <returns></returns>
    public virtual MethodBuilder GetGetter(string atributo)
    {
        MethodBuilder methodBuilder = null;
        var camelCase = "get" + UpperCase(atributo);
        methodBuilder = GetMethodBuilder(camelCase);
        return methodBuilder;
    }

    // retorna o setter com base no nome do atributo associado
    public virtual MethodBuilder GetSetter(string atributo)
    {
        MethodBuilder methodBuilder = null;
        var camelCase = "set" + UpperCase(atributo);
        methodBuilder = GetMethodBuilder(camelCase);
        return methodBuilder;
    }

    public virtual string GetAccessModifier()
    {
        return accessModifier;
    }

    public virtual void SetAccessModifier(string accessModifier)
    {
        this.accessModifier = accessModifier;
    }

    public virtual string GetName()
    {
        return name;
    }

    public virtual void SetName(string name)
    {
        this.name = name;
    }

    public virtual IList<AttributeBuilder> GetAttributes()
    {
        return attributes;
    }

    public virtual void SetAttributes(IList<AttributeBuilder> attributes)
    {
        this.attributes = attributes;
    }

    public virtual IList<MethodBuilder> GetMethods()
    {
        return methods;
    }

    public virtual void SetMethods(IList<MethodBuilder> m)
    {
        methods = m;
    }

    public virtual IList<ImportBuilder> GetImports()
    {
        return imports;
    }

    public virtual void SetImports(IList<ImportBuilder> imports)
    {
        this.imports = imports;
    }

    public virtual void SetPrincipalConstructor(string _public, string name)
    {
        SetPrincipalConstructor(new ConstructorBuilder(_public, name));
    }

    /// <summary>
    ///     Retorna objeto do atributo com base no nome passado
    /// </summary>
    /// <param name="nome">nome do atributo</param>
    /// <returns>Atributo com o nome especificado</returns>
    public virtual AttributeBuilder GetAttribute(string nome)
    {
        AttributeBuilder attr = null;
        foreach (AttributeBuilder atr in GetAttributes())
            if (atr.GetName().Equals(nome))
                attr = atr;

        return attr;
    }

    public virtual bool HasAttribute(string name)
    {
        return HasAttribute(GetAttribute(name));
    }

    public virtual bool HasAttribute(AttributeBuilder attr)
    {
        return attributes.Contains(attr);
    }

    // retorna o método com base no nome
    public virtual MethodBuilder GetMethodBuilder(string nome)
    {
        MethodBuilder methodBuilder = null;
        foreach (MethodBuilder met in GetMethods())
            if (met.GetName().Equals(nome))
                methodBuilder = met;

        if (superClassBuilder != null)
            foreach (MethodBuilder met in superClassBuilder.GetMethods())
                if (met.GetName().Equals(nome))
                    methodBuilder = met;

        return methodBuilder;
    }

    public virtual bool HasMethod(MethodBuilder method)
    {
        return methods.Contains(method);
    }

    public virtual bool HasMethod(string método)
    {
        var retorno = false;
        foreach (MethodBuilder m in methods)
            if (m.GetName().Equals(método))
                return true;

        return retorno;
    }

    public virtual bool FindMethod(MethodBuilder method)
    {
        var ret = false;
        foreach (MethodBuilder met in GetMethods())
        {
            //compare name
            string nome = met.GetName();

            //comparar os parâmetros e quantity de parâmetros
            IList<ParameterBuilder> params = met.GetParameters();
            int quantity = @params.Count; //quantity de parâmetros
            var countParams = 0; //quantity de parâmetros iguais
            if (quantity == method.GetParameters().Count)
                for (var j = 0; j < quantity; j++)
                {
                    ParameterBuilder p1 = @params[j];
                    ParameterBuilder p2 = method.GetParameter(j);
                    if (p1.GetTipo().Equals(p2.GetTipo())) countParams++;
                }


            //para ser igual deve ter: o nome, parâmetros e tipo de retorno igual
            if (method.GetName().Equals(nome) && method.GetParameters().Count == quantity && countParams == quantity &&
                method.GetReturnType().Equals(met.GetReturnType())) return true;
        }

        return ret;
    }

    // cria uma classe a partir de uma já predefinida, classes prontas da linguagem
    // Java
    public static ClassBuilder AddClassBuilder(string nome, string pacote, string caminho)
    {
        ClassBuilder classBuilder = null;
        ClassBuilder classBuilderPai = null;
        if (nome.EndsWith("Exception") && !nome.Equals("Exception"))
        {
            logger.Log(Level.INFO, nome + ": é uma classe de exceção ");
            classBuilder = new ExceptionBuilder(nome, pacote, caminho);
        }
        else
        {
            classBuilder = new ClassBuilder(nome, pacote, caminho);
        }

        logger.Log(Level.INFO, nome + ": adicionando classe " + classBuilder.GetNomeCompleto());
        logger.Log(Level.INFO, classBuilder.GetName() + ": sendo pesquisada");

        // caso contrário, continua
        Class<TWildcardTodo> predefinida = Class.ForName(classBuilder.GetNomeCompleto());
        var modifiers = ModifiersFromInt(predefinida.GetModifiers());
        Class<TWildcardTodo> superClasse = predefinida.GetSuperclass();
        if (predefinida.IsInterface()) classBuilder = new InterfaceBuilder(nome, pacote, caminho);

        if (superClasse != null)
        {
            // defino a superclasse para minha metaclasse
            // infelizmente teve recursividade
            string nomePacoteCompleto = superClasse.GetPackage().GetName();
            string nomeClasseCompleto = superClasse.GetName();
            var nomePacoteDividido = nomePacoteCompleto.Split("\\\\.");
            var nomeClasseDividido = nomeClasseCompleto.Split("\\\\.");
            logger.Log(Level.INFO, classBuilder.GetNomeCompleto() + " tem superclasse " + superClasse.GetName());

            /// <summary>
            /// superClasse.getName() retorna o nome totalmente qualificado:
            /// java.lang.String superClasse.getPackage().getName() retorna o
            /// nome totalmente qualificado: java.lang O nome da classe é a
            /// última palavra, então eu precisei dividir por "." E precisei
            /// fazer recursividade
            /// </summary>
            try
            {
                classBuilderPai = AddClassBuilder(nomeClasseDividido[nomeClasseDividido.length - 1],
                    nomePacoteDividido[nomePacoteDividido.length - 1],
                    superClasse.GetPackage().GetName()
                        .Replace("." + nomePacoteDividido[nomePacoteDividido.length - 1], ""));
                classBuilder.SetSuperClass(classBuilderPai);
            }
            catch (CloneNotSupportedException e)
            {
                e.PrintStackTrace();
            }
        }


        // coloca o modificador de acesso
        classBuilder.SetAccessModifier(modifiers[0]);

        // modificadores de não-acesso
        IList<string> mnacesso = new List();
        for (var k = 1; k < modifiers.Count; k++)
        {
            var mod = modifiers[k];
            mnacesso.Add(mod);
        }


        // coloca os modificadores de não-acesso
        classBuilder.SetNonAccessMod(mnacesso);
        if (mnacesso.Contains("interface"))
        {
            classBuilder.SetInterface(true);
            classBuilder.RemoveConstrutor(classBuilder.GetPrincipalConstructor());
        }

        logger.Log(Level.INFO, "adicionando métodos");

        // pega os métodos declarados
        foreach (Method method in predefinida.GetDeclaredMethods())
        {
            string name = method.GetName();
            string retType = method.GetReturnType().GetSimpleName();
            Parameter[] params = method.GetParameters();
            var mods = ModifiersFromInt(method.GetModifiers());

            // por padrão o modificador de acesso é public
            MethodBuilder _metodo = new MethodBuilder("public", retType, name);
            logger.Log(Level.INFO, "adicionando método: " + _metodo.GetSignature());

            // em teoria o primeiro modificador é de acesso
            IList<string> modsnacesso = new List();

            // começando a partir do segundo modificador
            for (var j = 1; j < mods.Count; j++) modsnacesso.Add(mods[j]);


            // coloca todos os mdificadores de não-acesso
            _metodo.SetNonAccessMod(modsnacesso);
            foreach (Parameter param in @params)
            {
                Class<TWildcardTodo> cl = param.GetType();
                _metodo.AddParameters(cl.GetSimpleName(), param.GetName());
            }

            if (classBuilder.isInterface)
            {
                _metodo.SetBelongsInterface(true);
                logger.Log(Level.INFO, classBuilder.GetName() + " é interface ");
            }

            classBuilder.AddMethod(_metodo);

            /// <summary>
            /// 
            /// if (classePai != null) {
            /// 
            /// if (classePai.temMétodo(metodo)) { logger.log(Level.WARNING,
            /// "CLASSE PAI TEM O MESMO MÉTODO!"); } }
            /// </summary>
            logger.Log(Level.INFO, classBuilder.GetName() + ": adicionado método: {0}", _metodo.GetSignature());
        }


        //atributos públicos
        foreach (Field atributo in predefinida.GetDeclaredFields())
        {
            string tipo = atributo.GetType().GetName();
            var mods = ModifiersFromInt(atributo.GetModifiers());
            var modAcesso = mods[0];
            AttributeBuilder atr = new AttributeBuilder(modAcesso, tipo, atributo.GetName());
            for (var h = 1; h < mods.Count; h++) atr.AddModificador(mods[h]);

            classBuilder.AddAttribute(atr);
        }

        var contador = 0;

        // construtores declarados
        foreach (java.lang.reflect.Constructor<?> constructor in predefinida.GetDeclaredConstructors())
        {
            var mods = ModifiersFromInt(constructor.GetModifiers());
            var c = new ConstructorBuilder(mods[0], classBuilder.GetName());
            Parameter[] params = constructor.GetParameters();
            foreach (Parameter p in @params)
                c.AddParameters(new ParameterBuilder(p.GetType().GetSimpleName(), p.GetName()));

            for (var h = 1; h < mods.Count; h++) c.AddModNacesso(mods[h]);

            contador++;
            if (contador == 1) logger.Log(Level.INFO, "primeiro construtor encontrado: {0}", c.GetSignature());

            if (classBuilder.FindMethod(c)) continue;

            classBuilder.AddConstrutor(c);
        }

        AddClassBuilder(classBuilder);
        return classBuilder;
    }

    // adicionar uma metaclasse criada pelo usuário
    public static ClassBuilder AddClassBuilder(ClassBuilder classBuilder)
    {
        logger.Log(Level.INFO, "adicionado metaclasse " + classBuilder.GetName());
        if (classes[classBuilder.GetNomeCompleto()] != null)
            return classes.Replace(classBuilder.GetNomeCompleto(), classBuilder);

        classes.Put(classBuilder.GetNomeCompleto(), classBuilder);
        return classBuilder;
    }

    private static IList<string> ModifiersFromInt(int modifiers)
    {
        return Arrays.AsList(Modifier.ToString(modifiers).Split("\\\\s"));
    }

    /// <summary>
    ///     Adiciona o nome da classe com o nome totalmente qualificado dela
    /// </summary>
    /// <param name="name"></param>
    /// <returns>o caminhoCompleto</returns>
    public virtual string AddFullName(string name, string fullName)
    {
        if (fullNames.ContainsKey(name)) return "";

        return fullNames.Put(name, fullName);
    }

    public virtual string GetNomeCompleto(string name)
    {
        return fullNames[name];
    }

    public static ClassBuilder GetStaticCall(string name)
    {
        return classes[name];
    }

    // acessar uma classe importada
    public virtual ClassBuilder GetClasse(string name)
    {
        ClassBuilder cl = null;
        var generics = false;
        if (name.Contains("<") && name.Contains(">"))
        {
            name = name.Split("<")[0].Trim();
            generics = true;
        }

        if (name.Equals(GetName()) || name.Equals(GetNomeCompleto())) return this;

        if (name.Contains("."))
            cl = classes[name];
        else
            cl = classes[fullNames[name]];

        if (cl != null)
        {
            logger.Log(Level.INFO, "convertendo nome simples em nome completo: " + name + "=" + fullNames[name]);
            logger.Log(Level.INFO, "classe reconhecida: " + cl.GetNomeCompleto());
        }

        if (generics) cl.SetHasGenerics(true);

        return cl;
    }

    public virtual ExceptionBuilder GetExceção(string nome)
    {
        return (ExceptionBuilder)GetClasse(nome);
    }

    public virtual string CallStatic(string método, params string[] args)
    {
        return GetMethodBuilder(método).GetStaticCall(GetName(), args);
    }

    public virtual bool AddImport(ClassBuilder classBuilder)
    {
        AddFullName(classBuilder.GetName(), classBuilder.GetNomeCompleto());
        return imports.Add(new ImportBuilder(classBuilder.GetName(), classBuilder.GetPacote().GetCaminho()));
    }

    public virtual bool AddImport(string fullPackage)
    {
        return AddImport(GetStaticCall(fullPackage));
    }

    public virtual string GetImport(string classe)
    {
        var path = "";
        foreach (ImportBuilder _import in imports)
            if (_import.GetClasse().Equals(classe))
                path = _import.GetCaminho();

        return path;
    }

    public virtual void SetNonAccessMod(IList<string> modsnacesso)
    {
        nonAccessModifiers = modsnacesso;
    }

    /// <summary>
    ///     Adiciona um modificador de não-acesso
    /// </summary>
    /// <param name="mod"></param>
    /// <returns></returns>
    public virtual bool AddNonAccessMod(string mod)
    {
        return nonAccessModifiers.Add(mod);
    }

    public virtual bool RemoveNonAccessMod(string mod)
    {
        return nonAccessModifiers.Remove(mod);
    }

    public virtual IList<string> GetNonAccessMod()
    {
        return nonAccessModifiers;
    }

    public virtual IList<ConstructorBuilder> GetConstructors()
    {
        IList<ConstructorBuilder> list = new List();
        foreach (MethodBuilder m in GetMethods())
            if (m is ConstructorBuilder)
                list.Add((ConstructorBuilder)m);

        return list;
    }

    public virtual ExceptionTreatmentBuilder TratarExceção(params string[] exceções)
    {
        if (exceções.length == 0) return null;

        ExceptionTreatmentBuilder tratamento = ExceptionBuilder.Tratar(this, exceções);
        return tratamento;
    }

    public virtual string LançarExceção(string nome, params string[] args)
    {
        return ExceptionBuilder.Lançar(this, nome, args);
    }
}