import static org.junit.Assert.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.*;
import se.hkr.entities.Category;
import se.hkr.entities.Goal;
import se.hkr.entities.HabitReminder;
import se.hkr.entities.Quote;

import java.util.ArrayList;
import java.util.List;

public class EntityTests {
    private static SessionFactory sessionFactory;

    @BeforeClass
    public static void setUp() {
        // Initialize Hibernate session factory for testing
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
    }

    @AfterClass
    public static void tearDown() {
        // Close the session factory after all tests
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @Before
    public void beforeTest() {
        // Perform any setup before each test, such as clearing existing data
        clearDatabase();
    }

    @After
    public void afterTest() {
        // Perform any cleanup after each test, such as clearing created entities
        clearDatabase();
    }

    private void clearDatabase() {
        // Open a session and delete all entities
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        // Delete goals first
        session.createQuery("DELETE FROM Goal").executeUpdate();

        // Delete habit reminders
        session.createQuery("DELETE FROM HabitReminder").executeUpdate();

        // Delete other entities (Task, Quote, Category)
        session.createQuery("DELETE FROM Task").executeUpdate();
        session.createQuery("DELETE FROM Quote").executeUpdate();
        session.createQuery("DELETE FROM Category").executeUpdate();

        transaction.commit();
        session.close();
    }

    @Test
    public void testCreateGoalWithCategoryAndHabitReminder() {
        // Create a category entity
        Category category = new Category();
        category.setName("Studies");
        category.setDescription("This category covers study goals");

        // Save the category to the database
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(category);
        transaction.commit();
        session.close();

        // Get the ID of the saved category
        int categoryId = category.getId();

        // Create a habit reminder entity
        HabitReminder habitReminder = new HabitReminder();
        habitReminder.setName("Study Reminder");
        habitReminder.setDescription("Set aside 2-3h to study everyday");
        habitReminder.setFrequency(1);

        // Save the habit reminder to the database
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        session.save(habitReminder);
        transaction.commit();
        session.close();

        // Get the ID of the saved habit reminder
        int habitReminderId = habitReminder.getId();

        // Create a goal entity with related category and habit reminder
        Goal goal = new Goal();
        goal.setName("Pass Database Technique Exam");
        goal.setDescription("By passing this exam I will achieve 7.5 credit points");
        goal.setFrequency(1);
        goal.setCategory(category);
        goal.setHabitReminder(habitReminder);

        // Save the goal to the database
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        session.save(goal);
        transaction.commit();
        session.close();

        // Get the ID of the saved goal
        int goalId = goal.getId();

        // Verify that the goal and its related entities were created successfully
        session = sessionFactory.openSession();
        Goal retrievedGoal = session.get(Goal.class, goalId);
        session.close();
        assertNotNull(retrievedGoal);
        assertNotNull(retrievedGoal.getCategory());
        assertEquals(categoryId, retrievedGoal.getCategory().getId());
        assertNotNull(retrievedGoal.getHabitReminder());
        assertEquals(habitReminderId, retrievedGoal.getHabitReminder().getId());
        // Add more assertions as needed
    }

    @Test
    public void testCreateCategoryWithQuotes() {
        // Create a category entity with related quotes
        Category category = new Category();
        category.setName("Studies");
        category.setDescription("This category covers study goals");

        Quote quote1 = new Quote();
        quote1.setText("The beautiful thing about learning is that no one can take it away from you. - B.B.King");
        quote1.setCategory(category); // Set the category for the quote

        Quote quote2 = new Quote();
        quote2.setText("I find that the harder I work, the more luck I seem to have. - Thomas Jefferson");
        quote2.setCategory(category); // Set the category for the quote

        // Add the quotes to the category's quotes list
        var quotes = new ArrayList<Quote>();
        quotes.add(quote1);
        quotes.add(quote2);
        category.setQuotes(quotes);

        // Save the category to persist the quotes as well
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(category);
        transaction.commit();
        session.close();

        // Re-open the session to access the category and its quotes
        session = sessionFactory.openSession();
        Category retrievedCategory = session.get(Category.class, category.getId());
        assertNotNull(retrievedCategory);
        assertNotNull(retrievedCategory.getQuotes()); // Access the quotes collection eagerly
        assertEquals(2, retrievedCategory.getQuotes().size());
        session.close();
    }

    @Test
    public void testCreateHabitReminderWithCategoryAndGoals() {
        // Create a category entity
        Category category = new Category();
        category.setName("Studies");
        category.setDescription("This category covers study goals");

        // Save the category to the database
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(category);
        transaction.commit();
        session.close();

        // Get the ID of the saved category
        int categoryId = category.getId();

        // Create two goal entities
        Goal goal1 = new Goal();
        goal1.setName("Review Database Technique slides");
        goal1.setDescription("Lectures 1-5");
        goal1.setFrequency(1);

        Goal goal2 = new Goal();
        goal2.setName("Review Agile Development Methods slides");
        goal2.setDescription("Lectures 1-5");
        goal2.setFrequency(2);

        // Create a habit reminder entity with related category and goals
        HabitReminder habitReminder = new HabitReminder();
        habitReminder.setName("Lecture slides review");
        habitReminder.setDescription("Review lecture slides every week");
        habitReminder.setFrequency(1);
        habitReminder.setCategory(category);

        // Set the goals for the habit reminder
        List<Goal> goals = new ArrayList<>();
        goal1.setHabitReminder(habitReminder);
        goal2.setHabitReminder(habitReminder);

        goals.add(goal1);
        goals.add(goal2);
        habitReminder.setGoals(goals);

        // Save the habit reminder along with its relationships to the database
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        session.save(habitReminder);
        transaction.commit();
        session.close();

        // Get the IDs of the saved entities
        int goalId1 = goal1.getId();
        int goalId2 = goal2.getId();
        int habitReminderId = habitReminder.getId();

        // Re-open the session to verify the results
        session = sessionFactory.openSession();

        // Retrieve the habit reminder from the database
        habitReminder = session.get(HabitReminder.class, habitReminderId);

        // Verify that the habit reminder and its related entities were created successfully
        assertNotNull(habitReminder);
        assertNotNull(habitReminder.getCategory());
        assertEquals(categoryId, habitReminder.getCategory().getId());
        assertNotNull(habitReminder.getGoals());
        assertEquals(2, habitReminder.getGoals().size());
        assertTrue(habitReminder.getGoals().stream().anyMatch(goal -> goal.getId() == goalId1));
        assertTrue(habitReminder.getGoals().stream().anyMatch(goal -> goal.getId() == goalId2));

        // Close the session
        session.close();
    }
}

