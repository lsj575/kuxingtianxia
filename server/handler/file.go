package handler

import (
	"github.com/lsj575/kxtx/server/util"
	"io"
	"net/http"
	"os"
)

// 处理图片上传
func ImgUploadHandler(w http.ResponseWriter, r *http.Request) {
	if r.Method == http.MethodPost {
		// 接收文件流及存储到本地目录
		file, header, err := r.FormFile("file")
		if err != nil {
			w.Write(util.NewRespMsg(1, "Failed to get data", nil).JSONBytes())
			return
		}
		defer file.Close()

		// 本地创建文件
		fPath := "./static/img/" + header.Filename
		newFile, err := os.Create(fPath)
		if err != nil {
			w.Write(util.NewRespMsg(1, "Failed to create file", nil).JSONBytes())
			return
		}
		defer newFile.Close()

		// 将上传的文件复制进去
		_, err = io.Copy(newFile, file)
		if err != nil {
			w.Write(util.NewRespMsg(1, "Failed to save data to file", nil).JSONBytes())
			return
		}

		w.Write(util.NewRespMsg(0, "Upload Finished", struct {
			Location string
		}{
			Location: fPath[1:],
		}).JSONBytes())
	}
}