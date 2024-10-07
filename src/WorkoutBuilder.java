
import java.util.NoSuchElementException;

public class WorkoutBuilder implements ListADT<Exercise>{
  private int cooldownCount; // The number of exercises with WorkoutType equal 
  //to COOLDOWN in this WorkoutBuilder list
  private LinkedExercise head; // The node containing the element at index 0 of 
  //this singly-linked list
  private int primaryCount; //The number of exercises with WorkoutType equal to PRIMARY in 
  //this WorkoutBuilder list
  private int size; // The total number of exercises contained in this WorkoutBuilder list
  private LinkedExercise tail; // The node containing the last element of this singly-linked list
  private int warmupCount; //The number of exercises with WorkoutType equal to WARMUP in 
  //this WorkoutBuilder list
  
  
  /**
   * Accesses the total number of elements in this WorkoutBuilder list
   * 
   * @return the size of this list
   */
  @Override
  public int size() {
    return size;
  }
  
  /**
   * Accesses the number of warm-up exercises stored in this WorkoutBuilder list
   * 
   * @return the count of exercises with WorkoutType equal to WARMUP
   */
  public int getWarmupCount() {
    return warmupCount;
  }
  
  /**
   * Accesses the number of primary exercises stored in this WorkoutBuilder list
   * 
   * @return the count of exercises with WorkoutType equal to PRIMARY
   */
  public int getPrimaryCount() {
    return primaryCount;
  }
  
  /**
   * Accesses the number of cool-down exercises stored in this WorkoutBuilder list
   * 
   * @return the count of exercises with WorkoutType equal to COOLDOWN
   */
  public int getCooldownCount() {
    return cooldownCount;
  }
  
  /**
   * Checks whether this WorkoutBuilder list is empty
   * 
   * @return true if this list contains no elements and neither its head nor tail refer to 
   * LinkedExercise objects
   */
  @Override
  public boolean isEmpty() {
    if(size == 0 && head == null && tail == null) {
      return true;
    }
    return false;
  }
  
  /**
   * Removes all elements from this list. The list will be empty after this call returns.
   */
  public void clear() {
    head = null;
    tail = null;
    size = 0;
    primaryCount = 0;
    cooldownCount = 0;
    warmupCount = 0;
  }
  
  /**
   * Finds the index of a given exercise in this WorkoutBuilder list, if it is present. 
   * Note that Exercise contains an overridden equals() method for use here.
   * OPTIONAL IMPLEMENTATION CHALLENGE: check as few nodes as possible.
   * 
   * @param findObject - the exercise to search for in this list
   * @return the index of this object in the list if it is present; -1 if it is not
   */
  @Override
  public int indexOf(Exercise findObject) {
    if(head == null) { // list is empty
      return -1;
    }
    int index = 0;
    boolean flag = false;
    LinkedExercise curr = head;
    while(curr != null) {
      if(curr.getExercise().equals(findObject)) {
        flag = true;
        break;
      }
      curr = curr.getNext();
      index++;
    }
    if(flag == false) { // element not in list
      return -1; 
    }
    return index;
  }
  
  /**
   * Returns the exercise stored at the given index of this list without removing it.
   * 
   * @param index - position within this list
   * @return the exercise stored at the given index of this list
   * @throws IndexOutOfBoundsException - with a descriptive error message 
   * if the index is not valid for the current size of this list
   */
  @Override
  public Exercise get(int index) {
    if(index >= size() || index < 0 || isEmpty()) { // invalid index
      throw new IndexOutOfBoundsException("Index is invalid!");
    }
    LinkedExercise curr = head;
    int i = 0;
    while(curr != null && index != i) {
      curr = curr.getNext();
      i++;
    }
    return curr.getExercise(); // exercise at given index
  }
  
  /**
   * Adds the provided Exercise to the appropriate position in the list for its WorkoutType, 
   * and increments the corresponding counter fields:
   *    WARMUP: adds to the FRONT (head) of the list
   *    PRIMARY: adds after all warm-ups and before any cool-downs; if there are any existing 
   *    primary exercises, adds after all of those as well *** NOT TRUE, REDOWNLOAD
   *    COOLDOWN: adds to the END (tail) of the list
   * We recommend implementing private helper methods for each of these cases, 
   * but this is not required. Remember to consider the case where you are adding 
   * the very first exercise to the list!
   * 
   * @param newObject - the exercise to add to the WorkoutBuilder list
   */
  @Override
  public void add(Exercise newObject) {
    LinkedExercise newExercise = new LinkedExercise(newObject);
    
    // adding to an empty list
    
    if(size == 0) {
      head = newExercise;
      tail = newExercise;
      // incrementing the appropriate counter
      if(newExercise.getExercise().getType() == WorkoutType.PRIMARY) {
        primaryCount++;
      }
      else if(newExercise.getExercise().getType() == WorkoutType.WARMUP) {
        warmupCount++;
      }
      else if(newExercise.getExercise().getType() == WorkoutType.COOLDOWN) {
        cooldownCount++;
      }
      size++;
    }
    
    // adding a warmup exercise to the beginning of a non-empty the list
    
    else if(newObject.getType() == WorkoutType.WARMUP) {
      newExercise.setNext(head);
      head = newExercise;
      warmupCount++;
      size++;
    }
    
    // adding a cooldown exercise to the end of a non-empty list
    
    else if(newObject.getType() == WorkoutType.COOLDOWN) {
      if(size == 1) { // list has only one element, head node must link with tail
        tail.setNext(newExercise);
        tail = tail.getNext();
        head.setNext(tail);
        cooldownCount++;
        size++;
      }
      else { // list has more than one element
        tail.setNext(newExercise);
        tail = tail.getNext();
        cooldownCount++;
        size++;
      }
    }
    
    // adding a primary exercise after warmup and before existing primary and cooldown exercises
    
    else if(newObject.getType() == WorkoutType.PRIMARY) {
      addPrimary(newObject);
    }
  }
  
  /**
   * Helper method for add: adds an exercise of WorkoutType PRIMARY to non-empty list.
   * 
   * @param newObject - PRIMARY exercise to be added
   */
  private void addPrimary(Exercise newObject) {
    LinkedExercise primaryExercise = new LinkedExercise(newObject);
    LinkedExercise curr = head;
    
    // adding a primary exercise to the beginning of a list
    
    if(head.getExercise().getType() == WorkoutType.COOLDOWN 
        || head.getExercise().getType() == WorkoutType.PRIMARY) {
      primaryExercise.setNext(head);
      head = primaryExercise;
      primaryCount++;
      size++;
      return;
    }
    
    // adding a primary exercise to the end of a list
    
    while(curr.getNext() != null && curr.getNext().getExercise().getType() != WorkoutType.PRIMARY
        && curr.getNext().getExercise().getType() != WorkoutType.COOLDOWN) {
      curr = curr.getNext();
    }
    
    // adding a primary exercise to the end of a list
    
    if(curr.getNext() == null) {
      curr.setNext(primaryExercise);
      primaryCount++;
      size++;
      return;
    }
    
    // adding a primary exercise to the middle of a list
    
    primaryExercise.setNext(curr.getNext());
    curr.setNext(primaryExercise);
    primaryCount++;
    size++;
  }
  
  /**
   * Removes an exercise of the provided type from the list, if one exists, and decrements the 
   * corresponding counter fields:
   *    WARMUP: removes the FIRST (head) element from the list
   *    PRIMARY: removes the FIRST exercise of type PRIMARY from the list
   *    COOLDOWN: removes the LAST (tail) element from the list
   * You are encouraged to implement private helper methods for each of these cases as well, 
   * but this is not required. 
   * Be sure to check that there are any exercises with the given type present 
   * in the list, and remember to consider the case where you are removing the very last exercise 
   * from the entire list!
   * 
   * @param type - the type of exercise to remove from the list
   * @return the exercise object that has been removed from the list
   * @throws NoSuchElementException - if there are no exercises in the list 
   * with the given WorkoutType
   */
  public Exercise removeExercise(WorkoutType type) throws NoSuchElementException {
    // invalid exercise type     *** keep?
    if(type != WorkoutType.PRIMARY && type != WorkoutType.COOLDOWN && type != WorkoutType.WARMUP) {
      throw new NoSuchElementException("Invalid exercise type!");
    }
    if(size == 0) { // empty list: cannot remove anything
      throw new NoSuchElementException("List is empty!");
    }
    LinkedExercise removedExercise = new LinkedExercise(null);
    
    // removing warmup exercise
    
    if(type == WorkoutType.WARMUP) {
      if(head.getExercise().getType() != WorkoutType.WARMUP) { // No warmup exercises in the list
        throw new NoSuchElementException("No WARMUP exercises are in the list!");
      }
      if(size == 1) { // only one element: tail and head point to the same exercise
        removedExercise = head;
        head = null;
        tail = null;
        warmupCount--;
        size--;
        return removedExercise.getExercise();
      }
      removedExercise = head;
      head = head.getNext();
      warmupCount--;
      size--;
      return removedExercise.getExercise();
    }
    
    // removing cooldown exercise
    
    else if(type == WorkoutType.COOLDOWN) {
      return removeCooldown();
    }
    
    // removing primary exercise
    
    return removePrimary();
  }
  
  /**
   * Helper method for removeExercise which removes an exercise of type PRIMARY.
   * 
   * @return the PRIMARY exercise that was removed 
   */
  private Exercise removePrimary() {
    LinkedExercise curr = head;
    LinkedExercise removedExercise = new LinkedExercise(null);
    if(size == 1) { // only one element: tail and head point to the same exercise
      removedExercise = head;
      if(removedExercise.getExercise().getType() != WorkoutType.PRIMARY) {
        throw new NoSuchElementException("No PRIMARY exercises are in the list!");
      }
      head = null;
      tail = null;
      primaryCount--;
      size--;
      return removedExercise.getExercise();
    }
    
    // removing primary exercise from the beginning of the list
    
    if(head.getExercise().getType() == WorkoutType.PRIMARY) {
      removedExercise = head;
      head = head.getNext();
      primaryCount--;
      size--;
      return removedExercise.getExercise();
    }
    
    // removing primary exercise from the middle or end of the list
    
    while(curr.getNext().getNext() != null && 
        curr.getNext().getExercise().getType() != WorkoutType.PRIMARY ) {
      curr = curr.getNext();
    }
    if(curr.getNext().getExercise().getType() != WorkoutType.PRIMARY) {
      throw new NoSuchElementException("No PRIMARY exercises are in the list!");
    }
    removedExercise = curr.getNext();
    curr.setNext(curr.getNext().getNext());
    primaryCount--;
    size--;
    return removedExercise.getExercise();
  }
  
  /**
   * Helper method for removeExercise which removes an exercise of type COOLDOWN.
   * 
   * @return the COOLDOWN exercise that was removed 
   */
  private Exercise removeCooldown() {
    if(tail.getExercise().getType() != WorkoutType.COOLDOWN) {
      throw new NoSuchElementException("No COOLDOWN exercises are in the list!");
    }
    LinkedExercise curr = head;
    LinkedExercise removedExercise = new LinkedExercise(null);
    if(size == 1) { // only one element: tail and head point to the same exercise
      removedExercise = tail;
      head = null;
      tail = null;
      cooldownCount--;
      size--;
      return removedExercise.getExercise();
    }
    
    // removing cooldown exercise from list with more than one element
    
    while(curr.getNext().getNext() != null) {
      curr = curr.getNext();
    }
    removedExercise = curr.getNext();
    tail = curr;
    tail.setNext(null);
    cooldownCount--;
    size--;
    return removedExercise.getExercise();
  }

  /**
   * Removes the exercise with the provided ID number from the list, if it is present, 
   * and adjusts any corresponding counter fields as necessary. 
   * This method uses a linear search algorithm.
   * 
   * @param exerciseID - the unique identifier of the exercise to be removed
   * @return the exercise object that has been removed from the list
   * @throws NoSuchElementException - if no exercises in the list match 
   * the provided exerciseID number
   */
  public Exercise removeExercise(int exerciseID) throws NoSuchElementException {
    // checks if an exercise with given ID exists in the list
    if(!idPresent(exerciseID)) {
      throw new NoSuchElementException("ERROR: " + exerciseID + " is not present in the list!");
    }
    LinkedExercise curr = head;
    LinkedExercise removedExercise = new LinkedExercise(null);
    if(size == 1) { // only one element: tail and head point to the same exercise
      removedExercise = head;
      head = null;
      tail = null;
    }
 
    // removing exercise from list with more than one element
    
    else if(head.getExercise().getExerciseID() == exerciseID) { // head must be removed
      removedExercise = head;
      head = head.getNext();
    }
    
    else if(tail.getExercise().getExerciseID() == exerciseID) { // tail must be removed
      removedExercise = tail;
      while(curr.getNext().getNext() != null) {
        curr = curr.getNext();
      }
      tail = curr;
      tail.setNext(null);
    }
    
    else { // removing exercise from the middle of the list
      while(curr.getNext().getExercise().getExerciseID() != exerciseID) {
        curr = curr.getNext();
      }
      removedExercise = curr.getNext();
      curr.setNext(curr.getNext().getNext());
    }
    
    // checks the type of the exercise and changes appropriate counter value
    
    if(removedExercise.getExercise().getType() == WorkoutType.COOLDOWN) {
      cooldownCount--;
      size--;
    }
    else if(removedExercise.getExercise().getType() == WorkoutType.WARMUP) {
      warmupCount--;
      size--;
    }
    else if(removedExercise.getExercise().getType() == WorkoutType.PRIMARY) {
      primaryCount--;
      size--;
    }
    
    return removedExercise.getExercise();
  }
  
  /**
   * Helper method for removeExercise which checks if an exercise exists in the list with the 
   * passed exerciseID parameter.
   * 
   * @param exerciseID - the ID that was passed to the removeExercise method
   * @return true if the ID exists in the list, false otherwise
   */
  private boolean idPresent(int exerciseID) {
    LinkedExercise curr = head;
    while(curr != null) {
      if(curr.getExercise().getExerciseID() == exerciseID) {
        return true;
      }
      curr = curr.getNext();
    }
    return false;
  }
  
  /**
   * Returns a String representation of the contents of this list, 
   * as the concatenated String representations of all LinkedExercise nodes in this list. 
   * See the sample output at the end of the writeup for examples.
   * 
   * @return return a String representation of the content of this list. If this list is empty, 
   * an empty String ("") will be returned.
   */
  @Override
  public String toString() {
    if(size == 0) {
      return "";
    }
    LinkedExercise curr = head;
    String list = "";
    while(curr != null) {
      list += curr.toString();
      curr = curr.getNext();
    }
    return list;
  }
  
  public static void main(String args[]) {
    WorkoutBuilder e = new WorkoutBuilder();
    e.add(new Exercise(WorkoutType.PRIMARY, "curl"));
    e.add(new Exercise(WorkoutType.COOLDOWN, "walk"));
    e.add(new Exercise(WorkoutType.WARMUP, "jog"));
    e.add(new Exercise(WorkoutType.PRIMARY, "pushup"));
    int index1 = e.indexOf(e.get(2));
    if(index1 != 2) {
      System.out.println("invalid index");
    }
    else {
      System.out.println("pass");
    }
    index1 = e.indexOf(e.get(3));
    if(index1 != 3) {
      System.out.println("invalid index");
    }
    else {
      System.out.println("pass");
    }
  }
}
