package db

import (
	"fmt"
	mydb "github.com/lsj575/kxtx/server/db/mysql"
	"time"
)

func AddFan(attentionUsername string, beAttentionUserName string) bool {
	stmt, err := mydb.DBConn().Prepare(
		"INSERT INTO fan (`attention_username`, `be_attention_username`, `create_time`) " +
			"values (?, ?, ?)")
	if err != nil {
		fmt.Println("Failed to insert, err: ", err)
		return false
	}
	defer stmt.Close()

	result, err := stmt.Exec(attentionUsername, beAttentionUserName, time.Now().Unix())
	if err != nil {
		fmt.Println("Failed to insert, err: ", err)
		return false
	}

	if rowsAffected, err := result.RowsAffected(); err == nil && rowsAffected > 0 {
		return true
	}

	return false
}

func GetFans(username string) ([]User, error) {
	var users []User

	stmt, err := mydb.DBConn().Prepare(
		"SELECT user.id, fan.be_attention_username, avatar, signature, user.create_time, last_login_time FROM user " +
			"INNER JOIN fan ON fan.be_attention_username = user.username WHERE attention_username = ? && status = 1")
	if err != nil {
		fmt.Println(err.Error())
		return users, err
	}
	defer stmt.Close()

	rows, err := stmt.Query(username)
	if err != nil {
		return nil, err
	}

	for rows.Next() {
		user := User{}
		err = rows.Scan(&user.Id, &user.Username, &user.Avatar, &user.Signature, &user.SignUpAt, &user.LastActiveAt)
		if err != nil {
			fmt.Println(err.Error())
			break
		}
		users = append(users, user)
	}
	return users, nil
}

func GetFansNote(username string) ([]Note, error) {
	var notes []Note

	stmt, err := mydb.DBConn().Prepare(
		"SELECT id, username, content, img, latitude, longitude, location, create_time, update_time" +
			"FROM note AS n " +
			"INNER JOIN fan AS f ON f.be_attention_username = n.username WHERE f.attention_username = ? && n.status = 1 && n.isOpen = 1")
	if err != nil {
		fmt.Println(err.Error())
		return notes, err
	}
	defer stmt.Close()

	rows, err := stmt.Query(username)
	if err != nil {
		return nil, err
	}

	for rows.Next() {
		note := Note{}
		err = rows.Scan(&note.Id, &note.Content, &note.Img, &note.Latitude, &note.Longitude, &note.Location,
			&note.CreateTime, &note.UpdateTime)
		if err != nil {
			fmt.Println(err.Error())
			break
		}
		notes = append(notes, note)
	}
	return notes, nil
}