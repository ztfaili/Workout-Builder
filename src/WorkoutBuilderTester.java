
import java.util.NoSuchElementException;

public class WorkoutBuilderTester {
  
  // checks for the correctness of the WorkoutBuilder.clear() method
 public static boolean testClear() {
   Exercise.resetIDNumbers();
   WorkoutBuilder b = new WorkoutBuilder();
   b.add(new Exercise(WorkoutType.PRIMARY, "run")); 
   b.add(new Exercise(WorkoutType.WARMUP, "jog")); 
   b.add(new Exercise(WorkoutType.PRIMARY, "press")); 
   b.clear();
   Exercise.resetIDNumbers();
   if(!b.toString().equals("") || !(b.getCooldownCount() == 0) || !(b.getPrimaryCount() == 0) 
       || !(b.getWarmupCount() == 0) || !b.isEmpty()) {
     return false;
   }
   return true;
 }

 // checks for the correctness of the WorkoutBuilder.add() method
 public static boolean testAddExercises() {
   Exercise.resetIDNumbers();
   WorkoutBuilder b = new WorkoutBuilder();
   b.add(new Exercise(WorkoutType.PRIMARY, "run")); // (1)
   b.add(new Exercise(WorkoutType.WARMUP, "jog")); // (2)
   b.add(new Exercise(WorkoutType.PRIMARY, "press")); // (3)
   b.add(new Exercise(WorkoutType.COOLDOWN, "walk")); // (4)
   b.add(new Exercise(WorkoutType.WARMUP, "bicep curls")); // (5)
   b.add(new Exercise(WorkoutType.COOLDOWN, "pushups")); // (6)
   Exercise.resetIDNumbers();
   if(!b.toString().equals("WARMUP: bicep curls (5) -> WARMUP: jog (2) -> PRIMARY: press (3) -> "
       + "PRIMARY: run (1) -> COOLDOWN: walk (4) -> COOLDOWN: pushups (6) -> END")) {
     return false;
   }
   return true;
 }

 // checks for the correctness of BOTH of the WorkoutBuilder.removeExercise() methods
 public static boolean testRemoveExercises() {
   Exercise.resetIDNumbers();
   WorkoutBuilder b = new WorkoutBuilder();
   b.add(new Exercise(WorkoutType.PRIMARY, "run")); // (1)
   b.add(new Exercise(WorkoutType.WARMUP, "jog")); // (2)
   b.add(new Exercise(WorkoutType.PRIMARY, "press")); // (3)
   b.add(new Exercise(WorkoutType.COOLDOWN, "walk")); // (4)
   b.add(new Exercise(WorkoutType.WARMUP, "bicep curls")); // (5)
   b.add(new Exercise(WorkoutType.COOLDOWN, "pushups")); // (6)
   

   // current list: "WARMUP: bicep curls (5) -> WARMUP: jog (2) -> PRIMARY: press (3) -> "
   //+ "PRIMARY: run (1) -> COOLDOWN: walk (4) -> COOLDOWN: pushups (6) -> END"
   
   // removing WARMUP with ID
   
   b.removeExercise(2);
   if(!b.toString().equals("WARMUP: bicep curls (5) -> PRIMARY: press (3) -> "
       + "PRIMARY: run (1) -> COOLDOWN: walk (4) -> COOLDOWN: pushups (6) -> END")
       || b.size() != 5 || b.getWarmupCount() != 1) {
     return false;
   }
   
   b.add(new Exercise(WorkoutType.WARMUP, "jog")); // (7)
   
   // removing WARMUP with type
   
   b.removeExercise(WorkoutType.WARMUP);
   if(!b.toString().equals("WARMUP: bicep curls (5) -> PRIMARY: press (3) -> "
       + "PRIMARY: run (1) -> COOLDOWN: walk (4) -> COOLDOWN: pushups (6) -> END")
       || b.size() != 5 || b.getWarmupCount() != 1) {
     return false;
   }

   // removing PRIMARY with ID
   
   b.removeExercise(3);
   if(!b.toString().equals("WARMUP: bicep curls (5) -> "
       + "PRIMARY: run (1) -> COOLDOWN: walk (4) -> COOLDOWN: pushups (6) -> END")
       || b.size() != 4 || b.getPrimaryCount() != 1) {
     return false;
   }
   
   // removing PRIMARY with type
   
   b.removeExercise(WorkoutType.PRIMARY);
   if(!b.toString().equals("WARMUP: bicep curls (5) -> "
       + "COOLDOWN: walk (4) -> COOLDOWN: pushups (6) -> END")
       || b.size() != 3 || b.getPrimaryCount() != 0) {
     return false;
   }
   
   // removing COOLDOWN with ID
   
   b.removeExercise(6);
   if(!b.toString().equals("WARMUP: bicep curls (5) -> "
       + "COOLDOWN: walk (4) -> END")
       || b.size() != 2 || b.getCooldownCount() != 1) {
     return false;
   }
   
   // removing COOLDOWN with type
   b.removeExercise(WorkoutType.COOLDOWN);
   if(!b.toString().equals("WARMUP: bicep curls (5) -> "
       + "END")
       || b.size() != 1 || b.getCooldownCount() != 0) {
     return false;
   }
   Exercise.resetIDNumbers();
   return true;
 }

 // checks for the correctness of the WorkoutBuilder.get() method
 public static boolean testGetExercises() {
   Exercise.resetIDNumbers();
   WorkoutBuilder b = new WorkoutBuilder();
   b.add(new Exercise(WorkoutType.PRIMARY, "run")); // (1)
   b.add(new Exercise(WorkoutType.WARMUP, "jog")); // (2)
   b.add(new Exercise(WorkoutType.PRIMARY, "press")); // (3)
   b.add(new Exercise(WorkoutType.COOLDOWN, "walk")); // (4)
   b.add(new Exercise(WorkoutType.WARMUP, "bicep curls")); // (5)
   b.add(new Exercise(WorkoutType.COOLDOWN, "pushups")); // (6)
   
   // current list: "WARMUP: bicep curls (5) -> WARMUP: jog (2) -> PRIMARY: press (3) -> "
   //+ "PRIMARY: run (1) -> COOLDOWN: walk (4) -> COOLDOWN: pushups (6) -> END"
   
   Exercise get0 = b.get(0); // getting the head
   if(!get0.toString().equals("WARMUP: bicep curls (5)")) {
     return false;
   }
   
   Exercise get6 = b.get(5); // getting the tail
   if(!get6.toString().equals("COOLDOWN: pushups (6)")) {
     return false;
   }
   
   Exercise get3 = b.get(3);
   if(!get3.toString().equals("PRIMARY: run (1)")) {
     return false;
   }
   Exercise.resetIDNumbers();
   return true;
 }

 // a test suite method to run all your test methods
 public static boolean runAllTests() {
   boolean clear = testClear(), 
       add = testAddExercises(), 
       remove = testRemoveExercises(),
       get = testGetExercises();
   
   System.out.println("test clear: "+(clear?"pass":"FAIL"));
   System.out.println("test add: "+(add?"pass":"FAIL"));
   System.out.println("test remove: "+(remove?"pass":"FAIL"));
   System.out.println("test get: "+(get?"pass":"FAIL"));
   
   // TODO: add calls to any other test methods you write
   
   return clear == true && add == true && remove == true && get == true;
 }

 public static void main(String[] args) {
   runAllTests();
   demo();
 }

 /**
  * Helper method to display the size and the count of different boxes stored in a list of boxes
  * 
  * @param list a reference to an InventoryList object
  * @throws NullPointerException if list is null
  */
 private static void displaySizeCounts(WorkoutBuilder list) {
   System.out.println("  Size: " + list.size() + ", warmupCount: " + list.getWarmupCount()
       + ", primaryCount: " + list.getPrimaryCount() + ", cooldownCount: " + list.getCooldownCount());
 }

 /**
  * Demo method showing how to use the implemented classes in P07 Inventory Storage System
  * 
  * @param args input arguments
  */
 public static void demo() {
   // Create a new empty WorkoutBuilder object
   WorkoutBuilder list = new WorkoutBuilder();
   System.out.println(list); // prints list's content
   displaySizeCounts(list); // displays list's size and counts
   // Add a primary exercise to an empty list
   list.add(new Exercise(WorkoutType.PRIMARY, "5k run")); // adds PRIMARY: 5k run (1)
   System.out.println(list); // prints list's content
   list.add(new Exercise(WorkoutType.WARMUP, "stretch")); // adds WARMUP: stretch (2) at the head of the list
   System.out.println(list); // prints list's content
   list.add(new Exercise(WorkoutType.PRIMARY, "bench press")); // adds PRIMARY: bench press (3)
   System.out.println(list); // prints list's content
   list.add(new Exercise(WorkoutType.WARMUP, "upright row")); // adds WARMUP: upright row (4) at the head of the list
   System.out.println(list); // prints list's content
   list.add(new Exercise(WorkoutType.WARMUP, "db bench")); // adds WARMUP: db bench (5) at the head of the list
   System.out.println(list); // prints list's content
   displaySizeCounts(list); // displays list's size and counts
   // Add more exercises to list and display its contents
   list.add(new Exercise(WorkoutType.COOLDOWN, "stretch")); // adds COOLDOWN: stretch (6) at the end of the list
   System.out.println(list); // prints list's content
   displaySizeCounts(list); // displays list's size and counts
   list.add(new Exercise(WorkoutType.COOLDOWN, "sit-ups")); // adds COOLDOWN: sit-ups (7) at the end of the list
   System.out.println(list); // prints list's content
   displaySizeCounts(list); // displays list's size and counts
   list.removeExercise(WorkoutType.COOLDOWN); // removes COOLDOWN: sit-ups (7) from the list
   System.out.println(list); // prints list's content
   displaySizeCounts(list); // displays list's size and counts
   list.add(new Exercise(WorkoutType.PRIMARY, "deadlift")); // adds PRIMARY: deadlift (8)
   System.out.println(list); // prints list's content
   displaySizeCounts(list); // displays list's size and counts
   list.removeExercise(WorkoutType.COOLDOWN); // removes COOLDOWN: stretch (6) from the list
   System.out.println(list); // prints list's content
   displaySizeCounts(list); // displays list's size and counts
   list.removeExercise(WorkoutType.WARMUP); // removes WARMUP: db bench (5)
   System.out.println(list); // prints list's content
   list.removeExercise(3); // removes PRIMARY: bench press (3) from the list
   System.out.println(list); // prints list's content
   displaySizeCounts(list); // displays list's size and counts
   try {
     list.removeExercise(25); // tries to remove box #25
   } catch (NoSuchElementException e) {
     System.out.println(e.getMessage());
   }
   System.out.println(list); // prints list's content
   displaySizeCounts(list); // displays list's size and counts
   // remove all warm-ups
   while (list.getWarmupCount() != 0) {
     list.removeExercise(WorkoutType.WARMUP);
   }
   System.out.println(list); // prints list's content
   displaySizeCounts(list); // displays list's size and counts
   list.removeExercise(1); // removes PRIMARY: 5k run (1) from the list -> empty list
   System.out.println(list); // prints list's content
   displaySizeCounts(list); // displays list's size and counts
   list.add(new Exercise(WorkoutType.COOLDOWN, "walk")); // adds COOLDOWN: walk (9) to the list
   System.out.println(list); // prints list's content
   displaySizeCounts(list); // displays list's size and counts
   list.removeExercise(8); // removes PRIMARY: deadlift (8) from the list
   System.out.println(list); // prints list's content
   displaySizeCounts(list); // displays list's size and counts
   list.removeExercise(WorkoutType.COOLDOWN); // removes COOLDOWN: walk (9) from the list
   System.out.println(list); // prints list's content
   displaySizeCounts(list); // displays list's size and counts
   list.add(new Exercise(WorkoutType.WARMUP, "pull-up")); // adds WARMUP: pull-up (10) to the list
   System.out.println(list); // prints list's content
   displaySizeCounts(list); // displays list's size and counts
   list.removeExercise(10); // removes WARMUP: pull-up (10) from the list
   System.out.println(list); // prints list's content
   displaySizeCounts(list); // displays list's size and counts
 }

}
