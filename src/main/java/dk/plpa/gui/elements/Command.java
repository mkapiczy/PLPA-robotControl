package dk.plpa.gui.elements;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Command {
    String name;
    String arg;

    public Command(String name) {
        this.name = name;
        arg = "";
    }

    @Override
    public String toString() {
        return this.name;
    }
}