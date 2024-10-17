// 달력 라이브러리를 불러옵니다.
const datepicker = document.getElementById('calendar');
let userDate;

function handleDateChange(selectedDates, dateStr, instance) {
    userDate = dateStr;
}

flatpickr(datepicker, {
    dateFormat: 'Y-m-d',
    enableTime: false,
    minDate: 'today',
    locale: 'ko',
    onChange: handleDateChange, // 날짜 선택 이벤트 핸들러 등록
});

// 일정을 생성합니다.
function addSchedule() {
    // 1. 작성한 일정을 불러옵니다.
    let date = userDate;
    let user = $('#user').val();
    let pw = $('#pw').val();
    let content = $('#content').val();

    if (date && user && pw && content) {
        // 4. 전달할 data JSON으로 만듭니다.
        let data = {'date': date, 'user': user, 'pw': pw, 'content': content};

        // 5. POST /api/memos 에 data를 전달합니다.
        $.ajax({
            type: "POST",
            url: "/api/schedule",
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function (response) {
                alert('메시지가 성공적으로 작성되었습니다.');
                window.location.reload();
            }
        });
    } else {
        alert('모든 항목을 입력해주세요.');
    }

}

$(document).ready(function () {
    // HTML 문서를 로드할 때마다 실행합니다.
    getSchedule();
})

// 전체 스케줄을 불러와서 보여줍니다.
function getSchedule() {
    // 1. 기존 스케줄 내용을 지웁니다.
    $('#cards-box').empty();
    // 2. 스케줄 목록을 불러와서 HTML로 붙입니다.
    $.ajax({
        type: 'GET',
        url: '/api/schedule',
        success: function (response) {
            for (let i = 0; i < response.length; i++) {
                let message = response[i];
                let id = message['id'];
                let user = message['user'];
                let content = message['content'];
                let date = message['date'];
                let updateDate = message['updateDate'];
                let createDate = message['createDate'];
                addHTML(id, user, content, date, updateDate, createDate);
            }
        }
    })
}

// 날짜를 기준으로 조회합니다.
function getDateSchedule() {
    // 1. 기존 스케줄 내용을 지웁니다.
    $('#cards-box').empty();

    // 2. 선택한 날짜 정보를 가져옵니다.
    let date = userDate;
    // 3. 스케줄 목록을 불러와서 HTML로 붙입니다.

    $.ajax({
        type: 'GET',
        url: `/api/schedule/date/${date}`,
        success: function (response) {
            for (let i = 0; i < response.length; i++) {
                let message = response[i];
                let id = message['id'];
                let user = message['user'];
                let content = message['content'];
                let date = message['date'];
                let updateDate = message['updateDate'];
                let createDate = message['createDate'];
                addHTML(id, user, content, date, updateDate, createDate);
            }
        }, error: function (xhr, status, error) {
            alert('해당 날짜에 일정이 없습니다.');
            console.error('AJAX 요청 실패:', status, error);
        }
    })
}

// ID를 기준으로 조회합니다.
function getIdSchedule() {
    // 1. 기존 스케줄 내용을 지웁니다.
    $('#cards-box').empty();

    // 2. 선택한 날짜 정보를 가져옵니다.
    let id = $('#id-check').val();

    // 3. 스케줄 목록을 불러와서 HTML로 붙입니다.
    $.ajax({
        type: 'GET',
        url: `/api/schedule/id/${id}`,
        success: function (response) {
            let id = response.id;
            let user = response.user;
            let content = response.content;
            let date = response.date;
            let updateDate = response.updateDate;
            let createDate = response.createDate;
            addHTML(id, user, content, date, updateDate, createDate);
        }, error: function (xhr, status, error) {
            alert('id에 해당하는 일정이 없습니다.');
            console.error('AJAX 요청 실패:', status, error);
        }
    })
}

// 메모 하나를 HTML로 만들어서 body 태그 내 원하는 곳에 붙입니다.
function addHTML(id, user, content, date, updateDate, createDate) {
    // 1. HTML 태그를 만듭니다.
    let tempHtml = `<div class="card">
                    <!-- date/username 영역 -->
                    <div class="metadata">
                        <div id="date" class="date">
                            날짜: ${date}
                        </div>
                        <div id="user" class="user">
                            작성자명: ${user}
                        </div>
                        <div id="${id}" class="scheduleId">
                            ID: ${id}
                        </div>
                        <div class="updateDate">
                            최근 수정일: ${updateDate}
                        </div>
                        <div class="createDate">
                            등록일: ${createDate}
                        </div>
                    </div>
                    <!-- contents 조회/수정 영역-->
                    <div class="contents">
                        <div id="${id}-contents" class="text">
                            ${content}
                        </div>
                        <div id="${id}-editarea" class="edit">
                            <textarea id="${id}-textarea" class="te-edit" name="" id="" cols="30" rows="5"></textarea>
                        </div>
                    </div>
                    <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                        <button id="${id}-edit-btn" class="btn btn-primary edit-btn" type="button" onclick="editPost('${id}')">수정</button>
                        <button id="${id}-delete-btn" class="btn btn-danger delete-btn" type="button" onclick="deleteOne('${id}')">삭제</button>
                        <button id="${id}-submit-btn" class="btn btn-primary submit-btn" type="button" onclick="submitEdit('${id}')">저장</button>
                    </div>
                </div>`;
    // 2. #cards-box 에 HTML을 붙인다.
    $('#cards-box').append(tempHtml);
}

// 비밀번호를 확인합니다.
function checkPw(id) {
    let pw = prompt('비밀번호를 입력해주세요.');
    $.ajax({
        type: 'GET',
        url: `/api/schedule/id/${id}`,
        success: function (response) {
            for (let i = 0; i < response.length; i++) {
                let message = response[i];
                let responsePw = message['pw'];
                if (pw !== responsePw) {
                    alert('비밀번호가 일치하지 않습니다.');
                    window.location.reload();
                }
            }
        }, error: function (xhr, status, error) {
            console.error('AJAX 요청 실패:', status, error);
        }
    });
}

// 수정 버튼을 눌렀을 때, 기존 작성 내용을 textarea 에 전달합니다.
// 숨길 버튼을 숨기고, 나타낼 버튼을 나타냅니다.
function editPost(id) {
    checkPw(id);
    showEdits(id);
    let contents = $(`#${id}-contents`).text().trim();
    $(`#${id}-textarea`).val(contents);
}

function showEdits(id) {
    $(`#${id}-editarea`).show();
    $(`#${id}-submit-btn`).show();
    $(`#${id}-delete-btn`).show();

    $(`#${id}-contents`).hide();
    $(`#${id}-edit-btn`).hide();
}

// 메모를 수정합니다.
function submitEdit(id) {
    // 1. 작성 대상 메모의 username과 contents 를 확인합니다.
    let content = $(`#${id}-textarea`).val().trim();

    // 2. 전달할 data JSON으로 만듭니다.
    let data = {'content': content};

    // 4. PUT /api/memos/{id} 에 data를 전달합니다.
    $.ajax({
        type: "PUT",
        url: `/api/schedule/${id}`,
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (response) {
            alert('메시지 변경에 성공하였습니다.');
            window.location.reload();
        }, error: function (xhr, status, error) {
            console.error('AJAX 요청 실패:', status, error);
        }
    });
}

// 메모를 삭제합니다.
function deleteOne(id) {
    checkPw(id);
    $.ajax({
        type: "DELETE",
        url: `/api/schedule/${id}`,
        success: function (response) {
            alert('메시지 삭제에 성공하였습니다.');
            window.location.reload();
        }, error: function (xhr, status, error) {
            console.error('AJAX 요청 실패:', status, error);
        }
    })
}