api register {
  post '/v1/user/register'
  fixtures ^ [ "sign" ]
}

mock register {

  validate {
    usernameRequired true, [ code: 400, msg: "username required" ]
    usernameMinLength 6, [ code: 400, msg: "username too short" ]
    usernameMaxLength 20, [ code: 400, msg: "username too long" ]
    usernameRegex '^[a-zA-Z0-9]+$', [ code: 400, msg: "username format error" ]

    passwordRequired true, [ code: 400, msg: "password required" ]
    passwordMinLength 6, [ code: 400, msg: "password too short" ]
    passwordMaxLength 20, [ code: 400, msg: "password too long" ]

    repasswordRequired true, [ code: 400, msg: "repassword required" ]
  }

  def jsonBody = req.jsonBody()

  def (username, password, rePassword) = [jsonBody["username"], jsonBody["password"], jsonBody["repassword"]]

  if (username == "samchi") {
    return [ code: 400, msg: "username existed" ]
  }

  if (password != rePassword) {
    return [ code: 400, msg: "repassword error" ]
  }

  [code: 200, msg: "success"]

}
