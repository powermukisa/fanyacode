package fanyacodeapi.authz

default allow := false

allow  {
  input.method == "PUT"
#  input.path = ["api", "authorities"]
  input.path == "/api/authorities"
  token.payload.role == 2
  user_owns_token
}

allow {
  input.method == "GET"
  user_owns_token
}

allow {
  input.method == "POST"
  user_owns_token
}

# Ensure that the token was issued to the user supplying it.
user_owns_token {
#input.user == token.payload.azp
true # hardcode to true for now
}

# Helper to get the token payload.
token := {"payload": payload} {
    [header, payload, signature] := io.jwt.decode(input.token)
}
