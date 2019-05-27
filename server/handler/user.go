package handler

import (
	"fmt"
	mylayer "github.com/lsj575/filestore-server/db"
	"github.com/lsj575/filestore-server/util"
	"io/ioutil"
	"net/http"
	"time"
)

const (
	pwd_salt = "*#890"
)

// 处理用户注册请求
func SignUpHandler(w http.ResponseWriter, r *http.Request) {
	if r.Method == http.MethodGet {
		data, err := ioutil.ReadFile("./static/view/signup.html")
		if err != nil {
			w.WriteHeader(http.StatusInternalServerError)
			return
		}

		w.Write(data)
		return
	} else {
		r.ParseForm()
		username := r.Form.Get("username")
		password := r.Form.Get("password")

		if len(username) < 3 || len(password) < 5 {
			w.Write([]byte("Invalid parameter"))
			return
		}

		enc_pwd := util.Sha1([]byte(password + pwd_salt))
		suc := mylayer.UserSignUp(username, enc_pwd)
		if suc {
			w.Write([]byte("SUCCESS"))
		} else {
			w.Write([]byte("FAIL"))
		}
	}
}

// 登录接口
func SignInHandler(w http.ResponseWriter, r *http.Request) {
	if r.Method == http.MethodGet {
		data, err := ioutil.ReadFile("./static/view/signin.html")
		if err != nil {
			w.WriteHeader(http.StatusInternalServerError)
			return
		}
		w.Write(data)
	}
	r.ParseForm()
	username := r.Form.Get("username")
	password := r.Form.Get("password")
	enc_pwd := util.Sha1([]byte(password + pwd_salt))

	// 校验用户名密码
	pwdChecked := mylayer.UserSignIn(username, enc_pwd)
	if !pwdChecked {
		w.Write([]byte("FAILED"))
		return
	}

	// 生成访问凭证（token）
	token := GenToken(username)
	updateToken := mylayer.UpdateToken(username, token)
	if !updateToken {
		w.Write([]byte("FAILED"))
		return
	}

	// 登录成功后重定向到首页
	resp := util.RespMsg{
		Code: 0,
		Msg:  "OK",
		Data: struct {
			Location string
			Username string
			Token    string
		}{
			Location: "http://" + r.Host + "/static/view/home.html",
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

func UserInfoHandler(w http.ResponseWriter, r *http.Request)  {
	// 解析请求参数
	r.ParseForm()
	username :=r.Form.Get("username")
	//token := r.Form.Get("token")
	// 验证token是否有效
	//isValidToken := isTokenValid(token)
	//if !isValidToken {
	//	w.WriteHeader(http.StatusForbidden)
	//	return
	//}
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
