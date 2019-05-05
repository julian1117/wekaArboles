package Mineria;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Random;
import weka.associations.Apriori;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
/**
 *
 * @author CAMILO
 */
public class ArbolesDecisionMineria implements Serializable {

    // definimos el formato para los deciales
    DecimalFormat format = new DecimalFormat("#.##");

    /**
     * Algoritmo de weka Arbol RJ48     *
     * @param data conjunto de datos
     * @return el resultado del analisis del arbol RJ48
     */
    public String arbolRJ48(Instances data) {
        try {
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

            return resBay;
        } catch (Exception ex) {
            return "El error es: " + ex.getMessage();
        }
    }

}
