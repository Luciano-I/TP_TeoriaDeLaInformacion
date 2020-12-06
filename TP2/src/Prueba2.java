import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Prueba2 {
    public static void main(String[] args) {
        ArrayList<Integer> numeros =new ArrayList<Integer>();
        for (int i=10;i>0;i--)
            numeros.add(i);
        Iterator<Integer> it = numeros.iterator();
        while(it.hasNext())
            System.out.println(it.next());

        Collections.reverse(numeros);
        System.out.println("\n\n\n\n");
        it = numeros.iterator();
        while(it.hasNext())
            System.out.println(it.next());
    }
}
