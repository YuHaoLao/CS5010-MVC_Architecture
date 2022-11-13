import java.util.ArrayList;

/**
 * A portfolio of stocks is a collection of stocks with corresbounding share amount and date of purchase.
 */
public class FlexiblePortfolio {
  private ArrayList<Stock> stockList;
  private ArrayList<Integer> shareList;
  private ArrayList<String> dateList;
  private String name;

  public FlexiblePortfolio(String name) {
    this.name = name;
    stockList = new ArrayList<>(); // [apple, msft, alibaba]
    shareList = new ArrayList<>();  // [ 100,  200 ,  300]
    dateList = new ArrayList<>();
  }

  /**
   * Return the StockList of this portfolio.
   *
   * @return the StockList of this portfolio.
   */
  public ArrayList<Stock> getStockList() {
    return stockList;
  }

  /**
   * Return the shareList of this portfolio.
   *
   * @return the shareList of this portfolio.
   */
  public ArrayList<Integer> getShareList() {
    return shareList;
  }

  /**
   * Return the dateList of this portfolio.
   *
   * @return the dateList of this portfolio.
   */
  public ArrayList<String> getDateList() {
    return dateList;
  }

  /**
   * Return the name of this portfolio.
   *
   * @return the name of this portfolio.
   */
  public String getName() {
    return name;
  }


  /**
   * Return the name of stocks as String in this portfolio.
   *
   * @return the name of stocks as String
   */
  public ArrayList<String> getStockName() {
    ArrayList<String> portfolio = new ArrayList<>();
    for (int i = 0; i < stockList.size(); i++) {
      portfolio.add(stockList.get(i).getSymbol());
    }
    return portfolio;
  }

  /**
   * Add a stock in this portfolio.
   *
   * @param stock Stock.
   * @param share Share.
   * @param date Date.
   */

  public void addStock(Stock stock, int share, String date) {

    this.stockList.add(stock);
    this.shareList.add(share);
    this.dateList.add(date);

  }




}
