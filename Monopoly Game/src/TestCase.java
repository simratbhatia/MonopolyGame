public class TestCase {

    public static void main(String[] args) {
        testGamePlay();
        testBuyProperty();
        testPayRent();
        testTreasureEvent();
        testGoToJailEvent();
    }

    // Test method to simulate and check general game play
    public static void testGamePlay() {
        Board board = new Board();

        Player player1 = new Player("Player1", 1);
        Player player2 = new Player("Player2", 2);

        board.addPlayer(player1);
        board.addPlayer(player2);

        // Simulate multiple turns
        for (int i = 0; i < 10; i++) {
            board.playTurn(0);
        }

        // Assertions
        assert (player1.getBalance() >= 0) : "Player1's balance should be non-negative";
        assert (player2.getBalance() >= 0) : "Player2's balance should be non-negative";

        System.out.println("Game play test passed!");
    }

    // Test method to simulate and check property buying
    public static void testBuyProperty() {
        Board board = new Board();
        Player player = new Player("TestPlayer", 1);
        board.addPlayer(player);

        // Setting the player's position to a property that can be bought
        player.move(1);
        Property property = board.getProperties().get(player.getCurrPosition());

        // Test buying the property
        player.buyProperty(property);

        // Assertions
        assert (player.getBalance() < 1000) : "Player's balance should be less than 1000";
        assert (property.getOwner() == player) : "Player did not buy the property";
        assert (player.getProperties().contains(property)) : "Player did not acquire the property";

        System.out.println("Buy Property test passed!");
    }

    // Test method to simulate and check rent payment
    public static void testPayRent() {
        Board board = new Board();
        Player owner = new Player("Owner", 1);
        Player renter = new Player("Renter", 2);
        board.addPlayer(owner);
        board.addPlayer(renter);

        // Setting the renter's position to an owned property
        renter.move(1);
        Property property = board.getProperties().get(renter.getCurrPosition());
        property.purchase(owner);  // Assign owner

        // Ensure renter has enough balance to pay rent
        renter.addMoney(property.getPrice() + 100);  // Adjust balance to cover rent

        // Test paying rent
        property.payRent(renter);  // Corrected line, call payRent on the Property

        // Assertions
        assert (renter.getBalance() < 1000) : "Renter's balance should be less than 1000";
        assert (owner.getBalance() > 0) : "Owner's balance should be positive";

        System.out.println("Pay Rent test passed!");
    }

    // Test method to simulate and check treasure event
    public static void testTreasureEvent() {
        Board board = new Board();
        Player player = new Player("TreasurePlayer", 1);
        board.addPlayer(player);

        // Set the player's position to a treasure property
        player.move(6);  // Assuming treasure property is at position 6
        Property treasureProperty = board.getProperties().get(player.getCurrPosition());

        // Test treasure event
        int initialBalance = player.getBalance();
        player.buyProperty(treasureProperty);  // Buying the treasure property triggers a cash reward

        // Assertions
        assert (player.getBalance() > initialBalance) : "Player's balance should increase after a treasure event";

        System.out.println("Treasure Event test passed!");
    }

    // Test method to simulate and check "Go to Jail" event
    public static void testGoToJailEvent() {
        Board board = new Board();
        Player player = new Player("GoToJailPlayer", 1);
        board.addPlayer(player);

        // Set the player's position to a "Go to Jail" property
        player.move(12);  // Assuming "Go to Jail" property is at position 12
        Property goToJailProperty = board.getProperties().get(player.getCurrPosition());

        // Test "Go to Jail" event
        player.changeLocation();  // This method should set the player's position to the jail

        // Assertion
        assert (player.getCurrPosition() == 4) : "Player did not go to jail";

        System.out.println("Go to Jail Event test passed!");
    }

}