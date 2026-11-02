//IMPORT REACT UTILITIES
import { Dimensions, StyleSheet, Pressable, View } from 'react-native';
import React, { useState, useEffect, useContext } from 'react';
//icons
import { FontAwesome5 } from '@expo/vector-icons';
//utilities
import ListOfTwoks from '../Utilities/ListOfTwoks';
//class
import UserClass from '../../CLASS/UserClass';
//context
import Context from '../../CONTEXT/ContextProvider'



//Mostra la bacheca specifica dell'utente selezionato
//1) l'utente loggato può seguire o smettere di seguire l'utente selezionato
//2) se un post è associato a un luogo può vere sulla mappa la posizione specifica
//tidSequence -1 --> ordine casuale
//tidSequence  n --> dal tid specificato in poi
function UserInfoPage(props) {
    
    //globalVar
    const { globalVar } = useContext(Context)

    //Viene passato l'uid dell'utente
    const uid = props.uid
    //const follower
    const [follower, setFollower] = useState();

    useEffect(() => {
            isFollowed(uid)
            props.navigation.setOptions({
                headerRight: () => ( 
                    follower == false ?
                        <Pressable onPress={() => { follow(uid); setFollower(true)}}>
                            <FontAwesome5 name="user-plus" size={24} color="black" />
                        </Pressable>
                    : <Pressable onPress={() => { unfollow(uid); setFollower(false)}}>
                            <FontAwesome5 name="user-check" size={24} color="black" />
                        </Pressable>
                ),
            });

    }, [props.navigation, follower]);

    //FUNZIONI
    //Permette di aggiungere l'utente selezionato alla lista degli utenti seguiti
    function follow(uid) {
        UserClass.follow(globalVar.sid, uid)
            .then(result => console.log("Utente aggiunto alla lista dei seguiti"))
            .catch(error => alert("Si è verificato un errore durante l'aggiunta' dell'utente alla lista dei seguiti"))
    }
    //Permette di rimuovere l'utente selezionato dalla lista degli utenti seguiti
    function unfollow(uid) {
        UserClass.unfollow(globalVar.sid, uid)
            .then(result => console.log("Utente rimosso dalla lista dei seguiti"))
            .catch(error => alert("Si è verificato un errore durante la rimozione dell'utente dalla lista dei seguiti"))
    }
    //Permette di verificare se l'utente selezionato è presente nella lista degli utenti seguiti
    function isFollowed(uid) {
        UserClass.isFollowed(globalVar.sid, uid)
            .then(result => setFollower(result.followed))
            .catch(error => alert("Si è verificato un errore durante l'identificazione dell'utente come seguito/non seguito"))
    }

    return (
        <View>
            <View style={styles.userStyle}>
                <ListOfTwoks 
                    uid={uid}
                    tidSequence={-1} 
                    navigation={props.navigation}
                    screen={'UserInfoPage_Screen'}
                ></ListOfTwoks>
            </View>
        </View>

    );
}

const styles = StyleSheet.create({
    userStyle: {
        width: "100%",
        height: (Dimensions.get('window').height),
        //backgroundColor: '#87CEEB',
        alignItems: 'stretch',
        justifyContent: 'flex-start',
    },
});

export default UserInfoPage;