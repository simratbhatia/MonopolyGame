import java.util.*;

class Player {
    private String name;int balance;
    private ArrayList<Property> properties;
    private int currPosition;int strategy;
    public Player(String name,int strategy){
        this.currPosition=0;
        this.name=name;
        this.strategy=strategy;
        this.properties=new ArrayList<>();
        this.balance=5000;
    }

    public ArrayList<Property> getProperties() {
        return this.properties;
    }

    public int subtractMoney(int amount) {
        if ((this.balance -= amount) < 0) {
            System.out.println("Cannot buy Property");
            return -1;
        }
        this.balance-=amount;          //sout("bought property ")
        return 1;
    }
    public String getName(){
        return this.name;
    }
    public int getBalance(){
        return this.balance;
    }
    public void addMoney(int amount){
        this.balance+=amount;
    }
    public void move(int steps){
        this.currPosition+=steps;
        if((this.currPosition)>15){
            this.currPosition-=16;
//            this.currPosition-=16;
        }
    }

    public void changeLocation(){        //will be used for go to jail
        this.currPosition=4;
    }    // function to go to jail
    public int getCurrPosition(){
        return this.currPosition;
    }
    public void buyProperty(Property property){           //
        if(this.balance>=property.getPrice()){
            property.purchase(this);
            this.properties.add(property);          //properties refers to all cities. not bought one
        }
    }
    public int getStrategy(){            //
        return this.strategy;
    }
}

class Property{
    private int price;String blockname;int location;String typeofBlock;
    private Player owner;
    public Property(int location,String blockname,int price,String typeofBlock){
        this.price=price;
        this.blockname=blockname;
        this.location=location;
        this.typeofBlock=typeofBlock;
        this.owner=null;
    }


    public Property(int location,String blockname){
        this.location=location;
        this.blockname=blockname;
        this.typeofBlock="nonProperty";
    }
    public void purchase(Player player){
        this.owner=player;
        player.subtractMoney(this.getPrice());
        System.out.println(this.getOwner().getName()+" bought "+this.getBlockname()+" and is left with "+this.getOwner().getBalance()+" balance.");
    }
    public String getBlockname(){
        return this.blockname;
    }

    public int getPrice(){
        return this.price;
    }
    public Player getOwner(){
        return this.owner;                  ///
    }
    public void payRent(Player player){
        if(this.owner!=null && !this.owner.equals(player)){
            int x=player.subtractMoney(this.getPrice());               //add rent here
            System.out.println(player.getName()+" paid "+this.getPrice()+" to "+ this.owner.getName());
            if(x<0){
                System.out.println(player.getName()+" lost the game after paying rent for " + this.blockname);
            }
            this.owner.addMoney(this.getPrice());           //rent here
        }
    }
}
class Board{
    private ArrayList<Property> properties;
    private ArrayList<Player> players;
    public Board(){
        this.properties=new ArrayList<>();
        this.players=new ArrayList<>();
        addproperties();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Property> getProperties() {
        return this.properties;
    }
    private void addproperties(){
        Property property1 = new Property(1,"Chandigarh",300,"city");
        Property property2 = new Property(3,"Patiala",250,"city");
        Property property3 = new Property(5,"Mumbai",350,"city");
        Property property4 = new Property(7,"Pune",290,"city");
        Property property5 = new Property(9,"Jaipur",200,"city");
        Property property6 = new Property(11,"Ajmer",220,"city");
        Property property7 = new Property(13,"Punchkula",380,"city");
        Property property8 = new Property(15,"Gurgaon",300,"city");
        Property property9 = new Property(2,"Punjab Airport",380,"airport");
        Property property10 = new Property(10,"Rajasthan Aiport",380,"airport");
        Property property11 = new Property(0,"Start");
        Property property12 = new Property(4,"Jail");
        Property property13 = new Property(8,"Free Parking");
        Property property14 = new Property(12,"Go to Jail");
        Property property15 = new Property(6,"Treasure");
        Property property16 = new Property(14,"Treasure");

        this.properties.add(0,property11);
        this.properties.add(1,property1);
        this.properties.add(2,property9);
        this.properties.add(3,property2);
        this.properties.add(4,property12);
        this.properties.add(5,property3);
        this.properties.add(6,property15);
        this.properties.add(7,property4);
        this.properties.add(8,property13);
        this.properties.add(9,property5);
        this.properties.add(10,property10);
        this.properties.add(11,property6);
        this.properties.add(12,property14);
        this.properties.add(13,property7);
        this.properties.add(14,property16);
        this.properties.add(15,property8);

    }

    public void addPlayer(Player player){
        this.players.add(player);
    }

    public void playTurn(int flag){
        int diceStart=1;
        int diceEnd=6;
        for(Player player : this.players){
            int randomInt=(int)Math.floor(Math.random()*(diceEnd-diceStart+1)+diceStart);
            player.move(randomInt);
            Property currProperty = this.properties.get(player.getCurrPosition());
            if((currProperty instanceof Property)&&currProperty.typeofBlock=="city"){
                if((currProperty.getOwner()==null)&&(player.getStrategy()==1)){
                    if(currProperty.getPrice()<300){
                        player.buyProperty(currProperty);
                    }
                }
                else if((currProperty.getOwner()==null)&&(player.getStrategy()==2)){
                    if(currProperty.getPrice()>=300){
                        player.buyProperty(currProperty);
                    }
                }
            } else if ((currProperty instanceof Property)&&currProperty.typeofBlock=="airport") {
                int x=player.subtractMoney(currProperty.getPrice());
                if(x<0){
                    System.out.println(player.getName()+" lost the game and have negative balance");
                    flag=1;
                }
            }
            else if((currProperty instanceof Property)&&currProperty.blockname=="Go to Jail"){
                player.changeLocation();
            }
            else if((currProperty instanceof Property)&&currProperty.blockname=="Treasure"){
                player.addMoney(500);
            }
            else{
                currProperty.payRent(player);        //

            }
            for (Player players : this.players){
                if(player.getBalance()<=0){
                    System.out.println(player.getName()+" lost the game and have no money");
                    flag=1;
                    break;    //change made, it will avoid the game going beyond negative balance.
                }
            }
            if(flag==1){
                break;         //added break
            }
        }
    }
}