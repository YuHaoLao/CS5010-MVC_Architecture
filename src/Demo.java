import java.io.IOException;

/**
 * Demo class to excute this MVC system.
 */
public class Demo {

  /**
   * Main.
   *
   * @param args main.
   * @throws IOException when error occurs opening files.
   */
  public static void main(String[] args) throws IOException {
    Model m = new ModelImpl();
    View v = new ViewImpl();

//    m.CreatePortfolio("health");
//
//    m.addStock("health","aapl",1000);
//    m.addStock("health","goog",1000);
//    m.CreatePortfolio("bbq");
//    m.CreatePortfolio("bbq");
//
//    System.out.println(m.getP_lists().get(0).getStockList().size());
//
//    for ( int i = 0; i<m.getP_lists().get(0).getStockList().size();i++ ){
//      System.out.println(m.getP_lists().get(0).getStockList().get(i).getSymbol());
//    }
//    for (int i =0;i<m.getP_lists().size();i++){
//      for (int j =0; j<m.getP_lists().get(0).getStockList().size();j++){
//        System.out.println(m.getP_lists().get(j).getStockList().get(j).getSymbol());
//      }
//      System.out.println(m.getP_lists().get(i).getName());
//    }

    Controller c = new ControllerImpl(m, v);
    c.run();
  }
}
