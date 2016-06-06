testcase profile {

  success

  pathVariables (
    userId: 1
  )

  responseBody (
    code: 200,
    profile: [
      id: 1,
      username: "SamChi"
    ]
  )

}

testcase profile {

  userNotExist

  pathVariables (
    userId: 0
  )

  responseBody (
    code: 404,
    msg: Ignore
  )
}
