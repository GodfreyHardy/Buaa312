import com.oocourse.spec3.exceptions.PersonIdNotFoundException;
import java.util.HashMap;
public class MyPersonIdNotFoundException extends PersonIdNotFoundException {
    private static int count=0;
    private int id;
    private static HashMap<Integer, Integer> countid = new HashMap<>();
    public MyPersonIdNotFoundException(int id){
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
        System.out.println(String.format("pinf-%d, %d-%d", count, id, countid.get(id)));
    }
}