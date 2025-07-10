import java.util.Scanner;

public class TareaCalificada1_BohorquezHuaringaKevin {

    public static void resolvedorDeSopas(char[][] matriz, String[] palabras) {
        int n = matriz.length;

        for (String palabra : palabras) {
            boolean encontrada = false;

            int[][] movimientos = {
                    {0, 1}, {1, 0}, {1, 1}, {1, -1}, {0, -1}, {-1, 0}, {-1, -1}, {-1, 1}
            };

            for (int i = 0; i < n && !encontrada; i++) {
                for (int j = 0; j < n && !encontrada; j++) {
                    for (int[] dir : movimientos) {
                        int dx = dir[0];
                        int dy = dir[1];

                        int x = i, y = j, k;
                        for (k = 0; k < palabra.length(); k++) {
                            if (x < 0 || x >= n || y < 0 || y >= n || matriz[x][y] != palabra.charAt(k)) {
                                break;
                            }
                            x += dx;
                            y += dy;
                        }

                        if (k == palabra.length()) {
                            int finX = i + dx * (palabra.length() - 1);
                            int finY = j + dy * (palabra.length() - 1);
                            System.out.println("Palabra \"" + palabra + "\" encontrada desde (" + i + "," + j + ") hasta (" + finX + "," + finY + ")");
                            encontrada = true;
                            break;
                        }
                    }
                }
            }

            if (!encontrada) {
                System.out.println("Palabra \"" + palabra + "\" no encontrada.");
            }
        }
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Digite la dimensión del arreglo N: ");
        int n = sc.nextInt();
        sc.nextLine();

        System.out.println("Digite las letras de cada fila en un solo string, separadas por espacios");
        System.out.println("Ejemplo: Si n = 4, entonces podria ingresar: \"esto sttm easa prne\"");
        System.out.print("-> ");
        String linea = sc.nextLine();

        String[] filas = linea.trim().split(" ");
        if (filas.length != n) {
            System.out.println("Error: Se esperaban " + n + " filas, pero se ingresaron " + filas.length);
            return;
        }

        char[][] matriz = new char[n][n];
        for (int i = 0; i < n; i++) {
            String fila = filas[i].toUpperCase();
            if (fila.length() != n) {
                System.out.println("Error: La fila " + (i + 1) + " no tiene " + n + " caracteres.");
                return;
            }
            matriz[i] = fila.toCharArray();
        }

        System.out.println("Matriz:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("Ingrese las palabras a buscar (separadas por espacios):");
        System.out.println("Por ejemplo: \"esto ese pato este\"");
        System.out.print("-> "); String palabrasInput = sc.nextLine();
        String[] palabrasCrudas = palabrasInput.trim().split(" ");

        int contadorValidas = 0;
        for (String palabra : palabrasCrudas) {
            if (!palabra.isEmpty() && palabra.length() <= n) {
                contadorValidas++;
            }
        }

        String[] palabrasValidas = new String[contadorValidas];
        int index = 0;
        for (String palabra : palabrasCrudas) {
            if (!palabra.isEmpty() && palabra.length() <= n) {
                palabrasValidas[index] = palabra.toUpperCase();
                index++;
            }
        }

        System.out.println("\nPalabras válidas (longitud <= " + n + "):");
        for (int i = 0; i < palabrasValidas.length; i++) {
            System.out.print(palabrasValidas[i] + " - ");
        }

        System.out.println("\nBuscando palabras en la sopa de letras...\n");

        System.out.println("Sopa de letras:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }

        resolvedorDeSopas(matriz, palabrasValidas);


    }
}
