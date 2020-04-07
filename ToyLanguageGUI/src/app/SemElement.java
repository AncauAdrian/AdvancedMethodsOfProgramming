package app;


public class SemElement {
    int index, value;
    String threads;

    public SemElement(int index, int value, String threads)
    {
        this.index = index;
        this.value = value;
        this.threads = threads;
    }

    public int getIndex(){
        return this.index;
    }

    public int getValue()
    {
        return this.value;
    }

    public String getThreads()
    {
        return this.threads;
    }
}
