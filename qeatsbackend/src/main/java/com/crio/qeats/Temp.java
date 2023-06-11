package com.crio.qeats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Temp {

  static Scanner sc=new Scanner(System.in);

  static void findMostTweets(){
    int numTweets=sc.nextInt();
    
    Map<String,Integer> tweetCounts= new HashMap<>();

    int result=Math.min(1,numTweets);
    for(int i=0;i<numTweets;++i){
      String tweet=sc.nextLine();
      String[] splitTweets=tweet.split(" ");
      String user=splitTweets[0];
      if(tweetCounts.containsKey(user)){
        int count=tweetCounts.get(user);
        count+=1;
        tweetCounts.put(user, count);
        result=Math.max(result, count);
      }
      else{
        tweetCounts.put(user,1 );
      }
    }
    List<String> mostTwetedUsers= new ArrayList<>();
    for(Map.Entry<String,Integer> entry:tweetCounts.entrySet() ){
      if(entry.getValue()==result){
        mostTwetedUsers.add(entry.getKey());
      }
    }
    Collections.sort(mostTwetedUsers);
    for(String user:mostTwetedUsers){
      System.out.println(user+" "+result);
    }


  }
  public static void main(String args[]){
   
    int numTestCases=sc.nextInt();
    while(numTestCases!=0){
      findMostTweets();
      numTestCases--;
    }
    
  }
}