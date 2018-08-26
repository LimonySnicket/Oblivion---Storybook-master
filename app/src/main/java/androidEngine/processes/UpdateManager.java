package androidEngine.processes;

import java.util.ArrayList;
import java.util.Collections;

import androidEngine.androidGraphics.graphicComponents.GraphicObject;
import androidEngine.androidGraphics.graphicComponents.ObjPosition;


public class UpdateManager {
    private ArrayList<GraphicObject>[] objects;
    private ArrayList<GraphicObject> visibleObjects;
    private ObjPosition frame;

    public UpdateManager(ArrayList<GraphicObject>...lists) {
        objects = new ArrayList[lists.length + 1];
        visibleObjects = new ArrayList<>();
        objects[0] = visibleObjects;
        for(int i = 0; i < lists.length; i++) {
            objects[i+1] = lists[i];
        }
        sortLists();
    }

    public void setFrame(ObjPosition f) {
        frame = f;
    }
    public void updateFrame(double x, double y) {
        frame.setPosition(x, y);
    }

    public void sortLists() {
        for(int i = 1; i < objects.length; i++) {
            try {
                Collections.sort(objects[i], GraphicObject.XWidthValComparator);
            } catch (NullPointerException e) {
                System.out.println("List " + i + " is empty");
            }
        }
    }

    public void sortList(ArrayList<GraphicObject> list) {
        try {
            Collections.sort(list, GraphicObject.XWidthValComparator);
        } catch (NullPointerException e) {
            System.out.println("List is empty");
        }
        //printList(list);
    }

    public void printList(ArrayList<GraphicObject> list) {
        System.out.println();
        for(GraphicObject obj: list) {
            System.out.print(" (" + obj + ") ");
        }
        System.out.println();
    }
    public void printLists() {
        for (ArrayList<GraphicObject> list : objects) {
            printList(list);
        }
    }

    public void updateGame(double inc) {
        for(int i = 1; i < objects.length; i++){
            try {
                for (GraphicObject object : objects[i]) {
                    if (object.needsGameUpdate()) {
                        sortList(objects[i]);
                        object.update(inc, objects);
                        if(frame != null) {
                            if(object.getX() + object.getWidth() + 10 > frame.getX() &&
                                    object.getX() < frame.getX() + frame.getWidth() + 10 &&
                                    object.getY() + object.getHeight() + 10 > frame.getY() &&
                                    object.getY() < frame.getY() + frame.getHeight() + 10) {
                                visibleObjects.add(object);
                            } else {
                                if(visibleObjects.contains(object)) {
                                    visibleObjects.remove(object);
                                }
                            }
                        }
                        //System.out.println(object + " UPDATED");
                    }
                }
            } catch (Exception e) {

            }
        }
    }

    public boolean framePresent() {
        if(frame != null) {
            return false ; //true;
        } else {
            return false;
        }
    }
    public ArrayList<GraphicObject> getVisibleObjects() {
        return visibleObjects;
    }
}