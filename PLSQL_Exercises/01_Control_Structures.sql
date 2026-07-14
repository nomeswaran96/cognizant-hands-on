SET SERVEROUTPUT ON;

-- ==============================================================================
-- Scenario 1: Apply 1% interest discount to loans of customers over 60 years old.
-- ==============================================================================
DECLARE
    CURSOR c_customer_loans IS
        SELECT l.LoanID, c.Name, c.DOB, l.InterestRate
        FROM Loans l
        JOIN Customers c ON l.CustomerID = c.CustomerID;
    v_age NUMBER;
BEGIN
    FOR r IN c_customer_loans LOOP
        v_age := MONTHS_BETWEEN(SYSDATE, r.DOB) / 12;
        IF v_age > 60 THEN
            -- Apply a 1% discount to current interest rate
            UPDATE Loans
            SET InterestRate = InterestRate - 1
            WHERE LoanID = r.LoanID;
            
            DBMS_OUTPUT.PUT_LINE('Applied 1% discount to Loan ID: ' || r.LoanID || ' for Customer: ' || r.Name || ' (Age: ' || ROUND(v_age) || ')');
        END IF;
    END LOOP;
    COMMIT;
END;
/

-- ==============================================================================
-- Scenario 2: Promote customers to VIP status (set flag IsVIP to TRUE) if balance is over $10,000.
-- ==============================================================================
DECLARE
    CURSOR c_customers IS
        SELECT CustomerID, Name, Balance 
        FROM Customers;
BEGIN
    FOR r IN c_customers LOOP
        IF r.Balance > 10000 THEN
            UPDATE Customers
            SET IsVIP = 'TRUE'
            WHERE CustomerID = r.CustomerID;
            
            DBMS_OUTPUT.PUT_LINE('Promoted Customer: ' || r.Name || ' (ID: ' || r.CustomerID || ') to VIP status.');
        END IF;
    END LOOP;
    COMMIT;
END;
/

-- ==============================================================================
-- Scenario 3: Send reminders to customers whose loans are due within the next 30 days.
-- ==============================================================================
DECLARE
    CURSOR c_due_loans IS
        SELECT l.LoanID, c.Name, l.EndDate
        FROM Loans l
        JOIN Customers c ON l.CustomerID = c.CustomerID
        WHERE l.EndDate BETWEEN SYSDATE AND SYSDATE + 30;
BEGIN
    DBMS_OUTPUT.PUT_LINE('--- LOAN DUE REMINDERS ---');
    FOR r IN c_due_loans LOOP
        DBMS_OUTPUT.PUT_LINE('Reminder: Customer ' || r.Name || ', your Loan (ID: ' || r.LoanID || ') is due on ' || TO_CHAR(r.EndDate, 'YYYY-MM-DD') || '.');
    END LOOP;
END;
/
