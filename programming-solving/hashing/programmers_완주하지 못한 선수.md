```java
import java.util.*;
class Solution {
    public String solution(String[] participant, String[] completion) {
        String answer = "";
        HashMap<String, Integer> map = new HashMap<>();
        
        for(String p : participant){
            map.put(p, map.getOrDefault(p,0)+1);
        }
        
        for(String c : completion){
            map.computeIfPresent(c, (String k, Integer v)-> --v);
        }
        
        for(Object o : map.keySet()){
            if(map.get(o)>0){
                return o.toString();
            }
        }     
        return answer;
    }
}
```

