import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String source = "#include<stdio.h>\n"+
                "int main()\n"+
                "{\n"+
                "int a = 2;\n"+
                "if (x > 0 && y < 5 || z <= 6) {\n" +
                "   printf(\"Hii\");\n" +
                "} else {\n" +
                "   printf(\"Hello\");\n" +
                "}\n"+
                "while(t < 5) {\n"+
                "printf(1);\n"+
                "t--;\n"+
                "}\n"+
                "return 0;\n"+
                "}";
        Tokens tokens = new Tokens(source);
        tokens.parse_c(0);
        tokens.show_tokens();
        System.out.println("C code... [ Source Code ]");
        tokens.show_source_code();

        System.out.println("\nPython starts here... [Target Code]");
        tokens.parse_c_to_python();
//        tokens.write_python_code();
    }
}