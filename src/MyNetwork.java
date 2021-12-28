import com.oocourse.spec3.exceptions.*;
import com.oocourse.spec3.main.*;

import java.util.*;

public class MyNetwork implements Network {
    private   HashMap<Integer,Person> people;
    private   HashMap<Integer,Group> groups;
    private   HashMap<Integer,Message> messages;
    private   ArrayList<Integer> emojiIdList;
    private   ArrayList<Integer> emojiHeatList;

    public MyNetwork() {
        people = new HashMap<>(6700);//expected size/0.75+1
        groups = new HashMap<>(16);
        messages = new HashMap<>(6700);
        emojiHeatList = new ArrayList<Integer>();
        emojiIdList = new ArrayList<Integer>();
    }
    @Override
    public boolean contains(int id) {
        return people.containsKey(id);
    }

    @Override
    public Person getPerson(int id) {
        if(people.containsKey(id)){
            return people.get(id);
        }
        return null;
    }

    @Override
    public void addPerson(Person person) throws EqualPersonIdException {
        if(people.containsKey(person.getId())){
            throw new MyEqualPersonIdException(person.getId());
        }
        else{
            people.put(person.getId(),person);
        }
    }

    @Override
    public void addRelation(int id1, int id2, int value) throws PersonIdNotFoundException, EqualRelationException {
        if(!contains(id1)){//是并列关系不是if else-if
            throw  new MyPersonIdNotFoundException(id1);
        }
        if(contains(id1) && !contains(id2)){
            throw  new MyPersonIdNotFoundException(id2);
        }
        if(contains(id1) && contains(id2) && getPerson(id1).isLinked(getPerson(id2))){//id1==id2
            throw new MyEqualRelationException(id1,id2);
        }
        MyPerson person1 = (MyPerson) getPerson(id1);
        MyPerson person2 = (MyPerson) getPerson(id2);
        person1.getAcquaintance().add(person2);
        person2.getAcquaintance().add(person1);
        person1.getValue().add(value);
        person2.getValue().add(value);
    }

    @Override
    public int queryValue(int id1, int id2) throws PersonIdNotFoundException, RelationNotFoundException {
        if(!contains(id1)){
            throw  new MyPersonIdNotFoundException(id1);
        }
        else if(contains(id1) && !contains(id2)){
            throw  new MyPersonIdNotFoundException(id2);
        }
        else if(contains(id1) && contains(id2) && !getPerson(id1).isLinked(getPerson(id2))){
            throw new MyRelationNotFoundException(id1,id2);
        }

        return getPerson(id1).queryValue(getPerson(id2));

    }

    @Override
    public int compareName(int id1, int id2) throws PersonIdNotFoundException {
        if(!contains(id1)){
            throw  new MyPersonIdNotFoundException(id1);
        }
        else if(contains(id1) && !contains(id2)){
            throw  new MyPersonIdNotFoundException(id2);
        }
        return getPerson(id1).getName().compareTo(getPerson(id2).getName());
    }

    @Override
    public int queryPeopleSum() {
        return people.size();
    }
    /*目前的复杂度基本是O(n)，从id->Person虽然理论上是O(1)但是仍然有性能损失
     * 为了提高效率，直接使用Person遍历，不采用id->person*/
    @Override
    public int queryNameRank(int id) throws PersonIdNotFoundException {
        if(!contains(id)){
            throw  new MyPersonIdNotFoundException(id);
        }
        int sum=0;
        Person p1 = getPerson(id);
        for(Person p2:people.values()){
            if(p1.getName().compareTo(p2.getName()) > 0){
                sum+=1;
            }
        }
        sum=sum+1;
        return sum;
    }

    @Override
    public boolean isCircle(int id1, int id2) throws PersonIdNotFoundException {//bfs 宽度搜索
        if(!contains(id1)){//id1==id2
            throw new MyPersonIdNotFoundException(id1);
        }
        else if(contains(id1) && !contains(id2)){
            throw new MyPersonIdNotFoundException(id2);
        }

        return bfs_find(id1,id2);
    }
    public boolean bfs_find(int id1,int id2){
        if (id1 == id2 ) {
            return true;
        }
        HashMap<Integer,Boolean>visited = new HashMap<Integer, Boolean>(6700);
        ArrayList<Person>queue = new ArrayList<Person>(6700);
        for(Integer i: people.keySet()){//visited 初始化
            visited.put(i,false);
        }
        queue.add(getPerson(id1));
        Person p2 = getPerson(id2) ;
        visited.put(id1,true);
        while(queue.size()>0){
            MyPerson p1 = (MyPerson) queue.remove(0);
            for(int i=0;i<p1.getAcquaintance().size();i++){
                Person neighbour = p1.getAcquaintance().get(i);
                boolean flag = visited.get(neighbour.getId());
                if(!flag){
                    if(neighbour.equals(p2)){
                        return true;
                    }
                    queue.add(neighbour);
                    visited.put(neighbour.getId(),true);
                }
                else{//标记了就跳过
                    continue;
                }
            }
        }
        return false;
    }
    @Override
    public int queryBlockSum() {//bfs 求连通子图的个数
        if (people.size() == 0) return 0;
        int count = 0;
        Set<Integer> visited = new HashSet<Integer>(6700);
        ArrayList<Person> queue = new ArrayList<Person>(6700);
        for (Integer i : people.keySet()) {//visited 初始化
            visited.add(i);
        }
        for (Person p : people.values()) {
            queue.add(p);
            visited.remove(p.getId());
            break;
        }//随机加入一个
        count = count + 1;
        //boolean signed=false;
        while (true) {
            while (queue.size() > 0) {
                MyPerson p1 = (MyPerson) queue.remove(0);
                for (int i = 0; i < p1.getAcquaintance().size(); i++) {
                    Person neighbour = p1.getAcquaintance().get(i);
                    boolean flag = visited.contains(neighbour.getId());
                    if (flag) {
                        queue.add(neighbour);
                        visited.remove(neighbour.getId());
                    }
                }
                //signed=true;
            }
            //System.out.println(signed);
            if(visited.size()==0)break;
            else if(visited.size()>0){
                for (Integer i : visited) {
                    queue.add(getPerson(i));
                    visited.remove(i);
                    break;
                }
            }
            //System.out.println("visited.size is: "+visited.size());
            //System.out.println("queue.size is: "+queue.size());
            count++;
            //signed=false;
        }
        return count;
    }
    @Override
    public void addGroup(Group group) throws EqualGroupIdException {
        if(groups.containsKey(group.getId())){
            throw new MyEqualGroupIdException(group.getId());
        }
        else {
            groups.put(group.getId(),group);
        }
    }

    @Override
    public Group getGroup(int id) {
        if(groups.containsKey(id)){
            return groups.get(id);
        }
        return null;
    }

    @Override
    public void addToGroup(int id1, int id2) throws GroupIdNotFoundException, PersonIdNotFoundException, EqualPersonIdException {
        if(!groups.containsKey(id2)){
            throw new MyGroupIdNotFoundException(id2);
        }
        else if(groups.containsKey(id2) && !people.containsKey(id1)){
            throw new MyPersonIdNotFoundException(id1);
        }
        else if(groups.containsKey(id2) && people.containsKey(id1) && getGroup(id2).hasPerson(getPerson(id1))){
            throw new MyEqualPersonIdException(id1);
        }
        else if(getGroup(id2).getSize() >= 1111){
            return;
        }
        Person p = people.get(id1);
        Group g = groups.get(id2);
        g.addPerson(p);
    }

    @Override
    public int queryGroupSum() {
        return groups.size();
    }

    @Override
    public int queryGroupPeopleSum(int id) throws GroupIdNotFoundException {
        if(!groups.containsKey(id)){
            throw new MyGroupIdNotFoundException(id);
        }
        return getGroup(id).getSize();
    }

    @Override
    public int queryGroupValueSum(int id) throws GroupIdNotFoundException {
        if(!groups.containsKey(id)){
            throw new MyGroupIdNotFoundException(id);
        }
        return getGroup(id).getValueSum();
    }

    @Override
    public int queryGroupAgeMean(int id) throws GroupIdNotFoundException {
        if(!groups.containsKey(id)){
            throw new MyGroupIdNotFoundException(id);
        }
        return getGroup(id).getAgeMean();
    }

    @Override
    public int queryGroupAgeVar(int id) throws GroupIdNotFoundException {
        if(!groups.containsKey(id)){
            throw new MyGroupIdNotFoundException(id);
        }
        return getGroup(id).getAgeVar();
    }

    @Override
    public void delFromGroup(int id1, int id2) throws GroupIdNotFoundException, PersonIdNotFoundException, EqualPersonIdException {
        if(!groups.containsKey(id2)){
            throw new MyGroupIdNotFoundException(id2);
        }
        else if(groups.containsKey(id2) && !people.containsKey(id1)){
            throw new MyPersonIdNotFoundException(id1);
        }
        else if(groups.containsKey(id2) && people.containsKey(id1) && !getGroup(id2).hasPerson(getPerson(id1))){
            throw new MyEqualPersonIdException(id1);
        }
        getGroup(id2).delPerson(people.get(id1)); //people.get(id1) get person
    }

    @Override
    public boolean containsMessage(int id) {
        return messages.containsKey(id);
    }

    @Override
    public void addMessage(Message message) throws EqualMessageIdException, EmojiIdNotFoundException, EqualPersonIdException {
        if(messages.containsKey(message.getId())){
            throw new MyEqualMessageIdException(message.getId());
        }
        else if(message instanceof EmojiMessage && !containsEmojiId(((EmojiMessage) message).getEmojiId())){
            throw new MyEmojiIdNotFoundException(((EmojiMessage) message).getEmojiId());
        }
        else if(message.getType() == 0 && message.getPerson1() == message.getPerson2()){
            throw new MyEqualPersonIdException(message.getPerson1().getId());
        }
        messages.put(message.getId(),message);
    }

    @Override
    public Message getMessage(int id) {
        if(messages.containsKey(id)){
            return messages.get(id);
        }
        return null;
    }

    @Override
    public void sendMessage(int id) throws RelationNotFoundException, MessageIdNotFoundException, PersonIdNotFoundException {
        if(!containsMessage(id)){
            throw new MyMessageIdNotFoundException(id);
        }
        else if(getMessage(id).getType() == 0 && !(getMessage(id).getPerson1().isLinked(getMessage(id).getPerson2()))){
            throw new MyRelationNotFoundException(getMessage(id).getPerson1().getId(),getMessage(id).getPerson2().getId());
        }
        else if(getMessage(id).getType() == 1 && !(getMessage(id).getGroup().hasPerson(getMessage(id).getPerson1()))){
            throw new MyPersonIdNotFoundException(getMessage(id).getPerson1().getId());
        }
        if(containsMessage(id) && getMessage(id).getType() == 0 && getMessage(id).getPerson1() != getMessage(id).getPerson2()
           && getMessage(id).getPerson1().isLinked(getMessage(id).getPerson2())){
            Message old_message = getMessage(id);
            messages.remove(id);
            old_message.getPerson1().addSocialValue(old_message.getSocialValue());
            old_message.getPerson2().addSocialValue(old_message.getSocialValue());
            if(old_message instanceof RedEnvelopeMessage){
                old_message.getPerson1().addMoney(-1*(((RedEnvelopeMessage) old_message).getMoney()));
                old_message.getPerson2().addMoney(((RedEnvelopeMessage) old_message).getMoney());
            }
            if(old_message instanceof EmojiMessage){
                if(emojiIdList.contains(((EmojiMessage)(old_message)).getEmojiId())){//emojidlist如果包含
                    int index=emojiIdList.indexOf(((EmojiMessage)(old_message)).getEmojiId());
                    emojiHeatList.set(index,emojiHeatList.get(index)+1);
                }
                else{//不包含
                    emojiIdList.add(((EmojiMessage)(old_message)).getEmojiId());
                    emojiHeatList.add(0);
                }
            }
            old_message.getPerson2().getMessages().add(0,old_message);
        }
        else if(containsMessage(id)&&getMessage(id).getType() == 1 && getMessage(id).getGroup().hasPerson(getMessage(id).getPerson1())){
            Message old_message = getMessage(id);
            int old_size = old_message.getGroup().getSize();
            messages.remove(id);//删除了需要保存一下
            int value_i=0;
            if(old_message instanceof RedEnvelopeMessage){
                value_i = ((RedEnvelopeMessage) (old_message)).getMoney()/old_size;
                int num=value_i*(old_size);
                old_message.getPerson1().addMoney(-1*num);
            }

            for(Person p:people.values()){
                if(old_message.getGroup().hasPerson(p)){
                    p.addSocialValue(old_message.getSocialValue());
                    if(old_message instanceof RedEnvelopeMessage){
                        p.addMoney(value_i);//需要计算 i 的值
                    }
                }
            }

            if(old_message instanceof EmojiMessage){
                if(emojiIdList.contains(((EmojiMessage)(old_message)).getEmojiId())){//emojidlist如果包含
                    int index=emojiIdList.indexOf(((EmojiMessage)(old_message)).getEmojiId());
                    emojiHeatList.set(index,emojiHeatList.get(index)+1);
                }
                else{//不包含
                    emojiIdList.add(((EmojiMessage)(old_message)).getEmojiId());
                    emojiHeatList.add(0);
                }
            }
        }
    }

    @Override
    public int querySocialValue(int id) throws PersonIdNotFoundException {
        if(!contains(id)){
            throw new MyPersonIdNotFoundException(id);
        }
        return getPerson(id).getSocialValue();
    }

    @Override
    public List<Message> queryReceivedMessages(int id) throws PersonIdNotFoundException {
        if(!contains(id)){
            throw new MyPersonIdNotFoundException(id);
        }
        return  getPerson(id).getReceivedMessages();
    }

    @Override
    public boolean containsEmojiId(int id) {
        return emojiIdList.contains(id);
    }

    @Override
    public void storeEmojiId(int id) throws EqualEmojiIdException {
        if(emojiIdList.contains(id)){
            throw new MyEqualEmojiIdException(id);
        }
        else{
            emojiIdList.add(id);
            emojiHeatList.add(0);
        }
    }

    @Override
    public int queryMoney(int id) throws PersonIdNotFoundException {
        if(!contains(id)){
            throw new MyPersonIdNotFoundException(id);
        }
        return getPerson(id).getMoney();
    }

    @Override
    public int queryPopularity(int id) throws EmojiIdNotFoundException {
        if(!emojiIdList.contains(id)){
            throw new MyEmojiIdNotFoundException(id);
        }
        int index=emojiIdList.indexOf(id);
        return emojiHeatList.get(index);
    }

    @Override
    public int deleteColdEmoji(int limit) {
        for(int i=0;i<emojiHeatList.size();i++){
            if(emojiHeatList.get(i) < limit){
                emojiHeatList.remove(i);
                emojiIdList.remove(i);
                i--;//remove会删除i标定元素，i+1会往前移
            }
        }
        Iterator<Map.Entry<Integer,Message>> Iter = messages.entrySet().iterator();
        while(Iter.hasNext()){//只能用迭代器删除
            //System.out.println("messages.size is: "+messages.size());
            Map.Entry<Integer,Message> item = Iter.next();
            Message old_messages = item.getValue();
            Integer key = item.getKey();
            if(old_messages instanceof EmojiMessage){
                //System.out.println("key-445 is: "+key);
                if(!containsEmojiId(((EmojiMessage)old_messages).getEmojiId())){
                    Iter.remove();//非线性表不用做变换
                }
            }
        }
        return emojiIdList.size();
    }
    public int min_path(int id1,int id2){//hashmap->arraylist
        final int INF = Integer.MAX_VALUE / 2;
        int matrix_length = people.size();
        ArrayList<Integer>graph = new ArrayList<>();
        for(Integer i:people.keySet()){
            graph.add(i);//将people.id加入到graph
        }
        //最短路径长度
        int[] shortest = new int[matrix_length];
        //判断该点的最短路径是否求出
        int[] visited = new int[matrix_length];
        //初始化
        Arrays.fill(shortest, INF);
        Arrays.fill(visited,0);//0:not visited 1:visited
        int index1 = graph.indexOf(id1);
        int index2 = graph.indexOf(id2);
        shortest[index1]=0;//自己到自己距离为0
        for(int i=0;i<matrix_length;i++){
            int min_dist=-1;//最小距离
            for(int y=0;y<matrix_length;y++){
                if(visited[y]==0 && (min_dist == -1 || shortest[y] < shortest[min_dist])){
                    min_dist=y;
                }
            }
            visited[min_dist]=1;
            int sign_graph = graph.get(min_dist);//序列->person_id
            MyPerson person_neigh = (MyPerson)people.get(sign_graph);//person_id->person
            for (int j = 0; j < person_neigh.getAcquaintance().size(); j++) {
                int ind = graph.indexOf(person_neigh.getAcquaintance().get(j).getId());
                if(visited[ind]==0 && shortest[ind] > shortest[min_dist]+ person_neigh.getValue().get(j)) {
                    shortest[ind] = shortest[min_dist] + person_neigh.getValue().get(j);
                }
            }
        }
        return shortest[index2];
    }
    @Override
    public int sendIndirectMessage(int id) throws MessageIdNotFoundException {//需要迪杰斯特拉算法
        int res=-1;
        if(!containsMessage(id)){
            throw new MyMessageIdNotFoundException(id);
        }
        else if(containsMessage(id) && getMessage(id).getType() == 1){
            throw new MyMessageIdNotFoundException(id);
        }
        boolean hasCircle = bfs_find(getMessage(id).getPerson1().getId(),getMessage(id).getPerson2().getId());
        if (containsMessage(id) && getMessage(id).getType() == 0 && !hasCircle) {
            return -1;
        }
        if(containsMessage(id) && getMessage(id).getType() == 0 && hasCircle){
            Message old_message = messages.get(id);
            messages.remove(id);//需要保存old_message
            Person p1 = old_message.getPerson1();
            Person p2 = old_message.getPerson2();
            int id1 = p1.getId();
            int id2 = p2.getId();
            res=min_path(id1,id2);
            p1.addSocialValue(old_message.getSocialValue());
            p2.addSocialValue(old_message.getSocialValue());
            if(old_message instanceof RedEnvelopeMessage){
                int num_money = ((RedEnvelopeMessage)(old_message)).getMoney();
                p1.addMoney(-1*num_money);
                p2.addMoney(num_money);
            }
            else if(old_message instanceof EmojiMessage){
                if(emojiIdList.contains(((EmojiMessage)(old_message)).getEmojiId())){//emojidlist如果包含
                    int index=emojiIdList.indexOf(((EmojiMessage)(old_message)).getEmojiId());
                    emojiHeatList.set(index,emojiHeatList.get(index)+1);
                }
                else{//不包含
                    emojiIdList.add(((EmojiMessage)(old_message)).getEmojiId());
                    emojiHeatList.add(0);
                }
            }
            old_message.getPerson2().getMessages().add(0,old_message);
        }
        return res;
    }


}
