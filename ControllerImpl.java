import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;



/**
 * An implementation of Controller.
 */
public class ControllerImpl implements Controller {

  private static Model model;
  private static View view;


  /**
   * Constructor of ControllerImpl, takes Model and View as parameters.
   *
   * @param model Model part of MVC.
   * @param view  View part of MVC.
   */
  public ControllerImpl(Model model, View view) {
    this.model = model;
    this.view = view;
  }

  @Override
  public void run() throws IOException {

    view.setMessage(" Welcome to the app, please chose the following services");

    view.setMessage("1. Create a portfolio");

    view.setMessage("2. Examine Composition");

    view.setMessage("3. Check Value");

    view.setMessage("q. quit");

    Scanner sc = new Scanner(System.in);
    String input = sc.nextLine();

    switch (input) {

      case "1":
        view.setMessage("What kind of portfolio you want to create?");
        view.setMessage("1. Create an inflexible portfolio");
        view.setMessage("2. Create a flexible portfolio");
        input = sc.nextLine();
        switch (input) {
          case "1":
            this.createInfPortfolio();
            this.run();
          case "2":
            createFlexPortfolio();
            this.run();
        }
        break;

      case "2":

        this.examineInfPortfolio();
        this.run();
        break;

      case "3":
        this.checkInfValue();
        this.run();
        break;


      case "4":
        // continue edit the unfinished  FlexPortfolio.
        // includes buy or sell
        //this.edit()
        this.run();
        break;


      case "q":
        break;

      default:
        view.setMessage("Please enter correctly!");
        this.run();
        break;
    }


  }

  /**
   * A helper method to check the validation when purchasing share of a stock. Only Integer is
   * accepted and a user can not purchase less than 200 share of a stock.
   *
   * @param input the share amount.
   * @return the result of valadition.
   */
  private Boolean checkInt(String input) {


    for (char c : input.toCharArray()) {
      if (!Character.isDigit(c)) {
        return false;
      }
    }
    if (Integer.parseInt(input) < 200) {
      return false;
    }
    return true;
  }

  /**
   * A helper method to check the validation when check the value of a portfolio. Only yyyy-mm-dd
   * format date is accepted.
   *
   * @param date the date String.
   * @return the result of valadition.
   */
  private Boolean checkDate(String date) {
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    formatter.setLenient(false);
    try {
      Date d = formatter.parse(date);
      return true;
    } catch (Exception e) {
      return false;
    }
  }


  private String getPortfolioName() {
    view.setMessage("You are choosing Inflexible portfolio,Please enter the name of this portfolio:");
    Scanner scan = new Scanner(System.in);
    String portfolio_name = scan.nextLine();
    String value = model.CreateInfPortfolio(portfolio_name);
    while (value.equals("This portfolio already exists.")) {
      view.setMessage("Please enter another name of this portfolio:");
      portfolio_name = scan.nextLine();
      value = model.CreateInfPortfolio(portfolio_name);
    }
    view.setMessage(value);
    return portfolio_name;
  }

  private String getFlexPortfolioName() {
    view.setMessage("You are choosing Flexible portfolio, Please enter the name of this portfolio:");
    Scanner scan = new Scanner(System.in);
    String portfolio_name = scan.nextLine();
    String value = model.CreateFlexPortfolio(portfolio_name);
    while (value.equals("This portfolio already exists.")) {
      view.setMessage("Please enter another name of this portfolio:");
      portfolio_name = scan.nextLine();
      value = model.CreateFlexPortfolio(portfolio_name);
    }
    view.setMessage(value);
    return portfolio_name;
  }

  private String getStockName() {
    Scanner scan = new Scanner(System.in);
    view.setMessage("Please enter the stock you wish to purchase:");
    return scan.nextLine();
  }

  private String getDate() {
    Scanner scan = new Scanner(System.in);
    view.setMessage("Please enter the date of the stock you wish to purchase :");
    String date = scan.nextLine();

    Boolean if_match = checkDate(date);


    while (!if_match) {
      view.setMessage("please enter the format in yyyy-mm--dd");
      scan = new Scanner(System.in);
      date = scan.nextLine();

      if_match =checkDate(date);

    }

    return date;
  }

  private String getNumOfShare() {
    Scanner scan = new Scanner(System.in);
    view.setMessage("Please enter the number of share you wish to purchase:");

    String numOFShare = scan.nextLine();

    while (!this.checkInt(numOFShare)) {
      view.setMessage("share must be positive and no less than 200");
      numOFShare = scan.nextLine();
      this.checkInt(numOFShare);
    }
    return numOFShare;
  }

  private void ifContinue() {
    Scanner scan = new Scanner(System.in);
    view.setMessage("Do you want to purchase other stock for this portfolio?(y/n)");
    String ifContinue = scan.nextLine();
    model.setInfo(ifContinue);

  }

  private String yesOrNo(String check) {
    Scanner scan = new Scanner(System.in);
    while (!(Objects.equals(check, "y") || Objects.equals(check, "n"))) {
      view.setMessage("Illegal input! Please enter again(y/n)");

      String valid = scan.nextLine();
      model.setInfo(valid);
      check = model.getInfo();
    }
    return check;
  }

  @Override
  public void createInfPortfolio() throws IOException {

    String portfolio_name = getPortfolioName();
    String check = "y";
    while (check.equals("y")) {
      String stockSymbol = getStockName();
      String numOFShare = getNumOfShare();
      model.addStock(portfolio_name, stockSymbol, Integer.parseInt(numOFShare), null);
      ifContinue();
      check = model.getInfo();
      check = yesOrNo(check);
    }
    this.saveInfPortfolio(portfolio_name);

  }

  @Override
  public void examineInfPortfolio() throws IOException {
    view.setMessage("Enter the name of the portfolio you want to examine: ");

    Scanner scan = new Scanner(System.in);
    String i = scan.nextLine();
    model.setInfo(i);

    File f = model.openInfFile(model.getInfo());
    while (!f.exists()) {
      view.setMessage("Portfolio " + f.getName().substring(0, f.getName().length() - 4)
              + " not exist! Please enter correctly: ");

      i = scan.nextLine();
      model.setInfo(i);
      f = model.openInfFile(model.getInfo());
    }

    view.printPortfolio(f);
  }

  public void createFlexPortfolio() throws IOException {

    String portfolio_name = getFlexPortfolioName();
    view.setMessage("Flexible Portfolio" + portfolio_name + "already create.");
    view.setMessage("Enter y to continue buy stock on portfolio " + portfolio_name + ", n to save the empty portfolio");
    Scanner scan = new Scanner(System.in);
    String if_continue = scan.nextLine();

    if (Objects.equals(if_continue, "y")) {
      String check = "y";
      while (check.equals("y")) {
        String stockSymbol = getStockName();
        String numOFShare = getNumOfShare();
        String date = getDate();
        model.addStock(portfolio_name, stockSymbol, Integer.parseInt(numOFShare), date);
        ifContinue();
        check = model.getInfo();
        check = yesOrNo(check);
      }
      this.saveFlexPortfolio(portfolio_name);
    } else if (Objects.equals(if_continue, "n")) {

      this.saveFlexPortfolio(portfolio_name);
    }


  }

  @Override
  public void saveInfPortfolio(String p_name) throws IOException {
    model.saveInfPortfolio(p_name);
    view.setMessage("Portfolio " + p_name + model.getInfo());
  }

  @Override
  public void saveFlexPortfolio(String p_name) throws IOException {
    model.saveFlexPortfolio(p_name);
    view.setMessage("Portfolio " + p_name + model.getInfo());
  }

  @Override
  public void checkInfValue() throws IOException {
    view.setMessage("Enter the name of the portfolio you want to check value: ");

    Scanner scan = new Scanner(System.in);
    String i = scan.nextLine();
    model.setInfo(i);
    File f = model.openInfFile(model.getInfo());

    while (!f.exists()) {
      view.setMessage("Portfolio " + f.getName().substring(0, f.getName().length() - 4)
              + " not exist! Please enter correctly: ");

      i = scan.nextLine();
      model.setInfo(i);
      f = model.openInfFile(model.getInfo());
    }

    view.setMessage("Enter the date you want to check value(yyyy-mm-d(no prefix 0 for days)): ");

    i = scan.nextLine();

    while (!this.checkDate(i)) {
      view.setMessage("Please enter correct date(yyyy-mm-d(no prefix 0 for days)): ");

      i = scan.nextLine();
      this.checkDate(i);
    }

    model.setInfo(i);
    String date = model.getInfo();

    String value = model.checkInfValue(f, date);
    if (value.equals("-1")) {
      view.setMessage("The data of " + date + " is not avaliable!");

    } else {
      view.setMessage(
              "The total value of portfolio--" + f.getName().substring(0, f.getName().length() - 4)
                      + " on Date " + date + " is:" + value);

    }
  }

//  public void edit(){
//    view.setMessage(" please enter the name of a Flexible Portfolio ");
//    Scanner scan = new Scanner(System.in);
//    String p_name = scan.nextLine();
////    model.edit(p_name);
//
//  }
}
