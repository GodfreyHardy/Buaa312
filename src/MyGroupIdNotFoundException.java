import com.oocourse.spec3.exceptions.GroupIdNotFoundException;
import java.util.HashMap;
public class MyGroupIdNotFoundException extends GroupIdNotFoundException {
    private static int count=0;
    private int id;
    private static HashMap<Integer, Integer> countid = new HashMap<>();
    public MyGroupIdNotFoundException(int id){
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
        System.out.println(String.format("ginf-%d, %d-%d", count, id, countid.get(id)));
    }
}