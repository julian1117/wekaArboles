package modelo;

import java.io.Serializable;

/**
 *
 * @author CAMILO
 */
public class Hijo implements Serializable{
    
    private String name;
    private String parent;

    public Hijo() {
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

}
