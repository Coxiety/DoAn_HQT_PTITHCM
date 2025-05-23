-- Stored procedure để lấy tất cả sinh viên
CREATE OR ALTER PROCEDURE SP_SINHVIEN_GetAll
AS
BEGIN
    SELECT MASV, HO, TEN, NGAYSINH, DIACHI, MALOP
    FROM SINHVIEN
    ORDER BY TEN, HO
END
GO

-- Stored procedure để lấy sinh viên theo mã sinh viên
CREATE OR ALTER PROCEDURE SP_SINHVIEN_GetByMaSV
    @MASV NCHAR(8)
AS
BEGIN
    SELECT MASV, HO, TEN, NGAYSINH, DIACHI, MALOP
    FROM SINHVIEN
    WHERE MASV = @MASV
END
GO

-- Stored procedure để lấy sinh viên theo mã lớp
CREATE OR ALTER PROCEDURE SP_SINHVIEN_GetByMaLop
    @MALOP NCHAR(8)
AS
BEGIN
    SELECT MASV, HO, TEN, NGAYSINH, DIACHI, MALOP
    FROM SINHVIEN
    WHERE MALOP = @MALOP
    ORDER BY TEN, HO
END
GO

-- Stored procedure để lấy sinh viên theo họ và tên
CREATE OR ALTER PROCEDURE SP_SINHVIEN_GetByHoTen
    @HO NVARCHAR(50),
    @TEN NVARCHAR(10)
AS
BEGIN
    SELECT MASV, HO, TEN, NGAYSINH, DIACHI, MALOP
    FROM SINHVIEN
    WHERE HO LIKE '%' + @HO + '%' AND TEN LIKE '%' + @TEN + '%'
    ORDER BY TEN, HO
END
GO

-- Stored procedure để tạo mã sinh viên mới
CREATE OR ALTER PROCEDURE SP_SINHVIEN_GetNewMaSV
    @MASV NCHAR(8) OUTPUT
AS
BEGIN
    DECLARE @NextID INT
    DECLARE @Prefix NCHAR(1) = 'N'
    
    -- Tìm mã sinh viên có số lớn nhất
    SELECT @NextID = ISNULL(MAX(CAST(SUBSTRING(MASV, 2, 7) AS INT)), 0) + 1
    FROM SINHVIEN
    WHERE MASV LIKE @Prefix + '%'
    
    -- Tạo mã mới với format Nxxxxxxx (x là số)
    SET @MASV = @Prefix + RIGHT('0000000' + CAST(@NextID AS VARCHAR(7)), 7)
END
GO

-- Stored procedure để thêm mới sinh viên
CREATE OR ALTER PROCEDURE SP_SINHVIEN_Insert
    @HO NVARCHAR(50),
    @TEN NVARCHAR(10),
    @NGAYSINH DATETIME,
    @DIACHI NVARCHAR(100),
    @MALOP NCHAR(8)
AS
BEGIN
    -- Tạo mã sinh viên mới
    DECLARE @MASV NCHAR(8)
    EXEC SP_SINHVIEN_GetNewMaSV @MASV OUTPUT
    
    -- Thêm sinh viên mới
    INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, MALOP)
    VALUES (@MASV, @HO, @TEN, @NGAYSINH, @DIACHI, @MALOP)
    
    -- Trả về mã sinh viên đã tạo
    SELECT @MASV AS MASV
END
GO

-- Stored procedure để cập nhật thông tin sinh viên
CREATE OR ALTER PROCEDURE SP_SINHVIEN_Update
    @MASV NCHAR(8),
    @HO NVARCHAR(50),
    @TEN NVARCHAR(10),
    @NGAYSINH DATETIME,
    @DIACHI NVARCHAR(100),
    @MALOP NCHAR(8)
AS
BEGIN
    UPDATE SINHVIEN
    SET HO = @HO,
        TEN = @TEN,
        NGAYSINH = @NGAYSINH,
        DIACHI = @DIACHI,
        MALOP = @MALOP
    WHERE MASV = @MASV
END
GO

-- Stored procedure để xóa sinh viên
CREATE OR ALTER PROCEDURE SP_SINHVIEN_Delete
    @MASV NCHAR(8)
AS
BEGIN
    DELETE FROM SINHVIEN WHERE MASV = @MASV
END
GO 