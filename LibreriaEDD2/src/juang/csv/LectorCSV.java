package juang.csv;

import juang.utils.StringUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
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

        private boolean[] ignoreList;

        /**
         * Inicializa un nuevo objeto csv representativo de {@code symbClass}.Se define un constructor para el objeto dependiendo del nombre de sus
 parametros de entrada y de los campos declarados en la {@code Class}
        {@code symbClass}.
         *
         * @param claseSimbolica la {@code Class} simbólica que representa los
         * objetos que se generen a partir de esta clase.
         * @param parametros los nombres de los parametros del constructor
         * necesarios para inicializar los objetos.
         * @param ignoreList
         */
        public ObjetoCSV(Class<T> claseSimbolica, String[] parametros, boolean[] ignoreList) {

            this.ignoreList = ignoreList;

            try {
                int tamaño = parametros.length;
                tipos = new Class[tamaño];

                for (int i = 0; i < tamaño; i++) {
                    tipos[i] = claseSimbolica.getDeclaredField(parametros[i]).getType();
                }

                constructor = claseSimbolica.getConstructor(tipos);
                System.out.println("[LectorCSV] Clase válida"
                        + "");

            } catch (NoSuchMethodException | SecurityException | NoSuchFieldException ex) {
                System.out.println("[LectorCSV] Error: no se ha encontrado un"
                        + "constructor en la clase " + claseSimbolica.getName()
                        + " que contenga los parámetros requeridos");
            }
        }

        /**
         * Instancia un nuevo objeto a partir del {@code String s} y el
         * {@code Constructor} definido para los objetos creados desde esta
         * clase.El método se encarga de transformar los datos divididos por
 ',' en los tipos de datos requeridos por el {@code Constructor}.
         *
         * @param campos
         * @see juang.csv.StringUtil#split
         * @see juang.csv.StringUtil#parseTo
         * @return un {@code Object} de la clase {@code symbClass} definida en
         * el constructor.
         * @throws IllegalArgumentException
         */
        public T crearObjeto(Lista<String> campos) {

            try {
                Object[] argumentos = new Object[tipos.length];
                Iterador<String> it = campos.iterador();
                int contador = 0;
                int i = 0;
                while (it.tieneSiguiente()) {
                    String campo = it.siguiente();
                    if (!ignoreList[contador]) {
                        argumentos[i] = StringUtil.parseClass(campo, tipos[i]);
                        i++;
                    }
                    contador++;
                }
                return constructor.newInstance(argumentos);

            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(ObjetoCSV.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }
    }

    private final char separador;
    private final String ruta;

    /**
     * El objeto plantilla para los objetos creados a partir de esta clase.
     */
    private ObjetoCSV<T> objetoCSV;

    /**
     * Crea u n nuevo lector csv para un archivo en específico, los datos de
     * este son guardados en una {@code List<String>} para luego ser procesados
     * por el lector a conveniencia.
     *
     * @param ruta la ruta en disco donde se encuentra claseSimbolica archivo.
     * @param claseSimbolica clase simbólica representativa del objeto que
     * define el archivo csv.
     * @param separador
     */
    public LectorCSV(String ruta, Class<T> claseSimbolica, char separador) {
        this.separador = separador;
        this.ruta = ruta;
        System.out.println("[LectorCSV] clase simbólica: " + claseSimbolica.getName());
        InputStreamReader sr = new InputStreamReader(getClass().getResourceAsStream(ruta));
        try {
            Lista<String> parametros = StringUtil.separar(new BufferedReader(sr).readLine(), separador);
            boolean[] ignoreList = getIgnoreList(parametros);

            Iterador<String> it = parametros.iterador();
            int contador = 0;
            while (it.tieneSiguiente()) {
                it.siguiente();
                if (ignoreList[contador] == true) {
                    it.remover();
                }
                contador++;
            }

            System.out.print("[LectorCSV] parámetros requeridos: ");
            System.out.println(Arrays.toString(parametros.toArray()));
            objetoCSV = new ObjetoCSV(claseSimbolica, parametros.toArray(), ignoreList);
        } catch (IOException ex) {
            Logger.getLogger(LectorCSV.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private boolean[] getIgnoreList(Lista<String> parametros) {
        int tamaño = parametros.tamaño();
        boolean[] ignorar = new boolean[tamaño];
        Iterador<String> it = parametros.iterador();
        int contador = 0;
        while (it.tieneSiguiente()) {
            if (it.siguiente().equals("")) {
                ignorar[contador] = true;
            }
            contador++;
        }
        return ignorar;
    }

    public T crearObjeto(String cadena) {

        Lista<String> campos = StringUtil.separar(cadena, separador);
        return objetoCSV.crearObjeto(campos);
    }

    public Lista<T> getObjetos() {

        try {
            long startTime = System.nanoTime();

            InputStreamReader sr = new InputStreamReader(getClass().getResourceAsStream(ruta));
            BufferedReader br = new BufferedReader(sr);
            br.readLine();
            String linea;

            Lista<T> objetos = new Lista<>();
            while ((linea = br.readLine()) != null) {
                objetos.agregar(crearObjeto(linea));
            }

            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1000000;
            System.out.println("[LectorCSV] Objetos generados en: "+duration+" ms.");
            return objetos;

        } catch (IOException ex) {
            Logger.getLogger(LectorCSV.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
