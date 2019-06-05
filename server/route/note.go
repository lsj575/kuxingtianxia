package route

import (
	"github.com/lsj575/kxtx/server/handler"
	"net/http"
)

func NoteHandlerFunc() {
	http.HandleFunc("/note/upload", handler.HTTPInterceptor(handler.NoteUploadHandler))
	http.HandleFunc("/note/get", handler.HTTPInterceptor(handler.GetNotesHandler))
	http.HandleFunc("/note/author", handler.HTTPInterceptor(handler.GetNotesByUsernameHandler))
	http.HandleFunc("/note/edit", handler.HTTPInterceptor(handler.NoteEditHandler))
	http.HandleFunc("/note/delete", handler.HTTPInterceptor(handler.NoteDeleteHandler))
	http.HandleFunc("/note/number", handler.HTTPInterceptor(handler.GetUserNoteNumberHandler))
}
