package sqlproject;
import java.sql.SQLException;
import java.util.*;

/**
 * The ApplicationHelper class provides methods to handle user tasks and display options in the SuperTrio Store Database System.
 * It acts as an intermediary between the main application and specific service classes.
 */
public class ApplicationHelper {
    private static final Scanner scan = new Scanner(System.in);
    private RemoveServices removeServices;
    private DisplayServices displayServices;
    private UpdateServices updateServices;
    private AddServices addServices;

    /**
     * Constructor for the ApplicationHelper class.
     *
     * @param rs The RemoveServices instance.
     * @param ds The DisplayServices instance.
     * @param us The UpdateServices instance.
     * @param as The AddServices instance.
     */
    public ApplicationHelper(RemoveServices rs, DisplayServices ds, UpdateServices us, AddServices as){
        this.removeServices = rs;
        this.displayServices = ds;
        this.updateServices = us;
        this.addServices =as;
    }

    
    /**
     * Handles the user's selected task based on the provided userChoiceTask.
     *
     * @param userChoiceTask            The user's selected task.
     * @throws ClassNotFoundException   If the class definition for a service is not found.
     * @throws SQLException             If a database access error occurs.
     */
    public void HandleUserTask (int userChoiceTask) throws ClassNotFoundException, SQLException{
        switch (userChoiceTask) {
            case 1:
                System.out.println("SELECTED ADD ");
                handleOperationForAdd();
                break;
            case 2:
                System.out.println("SELECTED UPDATE ");
                handleOperationForUpdate();
                break;
            case 3:
                System.out.println("SELECTED DELETE");
                this.handleOperationForRemove();
                break;
            case 4:
                System.out.println("SELECTED VIEW(DISPLAY)");
                handleOperationForDisplay();
                break;
            default:
                System.out.println("Invalid Choice For Task. Please Enter 1, 2,3 Or 4.");
        }
    }

    /**
     * Handles the add operations based on the user's input.
     *
     * @throws ClassNotFoundException  If the class definition for a service is not found.
     * @throws SQLException            If a database access error occurs.
     */
    public void handleOperationForAdd() throws ClassNotFoundException, SQLException {
        displayAdd();
        int userChoice = 0;
        try {
            userChoice = scan.nextInt();
            if(!(userChoice > 0 && userChoice <= 5)){
                throw new InputMismatchException();
            }
        }
        catch (InputMismatchException e){
            // If the user enters something that is not a integer
            System.out.println("Invalid input. Please enter a number between 1 and 5:");
            // Consume the invalid input to avoid an infinite loop
            scan.nextLine();
        }

        switch (userChoice) {
            case 1:
                System.out.println("SELECTED: ADD A NEW PRODUCT");
                this.addServices.addANewProduct();
                break;
            case 2:
                System.out.println("SELECTED: ADD A PRODUCT TO A WAREHOUSE");
                this.addServices.addWarehouseAvail();
                break;
            case 3:
                System.out.println("SELECTED: ADD A REVIEW");
                this.addServices.addReview();
                break;
            case 4:
                System.out.println("SELECTED: PLACE AN ORDER FOR A NEW CUSTOMER");
                this.addServices.newCustomer();
                break;
            case 5:
                System.out.println("SELECTED: PLACE AN ORDER FOR AN EXISTING CUSTOMER");
                this.addServices.existingCustomer();
                break;
            default:
                System.out.println("Invalid choice for table.");
        }
    }

    /**
     * Handles the update operations based on the user's input.
     */
    public void handleOperationForUpdate() {
        displayUpdates();
        int userChoice = 0;
        try {
            userChoice = scan.nextInt();
                if(userChoice<1 || userChoice>4){
                    throw new InputMismatchException();
                }
            }
            catch (InputMismatchException e){
                System.out.println("Invalid input. Please enter a number between 1 and 4:");
                scan.nextLine();
            }

        switch (userChoice) {
            
            case 1:
                System.out.println("Updating Product..");
                this.updateServices.updateProduct();
                break;
            case 2:
                System.out.println("Updating Inventory..");
                this.updateServices.updateIventory();
                break;
            case 3:
                System.out.println("Updating Review Description...");
                this.updateServices.updateReviewDescription();
                break;
            case 4:
                System.out.println("Flagging Review...");
                this.updateServices.FlagReview();
                break;
            default:
                System.out.println("Invalid choice for table. Please enter a number between 1 - 7=4.");
        }
    }

    /**
     * Handles the display operations based on the user's input.
     */
    public void handleOperationForDisplay() {
        displayDisplay();

        int userChoice = 0;
        try {
           userChoice = scan.nextInt();
           if(userChoice<1 || userChoice>6){
                throw new InputMismatchException();
            }
        }
        catch (InputMismatchException e){
            System.out.println("Invalid input. Please enter a number between 1 and 6:");
            scan.nextLine();
        }

        switch (userChoice) {
            case 1:
                System.out.println("SELECTED PRODUCT BY CATEGORY");
                this.displayServices.showProductByCategory();
                break;
            case 2:
                System.out.println("SELECTED Inventory");
                this.displayServices.ListInventory();
                break;
            case 3:
                System.out.println("Orders by Customer");
                this.displayServices.DisplayOrdersbyCustomers();
                break;
            case 4:
                System.out.println("Audit History ");
                this.displayServices.DisplayAuditTable();
                break;
            case 5:
                System.out.println("Flagged Customers ");
                this.displayServices.showFlaggedCustomer();
                this.displayServices.showFlagReviews();
                break;
            case 6:
                System.out.println("Average Review Score");
                this.displayServices.showReviewScore();
                break;
            default:
                System.out.println("Invalid choice for table. Please enter a number between 1 - 5 .");
        }
    }

    /**
     * Handles the remove operations based on the user's input.
     */
    public void handleOperationForRemove() {
        displayRemove();

        int userChoice = 0;
        try {
            userChoice = scan.nextInt();
            if(userChoice<1 || userChoice>5){
                throw new InputMismatchException();
            }
        }
        catch (InputMismatchException e){
            System.out.println("Invalid input. Please enter a number between 1 and 5:");
            scan.nextLine();
        }

        switch (userChoice) {
            case 1:
                System.out.println("SELECTED REMOVING FOR PRODUCT");
                this.removeServices.removeProduct();
                break;
            case 2:
                System.out.println("SELECTED REMOVING FOR CUSTOMER");
                this.removeServices.removeCustomer();
                break;
            case 3:
                System.out.println("SELECTED REMOVING FOR REVIEW");
                this.removeServices.removeReview();
                break;
            case 4:
                System.out.println("SELECTED REMOVING FOR ORDER");
                this.removeServices.removeOrder();
                break;
            case 5:
                System.out.println("SELECTED REMOVING FOR WAREHOUSE");
                this.removeServices.removeWarehouse();
                break;
            default:
                System.out.println("Invalid Input.");
        }
    }

    /**
     * Displays the main operations menu for the user.
     *
     * @return The user's selected task.
     */
    public int getUserTask() {
        
        displayOperations();
        int taskToday = 0;
        while(true){
            try {
                System.out.println("Enter the number:");
                taskToday=scan.nextInt();
                if (!(taskToday >= 0 && taskToday <= 4)) {
                    throw new InputMismatchException();
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter 0, 1, 2, 3, or 4.");
                scan.nextLine();
            }
    }

        return taskToday;
    }
 
    /**
     * Displays the main operations menu for the user.
     */
    public static void displayOperations(){
        System.out.println("To Exit the system Press 0");
        System.out.println("To Add Press 1");
        System.out.println("To Update Press 2");
        System.out.println("To Delete press 3");
        System.out.println("To View Press 4 ");
    }

     /**
     * Displays the menu for displaying various information.
     */
    public static void displayDisplay(){
        System.out.println("To Display The Products By Category Press 1");
        System.out.println("To Display The Inventory Press 2");
        System.out.println("To Display Orders by Customer Press 3");
        System.out.println("To Display The Audit History Press 4 ");
        System.out.println("To Display Flagged Customers Press 5 ");
        System.out.println("To Get a Porduct Review Average Press 6");
    }

     /**
     * Displays the menu for updating information.
     */
    public static void displayUpdates(){
        System.out.println("To Update a product press 1");
        System.out.println("To Update an inventory quantity press 2");
        System.out.println("To Update a review description press 3");
        System.out.println("To Flag a desired review press 4 ");
    }
    
    /**
     * Displays the menu for adding new information.
     */
    public static void displayAdd(){
        System.out.println("To add a new product, press 1");
        System.out.println("To add a product and the quantity to a warehouse, press 2");
        System.out.println("To add a review of a product, press 3");
        System.out.println("To place and order for new customer, press 4 ");
        System.out.println("To place and order for an existing customer, press 5 ");
    }

    /**
     * Displays the menu for removing information.
     */
    public static void displayRemove(){
        System.out.println("To Delete An Product Press 1");
        System.out.println("To Delete A Customer Press 2");
        System.out.println("To Delete A Review Press 3");
        System.out.println("To Delete An Order Press 4");
        System.out.println("To Delete A Warehouse Press 5");
    }

    /**
     * Closes all services.
     *
     * @param removeServices  The RemoveServices instance.
     * @param displayServices The DisplayServices instance.
     * @param updateServices  The UpdateServices instance.
     * @param addServices     The AddServices instance.
     */
    public void closeAllServices() {
        this.removeServices.close();
        this.displayServices.close();
        this.updateServices.close();
        this.addServices.close();
    }
}
