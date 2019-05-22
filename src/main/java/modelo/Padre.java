package modelo;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author CAMILO
 */
public class Padre implements Serializable {

    private String name;
    private String parent;
    private List<Padre> children;

    public Padre() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public List<Padre> getChildren() {
        return children;
    }

    public void setChildren(List<Padre> children) {
        this.children = children;
    }

    
}
