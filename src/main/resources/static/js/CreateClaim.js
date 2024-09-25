let currentStep = 1;
let dayIndex ; // Khởi tạo dayIndex dựa trên số lượng hàng hiện có

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
    dayIndex = document.querySelectorAll('#claims-body tr').length;
    showStep(currentStep);
});

// Hàm kiểm tra thời gian dự án
function validateTimes() {
    const projectStartTime = document.getElementById("project-start-time");
    const projectEndTime = document.getElementById("project-end-time");

    const startDate = new Date(projectStartTime.value);
    const endDate = new Date(projectEndTime.value);

    if (projectStartTime.value && projectEndTime.value) {
        if (startDate >= endDate) {
            alert("Ngày bắt đầu phải nhỏ hơn ngày kết thúc.");
            projectStartTime.value = "";
            projectEndTime.value = "";
        }
    }
}

// Hàm kiểm tra thời gian làm việc
function validateTime(startTimeInput, endTimeInput) {
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
        alert("Vui lòng nhập cả thời gian bắt đầu và kết thúc.");
        return false;
    }
    return true;
}

// Hàm tính toán tổng số giờ làm việc
function calculateTotalHours(row) {
    const startTimeInput = row.querySelector('.start-time');
    const endTimeInput = row.querySelector('.end-time');
    const totalHoursInput = row.querySelector('.total-hours');

    if (validateTime(startTimeInput, endTimeInput)) {
        const start = new Date(`1970-01-01T${startTimeInput.value}:00`);
        const end = new Date(`1970-01-01T${endTimeInput.value}:00`);
        const diff = (end - start) / 1000 / 60 / 60;

        totalHoursInput.value = diff > 0 ? diff.toFixed(2) : "";
    }
}

function checkDuplicateDates(dayInput) {
    var dates = [];
    var hasDuplicate = false;

    // Lặp qua từng trường ngày
    document.querySelectorAll('.day').forEach(function (input) {
        var dateValue = input.value;
        if (dates.includes(dateValue) && input !== dayInput) { // Nếu ngày đã tồn tại và không phải là ngày đang nhập
            hasDuplicate = true;
            alert("Ngày đã được chọn, vui lòng chọn ngày khác.");
            input.value = ''; // Xóa trường ngày bị trùng
        }
        dates.push(dateValue);
    });
    return hasDuplicate; // Trả về true nếu có trùng

}

// Cập nhật thứ trong tuần dựa trên ngày
function updateDayOfWeek(dayInput, dayOfWeekInput) {
    // Nếu ngày bị trùng, không cập nhật thứ trong tuần
    if (checkDuplicateDates(dayInput)) {
        dayInput.value = ''; // Xóa giá trị ngày nếu có ngày trùng
        dayOfWeekInput.value = ''; // Xóa giá trị thứ trong tuần
        return; // Thoát khỏi hàm nếu có ngày trùng
    }

    // Nếu không trùng ngày, tiếp tục cập nhật thứ trong tuần
    if (dayInput.value) {
        const date = new Date(dayInput.value);
        const daysOfWeek = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
        dayOfWeekInput.value = daysOfWeek[date.getDay()];
    } else {
        dayOfWeekInput.value = ''; // Xóa giá trị nếu không có ngày
    }
}



// Gắn sự kiện cho các trường đầu vào
document.addEventListener('DOMContentLoaded', function() {
    const projectStartTime = document.getElementById("project-start-time");
    const projectEndTime = document.getElementById("project-end-time");

    projectEndTime.addEventListener("change", validateTimes);

    // Gán sự kiện cho tất cả các trường ngày, thời gian bắt đầu, và thời gian kết thúc hiện có
    document.querySelectorAll('#claims-body .day').forEach(input => {
        input.addEventListener('change', function() {
            updateDayOfWeek(this, this.closest('tr').querySelector('.day-of-week'));
        });
    });
    document.querySelectorAll('#claims-body .start-time, #claims-body .end-time').forEach(input => {
        input.addEventListener('change', function() {
            calculateTotalHours(this.closest('tr'));
        });
    });

    // Khi nhấn nút "Thêm ngày"
    document.getElementById("add-day-btn").addEventListener("click", function() {
        // Lấy phần tbody của bảng
        const tbody = document.getElementById("claims-body");

        // Tạo một hàng mới
        const newRow = document.createElement("tr");

        // Nội dung của hàng mới
        newRow.innerHTML = `
            <td>
                <input type="date" class="day" name="claimDays[${dayIndex}].date">
            </td>
            <td>
                <input type="text" class="day-of-week" name="claimDays[${dayIndex}].day" readonly>
            </td>
            <td>
                <input type="time" class="start-time" name="claimDays[${dayIndex}].fromDate">
            </td>
            <td>
                <input type="time" class="end-time" name="claimDays[${dayIndex}].toDate">
            </td>
            <td>
                <input type="text" class="total-hours" name="claimDays[${dayIndex}].totalOfHours" readonly>
            </td>
            <td>
                <textarea class="remarks" name="claimDays[${dayIndex}].description" rows="2"></textarea>
            </td>
            <td>
                <button type="button" class="btn btn-danger remove-btn">Cancel</button>
            </td>
        `;

        // Thêm hàng mới vào tbody
        tbody.appendChild(newRow);

        // Gán sự kiện cho các phần tử mới
        newRow.querySelector('.day').addEventListener('change', function() {
            updateDayOfWeek(this, this.closest('tr').querySelector('.day-of-week'));

        });
        newRow.querySelector('.start-time').addEventListener('change', function() {
            calculateTotalHours(this.closest('tr'));
        });
        newRow.querySelector('.end-time').addEventListener('change', function() {
            calculateTotalHours(this.closest('tr'));
        });
        newRow.querySelector('.remove-btn').addEventListener('click', function() {
            newRow.remove();
        });

        dayIndex++; // Tăng chỉ số index cho hàng mới
    },{ once: true });

    // Gán sự kiện cho nút xóa của các hàng hiện có
    document.querySelectorAll(".remove-btn").forEach(function(button) {
        button.addEventListener("click", function() {
            button.closest("tr").remove();
        });
    });
});
