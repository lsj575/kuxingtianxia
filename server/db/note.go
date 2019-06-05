package db

import (
	"fmt"
	mydb "github.com/lsj575/kxtx/server/db/mysql"
	"strconv"
	"time"
)

func NoteUpload(username string, title string, content string, img string, latitude string, longitude string,
	location string, isOpen int) bool {
	stmt, err := mydb.DBConn().Prepare(
		"INSERT INTO note " +
			"(`username`, `title`, `content`, `img`, `latitude`, `longitude`, `location`, `create_time`, `update_time`, `isOpen`) " +
			"values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")
	if err != nil {
		fmt.Println("Failed to insert, err: ", err)
		return false
	}
	defer stmt.Close()

	result, err := stmt.Exec(
		username, title, content, img, latitude, longitude, location, time.Now().Unix(), time.Now().Unix(), isOpen)
	if err != nil {
		fmt.Println("Failed to insert, err: ", err)
		return false
	}

	if rowsAffected, err := result.RowsAffected(); err == nil && rowsAffected > 0 {
		return true
	}

	return false
}


type Note struct {
	Id int64
	Username string
	Title string
	Content string
	Img string
	Latitude string
	Longitude string
	Location string
	CreateTime string
	UpdateTime string
	IsOpen string
}

func GetNote(username string) ([]Note, error) {
	var notes []Note

	stmt, err := mydb.DBConn().Prepare(
		"SELECT id, username, title, content, img, latitude, longitude, location, create_time, update_time, isOpen " +
			"FROM note WHERE username = ? && status = 1")
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
		err = rows.Scan(&note.Id, &note.Username, &note.Title, &note.Content, &note.Img, &note.Latitude, &note.Longitude, &note.Location,
			&note.CreateTime, &note.UpdateTime, &note.IsOpen)
		if err != nil {
			fmt.Println(err.Error())
			break
		}
		notes = append(notes, note)
	}
	return notes, nil
}

func GetNoteByUsername(username string) ([]Note, error) {
	var notes []Note

	stmt, err := mydb.DBConn().Prepare(
		"SELECT id, username, title, content, img, latitude, longitude, location, create_time, update_time, isOpen " +
			"FROM note WHERE username = ? && status = 1 && isOpen = 1")
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
		err = rows.Scan(&note.Id, &note.Username, &note.Title, &note.Content, &note.Img, &note.Latitude, &note.Longitude, &note.Location,
			&note.CreateTime, &note.UpdateTime, &note.IsOpen)
		if err != nil {
			fmt.Println(err.Error())
			break
		}
		notes = append(notes, note)
	}
	return notes, nil
}

func NoteEdit(id string, title string, username string, content string, img string, isOpen string) bool {
	stmt, err := mydb.DBConn().Prepare(
		"SELECT status FROM note WHERE username = ? && id = ?")
	if err != nil {
		fmt.Println(err.Error())
		return false
	}
	defer stmt.Close()

	var status string
	err = stmt.QueryRow(username, id).Scan(&status)
	statusInt, _ := strconv.Atoi(status)
	if err != nil || statusInt != 1 {
		return false
	}

	stmt, err = mydb.DBConn().Prepare(
		"UPDATE note SET title = ?, content = ?, img = ?, isOpen = ? WHERE id = ?")
	if err != nil {
		fmt.Println(err.Error())
		return false
	}

	_, err = stmt.Exec(title, content, img, isOpen, id)
	if err != nil {
		fmt.Println(err.Error())
		return false
	}
	return true
}

func NoteDelete(id string, username string) bool {
	stmt, err := mydb.DBConn().Prepare(
		"SELECT status FROM note WHERE username = ? && id = ?")
	if err != nil {
		fmt.Println(err.Error())
		return false
	}
	defer stmt.Close()

	var status string
	err = stmt.QueryRow(username, id).Scan(&status)
	statusInt, _ := strconv.Atoi(status)
	if err != nil || statusInt != 1 {
		return false
	}

	stmt, err = mydb.DBConn().Prepare(
		"UPDATE note SET status = -1 WHERE id = ?")
	if err != nil {
		fmt.Println(err.Error())
		return false
	}

	_, err = stmt.Exec(id)
	if err != nil {
		fmt.Println(err.Error())
		return false
	}
	return true
}

func GetUserNoteNumber(username string) (int64, error) {
	stmt, err := mydb.DBConn().Prepare(
		"SELECT count(*) FROM note WHERE username = ? && status = 1")
	if err != nil {
		fmt.Println(err.Error())
		return 0, err
	}
	defer stmt.Close()

	var num int64
	err = stmt.QueryRow(username).Scan(&num)
	if err != nil {
		return 0, err
	}
	return num, err
}