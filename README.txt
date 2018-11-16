  Author
    Stanley Small (stanley.small1@maine.edu)

  Building
    To build the program type
      > javac Main.java
    at the terminal. This will produce the "Main.class" file.

  Running The Program
    You can run the program by typing
      > java Main
    at the terminal.

  Inputs
    The program will load and parse a configuration input file located in a
    subdirectory from your executable. The path passed to the as the first input
    argument takes the form: "./inputs/input.X.json" where "X" is some number
    between 1 and 100 that denotes the test number. An example input file is
    contained in the "inputs" subdirectory named "input.0.json". The input file
    must be a JSON file with the following format:

      "drinkextras":  Array of objects that defines the extra options available
                      for all drinks on the menu. Each object represents one
                      drink extra. These extras are optional and may not
                      necessarily be included in the order.
        "name": string. Name of the add-on.
        "price": decimal. Price for the add-on.
      "drinks": Array of objects. Each object represents one drink.
        "name": string. Name of the drink.
        "sizes": object[].
          "name": string. Name of the size (e.g.: Small, Medium, Large).
          "price": decimal. Price to charge the customer for this size.
      "food": Array of objects. Each object represents one food item.
        "name": string. Name of food item (e.g.: Bagel).
        "sizes": object[]. Sizes available for that item.
          "name": string. Name of the size (e.g.: half, whole).
          "price": decimal. Price for the size option.
        "extras": object[]. Any extra options available for the food option.
          "name": string. Name of the extra (cream cheese)
          "price": decimal. Price for the option.

    After configuration has been loaded, the program will prompt for orders.
    Order must always b entered with the following format:
      order [item] [size] [extra1]...[extraN], [item] [size] ...
    Example:
      order coffee medium whipcream flavorshot, coffee small, bagel half butter
    Note: item extras are optional

  Outputs
    The program will write a JSON file into a sub-directory from the executable.
    The path of the output file will take the form:
    "./outputs/STANLEY_SMALL.X.json" where  "X" is the number loaded from the
    input file's name.

    The JSON file contains an object array named "orders", containing objects
    with the following properties:
      "price":  The total order price.
      "error":  If applicable, an error message explaining why an order couldn't
                be processed and totaled.

  Questions
    If you have questions, email me or please schedule an interview.
