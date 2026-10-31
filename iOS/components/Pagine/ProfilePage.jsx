//IMPORT REACT UTILITIES
import React, { useContext, useState, useEffect } from 'react';
import { Dimensions, StyleSheet, View, StatusBar, Button } from 'react-native';
//icons
import { FontAwesome } from '@expo/vector-icons';
//utilities
import ListOfTwoks from '../Utilities/ListOfTwoks';
//page
import SettingsPage from './SettingsPage';
//context
import Context from '../../CONTEXT/ContextProvider'


//Mostra la pagina dell'utente loggato
//1) l'utente loggato può vedere i propri twok postati  --> se ne ha 0 viene mostrato un messaggio
//2) l'utente loggato può visualizzare la lista degli utenti seguiti
//3) l'utente loggato può modificare il proprio profilo
function ProfilePage(props) {
    
    //globalVar
    const { globalVar } = useContext(Context);

    //const apertura impostazioni
    const [settings, setSettings] = useState(false);
    //const picture
    const [picture, setPicture] = useState();

    //USEEFFECT
    //Apertura e chiusura impostazioni
    useEffect(() => {
        if (settings) {
            props.navigation.setOptions({
                headerRight: () => (
                    <View style={{ flexDirection: 'row', justifyContent: 'space-around' }}>
                        <Button title="Annulla" onPress={() => setSettings(false)} />
                    </View>
                ),
            });
        } else {
            props.navigation.setOptions({
                headerRight: () => (
                    <View style={{ flexDirection: 'row', justifyContent: 'space-around' }}>
                        <FontAwesome name="users" style={{ paddingRight: 20 }} size={24} color="black" onPress={() => props.navigation.navigate('Follower')} />
                        {/*Apertura delle impostazioni*/}
                        <FontAwesome name="gear" size={24} color="black" onPress={() => setSettings(true)} />
                    </View>
                ),
            });
        }
    }, [settings]);
    


    return (
        //Pagina delle impostazioni
        settings ?
            <SettingsPage onModify={(image) => {setPicture(image), setSettings(false)}}></SettingsPage>
        :
            //Pagina del profilo
            globalVar.network ?
            <View style={styles.profilePage}>
                <StatusBar barStyle='dark-content'></StatusBar>
                <ListOfTwoks
                    uid={globalVar.uid}
                    tidSequence={-1}
                    navigation={props.navigation}
                    screen={'Profile_Screen'}
                    picture={picture}
                ></ListOfTwoks>
            </View>
            :undefined
    );
}

const styles = StyleSheet.create({
    profilePage: {
        width: "100%",
        height: (Dimensions.get('window').height),
        alignItems: 'stretch',
        justifyContent: 'flex-start',
    },
});

export default ProfilePage;