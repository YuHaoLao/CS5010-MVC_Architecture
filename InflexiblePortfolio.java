import java.util.ArrayList;
import java.util.Objects;

/**
 * A portfolio of stocks is a collection of stocks with corresbounding share amount.
 */
public class InflexiblePortfolio {

  private ArrayList<Stock> stockList;
  private ArrayList<Integer> shareList;





  private String name;


  /**
   * Constructor for a portfolio.
   *
   * @param name the name of this portfolio.
   */
  public InflexiblePortfolio(String name) {
    this.name = name;
    stockList = new ArrayList<>(); // [apple, msft, alibaba]
    shareList = new ArrayList<>();  // [ 100,  200 ,  300]
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
   */

  public void addStock(Stock stock, int share) {

    for (int i =0; i< stockList.size();i++){
      if (Objects.equals(stock.getSymbol(), stockList.get(i).getSymbol())){
        shareList.set(i,share+ shareList.get(i));
        return;
      }
    }

    this.stockList.add(stock);
    this.shareList.add(share);


  }


}
