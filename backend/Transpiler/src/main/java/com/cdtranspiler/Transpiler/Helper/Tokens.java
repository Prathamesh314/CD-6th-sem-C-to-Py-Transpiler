package com.cdtranspiler.Transpiler.Helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tokens {

    String source;
    ArrayList<TokenType> map = new ArrayList<>();
    PythonHandler pythonHandler;

    ReservedWords rwords = new ReservedWords();

    int size;

    public Tokens(String sourcecode) {
        this.source = sourcecode;
        this.size = sourcecode.length();
    }

    private int parse_printf_statement(int i) {
        StringBuilder ins = new StringBuilder();
        i++;
        while (this.source.charAt(i) != ')') {
            ins.append(this.source.charAt(i));
            i++;
        }

        String formattedIns = ins.toString().trim();
        map.add(new TokenType("PRINT EXPR", formattedIns));
        return i;
    }

    private boolean is_alpha_numeric(char ch) {
        return Character.isLetterOrDigit(ch);
    }

    private boolean is_symbols(char ch) {
        List<String> l = new ArrayList<>();
        l.add("+");
        l.add("-");
        l.add("*");
        l.add("/");
        l.add("==");
        l.add("!=");
        l.add(">");
        l.add("<");
        l.add("=");
        l.add("&");
        l.add("&&");
        l.add("|");
        l.add("||");
        l.add(" ");
        l.add("0");
        l.add("1");
        l.add("2");
        l.add("3");
        l.add("4");
        l.add("5");
        l.add("6");
        l.add("7");
        l.add("8");
        l.add("9");

        String symbol = Character.toString(ch);
        return l.contains(symbol);
    }

    private int parse_if_statement(int i) {
        char ch = this.source.charAt(i);
        switch (ch) {
            case ' ':
            case '\n':
                i++;
                parse_if_statement(i);
                break;
            case 'p':
                if (this.source.startsWith("printf", i)) {
                    map.add(new TokenType("PRINT", "printf"));
                    i += 6;
                    parse_printf_statement(i);
                }
                break;
            default:
                StringBuilder k = new StringBuilder();
                while (this.source.charAt(i) == ' ' || is_alpha_numeric(this.source.charAt(i))) {
                    k.append(this.source.charAt(i));
                    i++;
                }
                map.add(new TokenType("EXPR", k.toString().trim()));
                break;
        }
        return i;
    }

    private int parse_if_expression(int i) {
        char ch = this.source.charAt(i);
        switch (ch) {
            case ' ':
            case '\n':
                i++;
                i = parse_if_expression(i);
                break;
            case '{':
                map.add(new TokenType("OPEN BRACS", "{"));
                i++;
                i = parse_if_statement(i);
                break;
            case '}':
                map.add(new TokenType("CLOSE BRACS", "}"));
                i++;
            case '(':
                map.add(new TokenType("OPEN PAR", "("));
                i++;
                i = parse_if_expression(i);
                break;
            case ')':
                map.add(new TokenType("CLOSE PAR", ")"));
                i++;
                parse_c(i);
                break;
            default:
                StringBuilder expr = new StringBuilder();
                while (this.source.charAt(i) != ')' && this.source.charAt(i) == ' ' || is_alpha_numeric(this.source.charAt(i)) || is_symbols(this.source.charAt(i))) {
                    expr.append(this.source.charAt(i));
                    i++;
                }
                map.add(new TokenType("EXPR", expr.toString().trim()));
                map.add(new TokenType("CLOSE PAR", ")"));
                break;
        }
        return i;
    }

    int parse_function_expression(int i)
    {
        // grammar for function
        // return type name () { body }
        String k = "";
        while (this.source.charAt(i) == ' '){
            i++;
        }

        if(this.source.charAt(i) == '(')
        {
            map.add(new TokenType("OPEN PAR", "("));
            i++;
        }
        String body = "";
        while(this.source.charAt(i) == 32 || is_alpha_numeric(this.source.charAt(i)))
        {
            body += this.source.charAt(i);
            i++;
        }

        map.add(new TokenType("EXPR", body));
        i++;
        map.add(new TokenType("CLOSE PAR", ")"));
        i++;
        return i;
    }

    void parse_identifier(int i)
    {

    }

    int parse_while_body(int i)
    {
        if(i >= this.source.length()) return 0;
        char ch = this.source.charAt(i);
        switch (ch)
        {
            case ';':
            case '\n':
                i++;
                i = parse_while_body(i);
                break;
            case '{':
                map.add(new TokenType("OPEN BRACS", "{"));
                i++;
                i = parse_while_body(i);
                break;
            case '}':
                map.add(new TokenType("CLOSE BRACS", "}"));
                break;
            default:
                String pp = "";
                while(is_alpha_numeric(this.source.charAt(i)) || is_symbols(this.source.charAt(i)))
                {
                    pp += this.source.charAt(i);
                    i++;
                }

                if(rwords.is_keyword(pp))
                {
                    map.add(new TokenType("KEYWORDS", pp));
                }else{
                    map.add(new TokenType("IDENT", pp));
                }
                i++;
                parse_while_expression(i);
                break;
        }
        return i;
    }

    int parse_while_expression(int i)
    {
        // while ( expr ) { body }
        if(i > map.size()) return 0;

        while(this.source.charAt(i) == ' '){
            i++;
        }

        char ch = this.source.charAt(i);
        switch(ch)
        {
            case ';':
            case '\n':
                i++;
                i = parse_while_expression(i);
                break;
            case '(':
                map.add(new TokenType("OPEN PAR", "("));
                i++;
                i = parse_while_expression(i);
                break;
            case ')':
                map.add(new TokenType("CLOSE PAR", ")"));
                i++;
                break;
            case '{':
                map.add(new TokenType("OPEN BRACS", "{"));
                i++;
                i = parse_while_body(i);
                break;
            case '}':
                map.add(new TokenType("CLOSE BRACS", "}"));
                i++;
                break;
            default:
                String kk = "";
                while(is_alpha_numeric(this.source.charAt(i)) || is_symbols(this.source.charAt(i)))
                {
                    kk += this.source.charAt(i);
                    i++;
                }
                map.add(new TokenType("EXPR", kk));
                i = parse_while_expression(i);
                break;
        }
        return i;
    }


    int parse_for_expression(int i)
    {
        // for(int i=0;i<n;i++){ body }

        while(this.source.charAt(i) == ' '){
            i++;
        }
        if(this.source.charAt(i) == '(')
        {
            map.add(new TokenType("OPEN PAR", "("));
            i++;
        }
        String expr = "";
        while(this.source.charAt(i) != ')')
        {
            expr += this.source.charAt(i);
            i++;
        }
        map.add(new TokenType("EXPR", expr));

        if(this.source.charAt(i) == ')')
        {
            map.add(new TokenType("CLOSE PAR", ")"));
            i++;
        }


        return i;
    }

    public void parse_c(int i) {
        while (i < size) {
            char ch = this.source.charAt(i);

            switch (ch) {
                case ';':
                case ' ':
                case '\t':
                case '\n':
                    i++;
                    break;
                case '#':
                    while (i<this.size && this.source.charAt(i) != '\n') {
                        i++;
                    }
                    break;
                case 'i':
                    if (this.source.startsWith("if", i)) {
                        i += 2;
                        map.add(new TokenType("IF", "if"));
                        i = parse_if_expression(i);
                    } else if(this.source.startsWith("int", i)) {
                        i+=3;
//                        map.add(new TokenType("INT", "int"));
                        while(this.source.charAt(i) == ' '){
                            i++;
                        }
                        String kk = "";
                        while(is_alpha_numeric(this.source.charAt(i))){
                            kk += this.source.charAt(i);
                            i++;
                        }

                        while (this.source.charAt(i) == ' ')
                        {
                            i++;
                        }
                        int is_fun = 0;
                        if(this.source.charAt(i) == '(')
                        {
                            is_fun = 1;

                        }
                        if(is_fun == 1){
                            map.add(new TokenType("FUNCTION", "int"));
                            map.add(new TokenType("IDENT", kk));
                            parse_function_expression(i);
                        }else{
                            int is_equal_present = 0;

                            while(this.source.charAt(i) != ';'){
                                if(this.source.charAt(i) == '='){
                                    is_equal_present = 1;
                                }
                                kk += this.source.charAt(i);
                                i++;
                            }
                            if(is_equal_present == 1){
                                map.add(new TokenType("DATATYPE", "int"));
                                map.add(new TokenType("IDENT", kk));
                            }

                            i++;
                        }

                    }else{
                        i++;
                    }
                    break;
                case 'e':
                    if(this.source.startsWith("else", i))
                    {
                        map.add(new TokenType("ELSE", "else"));
                        i+=4;
                        while(this.source.charAt(i) == ' ')
                        {
                            i++;
                        }

                    }else{
                        i++;
                    }
                    break;

                case 'p':
                    if (this.source.startsWith("printf", i)) {
                        i += 6;
                        i = parse_printf_statement(i);
                    } else {
                        i++;
                    }
                    break;
                case '{':
                    map.add(new TokenType("OPEN BRACS", "{"));
                    i++;
                    break;
                case '}':
                    map.add(new TokenType("CLOSE BRACS", "}"));
                    i++;
                    break;
                case 'w':
                    if(this.source.startsWith("while", i)){
                        map.add(new TokenType("WHILE", "while"));
                        i+=5;
                        i = parse_while_expression(i);
                        break;
                    }
                    break;
                case 'f':
                    if(this.source.startsWith("for", i)){
                        map.add(new TokenType("FOR", "for"));
                        i += 3;
                        i = parse_for_expression(i);
                        break;
                    }
                    break;
                default:
                    String pp = "";
                    if(!is_alpha_numeric(this.source.charAt(i)) && !is_symbols(this.source.charAt(i))){
                        i++;
                    }else{
                        while (is_alpha_numeric(this.source.charAt(i)) || is_symbols(this.source.charAt(i))){
                            pp += this.source.charAt(i);
                            i++;
                        }
                        map.add(new TokenType("EXPR", pp));
                        i++;
                    }
                    break;
            }
        }
    }

    ArrayList<TokenType> getTokens() {
        pythonHandler = new PythonHandler(map);
        return map;
    }

    void show_tokens()
    {
        ArrayList<TokenType> map = getTokens();
//        this.pythonHandler.show();
        for(int i=0;i<map.size();i++)
        {
            System.out.println("Token: " + map.get(i).getToken() + " | " + "Literal: "+ map.get(i).getLiteral());
        }

    }

    public String parse_c_to_python()
    {
        this.getTokens();
        this.pythonHandler.parse_to_python();
//        this.pythonHandler.show_pytokens();
        this.pythonHandler.write_code();
        return this.pythonHandler.getCode();
    }

    void show_py_tokens()
    {
        this.pythonHandler.show_pytokens();
    }

    void write_python_code()
    {
        this.pythonHandler.write_code();
    }

    void show_source_code()
    {
        System.out.println(this.source);
    }

}
