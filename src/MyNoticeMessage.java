import com.oocourse.spec3.main.Group;
import com.oocourse.spec3.main.Person;
import com.oocourse.spec3.main.NoticeMessage;


public class MyNoticeMessage implements NoticeMessage {
        private int type=0;
        private Group group;
        private int id;
        private Person person1;
        private Person person2;
        private String string;
        private int socialValue;

    public MyNoticeMessage(int messageId, String noticeString, Person messagePerson1, Person messagePerson2){
        this.type=0;
        this.group=null;
        this.id=messageId;
        this.person1=messagePerson1;
        this.person2=messagePerson2;
        this.string=noticeString;
        this.socialValue=noticeString.length();
    }


    public MyNoticeMessage(int messageId, String noticeString, Person messagePerson1, Group messageGroup){
        this.type=1;
        this.group=messageGroup;
        this.id=messageId;
        this.person1=messagePerson1;
        this.person2=null;
        this.string=noticeString;
        this.socialValue=noticeString.length();
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
        if (obj == null || !(obj instanceof MyNoticeMessage)) {
            return false;
        }
        return ((MyNoticeMessage)obj).getId() == id;
    }
    @Override
    public String getString(){
        return this.string;
    }
}