SET SERVEROUTPUT ON;

-- ==============================================================================
-- Scenario 1: Calculate and update savings account balances by 1% monthly interest.
-- ==============================================================================
CREATE OR REPLACE PROCEDURE ProcessMonthlyInterest AS
BEGIN
    UPDATE Accounts
    SET Balance = Balance * 1.01,
        LastModified = SYSDATE
    WHERE AccountType = 'Savings';
    
    DBMS_OUTPUT.PUT_LINE('Monthly interest of 1% applied successfully to all Savings accounts.');
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error processing monthly interest: ' || SQLERRM);
        ROLLBACK;
END;
/

-- ==============================================================================
-- Scenario 2: Update employee salary in a given department by a bonus percentage.
-- ==============================================================================
CREATE OR REPLACE PROCEDURE UpdateEmployeeBonus (
    p_department IN VARCHAR2,
    p_bonus_percentage IN NUMBER
) AS
BEGIN
    -- Input validation
    IF p_bonus_percentage < 0 THEN
        DBMS_OUTPUT.PUT_LINE('Error: Bonus percentage cannot be negative.');
        RETURN;
    END IF;

    UPDATE Employees
    SET Salary = Salary * (1 + p_bonus_percentage / 100)
    WHERE Department = p_department;
    
    DBMS_OUTPUT.PUT_LINE('Salary updated with a ' || p_bonus_percentage || '% bonus for department: ' || p_department);
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error updating employee bonus: ' || SQLERRM);
        ROLLBACK;
END;
/

-- ==============================================================================
-- Scenario 3: Transfer funds between accounts, explicitly checking for sufficient balance.
-- ==============================================================================
CREATE OR REPLACE PROCEDURE TransferFunds (
    p_source_account_id IN NUMBER,
    p_dest_account_id IN NUMBER,
    p_amount IN NUMBER
) AS
    v_balance NUMBER;
BEGIN
    -- Input validation
    IF p_amount <= 0 THEN
        RAISE_APPLICATION_ERROR(-20002, 'Transfer amount must be positive.');
    END IF;

    -- Fetch and check source balance
    SELECT Balance INTO v_balance FROM Accounts WHERE AccountID = p_source_account_id FOR UPDATE;
    
    IF v_balance < p_amount THEN
        RAISE_APPLICATION_ERROR(-20001, 'Insufficient funds in the source account.');
    END IF;

    -- Verify destination account exists
    DECLARE
        v_dest_exists NUMBER;
    BEGIN
        SELECT 1 INTO v_dest_exists FROM Accounts WHERE AccountID = p_dest_account_id;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RAISE_APPLICATION_ERROR(-20003, 'Destination account does not exist.');
    END;

    -- Deduct from source
    UPDATE Accounts
    SET Balance = Balance - p_amount,
        LastModified = SYSDATE
    WHERE AccountID = p_source_account_id;
    
    -- Add to destination
    UPDATE Accounts
    SET Balance = Balance + p_amount,
        LastModified = SYSDATE
    WHERE AccountID = p_dest_account_id;
    
    DBMS_OUTPUT.PUT_LINE('Transferred $' || p_amount || ' from Account ' || p_source_account_id || ' to Account ' || p_dest_account_id);
    COMMIT;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('Error: Source Account not found.');
        ROLLBACK;
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error during transfer: ' || SQLERRM);
        ROLLBACK;
END;
/
