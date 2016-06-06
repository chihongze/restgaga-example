globalHeaders (
  Accept: JSON_TYPE,
  "Content-Type": JSON_TYPE
)

env test {
  baseUrl 'http://localhost:8080/'
}

env pro {
  baseUrl 'http://localhost:8081/'
}

// tools functions

import java.security.MessageDigest

def md5(String s) {
  MessageDigest.getInstance("MD5").digest(s.bytes).encodeHex().toString()
}

def String getToken(currentEnv, username, password) {
  def loginUrl

  if (currentEnv == "test") {
    loginUrl = "http://localhost:8080/v1/user/login"
  } else if (currentEnv == "pro") {
    loginUrl = "http://localhost:8081/v1/user/login"
  }

  def loginResult = Unirest.post(loginUrl).headers([
    Accept: JSON_TYPE,
    "Content-Type": JSON_TYPE,
    sign: getSign([username: username, password: password])
  ]).body("{username: '${username}', password: '${password}'}").asJson().body.object

  return loginResult["token"]
}

SECRET_KEY = "123456"

def getSign(args) {
  def sortedArgs = new TreeMap(args)
  def entries = sortedArgs.collect({ k, v ->
    "${-> k}=${-> v}"
  })
  md5("${String.join(';', entries)}$SECRET_KEY")
}


// global fixtures

fixture sign {
  setUp {
    if (body instanceof Map) {
      String sign = getSign(body)
      headers["sign"] = sign
    }
  }
}

fixture token {
  setUp {
    if (! headers["token"]) {
      synchronized(global) {
        if (! headers["token"]) {
          headers["token"] = getToken(currentEnv, "samchi", "123456")
        }
      }
    }
  }
}

globalFixtures << [ "token", "sign" ]

// mock interceptor

mockInterceptor checkToken {

  def path = req.pathInfo()

  // 登录和注册无需检查token
  if (path == "/v1/user/login" || path == "/v1/user/register") {
    return
  }

  def token = req.headers("token")
  if (! token) {
    halt(200, [ code: 400, msg: "invalid token" ])
  }

}

mockInterceptor checkSign {

  def path = req.pathInfo()

  // 上传文件无需检查sign
  if (path == "/v1/user/upload") {
    return
  }

  def sign = req.headers("sign")
  if (! sign) {
    halt(200, [ code: 400, msg: "invalid sign" ])
  }
  
}
