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

    view.setMessage("1. buy");


    view.setMessage("2. exam");


    view.setMessage("3. check");


    view.setMessage("q. quit");


    Scanner choose = new Scanner(System.in);
    String input = choose.nextLine();

    switch (input) {

      case "1":

        this.createPortfolio();
        this.run();
        break;

      case "2":

        this.examinePortfolio();
        this.run();
        break;

      case "3":
        this.checkValue();
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
      if (Integer.parseInt(input) < 200) {
        return false;
      }
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
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-d");
    formatter.setLenient(false);
    try {
      Date d = formatter.parse(date);
      return true;
    } catch (Exception e) {
      return false;
    }
  }


  @Override
  public void createPortfolio() throws IOException {
    view.clearMessage();
    view.setMessage("Please enter the name of this portfolio:");

    Scanner scan = new Scanner(System.in);
    String portfolio_name = scan.nextLine();

    String value = model.CreatePortfolio(portfolio_name);
    view.setMessage(value);

    String check = "y";
    while (check.equals("y")) {
      view.clearMessage();
      view.setMessage("Please enter the stock you wish to purchase:");
      String stockSymbol = scan.nextLine();

      view.clearMessage();
      view.setMessage("Please enter the number of share you wish to purchase:");

      String numOFShare = scan.nextLine();

      while (!this.checkInt(numOFShare)) {
        view.clearMessage();
        view.setMessage("share must be positive and no less than 200");
        numOFShare = scan.nextLine();
        this.checkInt(numOFShare);
      }

      model.addStock(portfolio_name, stockSymbol, Integer.parseInt(numOFShare));


      view.clearMessage();
      view.setMessage("Do you want to purchase other stock for this portfolio?(y/n)");

      String ifContinue = scan.nextLine();
      model.setInput(ifContinue);

      check = model.getInput();
      while (!(Objects.equals(check, "y") || Objects.equals(check, "n"))) {
        view.clearMessage();
        view.setMessage("Illegal input! Please enter again(y/n)");

        String valid = scan.nextLine();
        model.setInput(valid);
        check = model.getInput();
      }

    }
    this.savePortfolio(portfolio_name);

  }

  @Override
  public void examinePortfolio() throws IOException {
    view.clearMessage();
    view.setMessage("Enter the name of the portfolio you want to examine: ");

    Scanner scan = new Scanner(System.in);
    String i = scan.nextLine();
    model.setInput(i);

    File f = model.openFile(model.getInput());
    while (!f.exists()) {
      view.clearMessage();
      view.setMessage("Portfolio " + f.getName().substring(0, f.getName().length() - 4)
              + " not exist! Please enter correctly: ");

      i = scan.nextLine();
      model.setInput(i);
      f = model.openFile(model.getInput());
    }

    view.printPortfolio(f);
  }

  @Override
  public void savePortfolio(String p_name) throws IOException {
    model.savePortfolio(p_name);
    view.clearMessage();
    view.setMessage("Portfolio " + p_name + model.getInput());

  }

  @Override
  public void checkValue() throws IOException {
    view.clearMessage();
    view.setMessage("Enter the name of the portfolio you want to check value: ");
//    view.print();
    Scanner scan = new Scanner(System.in);
    String i = scan.nextLine();
    model.setInput(i);
    File f = model.openFile(model.getInput());

    while (!f.exists()) {
      view.clearMessage();
      view.setMessage("Portfolio " + f.getName().substring(0, f.getName().length() - 4)
              + " not exist! Please enter correctly: ");

      i = scan.nextLine();
      model.setInput(i);
      f = model.openFile(model.getInput());
    }

    view.clearMessage();
    view.setMessage("Enter the date you want to check value(yyyy-mm-d(no prefix 0 for days)): ");

    i = scan.nextLine();

    while (!this.checkDate(i)) {
      view.clearMessage();
      view.setMessage("Please enter correct date(yyyy-mm-d(no prefix 0 for days)): ");

      i = scan.nextLine();
      this.checkDate(i);
    }

    model.setInput(i);
    String date = model.getInput();

    String value = model.checkValue(f, date);
    if (value.equals("-1")) {
      view.setMessage("The data of " + date + " is not avaliable!");

    } else {
      view.clearMessage();
      view.setMessage(
              "The total value of portfolio--" + f.getName().substring(0, f.getName().length() - 4)
                      + " on Date " + date + " is:" + value);

    }
  }
}
