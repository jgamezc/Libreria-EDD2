package juang.csv;

import juang.utils.StringUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import juang.colecciones.List;

/**
 * Esta clase maneja un archivo .csv en específico, define los métodos
 * necesarios para la creación de los objetos que este representa.
 *
 * @author juang
 * @param <T> El tipo de dato u objeto representado por este archivo .csv
 */
public class CsvReader<T> {

    /**
     * Esta clase define un objeto que guarda de manera simbolica los elementos
     * necesarios para instanciar un objeto de alguna clase cualquiera.
     *
     * @author juang
     * @param <T> Tipo de dato o clase de los elementos que se generan a partir
     * de este objeto csv;
     */
    public class CsvObject<T> {

        /**
         * Arreglo que guarda los tipos de parametros necesarios para instanciar
         * el constructor del objeto en su respectivo orden.
         */
        private Class[] types;
        /**
         * El constructor definido por los tipos de parámetros del objeto csv.
         */
        private Constructor<T> constructor;

        private boolean[] ignoreList;

        /**
         * Inicializa un nuevo objeto csv representativo de {@code symbClass}.Se
         * define un constructor para el objeto dependiendo del nombre de sus
         * parametros de entrada y de los campos declarados en la {@code Class}
         * {@code symbClass}.
         *
         * @param classReference la {@code Class} simbólica que representa los
         * objetos que se generen a partir de esta clase.
         * @param parameters los nombres de los parametros del constructor
         * necesarios para inicializar los objetos.
         * @param ignoreList
         */
        public CsvObject(Class<T> classReference, String[] parameters, boolean[] ignoreList) {

            this.ignoreList = ignoreList;

            try {
                int size = parameters.length;
                types = new Class[size];

                for (int i = 0; i < size; i++) {
                    types[i] = classReference.getDeclaredField(parameters[i]).getType();
                }

                constructor = classReference.getConstructor(types);
                System.out.println("[LectorCSV] Clase válida"
                        + "");

            } catch (NoSuchMethodException | SecurityException | NoSuchFieldException ex) {
                System.out.println("[LectorCSV] Error: no se ha encontrado un"
                        + "constructor en la clase " + classReference.getName()
                        + " que contenga los parámetros requeridos");
            }
        }

        /**
         * Instancia un nuevo objeto a partir del {@code String s} y el
         * {@code Constructor} definido para los objetos creados desde esta
         * clase.El método se encarga de transformar los datos divididos por ','
         * en los tipos de datos requeridos por el {@code Constructor}.
         *
         * @param fields
         * @see juang.csv.StringUtil#split
         * @see juang.csv.StringUtil#parseTo
         * @return un {@code Object} de la clase {@code symbClass} definida en
         * el constructor.
         * @throws IllegalArgumentException
         */
        public T createObject(List<String> fields) {

            try {
                Object[] arguments = new Object[types.length];
                int contador = 0;
                int i = 0;

                for (String field : fields) {
                    if (!ignoreList[contador]) {
                        arguments[i] = StringUtil.parseClass(field, types[i]);
                        i++;
                    }
                    contador++;
                }
                return constructor.newInstance(arguments);

            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(CsvObject.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }
    }

    private final char separator;
    private final String path;

    /**
     * El objeto plantilla para los objetos creados a partir de esta clase.
     */
    private CsvObject<T> csvObject;

    /**
     * Crea u n nuevo lector csv para un archivo en específico, los datos de
     * este son guardados en una {@code List<String>} para luego ser procesados
     * por el lector a conveniencia.
     *
     * @param path la ruta en disco donde se encuentra claseSimbolica archivo.
     * @param classReference clase simbólica representativa del objeto que
     * define el archivo csv.
     * @param separator
     */
    public CsvReader(String path, Class<T> classReference, char separator) {
        this.separator = separator;
        this.path = path;
        System.out.println("[LectorCSV] clase simbólica: " + classReference.getName());
        InputStreamReader sr = new InputStreamReader(getClass().getResourceAsStream(path));
        try {
            List<String> parameters = StringUtil.split(new BufferedReader(sr).readLine(), separator);
            boolean[] ignoreList = getIgnoreList(parameters);

            Iterator<String> it = parameters.iterator();
            int count = 0;
            while (it.hasNext()) {
                it.next();
                if (ignoreList[count] == true) {
                    it.remove();
                }
                count++;
            }

            System.out.print("[LectorCSV] parámetros requeridos: ");
            System.out.println(Arrays.toString(parameters.toArray()));
            csvObject = new CsvObject(classReference, parameters.toArray(), ignoreList);
        } catch (IOException ex) {
            Logger.getLogger(CsvReader.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private boolean[] getIgnoreList(List<String> parameters) {
        int tamaño = parameters.count();
        boolean[] ignoreList = new boolean[tamaño];
        int count = 0;
        
        for (String parameter : parameters) {
            if (parameter.equals("")) {
                ignoreList[count] = true;
            }
            count++;
        }
        return ignoreList;
    }

    public T createObject(String s) {

        List<String> fields = StringUtil.split(s, separator);
        return csvObject.createObject(fields);
    }

    public List<T> getObjects() {

        try {
            long startTime = System.nanoTime();

            InputStreamReader sr = new InputStreamReader(getClass().getResourceAsStream(path));
            BufferedReader br = new BufferedReader(sr);
            br.readLine();
            String line;

            List<T> objects = new List();
            while ((line = br.readLine()) != null) {
                objects.add(createObject(line));
            }

            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1000000;
            System.out.println("[LectorCSV] Objetos generados en: " + duration + " ms.");
            return objects;

        } catch (IOException ex) {
            Logger.getLogger(CsvReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
