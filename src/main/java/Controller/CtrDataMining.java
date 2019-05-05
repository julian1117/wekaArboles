package Controller;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import weka.associations.Apriori;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;

/**
 *
 * @author CAMILO
 */
public class CtrDataMining {

    // definimos el formato para los deciales
    DecimalFormat format = new DecimalFormat("#.##");

    public String mineria(String datos) {

        StringReader sr = new StringReader(datos);

        BufferedReader br = new BufferedReader(sr);

        try {
            Instances data = new Instances(br);

            data.setClassIndex(data.numAttributes() - 1);

            br.close();

            String descripcion = definirEncabezado(data);

            String resultadoBayesiano = redBayesiana(data);

           // String resultadoApriori = apriori(data);
            
            String resultadoJ48 = arbolJ48(data);

            String res = descripcion + "\n" + resultadoBayesiano +"\n >>" //+ resultadoApriori 
                    + "\n" + resultadoJ48;
            return res;

        } catch (IOException e) {
            return "El error es: " + e.getMessage();
        }

    }

    public String definirEncabezado(Instances data) {

        String descripcion = "<b>El atributo clase seleccionado es "
                + data.attribute(data.numAttributes() - 1).name() + "</br>";

        descripcion += "<b> con posibles valores:<b> ";

        for (int i = 0; i < data.attribute(data.numAttributes() - 1).numValues(); i++) {
            descripcion += "<b>" + data.attribute(data.numAttributes() - 1).value(i) + "</b>";
        }

        return descripcion;

    }

    public String redBayesiana(Instances data) {
        try {
            NaiveBayes nb = new NaiveBayes();

            nb.buildClassifier(data);

            Evaluation evalB = new Evaluation(data);

            /*
             aplicamos el clasificador bayesiano hacemos validacion cruzada, de bayesiana, con 10 campos
             y un aleatorio para una semilla
             */
            evalB.crossValidateModel(nb, data, 10, new Random(1));

            String resBay = "<br> <br> <b><center>Resultados NaiveBayes</center>"
                    + "<br>=======</br>"
                    + "Modelo generado indica los siguientes resultados: "
                    + "<br>=======</br>";
            //obtener resulados
            resBay = resBay + ("<b>1. numero de instancias clasificadas:<b>" + (int) evalB.numInstances() + "<br>");
            resBay = resBay + ("<b>2. porcentaje de instancias correctamente clasificadas:</b>" + format.format(evalB.pctCorrect()) + "<br>");
            resBay = resBay + ("<b>3. numero de instancias correctamente clasificadas:</b>" + (int) evalB.correct() + "<br>");
            resBay = resBay + ("<b>4. porcentaje de instancias incorrectamente clasificadas:</b>" + format.format(evalB.pctIncorrect()) + "<br>");
            resBay = resBay + ("<b>5. Numero de instancias incorrectamente clasificadas:</b>" + (int) evalB.incorrect() + "<br>");
            resBay = resBay + ("<b>6. Media del error absoluto:</b>" + format.format(evalB.meanAbsoluteError()) + "<br>");
            resBay = resBay + ("<b>7." + evalB.toMatrixString("Matriz de confucion").replace("\n", "<br>"));

            return resBay;

        } catch (Exception ex) {
            return "El error es: " + ex.getMessage();
        }

    }

    public String apriori(Instances data) {
        try {
            Apriori aso = new Apriori();
            aso.buildAssociations(data);
            String resApriori = "<br><b><center>Resultado asociacion Apriori</center><br>======<br> El modelo de asociacion generado"
                    + "indica los siguientes resultados: <br>=======<br></b>";
            //obtenemos resultado
            for (int i = 0; i < aso.getAssociationRules().getRules().size(); i++) {
                resApriori = resApriori + "<b>" + (i + 1) + ". si</b>" + aso.getAssociationRules().getRules().get(i).getPremise().toString();
                resApriori = resApriori + "<b>Entonces</b>" + aso.getAssociationRules().getRules().get(i).getConsequence().toString();
                resApriori = resApriori + "<b>Con un</b>" + (int) (aso.getAssociationRules().getRules().get(i).getPrimaryMetricValue() * 100) + "% de prioridad<br>";

            }
            return resApriori;
        } catch (Exception e) {
            return "El error es: " + e.getMessage();
        }

    }

    public String arbolJ48(Instances data) {
        try {
            J48 j48 = new J48();
            //clasificador de j48

            j48.buildClassifier(data);
            //Objeto para validacion modelo con red bayesiana

            Evaluation evalJ48 = new Evaluation(data);

            evalJ48.crossValidateModel(j48, data, 10, new Random(1));

            String resBay = "<br> <br> <b><center>Resultados NaiveBayes</center>"
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

            // se crea el arbol
            final javax.swing.JFrame jf = new javax.swing.JFrame("Arbol de decision: J48");

            jf.setSize(500, 400);

            jf.getContentPane().setLayout(new BorderLayout());

            TreeVisualizer tv = new TreeVisualizer(null, j48.graph(), new PlaceNode2());

            jf.getContentPane().add(tv, BorderLayout.CENTER);

            jf.addWindowListener(new java.awt.event.WindowAdapter() {
                public void windowClosing(java.awt.event.WindowEvent e) {
                    jf.dispose();
                }
            });
            jf.setVisible(true);
            tv.fitToScreen();

            return resBay;

        } catch (IOException e) {
            return "El error es: " + e.getMessage();

        }catch (Exception ex){
            return  ex.getMessage();
        }
    }

}
