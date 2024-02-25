import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PythonHandler {

    private ArrayList<TokenType> map;
    private ArrayList<TokenType> pymap = new ArrayList<>();

    private Map<String, String> pykws = new HashMap<>();
    PythonHandler(ArrayList<TokenType> map)
    {
        pykws.put("||", "or");
        pykws.put("&&", "and");
        this.map = map;
    }

    public void show()
    {
        for(int i=0;i<map.size();i++)
        {
            System.out.println("Token: "+map.get(i).getToken()+" | Literal: "+map.get(i).getLiteral());
        }
    }

    public int parse_python_function(int i)
    {
        pymap.add(new TokenType("FUNCTION", "def"));
        i++;
        while(map.get(i).getToken() != "OPEN BRACS"){
            pymap.add(new TokenType(map.get(i).getToken(), map.get(i).getLiteral()));
            i++;
        }
        return i;
    }

    int parse_python_if_expression(int i)
    {
        // if ( Expr ): body else: body

        pymap.add(new TokenType("IF", "if"));
        i++;
        pymap.add(new TokenType("OPEN PAR", "("));
        i++;
        String expr = map.get(i).getLiteral();
        String expr1 = "";
        for(int j=0;j<expr.length();j++)
        {
            if(expr.charAt(j) == '|')
            {
                if(expr.charAt(j+1) == '|')
                {
                    expr1 += " or ";
                    j++;
                }else{
                    expr1 += " | ";
                }
            }else if(expr.charAt(j) == '&')
            {
                if(expr.charAt(j+1) == '&')
                {
                    expr1 += " and ";
                    j++;
                }else{
                    expr1 += " & ";
                }
            }else{
                expr1 += expr.charAt(j);
            }
        }
        pymap.add(new TokenType(map.get(i).getToken(), expr1));
        i++;
        pymap.add(new TokenType("CLOSE PAR", ")"));
        i++;
//        parse_python_function_body(i);
        return i;

    }

    int parse_python_else_expression(int i)
    {
        TokenType tt = map.get(i);
        pymap.add(new TokenType("COLON", ":"));
        i++;
        i = parse_python_function_body(i);
        return i;
    }
    public int parse_python_function_body(int i)
    {
        TokenType tt = map.get(i);
        switch (tt.getToken())
        {
            case "IF":
                i = parse_python_if_expression(i);
                break;
            case "PRINT EXPR":
                pymap.add(new TokenType("PRINT EXPR", tt.getLiteral()));
                i++;
                break;
            case "DATATYPE":
                i++;
                pymap.add(new TokenType("LITERAL", map.get(i).getLiteral()));
                i++;
                break;
            default:
                i++;
                break;
        }
        return i;
    }

    public void parse_python_variables(int i)
    {

    }

    int parse_while_body(int i)
    {
        String ee = "";
        while (map.get(i).getLiteral() != "}"){
            TokenType tt = map.get(i);
            if(tt.getToken() == "PRINT EXPR"){
                pymap.add(new TokenType(tt.getToken(), tt.getLiteral()));
            }else{
                String expr = tt.getLiteral();
                for(int j=0;j<expr.length();j++)
                {
                    if(expr.charAt(j) == ';') continue;
                    if(expr.charAt(j) == '+')
                    {
                        if(expr.charAt(j+1) == '+')
                        {
                            ee += " += 1";
                            j++;
                        }else{
                            ee += expr.charAt(j);
                        }
                    }
                    else if(expr.charAt(j) == '-')
                    {
                        if(expr.charAt(j+1) == '-')
                        {
                            ee += " -= 1";
                            j++;
                        }
                        else{
                            ee += expr.charAt(j);
                        }
                    }else{
                        ee += expr.charAt(j);
                    }
                }
            }
            i++;
        }
        pymap.add(new TokenType("EXPR", ee));
        return i;
    }

    int parse_python_while_expression(int i)
    {
        if(map.get(i).getLiteral() == "(")
        {
            pymap.add(new TokenType("OPEN PAR", "("));
            i++;
        }

        String expr = map.get(i).getLiteral();

        String e1 = "";
        for(int j=0;j<expr.length();j++)
        {
            if(expr.charAt(j) == '&')
            {
                if(expr.charAt(j+1) == '&')
                {
                    e1 += " and ";
                    j++;
                }else{
                    e1 += " & ";
                }
            }
            else if(expr.charAt(j) == '|'){
                if(expr.charAt(j+1) == '|')
                {
                    e1 += " or ";
                    j++;
                }else{
                    e1 += " | ";
                }
            }
            else{
                e1 += expr.charAt(j);
            }
        }

        pymap.add(new TokenType(map.get(i).getToken(), e1));
        i++;
        pymap.add(new TokenType("CLOSE PAR", ")"));
        i++;
        pymap.add(new TokenType("COLON", ":"));
        i++;

        i = parse_while_body(i);
        return i;
    }

    public void parse_to_python()
    {
        int i=0;
        while(i < this.map.size())
        {
            TokenType t = map.get(i);
            String token = t.getToken();

            switch (token)
            {
                case "FUNCTION":
                    i = parse_python_function(i);
//                    i++;
                    break;
                case "OPEN BRACS":
                    pymap.add(new TokenType("COLON", ":"));
                    i++;
                    i = parse_python_function_body(i);
                    break;
                case "ELSE":
                    pymap.add(new TokenType("ELSE", "else"));
                    i++;
                    break;
                case "WHILE":
                    pymap.add(new TokenType("WHILE", "while"));
                    i++;
                    i = parse_python_while_expression(i);
                    break;
                case "DATATYPE":
                    i++;
                    pymap.add(new TokenType("LITERAL", map.get(i).getLiteral()));
                    break;
                case "IF":
                    i = parse_python_if_expression(i);
                    break;
                default:
                    i++;
                    break;
            }
        }
    }

    public void show_pytokens()
    {
        for(int i=0;i<pymap.size();i++)
        {
            System.out.println("TOKEN: "+pymap.get(i).getToken()+" | Literal: "+pymap.get(i).getLiteral());
        }
    }

    public void write_code()
    {
        String output = "";
        int tabs = 0;

        for(int i=0;i<pymap.size();i++)
        {

            if(pymap.get(i).getToken() == "FUNCTION"){
                output += " ".repeat(tabs);
                tabs += 4;
                output += pymap.get(i).getLiteral()+" ";
            }
            else if(pymap.get(i).getToken() == "ELSE")
            {
                output += " ".repeat(tabs-4);
                output += pymap.get(i).getLiteral();
                tabs -= 4;
            }
            else if(pymap.get(i).getToken() == "COLON"){
                output += pymap.get(i).getLiteral() + "\n";
                tabs += 4;
            }
            else if(pymap.get(i).getToken() == "PRINT EXPR")
            {
                output += " ".repeat(tabs);
                output += "print(";
                output += pymap.get(i).getLiteral();
                output += ")" + "\n";
            }else if(pymap.get(i).getToken() == "IF")
            {
                output += " ".repeat(tabs);
                output += pymap.get(i).getLiteral();
            }
            else if(pymap.get(i).getToken() == "WHILE"){
                output += " ".repeat(tabs-4);
                output += "while";
            }
            else if(pymap.get(i).getToken() == "EXPR")
            {
//                output += " ".repeat(tabs);
                if(pymap.get(i-1).getLiteral() != "(")
                {
                    output += " ".repeat(tabs);
                }
                output += pymap.get(i).getLiteral();
            }
            else if(pymap.get(i).getToken() == "LITERAL")
            {
                output += " ".repeat(tabs);
                output += pymap.get(i).getLiteral()+"\n";
            }
            else{
//                output += " ".repeat(tabs);
                output += pymap.get(i).getLiteral() + " ";
            }

        }
        System.out.println(output);
    }

}
