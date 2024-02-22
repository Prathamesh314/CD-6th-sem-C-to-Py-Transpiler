import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tokens {
    private class TokenType{
        private String token;
        private String literal;

        TokenType(String token, String literal)
        {
            this.token = token;
            this.literal = literal;
        }

        private String getToken()
        {
            return this.token;
        }

        private String getLiteral()
        {
            return this.literal;
        }
    }
    String source;
    ArrayList<TokenType> map = new ArrayList<>();
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
                while (this.source.charAt(i) == ' ' || is_alpha_numeric(this.source.charAt(i)) || is_symbols(this.source.charAt(i))) {
                    expr.append(this.source.charAt(i));
                    i++;
                }
                map.add(new TokenType("EXPR", expr.toString().trim()));
                break;
        }
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
                    } else {
                        i++;
                    }
                    break;
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
        return map;
    }

    void show_tokens()
    {
        for(int i=0;i<this.map.size();i++)
        {
            System.out.println("Token: "+map.get(i).token+" | Literal: "+map.get(i).literal);
        }
    }


}
