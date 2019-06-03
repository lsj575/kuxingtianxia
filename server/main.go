package main

import (
	"fmt"
	"github.com/lsj575/kxtx/server/route"
	"net/http"
)

func main() {
	http.Handle("/static/", http.StripPrefix("/static/", http.FileServer(http.Dir("./static"))))
	route.UserHandlerFunc()
	route.FileHandlerFunc()
	route.NoteHandlerFunc()
	route.FanHandlerFunc()

	err := http.ListenAndServe(":8080", nil)
	if err != nil {
		fmt.Printf("Failed to start server, err: %s", err.Error())
	}
}
