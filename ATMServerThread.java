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

    String input;
    private String[] langagues;
    private String[] users;
    private List<String> lines;
    private List<String> userInfo;
    private Boolean validated;
    private int balance;
    private int password;
    private String engagskod;
    private String[] prompts;

    public ATMServerThread(Socket socket) {
        super("ATMServerThread");
        this.socket = socket;
    }

    private String readLine() throws IOException {
        String str = in.readLine();
      //  System.out.println(""  + socket + " : " + str);
        return str;
    }

    private String[] getLanguage(int inputNr) throws IOException{
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



    private boolean validateUser(int userName, int passWord, String[] users) {
        if(userName == Integer.parseInt(users[1])){
            if(passWord != Integer.parseInt(users[3])) {
                return false;
            }
        this.balance = 5;
        this.password = 7;

        return true;
        }
        else if(userName == Integer.parseInt(users[10])){
            if(passWord != Integer.parseInt(users[12])){
                return false;
            }
        this.balance = 14;
        this.password = 16;
        return true;
        }
        return false;
    }

    private String[] getUsers() throws IOException{
        String out = null;
        List<String> lines = new ArrayList<String>();
        try(BufferedReader buffer = new BufferedReader(new FileReader("UserInfo.txt"))){
            while((out = buffer.readLine()) != null){
                lines.add(out);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return lines.toArray(new String[lines.size()]);
    }



    private void saveSaldoTouser(String user, String balance) {



    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter("UserInfo.txt"));
      writer.write(balance);
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

            String chooseLang = Files.readAllLines(Paths.get("textGUI.txt")).get(0);
            out.println(chooseLang); //skickar språkval
            input = in.readLine();
            int lang = Integer.parseInt(input);
            this.prompts = getLanguage(lang); //hämtar valt språk

            String currentUser = "USER1";
            
            int balance = 1000;
            int value;

            users = getUsers();

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


            int inputCardNumber = 0;
            int inputPinCode = 0;

            Boolean fullyValidated = false;

            while (!fullyValidated) {


              out.println(prompts[0]);
              boolean cardNumberDone = false;
            // Run the language-choosing process until clients
            //have chosen an available language
          //  while (!cardNumberDone) {
                inputCardNumber = Integer.parseInt(in.readLine());
                System.out.println(inputCardNumber);
              //    out.println(true);
            //    if (inputCardNumber == 1) {
                  cardNumberDone = true;



                out.println(prompts[2]);
                  boolean pinCodeDone = false;

            // Run the language-choosing process until clients
            //have chosen an available language
            //while (!pinCodeDone) {
                inputPinCode = Integer.parseInt(in.readLine());
                System.out.println(inputPinCode);
                //  out.println(true);
            //    if (inputPinCode == 1) {
                  pinCodeDone = true;
              //  }


            fullyValidated = validateUser(inputCardNumber,inputPinCode,users);
            if (fullyValidated) {
            out.println(prompts[4]);
          } else {
            out.println(prompts[3]);
          }
            System.out.println(fullyValidated);


            out.println(fullyValidated);

          }



          System.out.println("worked!!");

          out.println(prompts[6]);

          //  out.println(1);
            inputLine = in.readLine();

            int choise = Integer.parseInt(inputLine);


            while (choise != 4) {
                int deposit = 1;
                switch (choise) {
                case 2:
                    deposit = -1;
                case 3:
                    out.println("Enter amount: ");
                    inputLine = readLine();
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

            // saveData(currentUser, Integer.toString(balance));

            out.println("Good Bye");
            out.close();
            in.close();
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
