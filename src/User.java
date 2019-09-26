public class User {
    private String login;
    private String password;
    private String name;

    public User(String login, String password, String name) {
        this.login = login;
        this.password = password;
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public boolean isPasswordCorrect(String password) {
        if (this.password.equals(password)) {
            return true;
        }
        return false;
    }

    public void getInfo() {
        System.out.println(login + " | " + name);
    }
}
