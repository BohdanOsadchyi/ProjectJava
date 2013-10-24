package lab2;

import static java.lang.Math.max;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

public class DFA {
    
    private final int alpsz, size, q0;
    private ArrayList<Integer> fin;
    private boolean[] isFin;
    private ArrayList<State> q;
    
    public DFA(int _alpsz, int _size, int _q0)
    {
        this.alpsz  = _alpsz;
        this.size   = _size ;
        this.q0     = _q0   ;
        this.fin    = new ArrayList<Integer>();
        this.isFin  = new boolean[size];
        this.q = new ArrayList<State>();
        for (int i = 0; i < _size; i++)
            q.add(new State());
    }
    
    public DFA(int _alpsz, int _size, int _q0, ArrayList<State> _q, ArrayList<Integer> _fin)
    {
        this.alpsz  = _alpsz;
        this.size   = _size ;
        this.q0     = _q0   ;
        this.q      = _q;
        this.fin    = _fin;
        this.isFin  = new boolean[size];
        Iterator<Integer> it = fin.iterator();
        while(it.hasNext())
            isFin[it.next()] = true;
    }
    
    public void addFinState(int st)
    {
        fin.add(st);
        isFin[st] = true;
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
        System.out.println("DFA OUT");
        System.out.println("(" + alpsz + ", " + size + ", " + q0 + ")");
        System.out.println("Start state : " + q0);
        outFinStates();
        outAllStates();
    }
    
    
    private void outGroup(Integer ch, ArrayList<TreeSet<Integer>> group, ArrayList<Integer> groupType)
    {
        System.out.println("Char = " + ch);
        for (int i = 0; i < group.size(); i++)
        {
            System.out.print("GroupType " + groupType.get(i) + " {");
            Iterator it = group.get(i).iterator();
            while(it.hasNext())
            {
                System.out.print(" " + it.next());
            }
            System.out.println(" }");
        }
    }
    
    private void outAllType(ArrayList<TreeSet<Integer>> t)
    {
        for (int i = 0; i < t.size(); i++)
        {
            System.out.print(i + "[ ");
            Iterator itt = t.get(i).iterator();
            while(itt.hasNext())
            {
                System.out.print(itt.next() + " ");
            }
            System.out.println("]");
        }
    }
    
    private ArrayList<TreeSet<Integer>> groupTypeChar(TreeSet<Integer> s, int ch, Integer[] type)
    {
        ArrayList<TreeSet<Integer>> group = new ArrayList<TreeSet<Integer>>();
        ArrayList<Integer>      groupType = new ArrayList<Integer>();

        Iterator it = s.iterator();
        State st;
        while(it.hasNext())
        {
            Integer v = (Integer) it.next(), typeTo = -1; //type_to = -1 not edge
            st = this.q.get(v);
            if (!st.next[ch].isEmpty())
                typeTo = type[st.next[ch].first()];
            int index = groupType.indexOf(typeTo);
            if (index == -1)
            {
                TreeSet<Integer> newGroup = new TreeSet<Integer>();
                newGroup.add(v);
                group.add(newGroup);
                groupType.add(typeTo);
            }
            else
            {
                group.get(index).add(v);
            }
        }
        return group;
    }
    
    private int getStartStateMinDFA(ArrayList<TreeSet<Integer>> typeSet)
    {
        for (int i = 0; i < typeSet.size(); i++)
            if(typeSet.get(i).contains(this.q0))
                return i;
        return -1;
    }
    
    private ArrayList<Integer> getFinStatesMinDFA(ArrayList<TreeSet<Integer>> typeSet)
    {
        ArrayList<Integer> finMinDFA = new ArrayList<Integer>();
        for (int i = 0; i < typeSet.size(); i++)
        {
            Iterator it = typeSet.get(i).iterator();
            while(it.hasNext())
            {
                if (this.fin.contains( it.next() ))
                {
                    finMinDFA.add(i);
                    break;
                }
            }
        }
        return finMinDFA;
    }
    
    private State buildStateForType(TreeSet<Integer> s, Integer[] type)
    {
        State st = new State(), tmp = new State();
        Iterator it = s.iterator();
        while(it.hasNext())
        {
            Integer v = (Integer) it.next();
            for (int ch = 0; ch < this.alpsz; ch++)
            {
                tmp = q.get(v);
                if(!tmp.next[ch].isEmpty())
                {
                    Integer to = tmp.next[ch].first();
                    st.add(ch, type[to]);
                    //minDFA.addEdge(i, ch, type[to]);
                }
            }
        }
        return st;
    }
    
    
    public DFA minimize()
    {
        ArrayList<TreeSet<Integer>> typeSet = new ArrayList<TreeSet<Integer>>();
        ArrayList<Boolean> isProcess = new ArrayList<Boolean>();
        Integer[] type = new Integer[size];
        
        for (int i = 0; i < 2; i++)
        {
            typeSet.add(new TreeSet<Integer>());
            isProcess.add(false);
        }
        int t;
        for (int i = 0; i < size; i++)
        {
            t = (isFin[i] ? 0 : 1);
            type[i] = t;
            typeSet.get(t).add(i);
        }
        for (int i = 0; i < 2; i++)
            if (typeSet.get(i).size() < 2)
                isProcess.set(i, true);
        
        while(true)
        {
            boolean any = false;
            int sz = typeSet.size();
            for (int i = 0; i < sz && !any; i++)
            {
                if(isProcess.get(i) == true)
                    continue;
                boolean anychar = false;
                for (int ch = 0; ch < alpsz && !anychar; ch++)
                {
                    ArrayList<TreeSet<Integer>> group = new ArrayList<TreeSet<Integer>>();
                    group = groupTypeChar(typeSet.get(i), ch, type);
                  ////outGroup(ch, group, groupType);
                    if (group.size() == 1)
                        continue;
///                    System.out.println("Change in group " + i + " character " + ch);
                    anychar = true;
                    any = true;
                   
                    typeSet.set(i, group.get(0));
                    if(typeSet.get(i).size() < 2)
                        isProcess.set(i, true);
                    
                    Integer newType = null;
                    TreeSet<Integer> newTypeSet;
                    for (int j = 1; j < group.size(); j++)
                    {
                        newType    = typeSet.size();
                        newTypeSet = group.get(j); 
                        typeSet.add(newTypeSet);
                        isProcess.add( (newTypeSet.size() < 2) ? true : false );
                        Iterator itGroup = newTypeSet.iterator();
                        while(itGroup.hasNext())
                        {
                            type[(Integer)itGroup.next()] = newType;
                        }
                    }
                }
                if(!anychar)
                    isProcess.set(i, true);
            }
            if(!any)
                break;
        }

        int minDFA_alpsz = alpsz,
            minDFA_size  = typeSet.size(),
            minDFA_q0    = getStartStateMinDFA(typeSet);
        ArrayList<Integer> minDFA_fin = getFinStatesMinDFA(typeSet);
        ArrayList<State>   minDFA_q   = new ArrayList<State>();
        for (int i = 0; i < minDFA_size; i++)
        {
            State st = buildStateForType(typeSet.get(i), type);
            minDFA_q.add(st);
       }
        DFA minDFA = new DFA(minDFA_alpsz, minDFA_size, minDFA_q0, minDFA_q, minDFA_fin);
        //minDFA.out();
        return minDFA;
    }

    public boolean equivalents(DFA dfa)
    {
        if (this.size != dfa.size)
            return false;
        if (this.fin.size() != dfa.fin.size())
            return false;
        
        boolean[] used1 = new boolean[this.size],
                  used2 = new boolean[this.size];
        
        int[] eqState = new int[this.size];
        int[] deque   = new int[this.size];
        int head = 0, tail = 0, v = -1, eqv = -1, to = -1, eqto = -1, sz = -1, maxalpsz = max(this.alpsz, dfa.alpsz);
        State st, eqst;
        eqState[this.q0] = dfa.q0;
        used1[this.q0] = used2[dfa.q0] = true;
        deque[tail++] = this.q0;
        while(head < tail)
        {
            v = deque[head++];
            eqv = eqState[v];
            for (int ch = 0; ch < maxalpsz; ch++)
            {
                st   = this.q.get(v);
                eqst = dfa.q.get(eqv);
                sz = st.next[ch].size();
                if (sz != eqst.next[ch].size())
                    return false;
                if (sz == 0)
                    continue;
                to   = st.next[ch].first();
                eqto = eqst.next[ch].first();
                if (used1[to] != used2[eqto])
                    return false;
               if (!used1[to])
                {
                    deque[tail++] = to;
                    used1[to] = used2[eqto] = true;
                    eqState[to] = eqto;
                }
            }
        }
        return true;
    }
}
