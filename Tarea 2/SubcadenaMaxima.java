public class SubcadenaMaxima {
    public static void main(String[] args) {
        int[] original = {7, -4, 10, 7, 3, -3, 4, 8, -2, -10};
        int[] ajustado = new int[original.length];

        ajustado[0] = original[0];
        for (int i = 1; i < original.length; i++) {
            ajustado[i] = ajustado[i - 1] + original[i];
        }

        int acum = 0;
        int aux = Integer.MIN_VALUE;
        int min = -1;
        int max = -1;

        for (int i = 0; i < ajustado.length; i++) {
            int suma = ajustado[i] + acum;

            if (suma < 0) {
                acum += Math.abs(suma);
                min = i;
            } else {
                if (suma > aux) {
                    aux = suma;
                    max = i;
                }
            }

        }

        if (min == -1) min = 0;
        if (ajustado[min] < 0) min++;

        System.out.print("Arreglo: {");
        for (int i = 0; i < original.length; i++) {
            System.out.print(original[i]);
            if (i != original.length - 1) System.out.print(", ");
        }
        System.out.println("}");

        System.out.println("Índice mínimo: " + min);
        System.out.println("Índice máximo: " + max);

        int aux2 = 0;
        // Mostrar subcadena de interés
        if (min != -1 && max != -1 && min <= max) {
            System.out.print("Subcadena maxima: {");
            for (int i = min; i <= max; i++) {
                System.out.print(original[i]);
                if (i != max) System.out.print(", ");
                aux2 += original[i];
            }
            System.out.println("}");
            System.out.println("Suma de la subcadena maxima: " + aux2);
        } else {
            System.out.println("No se encontró una subcadena válida.");
        }
    }
}