import java.io.*;
import java.net.*;
import java.util.*;

import java.nio.file.*;
/**
   @author Viebrapadata
*/
public class ATMServerThread extends Thread {
    private Socket socket = null;
    private BufferedReader in;
    PrintWriter out;
    Scanner scanner;

    private String[] langagues;
    private String[] users;
    private String[] message;
    private Boolean validated;
    private int balance;
    private int password;
    private String engagskod;

    public ATMServerThread(Socket socket) {
        super("ATMServerThread");
        this.socket = socket;
    }

    private String readLine() throws IOException {
        String str = in.readLine();
    //    System.out.println(""  + socket + " : " + str);
        return str;
    }

    private boolean validateUser() {
    try {
      boolean userVaildated = false;
      while (userVaildated == false) {
      out.println("Enter cardNumber:");
      String cardNumber = readLine();
      out.println("Enter pin:");
      String pin = readLine();
      if (pin == "1" && cardNumber == "1") {
        userVaildated = true;
      }
    }

    return true;

    } catch (IOException e){
          e.printStackTrace();
      }

          return false;
    }

    private void saveData(String data) {

    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter("data.txt"));
      writer.write(data);
      writer.close();
    } catch (IOException e){
          e.printStackTrace();
      }

    }

    public void run(){

        try {


            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader
                (new InputStreamReader(socket.getInputStream()));

            String inputLine, outputLine;


            int balance = 1000;
            int value;


              /*
            // Validate user:
            boolean userVaildated = false;
            while (userVaildated == false) {
            out.println("Enter cardNumber:");
          inputLine = readLine();

            out.println("Enter pin:");
            inputLine = readLine();

            if (inputLine == "1" && inputLine == "1") {
              userVaildated = true;
              break;
            } else {
                out.println("failed, try again");
            }

            }

            */

            String welcome = Files.readAllLines(Paths.get("textGUI.txt")).get(6);

            out.println(welcome);
            inputLine = readLine();
            int choise = Integer.parseInt(inputLine);
            while (choise != 4) {
                int deposit = 1;
                switch (choise) {
                case 2:
                    deposit = -1;
                case 3:
                    out.println("Enter amount: ");
                    inputLine= readLine();
                    value = Integer.parseInt(inputLine);
                    balance += deposit * value;
                case 1:
                    out.println("Current balance is " + balance + " dollars");
                    out.println("(1)Balance, (2)Withdrawal, (3)Deposit, (4)Exit");
                    inputLine=readLine();
                    choise = Integer.parseInt(inputLine);
                    break;
                case 4:
                    break;
                default:
                    break;
                }
            }

            saveData(Integer.toString(balance));

            out.println("Good Bye");
            out.close();
            in.close();
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
