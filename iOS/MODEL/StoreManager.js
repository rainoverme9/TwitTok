//IMPORT SQLLITE
import * as SQLite from "expo-sqlite";

//Classe che memorizza UID, PVERSION e PICTURE degli utente presenti nel server
export default class StoreManager {

    //Creazione del DataBase
    constructor() {
        this.db = SQLite.openDatabase("dataBase")
    }
    
    //Creazione della tabella con UID (primary key), PVERSION e PICTURE
    initDB() {
        const query = `
            CREATE TABLE IF NOT EXISTS usersProfileInfo (
                uid integer  NOT NULL PRIMARY KEY,
                pversion integer NOT NULL DEFAULT 0,
                picture TEXT
            );
        `;
        this.db.transaction(tx => {
            tx.executeSql(query,
                (tx, result) => console.log("Creazione database avvenuta con successo"),
                (tx, error) => console.log("Errore nella creazione del Database"))
        });
    }

    //Eliminazione Tabella
    clearDB() {
        const transaction = (tx) => { 
            let query = "DELETE FROM usersProfileInfo"; 
            tx.executeSql( query,
                //gestione risultato della query
                (tx, queryResult) => {  
                    console.log("Eliminazione della tabella avvenuta con successo")
                },
                //gestione errore query
                (tx, error) => {        
                    console.log("Si è verificato un errore durante l'eliminazione della tabella")
                }
            );
        }
        //GESTIONE ERRORE TRANSIZIONE
        const error = (e) => {console.log("Si è verificato un errore durante l'esecuzione della query per l'eliminazione della tabella") };
        this.db.transaction(transaction, error);
    }

    //Ottiene la foto dell'utente
    getUserPicture(uid, onResult, onError) {
        //GESTIONE TRANSIZIONE
        const transaction = (tx) => { 
            let query = "SELECT picture from usersProfileInfo where uid = ?;";
            tx.executeSql( query, [uid],
                //gestione risultato della query
                (tx, queryResult) => {  
                    if (queryResult.rows.length > 0) {
                        onResult(queryResult.rows._array[0].picture)
                    } else {
                        onResult(null)
                    }
                },
                //gestione errore query
                (tx, error) => {     
                    onError(null)   
                    console.log("Si è verificato un errore durante il recupero della foto con uid " + uid)
                }
            );
        }
        //GESTIONE ERRORE TRANSIZIONE
        const error = (e) => { onError(null), console.log("Si è verificato un errore durante l'esecuzione della query per il recupero della foto") };
        this.db.transaction(transaction, error);
    }

    //Ottiene la pversion della foto dell'utente
    getUserPversion(uid, onResult, onError) {
        //GESTIONE TRANSIZIONE
        const transaction = (tx) => { 
            let query = "SELECT pversion from usersProfileInfo where uid = ?;"; 
            tx.executeSql( query, [uid],
                //gestione risultato della query
                (tx, queryResult) => {  
                    if (queryResult.rows.length > 0) {
                        onResult(queryResult.rows._array[0].pversion)
                    } else {
                        onResult(null)
                    }
                },
                //gestione errore query
                (tx, error) => {        
                    onError(null)   
                    console.log("Si è verificato un errore durante il recupero della versione della foto con uid " + uid)
                }
            );
        }
        //GESTIONE ERRORE TRANSIZIONE
        const error = (e) => { onError(null), console.log("Si è verificato un errore durante l'esecuzione della query per il recupero della pversion") };
        this.db.transaction(transaction, error);
    }

    //Salva la foto dell'utente
    storeUserPicture(uid, pversion, picture) {
        //GESTIONE TRANSIZIONE
        const transaction = (tx) => {
            let query = "INSERT OR REPLACE INTO usersProfileInfo (uid, pversion, picture) VALUES( ?, ?, ?);";
            tx.executeSql( query, [uid, pversion, picture],
                //gestione risultato della query
                (tx, queryResult) => {  
                    console.log("La foto dell'utente con uid: " + uid +" è stata aggiornata correttamente")
                },
                //gestione errore query
                (tx, error) => {        
                    console.log("Errore durante l'aggiornamento della foto dell'utente con uid: " + uid)
                }
            );
        }
        //GESTIONE ERRORE TRANSIZIONE
        const error = (e) => { console.log("Si è verificato un errore durante l'esecuzione della query per il salvataggio delle informazioni dell'utente con uid: "+ uid) };
        this.db.transaction(transaction, error);
    }
}