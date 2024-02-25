# C-Py-Transpilers
This is 6th sem Compiler Design project


# About

This ia a basic Transpiler written in JAVA which converts C language to Python language, semantically and syntactically.
It doesnot support lexical analysis for error checking, it just convert one language to other.

# My Approach

I have written grammar rule for 1) if-else 2) while 3) variable declarations 4) functions in C and collected Tokens like 1) Keywords 2) Identifiers 3) Operators etc. and saved all these tokens in a list

In later stage, I have passed this list to another class that converts this C tokens to Py tokens and also ensures semantic rules of python like python doesnot support (++) or (--) operators, it doesnot support (&&) or (||) so i have replaced them with correct syntax.

# Here are some screenshots 

1. ### C Program
<img src="/images/c.png" alt="C program"/>

2. ### Python Program

<img src="/images/py.png" alt="P Program" />
