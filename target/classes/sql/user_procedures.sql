-- Stored procedures cho quản lý tài khoản

-- Lấy tất cả tài khoản
CREATE OR ALTER PROCEDURE sp_TAIKHOAN_GetAll
AS
BEGIN
    SELECT * FROM TAIKHOAN
END
GO

-- Lấy tài khoản theo tên đăng nhập
CREATE OR ALTER PROCEDURE sp_TAIKHOAN_GetByLoginName
    @LOGINNAME NVARCHAR(50)
AS
BEGIN
    SELECT * FROM TAIKHOAN WHERE LOGINNAME = @LOGINNAME
END
GO

-- Lấy tài khoản theo mã giáo viên
CREATE OR ALTER PROCEDURE sp_TAIKHOAN_GetByMaGV
    @MAGV NCHAR(8)
AS
BEGIN
    SELECT * FROM TAIKHOAN WHERE MAGV_REF = @MAGV
END
GO

-- Lấy tài khoản theo mã sinh viên
CREATE OR ALTER PROCEDURE sp_TAIKHOAN_GetByMaSV
    @MASV NCHAR(8)
AS
BEGIN
    SELECT * FROM TAIKHOAN WHERE MASV_REF = @MASV
END
GO

-- Lấy tài khoản theo vai trò
CREATE OR ALTER PROCEDURE sp_TAIKHOAN_GetByRole
    @ROLE NVARCHAR(20)
AS
BEGIN
    SELECT * FROM TAIKHOAN WHERE ROLE = @ROLE
END
GO

-- Thêm tài khoản mới
CREATE OR ALTER PROCEDURE sp_TAIKHOAN_Insert
    @LOGINNAME NVARCHAR(50),
    @PASSWORD NVARCHAR(100),
    @ROLE NVARCHAR(20),
    @MAGV NCHAR(8) = NULL,
    @MASV NCHAR(8) = NULL
AS
BEGIN
    INSERT INTO TAIKHOAN (LOGINNAME, PASSWORD, ROLE, MAGV_REF, MASV_REF)
    VALUES (@LOGINNAME, @PASSWORD, @ROLE, @MAGV, @MASV)
END
GO

-- Cập nhật tài khoản
CREATE OR ALTER PROCEDURE sp_TAIKHOAN_Update
    @LOGINNAME NVARCHAR(50),
    @PASSWORD NVARCHAR(100),
    @ROLE NVARCHAR(20),
    @MAGV NCHAR(8) = NULL,
    @MASV NCHAR(8) = NULL
AS
BEGIN
    UPDATE TAIKHOAN
    SET PASSWORD = @PASSWORD,
        ROLE = @ROLE,
        MAGV_REF = @MAGV,
        MASV_REF = @MASV
    WHERE LOGINNAME = @LOGINNAME
END
GO

-- Xóa tài khoản
CREATE OR ALTER PROCEDURE sp_TAIKHOAN_Delete
    @LOGINNAME NVARCHAR(50)
AS
BEGIN
    DELETE FROM TAIKHOAN WHERE LOGINNAME = @LOGINNAME
END
GO

-- Kiểm tra đăng nhập
CREATE OR ALTER PROCEDURE sp_TAIKHOAN_ValidateLogin
    @LOGINNAME NVARCHAR(50),
    @PASSWORD NVARCHAR(100)
AS
BEGIN
    DECLARE @count INT
    SELECT @count = COUNT(*)
    FROM TAIKHOAN
    WHERE LOGINNAME = @LOGINNAME AND PASSWORD = @PASSWORD
    
    SELECT CASE WHEN @count > 0 THEN 1 ELSE 0 END
END
GO

-- Kiểm tra tên đăng nhập đã tồn tại hay chưa
CREATE OR ALTER PROCEDURE sp_TAIKHOAN_ExistsByLoginName
    @LOGINNAME NVARCHAR(50)
AS
BEGIN
    DECLARE @count INT
    SELECT @count = COUNT(*)
    FROM TAIKHOAN
    WHERE LOGINNAME = @LOGINNAME
    
    SELECT CASE WHEN @count > 0 THEN 1 ELSE 0 END
END
GO