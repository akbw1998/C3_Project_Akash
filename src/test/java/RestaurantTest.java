
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class RestaurantTest {
    Restaurant restaurant;

    //REFACTOR ALL THE REPEATED LINES OF CODE

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time() {
        //WRITE UNIT TEST CASE HERE

        //ARRANGE PATH 1: all times in same day.
        LocalTime openingTime = LocalTime.parse("10:00:00");
        LocalTime closingTime = LocalTime.parse("20:00:00");
        restaurant = new Restaurant("some_name", "some_location", openingTime, closingTime);
        Restaurant spiedRestaurant = Mockito.spy(restaurant);
        Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("15:00:00"));
        //act:
        boolean returnValue = spiedRestaurant.isRestaurantOpen();
        //assert:
        assertTrue(returnValue);

        //-------------------------------------------X------------------------------------------------

        //ARRANGE PART 2: current and opening times in same day, closing time in next day
        openingTime = LocalTime.parse("20:00:00");
        closingTime = LocalTime.parse("06:00:00");
        restaurant = new Restaurant("some_name", "some_location", openingTime, closingTime);
        spiedRestaurant = Mockito.spy(restaurant);
        Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("22:00:00"));
        //act:
        returnValue = spiedRestaurant.isRestaurantOpen();
        //assert:
        assertTrue(returnValue);

        //-------------------------------------------X--------------------------------------------------

        //ARRANGE PATH 3: current and closing time in next day, opening time in previous day:
        openingTime = LocalTime.parse("22:00:00");
        closingTime = LocalTime.parse("06:00:00");
        restaurant = new Restaurant("some_name", "some_location", openingTime, closingTime);
        spiedRestaurant = Mockito.spy(restaurant);
        Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("02:00:00"));
        //act:
        returnValue = spiedRestaurant.isRestaurantOpen();
        //assert:
        assertTrue(returnValue);
    }
        @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        //arrange:
        LocalTime openingTime = LocalTime.now().plusHours(6);
        LocalTime closingTime = LocalTime.now().plusHours(7);
        restaurant = new Restaurant("some_name","some_location",openingTime,closingTime);

        //act:
        boolean returnValue = restaurant.isRestaurantOpen();

        //assert:
        assertFalse(returnValue);
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @BeforeEach
    public void beforeEach(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
    /*
        Refactored repeating lines into @BeforeEach

        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    */
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
    /*
        Refactored repeated lines into @BeforeEach

        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    */
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
    /*
        Refactored repeating lines into @BeforeEach

        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    */
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

//<<<<<<<<<<<<<<<<<<<<DISPLAY SELECTED ITEM PRICES>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    /* REQUIREMENTS:
    In the stub code, you need to add another method that
     returns the order value,
     given the name of the items in <String> format.
      only purpose of this feature should be to display the order total
      Could be <methodName>(<itemName1>,<itemName2>,<itemName3>,...)
      Could be <methodName>(<list of itemNames>)
      The name of the item returned when the user selects the item is always in the menu
      You may add any attribute if necessary, you may add more than one method too, if necessary.
      You would have to figure out where to place these attributes, methods and test methods. You should also decide on their visibility.

     */

    @Test
    public void selecting_non_zero_items_from_menu_should_display_total_price_of_selected_items(){

        //arrange:
        restaurant = new Restaurant("some restaurant",null,null,null);

        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        restaurant.addToMenu("Tomato Soup",150);
        restaurant.addToMenu("Veggie Delight Pizza",350);
        restaurant.addToMenu("Pepperoni Pizza", 450);

        ArrayList<String> selectedItems = new ArrayList<String>();
        selectedItems.add("Sweet corn soup");
        selectedItems.add("Pepperoni Pizza");

        //act:
        int totalOrderValue = restaurant.displayOrderValue(selectedItems);

        //assert:
        assertEquals(totalOrderValue,569);
    }

    @Test
    public void selecting_zero_items_from_menu_should_display_total_price_equal_to_zero(){

        //arrange:
        restaurant = new Restaurant("some restaurant",null,null,null);

        ArrayList <String> selectedItems = new ArrayList<String>();

        //act:
        int totalOrderValue = restaurant.displayOrderValue(selectedItems);

        //assert:
        assertEquals(totalOrderValue,0);
    }
}