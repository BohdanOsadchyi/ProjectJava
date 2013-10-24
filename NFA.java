package lab2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.TreeSet;

/*
* Nondeterministic Finite Automaton
*/

public class NFA {

    private final int alpsz, size, q0;
    private ArrayList<Integer> fin;
    private ArrayList<State> q;
    private boolean[] used, notdeadlock;

    public NFA(int _alpsz, int _size, int _q0)
    {
        this.alpsz  = _alpsz;
        this.size   = _size ;
        this.q0     = _q0   ;
        this.fin    = new ArrayList<Integer>();
        this.q      = new ArrayList<State>();
        for (int i = 0; i < _size; i++)
            q.add(new State());
        this.used        = new boolean[_size];
        this.notdeadlock = new boolean[_size];
    }
    
    public void addFinState(int st)
    {
        fin.add(st);
    }
    
    public void addEdge(int v, int ch, int to)
    {
        q.get(v).add(ch, to);
    }
    
    public void outState(int v)
    {
        q.get(v).out();
    }
    
    public void outAllStates()
    {
        System.out.println("States : ");
        for (int i = 0; i < size; i++)
        {
               System.out.println("(" + i + ")");
               outState(i);
        }

    }
    
    public void outFinStates()
    {
        System.out.print("FinalStates :" );
        Iterator it = fin.iterator();
        while (it.hasNext())
        {
            System.out.print(" " + it.next());
        }
        System.out.println();
    }
    
    public void out()
    {
        System.out.println("NFA OUT");
        System.out.println("(" + alpsz + ", " + size + ", " + q0 + ")");
        outFinStates();
        outAllStates();
    }
    
    private void dfs(int v)
    {
        used[v] = true;
        State st = this.q.get(v);
        for (int ch = 0; ch < this.alpsz; ch++)
        {
            Iterator it = st.next[ch].iterator();
            while(it.hasNext())
            {
                Integer to = (Integer)it.next();
                if (!used[to])
                {
                    used[to] = true;
                    dfs(to);
                }
                if (notdeadlock[to])
                    notdeadlock[v] = true;
            }
        }
    }
    
    
    
    public ArrayList<Integer> findNotUsed()
    {
        ArrayList<Integer> notUsed = new ArrayList<Integer>();
        
        for (int i = 0; i < fin.size(); i++)
            notdeadlock[fin.get(i)] = true;
        
        dfs(this.q0);
        
        for (int i = 0; i < this.size; i++)
        {
            if (!used[i] || !notdeadlock[i])
            {
                notUsed.add(i);
///                System.out.println("NOT USED : " + i);
            }
        
        }
        return notUsed;
    }
    
    private void outSet(TreeSet<Integer> s, int id)
    {
        System.out.print("Set(" + id + ", " + s.hashCode() + ") {");
        Iterator it = s.iterator();
        while(it.hasNext())
        {
            System.out.print(" " + it.next());
        }
        System.out.println(" } ");
    }
    
    private boolean isFinState(TreeSet<Integer> s)
    {
        Iterator<Integer> it = fin.iterator();
        Integer tmp;
        while(it.hasNext())
        {
            if (s.contains(it.next()))
                return true;
        }
        return false;
    }
    
    private TreeSet<Integer> getToSet(TreeSet<Integer> s, int ch, ArrayList<Integer> notUsed)
    {
        TreeSet<Integer> to = new TreeSet<Integer>();
        Iterator it = s.iterator();
        while (it.hasNext())
        {
            Integer v = (Integer) it.next();

            if (notUsed.contains(v))
                continue;

            State st = this.q.get(v);
            if (st.next[ch] != null)
                to.addAll(st.next[ch]);

        }
        to.removeAll(notUsed);
        return to;
    }
    
    private int findSet(TreeSet<Integer> s, ArrayList<TreeSet<Integer>> setState, HashMap<Integer, TreeSet<Integer>> hashmap)
    {
        //is not found the eq set return -1; else index(id)
        boolean contains = hashmap.containsKey(s.hashCode());
                        
        if (contains)   
        {
            Iterator itHash = hashmap.get(s.hashCode()).iterator();
            while(itHash.hasNext())
            {
                Integer id = (Integer) itHash.next();
                if (setState.get(id).equals(s))
                    return id;
            }
        }
        return -1;
    }
     
    public DFA toDFA()
    {
        ArrayList<Integer> notUsed = findNotUsed();
       
        ArrayList<TreeSet<Integer>> setState = new ArrayList<TreeSet<Integer>>();
        HashMap<Integer, TreeSet<Integer>> hashmap = new HashMap<Integer, TreeSet<Integer>>();
        TreeSet<Integer> set = new TreeSet<Integer>(), setHash = new TreeSet<Integer>();
        ArrayList<State> dfaStates = new ArrayList<State>();
        
        set.add(q0);
        setState.add(set);
        setHash.add(0);
        hashmap.put(set.hashCode(), setHash);
        
        for (int i = 0; i < setState.size(); i++)
        {
            State st = new State();
            for (int ch = 0; ch < this.alpsz; ch++)
            {
                TreeSet<Integer> toSet = getToSet(setState.get(i), ch, notUsed);
                if (toSet.isEmpty())
                    continue;
                boolean contains = hashmap.containsKey(toSet.hashCode());
                Integer to = null;
                int id = findSet(toSet, setState, hashmap);                
                if (id == -1)
                {
                    to = setState.size();
                    if (!contains)
                    {
                        setHash = new TreeSet<Integer>();
                        setHash.add(to);
                        hashmap.put(toSet.hashCode(), setHash);
                    }
                    else
                    {
                        hashmap.get(toSet.hashCode()).add(to);
                    }
                    setState.add(toSet);
                }
                else
                {
                    to = id;
                }
//                if (to != null)
                 st.add(ch, to);
            }
            dfaStates.add(st);
        }
        
        ArrayList<Integer> dfaFin = new ArrayList<Integer>();
        for (int i = 0; i < setState.size(); i++)
            if (isFinState(setState.get(i)))
                dfaFin.add(i);
        
        DFA dfa = new DFA(this.alpsz, dfaStates.size(), 0, dfaStates, dfaFin);
        //dfa.out();
        return dfa;
    }
    
    public boolean equivalents(NFA nfa2)
    {
        DFA dfa1 = this.toDFA().minimize(),
            dfa2 = nfa2.toDFA().minimize();
        return dfa1.equivalents(dfa2);
    }
    
}
