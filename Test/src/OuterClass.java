
public class OuterClass{

    private int outerData;

    public OuterClass(int data){
        outerData = data;
    }

    public void outerMethod(){
        InnerClass inner = new InnerClass();
        inner.innerMethod();
    }

    public class InnerClass{
        public void innerMethod(){
            System.out.println("Access outerData from InnerClass " + outerData);
        }
    }
}