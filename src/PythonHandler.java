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

    public void parse_python_function(int i)
    {
        pymap.add(new TokenType("FUNCTION", "def"));
        i++;
        while(map.get(i).getToken() != "OPEN BRACS"){
            pymap.add(new TokenType(map.get(i).getToken(), map.get(i).getLiteral()));
            i++;
        }
    }

    void parse_python_if_expression(int i)
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
                    expr1 += " and ";
                }else{
                    expr1 += " | ";
                }
            }else if(expr.charAt(j) == '&')
            {
                if(expr.charAt(j+1) == '&')
                {
                    expr1 += " && ";
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
        pymap.add(new TokenType("COLON", ":"));
        i++;
        parse_python_function_body(i);

    }

    public void parse_python_function_body(int i)
    {
        TokenType tt = map.get(i);
        switch (tt.getToken())
        {
            case "IF":
                parse_python_if_expression(i);
                break;
            case "PRINT EXPR":
                pymap.add(new TokenType("PRINT EXPR", tt.getLiteral()));
                break;
            default:
                i++;
                break;
        }
    }

    public void parse_python_variables(int i)
    {

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
                    parse_python_function(i);
                    i++;
                    break;
                case "OPEN BRACS":
                    pymap.add(new TokenType("COLON", ":"));
                    i++;
                    parse_python_function_body(i);
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

}
