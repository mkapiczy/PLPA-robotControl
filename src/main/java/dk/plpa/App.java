package dk.plpa;


import gnu.mapping.Environment;
import kawa.standard.Scheme;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static kawa.standard.Scheme.registerEnvironment;


public class App {


    public static void main(String[] args) {

        FileRead fileRead = new FileRead();
        String stringToScheme = "";

        try {
            stringToScheme = fileRead.readFile("src/main/scheme/dk.plpa/test.scm", StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        registerEnvironment();
        Environment.setCurrent(new Scheme().getEnvironment());
        Object x;

        x = Scheme.eval(stringToScheme, Environment.getCurrent());

        System.out.println(x);
    }




}
