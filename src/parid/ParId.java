/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parid;

/**
 *
 * @author staff
 */
import java.io.*;
import java.util.StringTokenizer;
import java.util.*;

public class ParId {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        // TODO code application logic here
         if (args.length == 0) {

   System.out.println("No Command Line arguments");
   
  } else {
                for (int i = 0; i < args.length; i++) {
                    System.out.println("args[" + i + "]: " + args[i]);}
                }
         
                parid.cmapcomp cs=new parid.cmapcomp();
          
                //String s1= "data/"+args[0];
          //String s5= "result/"+args[1];
            String s1=args[0];
          String s5=args[1];
      //    String s1= "data/in.txt";
        //  String s5= "result/out.txt";
 
            FileReader fr=new FileReader(s1);
            BufferedReader br=new BufferedReader(fr);
            
            String s2="result/temp/s1.txt";
            //String s3="data/s2.txt"; 
            String s3="result/temp/s2.txt"; 
            
            String s6="result/temp/outtemp.txt";
      
             FileWriter fw5=new FileWriter(s5);
                BufferedWriter bw5=new BufferedWriter(fw5);
                FileWriter fw6=new FileWriter(s6);
                BufferedWriter bw6=new BufferedWriter(fw6);
    
            
            String line1, str1, str2, corsim;
            float  corsimf; //msr corpus
            double cscore;   //ipl
            while((line1=br.readLine())!=null)
            {
                
          
                FileWriter fw1=new FileWriter(s2);
                BufferedWriter bw1=new BufferedWriter(fw1);
                FileWriter fw2=new FileWriter(s3);
                BufferedWriter bw2=new BufferedWriter(fw2);
                
                StringTokenizer st=new StringTokenizer(line1,"\t");
                
                    corsim=st.nextToken();
                    corsimf= Float.parseFloat(corsim);
                    str1=st.nextToken();
                    str1=st.nextToken();
                    str1=st.nextToken();
                    str1=str1.replace("'s", "");
                    str1=str1.replace("n't", " not");
                    str1=str1.replace("&", "and");
                    str1=str1.replace("'m", " am");
                    str1=str1.replace("\"", "");
                    str1=str1.replace("'", "");
                    bw1.write(str1);
                    str2=st.nextToken();
                    str2=str2.replace("'s", "");
                    str2=str2.replace("n't", " not");
                    str2=str2.replace("&", "and");
                    str2=str2.replace("'m", " am");
                    str2=str2.replace("\"", "");
                    str2=str2.replace("'", "");
                    bw2.write(str2);
                 
 
            
            bw1.close();
            bw2.close();     //msr corpus
       
            
            
            
         
                                        
            cscore=parid.cmapcomp.cmapscore(s2,s3,bw5,bw6,corsim);
             
            String cscores;
 //            System.out.println("Concept map score: "+cscore);
              
             if (cscore==0.0)
             {
                 cscores=String.valueOf(cscore);
                 bw5.write(corsim+" 1:0.0 2:0.0 3:0.0 4:0.0"+"\n");
                 
                 if (corsimf==0.0)   //msr corpus
                 {
            
                 bw6.write(corsim+" 1:0.0 2:0.0 3:0.0 4:0.0"+"\n");
                 }
                 
             }
             else
             {
                 
             }
             cscores=String.valueOf(cscore);
    
             
            }
      
            bw5.close();
            bw6.close();
        }
    
    
    }

