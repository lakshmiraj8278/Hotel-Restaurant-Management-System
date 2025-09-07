import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
class ConsoleColors {
    public static final String RESET = "\033[0m";
    public static final String GREEN = "\033[0;32m";
    public static final String RED = "\033[0;31m";
    public static final String YELLOW = "\033[0;93m";
    public static final String BLUE = "\033[0;34m";
    public static final String CYAN = "\033[0;96m";
}
// Interface for common menu functionality
interface Menu {
    void displayMenu();
}

// Abstract class for user accounts
abstract class Account {
    protected String username;
    protected String password;
    protected String email;
    protected String mobile;

    public Account(String username, String password, String email, String mobile) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.mobile = mobile;
    }

    public abstract void accountType();
}

// Normal Account class
class NormalAccount extends Account {
    public NormalAccount(String username, String password, String email, String mobile) {
        super(username, password, email, mobile);
    }

    @Override
    public void accountType() {
        System.out.println(ConsoleColors.GREEN+"Normal Account Created Successfully!"+ConsoleColors.RESET);
    }
}

// Premium Account class
class PremiumAccount extends Account {
    public PremiumAccount(String username, String password, String email, String mobile) {
        super(username, password, email, mobile);
    }

    @Override
    public void accountType() {
        System.out.println(ConsoleColors.GREEN+"Premium Account Created Successfully!"+ConsoleColors.RESET);
    }
}



// Main class for the system
public class HotelRestaurantSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<String, Account> accounts = new HashMap<>(); // To store accounts
    private static final Map<String, Integer> cart = new HashMap<>(); // To store cart items

    private static Account loggedInAccount;
// Static account details
private static final int SBI_PIN = 1234;
private static final int HDFC_PIN = 5678;
private static final int UNION_PIN = 9012;

// Account balances for SBI, HDFC, and Union Bank
private static final int[] balances = {1000, 20000, 1500};

    public static void main(String[] args) {
    // Adding a static Normal user for login
    accounts.put("admin", new NormalAccount("admin", "Admin@123", "admin@example.com", "9876543210"));

    // Adding a static Premium user for login
    accounts.put("premium", new PremiumAccount("premium", "Premium@123", "premium@example.com", "9123456789"));

    while (true) {
        displayMainMenu();
        int option = getValidInput(4);

        switch (option) {
            case 1:
                createAccount();
                break;
            case 2:
                login();
                break;
            case 3:
                forgotPassword();
                break;
            case 4:
                System.out.println(ConsoleColors.GREEN+"Thank you for using the system. Goodbye!"+ConsoleColors.RESET);
                return;
        }
    }
}
    private static void displayMainMenu() {
        System.out.println(ConsoleColors.GREEN + "######      ##       ##             ######      ##             ######      ##     ######   ######   ##  ##           ######      ##    " + ConsoleColors.RESET);
System.out.println(ConsoleColors.GREEN + " ##  ##    ####     ####             " + ConsoleColors.RED + "##  ##    " + ConsoleColors.GREEN + "####             ##  ##    ####    # ## #   # ## #   ##  ##            " + ConsoleColors.RED + "##  ##    ####   " + ConsoleColors.RESET);
System.out.println(ConsoleColors.GREEN + " ##  ##    ####     ####             " + ConsoleColors.RED + "##  ##    " + ConsoleColors.GREEN + "####             ##  ##    ####      ##       ##     ##  ##            " + ConsoleColors.RED + "##  ##    ####   " + ConsoleColors.RESET);
System.out.println(ConsoleColors.GREEN + " #####    ##  ##   ##  ##            " + ConsoleColors.RED + "#####    " + ConsoleColors.GREEN + "##  ##            #####    ##  ##     ##       ##     ##  ##            " + ConsoleColors.RED + "#####    ##  ##  " + ConsoleColors.RESET);
System.out.println(ConsoleColors.GREEN + " ## ##    ######   ######            " + ConsoleColors.RED + "## ##    " + ConsoleColors.GREEN + "######            ##  ##   ######     ##       ##     ##  ##            " + ConsoleColors.RED + "## ##    ######  " + ConsoleColors.RESET);
System.out.println(ConsoleColors.GREEN + " ##  ##  ##    ## ##    ##           " + ConsoleColors.RED + "##  ##  " + ConsoleColors.GREEN + "##    ##           ##  ##  ##    ##    ##       ##     ##  ##            " + ConsoleColors.RED + "##  ##  ##    ## " + ConsoleColors.RESET);
System.out.println(ConsoleColors.GREEN + " ##   ## ##    ## ##    ##           " + ConsoleColors.RED + "##   ## " + ConsoleColors.GREEN + "##    ##          ######   ##    ##   ####     ####     #####            " + ConsoleColors.RED + "##   ## ##    ## " + ConsoleColors.RESET);
	System.out.println();
        System.out.println(ConsoleColors.YELLOW+"\n--------Welcome to the Hotel Restaurant System--------"+ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW+"1. Create New Account"+ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW+"2. Login"+ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW+"3. Forgot Password"+ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW+"4. Exit"+ConsoleColors.RESET);
        System.out.print(ConsoleColors.BLUE+"Enter your choice: "+ConsoleColors.RESET);
    }

    private static int getValidInput(int maxOption) {
        int choice;
        while (true) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= maxOption) break;
                System.out.print(ConsoleColors.RED+"Invalid choice. Please try again: "+ConsoleColors.RESET);
            } catch (NumberFormatException e) {
                System.out.print(ConsoleColors.RED+"Invalid input. Please enter a number: "+ConsoleColors.RESET);
            }
        }
        return choice;
    }

    private static void createAccount() {
    while (true) {
        System.out.println("\n" + ConsoleColors.YELLOW + "Select Account Type:" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW + "1. Normal" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW + "2. Premium" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW + "3. Back to Main Menu" + ConsoleColors.RESET);
        System.out.print(ConsoleColors.BLUE + "Enter your choice: " + ConsoleColors.RESET);
        int choice = getValidInput(3);

        if (choice == 3) return;

        String username = null, password = null, email = null, mobile = null;
        boolean isValid = false;

        while (!isValid) {
            if (username == null) {
                System.out.print(ConsoleColors.GREEN + "Enter Username: " + ConsoleColors.RESET);
                username = scanner.nextLine();
                if (username.length() < 4 || username.length() > 15) {
                    System.out.println(ConsoleColors.RED + "Username must be between 4 and 15 characters." + ConsoleColors.RESET);
                    username = null;
                    continue;
                }
            }
if (password == null) {
    System.out.print(ConsoleColors.GREEN + "Enter Password (1 capital, 1 special character, 8-15 characters): " + ConsoleColors.RESET);
    password = scanner.nextLine();

    if (password.length() < 8 || password.length() > 15 || 
        !password.matches(".*[A-Z].*") || 
        !password.matches(".*[!@#$%^&*].*")) {
        System.out.println(ConsoleColors.RED + "Password must contain at least one uppercase letter, one special character, and be 8-15 characters long." + ConsoleColors.RESET);
        password = null;
        continue;
    }
}


            if (email == null) {
    System.out.print(ConsoleColors.GREEN + "Enter Email: " + ConsoleColors.RESET);
    email = scanner.nextLine();
    if (!email.contains("@") || !email.contains(".") || email.indexOf('@') < 3) {
        System.out.println(ConsoleColors.RED + "Invalid email format. Must have at least 3 characters before '@'." + ConsoleColors.RESET);
        email = null;
        continue;
    }
}


            if (mobile == null) {
                System.out.print(ConsoleColors.GREEN + "Enter Mobile (6-9 starts, 10 digits): " + ConsoleColors.RESET);
                mobile = scanner.nextLine();
                if (!mobile.matches("[6-9][0-9]{9}")) {
                    System.out.println(ConsoleColors.RED + "Mobile number must start with 6-9 and be 10 digits." + ConsoleColors.RESET);
                    mobile = null;
                    continue;
                }
            }

            isValid = true;
        }

        Account account;
        if (choice == 1) {
            account = new NormalAccount(username, password, email, mobile);
        } else {
            account = new PremiumAccount(username, password, email, mobile);
        }

        accounts.put(username, account);
        System.out.println(ConsoleColors.GREEN + "Account created successfully!" + ConsoleColors.RESET);
        account.accountType();
        return;
    }
}

     private static void forgotPassword() {
    System.out.print(ConsoleColors.BLUE+"Enter your Username: "+ConsoleColors.RESET);
    String username = scanner.nextLine();

    Account account = accounts.get(username);
    if (account != null) {
        int attempts = 0;
        boolean isMobileVerified = false;

        while (attempts < 3) {
            System.out.print(ConsoleColors.BLUE+"Enter your registered Mobile Number: "+ConsoleColors.RESET);
            String mobile = scanner.nextLine();

            if (account.mobile.equals(mobile)) {
                isMobileVerified = true;
                break;
            } else {
                attempts++;
                System.out.println(ConsoleColors.RED+"Mobile number does not match. Attempts left: " + (3 - attempts)+ConsoleColors.RESET);
            }
        }

        if (isMobileVerified) {
            attempts = 0;
            boolean isOtpVerified = false;
            String otp = generateOTP();
            System.out.println(ConsoleColors.BLUE+"OTP sent to your mobile: " + otp+ConsoleColors.RESET);

            while (attempts < 3) {
                System.out.print(ConsoleColors.GREEN+"Enter OTP: "+ConsoleColors.RESET);
                String enteredOtp = scanner.nextLine();

                if (otp.equals(enteredOtp)) {
                    isOtpVerified = true;
                    break;
                } else {
                    attempts++;
                    System.out.println(ConsoleColors.RED+"Invalid OTP. Attempts left: " + (3 - attempts)+ConsoleColors.RESET);
                }
            }

            if (isOtpVerified) {
                System.out.print(ConsoleColors.BLUE+"Enter new Password: "+ConsoleColors.RESET);
                account.password = scanner.nextLine();
                System.out.println(ConsoleColors.GREEN+"Password reset successful!"+ConsoleColors.RESET);
            } else {
                System.out.println(ConsoleColors.RED+"Failed to verify OTP after 3 attempts. Please try again later."+ConsoleColors.RESET);
            }
        } else {
            System.out.println(ConsoleColors.RED+"Failed to verify mobile number after 3 attempts. Please try again later."+ConsoleColors.RESET);
        }
    } else {
        System.out.println(ConsoleColors.RED+"Username not found."+ConsoleColors.RESET);
    }
}

    private static String generateOTP() {
        Random random = new Random();
        return String.format("%04d", random.nextInt(10000));
    }


    private static void login() {
    System.out.println("\nSelect Login Type:");
    System.out.println(ConsoleColors.YELLOW + "1. Normal Login" + ConsoleColors.RESET);
    System.out.println(ConsoleColors.YELLOW + "2. Premium Login" + ConsoleColors.RESET);
    System.out.println(ConsoleColors.YELLOW + "3. Back to Main Menu" + ConsoleColors.RESET);
    System.out.print(ConsoleColors.BLUE + "Enter your choice: " + ConsoleColors.RESET);
    int choice = getValidInput(3);

    if (choice == 3) return;

    int attempts = 3;
    String username;
    Account account = null; // Initialize account to null

    while (attempts > 0) {
        System.out.print(ConsoleColors.BLUE + "Enter Username: " + ConsoleColors.RESET);
        username = scanner.nextLine();
        account = accounts.get(username);

        if (account == null) {
            attempts--;
            System.out.println(ConsoleColors.RED + "Username not found. Attempts remaining: " + attempts + ConsoleColors.RESET);
            if (attempts > 0) {
                System.out.println("Please try again.");
            } else {
                System.out.println(ConsoleColors.RED + "Too many failed attempts. Login denied." + ConsoleColors.RESET);
                return;
            }
        } else {
            break; // Exit loop if the username is found
        }
    }

    // Check if account is initialized before using it
    if (account != null) {
        boolean isNormalAccount = account instanceof NormalAccount;
        boolean isPremiumAccount = account instanceof PremiumAccount;

        // Verify account type
        if ((choice == 1 && isNormalAccount) || (choice == 2 && isPremiumAccount)) {
            attempts = 3; // Reset attempts for password input

            while (attempts > 0) {
                System.out.print(ConsoleColors.GREEN + "Enter Password: " + ConsoleColors.RESET);
                String password = scanner.nextLine();

                if (account.password.equals(password)) {
                    System.out.println(ConsoleColors.GREEN + "Login Successful!" + ConsoleColors.RESET);

                    // Set the logged-in account
                    loggedInAccount = account;
                    loggedInMenu(); // Proceed to the menu for logged-in users
                    return;
                } else {
                    attempts--;
                    System.out.println(ConsoleColors.RED + "Incorrect password. Attempts remaining: " + attempts + ConsoleColors.RESET);
                }
            }
            System.out.println(ConsoleColors.RED + "Too many failed attempts. Login denied." + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.RED + "Account type mismatch. Login failed." + ConsoleColors.RESET);
        }
    } else {
        // If account is null after loop ends, handle appropriately
        System.out.println(ConsoleColors.RED + "An unexpected error occurred." + ConsoleColors.RESET);
    }
}






    private static void loggedInMenu() {
        
        while (true) {
            System.out.println(ConsoleColors.YELLOW+"\n1. User Info"+ConsoleColors.RESET);
            System.out.println(ConsoleColors.YELLOW+"2. Veg Menu"+ConsoleColors.RESET);
            System.out.println(ConsoleColors.YELLOW+"3. Non-Veg Menu"+ConsoleColors.RESET);
            System.out.println(ConsoleColors.YELLOW+"4. Starters"+ConsoleColors.RESET);
            System.out.println(ConsoleColors.YELLOW+"5. Drinks"+ConsoleColors.RESET);
            System.out.println(ConsoleColors.YELLOW+"6. Ice Creams"+ConsoleColors.RESET);
            System.out.println(ConsoleColors.YELLOW+"7. Cart"+ConsoleColors.RESET);
            System.out.println(ConsoleColors.YELLOW+"8. Wallet"+ConsoleColors.RESET);
            System.out.println(ConsoleColors.RED+"9. Logout"+ConsoleColors.RESET);
            System.out.print(ConsoleColors.BLUE+"Enter your choice: "+ConsoleColors.RESET);

            int choice = getValidInput(9);

            switch (choice) {
                case 1:
                    displayUserInfo();
                    break;
                case 2:
                    vegMenu();
                    break;
                case 3:
                    nonVegMenu();
                    break;
                case 4:
                    startersMenu();
                    break;
                case 5:
                    drinksMenu();
                    break;
                case 6:
                    iceCreamsMenu();
                    break;
                case 7:
                    viewCart();
                    break;
                case 8:
                    walletMenu();
                    break;
                case 9:
                    System.out.println(ConsoleColors.RED+"Logging out..."+ConsoleColors.RESET);
                    return;
            }
        }
    }
    private static void displayUserInfo() {
    if (loggedInAccount != null) {
        System.out.println(ConsoleColors.YELLOW+"\nUser Info:"+ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW+"Username: "+ConsoleColors.RESET+ ConsoleColors.GREEN+loggedInAccount.username+ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW+"Email: " +ConsoleColors.RESET+ ConsoleColors.GREEN+loggedInAccount.email+ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW+"Mobile: " +ConsoleColors.RESET+ConsoleColors.GREEN+ loggedInAccount.mobile+ConsoleColors.RESET);
    } else {
        System.out.println(ConsoleColors.RED+"No user is logged in."+ConsoleColors.RESET);
    }
}

    private static void walletMenu() {
    while (true) {
        System.out.println(ConsoleColors.YELLOW+"\nWallet Menu:"+ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW+"1. Check Balance"+ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW+"2. Add Money"+ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW+"3. Back to Main Menu"+ConsoleColors.RESET);
        System.out.print(ConsoleColors.BLUE+"Enter your choice: "+ConsoleColors.RESET);
        int choice = getValidInput(3);

        switch (choice) {
            case 1:
                System.out.println(ConsoleColors.YELLOW+"\nSelect Account:"+ConsoleColors.RESET);
                System.out.println(ConsoleColors.YELLOW+"1. SBI"+ConsoleColors.RESET);
                System.out.println(ConsoleColors.YELLOW+"2. HDFC"+ConsoleColors.RESET);
                System.out.println(ConsoleColors.YELLOW+"3. Union Bank"+ConsoleColors.RESET);
                System.out.print(ConsoleColors.BLUE+"Enter your choice: "+ConsoleColors.RESET);
                int accountChoice = getValidInput(3);

                int attempts = 0;
                boolean isPinVerified = false;

                while (attempts < 3) {
                    System.out.print(ConsoleColors.GREEN+"Enter PIN: "+ConsoleColors.RESET);
                    int pin = Integer.parseInt(scanner.nextLine());

                    if (validatePin(accountChoice, pin, SBI_PIN, HDFC_PIN, UNION_PIN)) {
                        System.out.println(ConsoleColors.YELLOW+"Balance: Rs." +ConsoleColors.RESET+ConsoleColors.GREEN+ balances[accountChoice - 1]+ConsoleColors.RESET);
                        isPinVerified = true;
                        break;
                    } else {
                        attempts++;
                        System.out.println(ConsoleColors.RED+"Incorrect PIN. Attempts left: " + (3 - attempts)+ConsoleColors.RESET);
                    }
                }

                if (!isPinVerified) {
                    System.out.println(ConsoleColors.RED+"Failed to verify PIN after 3 attempts. Returning to Wallet Menu."+ConsoleColors.RESET);
                }
                break;

            case 2:
                System.out.println(ConsoleColors.YELLOW+"\nSelect Account:"+ConsoleColors.RESET);
                System.out.println(ConsoleColors.YELLOW+"1. SBI"+ConsoleColors.RESET);
                System.out.println(ConsoleColors.YELLOW+"2. HDFC"+ConsoleColors.RESET);
                System.out.println(ConsoleColors.YELLOW+"3. Union Bank"+ConsoleColors.RESET);
                System.out.print(ConsoleColors.BLUE+"Enter your choice: "+ConsoleColors.RESET);
                accountChoice = getValidInput(3);

                attempts = 0;
                isPinVerified = false;

                while (attempts < 3) {
                    System.out.print(ConsoleColors.GREEN+"Enter PIN: "+ConsoleColors.RESET);
                    int pin = Integer.parseInt(scanner.nextLine());

                    if (validatePin(accountChoice, pin, SBI_PIN, HDFC_PIN, UNION_PIN)) {
                        System.out.print(ConsoleColors.GREEN+"Enter Amount to Add: "+ConsoleColors.RESET);
                        int amount = Integer.parseInt(scanner.nextLine());

                        if (amount > 0) {
                            balances[accountChoice - 1] += amount;
                            System.out.println(ConsoleColors.GREEN+"Amount added successfully."+ConsoleColors.RESET);
                            System.out.println(ConsoleColors.YELLOW+"Updated Balance: Rs." + balances[accountChoice - 1]+ConsoleColors.RESET);
                        } else {
                            System.out.println(ConsoleColors.RED+"Invalid amount. Please enter a positive value."+ConsoleColors.RESET);
                        }
                        isPinVerified = true;
                        break;
                    } else {
                        attempts++;
                        System.out.println(ConsoleColors.RED+"Incorrect PIN. Attempts left: " + (3 - attempts)+ConsoleColors.RESET);
                    }
                }

                if (!isPinVerified) {
                    System.out.println(ConsoleColors.RED+"Failed to verify PIN after 3 attempts. Returning to Wallet Menu."+ConsoleColors.RESET);
                }
                break;

            case 3:
                return; // Exit wallet menu
        }
    }
}


 private static boolean validatePin(int accountChoice, int enteredPin, int sbiPin, int hdfcPin, int unionPin) {
        switch (accountChoice) {
            case 1:
                return enteredPin == sbiPin;//1234
            case 2:
                return enteredPin == hdfcPin;//5678
            case 3:
                return enteredPin == unionPin;//9012
            default:
                return false;
        }
    }

    private static int getValidInputMax(int maxOption) {
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine());
                if (input >= 1 && input <= maxOption) {
                    return input;
                }
                System.out.print(ConsoleColors.RED+"Invalid choice. Enter a number between 1 and " + maxOption + ": "+ConsoleColors.RESET);
            } catch (NumberFormatException e) {
                System.out.print(ConsoleColors.RED+"Invalid input. Please enter a valid number: "+ConsoleColors.RESET);
            }
        }
    }

    private static void vegMenu() {
    Map<String, Integer> vegItems = new LinkedHashMap<>();
    vegItems.put(ConsoleColors.YELLOW+"Paneer Butter Masala"+ConsoleColors.RESET, 200);
    vegItems.put(ConsoleColors.YELLOW+"Veg Biryani"+ConsoleColors.RESET, 150);
    vegItems.put(ConsoleColors.YELLOW+"Dal Tadka"+ConsoleColors.RESET, 120);
    vegItems.put(ConsoleColors.YELLOW+"Mix Veg Curry"+ConsoleColors.RESET, 180);
    vegItems.put(ConsoleColors.YELLOW+"Chole Bhature"+ConsoleColors.RESET, 160);
    vegItems.put(ConsoleColors.YELLOW+"Aloo Paratha"+ConsoleColors.RESET, 80);
    vegItems.put(ConsoleColors.YELLOW+"Matar Paneer"+ConsoleColors.RESET, 190);
    vegItems.put(ConsoleColors.YELLOW+"Kadai Paneer"+ConsoleColors.RESET, 220);
    vegItems.put(ConsoleColors.YELLOW+"Veg Fried Rice"+ConsoleColors.RESET, 130);
    vegItems.put(ConsoleColors.YELLOW+"Palak Paneer"+ConsoleColors.RESET, 210);

    while (true) {
        System.out.println(ConsoleColors.YELLOW+"\nVeg Menu:"+ConsoleColors.RESET);
        int index = 1;
        for (String item : vegItems.keySet()) {
            System.out.println(index + ". " + item + " - Rs." + vegItems.get(item));
            index++;
        }
        System.out.println(index + ConsoleColors.YELLOW+". Back to Food Menu"+ConsoleColors.RESET);
        System.out.print(ConsoleColors.BLUE+"Enter your choice: "+ConsoleColors.RESET);

        int choice = getValidInput(vegItems.size() + 1);

        if (choice == vegItems.size() + 1) return;

        String selectedItem = (String) vegItems.keySet().toArray()[choice - 1];
        System.out.print(ConsoleColors.BLUE+"Enter Quantity for " + selectedItem + ": "+ConsoleColors.RESET);
        int quantity = Integer.parseInt(scanner.nextLine());
        int price = vegItems.get(selectedItem) * quantity;
        cart.put(selectedItem, cart.getOrDefault(selectedItem, 0) + price);
        System.out.println(quantity + " x " + selectedItem + " added to cart. Total: Rs." + price);
        System.out.println(ConsoleColors.YELLOW+"1. Back to Veg Menu"+ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW+"2. Back to Food Menu"+ConsoleColors.RESET);
        System.out.print(ConsoleColors.BLUE+"Enter your choice: "+ConsoleColors.RESET);
        int action = getValidInput(2);

        if (action == 2) return;
    }
}

    private static void nonVegMenu() {
    Map<String, Integer> nonVegItems = new LinkedHashMap<>();
    nonVegItems.put(ConsoleColors.YELLOW+"Chicken Curry"+ConsoleColors.RESET, 250);
    nonVegItems.put(ConsoleColors.YELLOW+"Mutton Biryani"+ConsoleColors.RESET, 300);
    nonVegItems.put(ConsoleColors.YELLOW+"Fish Fry"+ConsoleColors.RESET, 200);
    nonVegItems.put(ConsoleColors.YELLOW+"Chicken Biryani"+ConsoleColors.RESET, 220);
    nonVegItems.put(ConsoleColors.YELLOW+"Prawns Curry"+ConsoleColors.RESET, 270);
    nonVegItems.put(ConsoleColors.YELLOW+"Egg Curry"+ConsoleColors.RESET, 150);
    nonVegItems.put(ConsoleColors.YELLOW+"Grilled Chicken"+ConsoleColors.RESET, 350);
    nonVegItems.put(ConsoleColors.YELLOW+"Mutton Rogan Josh"+ConsoleColors.RESET, 400);
    nonVegItems.put(ConsoleColors.YELLOW+"Fish Curry"+ConsoleColors.RESET, 230);
    nonVegItems.put(ConsoleColors.YELLOW+"Chicken Tikka"+ConsoleColors.RESET, 280);
    nonVegItems.put(ConsoleColors.YELLOW+"Lamb Chops"+ConsoleColors.RESET, 450);
    nonVegItems.put(ConsoleColors.YELLOW+"Butter Chicken"+ConsoleColors.RESET, 300);

    while (true) {
        System.out.println("\nNon-Veg Menu:");
        int index = 1;
        for (String item : nonVegItems.keySet()) {
            System.out.println(index + ". " + item + " - Rs." + nonVegItems.get(item));
            index++;
        }
        System.out.println(index + ". Back to Food Menu");
        System.out.print("Enter your choice: ");

        int choice = getValidInput(nonVegItems.size() + 1);

        if (choice == nonVegItems.size() + 1) return;

        String selectedItem = (String) nonVegItems.keySet().toArray()[choice - 1];
        System.out.print(ConsoleColors.BLUE+"Enter Quantity for " + selectedItem + ": "+ConsoleColors.RESET);
        int quantity = Integer.parseInt(scanner.nextLine());
        int price = nonVegItems.get(selectedItem) * quantity;
        cart.put(selectedItem, cart.getOrDefault(selectedItem, 0) + price);
        System.out.println(quantity + " x " + selectedItem + " added to cart. Total: Rs." + price);

        System.out.println(ConsoleColors.YELLOW+"1. Back to Non-Veg Menu"+ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW+"2. Back to Food Menu"+ConsoleColors.RESET);
        System.out.print(ConsoleColors.BLUE+"Enter your choice: "+ConsoleColors.RESET);
        int action = getValidInput(2);

        if (action == 2) return;
    }
}
private static void startersMenu() {
    Map<String, Integer> startersItems = new LinkedHashMap<>();
    startersItems.put(ConsoleColors.YELLOW + "Chicken 65" + ConsoleColors.RESET, 180);
    startersItems.put(ConsoleColors.YELLOW + "Mutton Kebab" + ConsoleColors.RESET, 250);
    startersItems.put(ConsoleColors.YELLOW + "Fish Fingers" + ConsoleColors.RESET, 200);
    startersItems.put(ConsoleColors.YELLOW + "Prawn Tempura" + ConsoleColors.RESET, 300);
    startersItems.put(ConsoleColors.YELLOW + "Chicken Tikka" + ConsoleColors.RESET, 280);
    startersItems.put(ConsoleColors.YELLOW + "Paneer Tikka" + ConsoleColors.RESET, 220);
    startersItems.put(ConsoleColors.YELLOW + "Veg Spring Rolls" + ConsoleColors.RESET, 150);
    startersItems.put(ConsoleColors.YELLOW + "Crispy Corn" + ConsoleColors.RESET, 170);
    startersItems.put(ConsoleColors.YELLOW + "Chilli Chicken" + ConsoleColors.RESET, 200);
    startersItems.put(ConsoleColors.YELLOW + "Tandoori Chicken" + ConsoleColors.RESET, 350);
    startersItems.put(ConsoleColors.YELLOW + "Mutton Seekh Kebab" + ConsoleColors.RESET, 300);
    startersItems.put(ConsoleColors.YELLOW + "Stuffed Mushrooms" + ConsoleColors.RESET, 240);

    while (true) {
        System.out.println("\nStarters Menu:");
        int index = 1;
        for (String item : startersItems.keySet()) {
            System.out.println(index + ". " + item + " - Rs." + startersItems.get(item));
            index++;
        }
        System.out.println(index + ". Back to Food Menu");
        System.out.print(ConsoleColors.BLUE + "Enter your choice: " + ConsoleColors.RESET);

        int choice = getValidInput(startersItems.size() + 1);

        if (choice == startersItems.size() + 1) return;

        String selectedItem = (String) startersItems.keySet().toArray()[choice - 1];
        System.out.print(ConsoleColors.BLUE + "Enter Quantity for " + selectedItem + ": " + ConsoleColors.RESET);
        int quantity = Integer.parseInt(scanner.nextLine());
        int price = startersItems.get(selectedItem) * quantity;
        cart.put(selectedItem, cart.getOrDefault(selectedItem, 0) + price);
        System.out.println(quantity + " x " + selectedItem + " added to cart. Total: Rs." + price);

        System.out.println(ConsoleColors.YELLOW + "1. Back to Starters Menu" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW + "2. Back to Food Menu" + ConsoleColors.RESET);
        System.out.print(ConsoleColors.BLUE + "Enter your choice: " + ConsoleColors.RESET);
        int action = getValidInput(2);

        if (action == 2) return;
    }
}

private static void drinksMenu() {
    Map<String, Integer> drinksItems = new LinkedHashMap<>();

    // Non-Alcoholic Drinks
    drinksItems.put(ConsoleColors.YELLOW + "Coca-Cola" + ConsoleColors.RESET, 50);
    drinksItems.put(ConsoleColors.YELLOW + "Pepsi" + ConsoleColors.RESET, 50);
    drinksItems.put(ConsoleColors.YELLOW + "Sprite" + ConsoleColors.RESET, 50);
    drinksItems.put(ConsoleColors.YELLOW + "Orange Juice" + ConsoleColors.RESET, 80);
    drinksItems.put(ConsoleColors.YELLOW + "Lemonade" + ConsoleColors.RESET, 70);
    drinksItems.put(ConsoleColors.YELLOW + "Mango Smoothie" + ConsoleColors.RESET, 100);
    drinksItems.put(ConsoleColors.YELLOW + "Cold Coffee" + ConsoleColors.RESET, 120);
    drinksItems.put(ConsoleColors.YELLOW + "Hot Coffee" + ConsoleColors.RESET, 100);
    drinksItems.put(ConsoleColors.YELLOW + "Masala Tea" + ConsoleColors.RESET, 60);
    drinksItems.put(ConsoleColors.YELLOW + "Green Tea" + ConsoleColors.RESET, 80);
    drinksItems.put(ConsoleColors.YELLOW + "Milkshake" + ConsoleColors.RESET, 150);
    drinksItems.put(ConsoleColors.YELLOW + "Virgin Mojito" + ConsoleColors.RESET, 120);

    // Alcoholic Drinks
    drinksItems.put(ConsoleColors.RED + "Beer" + ConsoleColors.RESET, 200);
    drinksItems.put(ConsoleColors.RED + "Wine" + ConsoleColors.RESET, 300);
    drinksItems.put(ConsoleColors.RED + "Whiskey" + ConsoleColors.RESET, 400);
    drinksItems.put(ConsoleColors.RED + "Vodka" + ConsoleColors.RESET, 350);
    drinksItems.put(ConsoleColors.RED + "Rum" + ConsoleColors.RESET, 320);
    drinksItems.put(ConsoleColors.RED + "Tequila" + ConsoleColors.RESET, 500);
    drinksItems.put(ConsoleColors.RED + "Cocktail" + ConsoleColors.RESET, 250);

    while (true) {
        System.out.println("\nDrinks Menu:");
        int index = 1;

        // Display menu items
        for (String item : drinksItems.keySet()) {
            System.out.println(index + ". " + item + " - Rs." + drinksItems.get(item));
            index++;
        }

        System.out.println(index + ". Back to Food Menu");
        System.out.print(ConsoleColors.BLUE + "Enter your choice: " + ConsoleColors.RESET);

        int choice = getValidInput(drinksItems.size() + 1);

        // Exit to food menu
        if (choice == drinksItems.size() + 1) return;

        String selectedItem = (String) drinksItems.keySet().toArray()[choice - 1];
        System.out.print(ConsoleColors.BLUE + "Enter Quantity for " + selectedItem + ": " + ConsoleColors.RESET);
        int quantity = Integer.parseInt(scanner.nextLine());
        int price = drinksItems.get(selectedItem) * quantity;
        cart.put(selectedItem, cart.getOrDefault(selectedItem, 0) + price);
        System.out.println(quantity + " x " + selectedItem + " added to cart. Total: Rs." + price);

        System.out.println(ConsoleColors.YELLOW + "1. Back to Drinks Menu" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW + "2. Back to Food Menu" + ConsoleColors.RESET);
        System.out.print(ConsoleColors.BLUE + "Enter your choice: " + ConsoleColors.RESET);
        int action = getValidInput(2);

        if (action == 2) return;
    }
}


private static void iceCreamsMenu() {
    Map<String, Integer> iceCreamItems = new LinkedHashMap<>();
    iceCreamItems.put(ConsoleColors.YELLOW + "Vanilla Ice Cream" + ConsoleColors.RESET, 60);
    iceCreamItems.put(ConsoleColors.YELLOW + "Chocolate Ice Cream" + ConsoleColors.RESET, 70);
    iceCreamItems.put(ConsoleColors.YELLOW + "Strawberry Ice Cream" + ConsoleColors.RESET, 70);
    iceCreamItems.put(ConsoleColors.YELLOW + "Butterscotch Ice Cream" + ConsoleColors.RESET, 80);
    iceCreamItems.put(ConsoleColors.YELLOW + "Mango Ice Cream" + ConsoleColors.RESET, 80);
    iceCreamItems.put(ConsoleColors.YELLOW + "Black Currant Ice Cream" + ConsoleColors.RESET, 90);
    iceCreamItems.put(ConsoleColors.YELLOW + "Cookies and Cream Ice Cream" + ConsoleColors.RESET, 100);
    iceCreamItems.put(ConsoleColors.YELLOW + "Pista Ice Cream" + ConsoleColors.RESET, 90);
    iceCreamItems.put(ConsoleColors.YELLOW + "Caramel Ice Cream" + ConsoleColors.RESET, 100);
    iceCreamItems.put(ConsoleColors.YELLOW + "Brownie Fudge Ice Cream" + ConsoleColors.RESET, 120);
    iceCreamItems.put(ConsoleColors.YELLOW + "Kulfi" + ConsoleColors.RESET, 80);
    iceCreamItems.put(ConsoleColors.YELLOW + "Tender Coconut Ice Cream" + ConsoleColors.RESET, 90);

    while (true) {
        System.out.println("\nIce Creams Menu:");
        int index = 1;
        for (String item : iceCreamItems.keySet()) {
            System.out.println(index + ". " + item + " - Rs." + iceCreamItems.get(item));
            index++;
        }
        System.out.println(index + ". Back to Food Menu");
        System.out.print(ConsoleColors.BLUE + "Enter your choice: " + ConsoleColors.RESET);

        int choice = getValidInput(iceCreamItems.size() + 1);

        if (choice == iceCreamItems.size() + 1) return;

        String selectedItem = (String) iceCreamItems.keySet().toArray()[choice - 1];
        System.out.print(ConsoleColors.BLUE + "Enter Quantity for " + selectedItem + ": " + ConsoleColors.RESET);
        int quantity = Integer.parseInt(scanner.nextLine());
        int price = iceCreamItems.get(selectedItem) * quantity;
        cart.put(selectedItem, cart.getOrDefault(selectedItem, 0) + price);
        System.out.println(quantity + " x " + selectedItem + " added to cart. Total: Rs." + price);

        System.out.println(ConsoleColors.YELLOW + "1. Back to Ice Creams Menu" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW + "2. Back to Food Menu" + ConsoleColors.RESET);
        System.out.print(ConsoleColors.BLUE + "Enter your choice: " + ConsoleColors.RESET);
        int action = getValidInput(2);

        if (action == 2) return;
    }
}

    // Declare it as static if required by your program


// Helper method to determine if the account is premium

    // Place this corrected code inside the HotelRestaurantSystem class
private static boolean paymentCompleted;  // Define the variable

private static boolean isPremiumAccount() {
    // Assuming `loggedInAccount` is the account of the currently logged-in user
    return loggedInAccount instanceof PremiumAccount;
}

private static int accountChoice;  // Define the variable

private static void processPayment(int totalAmount) {
    while (true) {
        System.out.println(ConsoleColors.YELLOW+"Select Account:"+ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW+"1. SBI"+ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW+"2. HDFC"+ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW+"3. Union Bank"+ConsoleColors.RESET);
        System.out.print(ConsoleColors.BLUE+"Enter your choice: "+ConsoleColors.RESET);
        accountChoice = getValidInput(3); // Set account choice

        int pinAttempts = 0; // Counter for PIN attempts

        while (pinAttempts < 3) {
            System.out.print(ConsoleColors.BLUE+"Enter PIN: "+ConsoleColors.RESET);
            int pin = Integer.parseInt(scanner.nextLine());

            // Validate PIN and check balance
            if (validatePin(accountChoice, pin, SBI_PIN, HDFC_PIN, UNION_PIN)) {
                if (totalAmount <= balances[accountChoice - 1]) {
                    balances[accountChoice - 1] -= totalAmount;
                    System.out.println(ConsoleColors.YELLOW+"Payment Successful! Rs." + totalAmount + " has been deducted from your account."+ConsoleColors.RESET);

                    // Print the bill details after payment
                    System.out.println(ConsoleColors.GREEN+"\n||--- Bill Details ---||"+ConsoleColors.RESET);
                    System.out.println(ConsoleColors.YELLOW+"WELCOME!!!"+ConsoleColors.RESET);
                    System.out.println();
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
                    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
                    Random random = new Random();
                    int tableNumber = random.nextInt(100) + 1; // Random table number between 1 to 100
                    int receiptNumber = random.nextInt(10000); // Random receipt number

                    System.out.println("Date: " + now.format(dateFormatter));
                    System.out.println("Time: " + now.format(timeFormatter));
                    System.out.println();
                    System.out.println(ConsoleColors.BLUE+"Table: #" + tableNumber+ConsoleColors.RESET);
                    System.out.println(ConsoleColors.YELLOW+"Receipt No.: " + receiptNumber+ConsoleColors.RESET);
                    System.out.println();
                    System.out.println(ConsoleColors.YELLOW+"Description\tQty\tPrice\tSubtotal"+ConsoleColors.RESET);

                    // Display cart items and total amount before payment
                    System.out.println("||------------Items purchased:----------||");
                    cart.forEach((item, quantity) -> System.out.println("|| ------- " + item + ": " + quantity + "-------||"));
                    System.out.println("---------Total Amount: Rs." + totalAmount);
                    System.out.println();

                    System.out.println("\n--------Thank you for your purchase!----------");
                    System.out.println(ConsoleColors.YELLOW+"1. Back to Main Menu"+ConsoleColors.RESET);
                    System.out.println(ConsoleColors.YELLOW+"2. Exit"+ConsoleColors.RESET);

                    int choice = getValidInput(2); // Get user input for navigation
                    if (choice == 1) {
                        return; // Back to main menu
                    } else {
                        System.out.println(ConsoleColors.GREEN+"Thank you for visiting!"+ConsoleColors.RESET);
                        System.exit(0); // Exit the program
                    }
                } else {
                    System.out.println(ConsoleColors.RED+"Insufficient funds in the selected account."+ConsoleColors.RESET);
                    System.out.println(ConsoleColors.YELLOW+"1. Go to Wallet"+ConsoleColors.RESET);
                    System.out.println(ConsoleColors.YELLOW+"2. Change Account"+ConsoleColors.RESET);
                    System.out.println(ConsoleColors.YELLOW+"3. Back to Food Menu"+ConsoleColors.RESET);
                    int choice = getValidInput(3);

                    switch (choice) {
                        case 1:
                            walletMenu(); // Navigate to wallet menu for balance management
                            break;
                        case 2:
                            continue; // Retry with a different account
                        case 3:
                            return; // Exit to food menu
                    }
                }
            } else {
                pinAttempts++;
                if (pinAttempts < 3) {
                    System.out.println(ConsoleColors.RED+"Incorrect PIN. Please try again. (" + (3 - pinAttempts) + " attempts remaining)"+ConsoleColors.RESET);
                } else {
                    System.out.println(ConsoleColors.RED+"Too many incorrect attempts. Please select your account again."+ConsoleColors.RESET);
                    break; // Exit the PIN validation loop to prompt account selection again
                }
            }
        }
    }
}

private static void viewCart() {
    while (true) {
        if (cart.isEmpty()) {
            System.out.println(ConsoleColors.GREEN + "\nYour cart is empty." + ConsoleColors.RESET);
            return; // Exit the loop if the cart is empty
        }

        // Display cart items and total amount
        System.out.println(ConsoleColors.YELLOW + "\nCart Items:" + ConsoleColors.RESET);
        cart.forEach((item, quantity) -> System.out.println("- " + item + ": " + quantity));
        int totalAmount = cart.values().stream().mapToInt(Integer::intValue).sum();

        if (paymentCompleted) {
            System.out.println(ConsoleColors.GREEN + "\nPayment already completed. Thank you for your purchase!" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.YELLOW + "\n1. Go Back" + ConsoleColors.RESET);
            int choice = getValidInput(1); // Only one option: Go Back
            if (choice == 1) return;
        }

        // Check if the account is premium
        double finalAmount = totalAmount;
        if (isPremiumAccount()) {
            finalAmount = totalAmount * 0.85; // Apply 15% discount for premium accounts
            System.out.println(ConsoleColors.GREEN + "15% discount applied for Premium Account. Total Amount after discount: Rs." + (int) finalAmount + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.YELLOW + "Total Amount: Rs." + totalAmount + ConsoleColors.RESET); // Show full amount for normal accounts
        }

        System.out.println();

        // Display menu options
        System.out.println(ConsoleColors.YELLOW + "\n1. Go Back" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW + "2. Proceed to Pay" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW + "3. Delete Item from Cart" + ConsoleColors.RESET);

        int choice = getValidInput(3); // Get valid input for menu choice

        switch (choice) {
            case 1:
                return; // Go back to the previous menu
            case 2:
                processPayment((int) finalAmount); // Pass the final amount as int directly
                paymentCompleted = true; // Mark payment as completed
                return; // Exit after processing
            case 3:
                deleteItemsFromCart(); // Call delete items method
                break;
        }
    }
}

private static void deleteItemsFromCart() {
    while (true) {
        if (cart.isEmpty()) {
            System.out.println(ConsoleColors.RED+"\nYour cart is empty."+ConsoleColors.RESET);
            return; // Exit if the cart is empty
        }

        System.out.println(ConsoleColors.YELLOW+"\nCart Items:"+ConsoleColors.RESET);
        int index = 1;
        for (String item : cart.keySet()) {
            System.out.println(index + ". " + item + " - Qty: " + cart.get(item));
            index++;
        }
        System.out.println(index + ConsoleColors.YELLOW+". Back to Cart Menu"+ConsoleColors.RESET);
        System.out.print(ConsoleColors.YELLOW+"Enter the item number to delete: "+ConsoleColors.RESET);

        int choice = getValidInput(index);

        if (choice == index) return; // Back to Cart Menu

        String selectedItem = (String) cart.keySet().toArray()[choice - 1];
        cart.remove(selectedItem);
        System.out.println(selectedItem + " has been removed from the cart.");
    }
}


}