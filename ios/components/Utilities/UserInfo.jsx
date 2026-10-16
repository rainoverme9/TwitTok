//IMPORT REACT UTILITIES
import { Image, Pressable, StyleSheet, Text, View } from 'react-native';
import React, { useEffect, useState, useContext } from 'react';
//class
import TwokClass from '../../CLASS/TwokClass';
//DataBase
import StoreClass from '../../CLASS/StoreClass';
//context
import Context from '../../CONTEXT/ContextProvider'

//Mostra la foto e il nome dell'utente. Cliccando su questi due elementi, se si è nella schermata home o nella schermata degli utenti seguiti viene mostrata la bacheca dell'utente selezionato
//Se c'è una posizione associata ad un twok (esclusa la pagina degli utenti seguiti), viene mostrata la posizione che permette di accedere alla mappa
function UserInfo(props) {

    //globalVar
    const { globalVar } = useContext(Context)

    
    //const userInfo
    const uid = props.uid
    const name = props.name
    //const foto
    const [picture, setPicture] = useState(null);
    const BASE64_PIC_HEADER = 'data:image/png;base64,'  //Nel caso la pic dell'utente sia null
    const pversion = props.pversion
    //const posizione
    const [posizione, setPosition] = useState(null);
    const lat = props.lat
    const lon = props.lon

    //USEEFFECT
    //Salva e restituisce la foto dell'utente
    useEffect(() => {
        if (props.picture) {
            setPicture(props.picture)
        } else {
            StoreClass.uploadStoreUserPicture(globalVar.sid, uid, pversion, result => setPicture(result))
        } 
    }, [uid]
    );

    //Ottiene la posizione associata al twok, se presente
    useEffect(() => {
        if (lat != null && lon != null) {
            getPosition(lat, lon)
        }
    }, [lat, lon]
    );

    //FUNCTION
    //Mostrola possibilità di visualizzare la mappa un twok è associato ad una posizione
    function getPosition(lat, lon) {
        TwokClass.getNameAddress({
            latitude: lat,
            longitude: lon
        })
            .then(result => { setPosition((result[0].country != null && result[0].city != null) ? result[0].country + " - " + result[0].city : result[0].name) })
            .catch(error => {if (error == "Error: Not authorized to use location services.") {setPosition("Visualizza Mappa")}})
    }

    return (
        <View style={styles.userInfo}>
            <View>
                {/*Se sono nella schermata Home e nella schermata degli utenti seguiti allora posso accedere alla bacheca dell'utente selezionato*/}
                {props.screen == 'Home_Screen' || props.screen == 'Follower_Screen' ?
                    <Pressable style={styles.row} onPress={() => props.navigation.navigate('UserInfo', { screen: props.screen, params: { uid: uid, name: name } })}>
                        <Image source={picture != null ? { uri: BASE64_PIC_HEADER + picture } : require('../../RISORSE/user.jpg')} style={{ width: 50, height: 50, borderRadius: 25}} />                      

                        <View style={{ width: '80%' }}>
                            <Text style={{ paddingLeft: 10 }}>{name}</Text>
                            {(posizione) ? <Pressable style={styles.row} onPress={() => props.navigation.navigate('Map', { screen: props.screen, params: { position: { lat: lat, lon: lon, posizione: posizione } } })}><Text style={{ paddingLeft: 4}} ellipsizeMode='tail' numberOfLines={1}>📍{posizione}</Text></Pressable> : undefined}
                        </View>
                    </Pressable>
                : 
                    <View style={styles.row} >
                        <View>
                            <Image source={picture != null  ? { uri: BASE64_PIC_HEADER + picture } : require('../../RISORSE/user.jpg')} style={{width: 50, height: 50, borderRadius: 25}} />
                        </View>
                        <View style={{ width: '85%' }}>
                            <Text style={{ paddingLeft: 10 }}>{name}</Text>
                            {(posizione) ? <Pressable style={styles.row} onPress={() => props.navigation.navigate('Map', { screen: props.screen, params: { position: { lat: lat, lon: lon, posizione: posizione } } })}><Text style={{ paddingLeft: 4}} ellipsizeMode='tail' numberOfLines={1}>📍{posizione}</Text></Pressable> : undefined}
                        </View>
                    </View>
                }
            </View>            
        </View>
    );
}

const styles = StyleSheet.create({
    userInfo: {
        height: 70,
        paddingRight: 30,
        paddingLeft: 15,
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        borderBottomWidth: 0.3,
        borderBottomColor: 'grey'
    },
    column: {
        flexDirection: 'column',
        alignItems: 'center'
    },
    row: {
        flexDirection: 'row',
        alignItems: 'center'
    },
});

export default UserInfo;