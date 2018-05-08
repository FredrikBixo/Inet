import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
   @author Snilledata
*/
public class ATMClient {
    private static int connectionPort = 8990;

    public static void main(String[] args) throws IOException {

        Socket ATMSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        String adress = "";



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

        Scanner LangScanner = new Scanner(System.in);
            boolean langSelected = false;
            while(langSelected == false){
                System.out.print("> ");
                int langOption = LangScanner.nextInt();
                //skickar det valda språket till servern
                out.println(langOption);
                langSelected = true;
            }

        Scanner scanner = new Scanner(System.in);

        // enter cardNumber
        long cardNumber;
        long pinCode;

        Boolean fullyValidated = false;

        while (!fullyValidated) {

          System.out.println(in.readLine());
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

          System.out.println(in.readLine());

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
        System.out.println(in.readLine());
        Boolean line =  Boolean.parseBoolean(in.readLine());
        System.out.println(line);
        if (line == true) {
          fullyValidated = true;
        } else {
        //  System.out.println("Wrong user, try again... ");
        }

      }

      //  Scanner scanner1 = scanner.nextInt();
        System.out.println(in.readLine());
        System.out.print("> ");
        int menuOption = scanner.nextInt();
        int userInput;
        out.println(menuOption);
        while(menuOption < 4) {
                if(menuOption == 1) {
                        System.out.println(in.readLine());
                        System.out.println(in.readLine());
                        System.out.print("> ");
                        menuOption = scanner.nextInt();
                        out.println(menuOption);
                } else if (menuOption > 3) {
                    break;
                }
                else {
                    System.out.println(in.readLine());
                    userInput = scanner.nextInt();
                    out.println(userInput);
                    String str;
                    do {
                        str = in.readLine();
                        System.out.println(str);
                    } while (! str.startsWith("(1)"));
                    System.out.print("> ");
                    menuOption = scanner.nextInt();
                    out.println(menuOption);
                }
        }



        out.close();
        in.close();
        ATMSocket.close();
    }
}
