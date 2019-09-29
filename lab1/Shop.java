public class Shop {
    public String name;
    public String info;
    public String address;
    public Categories categories = new Categories();
    public DatabaseOfUsers databaseOfUsers = new DatabaseOfUsers();

    public void addCategory(String name) {
        boolean result = categories.addCategory(name);
        System.out.println("[ " + name + " ] Добавление категории: " + result);
    }

    public void getCategories() {
        categories.getCategories();
    }

    public boolean addItemToCategory(String category, String info, Double cost) {
        try {
            for (int i = 0; i < categories.categories.size(); i++) {
                if (categories.categories.get(i).name.equals(category)) {
                    categories.categories.get(i).addItem(info, cost);
                    System.out.println("[ " + info + ": $" + cost + " ] Добавление товара выполнено.");
                    return true;
                }
            }
            addCategory(category);
            addItemToCategory(category, info, cost);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void getgetCategoriesAndItems() {
        categories.getgetCategoriesAndItems();
    }

    public void addUser(String login, String password, String name) {
        boolean result = databaseOfUsers.addUser(login, password, name);
        System.out.println("[ " + login + " ] Добавление пользователя: " + result);
    }

    public void removeUser(String login, String password) {
        boolean result = databaseOfUsers.removeUser(login, password);
        System.out.println("[ " + login + " ] Удаление пользователя: " + result);
    }

    public void getUsers() {
        databaseOfUsers.getUsers();
    }
 }
