package db

import (
	"fmt"
	mydb "github.com/lsj575/kxtx/server/dbrver/db/mysql"
)

// 通过用户名和密码完成用户注册操作
func UserSignUp(username string, password string) bool {
	stmt, err := mydb.DBConn().Prepare(
		"INSERT ignore INTO tbl_user (`user_name`, `user_pwd`) values (?, ?)")
	if err != nil {
		fmt.Println("Failed to insert, err: ", err)
		return false
	}
	defer stmt.Close()

	result, err := stmt.Exec(username, password)
	if err != nil {
		fmt.Println("Failed to insert, err: ", err)
		return false
	}

	if rowsAffected, err := result.RowsAffected(); err == nil && rowsAffected > 0 {
		return true
	}

	return false
}

// 判断密码是否一致
func UserSignIn(username string, password string) bool {
	stmt, err := mydb.DBConn().Prepare(
		"SELECT * FROM tbl_user WHERE user_name = ? LIMIT 1")
	if err != nil {
		fmt.Println("Failed to find, err: ", err)
		return false
	}
	defer stmt.Close()
	
	rows, err := stmt.Query(username)
	if err != nil {
		fmt.Println(err.Error())
		return false
	} else if rows == nil {
		fmt.Println("Username not found: " + username)
		return false
	}

	parseRows := mydb.ParseRows(rows)
	if len(parseRows) > 0 && string(parseRows[0]["user_pwd"].([]byte)) == password {
		return true
	}

	return false
}

// 更新用户登录的token
func UpdateToken(username string, token string) bool {
	stmt, err := mydb.DBConn().Prepare(
		"REPLACE INTO tbl_user_token (`user_name`, `user_token`) values (?, ?)")
	if err != nil {
		fmt.Println(err.Error())
		return false
	}
	defer stmt.Close()

	_, err = stmt.Exec(username, token)
	if err != nil {
		fmt.Println(err.Error())
		return false
	}
	return true
}

type User struct {
	Username string
	Email string
	Phone string
	SignupAt string
	LastActiveAt string
	Status int
}

func GetUserInfo(username string) (User, error) {
	user := User{}

	stmt, err := mydb.DBConn().Prepare(
		"SELECT user_name, signup_at FROM tbl_user WHERE user_name = ? LIMIT 1")
	if err != nil {
		fmt.Println(err.Error())
		return user, err
	}
	defer stmt.Close()

	err = stmt.QueryRow(username).Scan(&user.Username, &user.SignupAt)
	if err != nil {
		return user, err
	}
	return user, nil
}