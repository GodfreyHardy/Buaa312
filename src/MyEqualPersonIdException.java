import com.oocourse.spec3.exceptions.EqualPersonIdException;
import java.util.HashMap;

public class MyEqualPersonIdException extends EqualPersonIdException {
    private static int count=0;
    private int id;
    private static HashMap<Integer, Integer> countid = new HashMap<>();
    public MyEqualPersonIdException(int id){
        count++;
        this.id=id;
        if (countid.containsKey(id)) {
            countid.put(id,countid.get(id) + 1);//repalce
        }
        else {
            countid.put(id,1);
        }
    }
    @Override
    public void print(){
        System.out.println(String.format("epi-%d, %d-%d", count, id, countid.get(id)));
    }
}
