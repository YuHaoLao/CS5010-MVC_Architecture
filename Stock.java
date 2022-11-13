import java.util.ArrayList;

/**
 * A class of Stock, obtain from AlphaVantage API.
 */
public class Stock {

  private Api info;

  /**
   * Constructor of Stock, takes symbol of a stock as parameters.
   *
   * @param info symbol of stock.
   */
  public Stock(Api info) {
    this.info = info;
  }

  /**
   * Get the information of this stock.
   *
   * @return the information of this stock.
   */
  public String getInfo() {
    return info.getInfo().toString();
  }

  /**
   * Get the symbol of this stock.
   *
   * @return the symbol of this stock.
   */
  public String getSymbol() {
    return this.info.symbol;
  }


  /**
   * Get the list of date of this stock.
   *
   * @return the list of date of this stock.
   */
  public ArrayList<String> getTimeList() {
    return this.info.getTimeList();
  }

  /**
   * Get the list of open value of this stock.
   *
   * @return the list of open value of this stock.
   */
  public ArrayList<String> getOpenList() {
    return this.info.getOpenList();
  }

  /**
   * Get the list of high value of this stock.
   *
   * @return the list of high value of this stock.
   */
  public ArrayList<String> getHighList() {
    return this.info.getHighList();
  }

  /**
   * Get the list of close value of this stock.
   *
   * @return the list of close value of this stock.
   */
  public ArrayList<String> getCloseList() {
    return this.info.getCloseList();
  }

  /**
   * Get the list of low value of this stock.
   *
   * @return the list of low value of this stock.
   */
  public ArrayList<String> getLowList() {
    return this.info.getLowList();
  }

  /**
   * Get the list of volume value of this stock.
   *
   * @return the list of volume value of this stock.
   */
  public ArrayList<String> getVolumeList() {
    return this.info.getVolumeList();
  }
}
