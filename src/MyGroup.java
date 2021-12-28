import com.oocourse.spec3.main.Group;
import com.oocourse.spec3.main.Person;

import java.util.ArrayList;

public class MyGroup implements Group {
    private int id;
    private ArrayList<Person> people=new ArrayList<Person>();
    public MyGroup(int id){
        this.id=id;
    }
    public  int getId(){
        return this.id;
    }

    public  boolean equals(Object obj){
        if(obj == null || !(obj instanceof Group)){
            return false;
        }
        else {
            return (((Group) obj).getId() == this.id);
        }
    }
    public void addPerson(Person person){
        this.people.add(person);
    }
    public  boolean hasPerson(Person person){
        for(int i=0;i<people.size();i++){
            if(people.get(i).equals(person)){
                return true;
            }
        }
        return false;
    }

    public  int getValueSum(){
        int value_sum=0;
        for(int i=0;i<people.size();i++){
            for(int j=0;j<people.size();j++){
                if(people.get(i).isLinked(people.get(j))){
                    value_sum+=people.get(i).queryValue(people.get(j));
                }
            }
        }
        return value_sum;
    }

    public  int getAgeMean(){
        if(people.size()==0)return 0;
        int agemean=0;
        for(int i=0;i< people.size();i++){
            agemean+=people.get(i).getAge();
        }
        return agemean/ people.size();
    }
    public  int getAgeVar(){
        if(people.size()==0)return 0;
        int age_var=0;
        for(int i=0;i<people.size();i++){
            int temp=people.get(i).getAge()-getAgeMean();
            age_var+=temp*temp;
        }
        return age_var/people.size();
    }
    public void delPerson(Person person){
        for(int i=0;i<people.size();i++){
            if(people.get(i).equals(person)){
                people.remove(person);
                i--;//i remove
            }
        }
    }
    public  int getSize(){
        return people.size();
    }
}