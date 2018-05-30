import java.io.*;
import java.net.*;
import java.util.Scanner;

import java.util.*;

import java.nio.file.*;

/**
   @author Fredrik Bixo, Carl Hultberg built on @snilledata code
*/
public class ATMClient {
    private static int connectionPort = 8990;

    /**
    Function name: getLanguage
    Input type: integer (parsed from user)
    Return type: Arraylist 
    **/
    private static String[] getLanguage(int inputNr) throws IOException{
        String out = null;
        List<String> lines = new ArrayList<String>();
            if(inputNr == 1){
        try(BufferedReader buffer = new BufferedReader(new FileReader("svenska.txt"))){
            while((out = buffer.readLine()) != null){
                lines.add(out);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
         return lines.toArray(new String[lines.size()]);
            }
        else if(inputNr == 2){
        try(BufferedReader buffer = new BufferedReader(new FileReader("engelska.txt"))){
            while((out = buffer.readLine()) != null){
                lines.add(out);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
         return lines.toArray(new String[lines.size()]);
        }
        else {
            return lines.toArray(new String[lines.size()]);
        }
    }

    public static void main(String[] args) throws IOException {

        Socket ATMSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        String adress = "";
        String[] prompts = null;

        int maxOptions = 5;

        try {
            adress = args[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Missing argument ip-adress");
            System.exit(1);
        }
        try {
            ATMSocket = new Socket(adress, connectionPort);
            out = new PrintWriter(ATMSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader
                                    (ATMSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " +adress);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't open connection to " + adress);
            System.exit(1);
        }

        System.out.println("Contacting bank ... ");
        System.out.println(in.readLine());
        
       
        // prints out the language options 
        String chooseLang = Files.readAllLines(Paths.get("textGUI.txt")).get(0);
        System.out.println(chooseLang);
         
        //waiting for user input
        Scanner LangScanner = new Scanner(System.in);
            boolean langSelected = false;
            while(langSelected == false){
                System.out.print("> ");
                // next integer from user represents the chosen language
                int langOption = LangScanner.nextInt();

               // sends the chosen language to the server
                out.println(langOption);
               
               //gets the chosen language file
               prompts = ATMClient.getLanguage(langOption);
                langSelected = true;
            }

        Scanner scanner = new Scanner(System.in);

        // enter cardNumber
        long cardNumber;
        long pinCode;
        
        //Boolean to check if user is validated
        Boolean fullyValidated = false;
        // run until user is validated
        while (!fullyValidated) {
           //ask to enter cardNr (parses number from the server) 
          System.out.println(prompts[Integer.parseInt(in.readLine())]);
          boolean cardNumberDone = false;
          boolean pinCodeDone = false;

          System.out.print("> ");
          cardNumber = scanner.nextInt();
          // Send the input card number to the server
          out.println(cardNumber);
          // Ask to enter pinCode until clients entered the right one
          System.out.println(prompts[Integer.parseInt(in.readLine())]);

     
          System.out.print("> ");
          pinCode = scanner.nextInt();
          // Send the input card number to the server
          out.println(pinCode);
   
          pinCodeDone = true;
        
          System.out.println(prompts[Integer.parseInt(in.readLine())]);
          // reads boolean, if true then we move on to the next state
          Boolean line =  Boolean.parseBoolean(in.readLine());
          System.out.println(line);
          if (line == true) {
            fullyValidated = true;
          } else {
         // lägg till felmeddelande gällande inlogg om vi fucking pallar
          }

      }
        //welcome message
        System.out.println(prompts[Integer.parseInt(in.readLine())]);
        System.out.print("> ");
        int menuOption = scanner.nextInt();
        int userInput;
        out.println(menuOption);
        while(menuOption < maxOptions) {
                //get balance
                if(menuOption == 1) {
                        int i = Integer.parseInt(in.readLine());
                        //prints the balance and a string to make UX better
                        System.out.println(prompts[Integer.parseInt(in.readLine())] + Integer.toString(i));
                        System.out.println(prompts[Integer.parseInt(in.readLine())]);
                        System.out.print("> ");
                        menuOption = scanner.nextInt();
                        //back to main menu
                        out.println(menuOption);
                } else if (menuOption > maxOptions-1) {
                    break;
                }  else if(menuOption == 2) { 
                  //prints message to ask user to enter the amount they want to withdraw
                  System.out.println(prompts[Integer.parseInt(in.readLine())]);
                  userInput = scanner.nextInt();
                  out.println(userInput); // entered amount
                  // int i represents the entered amount
                  int i = Integer.parseInt(in.readLine());
<<<<<<< HEAD
                  System.out.println(i);

                  if (i != 2) {
=======
                  // prints the updated balance
>>>>>>> 40448e70c6349326a6002887d1c056935f88507f
                  System.out.println(prompts[Integer.parseInt(in.readLine())] + Integer.toString(i));
                  System.out.println(prompts[Integer.parseInt(in.readLine())]);
                } else {
                  System.out.println("Error");
                  //System.out.println(Integer.parseInt(in.readLine()));
                  int d = Integer.parseInt(in.readLine());
                  System.out.println(prompts[Integer.parseInt(in.readLine())] + Integer.toString(d));
                  System.out.println(prompts[Integer.parseInt(in.readLine())]);
                  System.out.println(prompts[Integer.parseInt(in.readLine())]);
                  // System.out.println(prompts[Integer.parseInt(in.readLine())]);
                }


                  System.out.print("> ");
                  // back to main menu
                  menuOption = scanner.nextInt();
                  out.println(menuOption);

                }   else if(menuOption == 3) {
                  // same as withdraw but now the user wants to deposit money
                  System.out.println(prompts[Integer.parseInt(in.readLine())]);
                  userInput = scanner.nextInt();
                  out.println(userInput); // entered amount
<<<<<<< HEAD

                //  System.out.println(prompts[Integer.parseInt(in.readLine())]);
                  int i = Integer.parseInt(in.readLine());
                //  System.out.println(i);

              //    if (i != 2) {
=======
                  // int i represents the entered amount
                  int i = Integer.parseInt(in.readLine());
                  // prints the updated balance
>>>>>>> 40448e70c6349326a6002887d1c056935f88507f
                  System.out.println(prompts[Integer.parseInt(in.readLine())] + Integer.toString(i));
                  System.out.println(prompts[Integer.parseInt(in.readLine())]);


                  System.out.print("> ");
                  menuOption = scanner.nextInt();
                  // back to main menu
                  out.println(menuOption);

                } else if(menuOption == 4) {
<<<<<<< HEAD

                      System.out.println(chooseLang);
                      Scanner LangScanner2 = new Scanner(System.in);
=======
                  // prints out language options
                  System.out.println(chooseLang);
                  // waiting for user input 
                  Scanner LangScanner2 = new Scanner(System.in);
>>>>>>> 40448e70c6349326a6002887d1c056935f88507f
                      boolean langSelected2 = false;
                      // run until language is selected
                      while(langSelected2 == false){
                          System.out.print("> ");
                          // integer representing the chosen language
                          int langOption2 = LangScanner2.nextInt();
                          // sends chosen language to the server
                          out.println(langOption2);
                         //updating array
                         prompts = ATMClient.getLanguage(langOption2);
<<<<<<< HEAD
                         langSelected2 = true;

=======
                         //language chosen, move on to next state
                         langSelected2 = true;
>>>>>>> 40448e70c6349326a6002887d1c056935f88507f
                      }

                      System.out.println(prompts[6]);
                      System.out.print("> ");
                      // back to main menu
                      menuOption = scanner.nextInt();
                    

                    // wait until server response
                      out.println(menuOption);

                      in.readLine();
<<<<<<< HEAD


=======
>>>>>>> 40448e70c6349326a6002887d1c056935f88507f
                }
        }


        out.close();
        in.close();
        ATMSocket.close();
    }
}
