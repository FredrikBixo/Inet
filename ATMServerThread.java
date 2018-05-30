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
    // declaring variables  
    String input;
    private String[] langagues;
    private String[] users;
    private List<String> lines;
    private List<String> userInfo;
    private Boolean validated;
    private int balance;
    private int password;
    private String[] prompts;

    public ATMServerThread(Socket socket) {
        super("ATMServerThread");
        this.socket = socket;
    }

    private String readLine() throws IOException {
        String str = in.readLine();
        return str;
    }
    /**
    function name: getLanguage
    input: integer (from user)
    return type: Arraylist
    **/
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


    /**
    function name: validateUser
    input: int, int, Array
    return type: Boolean
    **/
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

    /**
    function name: getUsers
    input: N/A
    Return type: Array
    **/
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


    /**
    function name: saveSaldoTouser
    input: int, int
    return type: N/A
    **/
    private void saveSaldoTouser(String user, int balance) {
      users[this.balance] = Integer.toString(balance);
      String joined2 = String.join("\n", users);

    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter("UserInfo.txt"));
      writer.write(joined2);
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

            out.println(0); //sends language
            input = in.readLine();
            //input representing chosen language
            int lang = Integer.parseInt(input);
            this.prompts = getLanguage(lang); //gets chosen language
            int value;
            users = getUsers();
           
            int inputCardNumber = 0;
            int inputPinCode = 0;
            //checks if validated
            Boolean fullyValidated = false;
            
           
            // Run the language-choosing process until clients have chosen an available language
            while (!fullyValidated) {
                //ask to enter cardNr
                out.println(0);
                boolean cardNumberDone = false;
                //parsing user input
                inputCardNumber = Integer.parseInt(in.readLine());
                System.out.println(inputCardNumber);
                cardNumberDone = true;
                //ask to enter pinCode
                out.println(2);
                boolean pinCodeDone = false;
                //parsing user input
                inputPinCode = Integer.parseInt(in.readLine());
                System.out.println(inputPinCode);
                pinCodeDone = true;
            // uses the parsed user input as arguments in the fullyValidated function to check if cardNr and pinCode matches   
            fullyValidated = validateUser(inputCardNumber,inputPinCode,users);
            if (fullyValidated) {
            out.println(4);
            // user logged in, get balance;
            balance = Integer.parseInt(users[this.balance]);
          } else {
            out.println(3);
          }
            System.out.println(fullyValidated);
            out.println(fullyValidated);
          }
          //sends integer client to make it print the menu
          out.println(6);
          inputLine = in.readLine();
          //waiting for user input
          int choise = Integer.parseInt(inputLine);

          int maxOptions = 5;
          while (choise != 5) {
                int deposit = 1;
                switch (choise) {
                case 2:
                    deposit = -1;
                case 3:
                    //sends integer to tell the server to print enter amount
                    out.println(8);
                    inputLine = readLine();
                    value = Integer.parseInt(inputLine);
                    // logic in order to deposit money
                    balance += deposit * value;
                case 1:
                    //tells the server to print balance and SEK as tail
                    out.println(balance);
                    out.println(10);
                    out.println(6);
                    //waiting for next input
                    inputLine=readLine();
                    choise = Integer.parseInt(inputLine);
                    break;
                  case 4:
                      inputLine=readLine();
                      System.out.println(inputLine);
                      choise = Integer.parseInt(inputLine);
                      break;
                case 5:
                    break;
                default:
                    break;
                }
            }
            //save balance
            saveSaldoTouser("",balance);

            out.println("Good Bye");
            out.close();
            in.close();
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
