package lab2;

import java.util.*;

public class State {
    
    public static final int MAX_ALP = 256;

    TreeSet<Integer>[] next;
        
    public State()
    {
        this.next = new TreeSet[MAX_ALP];
        for (int i = 0; i < MAX_ALP; i++)
            this.next[i] = new TreeSet<Integer>();
    }
    
    public void add(int ch, int to)
    {
        next[ch].add(to);
    }
    
    public void out()
    {
        for (int ch = 0; ch < MAX_ALP; ch++)
        {
            if (next[ch].isEmpty())
                continue;
            char c = (char) (ch + 'a');
            System.out.print(c + " : ");
            Iterator it = next[ch].iterator();
            while (it.hasNext())
            {
                System.out.print(it.next() + " ");
            }
            System.out.println();
        }                        
    }
}
