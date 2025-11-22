import java.util.LinkedHashMap;

public class AccessOrder {
    public static void  main(String[] args){

        LinkedHashMap<Integer,String> hm= new LinkedHashMap<>(16,0.75f,true);

        hm.put(1,"one");
        hm.put(2,"two");
        hm.put(3,"three");
        hm.put(4,"four");
        hm.put(5,"five");

        System.out.println("After insertion: ");
        hm.forEach((key, value) ->
                System.out.println(key + " : " + value)
        );

        hm.get(3);

        System.out.println("After Fetching: ");
        hm.forEach((key, value) ->
                System.out.println(key + " : " + value)
        );

        System.out.println(" least accessed element at the beginning");
        System.out.println(" Most recently accessed element at the end");



        LinkedHashMap<Integer,String> hm1= new CustomizeLinkedHashMap(16,0.75f,true);

        hm1.put(1,"one");
        hm1.put(2,"two");
        hm1.put(3,"three");
        hm1.put(4,"four");
        hm1.put(5,"five");

        System.out.println("Using remove eldest entry : ");
        hm1.forEach((key, value) ->
                System.out.println(key + " : " + value)
        );
    }
}
