package lab2;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Lab2 {

    private static NFA buildNFA(Scanner in)
    {
        int alpsz, size, q0, finsz, edgesz;
        alpsz = in.nextInt();
        size  = in.nextInt();
        q0    = in.nextInt();
        NFA nfa = new NFA(alpsz, size, q0);
        finsz = in.nextInt();
        for (int i = 0; i < finsz; i++)
            nfa.addFinState( in.nextInt() );
        edgesz = in.nextInt();
        int v, ch, to; 
        char c;
        for (int i = 0; i < edgesz; i++)
        {
            v = in.nextInt();
            ch = (int)(in.next().charAt(0) - 'a');
            to = in.nextInt();
            nfa.addEdge(v, ch, to);
        }
        return nfa;
    }
    
    private static DFA buildDFA(Scanner in)
    {
        int alpsz, size, q0, finsz, edgesz;
        alpsz = in.nextInt();
        size  = in.nextInt();
        q0    = in.nextInt();
        DFA dfa = new DFA(alpsz, size, q0);
        finsz = in.nextInt();
        for (int i = 0; i < finsz; i++)
            dfa.addFinState( in.nextInt() );
        edgesz = in.nextInt();
        int v, ch, to; 
        char c;
        for (int i = 0; i < edgesz; i++)
        {
             v = in.nextInt();
             ch = (int)(in.next().charAt(0) - 'a');
             to = in.nextInt();
             dfa.addEdge(v, ch, to);
        }
        return dfa;
    }
    
    public static void main(String[] args) {
        
       try {
            Scanner in1 = new Scanner(new FileInputStream("input.txt")); 
            Scanner in2 = new Scanner(new FileInputStream("input2.txt"));
            
            NFA nfa1 = buildNFA(in1),
                nfa2 = buildNFA(in2);
            
            DFA dfa1 = nfa1.toDFA();
                dfa2 = nfa2.toDFA();
            
            dfa1.out();
            dfa2.out();
            
            dfa1.minimize().out();
            dfa2.minimize().out();
            
            System.out.println("Result : " + nfa1.equivalents(nfa2));
            
       } catch (FileNotFoundException ex) {
            System.out.println("Lab2 main " + ex);
        }
          
       
    }
}
