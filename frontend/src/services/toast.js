import 'izitoast/dist/css/iziToast.min.css'
import iZtoast from 'izitoast'

const toast = {
  alert:(message) =>{
    return iZtoast.error({
      title: '알림',
      message: message,
      position: 'center'
    });
  },
  error: (data, title = 'Error') => {
    return iZtoast.error({
      title: title,
      message: data.message,
      position: 'bottomCenter'
    });
  },
  success: (method, title = 'Success') => {
    return iZtoast.success({
      title: toast.getMessage(method),
      message: '',
      position: 'bottomCenter',
      backgroundColor: toast.getBackgroundColor(method),
      titleColor: toast.getTitleColor(method),
      displayMode: 1000

    });
  },
  getMessage: (method) => {
    switch (method) {
      case 'get' :
        return '조회 성공'
        break;
      case 'post':
        return '생성 성공'
        break;
      case 'delete':
        return '삭제 성공'
        break;
      case 'patch':
      case 'put':
        return '수정 성공'
        break;
    }
    return '성공'
  },
  getBackgroundColor: (method) => {
    switch (method) {
      case 'delete':
        return 'red'
        break;
      case 'patch':
      case 'put':
        return 'orange'
        break;
    }
    return ''
  },
  getTitleColor: (method) => {
    switch (method) {
      case 'delete':
        return 'white'
        break;
      case 'patch':
      case 'put':
        return 'blue'
        break;
    }
    return ''
  },
  getErrorMessage: (status) => {
    switch (status) {
      case 400:
        return "잘못된 파라미터 입니다."
        break;
      case 404:
        return "잘못된 요청 주소 입니다."
        break;
      case 401:
        return "인증되지 않은 사용자 입니다.";
        break;
      case 403:
        return "접근이 금지되어 있습니다."
        break;
    }
    return "서버 장애 입니다. 관리자에게 문의 하세요."
  }
};

export default toast;
