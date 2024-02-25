# C-Py-Transpilers
This is 6th sem Compiler Design project


# About

This ia a basic Transpiler written in JAVA which converts C language to Python language, semantically and syntactically.
It doesnot support lexical analysis for error checking, it just convert one language to other.

# My Approach

I have written grammar rule for 1) if-else 2) while 3) variable declarations 4) functions in C and collected Tokens like 1) Keywords 2) Identifiers 3) Operators etc. and saved all these tokens in a list

In later stage, I have passed this list to another class that converts this C tokens to Py tokens and also ensures semantic rules of python like python doesnot support (++) or (--) operators, it doesnot support (&&) or (||) so i have replaced them with correct syntax.

# Here are some screenshots 

1. # C Program
<img src="/images/c.png" alt="C program"/>

2. # Python Program

<img src="/images/py.png" alt="P Program" />


# How to run this program locally

1. Prerequisites
   > docker
   > java
   > node

2. Clone this repo on your local machine
3. Run this command :- `docker compose up -d` on your terminal
4. Goto http://localhost:8080 and create a new database [transpiler]
5. Backend is in JAVA so run it on your regular java ide
6. Frontend is in nextjs. To run frontend, Open new terminal [If backend and frontend both are running on same IDE] `cd frontend && npm run dev`
7. Goto localhost:3000 to view website
