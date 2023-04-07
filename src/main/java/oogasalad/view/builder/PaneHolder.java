package oogasalad.view.builder;

import javafx.scene.layout.Pane;

import java.util.LinkedList;
import java.util.Queue;

public class PaneHolder {

    private Queue<Pane> myPrevious;
    private Pane myCurrent;

    PaneHolder(Pane current){
        myPrevious = new LinkedList<>();
        myCurrent = current;
    }

    public void addPane(Pane pane){
        myPrevious.add(myCurrent);
        myCurrent = pane;
    }

    public Pane restorePreviousPane(){
        myCurrent = myPrevious.poll();
        return myCurrent;
    }
}
