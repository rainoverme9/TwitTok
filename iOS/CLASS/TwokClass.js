//IMPORT
//model
import CommunicationController from '../MODEL/CommunicationController';
//location
import * as Location from 'expo-location';

//Classe che gestisce le operazioni con i twok
export default class TwokClass {

    //Funzione che restituisce il buffer iniziale costituito da 5 twok in ordina casuale
    static async downloadInitialBufferTwoks(sid, uid) {
        result = []
        for (let i = 0; i < 5; i++) {
            newTwok = await CommunicationController.getTwok(sid, uid, null)
            result.push(newTwok)
        }
        return result
    }

    //Funzione che restituisce il buffer iniziale costituito da 5 twok da un determinato tid in poi
    static async downloadInitialBufferSpecificTwoks(sid, tid) {
        result = []
        let newTid = tid;
        for (let i = 0; i < 5; i++) {
            newTwok = await CommunicationController.getTwok(sid, null, newTid)
            if (JSON.stringify(newTwok) === 'no twok found with given params') {
                return result
            }
            result.push(newTwok)
            newTid += 1;
        }
        return result
    }

    //Funzione che restituisce un twok
    static async dowloadOneTwok(sid, uid, tid) {
        newTwok = await CommunicationController.getTwok(sid, uid, tid)
        return newTwok

    }

    //Funzione che aggiunge un nuovo twok al server
    static async addTwok(twok) {
        return await CommunicationController.addTwok(twok)
    }

    //Funzione che ottiene e restituisce i permessi di geolocalizzazione
    static async locationPermissionAsync() {
        let canUseLocation = false;
        const grantedPermission = await Location.getForegroundPermissionsAsync()
        if (grantedPermission.status === "granted") {
            canUseLocation = true;
        } else {
            const permissionResponse = await Location.requestForegroundPermissionsAsync()
            if (permissionResponse.status === "granted") {
                canUseLocation = true;
            }
        }
        if (canUseLocation) {
            const location = await Location.getCurrentPositionAsync()
            return location
        } else {
            return "No Permission"
        }
    }

    //Funzione che ottiene i permessi ottenuti
    static async getPermissionDeclared() {
        let canUseLocation = false;
        const grantedPermission = await Location.getForegroundPermissionsAsync()
        if (grantedPermission.status === "granted") {
            canUseLocation = true;
        }
        return canUseLocation
    }

    //Funzione che ottiene il Reverse-Geocoding
    static async getNameAddress(lat, lon) {
        const address = await Location.reverseGeocodeAsync(lat, lon)
        return address
    }
}