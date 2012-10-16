   import java.io.*;
   import java.net.*;
   import java.util.Scanner;
   
   public class SpcCadets1a{
      public static String nameToScramble = null;
      public static String oURL = "http://wordsmith.org/anagram/anagram.cgi?anagram=&t=10&a=n";
      public static String[] anagrams;
      public static int results;
      public static int anagramCount = 0;
      public static int totalResults;
      public static boolean noAnagrams = false;
   	
      
      public static void main (String[] args){
         nameToScramble = getNameToScramble();
         if(nameToScramble.equals(""))System.exit(0);
         else
         {
            getName();
            if(noAnagrams == true)
            {
               System.out.println("No anagrams found!");
               System.exit(0);
            }
            if(anagramCount < 10)
            {
               System.out.println(anagramCount + " anagrams found");
            }
            else if(anagramCount == 10 && totalResults == 10)
            {
               System.out.println( "10 anagrams found.");
            }
            else if(anagramCount == 10 && totalResults > 10)
            {
               System.out.println( "More than 10 anagrams found, first ten will be displayed.");
            }
         
            for(int i = 0; i < anagramCount; i++)
            {
               System.out.println(anagrams[i]);
            }
           
         }
         
      }
      
      public static String getNameToScramble()
      {
         String temp = null;
         System.out.print("Enter the name you would like to find anagrams for: ");
      
         Scanner scnr = new Scanner(System.in);
      
         temp = scnr.nextLine();
      
         if(temp.trim().equals(""))
         {
            System.out.println("No name entered, please try again.");
         }
         else
         {
         
            System.out.println("Name '" +  temp + "' received, finding anagrams now.");
            scnr.close();
            return temp;
         }
         scnr.close();
         return "";
      }
      
      public static void getName()
      {
         try
         {
            String noSpace = nameToScramble.replace(' ', '+');
            String tempHalfOne = oURL.substring(0, 49);
            String tempHalfTwo = oURL.substring(49);
            URL complete = new URL(tempHalfOne + noSpace + tempHalfTwo);
            System.out.println(complete);
            getPage(complete);
         }
            catch(MalformedURLException e)
            {System.out.println("Malformed URL, exiting now");
               System.exit(0);}
      
      
      
      }
   
   
      public static void getPage(URL x)
      {
         int lineNo = 0;
         int begin;
         int end;
         results = 10;
         anagrams = new String[10];
         String tempName = "";
      
         try{
            BufferedReader in = new BufferedReader(
               new InputStreamReader(x.openStream()));
            BufferedReader blankTest = new BufferedReader(
               new InputStreamReader(x.openStream()));
               
         		
            String line;
            String blankTestLine;
            int lineTestNo = 0;
         		
            while(((blankTestLine = blankTest.readLine()) != null))
            {
               for(int z = 0; z < 92; z++)
               {
                  lineTestNo++;
               } 
               if(blankTestLine.equals("No anagrams found."))
               { noAnagrams = true;
               }          	
            }
            while(((line = in.readLine()) != null) && noAnagrams == false)
            {
               if(lineNo == 91)
               {
                  int sol = line.indexOf(">");
                  int eol = line.indexOf("f");
                  String tempInt = line.substring(sol+1, eol-1);
                  //System.out.println(tempInt);
                  results = Integer.parseInt(tempInt);
                  if(results >= 10)
                  {
                     totalResults = results;
                     results = 10;
                     anagrams = new String[10];
                  }
                  else 
                  {
                     anagrams = new String[results];
                  }
               
               }
               if(lineNo >= 92 && lineNo <=101 )
               {
                  //System.out.println(lineNo);
                  if(lineNo == 92)
                  {
                     begin =	line.indexOf('>',7);
                     end = line.indexOf('<',begin);
                     tempName = line.substring(begin+1, end);
                     anagrams[anagramCount] = tempName;
                     //System.out.println(line);  
                     anagramCount++;
                     
                  }
                  else if(anagramCount <= results-1)
                  {
                     //System.out.println(line);            
                     end = line.indexOf('<',0);
                     tempName = line.substring(0, end);
                     anagrams[anagramCount] = tempName;
                     anagramCount++;
                  }
               
                  
                  lineNo++;
               }
               
               else{lineNo++;
               }
            }
               
         }
            catch(IOException e)
            {
               System.out.println("Something went wrong with the website connection, closing now");
               System.exit(0);
            }
      }
   }