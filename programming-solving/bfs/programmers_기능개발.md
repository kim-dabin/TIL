# [프로그래머스/자바] 기능개발 

[문제링크](https://programmers.co.kr/learn/courses/30/lessons/42586)



## 접근방식 

1. Node 객체를 만듦 
   1. dDay - 개발기간 
   2. count - 현재 progresses,speeds 배열을 가리키는 index
2. Queue를 생성하고 1번의 Node를 타입으로 함 
3. getRestDays 메서드를 만들어서 개발기간 계산 



## 소스

```java
import java.util.*;
class Solution {
 static class Node{
        int dDay;
        int count; 
        public Node(int dDay, int count){
            this.dDay = dDay;
            this.count = count;
        }
    }
    static public int getRestDays(int prog, int speed){
        int days = (100-prog);
        int restDays = (days%speed)==0? (days/speed): (days/speed)+1;
        
        return restDays;
    }
    
    public int[] solution(int[] progresses, int[] speeds) {
        
        List<Integer> list = new ArrayList<>(); 
        Queue<Node> q = new LinkedList<>();
        int restDays = getRestDays(progresses[0],speeds[0]);
        q.add(new Node(restDays,1));
        int cnt = 1;
        int idxCnt = 0;
        list.add(0,1);
        while(!q.isEmpty()){
            Node now = q.poll();
            int prog = now.dDay;
            int idx = now.count;
            if(idx >= progresses.length)    continue;
            restDays = getRestDays(progresses[idx],speeds[idx]);
            
            if(restDays<=prog) {
            	cnt++;
            	q.add(new Node(prog,idx+1));
            	
            }
            else{
            	idxCnt++;
            	cnt=1;
                q.add(new Node(restDays,idx+1));
            }

            if(list.size()<=idxCnt) {
            	list.add(cnt);
            }else {
            	list.set(idxCnt, cnt);
            }
        }//while end 
        
        int[] answer = new int[list.size()];
        for(int i=0; i<list.size(); i++){
            answer[i] = list.get(i);
        }
       
        return answer;
    }
}
```



