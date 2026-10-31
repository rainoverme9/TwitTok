export default class CommunicationController {
    static BASE_URL = "https://develop.ewlab.di.unimi.it/mc/twittok/"

    static async genericRequest(endpoint, parameters) {
        const url = this.BASE_URL + endpoint;
        let httpResponse = await fetch(url, {
            method: 'POST',
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(parameters)
        });
        const status = httpResponse.status;
        
        if (status == 200) {
            let deserializedObject = await httpResponse.json();
            return deserializedObject;
        }
        else {
            let deserializedObject = await httpResponse.text();
            let error = new Error(deserializedObject);
            throw error;
        }
    }

    static async register() {
        const parameters = {};
        return await CommunicationController.genericRequest("register", parameters)
    }
    
    static async getProfile(sid) {
        const parameters = { sid: sid };
        return await CommunicationController.genericRequest("getProfile", parameters)
    }

    static async getTwok(sid, uid, tid) {
        const parameters = { sid: sid, uid: uid, tid: tid };
        return await CommunicationController.genericRequest("getTwok", parameters)
    }

    static async addTwok(twok) {
        const parameters = {
            sid: twok.sid,
            text: twok.text,
            bgcol: twok.bgcol,
            fontcol: twok.fontcol,
            fontsize: twok.fontsize,
            fonttype: twok.fonttype,
            halign: twok.halign,
            valign: twok.valign,
            lat: twok.lat,
            lon: twok.lon
        };
        return await CommunicationController.genericRequest("addTwok", parameters)
    }

    static async getPicture(sid, uid) {
        const parameters = { sid: sid, uid: uid };
        return await CommunicationController.genericRequest("getPicture", parameters)
    }

    static async getFollowed(sid) {
        const parameters = { sid: sid };
        return await CommunicationController.genericRequest("getFollowed", parameters)
    }

    static async follow(sid, uid) {
        const parameters = { sid: sid, uid: uid };
        return await CommunicationController.genericRequest("follow", parameters)
    }

    static async unfollow(sid, uid) {
        const parameters = { sid: sid, uid: uid };
        return await CommunicationController.genericRequest("unfollow", parameters)
    }

    static async isFollowed(sid, uid) {
        const parameters = { sid: sid, uid: uid };
        return await CommunicationController.genericRequest("isFollowed", parameters)
    }

    static async setProfile(sid, name, picture) {
        const parameters = { sid: sid, name: name, picture: picture };
        return await CommunicationController.genericRequest("setProfile", parameters)
    }
}