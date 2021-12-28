import com.oocourse.spec3.main.Message;
import com.oocourse.spec3.main.Person;
import java.util.ArrayList;


public class MyPerson implements Person {
    private int id;
    private String name="";
    private int age;
    private int socialValue=0;
    private int money=0;
    private ArrayList<Person>acquaintance=new ArrayList<Person>();
    private ArrayList<Integer>value=new ArrayList<Integer>();
    private ArrayList<Message>messages=new ArrayList<Message>();
    public MyPerson(int id, String name, int age){
        this.id=id;
        this.name=name;
        this.age=age;
    }
    public  int getId(){
        return this.id;
    }
    public  String getName(){
        return this.name;
    }
    public  int getAge(){
        return this.age;
    }
    public  boolean equals(Object obj){
        if(obj == null || !(obj instanceof Person)){
            return false;
        }
        else{
            return (((Person) obj).getId() == id);
        }
    }
    public  boolean isLinked(Person person){
        if(person.getId()==this.id)return true;//避免没有acquaintance的情况
        for(int i=0;i<acquaintance.size();i++){
            if(acquaintance.get(i).getId()==person.getId()){
                return true;
            }
        }
        return false;
    }
    public int queryValue(Person person){
        for(int i=0;i<acquaintance.size();i++){
            if(acquaintance.get(i).getId()==person.getId()){
                return value.get(i);
            }
        }
        return  0;
    }
    public ArrayList<Integer> getValue(){
        return this.value;
    }
    public  int compareTo(Person p2){
        return name.compareTo(p2.getName());
    }
    public  void addSocialValue(int num){
        this.socialValue=this.socialValue+num;
    }
    public  int getSocialValue(){
        return this.socialValue;
    }
    public  ArrayList<Person> getAcquaintance(){
        return this.acquaintance;
    }
    public  ArrayList<Message> getMessages(){
        return this.messages;
    }
    public  ArrayList<Message> getReceivedMessages(){
        ArrayList<Message> received_messages =new ArrayList<Message>();
        int m_size=(messages.size() >= 4)?4:messages.size();
        for(int i=0;i<m_size;i++){
            received_messages.add(messages.get(i));
        }
        return received_messages;
    }
    public void addMoney(int num){
        this.money=this.money+num;
    }
    public  int getMoney(){
        return this.money;
    }
}