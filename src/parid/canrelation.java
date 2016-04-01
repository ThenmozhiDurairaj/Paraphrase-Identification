/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parid;

/**
 *
 * @author Thenmozhi
 */
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

public class canrelation {
    
    // public static void main(String[] args) throws FileNotFoundException, IOException{
         public void rel() throws FileNotFoundException, IOException{
        String subj="",verb="",obj="",line="",str1="";
        String subject,object;
        String str[]=new String[1000];
        String sbar[]=new String[1000];
        String vbar[]=new String[1000];
        String obar[]=new String[1000];
    
        try
        {
          
            String s1="result/temp/tripsimcm.txt";
            String s2="result/temp/cantrip.txt";
            FileReader fr=new FileReader(s1);
            BufferedReader br=new BufferedReader(fr);
            FileWriter fw=new FileWriter(s2);
            BufferedWriter bw=new BufferedWriter(fw);
            int i=0, k=0;
            while((line=br.readLine())!=null)
            {
            subj="";verb="";obj="";
            
                StringTokenizer st=new StringTokenizer(line," ");
                subj=st.nextToken()+" "+st.nextToken();
                    
                    str1=st.nextToken();
                  
                    while(st.hasMoreTokens())
                    {
                        if((str1.endsWith("np")))
                             obj=str1+" ";
                        else
                            verb=verb+str1+" ";
                        
                        str1=st.nextToken();
                    }
                    obj=obj+str1;
            
          
        
                       subject=subj.replace("np ", "");
                       object=obj.replace("np ", "");
             
                       if(!(subject.equalsIgnoreCase("it"))&&!(object.equalsIgnoreCase("it"))&&!(subject.equalsIgnoreCase(object))&&!(object.equalsIgnoreCase("they"))&&!(subject.equalsIgnoreCase("?"))&&!(object.equalsIgnoreCase("?"))&&!(subject.equalsIgnoreCase("he")))
                       {
                            bw.write(subj+" "+verb+" "+obj+"\n");
                            sbar[k]=subj;
                            vbar[k]=verb;
                            vbar[k]=vbar[k].replace("pp ", "vp ");
                            obar[k]=obj;
                            str[i]=subj+" "+obj;
                      //       System.out.println(str[i]);
                            i++;
                          k++;
                       }
                                 
                       
            }
            
            for(int j=0;j<i;j++)
            {
                str[j]=str[j].replace("np ", "");
            //    System.out.println(str[j]);
            }
            
            conceptpair(str,i);
            relext(sbar,vbar,obar,k);
            //relationlabel();
            
            br.close();
            bw.close();
            
        } //try
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
         
   public static void conceptpair(String str[],int i) throws FileNotFoundException, IOException
   {
       String freq1[]=new String[1000];
       
       String s2="result/temp/conceptpair.txt";
            FileWriter fw=new FileWriter(s2);
            BufferedWriter bw=new BufferedWriter(fw);
            
       int x=1, l=0, flag;
       freq1[0]=str[0];
                while(x<i)
                {
                    int y=0;
                    flag=0;
                    while(y<i)
                    {
                        if(str[x].equalsIgnoreCase(freq1[y]))
                        {
                            flag++;
                            y++;
                        }
                        else
                            y++;
                    }
                    if(flag==0)
                       freq1[++l]=str[x];
                    x++;
        
                }       
        
                for(int g=0;g<=l;g++)
                      bw.write(freq1[g]+"\n");
                                             
                //conceptpairlem(freq1,l);
                
                    bw.close();
       
   }
   
   public static void relext(String subj[], String verb[], String obj[], int k) throws FileNotFoundException, IOException
   {
        String s, st1,st2, subject,object;
        String vbset[]=new String[1000];
        String relation;
        
            String s1="result/temp/conceptpair.txt";
            FileReader fr=new FileReader(s1);
            BufferedReader br=new BufferedReader(fr);
            
            String s2="result/temp/propositiontemp.txt";
            FileWriter fw=new FileWriter(s2);
            BufferedWriter bw=new BufferedWriter(fw);
            
            while((s=br.readLine())!=null)
            {
                int j=0;
                StringTokenizer st=new StringTokenizer(s," ");
                subject=st.nextToken();
                object=st.nextToken();
            int flag=0;    
      //      System.out.println("Subject: "+subject+", Object: "+object);
            st1="np "+subject;
            st2="np "+object;
                 for(int i=0;i<k;i++)
            {
                if((st1.equals(subj[i]))&&(st2.equals(obj[i])))
                {
                   verb[i]=verb[i].replace("vp ", "");
           
                   vbset[j]=verb[i];
                   j++;
                        
                }
            }
           
            
            String rl="";
            int fl=0;
                    if(j>1)    //more than one relation
                    {
                      rl=relationlabel(vbset,j);
                      fl=1;
                    }
             for(int y=0;y<j;y++)
            {
         //        System.out.println("entered");
          //      System.out.println(subject+"\t"+vbset[y]+"\t"+object);
                if (fl==1)
                    bw.write(subject+"\t"+rl+"\t"+object+"\n");
                else
                    bw.write(subject+"\t"+vbset[y]+"\t"+object+"\n");
            }
         
            }
            bw.close();
            br.close();
            
            canconcept cc=new canconcept();
            cc.uniquetriples();
        
   }
   
   public static String relationlabel(String vbset[], int j) throws FileNotFoundException, IOException
   {
        //String dir = "C:/Program Files/WordNet";
          String dir = "WordNet";
	JWS	ws = new JWS(dir, "2.1");
        Lin lin = ws.getLin();

        
        String prep[]={" in"," on"," under"," for"," by"," at"," as"," with","based"};
    
        String vbmain[]=new String[1000];
        int relation;
        
        for(int i=0;i<j;i++)
        {
     
             vbmain[i]=vbset[i];
     
             for(int x=0;x<9;x++)
                 vbmain[i]=vbmain[i].replace(prep[x],"");
     
             vbmain[i]=lem(vbmain[i]);
                   
        }
  
  /*      for(int i=0;i<j;i++)
        {
  
               System.out.println(vbmain[i]);
        }*/
         
      relation=maxfreq(vbmain,j);
          //      System.out.println("relation "+vbset[relation]);
                
           
                int k=100;
     
                
                return vbset[relation];
        
   }
   
        
   public static int maxfreq(String str1[],int i) throws FileNotFoundException, IOException
   {
       int count=0,l,m,s=0,r;
        String str,str3;
   
        int freq[]=new int[1000];
        String freq1[]=new String[1000];
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
                        freq1[++l]=str1[x];
                    
                  
                    x++;
                    //count=1;
                }
            
                  r=0;
                    int p=0;
                    int q=0;
                    int sum=0;
              
                        while(q<=l)
                        {
              
                            count=0;
                            p=0;
                            while(p<i)
                            {
                            if(freq1[q].equalsIgnoreCase(str1[p]))
                            {
              
                                count=count+1;
              
                            }                                                        
                                p=p+1;
                            }
              
                            freq[r]=count;
                            sum+=count;
                //            System.out.println("count "+count);
                            r=r+1;
                            q=q+1;
                            //count=0;
                        }
              
                        int ii, jj, temp;
                        String stemp;
   
                        for(ii=0;ii<l-1;ii++)
                        {
                            for(jj=ii+1;jj<l;jj++)
                            {
                                if(freq[ii]<freq[jj])
                                {
                                    temp=freq[ii];
                                    stemp=freq1[ii];
                                    freq[ii]=freq[jj];
                                    freq1[ii]=freq1[jj];
                                    freq[jj]=temp;
                                    freq1[jj]=stemp;
                                }
                            }
                        }
                        int f;
                     //   System.out.println(l);
            /*            for(f=0;f<=l;f++)
                        {
     
                       
                             System.out.println(f+" "+freq1[f]+" "+freq[f]+" "+(freq[f]*100.0)/sum);
                        } */
                        
                        int max=freq[0];
                        int c=0;
                        for(f=1;f<=l;f++)
                        {
                            if (freq[f]>max)
                            {
                                max=freq[f];
                                c=f;
                            }       
                        }
                //        System.out.println("max "+max);
                //        System.out.println("posi "+c);
                        return c;
    
   }
   
   
   public static String lem(String str) throws FileNotFoundException, IOException
   {
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos,lemma,");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props, false);
        
          
        
   
                Annotation document = pipeline.process(str);
                String lemma=null;
                for (CoreMap sentence : document.get(SentencesAnnotation.class)) 
                    for (CoreLabel token : sentence.get(TokensAnnotation.class)) 
                    {
                        lemma = token.get(LemmaAnnotation.class);
                    }                
   
                return lemma;
       

   }
}
