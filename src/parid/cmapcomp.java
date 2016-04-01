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
import java.util.StringTokenizer;

import java.util.TreeMap;
import edu.sussex.nlp.jws.*;

import java.util.*;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.ling.CoreAnnotations.*;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class cmapcomp {
    
public static double cmapscore(String s1, String s2, BufferedWriter bw5,BufferedWriter bw6, String corsim)throws FileNotFoundException, IOException
  {
            chunker ch=new chunker();
   //         sentencedivision sd=new sentencedivision();
            sentdiv sd=new sentdiv();
            canconcept cc=new canconcept();
            similarity sm=new similarity();
            canrelation cr=new canrelation();
            
           try{
            
                String concept1[]=new String[1000];
                String concept2[]=new String[1000];
                String nes[]=new String[1000];
                String rel1[]=new String[1000];
                String rel2[]=new String[1000];
                String pair1[]=new String[1000];
                String pair2[]=new String[1000];
              
                int k=0,l=0,score=0,neno=0;
                int relno1=0, relno2=0;
                int pairno1=0, pairno2=0;
                float scorecon=0,scorerel=0,scorepair=0, scoreword=0;
                double scorecmap=0; //ipl
          
                ch.chunk(s1);
                sd.div();
                cc.uniqueconcepts();
                sm.sim();
                cr.rel();
                            
        
            k=ext_concept(concept1);
            neno=ext_ne(nes,neno);
            relno1=ext_rel(rel1);
              pairno1=ext_pair(pair1);    
        
              
          
            ch.chunk(s2);
            sentdiv.div();
            cc.uniqueconcepts();
            sm.sim();
            cr.rel();
            
           l=ext_concept(concept2);
           neno=ext_ne(nes,neno);
           relno2=ext_rel(rel2);  
           pairno2=ext_pair(pair2);
            
            
           scorecon=conceptcomp(concept1,k,concept2,l,nes,neno);
           scorerel=relcomp(rel1,relno1,rel2,relno2);
           //scorepair=paircomp(pair1,pairno1,pair2,pairno2);
           scorepair=paircomp(pair1,pairno1,pair2,pairno2,rel1,relno1,rel2,relno2);
           scoreword=wordcomp(s1,s2);
           
         
           
           bw5.write(corsim+" 1:"+scorecon+" 2:"+scorerel+" 3:"+scorepair+" 4:"+scoreword+"\n");
           bw6.write(corsim+" 1:"+scorecon+" 2:"+scorerel+" 3:"+scorepair+" 4:"+scoreword+"\n");
         
           scorecmap=(scorecon+(2*scorepair)+(0.5*scoreword))/3.5;
         
         
     //         System.out.println("Concept Map Similarity: "+scorecmap);      
              return scorecmap;      
            
           }
            catch(Exception E)
            {
                System.out.println(E);
                return 0;
            }
           
    
        }
    

public static float wordcomp(String s1,String s2)throws FileNotFoundException, IOException
    {
        float score=0;
        String word1[]=new String[10000];
        String word2[]=new String[10000];
        int i=0, j=0;
        String str1, str2;
        
        FileReader fr1=new FileReader(s1);
        BufferedReader br1=new BufferedReader(fr1);
        str1=br1.readLine();
        
        StringTokenizer st=new StringTokenizer(str1," ");
                word1[i++]=st.nextToken();
                                                       
                    while(st.hasMoreTokens())
                    {
                        word1[i]=st.nextToken();
                             i++;
                    }    
          //          System.out.println("word1");   
         for(int k=0;k<i;k++)
         {
           word1[k]=word1[k].replace(",", "");
           word1[k]=word1[k].replace(".", "");
      //     System.out.println(word1[k]);   
         }
         
         FileReader fr2=new FileReader(s2);
        BufferedReader br2=new BufferedReader(fr2);
        str2=br2.readLine();
        
        StringTokenizer st1=new StringTokenizer(str2," ");
                word2[j++]=st1.nextToken();
                                                       
                    while(st1.hasMoreTokens())
                    {
                        word2[j]=st1.nextToken();
                             j++;
                    }    
           //         System.out.println("word2");   
         for(int k=0;k<j;k++)
         {
           word2[k]=word2[k].replace(",", "");
           word2[k]=word2[k].replace(".", "");
        //   System.out.println(word2[k]);   
         }

 //      System.out.println("matched words");
 //      System.out.println(" i and j value "+i+" "+j);
        for(int x=0;x<i;x++)
        {
           
            for(int y=0;y<j;y++)
            {
           
                int flag=0;
        
                if (word1[x].equalsIgnoreCase(word2[y]))
                {

              //      System.out.println(word1[x]+" "+word2[y]);
                  //  if(flag==1)
                        score=score+1;
                  
                    
                }
            }
        }
        //score=(score*100)/(l);
 //       System.out.println("matched score "+score);
        score=score/(i+j-score);
        
   //     System.out.println("simscore "+i+" "+j+" "+score);
         
        if (score>1)
                score=1;
        if (score<0)
                score=0;
        
        return score;
    }



    public static float conceptcomp(String concept1[],int k, String concept2[],int l, String nes[], int neno)throws FileNotFoundException, IOException
    {
            //String dir = "C:/Program Files/WordNet";
            String dir = "WordNet";
            JWS	ws = new JWS(dir, "2.1");
            Lin lin = ws.getLin();
            
         float score=0;
         
 /*       System.out.println("concept1");
        for(int j=0;j<k;j++)
           System.out.println(concept1[j]); 
        System.out.println("concept2");
        for(int j=0;j<l;j++)
           System.out.println(concept2[j]);
        System.out.println("named entities");
        for(int j=0;j<neno;j++)
           System.out.println(nes[j]);   
        
        System.out.println("matched concepts"); */
        for(int i=0;i<k;i++)
        {
            for(int x=0;x<l;x++)
            {
                
                int flag=0;
           
                if((concept1[i].equals(concept2[x]))&&!(concept1[i].equals("null"))||(lin.max(concept1[i], concept2[x], "n")>=0.6)||(concept1[i].equals(concept2[x])))
                {
               
              //      System.out.println(concept1[i]+" "+concept2[x]);
                  //  if(flag==1)
                        score=score+1;
                 
                }
            }
        }
        
   //     System.out.println("matched score "+score);
        score=score/(k+l-score);
        
   //     System.out.println("simscore "+k+" "+l+" "+score);
        
        if (score>1.0)
            score=1;
        else if (score<0.0)
            score=0;
        
        return score;
            
        }

    
    public static int ext_concept(String concept[]) throws FileNotFoundException, IOException
    {
        int k=0;
        String line;
        FileReader fr=new FileReader("result/temp/conceptset.txt");
        BufferedReader br=new BufferedReader(fr);
        
        while((line=br.readLine())!=null)
            {
                if(!(line.equalsIgnoreCase("it"))&&!(line.equalsIgnoreCase("?"))&&!(line.equalsIgnoreCase("he"))&&!(line.equalsIgnoreCase("they")))
                {
                    concept[k]=line;
                    k++;
                }
                    
            }
        
        br.close();
            
        return k;
    } 
    
    public static int ext_ne(String ne[],int k) throws FileNotFoundException, IOException
    {
        //int k=0;
        String line;
        FileReader fr=new FileReader("result/temp/neset.txt");
        BufferedReader br=new BufferedReader(fr);
        
        while((line=br.readLine())!=null)
            {
           
                {
                    ne[k]=line;
                    k++;
                }
                    
            }
        
        br.close();
            
        return k;
    } 
    
    public static int ext_rel(String rel[]) throws FileNotFoundException, IOException
    {
        String subj="",verb="",obj="",line="",str1="";
        String subject,object;
        String str[]=new String[1000];
        String sbar[]=new String[1000];
        String vbar[]=new String[1000];
        String obar[]=new String[1000];
        
        String prep[]={" in"," on"," under"," for"," by"," at"," as"," with","based", " up", " down", " to"," through"," over"};
            
    //    try{
        FileReader fr=new FileReader("result/temp/cantrip.txt");
        BufferedReader br=new BufferedReader(fr);
            
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
                            sbar[k]=subject;
                            vbar[k]=verb;
                            obar[k]=object;
                            str[i]=subj+" "+obj;
                            rel[k]=vbar[k].replace("vp ", "");
                            rel[k]=rel[k].replace("pp ", "");
                      
                            for(int x=0;x<14;x++)
                                rel[k]=rel[k].replace(prep[x],"1");
                            rel[k]=lem(rel[k]);
                      
                            i++;
                          k++;
              } //while
            
            br.close();
            
            return k;
    
    }
    
    public static float relcomp(String rel1[],int relno1, String rel2[],int relno2)throws FileNotFoundException, IOException
    {
            //String dir = "C:/Program Files/WordNet";
            String dir = "WordNet";
            JWS	ws = new JWS(dir, "2.1");
            Lin lin = ws.getLin();
            
         float score=0;
         
  /*      System.out.println("relations1");
        for(int j=0;j<relno1;j++)
           System.out.println(rel1[j]); 
        System.out.println("relations2");
        for(int j=0;j<relno2;j++)
           System.out.println(rel2[j]); 
                
        System.out.println("matched relations");*/
        for(int i=0;i<relno1;i++)
        {
            for(int x=0;x<relno2;x++)
            {
              
                int flag=0;
                 if((rel1[i].equals(rel2[x])) ||(lin.max(rel1[i], rel2[x], "v")>=0.6))
                     //if((rel1[i].equals(rel2[x])) ||(lin.max(rel1[i], rel2[x], "n")>=0.55))
                 {
                
                 //   System.out.println(rel1[i]+" "+rel2[x]);
                
                        score=score+1;
                    
                }
            }
        }
                score=score/(relno1+relno2-score);
   //     System.out.println("score"+score);
        
        if (score>1)
            score=1;
        if (score<0)
                score=0;

        return score;
            
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
       
       
     public static int ext_pair(String pair[]) throws FileNotFoundException, IOException
    {
        int k=0;
        String line;
        FileReader fr=new FileReader("result/temp/conceptpair.txt");
        BufferedReader br=new BufferedReader(fr);
        
        while((line=br.readLine())!=null)
            {
                pair[k]=line;
                k++;
            }
        br.close();
        return k;
    }
     
      
     public static float paircomp(String pair1[],int pairno1, String pair2[],int pairno2, String rel1[],int relno1, String rel2[],int relno2)throws FileNotFoundException, IOException
    {
          //  String dir = "C:/Program Files/WordNet";
          String dir = "WordNet";
            JWS	ws = new JWS(dir, "2.1");
            Lin lin = ws.getLin();
            
         float score=0;
         String sub1,obj1,sub2,obj2;
         
  //      System.out.println("concept pair 1");
  /*     for(int j=0;j<pairno1;j++)
        {
           System.out.println(pair1[j]); 
             System.out.println(rel1[j]);
        }
        System.out.println("concept pair 2");
        for(int j=0;j<pairno2;j++)
        {
            System.out.println(pair2[j]);  
               System.out.println(rel2[j]);  
        }
                
         
        System.out.println("matched pair"); */
    /*    for(int j=0;j<pairno1;j++) //examples
        {
            if(pair1[j].equals(pair2[j]))
            {
                if((lin.max(rel1[j], rel2[j], "v")>=0.6))
                                 score=score+1;
                
                
            }
            else
            {
                
                StringTokenizer st=new StringTokenizer(pair1[j]," ");
            sub1=st.nextToken();
            obj1=st.nextToken();
                StringTokenizer st1=new StringTokenizer(pair2[j]," ");
                sub2=st1.nextToken();
                obj2=st1.nextToken();
                if(((lin.max(obj1, sub2, "n")>=0.6)&&(lin.max(sub1, obj2, "n")>=0.6))||(obj1.equals(sub2))&&(sub1.equals(obj2)))                        
                {
                    System.out.println(j+":yyy");
                 if((rel1[j].endsWith("1")||rel2[j].endsWith("1")))
                {
                    rel1[j]=lem(rel1[j].replace("1", ""));
                    rel2[j]=lem(rel2[j].replace("1", ""));
                    if ((lin.max(rel1[j], rel2[j], "v")>=0.6))
                        {
                            score=score+1;
                        }
                }
                 else if ((rel1[j].equals("be")&&rel2[j].startsWith("include")||rel2[j].equals("be")&&rel1[j].startsWith("include"))&&!rel1[j].equals(rel2[j]))
                    {
                     System.out.println(j+":yyy");
                        score=score+1;
                    }
                }
                if(((lin.max(sub1, sub2, "n")>=0.6)&&(lin.max(obj1, obj2, "n")>=0.6))||((lin.max(obj1, sub2, "n")>=0.6)&&(lin.max(sub1, obj2, "n")>=0.6))||(sub1.equals(sub2))&&(obj1.equals(obj2))||(obj1.equals(sub2))&&(sub1.equals(obj2)))                        
         //     if(((lin.max(sub1, sub2, "n")>=0.6)&&(lin.max(obj1, obj2, "n")>=0.6)))                        
                {
                   if((lin.max(rel1[j], rel2[j], "v")>=0.6))
                   {
                
                       score=score+1;
                   }
                   else if (rel1[j].equals("be")&&rel2[j].startsWith("include")||rel2[j].equals("be")&&rel1[j].startsWith("include"))
                     score=score+1;
                }
               
            }
        } */
        
        for(int i=0;i<pairno1;i++)
        {
        //    System.out.println("pair1"+pair1[i]);
            StringTokenizer st=new StringTokenizer(pair1[i]," ");
            sub1=st.nextToken();
            obj1=st.nextToken();
            for(int x=0;x<pairno2;x++)
            {
              //  System.out.println("pair2"+pair2[x]);
                StringTokenizer st1=new StringTokenizer(pair2[x]," ");
                sub2=st1.nextToken();
                obj2=st1.nextToken();
              
                int flag=0;

            if(((lin.max(sub1, sub2, "n")>=0.6)&&(lin.max(obj1, obj2, "n")>=0.6))||((lin.max(obj1, sub2, "n")>=0.6)&&(lin.max(sub1, obj2, "n")>=0.6))||(sub1.equals(sub2))&&(obj1.equals(obj2))||(obj1.equals(sub2))&&(sub1.equals(obj2)))                        
                {
                 //   System.out.println(pair1[i]+" "+pair2[x]);
                    if((rel1[i].contains("not")&&!rel2[i].contains("not"))||(rel2[i].contains("not")&&!rel1[i].contains("not")))
                        flag=1;
                        
                    if (flag==0)
                        score=score+1;
                    
                }
            }
        } 

    //    System.out.println("pair score "+score);
        score=score/(pairno1+pairno2-score);
   //     System.out.println("score "+score);
        
        if (score>1.0)
            score=1;
        else if (score<0.0)
            score=0;
        
        return score;
            
        }
}
