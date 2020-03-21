import domain.*;

public class Main {

    public static void main(String[] args) {
        EntityManagerClass entityManagerClass = new EntityManagerClass();
        if (EntityManagerFactory.isDatabaseFull()) {
            Bank bank = new Bank();
            bank.setName("Bank");
            bank.setOwner("Example");
            bank.setId((long) 23);
            Bank bank1 = entityManagerClass.find(Bank.class, 1);
        }
    }
}
