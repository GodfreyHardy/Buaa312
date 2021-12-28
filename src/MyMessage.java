import com.oocourse.spec3.main.Message;
import com.oocourse.spec3.main.Person;
import com.oocourse.spec3.main.Group;
public class MyMessage implements Message {
    private int id;
    private int socialValue;
    private int type;
    private Person person1;
    private Person person2;
    private Group group;

    public MyMessage(int messageId, int messageSocialValue, Person messagePerson1, Person messagePerson2){
        this.type=0;
        this.group=null;
        this.id=messageId;
        this.socialValue=messageSocialValue;
        this.person1=  messagePerson1;
        this.person2=  messagePerson2;
    }

    public MyMessage(int messageId, int messageSocialValue, Person messagePerson1, Group messageGroup){
        this.type=1;
        this.person2=null;
        this.id=messageId;
        this.socialValue=messageSocialValue;
        this.person1=  messagePerson1;
        this.group=  messageGroup;
    }
    @Override
    public int getType() {
        return this.type;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public int getSocialValue() {
        return this.socialValue;
    }

    @Override
    public Person getPerson1() {
        return this.person1;
    }

    @Override
    public Person getPerson2() {//requires person2 != null;
        if (person2 != null) {
            return person2;
        } else {
            return null;
        }
    }

    @Override
    public Group getGroup() {//requires group != null;
        return this.group;
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof MyMessage)) {
            return false;
        }
        return ((MyMessage)obj).getId() == id;
    }
}