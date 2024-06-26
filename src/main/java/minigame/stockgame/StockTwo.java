package minigame.stockgame;

import player.PlayerProfile;
import ui.ResponseManager;

public class StockTwo implements Stock{
    private static final String STOCK_GRAPH =
            "▲                                                         \n" +
            "│                                                  xxx    \n" +
            "│                                                xx       \n" +
            "│                                                x        \n" +
            "│                                               xx        \n" +
            "│                                               x         \n" +
            "│                                            xxxx         \n" +
            "│                                            x            \n" +
            "│                                            x            \n" +
            "│                                           xx            \n" +
            "│                                          xx             \n" +
            "│                      xxx               xxx              \n" +
            "│                     x  xx         x   xx                \n" +
            "│          xx        xx   xx     xxxxx xx                 \n" +
            "│         xxxx    xxx      xxxxxx    xxx                  \n" +
            "│         x  xxxxxx                                       \n" +
            "│        x                                                \n" +
            "│      xxx                                                \n" +
            "│ xxxxxx                                                  \n" +
            "│                                                         \n" +
            "└────────────────────────────────────────────────────────►\n";
    private static final String STOCK_INFORMATION =
            "Demand for robots have risen in multiple sectors. -CNN \n"
                    + "Will Elon Musk expand Tesla's robotic industry? -Economists \n"
                    + "I work at Tesla and I recently received a raise -User634786 from X \n";
    private static final String STOCK_NAME = "Tesla (Large multi-national company) ";

    private static final String HIDDEN_INFO = "Elon musk will soon announce " +
            "to increase Tesla's robotic industry\n";

    private static final int STOCK_PRICE = 170;

    public void printInfo(PlayerProfile playerProfile) {
        ResponseManager.indentPrint(STOCK_GRAPH);
        ResponseManager.indentPrint(STOCK_INFORMATION);
        ResponseManager.indentPrint(STOCK_NAME + "\n");
        ResponseManager.indentPrint("Price per stock : " + STOCK_PRICE + '\n');
        if (playerProfile.occupation.equals("Robotics")) {
            ResponseManager.indentPrint((HIDDEN_INFO));
        }
    }

    public int returnProfit() {
        return getRandomNumber(10, 30);
    }

    public int returnStockPrice() {
        return STOCK_PRICE;
    }

    public String returnStockName() {
        return STOCK_NAME;
    }

    public int investmentGain(int stockAmount) {
        int gainPerStock = returnProfit();
        int gain = gainPerStock * stockAmount;
        System.out.println("The stock price risen by: " + gainPerStock);
        System.out.println("Your gain in stock for this round is: " + gain);
        return gain;
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
