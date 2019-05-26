package modelo;

import java.util.ArrayList;

/**
 *
 * @author CAMILO
 */
public class Nodo{
    
    private String name;
    private String nodo;
    private ArrayList<Nodo> children;
    
    public Nodo(String nodo) {
        this.nodo = nodo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Nodo> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Nodo> children) {
        this.children = children;
    }

    public String getNodo() {
        return nodo;
    }

    public void setNodo(String nodo) {
        this.nodo = nodo;
    }
}
