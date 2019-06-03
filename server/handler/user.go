package handler

import (
	"fmt"
	mylayer "github.com/lsj575/kxtx/server/db"
	"github.com/lsj575/kxtx/server/util"
	"net/http"
	"time"
)

const (
	pwd_salt = "*#890"
)

// 处理用户注册请求
func SignUpHandler(w http.ResponseWriter, r *http.Request) {
	if r.Method == http.MethodPost {
		r.ParseForm()
		username := r.Form.Get("username")
		password := r.Form.Get("password")

		if len(username) < 3 || len(password) < 5 {
			w.Write(util.NewRespMsg(1, "Invalid parameter", nil).JSONBytes())
			return
		}

		enc_pwd := util.Sha1([]byte(password + pwd_salt))
		suc := mylayer.UserSignUp(username, enc_pwd)
		if suc {
			w.Write(util.NewRespMsg(0, "SUCCESS", nil).JSONBytes())
		} else {
			w.Write(util.NewRespMsg(1, "FAILED", nil).JSONBytes())
		}
	}
}

// 登录接口
func SignInHandler(w http.ResponseWriter, r *http.Request) {
	r.ParseForm()
	username := r.Form.Get("username")
	password := r.Form.Get("password")
	enc_pwd := util.Sha1([]byte(password + pwd_salt))

	// 校验用户名密码
	pwdChecked := mylayer.UserSignIn(username, enc_pwd)
	if !pwdChecked {
		w.Write(util.NewRespMsg(1, "FAILED", nil).JSONBytes())
		return
	}

	// 生成访问凭证（token）
	token := GenToken(username)
	updateToken := mylayer.UpdateToken(username, token)
	if !updateToken {
		w.Write(util.NewRespMsg(1, "FAILED", nil).JSONBytes())
		return
	}

	// 登录成功
	resp := util.RespMsg{
		Code: 0,
		Msg:  "OK",
		Data: struct {
			Username string
			Token    string
		}{
			Username: username,
			Token:    token,
		},
	}
	w.Write(resp.JSONBytes())
}

func GenToken(username string) string {
	// md(username + timestamp + token_salt) + timestamp[:8]
	ts := fmt.Sprintf("%x", time.Now().Unix())
	tokenPrefix := util.MD5([]byte(username + ts + "_tokensalt"))
	return tokenPrefix + ts[:8]
}

func ChangeUserAvatarHandler(w http.ResponseWriter, r *http.Request)  {
	username := r.FormValue("username")
	avatar := r.FormValue("avatar")

	changeAvatar := mylayer.ChangeAvatar(username, avatar)
	if !changeAvatar {
		w.Write(util.NewRespMsg(1, "FAILED", nil).JSONBytes())
		return
	}
	w.Write(util.NewRespMsg(0, "SUCCESS", nil).JSONBytes())
}

func ChangeUsernameHandler(w http.ResponseWriter, r *http.Request)  {
	username := r.FormValue("username")
	newName := r.FormValue("new_name")

	changeUsername := mylayer.ChangeUsername(username, newName)
	if !changeUsername {
		w.Write(util.NewRespMsg(1, "FAILED", nil).JSONBytes())
		return
	}
	w.Write(util.NewRespMsg(0, "SUCCESS", nil).JSONBytes())
}

func ChangeUserSignature(w http.ResponseWriter, r *http.Request)  {
	username := r.FormValue("username")
	signature := r.FormValue("signature")

	changeSignature := mylayer.ChangeSignature(username, signature)
	if !changeSignature {
		w.Write(util.NewRespMsg(1, "FAILED", nil).JSONBytes())
		return
	}
	w.Write(util.NewRespMsg(0, "SUCCESS", nil).JSONBytes())
}

func UserInfoHandler(w http.ResponseWriter, r *http.Request)  {
	// 解析请求参数
	r.ParseForm()
	username :=r.Form.Get("username")

	// 查询用户信息
	user, err := mylayer.GetUserInfo(username)
	if err != nil {
		w.WriteHeader(http.StatusForbidden)
		return
	}

	// 组装并响应数据
	resp := util.RespMsg{
		Code: 0,
		Msg:  "OK",
		Data: user,
	}
	w.Write(resp.JSONBytes())
}

func isTokenValid(token string) bool {
	if len(token) != 40 {
		return false
	}
	// TODO: 判断token的时效性，是否过期
	// TODO: 从数据库表tbl_user_token查询username对应的token信息
	// TODO: 对比两个token是否一致
	return true
}
