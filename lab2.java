public class lab2 {

    int coins = 1;

    void muliplyCoins(int ratio) {
        int newcoins = coins * ratio;
        coins = newcoins;
    }
    public static void main(String[] args) {
        lab2 l2 = new lab2();
        l2.muliplyCoins(10);
        l2.muliplyCoins(20);
    }
}