package Mineria;

import com.google.gson.Gson;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.json.Json;
import modelo.Hijo;
import modelo.JsonArboles;
import modelo.Padre;
import weka.associations.Apriori;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.classifiers.trees.RandomTree;
import weka.core.Instances;

/**
 *
 * @author CAMILO
 */
public class ArbolesDecisionMineria implements Serializable {

    String[][] nombreDatos;

    // definimos el formato para los deciales
    DecimalFormat format = new DecimalFormat("#.##");

    //Libreria Gson
    Gson gson = new Gson();

    /**
     * Algoritmo de weka Arbol RJ48
     *
     *
     * @param data conjunto de datos
     * @return el resultado del analisis del arbol RJ48
     */
    public String arbolRJ48(Instances data) {
        try {

            JsonArboles jArbol = new JsonArboles();
            //Creamos el objeto arbol rj48
            J48 j48 = new J48();
            //clasificador de j48

            j48.buildClassifier(data);
            //Objeto para validacion modelo con red bayesiana

            Evaluation evalJ48 = new Evaluation(data);

            evalJ48.crossValidateModel(j48, data, 10, new Random(1));
            //System.out.println(j48.graph());
            String[] arbol = j48.graph().split("\n");

            String resBay = "<b><center>Resultados RJ48</center>"
                    + "<br>=======</br>"
                    + "Modelo generado indica los siguientes resultados: "
                    + "<br>=======</br>";
            //obtener resulados
            resBay = resBay + ("<b>1. numero de instancias clasificadas:<b>" + (int) evalJ48.numInstances() + "<br>");
            resBay = resBay + ("<b>2. porcentaje de instancias correctamente clasificadas:</b>" + format.format(evalJ48.pctCorrect()) + "<br>");
            resBay = resBay + ("<b>3. numero de instancias correctamente clasificadas:</b>" + (int) evalJ48.correct() + "<br>");
            resBay = resBay + ("<b>4. porcentaje de instancias incorrectamente clasificadas:</b>" + format.format(evalJ48.pctIncorrect()) + "<br>");
            resBay = resBay + ("<b>5. Numero de instancias incorrectamente clasificadas:</b>" + (int) evalJ48.incorrect() + "<br>");
            resBay = resBay + ("<b>6. Media del error absoluto:</b>" + format.format(evalJ48.meanAbsoluteError()) + "<br>");
            resBay = resBay + ("<b>7." + evalJ48.toMatrixString("Matriz de confucion").replace("\n", "<br>"));
            resBay = resBay + ("<b>8." + arbol + "<br>");
            jArbol.setNumeroInstancias((int) evalJ48.numInstances());
            jArbol.setInstanciasCorrectas(format.format(evalJ48.pctCorrect()));
            jArbol.setNumeroInstanciasCorrectas((int) evalJ48.correct());
            jArbol.setPorcentInstanciasCorrectas(format.format(evalJ48.pctIncorrect()));//incorrectas
            jArbol.setNumeroInstanciasIncorrectas((int) evalJ48.incorrect());
            //jArbol.setMediaError(format.format(evalJ48.meanAbsoluteError()));
            //jArbol.setArbol(arbol);

            //String JSON = gson.toJson(jArbol);
            String resPu = transformacionJ48JSON(arbol);

            //return resBay+"\n >>>>>"+res;
            return resPu;
        } catch (Exception ex) {
            // ex.printStackTrace();
            return "El error en RJ48 es: " + ex.getMessage();
        }
    }

    /**
     * Algoritmo de weka Arbol RJ48
     *
     *
     * @param data conjunto de datos
     * @return el resultado del analisis del arbol RJ48
     */
    public String RandomForest(Instances data) {
        try {
            //lala
            Gson gson = new Gson();
            JsonArboles jArbol = new JsonArboles();
            //Creamos el objeto arbol rj48
            RandomForest rF = new RandomForest();
            //clasificador de j48

            rF.buildClassifier(data);
            //Objeto para validacion modelo con red bayesiana

            Evaluation evalJ48 = new Evaluation(data);

            evalJ48.crossValidateModel(rF, data, 10, new Random(1));

            String resBay = "<b><center>Resultados Forest</center>"
                    + "<br>=======</br>"
                    + "Modelo generado indica los siguientes resultados: "
                    + "<br>=======</br>";
            //obtener resulados
            resBay = resBay + ("<b>1. numero de instancias clasificadas:<b>" + (int) evalJ48.numInstances() + "<br>");
            resBay = resBay + ("<b>2. porcentaje de instancias correctamente clasificadas:</b>" + format.format(evalJ48.pctCorrect()) + "<br>");
            resBay = resBay + ("<b>3. numero de instancias correctamente clasificadas:</b>" + (int) evalJ48.correct() + "<br>");
            resBay = resBay + ("<b>4. porcentaje de instancias incorrectamente clasificadas:</b>" + format.format(evalJ48.pctIncorrect()) + "<br>");
            resBay = resBay + ("<b>5. Numero de instancias incorrectamente clasificadas:</b>" + (int) evalJ48.incorrect() + "<br>");
            resBay = resBay + ("<b>6. Media del error absoluto:</b>" + format.format(evalJ48.meanAbsoluteError()) + "<br>");
            resBay = resBay + ("<b>7." + evalJ48.toMatrixString("Matriz de confucion").replace("\n", "<br>"));

            jArbol.setNumeroInstancias((int) evalJ48.numInstances());
            jArbol.setInstanciasCorrectas(format.format(evalJ48.pctCorrect()));
            jArbol.setNumeroInstanciasCorrectas((int) evalJ48.correct());
            jArbol.setPorcentInstanciasCorrectas(format.format(evalJ48.pctIncorrect()));//incorrectas
            jArbol.setNumeroInstanciasIncorrectas((int) evalJ48.incorrect());
            // jArbol.setMediaError(format.format(evalJ48.meanAbsoluteError("Matriz de confucion").replace("\n", "<br>")));

            String JSON = gson.toJson(jArbol);

            return resBay + "\n" + JSON;
        } catch (Exception ex) {
            return "El error es: " + ex.getMessage();
        }
    }

    /**
     * Algoritmo de weka Arbol RJ48
     *
     *
     * @param data conjunto de datos
     * @return el resultado del analisis del arbol RJ48
     */
    public String RandomTree(Instances data) {
        try {

            Gson gson = new Gson();
            JsonArboles jArbol = new JsonArboles();
            //Creamos el objeto arbol rj48
            RandomTree rTree = new RandomTree();
            //clasificador de j48

            rTree.buildClassifier(data);
            //Objeto para validacion modelo con red bayesiana

            Evaluation evalJ48 = new Evaluation(data);

            evalJ48.crossValidateModel(rTree, data, 10, new Random(1));

            String resBay = "<b><center>Resultados RANDOMTREE</center>"
                    + "<br>=======</br>"
                    + "Modelo generado indica los siguientes resultados: "
                    + "<br>=======</br>";
            //obtener resulados
            resBay = resBay + ("<b>1. numero de instancias clasificadas:<b>" + (int) evalJ48.numInstances() + "<br>");
            resBay = resBay + ("<b>2. porcentaje de instancias correctamente clasificadas:</b>" + format.format(evalJ48.pctCorrect()) + "<br>");
            resBay = resBay + ("<b>3. numero de instancias correctamente clasificadas:</b>" + (int) evalJ48.correct() + "<br>");
            resBay = resBay + ("<b>4. porcentaje de instancias incorrectamente clasificadas:</b>" + format.format(evalJ48.pctIncorrect()) + "<br>");
            resBay = resBay + ("<b>5. Numero de instancias incorrectamente clasificadas:</b>" + (int) evalJ48.incorrect() + "<br>");
            resBay = resBay + ("<b>6. Media del error absoluto:</b>" + format.format(evalJ48.meanAbsoluteError()) + "<br>");
            resBay = resBay + ("<b>7." + evalJ48.toMatrixString("Matriz de confucion").replace("\n", "<br>"));

            jArbol.setNumeroInstancias((int) evalJ48.numInstances());
            jArbol.setInstanciasCorrectas(format.format(evalJ48.pctCorrect()));
            jArbol.setNumeroInstanciasCorrectas((int) evalJ48.correct());
            jArbol.setPorcentInstanciasCorrectas(format.format(evalJ48.pctIncorrect()));//incorrectas
            jArbol.setNumeroInstanciasIncorrectas((int) evalJ48.incorrect());
            //jArbol.setMediaError(format.format(evalJ48.meanAbsoluteError()));
            jArbol.setArbol(evalJ48.toCumulativeMarginDistributionString());

            String JSON = gson.toJson(jArbol);

            return resBay + "\n" + JSON;
        } catch (Exception ex) {
            return "El error es: " + ex.getMessage();
        }
    }

//    public String transformacionJ48JSON(String[] data) {
//        Padre arbol = new Padre();
//        Padre nodo = new Padre();
//        for (int i = 0; i < data.length - 1; i++) {
//            //Se verifica que sea hijo
//            if (i % 2 == 0) {
//                nodo.setParent(data[i].substring(0, Math.min(2, data[i].length())));
//                nodo.setName(data[i].substring(4, Math.min(6, data[i].length())));
//                //objPadre.setHijo(objHijo);
//            }
//            hijo[i] = nodo;
//        }
//        return gson.toJson(hijo);
//    }
    /**
     *
     */
    public String transformacionJ48JSON(String[] data) {
        List<Padre> arbol = new ArrayList<>();
        List<Hijo> hijo = new ArrayList<>();
        String aux = "";

        for (int i = 0; i < data.length; i++) {
            //Se verifica que sea hijo
            //if (i % 2 == 0 ) {
            //HijoS
            boolean palabraSplit = data[i].matches(".*->.*");
            if (palabraSplit) {
                // System.out.println("Entra si es ->");
                String[] hijos = data[i].split("->");
            
                //Padres
                String[] padre = hijos[1].split(" ");
                

                Hijo objHijo = new Hijo();

                objHijo.setParent(hijos[0]);
                objHijo.setName(padre[0]);
                hijo.add(objHijo);
            } else {
                // System.out.println(data[i]);
            }
            // }
            //System.out.println("termina if ");

        }

        //For para padres y sus hijos
        //Recorre arreglo principal
        //System.out.println("antes for j");
        for (int j = 0; j < data.length; j++) {
           // System.out.println("Entra for j");

            //objeto padre
            Padre objPadre = new Padre();
            objPadre.setParent("null");
            //System.out.println("Crea padre");

            // los hijos que contiene el padre
            List<Padre> children = new ArrayList<>();

            //recorre unicamente arreglo de hijos
            for (int l = 0; l < hijo.size(); l++) {
                boolean palabraSplit = data[j].matches(".*->.*");
                if (palabraSplit) {
                    String[] hijos = data[j].split("->");
                    String[] padre = hijos[1].split(" ");
                    String pad = padre[0];
                    System.out.println("padre " + pad + " - " + hijo.get(l).getParent());

                    if (pad.equals(hijo.get(l).getParent())) {
                        // Agregamos el nombre del objeto padre
                        objPadre.setName(hijo.get(l).getParent());
                        // Sera hijo del objeto padre
                        Padre h = new Padre();
                        h.setName(hijo.get(l).getName());
                        h.setParent(hijo.get(l).getParent());
                        //validamos si es padre
                        if (this.isPadre(h.getName(), hijo)) {
                            //como es padre, buscamos sus hijos
                            h.setChildren(this.getChildrens(h.getName(), hijo));
                        }
                        // Agregamos el hijo a la lista de hijos del padre
                        children.add(h);
                    }
                }
            }

            if (objPadre.getName() != null) {
                // Agregamos el objeto solo si no se ha agregado anteriormente
                if (!aux.equals(objPadre.getName())) {
                    objPadre.setChildren(children);
                    arbol.add(objPadre);
                    // agregamos el nombre a la variable auxiliar
                    aux = objPadre.getName();
                    break;
                }
            }
        }
        return gson.toJson(arbol);
    }

    /**
     * verifica si un nodo es padre
     *
     * @param name el nombre del nodo a verificar
     * @param hijo la lista de hijos
     * @return true si es padre, de lo contrario false
     */
    public boolean isPadre(String name, List<Hijo> hijos) {
        for (int i = 1; i < hijos.size() - 1; i++) {
            if (hijos.get(i).getParent().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtiene los hijos de el nodo padre del arbol
     *
     * @param padre es el nombre del primer nodo del arbol
     * @param hijos es la lista de hijos del arbol
     * @return la lista ordenada de hijos del padre del
     */
    public List<Padre> getChildrens(String padre, List<Hijo> hijos) {
        List<Padre> childrens = new ArrayList<>();
        for (int i = 1; i < hijos.size(); i++) {
            Padre h = new Padre();
            // Validamos si es hijo
            if (hijos.get(i).getParent().equals(padre)) {
                h.setName(hijos.get(i).getName());
                h.setParent(hijos.get(i).getParent());
                // verifico si tiene hijos
                if (isPadre(h.getName(), hijos)) {
                    // volvemos a llamar el metodo para obtener los hijos
                    h.setChildren(this.getChildrens(h.getName(), hijos));
                }
                childrens.add(h);
            }
        }
        return childrens;
    }

    /**
     * Construye el arbol
     *
     * @param d datos para construir el arbol
     * @param arbol el arbol a retornar
     * @param p posicion del array de datos
     * @return
     */
    public Padre arbol(String[] d, Padre arbol, int p) {
        // condicion base
        if (p == d.length) {
            return arbol;
        }
        return arbol(d, arbol, p + 1);
    }

}
