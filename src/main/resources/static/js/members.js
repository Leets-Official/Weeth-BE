const apiEndpoint = (window.location.hostname === 'localhost')
    ? 'http://localhost:8080/api/v1'
    : 'https://api.weeth.site/api/v1';

document.addEventListener('DOMContentLoaded', function () {
    if (getToken()) {
        loadMembers();
    } else {
        alert('JWT token is missing. Please log in.');
        window.location.href = "/admin/login";
    }

    document.getElementById('topbarSearchInput').addEventListener('input', filterMembers);
    document.getElementById('approveSelectedButton').addEventListener('click', function() {
        confirmAction('승인', approveSelectedUsers);
    });
    const checkAllBox = document.getElementById('checkAll');
    checkAllBox.addEventListener('change', function() {
        const memberCheckboxes = document.querySelectorAll('.member-checkbox');
        memberCheckboxes.forEach(checkbox => {
            checkbox.checked = checkAllBox.checked;
        });
    });
});

let allMembers = [];

let selectedUserIds = [];

function updateSelectedUserIds() {
    selectedUserIds = Array.from(document.querySelectorAll('.member-checkbox:checked'))
        .map(checkbox => checkbox.value);
}

// 모든 사용자 조회
function loadMembers() {
    apiRequest(`${apiEndpoint}/admin/users/all`)
        .then(response => response.json())
        .then(data => {
            if (data.code === 200) {
                allMembers = data.data;
                displayMembers(allMembers);
            } else {
                throw new Error(data.message);
            }
        })
        .catch(error => {
            showTokenErrorMessage(error.message);
            console.error('Error loading members:', error);
        });
}

// 검색을 위한 필터
function filterMembers() {
    const query = document.getElementById('topbarSearchInput').value.toLowerCase();
    const filteredMembers = allMembers.filter(member =>
        member.name.toLowerCase().includes(query) ||
        member.email.toLowerCase().includes(query) ||
        member.studentId.toLowerCase().includes(query) ||
        member.tel.toLowerCase().includes(query) ||
        member.department.toLowerCase().includes(query) ||
        member.position.toLowerCase().includes(query) ||
        member.status.toLowerCase().includes(query) ||
        member.role.toLowerCase().includes(query)
    );
    displayMembers(filteredMembers);
}

// 조회된 사용자들을 반복하며 화면에 표시
function displayMembers(membersArray) {
    const membersList = document.getElementById('membersList');
    const statusMapping = {
        'ACTIVE': '활동 중',
        'WAITING': '대기 중',
        'LEFT': '탈퇴',
        'BANNED': '차단'
    };

    const roleMapping = {
        'ADMIN': '관리자',
        'USER': '사용자'
    };
    if (membersList) {
        membersList.innerHTML = '';

        membersArray.forEach(member => {
            const row = document.createElement('tr');
            let rowClass;
            switch (member.status) {
                case 'ACTIVE':
                    rowClass = 'border-left-success';
                    break;
                case 'BANNED':
                    rowClass = 'border-left-danger';
                    break;
                case 'LEFT':
                    rowClass = 'border-left-warning';
                    break;
                default:
                    rowClass = 'border-left-secondary';
            }
            const statusText = statusMapping[member.status] || member.status;
            const roleText = roleMapping[member.role] || member.role;

            row.innerHTML = `
                <td class="${rowClass}">
                    <input type="checkbox" class="member-checkbox" value="${member.id}">
                    <div>${member.id}</div>
                </td>
                <td>${member.name}</td>
                <td>${member.email}</td>
                <td>${member.studentId}</td>
                <td>${member.tel}</td>
                <td>${member.department}</td>
                <td>${member.cardinals.join(', ')}</td>
                <td>${statusText}</td>
                <td>${roleText}</td>
                <td>${member.attendanceCount}</td>
                <td>${member.absenceCount}</td>
                <td>${member.penaltyCount}</td>
                <td>${formatDate(member.createdAt)}</td>
                <td>
                    <a class="button" href="#" data-toggle="modal" data-target="#managemember" onclick="setModalContent(${member.id})">
                        <i class="fas fa-fw fa-cog"></i>
                    </a>
                </td>
            `;
            membersList.appendChild(row);
        });
    }
}

// 관리버튼 클릭시 모달창으로 관리 기능들 표시
function setModalContent(userId) {
    const member = allMembers.find(m => m.id === userId);
    const modalBodyContent = document.getElementById('modal-body-content');
    if (modalBodyContent) {
        modalBodyContent.innerHTML = `
            <button class="btn btn-primary btn-sm" onclick="confirmAction('가입 승인', approveUser, ${member.id})" ${member.status === 'ACTIVE' ? 'disabled' : ''}>가입 승인</button>
            ${member.role === 'USER' ?
            `<button class="btn btn-primary btn-sm" onclick="confirmAction('관리자로 변경', changeUserRole, ${member.id}, 'ADMIN')">관리자로 변경</button>` :
            `<button class="btn btn-success btn-sm" onclick="confirmAction('사용자로 변경', changeUserRole, ${member.id}, 'USER')">사용자로 변경</button>`
        }
            <button class="btn btn-secondary btn-sm" onclick="confirmAction('비밀번호 초기화', resetPassword, ${member.id})">비밀번호 초기화</button>
            <button class="btn btn-danger btn-sm" onclick="confirmAction('유저 추방', deleteUser, ${member.id})">유저 추방</button>
           
            <div class="form-group mt-3">
                <label for="nextCardinal-${member.id}">진행할 다음 기수 입력</label>
                <input type="number" class="form-control" id="nextCardinal-${member.id}" min="1" placeholder="기수 입력">
                <button class="btn btn-info btn-sm mt-2" onclick="confirmAction('다음 기수 진행', submitNextCardinal, ${member.id})">제출</button>
            </div>
            
            <div class="form-group mt-3">
                <label for="penaltyDescription-${member.id}">패널티 입력</label>
                <input type="text" class="form-control" id="penaltyDescription-${member.id}" placeholder="패널티 세부사항 입력">
                <button class="btn btn-warning btn-sm mt-2" onclick="confirmAction('패널티 부여', submitPenalty, ${member.id})">제출</button>
            </div>
        `;
    }
}

/*
사용자 관리 기능 메서드
*/

// 가입승인
function approveUser(userId) {
    return apiRequest(`${apiEndpoint}/admin/users?userId=${userId}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(response => response.json());
}

// 선택된 사용자 모두 가입 승인
function approveSelectedUsers() {
    updateSelectedUserIds();

    if (selectedUserIds.length === 0) {
        alert('선택된 사용자가 없습니다.');
        return;
    }

    let message = '';

    selectedUserIds.forEach(userId => {
        approveUser(userId).then(response => {
            if (response.code === 200) {

            } else {
                alert(`사용자 ${userId} 승인 실패: ${response.message}`);
            }
        }).catch(error => {
            alert(`사용자 ${userId} 승인 중 오류 발생: ${error.message}`);
        });
    });
    alert("승인 완료");
    loadMembers();
}

// 사용자 role 변경
function changeUserRole(userId, role) {
    return apiRequest(`${apiEndpoint}/admin/users/role?userId=${userId}&role=${role}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(response => response.json());
}

// 사용자 밴
function deleteUser(userId) {
    return apiRequest(`${apiEndpoint}/admin/users?userId=${userId}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({userId: userId})
    }).then(response => response.json());
}

// 비밀번호 초기화
function resetPassword(userId) {
    return apiRequest(`${apiEndpoint}/admin/users/reset?userId=${userId}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(response => response.json());
}

// 다음기수 활동
function submitNextCardinal(userId) {
    const cardinalInput = document.getElementById(`nextCardinal-${userId}`);
    const cardinal = cardinalInput.value;

    return apiRequest(`${apiEndpoint}/admin/users/apply?userId=${userId}&cardinal=${cardinal}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(response => response.json());
}

// 패널티 부여
function submitPenalty(userId) {
    const penaltyDescription = document.getElementById(`penaltyDescription-${userId}`).value;

    const data = {
        userId: userId,
        penaltyDescription: penaltyDescription
    };

    return apiRequest(`${apiEndpoint}/admin/penalties`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }).then(response => response.json());
}

// 매 요청시 확인 메시지를 표시하고, 완료하면 다시 멤버를 조회
function confirmAction(actionName, actionFunction, ...args) {
    if (confirm(`${actionName} 하시겠습니까? ${actionName === '비밀번호 초기화' ? '초기화시 비밀번호가 학번으로 초기화됩니다.' : ''}`)) {
        actionFunction(...args)
            .then(response => {
                if(response.code===200) {
                    alert(`${actionName} 성공`);
                    loadMembers();
                }
                else {
                        throw new Error(response.message);
                    }
                })
            .catch(error => {
                alert(`${actionName} 실패: ${error.message}`);
            });
    }
}

function showTokenErrorMessage(message) {
    const tokenErrorMessageDiv = document.getElementById('tokenErrorMessage');
    if (tokenErrorMessageDiv) {
        tokenErrorMessageDiv.style.display = 'block';
        tokenErrorMessageDiv.innerText = message;
    }
}

function formatDate(dateString) {
    const options = {year: 'numeric', month: '2-digit', day: '2-digit'};
    return new Date(dateString).toLocaleDateString('ko-KR', options).replace(/\. /g, '-').replace(/\.$/, '');
}