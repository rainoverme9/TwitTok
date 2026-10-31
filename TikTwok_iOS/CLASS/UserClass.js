//IMPORT
//model
import CommunicationController from '../MODEL/CommunicationController';

//Classe che gestisce le operazioni con gli utenti
export default class UserClass {

    //Funzione che restituisce il profilo dell'utente loggato: UID, NAME, PICTURE e PVERSION
    static async getProfile(sid) {
        return await CommunicationController.getProfile(sid)
    }

    //Funzione che restituisce il profilo dell'utente selezionato: UID, NAME, PICTURE e PVERSION
    static async getPicture(sid, uid) {
        return await CommunicationController.getPicture(sid, uid)
    }

    //Funzione che restituisce la lista degli utenti seguiti dell'utente loggato
    static async getAllFollower(sid) {
        result = []
        seguiti = await CommunicationController.getFollowed(sid)
        for (const follower of seguiti) {
            if (JSON.stringify(seguiti) === '{}') {
                return result
            }
            result.push(follower)
        }
        return result
    }

    //Funzione che permette di aggiungere l'utente selezionato alla lista dei seguiti
    static async follow(sid, uid) {
        return await CommunicationController.follow(sid, uid) 
    }

    //Funzione che permette di rimuovere l'utente selezionato dalla lista dei seguiti
    static async unfollow(sid, uid) {
        return await CommunicationController.unfollow(sid, uid)    
    }

    //Funzione che permette di vedere se l'utente selezionato è presente nella lista dei seguiti
    static async isFollowed(sid, uid) {
        return await CommunicationController.isFollowed(sid, uid)
    }

    ////Funzione che permette di modificare il profilo dell'utente loggato
    static async setProfile(sid, name, picture)  {
        return await CommunicationController.setProfile(sid, name, picture)
    }

    //Funzione che permette di registrarsi
    static async register()  {
        return await CommunicationController.register()
    }    
}