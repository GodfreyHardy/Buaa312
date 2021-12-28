import com.oocourse.spec3.exceptions.EqualEmojiIdException;
import java.util.HashMap;
public class MyEqualEmojiIdException extends EqualEmojiIdException {
    private static int count=0;
    private int id;
    private static HashMap<Integer, Integer> countid = new HashMap<>();
    public MyEqualEmojiIdException(int id){
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
        System.out.println(String.format("eei-%d, %d-%d", count, id, countid.get(id)));
    }
}