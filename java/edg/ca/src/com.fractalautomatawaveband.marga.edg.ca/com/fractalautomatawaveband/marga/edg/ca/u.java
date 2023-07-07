package com.fractalautomatawaveband.marga.edg.ca;

public class u
{
  public static boolean cin(char c,String set)
  {
    for(int i=0;i<set.length();i++)
    {
      if(set.charAt(i)==c)
      {
        return true;
      }
    }
    return false;
  }
  
  public static boolean sin(String s,String[] arr)
  {
    for(int i=0;i<arr.length;i++)
    {
      if(s.equals(arr[i]))
      {
        return true;
      }
    }
    return false;
  }
  
  public static void d(String msg)
  {
    System.out.println("[DEBUG] "+msg);
  }
}

