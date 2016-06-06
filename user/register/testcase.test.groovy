testcase register {

  success

  mapBody (
    username: "jackson",
    password: "123456",
    repassword: "123456"
  )

  responseBody(
    code: 200,
    msg: Ignore
  )

}
