api login {
  post '/v1/user/login'
  fixtures ^ [ "sign" ]
}

mock login {

  validate {
    usernameRequired true, [ code: 400, msg: "username required" ]
    usernameRegex '^[a-zA-Z0-9_]{6,20}$', [ code: 400, msg: "username format error" ]
    passwordRequired true, [ code: 400, msg: "password required" ]
    passwordMinLength 6, [ code: 400, msg: "the min length of password is 6" ]
    passwordMaxLength 20, [ code: 400, msg: "the max length of password is 20" ]
  }

  def jsonBody = req.jsonBody()
  def (username, password) = [jsonBody["username"], jsonBody["password"]]
  if (username == "samchi" && password == "123456") {
    [ code: 200, msg: "success", token: "abcdef" ]
  } else {
    [ code: 400, msg: "username and password no match" ]
  }
  
}
