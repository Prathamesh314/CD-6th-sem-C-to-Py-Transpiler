# C-Py-Transpilers

## Overview
This repository contains a project developed for the 6th semester Compiler Design course.

## About

C-Py-Transpilers is a basic transpiler written in Java designed to convert C language code into Python code both semantically and syntactically. Unlike a full compiler, it doesn't perform lexical analysis for error checking; its sole purpose is to convert code from one language to another.

## Approach

The approach involves defining grammar rules for common C language constructs such as `if-else`, `while`, variable declarations, and functions. Tokens such as keywords, identifiers, and operators are collected and organized into a list. Then, a conversion process takes place where these C tokens are transformed into Python tokens, ensuring adherence to Python's semantic rules. For instance, operators like `(++)`, `(--)`, `(&&)`, and `(||)` which are not supported in Python are appropriately replaced.

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
2. Open your terminal and run the command `docker compose up -d`.
3. Navigate to http://localhost:8080 and create a new database named `transpiler`.
4. The backend is written in Java, so run it on your preferred Java IDE.
5. The frontend is built with Next.js. To run it, open a new terminal window (if the backend and frontend are running on the same IDE) and navigate to the `frontend` directory. Then, run `npm run dev`.
6. Visit http://localhost:3000 to view the website.

With this setup, you can explore the functionality of the C-Py-Transpilers project locally.

Ensure the fonts are visually pleasing, headings are clear and concise, installation guidelines are easy to follow, and images are neatly arranged for better readability and understanding.
