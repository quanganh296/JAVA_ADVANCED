-- Tạo Database (nếu chưa có)
CREATE DATABASE IF NOT EXISTS JavaAdvanced_DB;
USE JavaAdvanced_DB;

-- 1. Tạo bảng tài khoản [cite: 13, 14, 15, 16, 17, 18]
CREATE TABLE Accounts (
    AccountId VARCHAR(10) PRIMARY KEY,
    FullName NVARCHAR(50),
    Balance DECIMAL(18, 2)
);

-- 2. Chèn dữ liệu mẫu [cite: 19, 20]
INSERT INTO Accounts VALUES ('ACC01', 'Nguyen Van A', 5000), ('ACC02', 'Tran Thi B', 2000);

-- 3. Tạo Procedure cập nhật số dư [cite: 21, 22, 23, 24, 25, 26, 27, 28]
DELIMITER //
CREATE PROCEDURE sp_UpdateBalance (
    Id VARCHAR(10),
    Amount DECIMAL(18, 2)
)
BEGIN
    UPDATE Accounts SET Balance = Balance + Amount WHERE AccountId = Id;
END //
DELIMITER ;