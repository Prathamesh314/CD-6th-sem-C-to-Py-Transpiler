import java.util.ArrayList;

public class PythonHandler {

    private ArrayList<TokenType> map;
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



}
