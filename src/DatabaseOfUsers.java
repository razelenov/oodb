import java.util.ArrayList;

public class DatabaseOfUsers {
    private ArrayList<User> users = new ArrayList<>();

    public boolean addUser(String login, String password, String name) {
        try {
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getLogin().equals(login)) {
                    return false;
                }
            }
            users.add(new User(login, password, name));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean removeUser(String login, String password) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getLogin().equals(login) && users.get(i).isPasswordCorrect(password) == true) {
                users.remove(i);
                return true;
            }
        }
        return false;
    }

    public void getUsers() {
        for (int i = 0; i < users.size(); i++) {
            users.get(i).getInfo();
        }
    }
}
