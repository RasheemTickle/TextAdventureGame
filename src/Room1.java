public class Room {
    private String description;
    private HashMap<String, Room>exits;

    public Room(String description) {
        this.description = description;
        extis = new HashMap<String, Room>();

    }
    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }
    public Room getExit(String direction, Room neighbor) {
        return extis.put(direction, neighbor);
    }
    public String getShortDescription() {
        return description;
    }
}
