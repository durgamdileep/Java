import java.util.LinkedHashMap;


public class InsertionOrder {
    public  static  void main(String[] args){


        LinkedHashMap<Integer,String> hm= new LinkedHashMap<>();

        hm.put(1,"one");
        hm.put(2,"two");
        hm.put(3,"three");
        hm.put(4,"four");
        hm.put(5,"five");

        hm.get(3);

        hm.forEach((key, value) ->
                System.out.println(key + " : " + value)
        );


    }
}
