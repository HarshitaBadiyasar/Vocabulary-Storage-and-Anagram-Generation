import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
public class Anagram 
{
     public static Hashfnx abc=new Hashfnx();
     public static Permutation xyz=new Permutation();
     public static void main (String[] args) throws  FileNotFoundException, InvalidinputException, NoSpaceFoundException 
     {
          //Anagram obj=new Anagram();
          File file = new File("vocabulary.txt");
          
               Scanner sc = new Scanner(file);
               int p=sc.nextInt();
               int noofstrings=0;
               while (noofstrings<p)  /*sc.hasNextLine()*/
               {
                    String s= sc.nextLine();
                    /*int temp=0;
                    for(temp=0;temp<s.length();temp++)
                    {
                         if((s.charAt(temp)>='0' && s.charAt(temp)<='9') || (s.charAt(temp)>='a' && s.charAt(temp)<='z') || ( s.charAt(temp)=='`') )
                              continue;
                         else
                              break;
                    }
                    if(temp==s.length() && s.length()>2 && s.length()<13)
                    {*/
                         int index=abc.stringtoint(s);
                         abc.storetoarray(index,s);
                         //int i = s.nextInt();
                         noofstrings++;
                    /*}
                    else
                    {
                         throw new InvalidinputException("Input is wrong");//throw exception
                    }*/
               }
               sc.close();
          


          File file1 = new File("input.txt");
          
               Scanner scp = new Scanner(file1);
               int pa=scp.nextInt();
               //int i = sc.nextInt();
               int noofstrings1=0;
               while (noofstrings1<pa) 
               {
                    String s= scp.nextLine();
                    int temp=0;
                    for(temp=0;temp<s.length();temp++)
                    {
                         if((s.charAt(temp)>='0' && s.charAt(temp)<='9') || (s.charAt(temp)>='a' && s.charAt(temp)<='z') || ( s.charAt(temp)=='`') )
                              continue;
                         else
                              break;
                    }
                    if(temp==s.length() && s.length()>2 && s.length()<13)
                    {
                         xyz.permute(s+"  ","");
                         String[] arr=xyz.values;
                         // make it in order lexiographic
                         for(int temp1=0;temp1<arr.length;temp1++)
                         {
                              System.out.println(arr[temp1]);
                         }
                         System.out.println("-1");
                         
                         noofstrings1++;
                    }
                    /*else
                    {
                         throw new InvalidinputException("Input wrong");//throw exception
                    }*/
               }
               scp.close();
          
     }
}

class Hashfnx
{
     int p=47;
     int m=35023;//(25000/0.75~33333~35000)
     String Hasharray[]=new String[m];
     public int stringtoint(String sc)
     {
          int index=0;
          final char[] s = sc.toCharArray();
          long pi=1;
          int l=s.length;
          for(int temp=0;temp<l;temp++)
          {
               if(s[temp]>='0' && s[temp]<='9')
               {
                    index=(int)((index+(s[temp]-'0'+5)*pi)%m);
                    pi=(pi*p)%m;
               }
               else
               {
                    index=(int)((index+(s[temp]-'`'+5)*pi)%m);
                    pi=(pi*p)%m;
               }
          }
          return index;
     }
     public void storetoarray(int index,String sc) throws NoSpaceFoundException
     {
          int noofprobe=0;
          int probe=(m+1)/2;
          while(noofprobe<probe)
          {
               if(Hasharray[index]==null)
               {
                    Hasharray[index]=sc;
                    break;
               }
               noofprobe++;
               index=(index+(noofprobe*noofprobe))%m;
          }
          if(noofprobe==probe)
               throw new NoSpaceFoundException("no space found there");

     }
     public boolean findthestring(int index,String sc)
     {
          int noofprobe=0;
          int probe=(m+1)/2;
          while(noofprobe<probe)
          {
               if(Hasharray[index]==null)
                    return false;
               else if(Hasharray[index]==sc)
                    return true;
               noofprobe++;
               index=(index+(noofprobe*noofprobe))%m;
          }
          if(noofprobe==probe)
               return false;
          else
               return true;
     }
}



class Permutation
{
     Hashfnx abc=new Hashfnx();
     public String[] values=new String[1];
     int valuekaindex=0;
     int valuekasize=1;
     //values=null;
     public void permute(String str, String ans)//will permute,check, if valid then store, and again next permute
     {
          if (str.length() == 0) 
          {
               ans=correctthestring(ans);//about spaces
               checkthestring(ans);//about lenght and available in vocab
               return ;
          }
          for(int i = 0; i < str.length(); i++) 
          {
               char c = str.charAt(i);
               String substr = str.substring(0, i) + str.substring(i + 1);
               permute(substr, ans + c);
          }
     }

     public void checkthestring(String s)
     {
          String[] a=new String[3];
          int start=0;
          int index=0;
          for(int temp=0;temp<s.length();temp++)
          {
               if(s.charAt(temp)==' ')
               {
                    a[index]=s.substring(start,temp);
                    start=temp+1;
                    index++;
               }
               if(temp==s.length()-1){
                    a[index]=s.substring(start,temp);
                    index++;
               }
          }
          for(int temp=0;temp<index;temp++)
          {
               if(a[temp].length()<3)
                    return;
          }
          boolean ba=true;
          for(int temp=0;temp<index;temp++)
          {
               int indexofsubstr=abc.stringtoint(a[temp]);
               ba=abc.findthestring(indexofsubstr,a[temp]);
               if(ba==false)
                    break;
          }
          if(ba==true)
          {
               int temp=0;
               for(temp=0;temp<valuekaindex;temp++)
               {
                    if(values[temp]==s)   
                         break;
               }
               if(temp==valuekaindex)
               {
                    if(valuekaindex==valuekasize)
                    {
                         String[] array = new String[2*valuekasize];
                         for(int i=0;i<valuekaindex;i++){
                              array[i]=values[i];
                         }
                         values=array;
                         valuekasize=2*valuekasize;
                    }
                    values[valuekaindex]=s;
                    valuekaindex++;
               }
               
          }
     }


     public String correctthestring(String s)
     {
          if(s.charAt(0)==' ')
          {
               if(s.charAt(1)==' ')
                    s=s.substring(2,s.length());
               else if(s.charAt(s.length()-1)==' ')
                    s=s.substring(1,s.length()-1);
               else
                    s=s.substring(1,s.length());
          }
          if(s.charAt(s.length()-1)==' ')
          {
               if(s.charAt(s.length()-2)==' ')
                    s=s.substring(0,s.length()-2);
               else
                    s=s.substring(0,s.length()-1);
          }
          for(int temp=0;temp<s.length();temp++)
          {
               if(s.charAt(temp)==' ')
               {
                    if(s.charAt(temp+1)==' ')
                    {
                         s=s.substring(0,temp)+s.substring(temp+2,s.length());
                    }
                    break;
               }
          }
          return s;
     } 
}



class InvalidinputException extends Exception
{
     InvalidinputException(String s)
     {
          super(s);
     }
}


class NoSpaceFoundException extends Exception
{
     NoSpaceFoundException(String s)
     {
          super(s);
     }
}