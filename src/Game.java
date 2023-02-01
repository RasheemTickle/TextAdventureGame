public class Game {
    private Room currentRoom;
    private Parser parser;
    private Player player;
    boolean wantToQuit;

    public Game() {
        parser = new Parser();
        player = new Player();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.createRooms();
        game.play();
    }

    private void createRooms() {

        Room outside = new Room("You are outside of a huge mysterious looking mansion, but the door won't open.",
                "You look around and the sun glistens in the bright blue sky. This huge white mansion has a weird feeling to it, but the front door is locked. You look at the green grass and see a key on the ground");
        Room kitchen = new Room("This is the kitchen where the food is made by a world class chef", "There are awards on the walls around you from cooking competitions. " +
                "The smell of food fills the air but there is no food or chef anywhere to be found.");
        Room bowlingAlley = new Room("Woah.. you just walked into a huge bowling alley!", "The size of this bowling alley is amazing! The colorful lights and upbeat " +
                "music fills the room. Maybe some bowling would be fun, try rolling a ball down the lane.");
        Room button = new Room("You just stepped into a pitch black lifeless room", "The lights suddenly turn on. You are looking at a giant room with lights all " +
                "pointing at a big red button. Would pushing it be a good idea?");
        Room spikes = new Room("That's not good at all. You fell through a trap door and die by spikes down below. I don't think that was a friendly button. GAME OVER",
                "Game over, restart your game.");
        Room Transylvania = new Room("Hmmm, this really doesn't seem like a house anymore", "Okay, this mansion is getting really wierd. You are looking at a transylvania of vampires " +
                "and other scary looking monsters. Hopefully you brought a weapon to stab the monsters! Use command stab knife");
        Room InNOut = new Room("You have just entered an InNOut Burger!", "You made it back to where you live! You win!");


        outside.setExit("key", kitchen);
        kitchen.setExit("forward", bowlingAlley);
        bowlingAlley.setExit("forward", button);
        button.setExit("button", spikes);
        button.setExit("forward", Transylvania);
        Transylvania.setExit("forward", InNOut);

        Item obj = new Item();
        Item obj2 = new Item();
        Item obj3 = new Item();

        outside.setItem("key", obj);
        kitchen.setItem("knife", obj2);
        bowlingAlley.setItem("ball", obj3);


        currentRoom = outside;

    }

    public void play() {
        printWelcome();


        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);

        }
        System.out.println(("Thanks for playing"));
    }

    private boolean processCommand(Command command) {
        wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
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
            case PUSH:
                push(command);
                break;
            case USE:
                use(command);
                break;
            case ROLL:
                roll(command);
                break;
            case STAB:
                stab(command);
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

    private void look(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("You can't look at " + command.getSecondWord());
            return;
        }
        System.out.println(currentRoom.getLongDescription());
        System.out.println(player.getItemString());
    }

    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go
            System.out.println("Go Where?");
            return;
        }

        String direction = command.getSecondWord();

        //try to leave current room
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("Can't go this way!");
        }

        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getShortDescription());

        }
    }

    private void push(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("push what?");
            return;
        }

        String direction = command.getSecondWord();

        //try to leave current room
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("You can't push that!");
        } else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getShortDescription());
            System.out.println(currentRoom.getLongDescription());
            wantToQuit = true;
        }
    }

    private void stab(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("stab what?");
            return;
        }

        String stabKnife = command.getSecondWord();


        if (stabKnife == null) {
            System.out.println("You can't stab that!");
        }
        else if (!player.getInv().containsKey("knife")) {
            System.out.println("You tried to attack the vampires without a weapon. YOU DIED!");
            wantToQuit = true;
        }
        else{
            System.out.println("You just killed the king vampire. You are now the new king of Transylvania! The next room is now open..");
            }
        }


        private void roll (Command command){
            if (!command.hasSecondWord()) {
                System.out.println("roll what?");
                return;
            }

            String rollBall = command.getSecondWord();

            //try to leave current room


            if (rollBall == null) {
                System.out.println("You can't roll that!");
            } else if (!player.getInv().containsKey("ball")) {
                System.out.println("You need to grab the ball to roll it");
            } else {
                System.out.println("You rolled a strike! The next room is now open!");


            }
        }
        private void use(Command command){
            if (!command.hasSecondWord()) {
                System.out.println("use what?");
                return;
            }

            String direction = command.getSecondWord();

            //try to leave current room
            Room nextRoom = currentRoom.getExit(direction);

            if (nextRoom == null) {
                System.out.println("You can't use that!");
            } else if (!player.getInv().containsKey("key")) {
                System.out.println("The door is locked, you need a key");
            } else {
                currentRoom = nextRoom;
                System.out.println(currentRoom.getShortDescription());
            }
        }

        private void grab (Command command){
            if (!command.hasSecondWord()) {
                System.out.println("Grab what?");
                return;
            }
            String key = command.getSecondWord();

            Item grabItem = currentRoom.getItem(key);
            if (grabItem == null) {
                System.out.println("You can't grab " + command.getSecondWord());
            } else {
                player.setItem(key, grabItem);
            }
        }


        private void drop (Command command){
            if (!command.hasSecondWord()) {
                System.out.println("Drop what?");
                return;
            }
            String key = command.getSecondWord();

            Item dropItem = player.getItem(key);
            if (dropItem == null) {
                System.out.println("You can't drop " + command.getSecondWord());
            } else {
                currentRoom.setItem(key, dropItem);
            }
        }


        private boolean quit (Command command) {
            if (command.hasSecondWord()) {
                System.out.println("You can't quit " + command.getSecondWord());
                return false;
            } else {
                return true;
            }
        }
        private void printWelcome() {
            System.out.println();
            System.out.println("Welcome to my text adventure game!");
            System.out.println("You find yourself in a crazy random world where you are dying of hunger!");
            System.out.println("Type \\‘help\\\" if you need assistance");
            System.out.println();
            System.out.println("You have no memory of what happened. You saw a flash of lights and you woke up to the sound of nature. You are not safe, take a look around.");
        }

    }






