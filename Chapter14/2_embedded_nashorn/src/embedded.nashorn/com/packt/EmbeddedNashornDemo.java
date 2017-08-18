package com.packt;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import java.io.*;
public class EmbeddedNashornDemo {
    public static void main(String[] args) throws Exception{
        
        NashornScriptEngineFactory factory = new NashornScriptEngineFactory();
        ScriptEngine engine = factory.getScriptEngine("--language=es6");
        
        //Javascript function
        engine.eval("function sum(a, b) { return a + b; }");
        System.out.println(engine.eval("sum(1, 2);"));

        //Template strings
        engine.eval("let name = 'Sanaulla'");
        System.out.println(engine.eval("print(`Hello Mr. ${name}`)"));

        //Set
        engine.eval("var s = new Set(); s.add(1).add(2).add(3).add(4).add(5).add(6);");
        System.out.println("Set elements");
        engine.eval("for (let e of s) { print(e); }");
        
        //Reading Javascript source
        engine.eval(new FileReader("src/embedded.nashorn/com/packt/embeddable.js"));
        int difference = (int)engine.eval("difference(1, 2);");
        System.out.println("Difference between 1, 2 is: " + difference);
    }
}