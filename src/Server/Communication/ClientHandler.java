package Server.Communication;

import Server.DB.DataBase;
import Server.Game.GameRecord;
import Server.Server;
import Server.User.User;
import javafx.scene.chart.XYChart;

import java.io.DataInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    public ObjectInputStream in = null;
    public ObjectOutputStream out = null;
    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {

        try {
            in = new ObjectInputStream(clientSocket.getInputStream());
            out = new ObjectOutputStream(clientSocket.getOutputStream());
        }catch (Exception e){

        }
        String request;
        try {
            while (true) {
                request = in.readUTF();
                if (request.startsWith("sign")) {
                    String[] user=request.split(" ");
                    if(DataBase.getInstance().find(user[1])!=null){
                        out.writeObject(null);
                    }
                    else{
                        User newuser=new User(user[1]);
                        newuser.password=user[2];
                        DataBase.getInstance().addUser(newuser);
                        out.writeObject(newuser);
                        Server.online.add(newuser);
                        DataBase.getInstance().SaveClients();
                        Server.map.put(user[1],this);
                    }
                }
                else if(request.startsWith("log")){
                    String[] login=request.split(" ");
                    User user=DataBase.getInstance().find(login[1]);
                    if(user.password.equals(login[2])){
                        out.writeObject(user);
                        Server.online.add(user);
                        Server.map.put(login[1],this);
                    }
                    else{
                        out.writeObject(new User(null));
                    }
                }
                else if(request.startsWith("edit")){
                    String[] edit=request.split(" ");
                    User user= DataBase.getInstance().find(edit[1]);
                    user.password=edit[2];
                    DataBase.getInstance().SaveClients();
                }
                else if(request.startsWith("delete")){
                    String[] delete=request.split(" ");
                    DataBase.getInstance().deleteUser(new User(delete[1]));
                    Server.online.remove(new User(delete[1]));
                    Server.map.remove(delete[1]);
                    DataBase.getInstance().SaveClients();

                }
                else if(request.startsWith("leaderboard")){
                    Comparator<User> leader=new Comparator<User>() {
                        @Override
                        public int compare(User o1, User o2) {
                            return o2.wins-o1.wins;
                        }
                    };
                    Collections.sort(DataBase.getInstance().getusers(),leader);
                    out.writeObject(DataBase.getInstance().getusers());
                }
                else if(request.startsWith("out")){
                    String[] signout=request.split(" ");
                    Server.online.remove(new User(signout[1]));
                    Server.map.remove(signout[1]);
                }
                else if(request.startsWith("online")){
                    out.writeObject(Server.online);
                }
                else if(request.startsWith("block")){
                    String[] block=request.split(" ");
                    User user= DataBase.getInstance().find(block[1]);
                    user.blocked.add(new User(block[2]));
                }
                else if(request.startsWith("challenge")){
                    String[] challenge=request.split(" ");
                    Server.map.get(challenge[2]).out.writeUTF("challenge "+challenge[1]);


                }
                else if(request.startsWith("unblock")){
                    String [] unblock=request.split(" ");
                    DataBase.getInstance().find(unblock[1]).blocked.remove(new User(unblock[2]));
                }
                else if(request.startsWith("chat")){
                    String[] parsed=request.split(" ");
                    String fill=parsed[3];
                    for (int i = 4; i <parsed.length ; i++) {
                        fill=fill+" "+parsed[i];
                    }
                    Server.map.get(parsed[2]).out.writeUTF("chat "+ parsed[1] +" "+ fill);
                }
                else if(request.startsWith("resign")){
                    String parsed[]=request.split(" ");
                    DataBase.getInstance().find(parsed[1]).losses++;
                    DataBase.getInstance().find(parsed[2]).wins++;
                    DataBase.getInstance().SaveClients();
                    Server.map.get(parsed[2]).out.writeUTF("resign");
                    Object o=null;
                    while(! (o instanceof GameRecord)){
                        o=in.readObject();
                    }
                    DataBase.getInstance().addGame((GameRecord)o);
                    DataBase.getInstance().SaveGames();
                }
            }
        }catch (Exception e){}

    }
}
