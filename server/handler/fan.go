package handler

import (
	mylayer "github.com/lsj575/kxtx/server/db"
	"github.com/lsj575/kxtx/server/util"
	"net/http"
)

func AddFanHandler(w http.ResponseWriter, r *http.Request)  {
	attentionUsername := r.FormValue("attention_username")
	beAttentionUsername := r.FormValue("be_attention_username")

	fan := mylayer.AddFan(attentionUsername, beAttentionUsername)
	if !fan {
		w.Write(util.NewRespMsg(1, "FAILED", nil).JSONBytes())
		return
	}
	// 返回信息
	w.Write(util.NewRespMsg(0, "SUCCESS", nil).JSONBytes())
}

func GetFansHandler(w http.ResponseWriter, r *http.Request)  {
	// 解析请求参数
	username :=r.FormValue("username")

	// 查询记事信息
	fans, err := mylayer.GetFans(username)
	if err != nil {
		w.WriteHeader(http.StatusForbidden)
		return
	}

	// 组装并响应数据
	resp := util.RespMsg{
		Code: 0,
		Msg:  "OK",
		Data: fans,
	}
	w.Write(resp.JSONBytes())
}

func GetFansNote(w http.ResponseWriter, r *http.Request)  {
	//username := r.FormValue("username")

	//notes, err := mylayer.GetFansNote(username)
}
