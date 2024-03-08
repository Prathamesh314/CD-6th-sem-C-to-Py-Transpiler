package com.cdtranspiler.Transpiler.Helper;

public class main {
    public static void main(String[] args) {
        String source = "#include<stdio.h>\n"+"int main()\n"+"{\n"+"int a;\n"+
                "for(int i=100;i>=10;i-=2)\n" + "{\n" + "\tprintf('Hello World');\n" + "}\n"
                + "\treturn 0;\n" + "}\n";
        Tokens t = new Tokens(source);
        t.parse_c(0);
        t.show_tokens();
//
        System.out.println("Source Program");
        t.show_source_code();

        System.out.println("Target Program [In Python]");
        t.parse_c_to_python();
        t.show_py_tokens();
    }
}
