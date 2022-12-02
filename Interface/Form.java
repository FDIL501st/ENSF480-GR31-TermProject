package Interface;
public abstract class Form {
    protected static boolean loginStatus = false; //indicate if user has logged in
    protected static boolean ticketFormOpened = false;
    public abstract void run();
}
