package dk.plpa.scheme;

import dk.plpa.utils.FileReader;
import gnu.mapping.Environment;
import kawa.standard.Scheme;

import static kawa.standard.Scheme.registerEnvironment;

public class SchemeConfigurer {

    private String schemeFilePath;

    public SchemeConfigurer(String schemeFilePath){
        this.schemeFilePath = schemeFilePath;

    }

    public void configureSchemeEnvironment(){
        registerEnvironment();
        Environment.setCurrent(new Scheme().getEnvironment());
        String schemeDefinition = FileReader.readFile(schemeFilePath);
        Scheme.eval(schemeDefinition, Environment.getCurrent());
    }
}
