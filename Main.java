import java.util.Scanner; // used to parse system input
import java.util.HashMap; // used to store the items and prices
import java.nio.file.Files; // used to read the JSON file as a string
import java.nio.file.Paths; // used to get the file via a path

class Main {
  // hashmap for the menu items and extras (ex. coffee+small -> price)
  public static HashMap<String, Double> items = new HashMap<>();
  public static HashMap<String, Double> extras = new HashMap<>();

  // the main program
  public static void main(String[] args) throws Exception {
    importJson(); // import the JSON

    while(true) { // run until the user quits
      System.out.println("\nPLEASE ENTER AN ORDER");
      System.out.println("(order [item] [size] [extra1]...[extraN], [item] [size] ..., -[payment method]) \n");
      double price = 0; // holds the price for each order
      boolean cashDiscount = false;
      Scanner order = new Scanner(System.in); // a scanner for the order
      order.next(); // remove the text "order"
      order = new Scanner(order.nextLine()).useDelimiter(",|\n");

      System.out.println("\n CUSTOMER ORDER\n____________________________");

      while(order.hasNext()) { // while the order still has itms for order
        Scanner item = new Scanner(order.next()); // scanner used for the item
        String itemName = item.next(); // holds the name of the item (i.e. bagel)
        if(itemName.contains("-")) {
          cashDiscount = itemName.contains("cash");
          break;
        }
        String id = itemName + "+" + item.next(); // holds the name and size

        if (items.containsKey("drink+" + id)) {// check if item is a drink
          double itemPrice = items.get("drink+" + id); // get price from HashMap
          price += itemPrice;
          System.out.format("%-23s$%-10.2f\n", id.replace('+',' '), itemPrice);
          while(item.hasNext()) { // while item has extras
            String extra = item.next(); // holds name of extra
            if (extras.containsKey("drink+" + extra)) {// validates extra
              double extraPrice = extras.get("drink+" + extra); // get the price
              price += extraPrice;
              System.out.format("+ %-21s$%-10.2f\n", extra, extraPrice);
            }
            else System.out.println("DRINK EXTRA NOT FOUND"); // error message
          }
          System.out.println();
        }
        else if (items.containsKey("food+" + id)) { // checks if item is food
          double itemPrice = items.get("food+" + id); // get price
          price += itemPrice;
          System.out.format("%-23s$%-10.2f\n", id.replace('+',' '), itemPrice);
          while(item.hasNext()) { // while food item has extras
            String extra = item.next(); // store the extra name here
            if (extras.containsKey("food+" + itemName + "+" + extra)) {
              double extraPrice = extras.get("food+" + itemName + "+" + extra);
              price += extraPrice;
              System.out.format("+ %-21s$%-10.2f\n", extra, extraPrice);
            }
            else System.out.println("FOOD EXTRA NOT FOUND"); // error message
          }
          System.out.println();
        }
      }
      System.out.println("____________________________");
      if (cashDiscount) {
        System.out.format("CASH DISCOUNT (5%%)    -$%-10.2f\n", price/20);
        price *= .95;
      }
      System.out.format("Order Total:           $%-10.2f\n", price);
    }
  }
  public static void importJson() throws Exception {
    String json = new String(Files.readAllBytes(Paths.get("inputs/input.1.json"))).replaceAll("\\s","");
    String[] result = json.split("\"|(?=\\{)|(?=\\})|(?<=\\{)|(?<=\\})|(?<=\\:)|(?=\\])|(?<=\\])");

    int i = 1;
    // parse drink extras
    while(!result[i++].equals("drinkextras"));
    while(!result[i++].equals("]"))
      if(result[i].equals("name"))
        extras.put("drink+" + result[i+2], new Double(result[i+6]).doubleValue());

    // parse drinks
    while(!result[i++].equals("drinks"));
    String name = "";
    while(!result[i++].equals("]")) {
      while(!result[i++].equals("name"));
      name = result[i+1];
      while(!result[i++].equals("]"))
        if(result[i].equals("name"))
          items.put("drink+" + name + "+" + result[i + 2], new Double(result[i+6]).doubleValue());
      i++;
    }

    // parse food
    while(!result[i++].equals("food"));
    while(!result[i++].equals("]")) {
      while(!result[i++].equals("name"));
      name = result[i+1];
      while(!result[i++].equals("]"))
        if(result[i].equals("name"))
          items.put("food+" + name + "+" + result[i + 2], new Double(result[i+6]).doubleValue());
      while(!result[i++].equals("]"))
        if(result[i].equals("name"))
          extras.put("food+" + name + "+" + result[i + 2], new Double(result[i+6]).doubleValue());
      i++;
    }

    System.out.println(extras);
    System.out.println(items);
  }
}
