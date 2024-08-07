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

function displayMeetings(meetingArray) {
    const meetingContainer = document.getElementById('meetingContainer');
    if (meetingContainer) {
        meetingContainer.innerHTML = '';

        meetingArray.forEach((meeting, index) => {

            const collapseId = `collapseCardExample${index}`;

            const card = document.createElement('div');
            card.className = 'card shadow mb-4';

            card.innerHTML = `
                <a href="#${collapseId}" class="d-block card-header py-3" data-toggle="collapse" role="button" aria-expanded="true" aria-controls="${collapseId}">
                    <h6 class="m-0 font-weight-bold text-gray-600">${meeting.title}</h6>
                </a>
                <div class="collapse" id="${collapseId}">
                    <div class="card-body">
                        <p><strong>제목:</strong> ${meeting.title}</p>
                        <p><strong>주차:</strong> ${meeting.weekNumber}</p>
                        <p><strong>출석 가능 시간:</strong> ${formatTime(meeting.start)} - ${formatTime(meeting.end)}</p>
                        <p><strong>장소:</strong> ${meeting.location}</p>
                        <p><strong>내용:</strong> ${meeting.content}</p>
                        <p><strong>참여인원:</strong> ${meeting.memberCount}</p>
                        <p><strong>기수:</strong> ${meeting.cardinal}</p>
                        <p><strong>출석 코드:</strong> ${meeting.code}</p>
                        <button id="closeAttendance" class="btn btn-danger text-sm-center" onclick="closeAttendance('${meeting.start}', ${meeting.cardinal})">출석마감</button>
                    </div>
                </div>
            `;
            meetingContainer.appendChild(card);
        });
    }
}

function filterMeetings() {
    const query = document.getElementById('topbarSearchInput').value.toLowerCase();
    const filteredMeetings = allMeetings.filter(meeting =>
        meeting.cardinal.toString().includes(query) ||
        meeting.title.toLowerCase().includes(query) ||
        meeting.weekNumber.toString().includes(query)
    );
    console.log('Filtered meetings:', filteredMeetings);
    displayMeetings(filteredMeetings);
}

function saveMeeting() {
    const weekNumber = document.getElementById('weekNumber').value;
    const title = document.getElementById('title').value;
    const content = document.getElementById('eventContent').value;
    const location = document.getElementById('location').value;
    const start = document.getElementById('startDateTime').value;
    const end = document.getElementById('endDateTime').value;
    const cardinal = document.getElementById('cardinal').value;

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

function formatTime(timeString) {
    const [date, time] = timeString.split('T');
    const [hours, minutes] = time.split(':');
    return `${date} ${hours}:${minutes}`;
}
