# [JAVA/프로그래머스] 디스크 컨트롤러

## 문제

[문제링크](https://programmers.co.kr/learn/courses/30/lessons/42627#)

###### 문제 설명

하드디스크는 한 번에 하나의 작업만 수행할 수 있습니다. 디스크 컨트롤러를 구현하는 방법은 여러 가지가 있습니다. 가장 일반적인 방법은 요청이 들어온 순서대로 처리하는 것입니다.

예를들어

```
- 0ms 시점에 3ms가 소요되는 A작업 요청
- 1ms 시점에 9ms가 소요되는 B작업 요청
- 2ms 시점에 6ms가 소요되는 C작업 요청
```

와 같은 요청이 들어왔습니다. 이를 그림으로 표현하면 아래와 같습니다.
![Screen Shot 2018-09-13 at 6.34.58 PM.png](https://tva1.sinaimg.cn/large/0081Kckwgy1glb12euedsj312i0aaq3i.jpg)

한 번에 하나의 요청만을 수행할 수 있기 때문에 각각의 작업을 요청받은 순서대로 처리하면 다음과 같이 처리 됩니다.
![Screen Shot 2018-09-13 at 6.38.52 PM.png](https://tva1.sinaimg.cn/large/0081Kckwgy1glb12fgdv8j30yw0c675f.jpg)

```
- A: 3ms 시점에 작업 완료 (요청에서 종료까지 : 3ms)
- B: 1ms부터 대기하다가, 3ms 시점에 작업을 시작해서 12ms 시점에 작업 완료(요청에서 종료까지 : 11ms)
- C: 2ms부터 대기하다가, 12ms 시점에 작업을 시작해서 18ms 시점에 작업 완료(요청에서 종료까지 : 16ms)
```

이 때 각 작업의 요청부터 종료까지 걸린 시간의 평균은 10ms(= (3 + 11 + 16) / 3)가 됩니다.

하지만 A → C → B 순서대로 처리하면
![Screen Shot 2018-09-13 at 6.41.42 PM.png](https://tva1.sinaimg.cn/large/0081Kckwgy1glb12gd1poj311k0cuabb.jpg)

```
- A: 3ms 시점에 작업 완료(요청에서 종료까지 : 3ms)
- C: 2ms부터 대기하다가, 3ms 시점에 작업을 시작해서 9ms 시점에 작업 완료(요청에서 종료까지 : 7ms)
- B: 1ms부터 대기하다가, 9ms 시점에 작업을 시작해서 18ms 시점에 작업 완료(요청에서 종료까지 : 17ms)
```

이렇게 A → C → B의 순서로 처리하면 각 작업의 요청부터 종료까지 걸린 시간의 평균은 9ms(= (3 + 7 + 17) / 3)가 됩니다.

각 작업에 대해 [작업이 요청되는 시점, 작업의 소요시간]을 담은 2차원 배열 jobs가 매개변수로 주어질 때, 작업의 요청부터 종료까지 걸린 시간의 평균을 가장 줄이는 방법으로 처리하면 평균이 얼마가 되는지 return 하도록 solution 함수를 작성해주세요. (단, 소수점 이하의 수는 버립니다)

##### 제한 사항

- jobs의 길이는 1 이상 500 이하입니다.
- jobs의 각 행은 하나의 작업에 대한 [작업이 요청되는 시점, 작업의 소요시간] 입니다.
- 각 작업에 대해 작업이 요청되는 시간은 0 이상 1,000 이하입니다.
- 각 작업에 대해 작업의 소요시간은 1 이상 1,000 이하입니다.
- 하드디스크가 작업을 수행하고 있지 않을 때에는 먼저 요청이 들어온 작업부터 처리합니다.

##### 입출력 예

| jobs                     | return |
| ------------------------ | ------ |
| [[0, 3], [1, 9], [2, 6]] | 9      |

##### 입출력 예 설명

문제에 주어진 예와 같습니다.

- 0ms 시점에 3ms 걸리는 작업 요청이 들어옵니다.
- 1ms 시점에 9ms 걸리는 작업 요청이 들어옵니다.
- 2ms 시점에 6ms 걸리는 작업 요청이 들어옵니다.



## 문제접근

### 아이디어

- 비선점 스케줄링 알고리즘 SJF(Shortest Job First)을 이용해서 푸는 문제이다. 
- 대기시간을 모르기 때문에, 작업의 소요시간이 가장 짧은 것을 먼저 처리한다. 
- 하드디스크가 작업을 수행하고 있지 않을 때에는 먼저 요청이 들어온 작업부터 처리해야 하기 때문에, 대기큐에 도착한 작업들 중 작업이 요청되는 시점이 빠른 것을 먼저 실행한다
- 하드 디스크는 한번에 하나의 작업만 가능하기 때문에, 해당 작업이 끝나고 대기큐에 도착한 작업들 중  소요시간이 가장 작은 작업 선택해 실행한다  



## 소스



```java
import java.util.*;

class Solution {
class Job implements Comparable<Job> {
        int start;
        int time;
        
        public Job(int start, int time){
            this.start = start;
            this.time = time;
        }
        
        @Override
        public int compareTo(Job job) {
        	return this.time - job.time;
        }    
    }
    
    public int solution(int[][] jobs) {
        int answer = 0;
        PriorityQueue<Job> pq = new PriorityQueue<>();
        Queue<Job> que = new LinkedList<>();

        Arrays.sort(jobs, new Comparator<int[]>() {
        	@Override
        	public int compare(int[] o1, int[] o2) {
        		if(o1[0]==o2[0])	return Integer.compare(o1[1], o2[1]);
        		return Integer.compare(o1[0],o2[0]);
        	}
        });
            
        for(int i=0; i<jobs.length; i++){
           int start = jobs[i][0];
           int time = jobs[i][1];
           Job j = new Job(start, time);
            que.add(j);
        }
        
        
        int time = 0;
        while(!(que.isEmpty()&&pq.isEmpty())){
        	while(!que.isEmpty()&&que.peek().start <= time ) {
            	Job nowJob = que.poll();
            	pq.add(nowJob);
            }
            
           if(!pq.isEmpty()) {
               while(!pq.isEmpty()) {
        		   Job job = pq.poll();
        		   answer+=(time-job.start)+job.time;
        		   time+=job.time;
        	        while(!que.isEmpty()&&que.peek().start <= time ) {
               	        Job nextJob = que.poll();
               	        pq.add(nextJob);
                    }         
               }
 
           }else {
        	   time++;
           }
        }
        
        return answer/jobs.length;
    }
    
}
```



### 틀린 소스

```java
class Solution {
    public int solution(int[][] jobs) {
        int answer = 0;
        int[] times = new int[2001];
        int nowTime = 0;
        int total = 0;
        
        for(int i=0; i<jobs.length; i++){
           int start = jobs[i][0];
           int time = jobs[i][1];
           times[start+time] = time; 
        }
        
        for(int i=0; i<times.length; i++){
            int waitingTime = 0;
            if(times[i]>0){
                int start = i-times[i];
                if(nowTime>start){
                    waitingTime = nowTime-start;
                    nowTime+=times[i];
                }   
                else nowTime+=times[i]+(start-nowTime);
                total+=(waitingTime+times[i]);
                
                
            }//if end 
        }
        
        answer = total/jobs.length;
        
        return answer;
    }
}
```

