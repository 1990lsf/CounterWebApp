package com.org.testUtil;



import java.util.Iterator;
import java.util.TreeSet;

public class numberRandom {
    String[] stra = {"1","2","2","3","4","5"};
    int n = stra.length;
    boolean[] visited = new boolean[n];
    String result = "";
    TreeSet<String> ts = new TreeSet<String>();
    int[][] a = new int[n][n];
    private  void searchMap()
    {
        
        for(int i=0;i<n;i++)
        {
           for(int j=0;j<n;j++)
           {
               if(i==j)
               {
                   //图中对角线部分是无法访问的
                   a[i][j]=0;
                   }else{
                       a[i][j]=1;
                   }
           }            
        }
        //3和5不能相连
        a[3][5]=0;
        a[5][3]=0;
        //开始遍历
        for(int i=0;i<n;i++)
        {
            search(i);
        }
        Iterator<String> it = ts.iterator();
        while(it.hasNext())
        {
            String str =it.next();
            //4不能在第三位
            if(str.indexOf("4")!=2){
                System.out.println(str);
            }
        }
      }
    //这是个深度优先的遍历
    private void search(int startIndex){
        visited[startIndex] = true; 
        result = result + stra[startIndex];
        if(result.length() ==n)
        {
            ts.add(result);
        }
        for(int j=0;j<n;j++)
        {
            if(a[startIndex][j]==1&&visited[j]==false)
            {
                search(j);
            }else
            {
                continue;
            }
        }
        //一个result结束后踢掉最后一个，寻找别的可能性，若没有的话，则继续向前踢掉当前最后一个
        result = result.substring(0,result.length()-1);
        visited[startIndex] = false;
        
    }
    
    
    public static void main(String[] args){
        new numberRandom().searchMap();
        
    }

}