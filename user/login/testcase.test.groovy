testcase login {

  success

  mapBody(
    username: "samchi",
    password: "123456"
  )

  responseBody(
    code: 200,
    msg: Ignore
  )

}


testcase login {

  passwordErr

  mapBody(
    username: "samchi",
    password: "654321"
  )

  responseBody(
    code: 400,
    msg: predict { it.contains("no match") }
  )

}
