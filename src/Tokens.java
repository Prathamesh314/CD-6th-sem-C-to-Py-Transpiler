import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tokens {

    String source;
    ArrayList<TokenType> map = new ArrayList<>();
    PythonHandler pythonHandler;

    int size;

    Tokens(String sourcecode) {
        this.source = sourcecode;
        this.size = sourcecode.length();
    }

    private void parse_printf_statement(int i) {
        StringBuilder ins = new StringBuilder();
        i++;
        while (this.source.charAt(i) != ')') {
            ins.append(this.source.charAt(i));
            i++;
        }

        String formattedIns = ins.toString().trim();
        map.add(new TokenType("PRINT EXPR", formattedIns));
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
        l.add(">=");
        l.add("<=");

        String symbol = Character.toString(ch);
        return l.contains(symbol);
    }

    private void parse_if_statement(int i) {
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
    }

    private void parse_if_expression(int i) {
        char ch = this.source.charAt(i);
        switch (ch) {
            case ' ':
            case '\n':
                i++;
                parse_if_expression(i);
                break;
            case '{':
                map.add(new TokenType("OPEN BRACS", "{"));
                i++;
                parse_if_statement(i);
                break;
            case '}':
                map.add(new TokenType("CLOSE BRACS", "}"));
                i++;
            case '(':
                map.add(new TokenType("OPEN PAR", "("));
                i++;
                parse_if_expression(i);
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
    }

    void parse_function_expression(int i)
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
    }

    void parse_identifier(int i)
    {

    }

    void parse_c(int i) {
        while (i < size) {
            char ch = this.source.charAt(i);
            switch (ch) {
                case ' ':
                case '\t':
                case '\n':
                    i++;
                    break;
                case '#':
                    while (this.source.charAt(i) != '\n') {
                        i++;
                    }
                    break;
                case 'i':
                    if (this.source.startsWith("if", i)) {
                        i += 2;
                        map.add(new TokenType("IF", "if"));
                        parse_if_expression(i);
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
                            map.add(new TokenType("DATATYPE", "int"));
                            map.add(new TokenType("IDENT", kk));
                            parse_identifier(i);
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
                        map.add(new TokenType("OPEN BRACS", "{"));
                        i++;
                        parse_if_expression(i);
                    }else{
                        i++;
                    }

                case 'p':
                    if (this.source.startsWith("printf", i)) {
                        i += 6;
                        parse_printf_statement(i);
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
                default:
                    i++;
            }
        }
    }

    ArrayList<TokenType> getTokens() {
        pythonHandler = new PythonHandler(map);
        return map;
    }

    void show_tokens()
    {
        getTokens();
        this.pythonHandler.show();
    }

    void parse_c_to_python()
    {
        this.pythonHandler.parse_to_python();
        this.pythonHandler.show_pytokens();
    }

}
