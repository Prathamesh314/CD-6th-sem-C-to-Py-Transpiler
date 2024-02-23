import java.util.ArrayList;

public class PythonHandler {

    private ArrayList<TokenType> map;
    private ArrayList<TokenType> pymap = new ArrayList<>();
    PythonHandler(ArrayList<TokenType> map)
    {
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
                    parse_python_function(i);
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
