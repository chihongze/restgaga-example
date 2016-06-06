api uploadFile {
  put '/v1/user/upload'
  fixtures ^ [ "token" ]
  headers (
    "Content-Type": "text/plain"
  )
}

mock uploadFile {
  def body = req.body()
  if (body == "testfile") {
    [ code: 200, msg: "upload success" ]
  } else {
    [ code: 400, msg: "invalid file" ]
  }
}
