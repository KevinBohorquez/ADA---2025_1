package Semana6;

public class NodoArbol {
    private Cliente cliente;
    private NodoArbol izquierdo;
    private NodoArbol derecho;

    public NodoArbol(Cliente cliente) {
        this.cliente = cliente;
        this.izquierdo = null;
        this.derecho = null;
    }

    // Getters y Setters
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public NodoArbol getIzquierdo() { return izquierdo; }
    public void setIzquierdo(NodoArbol izquierdo) { this.izquierdo = izquierdo; }

    public NodoArbol getDerecho() { return derecho; }
    public void setDerecho(NodoArbol derecho) { this.derecho = derecho; }

    /**
     * Inserta un cliente en el árbol binario de búsqueda
     */
    public void insertar(Cliente nuevoCliente) {
        int comparacion = nuevoCliente.compareTo(this.cliente);

        if (comparacion < 0) {
            if (izquierdo == null) {
                izquierdo = new NodoArbol(nuevoCliente);
            } else {
                izquierdo.insertar(nuevoCliente);
            }
        } else if (comparacion > 0) {
            if (derecho == null) {
                derecho = new NodoArbol(nuevoCliente);
            } else {
                derecho.insertar(nuevoCliente);
            }
        }
        // Si comparacion == 0, el cliente ya existe (no se inserta duplicado)
    }

    /**
     * Busca un cliente en el árbol por su clave
     */
    public Cliente buscar(String clave) {
        String claveActual = cliente.generarClave();
        int comparacion = clave.compareTo(claveActual);

        if (comparacion == 0) {
            return cliente;
        } else if (comparacion < 0 && izquierdo != null) {
            return izquierdo.buscar(clave);
        } else if (comparacion > 0 && derecho != null) {
            return derecho.buscar(clave);
        }

        return null; // No encontrado
    }

    /**
     * Realiza un recorrido en orden del árbol
     */
    public void recorridoEnOrden(StringBuilder sb) {
        if (izquierdo != null) {
            izquierdo.recorridoEnOrden(sb);
        }
        sb.append(cliente.toString()).append("\n");
        if (derecho != null) {
            derecho.recorridoEnOrden(sb);
        }
    }
}