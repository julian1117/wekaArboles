package Mineria;

import com.google.gson.Gson;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import modelo.JsonArboles;
import modelo.Nodo;
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

    //Lista para guardar la informacion del arbol y el JSON para D3.JS
    private ArrayList<String> listaRetorno = new ArrayList<>();

    // definimos el formato para los deciales
    DecimalFormat format = new DecimalFormat("#.##");

    //Libreria Gson
    Gson gson = new Gson();

    // instanciamos el data mining
    private DataMining dataMining;

    /**
     * Algoritmo de weka Arbol RJ48
     *
     *
     * @param data conjunto de datos
     * @return el resultado del analisis del arbol RJ48
     */
    public String arbolRJ48(Instances data) {
        this.dataMining = new DataMining();
        try {

            JsonArboles jArbol = new JsonArboles();
            //Creamos el objeto arbol rj48
            J48 j48 = new J48();
            //clasificador de j48
            j48.buildClassifier(data);
            //Objeto para validacion modelo con red bayesiana
            Evaluation evalJ48 = new Evaluation(data);
            evalJ48.crossValidateModel(j48, data, 10, new Random(1));
            //Obtenemos el grafico del arbol generado por weka
            String arbol = j48.graph();

            String resBay = "<b><center>Resultados RJ48</center>"
                    + "<br>=======</br>"
                    + "Modelo generado indica los siguientes resultados: "
                    + "<br>=======</br>";
            //obtener resulados
            resBay = resBay + ("<span class=\"text-success\">1. numero de instancias clasificadas: </span>" + (int) evalJ48.numInstances() + "<br>");
            resBay = resBay + ("<span class=\"text-success\">2. porcentaje de instancias correctamente clasificadas: </span>" + format.format(evalJ48.pctCorrect()) + "<br>");
            resBay = resBay + ("<span class=\"text-success\">3. numero de instancias correctamente clasificadas: </span>" + (int) evalJ48.correct() + "<br>");
            resBay = resBay + ("<span class=\"text-success\">4. porcentaje de instancias incorrectamente clasificadas: </span>" + format.format(evalJ48.pctIncorrect()) + "<br>");
            resBay = resBay + ("<span class=\"text-success\">5. Numero de instancias incorrectamente clasificadas: </span>" + (int) evalJ48.incorrect() + "<br>");
            resBay = resBay + ("<span class=\"text-success\">6. Media del error absoluto: </span>" + format.format(evalJ48.meanAbsoluteError()) + "<br>");
            resBay = resBay + ("<span class=\"text-success\">7." + evalJ48.toMatrixString("Matriz de confucion </span>").replace("\n", "<br>"));

            jArbol.setNumeroInstancias((int) evalJ48.numInstances());
            jArbol.setInstanciasCorrectas(format.format(evalJ48.pctCorrect()));
            jArbol.setNumeroInstanciasCorrectas((int) evalJ48.correct());
            jArbol.setPorcentInstanciasCorrectas(format.format(evalJ48.pctIncorrect()));//incorrectas
            jArbol.setNumeroInstanciasIncorrectas((int) evalJ48.incorrect());
            //jArbol.setMediaError(format.format(evalJ48.meanAbsoluteError()));
            //jArbol.setArbol(arbol);

            String resPu = transformacionJ48JSON(arbol);

            listaRetorno.add(this.dataMining.encabezado(data) + "\n" + resBay + "<span class='text-success'><br>Objeto JSON:</span><br>" + resPu);
            listaRetorno.add(resPu);

            return gson.toJson(listaRetorno);

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
        this.dataMining = new DataMining();
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
            
            System.out.println(arbol);
            String resPu = transformacionJ48JSON(arbol);

            listaRetorno.add(this.dataMining.encabezado(data) + "\n" + resBay + "<span class='text-success'><br>Objeto JSON:</span><br>" + resPu);
            listaRetorno.add(resPu);

            //return resBay + "\n" + JSON;
            return gson.toJson(listaRetorno);

        } catch (Exception ex) {
            return "El error es: " + ex.getMessage();
        }
    }

    /**
     * Algoritmo de weka Arbol RJ48
     *
     *
     * @param data conjunto de datos
     * @return el resultado del analisis del arbol rabdomtree
     */
    public String RandomTree(Instances data) {
        this.dataMining = new DataMining();
        try {

            JsonArboles jArbol = new JsonArboles();
            //Creamos el objeto arbol rj48
            RandomTree rTree = new RandomTree();
            //clasificador de j48

            rTree.buildClassifier(data);
            //Objeto para validacion modelo con red bayesiana

            Evaluation evalJ48 = new Evaluation(data);

            evalJ48.crossValidateModel(rTree, data, 10, new Random(1));

            //Obtenemos el grafico del arbol generado por weka
            String arbol = rTree.graph();

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

            //String JSON = gson.toJson(jArbol);
            String resPu = transformacionJ48JSON(arbol);
            System.out.println(resPu);            
s
            listaRetorno.add(this.dataMining.encabezado(data) + "\n" + resBay + "<span class='text-success'><br>Objeto JSON:</span><br>" + resPu);
            listaRetorno.add(resPu);

            return resPu;
            //return resBay + "\n" + JSON;
        } catch (Exception ex) {
            return "El error es: " + ex.getMessage();
        }
    }

    /**
     * Metodo para obtener el JSON que se enviara al arbol d3.js
     *
     * @param dataGr grafico en formato string que retorna el arbol
     * @return JSON que se enviara a d3.js
     */
    public String transformacionJ48JSON(String dataGr) {
        String[] data = dataGr.split("->");
        List<Nodo> listNodo = new ArrayList<>();
        String padre = "";

        for (int i = 0; i < data.length; i++) {
            int tamanoCadena = data[i].length();
            Nodo objNodo = null;
            String hijo = "";

            //Creamo el nodo padre principal del arbol
            if (i == 0) {
                padre = data[i].substring(tamanoCadena - 2);
                objNodo = new Nodo(padre);
            }
            //Accedemos al los nodos hijos
            if (i > 0) {
                int numLong = 2;
                if (i >= 10 && i < 99) {
                    numLong = 3;
                } else if (i >= 100 && i < 999) {
                    numLong = 4;
                } else if (i >= 1000 && i < 9999) {
                    numLong = 5;
                } else if (i >= 10000 && i < 99999) {
                    numLong = 6;
                } else if (i >= 100000 && i < 999999) {
                    numLong = 7;
                } else if (i >= 1000000 && i < 9999999) {
                    numLong = 8;
                } else if (i >= 10000000 && i < 99999999) {
                    numLong = 9;
                }
                hijo = data[i].substring(0, numLong);
                objNodo = new Nodo(hijo);
                Nodo padreNodo = obtenerPadre(padre, listNodo);
                padreNodo.getChildren().add(objNodo);
            }
            String nombre = nombreNodo(data[i]);
            objNodo.setName(nombre);
            listNodo.add(objNodo);
            objNodo.setChildren(new ArrayList<Nodo>());

            padre = data[i].substring(tamanoCadena - 2);
            if (!padre.contains("N")) {
                padre = "N" + padre;
            }
        }
        return gson.toJson(listNodo.get(0));
    }

    /**
     * Metodo que permite obtener el nombre del nodo
     *
     * @param data objeto del que obtendremos el peso y nombre
     * @return Peso y Nombre del nodo
     */
    private String nombreNodo(String data) {
        //Hacemos split por cada " 
        String nombreNodo[] = data.split("\"");
        //Obtenemos el valor del peso del nodo
        String nombre = "";
        //Validamos que el arreglo contenga el resto del nombre para poder concatenarlo al peso que ya se obtubo
        if (nombreNodo.length > 3) {
            return nombre = nombreNodo[1] + " - " + nombreNodo[3];
        } else {
            return null;
        }
    }

    /**
     * Metodo para obtiener el nodo padre de una clase hija
     *
     * @param nombrePadre nombre del nodo padre que se desea obtener
     * @param nodos lista de nodos donde se desea buscar
     * @return el nodo padre
     */
    private Nodo obtenerPadre(String nombrePadre, List<Nodo> nodos) {
        for (Nodo nodo : nodos) {
            if (nodo.getNodo().equals(nombrePadre)) {
                return nodo;
            }
        }
        return null;
    }

}
