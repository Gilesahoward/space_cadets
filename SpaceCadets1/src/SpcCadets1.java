   import java.io.*;
   import java.net.*;
   import java.util.Scanner;
   
   public class SpcCadets1{
      public static String eid = null;
      public static String name;
      public static String sotonURL = "http://www.ecs.soton.ac.uk/people/";
      public static String notFound = "Not Found";
   	
      
      public static void main (String[] args){
         eid = getEmailID();
         if(eid.equals(""))System.exit(0);
         else
         {
            name = getName();
            if(name.equals(notFound)){System.out.println("User not found! Program exiting.");}
            else
            {
               System.out.println("Email ID corresponds to user: " + name);
            }
         }
         
      }
      
      public static String getEmailID()
      {
         String temp = null;
         System.out.print("Enter the email ID you would like to look up: ");
      
         Scanner scnr = new Scanner(System.in);
      
         temp = scnr.nextLine();
      
         if(temp.trim().equals(""))
         {
            System.out.println("No email ID entered, please try again.");
         }
         else
         {
            System.out.println("Email ID '" +  temp + "' received, finding name now.");
            scnr.close();
            return temp;
         }
         scnr.close();
         return "";
      }
      
      public static String getName()
      {
         try
         {
            URL complete = new URL(sotonURL + eid);
            System.out.println(complete);
            return getPage(complete);
         }
            catch(MalformedURLException e)
            {System.out.println("Malformed URL, exiting now");
               System.exit(0);}
      
      
      
         return null;
      }
   
   
      public static String getPage(URL x)
      {
         int lineNo = 0;
         int begin;
         int end;
         String tempName = "";
            
         try{
            BufferedReader in = new BufferedReader(
               new InputStreamReader(x.openStream()));
            String line;
         	
         		
            while(((line = in.readLine()) != null))
            {
               if(lineNo == 148)
               {
                  begin =	line.indexOf('>');
                  end = line.indexOf('<',begin);
                  tempName = line.substring(begin+1, end);
                  if(tempName.equals("This page either does not exist or you are not able to view it."))
                     return notFound;
                  else
                  {
                     return tempName;
                  
                  }}
               else{lineNo++;
               }
            }
               
         }
            catch(IOException e)
            {
               System.out.println("Something went wrong with the website connection, closing now");
               System.exit(0);
            }
         return "";
      }
   }