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

/**
 *
 * @author SSN
 */
public class sentdiv {

    
    public static void div() throws FileNotFoundException, NoSuchElementException{
  //  public void div() throws FileNotFoundException, NoSuchElementException{
        String subj="",verb="",prep="",obj="",line="",str1="",line1="",str6="",str7="",prevsub="",prevobj="",prevverb="";
        String str="",subj2="",obj2="";
        String prev[]=new String[2];
        int flag1=0,flag2=0,i,flag3=0,flag4=0;
        int ij,lno=0;
        try
        {
   
            String s5="result/temp/tripcm.txt";
            String s6="result/temp/candidateconcepts.txt";
   
            FileWriter fw2=new FileWriter(s5);
            BufferedWriter bw2=new BufferedWriter(fw2);
            FileWriter fw3=new FileWriter(s6);
            BufferedWriter bw3=new BufferedWriter(fw3);
   
            
            String s1="result/temp/chunk.txt";
            String s2="result/temp/linecm.txt";
            FileReader fr=new FileReader(s1);
            BufferedReader br=new BufferedReader(fr);
       
            while((line1=br.readLine())!=null)
            {
       
                subj="?";verb="?";prep="?";obj="?";subj2="?";obj2="?";
                   FileWriter fw=new FileWriter(s2);
                    BufferedWriter bw=new BufferedWriter(fw);
           //     System.out.println("line no: "+lno++);
                StringTokenizer st=new StringTokenizer(line1," ");
                    str1=st.nextToken();
                    bw.write(str1+" ");
                    str1=st.nextToken();
                    while(st.hasMoreTokens())
                    {
                        if((str1.endsWith("P"))||(str1.endsWith("SBAR"))||(str1.endsWith("ADVP"))||(str1.endsWith("CC")))
                        {
                            bw.write("\n");
                            bw.write(str1+" ");
                        }
                        else
                        {
                            bw.write(str1+" ");
                        }
                        str1=st.nextToken();
                    }
                    bw.write(str1+"\n");
          //          bw.close();
            //}
            bw.close();
            
            
            
            String s3="result/temp/linecm.txt";
            
            FileReader fr1=new FileReader(s3);
            BufferedReader br1=new BufferedReader(fr1);
            
            int slno=0;
            while((line=br1.readLine())!=null)
            {
          //      System.out.println("start"+line);
                subj="?";verb="?";prep="?";obj="?";subj2="?";
                if((line.startsWith("NP"))||(line.startsWith("NNP")))
                {
                    subject(line, br1, bw2,bw3);
             //       System.out.println("sgsg");
                }
                else if(line.startsWith("PP"))
                {
                    
         //           System.out.println("hi"+line);
              
                    {
                    line=br1.readLine();
                    prev[0]=line;
                    while(((line!=null))&&!(line.startsWith("VP"))&&!(line.endsWith("ing "))&&!(line.startsWith("ADV")))
                       {
                        prev[0]=line;
                        line=br1.readLine();
                       }
              //      System.out.println("prev"+prev[0]);
                           verbfn(line, prev, 0, br1,bw2,bw3);
                    }
                }
                else if(line.startsWith("VP"))
                {
            //        System.out.println("verbphrase"+line);
                    prev[0]="?";
                           verbfn(line, prev, 0, br1,bw2,bw3);
                }
                else
                    continue;
                
            }
             }
          
          //  }
          //  bw1.close();
            bw2.close();
            bw3.close();
 
        } //try
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
          
     public static void subject(String line, BufferedReader br1, BufferedWriter bw2,BufferedWriter bw3)
     {
         String prev[]=new String[20];
         String subj[]=new String[20];
         String temp[]=new String[20];
         String temp1[]=new String[20];
         String verb="",prep="",obj="",str1="",line1="",str6="",str7="",prevsub="",prevobj="",prevverb="";
        String str="",subj2="?",obj2="?";
        int flag=0, i=0, flag1=0;
        try
        {
    //        System.out.println("sub loop");
            subj[0]=line;
            temp1[0]=line;
            if((line.startsWith("NP one"))||(line.startsWith("NP set"))||(line.startsWith("NP group"))||(line.startsWith("NP exampl")))
                flag1=1;
     //       System.out.println("sub loop"+subj[0]);
            if((line=br1.readLine())!=null)
            {              
                  if(line.startsWith("PP"))
                  {
                      if((line.startsWith("PP of"))||(line.startsWith("PP on")))
                      {
                      line=br1.readLine();
                      temp[0]=line;
                      if(line.startsWith("NP"))
                      {
                          line=br1.readLine();
                          if(line.startsWith("VP"))
                          {
                              if(flag1==1)
                                  verbfn(line, temp, i, br1,bw2,bw3);
                              else
                                verbfn(line, subj, i, br1,bw2,bw3);
                          }
                          
                      }
                      }
                      else if(line.endsWith("ing "))
                      {
                          verbfn(line, subj, i, br1,bw2,bw3);
                      }
                      
                  }
                          
                   else if(line.startsWith("CC"))
                  {
                      
                      subj[++i]=br1.readLine(); 
                  //    flag=1;
                      
                      if((line=br1.readLine())!=null)                      
                      {
                      if(line.startsWith("VP"))
                      {
                          verbfn(line, subj, i, br1,bw2,bw3);
                //        verbfn(line, subj2, flag, br1);
                      }
                      
                      }
                  }
                   else if(line.startsWith("VP"))
                   {
                         verbfn(line, subj, i, br1,bw2,bw3);
                   }
                   else if(line.startsWith("ADV"))
                   {
                       line=br1.readLine();
                         verbfn(line, subj, i, br1,bw2,bw3);
                   }
                   else if(line.startsWith("PP"))
                   {
               //        System.out.println("loop");
                       while((line!=null))
                       {
                        prev[0]=line;
                        if(!(line.startsWith("VP")))
                        {
                        line=br1.readLine();
                        }
                        else
                        {
                            flag=1;
                            break;
                        }
                        }
                       if(flag==1)
                           verbfn(line, prev, 0, br1,bw2,bw3);
                    }
                  else if(line.startsWith("NP"))
                   {
                       subj[0]=line;
                       if(((line=br1.readLine())!=null)&&(line.startsWith("VP")))
                       {
                       verbfn(line, subj, 0, br1,bw2,bw3);
                       }
                   }
                  else if(line.startsWith("WHNP"))
                   {
                       subj[0]=subj[i];
                       if(((line=br1.readLine())!=null)&&(line.startsWith("VP")))
                       {
                       verbfn(line, subj, 0, br1,bw2,bw3);
                       }
                   }
            }
        }
        catch(Exception e){System.out.println(e);}
     }
        
public static void verbfn(String line, String subj[], int i, BufferedReader br1, BufferedWriter bw2,BufferedWriter bw3)
     {
                String verb="",prep="",obj="",str1="",line1="",str6="",str7="",prev="",prevsub="",prevobj="",prevverb="";
        String str="",subj2="",obj2="";
        try
        {
          
            verb=line;
    //        System.out.println("verb loop"+verb);
            if((line=br1.readLine())!=null)
            {
                  if(line.startsWith("PP"))
                  {
                      line=line.replace("PP","");
                      verb=verb+line;
         //             System.out.println("line: "+verb);
                      line=br1.readLine();
                      object(line,subj,verb,i,br1,bw2,bw3);
                  }
                  else if(line.startsWith("VP"))
                  {
                      line=line.replace("VP","");
                      verb=verb+line;
                      line=br1.readLine();
                      while(!(line.startsWith("NP"))&&!(line.startsWith("AD")))
                      
                      {
              //            System.out.println("linevp: "+verb);
                          line=line.replace("VP","");
                          line=line.replace("PP","");
                          verb=verb+line;
                          line=br1.readLine();
                      }
                   //   System.out.println("line: "+verb);//continue;
                      object(line,subj,verb,i,br1,bw2,bw3);
                  }
                  else if(line.startsWith("NP"))
                  {
                      object(line,subj,verb,i,br1,bw2,bw3);
                  }
                  else if(line.startsWith("ADV"))
                  {
                      line=br1.readLine();
                      while(!(line.startsWith("NP")))
                      {
                          line=line.replace("PP","");
                          line=line.replace("VP","");
                          verb=verb+line;
                          line=br1.readLine();
                      }
                      object(line,subj,verb,i,br1,bw2,bw3);
                  }
                  else if(line.startsWith("ADJ"))
                  {
                      line=br1.readLine();
                      while(!(line.startsWith("NP")))
                      {
                          line=line.replace("PP","");
                          line=line.replace("VP","");
                          line=line.replace("ADJP","");
                          verb=verb+line;
                         line=br1.readLine();
                      }
                      object(line,subj,verb,i,br1,bw2,bw3);
                  }
            }
        }
        catch(Exception e){System.out.println(e);}
     }
        
     public static void object(String line, String subj[], String verb, int i, BufferedReader br1, BufferedWriter bw2,BufferedWriter bw3)
     {
         String obj[]=new String[20];
                String prep="",str1="",line1="",str6="",str7="",prev="",prevsub="",prevobj="",prevverb="";
        String str="",subj2="",obj2="";
        int k=0,l,j;
        try
        {
            
            
            obj[0]=line;
            prev=line;
     //     System.out.println("obj loop"+obj[0]);
          
            if((line=br1.readLine())!=null)
            {
                     if(line.startsWith("CC and"))
                      {
                          if ((verb.startsWith("VP is"))||(verb.startsWith("VP was")))
                          {
                              line=br1.readLine();
       
                              subject(line,br1,bw2,bw3);
                          }
       
                          
                          else
                          { 
                        //      System.out.println("obj loop"+line);
                              line=br1.readLine();
                              if(line.startsWith("VP"))
                              {
                              
                              verbfn(line,subj,i,br1,bw2,bw3);
                                 
                              }
                              else
                              {
                                  obj[++k]=line;
                          //        System.out.println("obj loop"+obj[k]);
                              }  
                
                          }
                      }
                     else if(line.startsWith("CC or"))
                      {
                          line=br1.readLine();
                              obj[++k]=line;
                      }   
                     else if(line.startsWith("PP"))
                     {
                         if((prev.startsWith("NP one"))||(prev.startsWith("NP set"))||(prev.startsWith("NP group"))||(prev.startsWith("NP type")))
                         {
                             obj[0]=br1.readLine();
                         }
                         else if(line.endsWith("ing "))        
                         {
                              for(j=0;j<=i;j++)
                                 for(l=0;l<=k;l++)
                                      display(subj[j],verb,obj[l],bw2,bw3);  
                             subj[0]=prev;
                             verb=line;
                             obj[0]=br1.readLine();
                             
                         }
                         else
                         {
                             obj[0]=prev;
                         }
                     }
                     else if(line.startsWith("NP")||line.startsWith("WHNP"))
                     {
                         if(line.startsWith("NP it ")||(line.startsWith("NP that"))||(line.startsWith("NP this"))||(line.startsWith("NP they"))||(line.startsWith("WHNP that")))
                             subject(subj[i],br1,bw2,bw3);
                         else if(line.startsWith("WHNP which"))
                             subject(obj[0],br1,bw2,bw3);
                         else
                             subject(line,br1,bw2,bw3);
                     }
                     else if(line.startsWith("ADVP"))
                     {
                             subject(br1.readLine(),br1,bw2,bw3);
                     }
       
                     else if(line.startsWith("VP"))
                     {
                    //     System.out.println("objectvp");
                         verbfn(line,obj,i,br1,bw2,bw3);
                     }
                     else if(line.startsWith("WHNP"))
                    {
                        subject(obj[k],br1,bw2,bw3);
                    }
                     
                    
                   for(j=0;j<=i;j++)
                       for(l=0;l<=k;l++)
                        display(subj[j],verb,obj[l],bw2,bw3);  
       
                             
            }       
            else
            {
            //    System.out.println("objectothers");
                for(j=0;j<=i;j++)
                       for(l=0;l<=k;l++)
                        display(subj[j],verb,obj[l],bw2,bw3);  
            }
              
                      
        }
        catch(Exception e){System.out.println(e);}
     }
            
                 

    public static void display(String subject,String verb,String object,BufferedWriter bw2,BufferedWriter bw3) throws Exception
    {
       
        System.out.println(subject+" "+verb+" "+object);
        
        subject=subject.replace("and ", "");
        subject=subject.replace("or ", "");
        subject=subject.replace(",", "");
        subject=subject.replace(";", "");
        
         object=object.replace(" and", "");
            object=object.replace(" or", "");
            object=object.replace(" ,", "");
            object=object.replace(" . .", "");
            object=object.replace(" ;", "");
            
            //verb=verb.replace("PP","VP");
         //   verb ="VP "+ lem(verb);
    
        if(!subject.endsWith("ing"))
            subject ="NP "+ lem(subject);
        if(!object.endsWith("ing"))
            object="NP "+lem(object);   
 
         
        bw2.write(subject+" "+verb+" "+object+"\n");
        candidateconcepts(subject,verb,object,bw3);
        //return 1;
    }
  
             
         public static void candidateconcepts(String subject,String verb,String object,BufferedWriter bw3) throws Exception
         {
     
           
             subject=subject.replace("NP ", "");
             subject=subject.toLowerCase();
             object=object.replace("NP ", "");
             object=object.toLowerCase();
       //      System.out.println("concept  "+subject);
             if(!(subject.equalsIgnoreCase("it"))&&!(object.equalsIgnoreCase("it")))
             {
             bw3.write(subject);
             bw3.write("\n");
             bw3.write(object);
             bw3.write("\n");
             }
      
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
