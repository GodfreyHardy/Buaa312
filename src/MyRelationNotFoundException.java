import com.oocourse.spec3.exceptions.RelationNotFoundException;
import java.util.HashMap;

public class MyRelationNotFoundException extends RelationNotFoundException {
    private  int id1;
    private  int id2;
    private static int count=0;
    private static HashMap<Integer, Integer> countid = new HashMap<>();

    public MyRelationNotFoundException(int id1, int id2){
        this.id1=id1;
        this.id2=id2;
        count++;
        if(id1!=id2){
            if (countid.containsKey(id1)) {
                countid.put(id1,countid.get(id1) + 1);
            }
            else {
                countid.put(id1,1);
            }
            if (countid.containsKey(id2)) {
                countid.put(id2,countid.get(id2) + 1);
            }
            else {
                countid.put(id2,1);
            }
        }
        else{
            if (countid.containsKey(id1)) {
                countid.put(id1,countid.get(id1) + 1);
            }
            else {
                countid.put(id1,1);
            }
        }
    }
    @Override
    public void print(){
        if(id1<id2){
            System.out.println(String.format("rnf-%d, %d-%d, %d-%d",count,id1,countid.get(id1),id2,countid.get(id2)));
        }
        else{
            System.out.println(String.format("rnf-%d, %d-%d, %d-%d",count,id2,countid.get(id2),id1,countid.get(id1)));
        }
    }
}