Hi!
EX:
Giao tiếp với server tại cổng 1107, theo kịch bản:
1.1.Gửi thông điệp là một chuỗi chứa mã sinh viên theo định dạng “;studentCode;qCode”.
Ví dụ: “;B15DCCN001;100”
1.2.Nhận thông điệp từ server chứa requestId là một chuỗi ngẫu nhiên duy nhất và một
chuỗi chứa 2 số cần xử lý a và b cần xử lý theo định dạng “requestId;a,b”
1.3.Thực hiện tính toán ước chung lớn nhất, bội chung nhỏ nhất, tổng, tích của a và b và gửi
thông điệp lên server theo định dạng “requestId;gcd,lcm,sum,mul”
1.4.Đóng socket và kết thúc
RUN:
step 1 : run server
step 2 : run client