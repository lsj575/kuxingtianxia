package handler

import (
	"github.com/lsj575/kxtx/server/util"
	"net/http"
)

// http请求拦截器
func HTTPInterceptor(h http.HandlerFunc) http.HandlerFunc {
	return http.HandlerFunc(
		func(w http.ResponseWriter, r *http.Request) {
			username := r.FormValue("username")
			token := r.FormValue("token")

			if len(username) < 3 || !isTokenValid(token) {
				w.Write(util.NewRespMsg(1, "Failed to get data", nil).JSONBytes())
				return
			}
			h(w, r)
		})
}
