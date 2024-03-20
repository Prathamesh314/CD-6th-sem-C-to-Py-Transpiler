# C-Py-Transpilers

## Overview
This repository contains a project developed for a Compiler Design course during the 6th semester.

## About

C-Py-Transpilers is a lightweight transpiler written in Java aimed at facilitating the conversion of C language code into Python, maintaining both semantic and syntactic integrity. Unlike comprehensive compilers, it focuses solely on the transformation aspect, omitting lexical analysis for error checking.

## Approach

The approach involves meticulously crafting grammar rules for prevalent C language constructs such as `if-else`, `while` loops, variable declarations, and functions. Tokens such as keywords, identifiers, and operators are meticulously parsed and organized into a cohesive list. Subsequently, a conversion algorithm is employed to translate these C tokens into their Python counterparts, ensuring strict adherence to Python's semantic guidelines. For instance, operators like `(++)`, `(--)`, `(&&)`, and `(||)`, incompatible with Python, are systematically substituted with valid Python syntax.

## Screenshots

### C Program
![C program](/images/c.png)

### Python Program
![Python program](/images/py.png)

## How to Run Locally

### Prerequisites
- Docker
- Java
- Node.js

1. Clone this repository to your local machine.
2. Execute `docker compose up -d` in your terminal.
3. Navigate to http://localhost:8080 and establish a new database named `transpiler`.
4. Run the backend on your preferred Java IDE.
5. If you don't have an IDE or using terminal,  follow these steps :-
     1. run `mvn clean package`
     2. **target** folder will be generated
     3. run `java -jar target/Transpiler-0.0.1-SNAPSHOT.jar`
7. For the frontend, built with Next.js, navigate to the `frontend` directory in a new terminal window. Then, execute `npm run dev`.
8. Access http://localhost:3000 to view the website.

By following these steps, you can explore the functionality of the C-Py-Transpilers project locally, ensuring a seamless experience.

Ensure the fonts are visually pleasing, headings are clear and concise, installation guidelines are easy to follow, and images are neatly arranged for better readability and understanding.
