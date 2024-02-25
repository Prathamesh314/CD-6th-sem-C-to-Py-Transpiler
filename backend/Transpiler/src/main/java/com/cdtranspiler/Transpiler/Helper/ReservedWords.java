package com.cdtranspiler.Transpiler.Helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReservedWords {
    ArrayList<String> words = new ArrayList<>();
    ReservedWords()
    {
        this.create();
    }



    void create()
    {
        words.add("int");
        words.add("long");
        words.add("double");
        words.add("char");
        words.add("for");
        words.add("while");
        words.add("do");
        words.add("if");
        words.add("else");
        words.add("break");
        words.add("continue");
        words.add("return");
        words.add("include");
        words.add("printf");
        words.add("scanf");
    }

    boolean is_keyword(String word)
    {
        int yes = 0;
        for(String s: words)
        {
            if(s == word)
            {
                yes = 1;
                break;
            }
        }

        return yes == 1;
    }
}