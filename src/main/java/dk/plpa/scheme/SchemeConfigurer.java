package dk.plpa.scheme;

import dk.plpa.utils.FileReader;
import gnu.mapping.Environment;
import kawa.standard.Scheme;

import java.util.List;

import static kawa.standard.Scheme.registerEnvironment;

public class SchemeConfigurer {

    private List<String> schemeFilePath;

    public SchemeConfigurer(List<String> schemeFilePath){
        this.schemeFilePath = schemeFilePath;

    }

    public void configureSchemeEnvironment(){
        registerEnvironment();
        Environment.setCurrent(new Scheme().getEnvironment());
        schemeFilePath.forEach(this::loadFile);
    }

    private void loadFile(String file) {
        String schemeDefinition = FileReader.readFile(file);
        Scheme.eval(schemeDefinition, Environment.getCurrent());
    }
}
