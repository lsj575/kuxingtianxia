package route

import (
	"github.com/lsj575/kxtx/server/handler"
	"net/http"
)

func UserHandlerFunc() {
	http.HandleFunc("/user/signup", handler.SignUpHandler)
	http.HandleFunc("/user/signin", handler.SignInHandler)
	http.HandleFunc("/user/info", handler.HTTPInterceptor(handler.UserInfoHandler))
}