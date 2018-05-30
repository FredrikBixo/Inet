import java.io.*;
import java.net.*;
import java.util.Scanner;

import java.util.*;

import java.nio.file.*;

/**
   @author Snilledata
*/
public class ATMClient {
    private static int connectionPort = 8990;
  //  private static String[] prompts;

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

        String chooseLang = Files.readAllLines(Paths.get("textGUI.txt")).get(0);
        System.out.println(chooseLang);

        Scanner LangScanner = new Scanner(System.in);
            boolean langSelected = false;
            while(langSelected == false){
                System.out.print("> ");
                int langOption = LangScanner.nextInt();

               // skickar det valda språket till servern
                out.println(langOption);

               prompts = ATMClient.getLanguage(langOption);
                langSelected = true;
            }

        Scanner scanner = new Scanner(System.in);

        // enter cardNumber
        long cardNumber;
        long pinCode;

        Boolean fullyValidated = false;

        while (!fullyValidated) {

          System.out.println(prompts[Integer.parseInt(in.readLine())]);
          boolean cardNumberDone = false;
          boolean pinCodeDone = false;

        //while (!cardNumberDone) {
            System.out.print("> ");
            cardNumber = scanner.nextInt();
            // Send the input card number to the server
            out.println(cardNumber);
            // wait until it received token from server.
            //Boolean line =  Boolean.parseBoolean(in.readLine());
            // System.out.println(line);
            // if (line == true) {
            // cardNumberDone = true;
            // } else {
            //  System.out.println("Wrong pinCode, try again... ");
            // }
          //  if (!cardNumberDone)
            //    System.out.println(lines.get(in.readInt()));
      //  }


        // Ask to enter pinCode until clients entered the right one

          System.out.println(prompts[Integer.parseInt(in.readLine())]);

      //  while (!pinCodeDone) {
          System.out.print("> ");
          pinCode = scanner.nextInt();
          // Send the input card number to the server
          out.println(pinCode);
          // wait until it received token from server.
          //Boolean line =  Boolean.parseBoolean(in.readLine());
      //    System.out.println(line);
        //  if (line == true) {
            pinCodeDone = true;
          //} else {
          //  System.out.println("Wrong pinCode, try again... ");
      //  }
          //  if (!pinCodeDone)
              //  System.out.println(lines.get(in.readInt()));
    //    }
        System.out.println(prompts[Integer.parseInt(in.readLine())]);
        Boolean line =  Boolean.parseBoolean(in.readLine());
        System.out.println(line);
        if (line == true) {
          fullyValidated = true;
        } else {
        //  System.out.println("Wrong user, try again... ");
        }

      }

      //  Scanner scanner1 = scanner.nextInt();
        System.out.println(prompts[Integer.parseInt(in.readLine())]);
        System.out.print("> ");
        int menuOption = scanner.nextInt();
        int userInput;
        out.println(menuOption);
        while(menuOption < maxOptions) {

                if(menuOption == 1) {
                        int i = Integer.parseInt(in.readLine());
                        System.out.println(prompts[Integer.parseInt(in.readLine())] + Integer.toString(i));
                        System.out.println(prompts[Integer.parseInt(in.readLine())]);
                        System.out.print("> ");
                        menuOption = scanner.nextInt();
                        out.println(menuOption);
                } else if (menuOption > maxOptions-1) {
                    break;
                }  else if(menuOption == 2) {

                  System.out.println(prompts[Integer.parseInt(in.readLine())]);
                  userInput = scanner.nextInt();
                  out.println(userInput); // entered amount

                  int i = Integer.parseInt(in.readLine());
                  System.out.println(i);

                  if (i != 2) {
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
                  menuOption = scanner.nextInt();
                  out.println(menuOption);

                }   else if(menuOption == 3) {

                  System.out.println(prompts[Integer.parseInt(in.readLine())]);
                  userInput = scanner.nextInt();
                  out.println(userInput); // entered amount

                //  System.out.println(prompts[Integer.parseInt(in.readLine())]);
                  int i = Integer.parseInt(in.readLine());
                //  System.out.println(i);

              //    if (i != 2) {
                  System.out.println(prompts[Integer.parseInt(in.readLine())] + Integer.toString(i));
                  System.out.println(prompts[Integer.parseInt(in.readLine())]);


                  System.out.print("> ");
                  menuOption = scanner.nextInt();
                  out.println(menuOption);

                } else if(menuOption == 4) {

                      System.out.println(chooseLang);
                      Scanner LangScanner2 = new Scanner(System.in);
                      boolean langSelected2 = false;
                      while(langSelected2 == false){
                          System.out.print("> ");
                          int langOption2 = LangScanner2.nextInt();

                         // skickar det valda språket till servern
                          out.println(langOption2);

                         prompts = ATMClient.getLanguage(langOption2);
                         langSelected2 = true;

                      }

                      System.out.println(prompts[6]);
                      System.out.print("> ");
                      menuOption = scanner.nextInt();
                    //  int userInput;

                    // wait until server response
                      out.println(menuOption);

                      in.readLine();


                }
        }


        out.close();
        in.close();
        ATMSocket.close();
    }
}
