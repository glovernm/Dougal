public class Employee {

  private String name;
  private String id;
  private boolean hasSwiped;
  private boolean isLate;
  private boolean isAdmin;

  public Employee() {
    name = null;
    id = null;
    hasSwiped = false;
    isLate = false;
    isAdmin = false;
  }

  public Employee(String name, String id, boolean isAdmin) {
    this();
    this.name = name;
    this.id = id;
    this.isAdmin = isAdmin;
  }

  public String getName()
  { return name; }

  public String getID()
  { return id; }

  public void setSwiped(boolean hasSwiped)
  { this.hasSwiped = hasSwiped; }

  public boolean getSwiped()
  { return hasSwiped; }

  public void setLate(boolean isLate)
  { this.isLate = isLate; }

  public boolean getLate()
  { return isLate; }

  public boolean getAdmin()
  { return isAdmin; }

  public String toString() {
    StringBuilder str = new StringBuilder();
    str.append("Name: " + name + "\n");
    str.append("PLU ID: " + id + "\n");
    str.append("Swiped in: " + hasSwiped + "\n");
    str.append("Late: " + isLate + "\n");
    str.append("Admin: " + isAdmin + "\n");
    return str.toString();
  }
}
