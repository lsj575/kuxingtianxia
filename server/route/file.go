package route

import (
	"github.com/lsj575/kxtx/server/handler"
	"net/http"
)

func FileHandlerFunc() {
	http.HandleFunc("/img/upload", handler.HTTPInterceptor(handler.ImgUploadHandler))
}
