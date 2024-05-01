package model;

import app.model.HabitService;
import app.model.Habit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class HabitServiceTest {

    @Test
    @DisplayName("Adding and deleting multiple habits")
    public void testAddAndDeleteHabit() {

      // Creating a HabitService class and adding 3 habits into it
      HabitService habitServiceClass = habitServiceSetup();

      // deleting 2 of the 3 habits added
      habitServiceClass.deleteHabit("Drink water");
      habitServiceClass.deleteHabit("Do sports");

      // leaving one habit in the list for the sake of testing

      Habit res = habitServiceClass.getListOfHabits().getLast();

      Habit exp = new Habit(3, "Meditate", "meditate for 10 minutes");

      // expected is the last habit that was added

      Assertions.assertEquals(res, exp);


      int resSize = habitServiceClass.getListOfHabits().size(); 
      int expSize = 1;

      //expected size of the list is 1

      Assertions.assertEquals(resSize, expSize);
    }

  @Test
  @DisplayName("Finding habit in habit list")
  public void testHabitFromList () {

    // Creating a HabitService class and adding 3 habits into it
    HabitService habitServiceClass = habitServiceSetup();

    // using the getHabitFromList() method to retrive a Habit
    Habit res = habitServiceClass.getHabitFromList("Do sports");
    Habit exp = new Habit(2, "Do sports", "run a 10k");

    Assertions.assertEquals(res, exp);


  }

  private HabitService habitServiceSetup() {
    HabitService habitServiceClass = new HabitService();
    Habit habit1 = new Habit(1, "Drink water", "drink 1000ml of water");
    Habit habit2 = new Habit(2, "Do sports", "run a 10k");
    Habit habit3 = new Habit(3, "Meditate", "meditate for 10 minutes");
    habitServiceClass.addHabit(habit1);
    habitServiceClass.addHabit(habit2);
    habitServiceClass.addHabit(habit3);

    return habitServiceClass;
  }

}
