# Assignment Week

Our customer **“International Stock Analysis”** (ISA) wants to create an application which
helps to store prices for stocks at a given time and compares it to previous prices.

### General Requirements
1. Create a new database which stores stocks and prices for a given moment in time.
2. The application should work as a “REPL” application, which means, it should only stop
when the user types “exit”.

### Application Features
1. The “import” command should allow importing the file “STOCK_DATA.csv” to your
   application
2. The “delete” command should allow you to delete all data from your database
3. The “search” command should help you to find the id for a company. It should support
   placeholders, so you need to type the first characters of a company to find it
4. The “show <id>” command should show you the last ten prices for a stock with a
   specific id.
5. The “add <id> <date> <price>” command should allow you add a new price for a
   specific time
6. The “max <id>” command show the highest price for a stock ever had
7. The “low <id>” command show the lowest price for a stock ever had
8. The “gap <id>” command show the difference between the highest and the lowest price
   ever recorded
9. The “update-industry <id> <industry>” command should update a stocks industry
   Assignment Week 1
10. The “industries” command lists all industries in the database with its ID and the number
    of stocks assigned to this industry
11. BONUS: The “export” command exports all data to a CSV file which could be imported
    again