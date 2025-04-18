# Use Cases for Finance Tracker Application

## 1. User Registration
**Description:** Allow users to create an account by providing a username.  
**Actors:** User  
**Preconditions:** None  
**Postconditions:** User account is created and stored in the database.
**Fullfilled:** no  

### 1.1 User Registration (Optional for expansion)
**Description:** Allow users to create an account by providing a username, an email and a password.  
**Actors:** User  
**Preconditions:** None  
**Postconditions:** User account is created and stored in the database.  
**Fullfilled:** no  

## 2. User Login
**Description:** Enable users to log in their account.  
**Actors:** User  
**Preconditions:** User must have a registered account.  
**Postconditions:** User is granted access to their dashboard.  
**Fullfilled:** no  

### 2.1 User Login (Optional for expansion)
**Description:** Enable users to log in their account with their credentials.  
**Actors:** User  
**Preconditions:** User must have a registered account.  
**Postconditions:** User is granted access to their dashboard if the credentials match.  
**Fullfilled:** no 

## 3. Manage Bank Accounts
**Description:** Allow users to add, edit, and delete their bank account details.  
**Actors:** User  
**Preconditions:** User must be logged in.  
**Postconditions:** Bank account details are updated in the system.  
**Fullfilled:** no  

### 3.1 Manage Depots (Optional for expansion)
**Description:** Allow users to add, edit, and delete depot accounts for managing investments.  
**Actors:** User  
**Preconditions:** User must be logged in.  
**Postconditions:** Depot account details are updated in the system.  
**Fullfilled:** no  

### 3.2 Manage Different Account Types (Optional for expansion)
**Description:** Allow users to manage various account types such as savings, checking, and credit accounts.  
**Actors:** User  
**Preconditions:** User must be logged in.  
**Postconditions:** Details for different account types are updated in the system.  
**Fullfilled:** no  

### 3.3 Organize Bank Accounts into Groups (Optional for expansion)
**Description:** Allow users to organize their bank accounts into groups based on account type and the bank they belong to.  
**Actors:** User  
**Preconditions:** User must be logged in.  
**Postconditions:** Bank accounts are grouped and stored in the system.  
**Fullfilled:** no    

## 4. Add Income
**Description:** Allow users to add income details, including source, amount, sender, and date.  
**Actors:** User  
**Preconditions:** User must be logged in.  
**Postconditions:** Income entry is saved in the system.  
**Fullfilled:** no  

### 4.1 Add Income that happen in intervalls
**Description:** Allow users to add income details, including source, amount, sender, date and intervall.  
**Actors:** User  
**Preconditions:** User must be logged in.  
**Postconditions:** Income entry and intervall is saved in the system.  
**Fullfilled:** no  

## 5. Add Expense
**Description:** Allow users to record expenses with details such as category (optional), recipient, amount, and date.  
**Actors:** User  
**Preconditions:** User must be logged in.  
**Postconditions:** Expense entry is saved in the system.  
**Fullfilled:** no  

### 5.1 Add Expense that happen in intervalls
**Description:** Allow users to record expenses with details such as category (optional), recipient, amount, date and intervall.  
**Actors:** User  
**Preconditions:** User must be logged in.  
**Postconditions:** Expense entry and intervall is saved in the system.  
**Fullfilled:** no  

## 6. View Transaction History
**Description:** Provide users with a list of all their income and expense transactions arranged in the order of execution.
**Actors:** User  
**Preconditions:** User must be logged in.  
**Postconditions:** Transaction history is displayed.  
**Fullfilled:** no  

### 6.1 Order and Filter Transaction History (Optional for expansion)
**Description:** Allow users to categorize (order and filter) their transactions (e.g., groceries, rent, entertainment).  
**Actors:** User  
**Preconditions:** User must be logged in.  
**Postconditions:** Transactions are categorized and stored.  
**Fullfilled:** no 

## 7. Set Budget Goals (Optional for expansion)
**Description:** Allow users to set monthly or yearly budget goals.  
**Actors:** User  
**Preconditions:** User must be logged in.  
**Postconditions:** Budget goals are saved in the system.  
**Fullfilled:** no  

## 8. Generate Financial Reports
**Description:** Enable users to generate reports summarizing their income, expenses, and savings.  
**Actors:** User  
**Preconditions:** User must be logged in.  
**Postconditions:** Financial report is generated and displayed.  
**Fullfilled:** no   

## 9. Export Data (Optional for expansion)
**Description:** Enable users to export their financial data in formats like CSV or PDF.  
**Actors:** User  
**Preconditions:** User must be logged in.  
**Postconditions:** Data is exported and downloaded.  
**Fullfilled:** no  