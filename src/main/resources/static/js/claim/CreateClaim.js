let currentStep = 1;

function showStep(step) {
    // Ẩn tất cả các bước và các bước hiện tại
    document.querySelectorAll('.form-step').forEach(function (element) {
        element.classList.remove('active');
    });
    document.querySelectorAll('.step').forEach(function (element) {
        element.classList.remove('active');
    });

    // Hiển thị bước hiện tại
    document.querySelector('.form-step[data-step="' + step + '"]').classList.add('active');
    document.querySelector('.step[data-step="' + step + '"]').classList.add('active');

    // Hiển thị hoặc ẩn các nút Quay lại và Tiếp theo
    const prevButton = document.querySelector('.action-buttons .prev-btn');
    const nextButton = document.querySelector('.action-buttons .next-btn');

    prevButton.style.display = (step === 1) ? 'none' : 'inline-block';
    nextButton.style.display = (step === 3) ? 'none' : 'inline-block';
}

function nextStep() {
    if (currentStep < 3) {
        currentStep++;
        showStep(currentStep);
    }
}

function prevStep() {
    if (currentStep > 1) {
        currentStep--;
        showStep(currentStep);
    }
}

// Gọi hàm showStep ban đầu để thiết lập trạng thái của các nút
document.addEventListener('DOMContentLoaded', function() {
    showStep(currentStep);
});

// Xử lý ngày và thời gian
document.addEventListener('DOMContentLoaded', function() {
    const dayInput = document.getElementById("day");
    const projectSelect = document.getElementById('project-name');
    const dayOfWeekInput = document.getElementById("day-of-week");
    const startTimeInput = document.getElementById("start-time");
    const endTimeInput = document.getElementById("end-time");
    const totalHoursInput = document.getElementById("total-hours");
    const projectStartTime = document.getElementById("project-start-time");
    const projectEndTime = document.getElementById("project-end-time");
    // Hàm kiểm tra thời gian
    function validateTimes() {
        const startDate = new Date(projectStartTime.value);
        const endDate = new Date(projectEndTime.value);

        if (projectStartTime.value && projectEndTime.value) {
            if (startDate >= endDate) {
                alert("Ngày bắt đầu phải nhỏ hơn ngày kết thúc.");
                projectStartTime.value="";
                projectEndTime.value="";
            }
        } else {
            alert("Vui lòng nhập cả ngày bắt đầu và ngày kết thúc.");

        }

    }
    function validateTime() {
        const startTime = startTimeInput.value;
        const endTime = endTimeInput.value;

        if (startTime && endTime) {
            const start = new Date(`1970-01-01T${startTime}:00`);
            const end = new Date(`1970-01-01T${endTime}:00`);

            if (start >= end) {
                alert("Thời gian bắt đầu phải nhỏ hơn thời gian kết thúc.");
                return false;
            }
        } else {
            alert("Vui lòng nhập cả thời gian bắt đầu và thời gian kết thúc.");
            return false;
        }
        return true;
    }

    // Hàm tính toán tổng số giờ làm việc
    function calculateTotalHours() {
        if (validateTime()) {
            const startTime = startTimeInput.value;
            const endTime = endTimeInput.value;

            const start = new Date(`1970-01-01T${startTime}:00`);
            const end = new Date(`1970-01-01T${endTime}:00`);

            let diff = (end - start) / 1000 / 60 / 60;

            if (diff < 0) {
                diff += 24;
            }

            totalHoursInput.value = diff.toFixed(2);
        } else {
            totalHoursInput.value = "";
        }
    }

    // Cập nhật thứ trong tuần dựa trên ngày
    function updateDayOfWeek() {
        const dateValue = dayInput.value;
        if (dateValue) {
            const date = new Date(dateValue);
            const daysOfWeek = ["Chủ nhật", "Thứ hai", "Thứ ba", "Thứ tư", "Thứ năm", "Thứ sáu", "Thứ bảy"];
            const dayOfWeek = daysOfWeek[date.getDay()];
            dayOfWeekInput.value = dayOfWeek;
        } else {
            dayOfWeekInput.value = "";
        }
    }

    endTimeInput.addEventListener("change", calculateTotalHours);
    projectEndTime.addEventListener("change", validateTimes);
    dayInput.addEventListener("change", updateDayOfWeek);

    // Xử lý các nút hành động
    const buttons = document.querySelectorAll(".action-buttons button");

    buttons.forEach(button => {
        button.addEventListener("click", function(event) {
            const action = button.textContent.trim();

            switch (action) {
                case "Save":
                    alert("Dữ liệu đã được lưu thành công!");
                    break;
                case "Submit":
                    alert("Yêu cầu đã được gửi đi!");
                    break;
                case "Approve":
                    alert("Yêu cầu đã được duyệt!");
                    break;
                case "Reject":
                    alert("Yêu cầu đã bị từ chối!");
                    break;
                case "Return":
                    alert("Yêu cầu đã được gửi lại để chỉnh sửa!");
                    break;
                case "Print":
                    alert("Đang chuẩn bị để in thông tin!");
                    window.print(); // In trang
                    break;
                case "Cancel Request":
                    alert("Yêu cầu đã bị hủy!");
                    break;
                case "Cancel":
                    alert("Thao tác đã bị hủy!");
                    break;
                case "Close":
                    alert("Đóng cửa sổ!");
                    window.close(); // Đóng cửa sổ trình duyệt
                    break;
                default:
                    console.warn("Hành động không xác định:", action);
            }
        });
    });
});
