//IMPORT REACT UTILITIES
import React, { useState, useContext, useEffect } from 'react';
import {  StyleSheet, TextInput, View, Image, Text, Pressable, TouchableOpacity } from 'react-native';
//imagePicker
import * as ImagePicker from 'expo-image-picker';
//class
import UserClass from '../../CLASS/UserClass'
//DataBase
import StoreClass from '../../CLASS/StoreClass';
//context
import Context from '../../CONTEXT/ContextProvider'

//Mostra la pagina in cui l'utente può modificare la propria foto e il proprio nome
function SettingsPage(props) {
    
    //globalVar
    const { globalVar } = useContext(Context)

    //cont user profile
    const [name, setName] = useState(null);
    const [picture, setPicture] = useState(null);
    const [pversion, setPversion] = useState(null);
    //const immagine di base
    const BASE64_PIC_HEADER = 'data:image/png;base64,'
    //const immagine imagePicker
    const [image, setImage] = useState(null);

    //USEEFFECT
    //Salvataggio e restituzione della foto profilo dell'utente loggato
    useEffect(() => { StoreClass.uploadStoreUserPicture(globalVar.sid, globalVar.uid, null, result => setPicture(result), result => setPversion(result+1)) }, []);


    //FUNZIONI
    //Aggiorna le informazioni dell'utente con nome e foto
    function setProfile(name) {
        UserClass.setProfile(globalVar.sid, name, image)
            .then(result => { console.log("Modifica del profilo avvenuta") })
            .catch(error => alert("Si è verificato un errore durante la modifica del tuo profilo"))
    }

    //Mostra il model con imagepicker
    const pickImage = async () => {
        let result = await ImagePicker.launchImageLibraryAsync({
            mediaTypes: ImagePicker.MediaTypeOptions.Images,
            allowsEditing: true,
            aspect: [4, 3],
            base64: true,
            quality: 1,
        });
        if (!result.canceled) {
            setImage(result.assets[0].base64);
            setPicture(result.assets[0].base64);
        }
    };

    return (
        <View style={styles.container} >
            <View style={styles.settings}>
                <View style={styles.foto}>
                    <Text style={styles.label}>Modifica foto</Text>
                    <Pressable onPress={pickImage}>
                        <Image source={picture != null ? { uri: BASE64_PIC_HEADER + picture } : require('../../RISORSE/user.jpg')} style={{width: 100, height: 100, borderRadius: 60}} />
                    </Pressable>
                </View>
                <View>
                    <View style={{alignItems: 'center'}}>
                        <Text style={styles.label}>Modifica @username</Text>
                    </View>
                    <TextInput
                        style={styles.input}
                        maxLength={20}
                        multiline={false}
                        placeholderTextColor="#7d7e79"
                        onChangeText={value => { setName(value) }}
                    />
                </View>
                <TouchableOpacity
                //Se nè il nome nè la foto vengono modificati il tasto è disabilitato
                    disabled={(name == null && image == null)}
                    style={styles.button}
                    onPress={() => {
                        if (image != null && image.length > 137000) {
                            alert('Il formato della foto eccessivo');
                        } else {
                            name == null ? (setProfile(null, image), StoreClass.storeUserPicture(globalVar.uid, pversion, picture))
                            : image == null ? setProfile(name, null) 
                            : (setProfile(name, image),  StoreClass.storeUserPicture(globalVar.uid, pversion, picture))
                            props.onModify(image)
                        }
                    }}
                >
                    <Text style={styles.buttonText}>Modifica</Text>
                </TouchableOpacity>
            </View>
        </View>
    );
}

const styles = StyleSheet.create({
    settings: {
        alignItems: 'stretch',
        width: "100%",
        padding: 20
    },
    foto: {
        padding: 20,
        alignItems: 'center',
    },
    username: {
        padding: 20
    },

    label: {
        alignItems: 'center',
        color: "#7d7e79",
        fontSize: 16,
        lineHeight: 30,
        padding: 10,
    },
    input: {
        height: 50,
        paddingHorizontal: 20,
        borderRadius: 5,
        borderWidth: 2,
        borderColor: "#e3e3e3",
        backgroundColor: "#fff",
    },
    button: {
        marginTop: 50,
        backgroundColor: "#2980b9",
        padding: 15,
        borderRadius: 15,
    },
    buttonText: {
        color: "#fff",
        fontWeight: "bold",
        fontSize: 16,
        textAlign: "center",
    },
})

export default SettingsPage;