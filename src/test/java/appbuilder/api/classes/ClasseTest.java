/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.api.classes;

import appbuilder.api.methods.Método;
import appbuilder.api.methods.Parametro;
import appbuilder.api.packages.Importação;
import appbuilder.api.packages.Pacote;
import appbuilder.api.vars.Atributo;
import appbuilder.api.vars.Objeto;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author psilva
 */
public class ClasseTest {
    
    public ClasseTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of setSuperClasse method, of class Classe.
     */
    @Test
    public void testSetSuperClasse_String() {
        System.out.println("setSuperClasse");
        String nomeClasse = "";
        Classe instance = null;
        instance.setSuperClasse(nomeClasse);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSuperClasse method, of class Classe.
     */
    @Test
    public void testSetSuperClasse_Classe() throws Exception {
        System.out.println("setSuperClasse");
        Classe superClasse = null;
        Classe instance = null;
        instance.setSuperClasse(superClasse);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSuperClasse method, of class Classe.
     */
    @Test
    public void testGetSuperClasse() {
        System.out.println("getSuperClasse");
        Classe instance = null;
        Classe expResult = null;
        Classe result = instance.getSuperClasse();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setInterface method, of class Classe.
     */
    @Test
    public void testSetInterface() {
        System.out.println("setInterface");
        boolean b = false;
        Classe instance = null;
        instance.setInterface(b);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of éInterface method, of class Classe.
     */
    @Test
    public void testÉInterface() {
        System.out.println("\u00e9Interface");
        Classe instance = null;
        boolean expResult = false;
        boolean result = instance.éInterface();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addInterface method, of class Classe.
     */
    @Test
    public void testAddInterface() {
        System.out.println("addInterface");
        Interface in = null;
        Classe instance = null;
        boolean expResult = false;
        boolean result = instance.addInterface(in);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeInterface method, of class Classe.
     */
    @Test
    public void testRemoveInterface() {
        System.out.println("removeInterface");
        Interface in = null;
        Classe instance = null;
        boolean expResult = false;
        boolean result = instance.removeInterface(in);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPrincipal method, of class Classe.
     */
    @Test
    public void testSetPrincipal() {
        System.out.println("setPrincipal");
        boolean b = false;
        Classe instance = null;
        boolean expResult = false;
        boolean result = instance.setPrincipal(b);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getInstancia method, of class Classe.
     */
    @Test
    public void testGetInstancia() {
        System.out.println("getInstancia");
        String[] argumentos = null;
        Classe instance = null;
        Objeto expResult = null;
        Objeto result = instance.getInstancia(argumentos);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of get method, of class Classe.
     */
    @Test
    public void testGet() {
        System.out.println("get");
        String nome = "";
        String[] argumentos = null;
        Objeto expResult = null;
        Objeto result = Classe.get(nome, argumentos);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPacote method, of class Classe.
     */
    @Test
    public void testGetPacote() {
        System.out.println("getPacote");
        Classe instance = null;
        Pacote expResult = null;
        Pacote result = instance.getPacote();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPacote method, of class Classe.
     */
    @Test
    public void testSetPacote_Pacote() {
        System.out.println("setPacote");
        Pacote pacote = null;
        Classe instance = null;
        instance.setPacote(pacote);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPacote method, of class Classe.
     */
    @Test
    public void testSetPacote_String() {
        System.out.println("setPacote");
        String pacote = "";
        Classe instance = null;
        instance.setPacote(pacote);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getConstrutorPrincipal method, of class Classe.
     */
    @Test
    public void testGetConstrutorPrincipal() {
        System.out.println("getConstrutorPrincipal");
        Classe instance = null;
        Construtor expResult = null;
        Construtor result = instance.getConstrutorPrincipal();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setConstrutorPrincipal method, of class Classe.
     */
    @Test
    public void testSetConstrutorPrincipal_Construtor() {
        System.out.println("setConstrutorPrincipal");
        Construtor construtorPrincipal = null;
        Classe instance = null;
        instance.setConstrutorPrincipal(construtorPrincipal);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNomeCompleto method, of class Classe.
     */
    @Test
    public void testGetNomeCompleto_0args() {
        System.out.println("getNomeCompleto");
        Classe instance = null;
        String expResult = "";
        String result = instance.getNomeCompleto();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMain method, of class Classe.
     */
    @Test
    public void testGetMain() {
        System.out.println("getMain");
        Classe instance = null;
        Método expResult = null;
        Método result = instance.getMain();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Classe.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Classe instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addAtributo method, of class Classe.
     */
    @Test
    public void testAddAtributo() {
        System.out.println("addAtributo");
        Atributo atr = null;
        Classe instance = null;
        boolean expResult = false;
        boolean result = instance.addAtributo(atr);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addConstrutor method, of class Classe.
     */
    @Test
    public void testAddConstrutor_Construtor() {
        System.out.println("addConstrutor");
        Construtor construtor = null;
        Classe instance = null;
        boolean expResult = false;
        boolean result = instance.addConstrutor(construtor);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeConstrutor method, of class Classe.
     */
    @Test
    public void testRemoveConstrutor() {
        System.out.println("removeConstrutor");
        Construtor construtor = null;
        Classe instance = null;
        boolean expResult = false;
        boolean result = instance.removeConstrutor(construtor);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addConstrutor method, of class Classe.
     */
    @Test
    public void testAddConstrutor_String_ParametroArr() {
        System.out.println("addConstrutor");
        String modAcesso = "";
        Parametro[] params = null;
        Classe instance = null;
        Construtor expResult = null;
        Construtor result = instance.addConstrutor(modAcesso, params);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addConstrutorPúblico method, of class Classe.
     */
    @Test
    public void testAddConstrutorPúblico() {
        System.out.println("addConstrutorP\u00fablico");
        Parametro[] params = null;
        Classe instance = null;
        Construtor expResult = null;
        Construtor result = instance.addConstrutorPúblico(params);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addConstrutorPrivado method, of class Classe.
     */
    @Test
    public void testAddConstrutorPrivado() {
        System.out.println("addConstrutorPrivado");
        Parametro[] params = null;
        Classe instance = null;
        Construtor expResult = null;
        Construtor result = instance.addConstrutorPrivado(params);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addMétodo method, of class Classe.
     */
    @Test
    public void testAddMétodo() {
        System.out.println("addM\u00e9todo");
        Método metodo = null;
        Classe instance = null;
        boolean expResult = false;
        boolean result = instance.addMétodo(metodo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeMétodo method, of class Classe.
     */
    @Test
    public void testRemoveMétodo() {
        System.out.println("removeM\u00e9todo");
        Método metodo = null;
        Classe instance = null;
        boolean expResult = false;
        boolean result = instance.removeMétodo(metodo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addGetter method, of class Classe.
     */
    @Test
    public void testAddGetter() {
        System.out.println("addGetter");
        Atributo atributo = null;
        Classe instance = null;
        boolean expResult = false;
        boolean result = instance.addGetter(atributo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addSetter method, of class Classe.
     */
    @Test
    public void testAddSetter() {
        System.out.println("addSetter");
        Atributo atributo = null;
        Classe instance = null;
        boolean expResult = false;
        boolean result = instance.addSetter(atributo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGetter method, of class Classe.
     */
    @Test
    public void testGetGetter() {
        System.out.println("getGetter");
        String atributo = "";
        Classe instance = null;
        Método expResult = null;
        Método result = instance.getGetter(atributo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSetter method, of class Classe.
     */
    @Test
    public void testGetSetter() {
        System.out.println("getSetter");
        String atributo = "";
        Classe instance = null;
        Método expResult = null;
        Método result = instance.getSetter(atributo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getModAcesso method, of class Classe.
     */
    @Test
    public void testGetModAcesso() {
        System.out.println("getModAcesso");
        Classe instance = null;
        String expResult = "";
        String result = instance.getModAcesso();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setModAcesso method, of class Classe.
     */
    @Test
    public void testSetModAcesso() {
        System.out.println("setModAcesso");
        String modAcesso = "";
        Classe instance = null;
        instance.setModAcesso(modAcesso);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNome method, of class Classe.
     */
    @Test
    public void testGetNome() {
        System.out.println("getNome");
        Classe instance = null;
        String expResult = "";
        String result = instance.getNome();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setNome method, of class Classe.
     */
    @Test
    public void testSetNome() {
        System.out.println("setNome");
        String nome = "";
        Classe instance = null;
        instance.setNome(nome);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAtributos method, of class Classe.
     */
    @Test
    public void testGetAtributos() {
        System.out.println("getAtributos");
        Classe instance = null;
        List<Atributo> expResult = null;
        List<Atributo> result = instance.getAtributos();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAtributos method, of class Classe.
     */
    @Test
    public void testSetAtributos() {
        System.out.println("setAtributos");
        List<Atributo> atributos = null;
        Classe instance = null;
        instance.setAtributos(atributos);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMétodos method, of class Classe.
     */
    @Test
    public void testGetMétodos() {
        System.out.println("getM\u00e9todos");
        Classe instance = null;
        List<Método> expResult = null;
        List<Método> result = instance.getMétodos();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setMetódos method, of class Classe.
     */
    @Test
    public void testSetMetódos() {
        System.out.println("setMet\u00f3dos");
        List<Método> metódos = null;
        Classe instance = null;
        instance.setMetódos(metódos);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getImportações method, of class Classe.
     */
    @Test
    public void testGetImportações() {
        System.out.println("getImporta\u00e7\u00f5es");
        Classe instance = null;
        List<Importação> expResult = null;
        List<Importação> result = instance.getImportações();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setImportações method, of class Classe.
     */
    @Test
    public void testSetImportações() {
        System.out.println("setImporta\u00e7\u00f5es");
        List<Importação> importações = null;
        Classe instance = null;
        instance.setImportações(importações);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setConstrutorPrincipal method, of class Classe.
     */
    @Test
    public void testSetConstrutorPrincipal_String_String() {
        System.out.println("setConstrutorPrincipal");
        String apublic = "";
        String nome = "";
        Classe instance = null;
        instance.setConstrutorPrincipal(apublic, nome);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAtributo method, of class Classe.
     */
    @Test
    public void testGetAtributo() {
        System.out.println("getAtributo");
        String nome = "";
        Classe instance = null;
        Atributo expResult = null;
        Atributo result = instance.getAtributo(nome);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of temAtributo method, of class Classe.
     */
    @Test
    public void testTemAtributo_String() {
        System.out.println("temAtributo");
        String nome = "";
        Classe instance = null;
        boolean expResult = false;
        boolean result = instance.temAtributo(nome);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of temAtributo method, of class Classe.
     */
    @Test
    public void testTemAtributo_Atributo() {
        System.out.println("temAtributo");
        Atributo atributo = null;
        Classe instance = null;
        boolean expResult = false;
        boolean result = instance.temAtributo(atributo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMétodo method, of class Classe.
     */
    @Test
    public void testGetMétodo() {
        System.out.println("getM\u00e9todo");
        String nome = "";
        Classe instance = null;
        Método expResult = null;
        Método result = instance.getMétodo(nome);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of temMétodo method, of class Classe.
     */
    @Test
    public void testTemMétodo() {
        System.out.println("temM\u00e9todo");
        Método metodo = null;
        Classe instance = null;
        boolean expResult = false;
        boolean result = instance.temMétodo(metodo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addClasse method, of class Classe.
     */
    @Test
    public void testAddClasse_3args() throws Exception {
        System.out.println("addClasse");
        String nome = "";
        String pacote = "";
        String caminho = "";
        Classe expResult = null;
        Classe result = Classe.addClasse(nome, pacote, caminho);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addClasse method, of class Classe.
     */
    @Test
    public void testAddClasse_Classe() {
        System.out.println("addClasse");
        Classe classe = null;
        Classe expResult = null;
        Classe result = Classe.addClasse(classe);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addNomeCompleto method, of class Classe.
     */
    @Test
    public void testAddNomeCompleto() {
        System.out.println("addNomeCompleto");
        String nome = "";
        String nomeCompleto = "";
        Classe instance = null;
        String expResult = "";
        String result = instance.addNomeCompleto(nome, nomeCompleto);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNomeCompleto method, of class Classe.
     */
    @Test
    public void testGetNomeCompleto_String() {
        System.out.println("getNomeCompleto");
        String nome = "";
        Classe instance = null;
        String expResult = "";
        String result = instance.getNomeCompleto(nome);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getClasseEstática method, of class Classe.
     */
    @Test
    public void testGetClasseEstática() {
        System.out.println("getClasseEst\u00e1tica");
        String nome = "";
        Classe expResult = null;
        Classe result = Classe.getClasseEstática(nome);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getClasse method, of class Classe.
     */
    @Test
    public void testGetClasse() {
        System.out.println("getClasse");
        String nome = "";
        Classe instance = null;
        Classe expResult = null;
        Classe result = instance.getClasse(nome);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of callStatic method, of class Classe.
     */
    @Test
    public void testCallStatic() {
        System.out.println("callStatic");
        String método = "";
        String[] args = null;
        Classe instance = null;
        Método expResult = null;
        Método result = instance.callStatic(método, args);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addImportação method, of class Classe.
     */
    @Test
    public void testAddImportação() {
        System.out.println("addImporta\u00e7\u00e3o");
        Classe classe = null;
        Classe instance = null;
        boolean expResult = false;
        boolean result = instance.addImportação(classe);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getImportação method, of class Classe.
     */
    @Test
    public void testGetImportação() {
        System.out.println("getImporta\u00e7\u00e3o");
        String classe = "";
        Classe instance = null;
        String expResult = "";
        String result = instance.getImportação(classe);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setModNAcesso method, of class Classe.
     */
    @Test
    public void testSetModNAcesso() {
        System.out.println("setModNAcesso");
        List<String> modsnacesso = null;
        Classe instance = null;
        instance.setModNAcesso(modsnacesso);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addModNAcesso method, of class Classe.
     */
    @Test
    public void testAddModNAcesso() {
        System.out.println("addModNAcesso");
        String mod = "";
        Classe instance = null;
        boolean expResult = false;
        boolean result = instance.addModNAcesso(mod);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeModNAcesso method, of class Classe.
     */
    @Test
    public void testRemoveModNAcesso() {
        System.out.println("removeModNAcesso");
        String mod = "";
        Classe instance = null;
        boolean expResult = false;
        boolean result = instance.removeModNAcesso(mod);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getModNAcesso method, of class Classe.
     */
    @Test
    public void testGetModNAcesso() {
        System.out.println("getModNAcesso");
        Classe instance = null;
        List<String> expResult = null;
        List<String> result = instance.getModNAcesso();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
