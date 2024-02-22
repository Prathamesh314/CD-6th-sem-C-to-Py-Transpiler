public class TokenType {
    private String token;
    private String literal;

    TokenType(String token, String literal)
    {
        this.token = token;
        this.literal = literal;
    }

    public String getToken()
    {
        return this.token;
    }

    public String getLiteral()
    {
        return this.literal;
    }
}
