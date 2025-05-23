-- Thêm dữ liệu mẫu vào bảng GIAOVIEN
INSERT INTO GIAOVIEN (MAGV, HO, TEN, SODTLL, DIACHI)
VALUES 
('GV001', N'Nguyễn Văn', N'An', '0901234567', N'123 Nguyễn Trãi, Quận 1, TP.HCM'),
('GV002', N'Trần Thị', N'Bình', '0912345678', N'456 Lê Lợi, Quận 5, TP.HCM'),
('GV003', N'Lê Văn', N'Cường', '0923456789', N'789 Điện Biên Phủ, Quận 3, TP.HCM');

-- Kiểm tra dữ liệu đã thêm
SELECT * FROM GIAOVIEN; 