package Semana6;

public class Cliente {
    private String codigo;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String correo;
    private String direccion;
    private String codigoPostal;

    // Constructor
    public Cliente(String codigo, String nombres, String apellidos,
                   String telefono, String correo, String direccion, String codigoPostal) {
        this.codigo = codigo;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.codigoPostal = codigoPostal;
    }

    // Getters y Setters
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getCodigoPostal() { return codigoPostal; }
    public void setCodigoPostal(String codigoPostal) { this.codigoPostal = codigoPostal; }

    /**
     * Genera la clave para la función hash basada en nombres y apellidos
     * Concatena nombres y apellidos para crear una clave única
     */
    public String generarClave() {
        return (nombres + apellidos).toLowerCase().replaceAll("\\s+", "");
    }

    /**
     * Método para comparar clientes por su clave
     */
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cliente cliente = (Cliente) obj;
        return generarClave().equals(cliente.generarClave());
    }

    /**
     * Compara clientes para el árbol binario de búsqueda
     * Retorna: negativo si this < other, 0 si son iguales, positivo si this > other
     */
    public int compareTo(Cliente other) {
        return this.generarClave().compareTo(other.generarClave());
    }

    @Override
    public String toString() {
        return String.format("Cliente{código='%s', nombres='%s', apellidos='%s', teléfono='%s'}",
                codigo, nombres, apellidos, telefono);
    }

    /**
     * Representación completa del cliente
     */
    public String toStringCompleto() {
        return String.format(
                "Código: %s\nNombres: %s\nApellidos: %s\nTeléfono: %s\nCorreo: %s\nDirección: %s\nCódigo Postal: %s",
                codigo, nombres, apellidos, telefono, correo, direccion, codigoPostal
        );
    }
}