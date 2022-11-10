import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Model Interface, implements all the functionality.
 */
public interface Model {

  /**
   * Pass the input string from controller into Model.
   *
   * @param i input String from controller.
   */
  void setInput(String i);

  /**
   * Get the String stored in Model.
   *
   * @return the String stored in Model.
   */
  String getInput();

  /**
   * Save the portfolio as .txt file.
   *
   * @param p_name the name of the portfolio.
   * @throws IOException when directory not exist.
   */
  void savePortfolio(String p_name) throws IOException;

  /**
   * Open the portfolio txt file.
   *
   * @param name the name of portfolio.
   * @return the txt file.
   * @throws IOException when txt file not exist.
   */
  File openFile(String name) throws IOException;

  /**
   * Check the total price value of a portfolio.
   *
   * @param f    txt file.
   * @param date the yyyy-mm-dd format date String.
   * @return the total price of this portfolio.
   * @throws FileNotFoundException when txt file not exist.
   */
  String checkValue(File f, String date) throws FileNotFoundException;

  String CreatePortfolio(String name);

  ArrayList<Portfolio> getP_lists();

  void addStock(String p_name, String s_name, int share);
}
