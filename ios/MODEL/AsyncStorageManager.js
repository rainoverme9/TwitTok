// IMPORT
import AsyncStorage from '@react-native-async-storage/async-storage';
import StoreClass from '../CLASS/StoreClass';

// Classi
import UserClass from '../CLASS/UserClass';

//Classe che gestisce l'AsyncStorage
export default class AsyncStorageManager {

    //Funzione che salva e restituisce SID e UID
    static async asyncStoreSid() {
        //Controllo se ho già salvato il sid nell'AsyncStorage
        const sidStored = await AsyncStorage.getItem("sid")
        var user = {
            sid: "",
            uid: "",
        }
        //Se esiste --> Setto i valori di user a quelli già salvati. Restituisco user
        if (sidStored) {
            return user = {sid: await AsyncStorage.getItem("sid"), uid: await AsyncStorage.getItem("uid")}

            //Se non esiste
        } else {
            //Creo il dataBase
            StoreClass.initDB()

            //Registro l'utente
            console.log("Registrazione utente");
            await UserClass.register()
                .then(async resultSid => {

                    await UserClass.getProfile(resultSid.sid)
                        .then( resultUid => {
                            //Setto il sid nell'AsyncStorage
                            AsyncStorage.setItem("sid", resultSid.sid)
                            console.log("Sid: ", resultSid.sid)

                            //Setto uid nell'AsyncStorage
                            AsyncStorage.setItem("uid", JSON.stringify(resultUid.uid))
                            console.log("Uid: ", resultUid.uid)

                            //Setto i valori di user a quelli appena ottenuti. Restituisco user
                            user.sid = resultSid.sid
                            user.uid = resultUid.uid
                        })
                        .catch(error => console.log("Errore durante la registrazione dell'uid dell'utente: ", error))
                })
                .catch(error => console.log("Errore durante la registrazione del sid dell'utente: ", error))
            return user
        }
    }

    // Funzione che elimina il SID dall'AsyncStorage
    static async clearSID() {
        await AsyncStorage.removeItem("sid")
    }
    // Funzione che elimina l'UID dall'AsyncStorage
    static async clearUID() {
        await AsyncStorage.removeItem("uid")
    }

    // Funzione che ottiene il SID dall'AsyncStorage
    static async getSID() {
        return await AsyncStorage.getItem("sid")
    }
    // Funzione che ottiene l'UID dall'AsyncStorage
    static async getUID() {
        return await AsyncStorage.getItem("uid")
    }

    // Funzione che ottiene il SID e l'UID dall'AsyncStorage
    static async getInfo() {
        const user = await AsyncStorageManager.asyncStoreSid()
        return user = {sid: user.sid, uid: user.uid}
    }
}