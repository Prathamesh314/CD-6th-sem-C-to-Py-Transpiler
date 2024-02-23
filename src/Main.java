import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String source = "#include<stdio.h>\n"+
                "int main()\n"+
                "{\n"+
                "if (x > 0) {\n" +
                "   printf(\"Positive\");\n" +
                "} else {\n" +
                "   printf(\"Non-positive\");\n" +
                "}\n"+
                "return 0;\n"+
                "}";
        Tokens tokens = new Tokens(source);
        tokens.parse_c(0);
        tokens.show_tokens();
        System.out.println("Python starts here....");
        tokens.parse_c_to_python();
    }
}