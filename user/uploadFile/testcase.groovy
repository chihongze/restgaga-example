testcase uploadFile {

  success

  fileBody "resource/testfile.txt"

  responseBody (
    code: 200
  )

}

testcase uploadFile {

  successWithStrBody

  stringBody "testfile"

  responseBody (
    code: 200
  )

}
