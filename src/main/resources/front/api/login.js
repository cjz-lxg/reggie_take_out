function loginApi(data) {
    return $axios({
      'url': '/user/login',
      'method': 'post',
      data
    })
  }

function loginoutApi() {
  return $axios({
    'url': '/user/loginout',
    'method': 'post',
  })
}

function sendMesApi(data){
    return $axios({
        'url': '/user/sendMsg',
        'method': 'post',
        data
    })
}

  