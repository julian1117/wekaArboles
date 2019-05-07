package Mineria;

import com.google.gson.Gson;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Random;
import javax.json.Json;
import modelo.JsonArboles;
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

    // definimos el formato para los deciales
    DecimalFormat format = new DecimalFormat("#.##");

    /**
     * Algoritmo de weka Arbol RJ48
     *
     *
     * @param data conjunto de datos
     * @return el resultado del analisis del arbol RJ48
     */
    public String arbolRJ48(Instances data) {
        try {
            
            Gson gson = new Gson();
            JsonArboles jArbol = new JsonArboles();
            //Creamos el objeto arbol rj48
            J48 j48 = new J48();
            //clasificador de j48

            j48.buildClassifier(data);
            //Objeto para validacion modelo con red bayesiana

            Evaluation evalJ48 = new Evaluation(data);

            evalJ48.crossValidateModel(j48, data, 10, new Random(1));

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
            
            jArbol.setNumeroInstancias((int)evalJ48.numInstances());
            jArbol.setInstanciasCorrectas(format.format(evalJ48.pctCorrect()));
            jArbol.setNumeroInstanciasCorrectas((int) evalJ48.correct());
            jArbol.setPorcentInstanciasCorrectas(format.format(evalJ48.pctIncorrect()));//incorrectas
            jArbol.setNumeroInstanciasIncorrectas((int) evalJ48.incorrect());
            //jArbol.setMediaError(format.format(evalJ48.meanAbsoluteError()));
            jArbol.setArbol(evalJ48.toCumulativeMarginDistributionString());
            
            String JSON = gson.toJson(jArbol);
            
            return resBay+"\n"+JSON;
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
    public String RandomForest(Instances data) {
        try {
            
            Gson gson = new Gson();
            JsonArboles jArbol = new JsonArboles();
            //Creamos el objeto arbol rj48
            RandomForest rF = new RandomForest();
            //clasificador de j48

            rF.buildClassifier(data);
            //Objeto para validacion modelo con red bayesiana

            Evaluation evalJ48 = new Evaluation(data);

            evalJ48.crossValidateModel(rF, data, 10, new Random(1));

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
            
            jArbol.setNumeroInstancias((int)evalJ48.numInstances());
            jArbol.setInstanciasCorrectas(format.format(evalJ48.pctCorrect()));
            jArbol.setNumeroInstanciasCorrectas((int) evalJ48.correct());
            jArbol.setPorcentInstanciasCorrectas(format.format(evalJ48.pctIncorrect()));//incorrectas
            jArbol.setNumeroInstanciasIncorrectas((int) evalJ48.incorrect());
            //jArbol.setMediaError(format.format(evalJ48.meanAbsoluteError()));
            jArbol.setArbol(evalJ48.toCumulativeMarginDistributionString());
            
            String JSON = gson.toJson(jArbol);
            
            return resBay+"\n"+JSON;
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
            
            jArbol.setNumeroInstancias((int)evalJ48.numInstances());
            jArbol.setInstanciasCorrectas(format.format(evalJ48.pctCorrect()));
            jArbol.setNumeroInstanciasCorrectas((int) evalJ48.correct());
            jArbol.setPorcentInstanciasCorrectas(format.format(evalJ48.pctIncorrect()));//incorrectas
            jArbol.setNumeroInstanciasIncorrectas((int) evalJ48.incorrect());
            //jArbol.setMediaError(format.format(evalJ48.meanAbsoluteError()));
            jArbol.setArbol(evalJ48.toCumulativeMarginDistributionString());
            
            String JSON = gson.toJson(jArbol);
            
            return resBay+"\n"+JSON;
        } catch (Exception ex) {
            return "El error es: " + ex.getMessage();
        }
    }

}
