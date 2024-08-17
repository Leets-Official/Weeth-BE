const apiEndpoint = (window.location.hostname === 'localhost')
    ? 'http://localhost:8080/api/v1'
    : 'https://api.weeth.site/api/v1';

document.addEventListener('DOMContentLoaded', function () {
    if (getToken()) {
        loadAttendanceEvents();
    } else {
        alert('JWT token is missing. Please log in.');
        window.location.href = "/admin/login";
    }
    document.getElementById('topbarSearchInput').addEventListener('input', filterMeetings);


    document.getElementById('meetingForm').addEventListener('submit', function (event) {
        event.preventDefault();
        if (!confirm('출석 일정을 저장하시겠습니까?')) {
            return;
        }
        saveMeeting();
    });
});

let allMeetings = [];
let currentMeetingId = 7;

function loadAttendanceEvents() {
    apiRequest(`${apiEndpoint}/admin/meetings`)
        .then(response => response.json())
        .then(data => {
            if (data.code === 200) {
                allMeetings = data.data;
                displayMeetings(allMeetings);
            } else {
                throw new Error(data.message);
            }
        })
        .catch(error => {
            showTokenErrorMessage(error.message);
            console.error('Error loading meetings:', error);
        });
}

function groupMeetingsByCardinal(meetings) {
    return meetings.reduce((groups, meeting) => {
        const cardinal = meeting.cardinal;
        if (!groups[cardinal]) {
            groups[cardinal] = [];
        }
        groups[cardinal].push(meeting);
        return groups;
    }, {});
}

function displayMeetings(meetingArray) {
    const meetingContainer = document.getElementById('meetingContainer');
    if (meetingContainer) {
        meetingContainer.innerHTML = '';

        // 미팅을 카디널별로 그룹화
        const groupedMeetings = groupMeetingsByCardinal(meetingArray);

        // 그룹화된 카디널별로 섹션을 생성
        Object.keys(groupedMeetings).forEach(cardinal => {
            const section = document.createElement('div');
            section.className = 'card shadow mb-4';

            const header = document.createElement('div');
            header.className = 'card-header py-3';
            header.innerHTML = `<h6 class="m-0 font-weight-bold text-success">${cardinal}기 정기모임</h6>`;
            section.appendChild(header);

            groupedMeetings[cardinal].forEach((meeting, index) => {
                const collapseId = `collapseCardExample${cardinal}_${index}`;

                const card = document.createElement('div');
                card.className = 'card-body';

                card.innerHTML = `
                    <a href="#${collapseId}" class="d-block card-header py-3" data-toggle="collapse" role="button" aria-expanded="true" aria-controls="${collapseId}">
                        <h6 class="m-0 font-weight-bold text-gray-600">${meeting.title}</h6>
                    </a>
                    <div class="collapse" id="${collapseId}">
                        <div class="card-body">
                            <p><strong>ID:</strong> ${meeting.id}</p>
                            <p><strong>제목:</strong> ${meeting.title}</p>
                            <p><strong>주차:</strong> ${meeting.weekNumber}</p>
                            <p><strong>출석 가능 시간:</strong> ${formatTime(meeting.start)} - ${formatTime(meeting.end)}</p>
                            <p><strong>장소:</strong> ${meeting.location}</p>
                            <p><strong>내용:</strong> ${meeting.content}</p>
                            <p><strong>참여인원:</strong> ${meeting.memberCount}명</p>
                            <p><strong>출석 코드:</strong> ${meeting.code}</p>
                            <button id="closeAttendance" class="btn btn-danger text-sm-center" onclick="closeAttendance('${meeting.start}', ${meeting.cardinal})">출석마감</button>
                            <button id="deleteMeeting" class="btn btn-danger text-sm-center" onclick="deleteMeeting('${meeting.id}')">삭제</button>
                            <button id="updateAttendance" href="#" data-toggle="modal" data-target="#updateMeeting" class="btn btn-primary text-sm-center" onclick="setModalContent('${meeting.id}')">수정</button>
                        </div>
                    </div>
                `;
                section.appendChild(card);
            });

            meetingContainer.appendChild(section);
        });
    }
}

function filterMeetings() {
    const query = document.getElementById('topbarSearchInput').value.toLowerCase();
    const filteredMeetings = allMeetings.filter(meeting =>
        meeting.cardinal.toString().includes(query) ||
        meeting.title.toLowerCase().includes(query) ||
        meeting.weekNumber.toString().includes(query) ||
        meeting.content.toLowerCase().includes(query)
    );
    console.log('Filtered meetings:', filteredMeetings);
    displayMeetings(filteredMeetings);
}

function saveMeeting() {
    const weekNumber = document.getElementById('weekNumber').value;
    const title = document.getElementById('title').value;
    const content = document.getElementById('eventContent').value;
    const location = document.getElementById('location').value;
    const start = document.getElementById('startDate').value;
    const end = document.getElementById('endDate').value;
    const cardinal = document.getElementById('cardinal').value;

    // 입력 값 유효성 검사
    if (!weekNumber || !title || !content || !location || !start || !end || !cardinal) {
        alert('모든 필드를 입력해주세요.');
        return;
    }

    const startDate = new Date(start);
    const endDate = new Date(end);
    if (startDate > endDate) {
        alert('시작 날짜는 종료 날짜보다 이전이어야 합니다.');
        return;
    }

    const meetingData = {
        title: title,
        content: content,
        location: location,
        start: start,
        end: end,
        weekNumber: parseInt(weekNumber),
        cardinal: parseInt(cardinal)
    };

    // 먼저 주차 정보를 저장하고 그 다음 출석 일정을 저장합니다.
    apiRequest(`${apiEndpoint}/admin/meetings`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(meetingData)
    })
        .then(response => response.json())
        .then(data => {
            if (data.code === 200) {
                alert(`미팅 저장 성공: ${data.message}`);
                loadAttendanceEvents();
            } else {
                throw new Error(data.message);
            }
        })
        .catch(error => {
            alert(`저장 실패: ${error.message}`);
        });
}

function updateMeeting(){
    const weekNumber = document.getElementById('updateWeekNumber').value;
    const title = document.getElementById('updateTitle').value;
    const content = document.getElementById('updateContent').value;
    const location = document.getElementById('updateLocation').value;
    const start = document.getElementById('start').value;
    const end = document.getElementById('end').value;
    const cardinal = document.getElementById('updateCardinal').value;

    // 입력 값 유효성 검사
    if (!weekNumber || !title || !content || !location || !start || !end || !cardinal) {
        alert('모든 필드를 입력해주세요.');
        return;
    }

    const startDate = new Date(start);
    const endDate = new Date(end);
    if (startDate > endDate) {
        alert('시작 날짜는 종료 날짜보다 이전이어야 합니다.');
        return;
    }

    const meetingData = {
        title: title,
        content: content,
        location: location,
        start: start,
        end: end,
        weekNumber: parseInt(weekNumber),
        cardinal: parseInt(cardinal)
    };

    // 먼저 주차 정보를 저장하고 그 다음 출석 일정을 저장합니다.
    apiRequest(`${apiEndpoint}/admin/meetings/${currentMeetingId}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(meetingData)
    })
        .then(response => response.json())
        .then(data => {
            if (data.code === 200) {
                alert(`정기모임 수정 성공: ${data.message}`);
                loadAttendanceEvents();
            } else {
                throw new Error(data.message);
            }
        })
        .catch(error => {
            alert(`정기모임 수정 실패: ${error.message}`);
        });
}

function closeAttendance(date, cardinal){
    if (!confirm('출석을 마감 하시겠습니까? 출석 마감 후에는 더 이상 출석체크를 할 수 없습니다.')) {
        return;
    }
    const now = date.split('T')[0];

    apiRequest(`${apiEndpoint}/admin/attendances?now=${now}&cardinal=${cardinal}`,{
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
    })
        .then(response => response.json())
        .then(data => {
            if(data.code === 200) {
                alert(`출석 마감 성공: ${data.message}`);
                loadAttendanceEvents();
            } else {
                throw new Error(data.message);
            }
        })
        .catch(error => {
            alert(`출석 마감 실패: ${error.message}`);
        });
}

function deleteMeeting(meetingId){
    if (!confirm('정기모임을 삭제하시겠습니까? 삭제된 정기모임에 해당하는 출석을 체크하지 못하게 됩니다.')) {
        return;
    }

    apiRequest(`${apiEndpoint}/admin/meetings/${meetingId}`,{
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        },
    })
        .then(response => response.json())
        .then(data => {
            if(data.code === 200) {
                alert(`정기모임 삭제 성공: ${data.message}`);
                loadAttendanceEvents();
            } else {
                throw new Error(data.message);
            }
        })
        .catch(error => {
            alert(`정기모임 삭제 실패: ${error.message}`);
        });
}



function setModalContent(meetingId) {
    const meeting = allMeetings.find(m => m.id === parseInt(meetingId));
    currentMeetingId = meetingId;
    const modalBodyContent = document.getElementById('meeting-modal-content');
    if (modalBodyContent) {
        modalBodyContent.innerHTML = `
            <form id="meetingUpdateForm">
                <div class="form-group">
                    <label for="updateTitle">제목</label>
                    <input type="text" class="form-control" id="updateTitle" placeholder="제목 입력" value="${meeting.title}">
                </div>
                <div class="form-group">
                    <label for="updateWeekNumber">주차</label>
                    <input type="number" class="form-control" id="updateWeekNumber" placeholder="주차 입력" min="0" value="${meeting.weekNumber}">
                </div>
                <div class="form-group">
                    <label for="start">시작 시간</label>
                    <input type="datetime-local" class="form-control" id="start" value="${meeting.start}">
                </div>
                <div class="form-group">
                    <label for="end">종료 시간</label>
                    <input type="datetime-local" class="form-control" id="end" value="${meeting.end}">
                </div>
                <div class="form-group">
                    <label for="updateLocation">장소</label>
                    <input type="text" class="form-control" id="updateLocation" placeholder="장소 입력" value="${meeting.location}">
                </div>
                <div class="form-group">
                    <label for="content">내용</label>
                    <textarea class="form-control" id="updateContent" rows="3" placeholder="내용 입력">${meeting.content}</textarea>
                </div>
                <div class="form-group">
                    <label for="updateCardinal">기수</label>
                    <input type="number" class="form-control" id="updateCardinal" placeholder="기수 입력" value="${meeting.cardinal}" min="1" step="1">
                </div>
                <button type="submit" class="btn btn-primary" id="submitMeetingButton">제출</button>
            </form>
        `;
        document.getElementById('meetingUpdateForm').addEventListener('submit', function (event) {
            event.preventDefault();
            if (!confirm('출석 일정을 수정하시겠습니까?')) {
                return;
            }
            updateMeeting();
        });
    }
}

function formatTime(timeString) {
    const [date, time] = timeString.split('T');
    const [hours, minutes] = time.split(':');
    return `${date} ${hours}:${minutes}`;
}
