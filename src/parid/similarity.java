/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parid;

/**
 *
 * @author Thenmozhi
 */
import java.util.TreeMap;
import edu.sussex.nlp.jws.*;
import java.io.*;
import java.util.StringTokenizer;



// 'TestExamples': how to use Java WordNet::Similarity

public class similarity
{
   //public static void main(String[] args)throws FileNotFoundException, IOException
    public void sim() throws FileNotFoundException, IOException
	{
            wordnetsim();
            changetriple();
        } 

        public void wordnetsim()throws FileNotFoundException, IOException
        {
		//String dir = "C:/Program Files/WordNet";
                String dir = "WordNet";
		JWS	ws = new JWS(dir, "2.1");

                FileReader fr=new FileReader("result/temp/conceptset.txt");
                BufferedReader br=new BufferedReader(fr);
                
                FileWriter fw=new FileWriter("result/temp/conceptsimset.txt");
                BufferedWriter bw=new BufferedWriter(fw);
                String conarr[]=new String[1000];
                String freq1[]=new String[1000];
                int i,j;
                int flag=0;
                String str, str1="", str2="";
  
		Lin lin = ws.getLin();
	//	System.out.println("Lin\n");
                
                try{
                    
                str=br.readLine();
                i=0;
                while((str)!=null)
                {
                conarr[i]= str;
                i++;
                str=br.readLine();
                }
                
                
                freq1[0]=conarr[0];
                
                int x=1,y=0;
                str1=conarr[0];
                bw.write(str1+" "+str1+"\n");
                while(x<i)
                {
         
                    flag=0;
         
                    
                    while(y>=0)
                    {
                          str1=conarr[x];
                          str2=conarr[y];
                    
         
    
                
         
// all senses
		TreeMap<String, Double> 	scores2	=	lin.lin(str1, str2, "n");			// all senses
		

        
                if((lin.max(str1, str2, "n")==1.00))
                {
                    bw.write(str1+" "+str2+"\n");
                   flag=1;            
                }
                    
                y--;
                }

                    if (flag==0)
                        bw.write(str1+" "+str1+"\n");
                    y=x;
                    x++;
                }
                bw.close();
                br.close();
                }
                catch(Exception e) { System.out.println(e); }
	}
        
        public void changetriple()throws FileNotFoundException, IOException
        {
        
            String str1="",str2="";

            
                
            FileReader fr2=new FileReader("result/temp/tripcm.txt");
            BufferedReader br2=new BufferedReader(fr2);
            
            FileWriter fw=new FileWriter("result/temp/tripsimcm.txt");
            BufferedWriter bw=new BufferedWriter(fw);
            
            String str,str3,str4="";
            int flag=0,flag1=0;
        
        
            str=br2.readLine();
            while(str!=null)
            {
                flag=0;
                flag1=0;
                str=str.toLowerCase();
                FileReader fr1=new FileReader("result/temp/conceptsimset.txt");
                BufferedReader br1=new BufferedReader(fr1);
                str3=br1.readLine();
                str4=str;
                while(str3!=null)
                {
                    StringTokenizer st=new StringTokenizer(str3," ");
                    str1=st.nextToken();
                //    bw.write(str1+" ");
                    str2=st.nextToken();
                  
                    if((!str1.equals(str2)))
                    {
                  
                        if((str.indexOf(str1)!=-1))
                        {
                            str4=str4.replace(str1,str2);
                     //       System.out.println("String 4 : "+str4);
                            flag=1;
                     //   break;
                        }
                        else
                        {
                            str3=br1.readLine();
                            continue;
                        }
                    }
                               
                       str3=br1.readLine();
                    
                }  
                    br1.close();
                    
                if (flag==1)
                {
                //    System.out.println(str4);
                    bw.write(str4+"\n");
                }
                else
                {
                  //  System.out.println(str);
                    bw.write(str+"\n");
                }
                 
                 
                str=br2.readLine(); 
            }
            br2.close();
        bw.close();
        }
        
} // eof
 
