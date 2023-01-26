public class Game {
    private Room currentRoom;
    private Parser parser;
    private Player player;
    public Game() {
        parser = new Parser();
        player = new Player();
    }
    public static void main(String[] args) {
        Game game = new Game();
        game.createRooms();
        game.play();
    }

    private void createRooms(){

        Room outside = new Room("You are outside of a huge mysterious looking mansion, but the door won't open.", "You look around and the sun glistens in the bright blue sky. This huge white mansion has a weird feeling to it. You look at the green grass and see a key on the ground");
        Room kitchen = new Room("This is the kitchen where the food is made by a world class chef", "There are awards on the walls around you from cooking competitions. The smell of food fills the air but there is no food or chef anywhere to be found.");
        Room bowlingAlley = new Room("Woah.. you just walked into a huge bowling alley!", "bowling long description");
        Room button = new Room("You just stepped into a pitch black lifeless room", "button long description");
        Room spikes = new Room("That's not good", "spikes long description");
        Room Transylvania = new Room("Hmmm, this really doesn't seem like a house anymore", "transylvania long description");
        Room InNOut = new Room("Just maybe there's some food here!", "innout long description");


        outside.setExit("forward", kitchen);
        kitchen.setExit("forward", bowlingAlley);
        bowlingAlley.setExit("forward", button);
        button.setExit("forward", Transylvania);
        Transylvania.setExit("forward", InNOut);

        Item obj = new Item();
        Item obj2 = new Item();

        outside.setItem("key", obj);
        kitchen.setItem("knife", obj2);


        currentRoom = outside;

    }

    public void play() {
        printWelcome();


        boolean finished = false;
        while(!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);

        }
        System.out.println(("Thanks for playing"));
    }

    private boolean processCommand(Command command){
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord){
            case UNKNOWN:
                System.out.println("What does that mean?");
                break;
            case HELP:
                printHelp();
                break;
            case GO:
                goRoom(command);
                break;
            case QUIT:
                wantToQuit = quit(command);
                break;
            case LOOK:
                look(command);
                break;
            case GRAB:
                grab(command);
                break;
            case DROP:
                drop(command);
                break;
            case PRESS:
                press(command);
               break;

        }
        return wantToQuit;
    }

    private void printHelp() {
        System.out.println("You are starving");
        System.out.println("try to look or move around, maybe you'll find food");
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    public void look(Command command) {
        if(command.hasSecondWord()) {
            System.out.println("You can't look at " + command.getSecondWord());
            return;
        }
        System.out.println(currentRoom.getLongDescription());
        System.out.println(player.getItemString());
    }

    private void goRoom(Command command)
    {
        if(!command.hasSecondWord()){
            // if there is no second word, we don't know where to go
            System.out.println("Go Where?");
            return;
        }

        String direction = command.getSecondWord();

        //try to leave current room
        Room nextRoom = currentRoom.getExit(direction);

        if(nextRoom == null){
            System.out.println("Can't go this way!");
        }

        else{
            currentRoom = nextRoom;
            System.out.println(currentRoom.getShortDescription());

        }
    }

    private void push(Command command) {
        if(!command.hasSecondWord()) {
            System.out.println("push what?");
            return;
        }
        String ke = command.getSecondWord();

        Button pushButton = goRoom(spikes) {

        }
    }

    private void grab(Command command) {
        if(!command.hasSecondWord()) {
            System.out.println("Grab what?");
            return;
        }
        String key = command.getSecondWord();

        Item grabItem = currentRoom.getItem(key);
        if(grabItem == null) {
            System.out.println("You can't grab " + command.getSecondWord());
        }
        else{
            player.setItem(key, grabItem);
        }
    }



    private void drop(Command command) {
        if(!command.hasSecondWord()) {
            System.out.println("Drop what?");
            return;
        }
        String key = command.getSecondWord();

        Item dropItem = player.getItem(key);
        if(dropItem == null) {
            System.out.println("You can't drop " + command.getSecondWord());
        }
        else{
            currentRoom.setItem(key, dropItem);
        }
    }


    private boolean quit(Command command) {
        if(command.hasSecondWord()) {
            System.out.println("You can't quit " + command.getSecondWord());
            return false;
        }
        else{
            return true;
        }
    }

    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to my text adventure game!");
        System.out.println("You find yourself in a crazy random world where you are dying of hunger!");
        System.out.println("Type \\‘help\\\" if you need assistance");
        System.out.println();
        System.out.println("we will print a long room description here");
    }

}





