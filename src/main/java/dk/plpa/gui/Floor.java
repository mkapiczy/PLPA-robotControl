package dk.plpa.gui;

import javafx.scene.layout.Region;
import lombok.Getter;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Floor extends Region {
    
    private List<FloorRow> rows = new ArrayList<>();

    public void addRow(FloorRow row){
        rows.add(row);
    }


}
