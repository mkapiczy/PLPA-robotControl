package dk.plpa.scheme;

import dk.plpa.utils.FileReader;
import gnu.mapping.Environment;
import kawa.standard.Scheme;

import java.util.ArrayList;
import java.util.List;

import static kawa.standard.Scheme.registerEnvironment;

public class SchemeConfigurer {

    private List<String> schemeFiles = new ArrayList<>();

    public SchemeConfigurer(){
        this.schemeFiles.add("src/main/scheme/dk.plpa/robot.scm");
        this.schemeFiles.add("src/main/scheme/dk.plpa/FloorPlan.scm");
        this.schemeFiles.add("src/main/scheme/dk.plpa/FloorUtil.scm");
        this.schemeFiles.add("src/main/scheme/dk.plpa/simulation2.scm");
        this.schemeFiles.add("src/main/scheme/dk.plpa/factorial.scm");
}

    public void configureSchemeEnvironment(){
        registerEnvironment();
        Environment.setCurrent(new Scheme().getEnvironment());
        configureSchemeFiles();
    }

    private void configureSchemeFiles(){
        this.schemeFiles.forEach(this::loadFileIntoSchemeEnvironment);
    }

    private void loadFileIntoSchemeEnvironment(String file) {
        String schemeDefinition = FileReader.readFile(file);
        Scheme.eval(schemeDefinition, Environment.getCurrent());
    }
}
