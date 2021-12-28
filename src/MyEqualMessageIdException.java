import com.oocourse.spec3.exceptions.EqualMessageIdException;
import java.util.HashMap;
public class MyEqualMessageIdException extends EqualMessageIdException {
    private static int count=0;
    private int id;
    private static HashMap<Integer, Integer> countid = new HashMap<>();
    public MyEqualMessageIdException(int id){
        count++;
        this.id=id;
        if (countid.containsKey(id)) {
            countid.put(id,countid.get(id) + 1);
        }
        else {
            countid.put(id,1);
        }
    }
    @Override
    public void print(){
        System.out.println(String.format("emi-%d, %d-%d", count, id, countid.get(id)));
    }
}