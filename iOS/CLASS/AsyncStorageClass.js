//IMPORT
//model
import AsyncStorageManager from '../MODEL/AsyncStorageManager'

//Classe che gestisce l'AsyncStorage
export default class AsyncStorageClass {

    //Funzione che salva e restituisce SID e UID
    static async asyncStoreSid() {
        return await AsyncStorageManager.asyncStoreSid()
    }

    // Funzione che ottiene il SID e l'UID dall'AsyncStorage
    static async getInfo() {
        return await AsyncStorageManager.asyncStoreSid()
    }
}