package handler

import (
	mylayer "github.com/lsj575/kxtx/server/db"
	"github.com/lsj575/kxtx/server/util"
	"net/http"
	"strconv"
)


func NoteUploadHandler(w http.ResponseWriter, r *http.Request)  {
	// 解析用户请求参数
	username := r.FormValue("username")
	title := r.FormValue("title")
	content := r.FormValue("content")
	img := r.FormValue("img")
	latitude := r.FormValue("latitude")
	longitude := r.FormValue("longitude")
	location := r.FormValue("location")
	isOpen := r.FormValue("isOpen")
	isOpenNum, _ := strconv.Atoi(isOpen)

	// 插入note表
	upload := mylayer.NoteUpload(username, title, content, img, latitude, longitude, location, isOpenNum)
	if !upload {
		w.Write(util.NewRespMsg(1, "FAILED", nil).JSONBytes())
		return
	}
	// 返回信息
	w.Write(util.NewRespMsg(0, "SUCCESS", nil).JSONBytes())
}

func GetNotesHandler(w http.ResponseWriter, r *http.Request)  {
	// 解析请求参数
	username :=r.FormValue("username")

	// 查询记事信息
	notes, err := mylayer.GetNote(username)
	if err != nil {
		w.WriteHeader(http.StatusForbidden)
		return
	}

	// 组装并响应数据
	resp := util.RespMsg{
		Code: 0,
		Msg:  "OK",
		Data: notes,
	}
	w.Write(resp.JSONBytes())
}

func GetNotesByUsernameHandler(w http.ResponseWriter, r *http.Request)  {
	// 解析请求参数
	username :=r.FormValue("author")

	// 查询记事信息
	notes, err := mylayer.GetNoteByUsername(username)
	if err != nil {
		w.WriteHeader(http.StatusForbidden)
		return
	}

	// 组装并响应数据
	resp := util.RespMsg{
		Code: 0,
		Msg:  "OK",
		Data: notes,
	}
	w.Write(resp.JSONBytes())
}

func NoteEditHandler(w http.ResponseWriter, r *http.Request) {
	// 解析用户参数
	if r.Method == http.MethodPost {
		id := r.FormValue("id")
		username := r.FormValue("username")
		title := r.FormValue("title")
		content := r.FormValue("content")
		img := r.FormValue("img")
		isOpen := r.FormValue("isOpen")

		// 更新note表
		edit := mylayer.NoteEdit(id, title, username, content, img, isOpen)
		if !edit {
			w.Write(util.NewRespMsg(1, "FAILED", nil).JSONBytes())
			return
		}

		w.Write(util.NewRespMsg(1, "SUCCESS", nil).JSONBytes())
	}
}

func NoteDeleteHandler(w http.ResponseWriter, r *http.Request)  {
	if r.Method == http.MethodPost {
		id := r.FormValue("id")
		username := r.FormValue("username")

		noteDelete := mylayer.NoteDelete(id, username)
		if !noteDelete {
			w.Write(util.NewRespMsg(1, "FAILED", nil).JSONBytes())
			return
		}

		w.Write(util.NewRespMsg(1, "SUCCESS", nil).JSONBytes())
	}
}

func GetUserNoteNumberHandler(w http.ResponseWriter, r *http.Request) {
	username := r.FormValue("username")

	number, err := mylayer.GetUserNoteNumber(username)
	if err != nil {
		w.Write(util.NewRespMsg(1, "FAILED", nil).JSONBytes())
		return
	}
	w.Write(util.NewRespMsg(1, "SUCCESS", struct {
		Number int64
	}{number}).JSONBytes())
}
