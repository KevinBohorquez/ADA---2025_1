package Semana6;

public class TablaHash {
    private NodoArbol[] tabla;
    private int tamaño;
    private int elementos;
    private int colisiones;

    public TablaHash(int tamaño) {
        // Usar un número primo para mejor distribución
        this.tamaño = siguientePrimo(tamaño);
        this.tabla = new NodoArbol[this.tamaño];
        this.elementos = 0;
        this.colisiones = 0;
    }

    /**
     * Función hash basada en el método de módulo con mejor distribución
     * Utiliza el algoritmo de hash de cadenas con multiplicación
     */
    private int funcionHash(String clave) {
        int hash = 0;
        for (int i = 0; i < clave.length(); i++) {
            hash = (hash * 31 + clave.charAt(i)) % tamaño;
        }
        return Math.abs(hash);
    }

    /**
     * Inserta un cliente usando encadenamiento con árboles binarios
     * Complejidad: O(log n) promedio por posición, O(n) peor caso
     */
    public boolean insertar(Cliente cliente) {
        String clave = cliente.generarClave();
        int indice = funcionHash(clave);

        if (tabla[indice] == null) {
            // Primera inserción en esta posición
            tabla[indice] = new NodoArbol(cliente);
            elementos++;
            return true;
        } else {
            // Hay colisión - insertar en el árbol binario
            Cliente existente = tabla[indice].buscar(clave);
            if (existente != null) {
                return false; // Cliente ya existe
            }

            tabla[indice].insertar(cliente);
            elementos++;
            colisiones++;
            return true;
        }
    }

    /**
     * Busca un cliente por nombres y apellidos
     * Complejidad: O(log n) promedio por posición
     */
    public Cliente buscar(String nombres, String apellidos) {
        String clave = (nombres + apellidos).toLowerCase().replaceAll("\\s+", "");
        int indice = funcionHash(clave);

        if (tabla[indice] == null) {
            return null;
        }

        return tabla[indice].buscar(clave);
    }

    /**
     * Encuentra el siguiente número primo mayor o igual a n
     */
    private int siguientePrimo(int n) {
        while (!esPrimo(n)) {
            n++;
        }
        return n;
    }

    /**
     * Verifica si un número es primo
     */
    private boolean esPrimo(int n) {
        if (n < 2) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;

        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }

    /**
     * Obtiene el contenido de una posición específica de la tabla
     */
    public String obtenerContenidoPosicion(int indice) {
        if (indice < 0 || indice >= tamaño || tabla[indice] == null) {
            return "Posición vacía";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Posición ").append(indice).append(":\n");
        tabla[indice].recorridoEnOrden(sb);
        return sb.toString();
    }

    /**
     * Calcula la distribución de elementos por posición
     */
    public String obtenerDistribucion() {
        StringBuilder sb = new StringBuilder();
        sb.append("Distribución de elementos por posición:\n");

        int posicionesOcupadas = 0;
        int maxElementosPorPosicion = 0;

        for (int i = 0; i < tamaño; i++) {
            if (tabla[i] != null) {
                posicionesOcupadas++;
                int elementosEnPosicion = contarElementosEnPosicion(i);
                if (elementosEnPosicion > maxElementosPorPosicion) {
                    maxElementosPorPosicion = elementosEnPosicion;
                }
                sb.append(String.format("Pos %d: %d elementos\n", i, elementosEnPosicion));
            }
        }

        sb.append(String.format("\nResumen:\n"));
        sb.append(String.format("Posiciones ocupadas: %d/%d\n", posicionesOcupadas, tamaño));
        sb.append(String.format("Máximo elementos por posición: %d\n", maxElementosPorPosicion));

        return sb.toString();
    }

    /**
     * Cuenta los elementos en una posición específica del árbol
     */
    private int contarElementosEnPosicion(int indice) {
        if (tabla[indice] == null) return 0;
        return contarNodosArbol(tabla[indice]);
    }

    /**
     * Cuenta recursivamente los nodos de un árbol
     */
    private int contarNodosArbol(NodoArbol nodo) {
        if (nodo == null) return 0;
        return 1 + contarNodosArbol(nodo.getIzquierdo()) + contarNodosArbol(nodo.getDerecho());
    }

    /**
     * Obtiene estadísticas de la tabla
     */
    public String obtenerEstadisticas() {
        int posicionesOcupadas = 0;
        int maxElementosPorPosicion = 0;
        int totalNiveles = 0;

        for (int i = 0; i < tamaño; i++) {
            if (tabla[i] != null) {
                posicionesOcupadas++;
                int elementosEnPosicion = contarElementosEnPosicion(i);
                if (elementosEnPosicion > maxElementosPorPosicion) {
                    maxElementosPorPosicion = elementosEnPosicion;
                }
                totalNiveles += calcularAltura(tabla[i]);
            }
        }

        double alturaPromedio = posicionesOcupadas > 0 ? (double)totalNiveles / posicionesOcupadas : 0;

        return String.format(
                "Tabla Hash con Árboles:\n" +
                        "Tamaño: %d\n" +
                        "Elementos: %d\n" +
                        "Posiciones ocupadas: %d\n" +
                        "Factor de carga: %.2f%%\n" +
                        "Colisiones: %d\n" +
                        "Máx. elementos por posición: %d\n" +
                        "Altura promedio de árboles: %.2f",
                tamaño, elementos, posicionesOcupadas, (elementos * 100.0 / tamaño),
                colisiones, maxElementosPorPosicion, alturaPromedio
        );
    }

    /**
     * Calcula la altura de un árbol binario
     */
    private int calcularAltura(NodoArbol nodo) {
        if (nodo == null) return 0;
        return 1 + Math.max(calcularAltura(nodo.getIzquierdo()), calcularAltura(nodo.getDerecho()));
    }

    public int getColisiones() { return colisiones; }
    public int getElementos() { return elementos; }
    public int getTamaño() { return tamaño; }
}