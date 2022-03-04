package Server.DB;

import Server.Game.GameRecord;
import Server.User.User;

import javax.swing.text.html.ListView;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DataBase {

    private final static DataBase instance = new DataBase();
    private List<GameRecord> Games;
    private List<User> AllUsers;

    public User find(String name){
        for (int i = 0; i < AllUsers.size(); i++) {
            if(AllUsers.get(i).name.equals(name))
                return AllUsers.get(i);
        }
        return null;
    }

    private DataBase() {
        Games = new CopyOnWriteArrayList<>();
        AllUsers = new CopyOnWriteArrayList<>();
    }

    public static DataBase getInstance() {
        return instance;
    }


    public void SaveClients() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(Paths.get("src/Server/DB/Clients.txt")))) {

            objectOutputStream.writeInt(AllUsers.size());
            for (User allUser : AllUsers) {
                objectOutputStream.writeObject(allUser);
            }
        } catch (IOException e) {
        }
    }

    public void ReadClients() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(Paths.get("src/Server/DB/Clients.txt")))) {
            int size = objectInputStream.readInt();
            AllUsers.clear();
            for (int i = 0; i < size; i++) {
                AllUsers.add((User) objectInputStream.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
        }
    }



    public void SaveGames() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(Paths.get("src/Server/DB/Games.txt")))) {

            objectOutputStream.writeInt(Games.size());
            for (GameRecord allgames : Games) {
                objectOutputStream.writeObject(allgames);
            }
        } catch (IOException e) {
        }
    }

    public void ReadGames() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(Paths.get("src/Server/DB/Games.txt")))) {
            int size = objectInputStream.readInt();
            Games.clear();
            for (int i = 0; i < size; i++) {
                Games.add((GameRecord) objectInputStream.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
        }
    }

    public void addUser( User User) {
        AllUsers.add(User);
    }
    public  void addGame(GameRecord gameRecord){
        Games.add(gameRecord);
    }
    public void deleteUser(User user){
        for (int i = 0; i < AllUsers.size(); i++) {
            if(AllUsers.get(i).equals(user))
                AllUsers.remove(i);
        }
    }
    public List<User> getusers(){
        return AllUsers;
    }



}
