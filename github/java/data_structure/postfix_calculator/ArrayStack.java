public class ArrayStack implements Stack {
  public static final int CAPACITY=100;

  private String[] data;

  private int t = -1;

  public ArrayStack() { this(CAPACITY); }

  public ArrayStack(int capacity) {
    data = new String[capacity]; 
  }

  public int size() { return (t + 1); }

  public boolean isEmpty() { return (t == -1); }

  public void push(String e) throws IllegalStateException {
    if (size() == data.length) throw new IllegalStateException("Stack is full");
    data[++t] = e;
  }

  public String top() {
    if (isEmpty()) return null;
    return data[t];
  }

  public String pop() {
    if (isEmpty()) return null;
    String answer = data[t];
    data[t] = null;                        
    t--;
    return answer;
  }

  public void removeTop() {
    if (!isEmpty()){
      data[t] = null;   
      t--;                    
    }
  }
}