
package parid;

import java.io.*;
//import java.net.*;
import java.util.StringTokenizer;
import java.util.*;

import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.ling.CoreAnnotations.*;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.TreeMap;
import edu.sussex.nlp.jws.*;

/**
 *
 * @author SSN
 */
public class canconcept {

    
 
    
    public void uniqueconcepts()
    {
        try{
      int i,count=0,l,m,s=0,r;
        String str,str2="";
        String str1[]=new String[1000];

        String freq1[]=new String[1000];

        FileReader fr=new FileReader("result/temp/candidateconcepts.txt");
                BufferedReader br=new BufferedReader(fr);

        FileWriter fw=new FileWriter("result/temp/conceptset.txt");
             BufferedWriter bw=new BufferedWriter(fw);
   
                str=br.readLine();
                i=0;
                while((str)!=null)
                {
                str1[i]= str;
                i++;
                str=br.readLine();
                }
            
         //       for(int g=0;g<i;g++)
           //         System.out.println(str1[g]);
                l=0;
                m=0;
                r=0;
                int flag;
                freq1[0]=str1[0];
                count=1;
                int x=1;
                while(x<i)
                {
                    int y=0;
                    flag=0;
                    while(y<i)
                    {
        
                        if(str1[x].equalsIgnoreCase(freq1[y]))
                        {
                            count++;
                            flag++;
                            y++;
                        }
                        else
                            y++;
                    }
                    if(flag==0)
                    {
                        freq1[++l]=str1[x];
        
                    }
                    x++;
        
                }       
        
                for(int g=0;g<=l;g++)
                {
                  //    System.out.println(g+" "+freq1[g]);
                      bw.write(freq1[g]+"\n");
                }
                             
                    bw.close();
            br.close();


      }

      catch(Exception e){System.out.println(e);}
        
    }
    
        public void uniquetriples()
    {
        try{
      int i,count=0,l,m,s=0,r;
        String str,str2="";
        String str1[]=new String[1000];

        String freq1[]=new String[1000];

        FileReader fr=new FileReader("result/temp/propositiontemp.txt");
                BufferedReader br=new BufferedReader(fr);

        FileWriter fw=new FileWriter("result/temp/proposition.txt");
             BufferedWriter bw=new BufferedWriter(fw);
   
                str=br.readLine();
                i=0;
                while((str)!=null)
                {
                str1[i]= str;
                i++;
                str=br.readLine();
                }
            
          //      for(int g=0;g<i;g++)
            //        System.out.println(str1[g]);
                l=0;
                m=0;
                r=0;
                int flag;
                freq1[0]=str1[0];
                count=1;
                int x=1;
                while(x<i)
                {
                    int y=0;
                    flag=0;
                    while(y<i)
                    {
        
                        if(str1[x].equalsIgnoreCase(freq1[y]))
                        {
                            count++;
                            flag++;
                            y++;
                        }
                        else
                            y++;
                    }
                    if(flag==0)
                    {
                        freq1[++l]=str1[x];
        
                    }
                    x++;
        
                }       
        
                for(int g=0;g<=l;g++)
                {
                  //    System.out.println(g+" "+freq1[g]);
                      bw.write(freq1[g]+"\n");
                }
                             
                    bw.close();
            br.close();


      }

      catch(Exception e){System.out.println(e);}
        
    }
    
}
