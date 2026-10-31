//IMPORT REACT UTILITIES
import StoreManager from '../MODEL/StoreManager';
//class
import UserClass from '../CLASS/UserClass';

// Generazione del Database
const dataBase = new StoreManager();

//Classe che gestisce il DataBase
export default class StoreClass {

    //Funzione che salva UID, PVERSION e PICTURE nel database --> Solo se non sono state inserite o sono state aggiornate
    static uploadStoreUserPicture(sid, uid, pversion, setPicture, setPversion) {

        if (pversion == null) {
            dataBase.getUserPicture(uid, result => {setPicture(result)}, error => alert("Errore durante il download della foto profilo"))
            dataBase.getUserPversion(uid, result => {setPversion(result)}, error => alert("Errore durante il download della foto profilo"))
        } else {
            if (pversion > 0) {
                dataBase.getUserPversion(uid,
                    pversionDB => {
                        //Se non sono mai state salvate le informazioni dell'utente o se la versione dell'immagine differisce da quella già salvata     --> Le salvo
                        if (pversion > pversionDB || pversionDB == null) {
                            UserClass.getPicture(sid, uid)
                                .then(richiestaRete => {
                                    dataBase.storeUserPicture(uid, richiestaRete.pversion, richiestaRete.picture)
                                    setPicture(richiestaRete.picture)
                                    setPversion(pversionDB)
                                })
                                .catch(error => alert("Errore durante il download della foto profilo"), console.log("Errore StoreClass ", error))
                        } else {
                            dataBase.getUserPicture(uid, result => { setPicture(result) }, error => setPicture(error))
                        }
                    },
                    error => alert("Errore durante il recupero della foto profilo")
                )
            }
        }
    }

    static storeUserPicture(uid, pversion, picture) {
        dataBase.storeUserPicture(uid, pversion, picture)
    }

    static initDB() {
        dataBase.initDB()
    }

    static clearDB() {
        dataBase.clearDB()
    }
}