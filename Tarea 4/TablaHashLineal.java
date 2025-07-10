package Semana6;

public class TablaHashLineal {
    private Cliente[] tabla;
    private boolean[] ocupado;
    private int tamaño;
    private int elementos;
    private int colisiones;

    public TablaHashLineal(int tamaño) {
        // Usar un número primo para mejor distribución
        this.tamaño = siguientePrimo(tamaño);
        this.tabla = new Cliente[this.tamaño];
        this.ocupado = new boolean[this.tamaño];
        this.elementos = 0;
        this.colisiones = 0;
    }

    /**
     * Función hash basada en el método de módulo (por división)
     * H(k) = (K mod N) donde N es un número primo
     */
    private int funcionHash(String clave) {
        int hash = 0;
        for (int i = 0; i < clave.length(); i++) {
            hash = (hash * 31 + clave.charAt(i)) % tamaño;
        }
        return Math.abs(hash);
    }

    /**
     * Inserta un cliente usando reasignación lineal
     * Complejidad: O(1) promedio, O(n) peor caso
     */
    public boolean insertar(Cliente cliente) {
        if (elementos >= tamaño * 0.75) { // Factor de carga del 75%
            redimensionar();
        }

        String clave = cliente.generarClave();
        int indice = funcionHash(clave);
        int indiceOriginal = indice;

        // Reasignación lineal: busca la siguiente posición disponible
        while (ocupado[indice]) {
            // Si encuentra el mismo cliente, no lo inserta
            if (tabla[indice] != null && tabla[indice].generarClave().equals(clave)) {
                return false;
            }
            colisiones++;
            indice = (indice + 1) % tamaño; // Tratamiento circular

            // Si regresa al índice original, la tabla está llena
            if (indice == indiceOriginal) {
                return false;
            }
        }

        tabla[indice] = cliente;
        ocupado[indice] = true;
        elementos++;
        return true;
    }

    /**
     * Busca un cliente por su clave
     * Complejidad: O(1) promedio, O(n) peor caso
     */
    public Cliente buscar(String nombres, String apellidos) {
        String clave = (nombres + apellidos).toLowerCase().replaceAll("\\s+", "");
        int indice = funcionHash(clave);
        int indiceOriginal = indice;

        while (ocupado[indice] || tabla[indice] != null) {
            if (tabla[indice] != null && tabla[indice].generarClave().equals(clave)) {
                return tabla[indice];
            }
            indice = (indice + 1) % tamaño;

            // Si regresa al índice original, no se encontró
            if (indice == indiceOriginal) {
                break;
            }
        }

        return null;
    }

    /**
     * Redimensiona la tabla cuando el factor de carga es alto
     */
    private void redimensionar() {
        Cliente[] tablaAnterior = tabla;
        boolean[] ocupadoAnterior = ocupado;
        int tamañoAnterior = tamaño;

        tamaño = siguientePrimo(tamaño * 2);
        tabla = new Cliente[tamaño];
        ocupado = new boolean[tamaño];
        elementos = 0;
        colisiones = 0;

        // Reinsertar todos los elementos
        for (int i = 0; i < tamañoAnterior; i++) {
            if (ocupadoAnterior[i] && tablaAnterior[i] != null) {
                insertar(tablaAnterior[i]);
            }
        }
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
     * Obtiene estadísticas de la tabla
     */
    public String obtenerEstadisticas() {
        return String.format(
                "Tabla Hash Lineal:\n" +
                        "Tamaño: %d\n" +
                        "Elementos: %d\n" +
                        "Factor de carga: %.2f%%\n" +
                        "Colisiones: %d\n" +
                        "Eficiencia: %.2f%%",
                tamaño, elementos, (elementos * 100.0 / tamaño),
                colisiones, (elementos > 0 ? (elementos * 100.0 / (elementos + colisiones)) : 100.0)
        );
    }

    public int getColisiones() { return colisiones; }
    public int getElementos() { return elementos; }
    public int getTamaño() { return tamaño; }
}