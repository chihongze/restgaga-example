api profile {
  get '/v1/user/profile/{userId}'
}

mock profile {
  if (req.headers("token") != "abcdef") {
    return [ code: 400, msg: "invalid token" ]
  }
  def userId = Integer.parseInt(req.params("userId"))
  if (userId > 0 && userId < 10) {
    [ code: 200, profile: [id: userId, username: "SamChi", grade: 10] ]
  } else {
    [ code: 404, msg: "not found" ]
  }
}
