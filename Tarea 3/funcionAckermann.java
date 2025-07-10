package TareaRecursividad;

import java.util.Stack;

public class funcionAckermann {

    public static int ackermann(int m, int n) {
        Stack<Integer> pila = new Stack<>();

        pila.push(m);
        while (!pila.isEmpty()) {
            m = pila.pop();

            if (m == 0) {
                n+=1;
            } else if (n == 0) {
                pila.push(m - 1);
                n=1;
            } else {
                pila.push(m - 1);
                pila.push(m);
                n-=1;
            }
        }
        return n;
    }

    public static void main(String[] args) {
        System.out.println("A(1, 2) = " + ackermann(1, 2));
    }
}

