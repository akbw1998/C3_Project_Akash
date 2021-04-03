
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private String name;
    private String location;
    public LocalTime openingTime;
    public LocalTime closingTime;
    private List<Item> menu = new ArrayList<Item>();

    public Restaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        this.name = name;
        this.location = location;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public boolean isRestaurantOpen() {
        // following evaluation is done due to 24 hour format of LocalTime objects and no distinction between dates:

        LocalTime currentTime = getCurrentTime();
        if(closingTime.isBefore(openingTime)){     // if opening time is in previous day & closing time is in next day:
            if(currentTime.isBefore(closingTime) && currentTime.isBefore(openingTime))  // and current time is in next day between the opening and closing:
                return true;
            else if((currentTime.compareTo(openingTime)>=0) && currentTime.isAfter(closingTime)) // and current time is in previous day between opening and closing (or equal to opening):
                return true;
        }
        else if(currentTime.isBefore(closingTime) && (currentTime.compareTo(openingTime)>=0))  // when all times are in same day, and current time is between opening and closing (or equal to opening):
        {
            System.out.println(currentTime);
            return true;}

        return false;
    }

    public LocalTime getCurrentTime(){ return  LocalTime.now(); }

    public List<Item> getMenu() { return menu; }
        //return null;
        //DELETE ABOVE RETURN STATEMENT AND WRITE CODE HERE
    //}

    private Item findItemByName(String itemName){
        for(Item item: menu) {
            if(item.getName().equals(itemName))
                return item;
        }
        return null;
    }

    public void addToMenu(String name, int price) {
        Item newItem = new Item(name,price);
        menu.add(newItem);
    }
    
    public void removeFromMenu(String itemName) throws itemNotFoundException {

        Item itemToBeRemoved = findItemByName(itemName);
        if (itemToBeRemoved == null)
            throw new itemNotFoundException(itemName);

        menu.remove(itemToBeRemoved);
    }
    public void displayDetails(){
        System.out.println("Restaurant:"+ name + "\n"
                +"Location:"+ location + "\n"
                +"Opening time:"+ openingTime +"\n"
                +"Closing time:"+ closingTime +"\n"
                +"Menu:"+"\n"+getMenu());

    }

    public String getName() {
        return name;
    }
}
