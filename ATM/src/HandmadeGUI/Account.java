package HandmadeGUI;

public class Account {

    private Controller controller;
    private int id;
    private String number;
    private String name;
    private long balance;
    private int count;


    public Account(Controller controller, int id, String number, String name, long balance, int count) {
        this.controller = controller;
        this.id = id;
        this.number = number;
        this.name = name;
        this.balance = balance;
        this.count = count;
    }


    public boolean withdraw(long amount) {
        System.out.println("Insert code to withdraw Ѣ" + amount);
        if (balance - amount > 0.0) {
            balance = balance - amount;
            return true;
        }
        System.out.println("Could not withdraw; insufficient balance.");
        return false;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getNumber() {
        return number;
    }


    public void setNumber(String number) {
        this.number = number;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public long getBalance() {
        return balance;
    }


    public void setBalance(long balance) {
        this.balance = balance;
    }


    @Override
    public String toString() {
        return count + ": " + name;
    }
}
