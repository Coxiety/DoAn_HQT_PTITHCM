document.addEventListener('DOMContentLoaded', function() {
    // Lấy các phần tử DOM
    const loginBtn = document.getElementById('loginBtn');
    const loginModal = document.getElementById('loginModal');
    const closeModal = document.getElementById('closeModal');
    const userTypeRadios = document.querySelectorAll('input[name="userType"]');
    const usernameLabel = document.getElementById('usernameLabel');
    const loginForm = document.querySelector('#loginModal form');
    
    // Mở modal đăng nhập khi click vào nút đăng nhập
    loginBtn.addEventListener('click', function() {
        loginModal.style.display = 'block';
    });
    
    // Đóng modal khi click vào nút đóng
    closeModal.addEventListener('click', function() {
        loginModal.style.display = 'none';
    });
    
    // Đóng modal khi click bên ngoài modal
    window.addEventListener('click', function(event) {
        if (event.target === loginModal) {
            loginModal.style.display = 'none';
        }
    });
    
    // Thay đổi label khi chọn loại người dùng
    userTypeRadios.forEach(function(radio) {
        radio.addEventListener('change', function() {
            if (this.value === 'student') {
                usernameLabel.textContent = 'Mã Sinh Viên';
            } else {
                usernameLabel.textContent = 'Tên Tài Khoản';
            }
        });
    });
}); 