package juang.csv;

import juang.utils.StringUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import juang.colecciones.Iterador;
import juang.colecciones.Lista;

/**
 * Esta clase maneja un archivo .csv en específico, define los métodos
 * necesarios para la creación de los objetos que este representa.
 *
 * @author juang
 * @param <T> El tipo de dato u objeto representado por este archivo .csv
 */
public class LectorCSV<T> {

    /**
     * Esta clase define un objeto que guarda de manera simbolica los elementos
     * necesarios para instanciar un objeto de alguna clase cualquiera.
     *
     * @author juang
     * @param <T> Tipo de dato o clase de los elementos que se generan a partir
     * de este objeto csv;
     */
    public class ObjetoCSV<T> {

        /**
         * Arreglo que guarda los tipos de parametros necesarios para instanciar
         * el constructor del objeto en su respectivo orden.
         */
        private Class[] tipos;
        /**
         * El constructor definido por los tipos de parámetros del objeto csv.
         */
        private Constructor<T> constructor;

        /**
         * Inicializa un nuevo objeto csv representativo de {@code symbClass}.
         * Se define un constructor para el objeto dependiendo del nombre de sus
         * parametros de entrada y de los campos declarados en la {@code Class}
         * {@code symbClass}.
         *
         * @param claseSimbolica la {@code Class} simbólica que representa los
         * objetos que se generen a partir de esta clase.
         * @param parametros los nombres de los parametros del constructor
         * necesarios para inicializar los objetos.
         */
        public ObjetoCSV(Class<T> claseSimbolica, String[] parametros) {

            try {
                int tamaño = parametros.length;
                tipos = new Class[tamaño];

                for (int i = 0; i < tamaño; i++) {
                    tipos[i] = claseSimbolica.getDeclaredField(parametros[i]).getType();
                }

                constructor = claseSimbolica.getConstructor(tipos);

            } catch (NoSuchMethodException | SecurityException | NoSuchFieldException ex) {
                Logger.getLogger(ObjetoCSV.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        /**
         * Instancia un nuevo objeto a partir del {@code String s} y el
         * {@code Constructor} definido para los objetos creados desde esta
         * clase. El método se encarga de transformar los datos divididos por
         * ',' en los tipos de datos requeridos por el {@code Constructor}.
         *
         * @see juang.csv.StringUtil#split
         * @see juang.csv.StringUtil#parseTo
         * @param cadena linea del documento .csv que representa las variables
         * del objeto.
         * @return un {@code Object} de la clase {@code symbClass} definida en
         * el constructor.
         * @throws IllegalArgumentException
         */
        public T crearObjeto(String cadena) {

            try {
                String[] campos = StringUtil.separar(cadena, ',').toArray();
                int tamaño = campos.length;
                Object[] argumentos = new Object[campos.length];

                for (int i = 0; i < tamaño; i++) {
                    argumentos[i] = StringUtil.parseClass(campos[i], tipos[i]);
                }

                return constructor.newInstance(argumentos);

            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(ObjetoCSV.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }
    }

    /**
     * El objeto plantilla para los objetos creados a partir de esta clase.
     */
    private ObjetoCSV<T> objetoCSV;
    /**
     * Las linas (sin incluir los encabezados de las columnas) del archivo.
     */
    public final Lista<String> lineas;
    /**
     * Lista de objetos generados por el lector csv.
     */
    private Lista<T> objetos;

    /**
     * Crea u n nuevo lector csv para un archivo en específico, los datos de
     * este son guardados en una {@code List<String>} para luego ser procesados
     * por el lector a conveniencia.
     *
     * @param ruta la ruta en disco donde se encuentra claseSimbolica archivo.
     * @param claseSymbolica clase simbólica representativa del objeto que
     * define el archivo csv.
     */
    public LectorCSV(String ruta, Class<T> claseSimbolica) {

        lineas = new Lista();
        objetos = new Lista();

        try {
            InputStreamReader sr = new InputStreamReader(getClass().getResourceAsStream(ruta));
            BufferedReader br = new BufferedReader(sr);
            String linea = br.readLine();
            String[] parametros = StringUtil.separar(linea, ',').toArray();
            objetoCSV = new ObjetoCSV(claseSimbolica, parametros);

            while ((linea = br.readLine()) != null) {
                lineas.agregar(linea);
            }

            generarObjetos();

        } catch (IOException ex) {
            Logger.getLogger(LectorCSV.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public T crearObjeto(String cadena) {

        return objetoCSV.crearObjeto(cadena);
    }

    private void generarObjetos() {

        for (Iterador<String> iterador = lineas.iterador(); iterador.tieneSiguiente();) {
            objetos.agregar(objetoCSV.crearObjeto(iterador.siguiente()));
        }
    }

    public Lista<T> getObjetos() {

        return objetos;
    }
}
