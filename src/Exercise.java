
public class Exercise {
  
  private static int nextIDNumber = 1;  // unique id number generator
  private WorkoutType type;
  private String description;
  private final int EXERCISE_ID;
  
  /**
   * For testing purposes ONLY: this method will reset the ID number generator in the Exercise class
   * and should be called at the beginning of all tester methods, so that any Exercises created in
   * those methods have predictable ID numbers no matter which other methods have been called first.
   */
  protected static void resetIDNumbers() { Exercise.nextIDNumber = 1; }
  
  /**
   * Creates a new Exercise and initializes its instance fields type, description, and its unique
   * ID number
   * 
   * @param type the category of exercise to create. It can be any of the constant WorkoutTypes
   *             defined in the enum WorkoutType: WorkoutType.WARMUP, WorkoutType.PRIMARY, or 
   *             WorkoutType.COOLDOWN
   * @param description a short description of this exercise, for display purposes
   */
  public Exercise(WorkoutType type, String description) {
    this.EXERCISE_ID = Exercise.nextIDNumber++;
    this.description = description;
    this.type = type;
  }
  
  /**
   * Gets the category associated with this individual exercise
   * @return the type of this Exercise, as defined in the enum WorkoutType
   */
  public WorkoutType getType() { return this.type; }
  
  /**
   * Gets the unique ID number associated with this individual exercise
   * @return the individual ID number of this exercise
   */
  public int getExerciseID() { return this.EXERCISE_ID; }
  
  @Override
  public boolean equals(Object o) {
    if (o instanceof Exercise) {
      Exercise definitelyAnExercise = (Exercise) o;
      return this.EXERCISE_ID == definitelyAnExercise.EXERCISE_ID;
    }
    return false;
  }
  
  /**
   * Returns a string representation of this Exercise in the format "type: description (ID)"
   * @return a string representation of this Exercise
   */
  @Override
  public String toString() {
    return this.type.toString()+": "+this.description+" ("+this.EXERCISE_ID+")";
  }

}

/**
 * This enumeration contains the possible types of exercise to be used in this program.
 */
enum WorkoutType {
  WARMUP, PRIMARY, COOLDOWN;
}