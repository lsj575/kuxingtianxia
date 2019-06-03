package route

import (
	"github.com/lsj575/kxtx/server/handler"
	"net/http"
)

func FanHandlerFunc()  {
	http.HandleFunc("/fan/add", handler.HTTPInterceptor(handler.AddFanHandler))
	http.HandleFunc("/fan/get", handler.HTTPInterceptor(handler.GetFansHandler))
	http.HandleFunc("/fan/note", handler.HTTPInterceptor(handler.GetFansNote))
}
